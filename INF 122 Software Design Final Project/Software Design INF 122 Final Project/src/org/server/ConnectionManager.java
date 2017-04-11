package org.server;
import java.net.*;
import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import org.shared.BoardState;
import org.shared.Board;
import org.shared.Move;
import java.util.concurrent.locks.*;

class ConnectionManager 
{
	private ServerSocket socket;
	private List<Player> playerList;
	private boolean serving;
	
	@SuppressWarnings("unchecked")
	public ConnectionManager(int port)
	throws IOException
	{
		this.socket = new ServerSocket(port);
		try
		{
			this.playerList = (ArrayList<Player>)ObjectSerializer.readPlayersFromFile();
		}
		catch(Exception e)
		{
			this.playerList = new ArrayList<Player>();
		}

		if(this.playerList == null)
		{
			this.playerList = new ArrayList<Player>();
		}
		printPlayers();
		//
		this.serving = true;
		
		
	}
	
	public void printPlayers()
	{
		for(Player p : this.playerList)
		{
			System.out.println(p);
		}
	}
	
	public void mainLoop()
	throws IOException
	{
		while(serving)
		{
			ConnectionManager.GameHandler gH = new ConnectionManager.GameHandler();
			for(int i = 0; i < 2; ++i)
			{
				Socket client = socket.accept();
				Thread t = new Thread()
				{
					public void run()
					{
						try
						{
							gH.initializationSequence(client);				
							gH.clientLoop();
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
					}
				};
				t.start();
			}
		}
	}
	
	public void stop()
	{
		this.serving = false;
		printPlayers();
		ObjectSerializer.writePlayersToFile(this.playerList);
		System.out.println("Successfully serialized player(s) to disk with filename " + ObjectSerializer.playerListFilename);
	}
	
	public Player givePlayer(String name)
	{
		for(Player player : this.playerList)
		{
			if(player.getName().equals(name))
			{
				return player;
			}
		}
		return null;
	}
	
	class SocketAndPlayer
	{
		private Player player;
		private Socket socket;
		public SocketAndPlayer(Socket socket, Player player)
		{
			this.player = player;
			this.socket = socket;
		}
		
		public Socket getSocket()
		{
			return this.socket;
		}
		
		public Player getPlayer()
		{
			return this.player;
		}
	}
	//nested class used to keep track of game between two clients
	//allows for multiple instances of games 
	class GameHandler
	{
		private String referenceClient;
		private Map<String, SocketAndPlayer> ready;
		private Logic logic;
		private Lock lockVal;
		private Lock secondLock, thirdLock;
		
		public GameHandler()
		{
			this.ready = new HashMap<String,SocketAndPlayer>();
			this.lockVal = new ReentrantLock();
			this.secondLock = new ReentrantLock();
			this.thirdLock = new ReentrantLock();
		}
		
		private String getOtherClient()
		{
			for(Map.Entry<String,SocketAndPlayer> entry : this.ready.entrySet())
			{
				if(!entry.getKey().equals(referenceClient))
				{
					return entry.getKey();
				}
			}
			return null;
		}
	
		private String getClientMessage(Socket client)
		throws IOException
		{
			DataInputStream in = new DataInputStream(client.getInputStream());
			String message = in.readUTF();
			return message;
		}
		
		private void sendClientMessage(Socket client, String message)
		throws IOException
		{
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			out.writeUTF(message);
		}
		
		private void clientSetReady(Socket client, Player p, String message)
		{
			
			String[] splitMessage = message.split(":");
			this.ready.put(splitMessage[0], new SocketAndPlayer(client, p));
			if(this.referenceClient == null)
			{
				this.referenceClient = splitMessage[0];
			}
		}
		
		private String getClientName(String message)
		{
			return message.split(":")[0];
		}
		
		private boolean bothClientsReady()
		{
			return this.ready.size() == 2;
		}
		
		public void initializationSequence(Socket client)
		{
			try	
			{
				boolean gaveName = false;
				String clientName = "";
				
				sendClientMessage(client, LogicFactory.getClassList());
				while(true)
				{
					if(!gaveName)
					{
						String message = getClientMessage(client);	
						if(this.logic == null)
						{
							this.logic = LogicFactory.getLogic(message.split(":")[1]);
						}
						
						clientName = getClientName(message);
						////test this out
						Player p = ConnectionManager.this.givePlayer(clientName);
						if(p == null)
						{
							p = new Player(clientName);
							ConnectionManager.this.playerList.add(p);
						}
						clientSetReady(client, p, message);
						///////
						gaveName = true;
					}					
					
					if(bothClientsReady())
					{
						break;
					}
					Thread.sleep(400);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		private void sendAll(Gson gson)
		throws IOException
		{
			for(Map.Entry<String,SocketAndPlayer> entry : this.ready.entrySet())
			{
				Board board = new Board(this.logic.getBoard());
				//check Key
				if(!entry.getKey().equals(this.referenceClient))
				{
					if (board.getCurrentWinState() == BoardState.WIN) {
						board.setCurrentWinState(BoardState.LOSE);
					} else if (board.getCurrentWinState() == BoardState.LOSE) {
						board.setCurrentWinState(BoardState.WIN);
					}
					board.flipCurrentlyTurn();
					sendClientMessage(entry.getValue().getSocket(), gson.toJson(board));
				} else {
					sendClientMessage(entry.getValue().getSocket(), gson.toJson(this.logic.getBoard()));
				}
			}
		}
		
		private Socket getWaitClient()
		{
			Socket client = null;
			for(Map.Entry<String,SocketAndPlayer> entry : this.ready.entrySet())
				{
					if(this.logic.getBoard().isCurrentlyTurn())
					{
						if(entry.getKey().equals(referenceClient))
						{
							client = entry.getValue().getSocket();
							break;
						}
					}
					else
					{
						if(!entry.getKey().equals(referenceClient))
						{
							client = entry.getValue().getSocket();
							break;
						}
					}
				}
			return client;
		}
		
		private boolean lock()
		{
			return this.lockVal.tryLock();
		}
		
		private void unlock()
		{
			this.lockVal.unlock();
		}
		
		private boolean eitherClientDisconnected()
		{
			for(Map.Entry<String,SocketAndPlayer> client : this.ready.entrySet())
			{
				if(!client.getValue().getSocket().isConnected())
				{
					return true;
				}
			}
			return false;
		}
		
		public void clientLoop()//Socket client)
		throws IOException
		{
			Gson gson = new Gson();
			if (thirdLock.tryLock())
			{
				sendAll(gson); // send initial board state to the clients
			}
			while(logic.getBoard().getCurrentWinState() == BoardState.NO_WIN_YET || eitherClientDisconnected())
			{
				//Socket client = getWaitClient();
				//needs to figure out client name
				if(lock())
				{
					Socket client = getWaitClient();
					String message;
					try
					{
						message = getClientMessage(client);
					}
					catch(EOFException | SocketException e)
					{
						unlock();
						break;
					}
					if(eitherClientDisconnected())
					{
						break;
					}
					Move m = gson.fromJson(message, Move.class);
					if (this.logic.checkValidMove(m))
						this.logic.makeMove(m);
					sendAll(gson);
					unlock();
				}
			}
			
			if(secondLock.tryLock() && !eitherClientDisconnected())
			{
				Player p1 = ready.get(referenceClient).getPlayer();
				Player p2 = ready.get(getOtherClient()).getPlayer();
				boolean finallyFlag = false;
				if (this.logic.getBoard().getCurrentWinState() == BoardState.WIN) {
					p1.incrementWinCount();
					p2.incrementLossCount();
					finallyFlag = true;
				} else if (this.logic.getBoard().getCurrentWinState() == BoardState.LOSE) {
					p1.incrementLossCount();
					p2.incrementWinCount();
					finallyFlag = true;
				} else if (this.logic.getBoard().getCurrentWinState() == BoardState.TIE) {
					finallyFlag = true;
				}
				if (finallyFlag) {
					p1.incrementGamesPlayed();
					p2.incrementGamesPlayed();
				}
			}
		}
	}	
}
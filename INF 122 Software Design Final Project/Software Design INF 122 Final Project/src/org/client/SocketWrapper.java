package org.client;

import java.net.*;
import java.io.*;

import com.google.gson.*;

import org.shared.*;

class SocketWrapper
{
	public static final int PORT = 4242; 
	
	private Socket client;
	private String clientName;
	private String otherClient;
	private String gameName;
	private Gson gson;
	private DataInputStream in;
	private DataOutputStream out;
	
	public SocketWrapper() 
	{
		client = new Socket();
		clientName = null;
		gson = new Gson();
	}
	
	public SocketWrapper(String clientName, String gameName, String serverName, int port)
	{
		gson = new Gson();
		clientName = null;
		try
		{	

			this.client = new Socket(serverName, port);
			
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			in.readUTF().split(":");//where the game options come in
			

			OutputStream outToServer = client.getOutputStream();
			out = new DataOutputStream(outToServer);
         
			out.writeUTF(clientName+":"+gameName);
			inFromServer = client.getInputStream();
			in = new DataInputStream(inFromServer);
			String[] splitMessage = in.readUTF().split(":");
			this.otherClient = splitMessage[0];
			this.gameName = splitMessage[1];
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void close() throws IOException
	{
		this.client.close();
	}
	
	public String getOtherClient()
	{
		return this.otherClient;
	}
	public String getGameName()
	{
		return this.gameName;
	}
	public Socket getClientSocket() {
		return client;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void connect(String serverName, int port) throws IOException {
		try
		{
			if (!client.isConnected() || client.isClosed()) {
				client = new Socket(serverName, port);
			}
			OutputStream outToServer = client.getOutputStream();
			out = new DataOutputStream(outToServer);
         
			InputStream inFromServer = client.getInputStream();
			in = new DataInputStream(inFromServer);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		} 
	}
	
	public void sendMessage(String s) throws IOException {
		if (out == null) {
			out = new DataOutputStream(client.getOutputStream());
		}
		out.writeUTF(s);
	}
	
	public void sendMessageAsJson(Object o) throws IOException {
		String jsonObject = gson.toJson(o);
		if (out == null) {
			out = new DataOutputStream(client.getOutputStream());
		}
		out.writeUTF(jsonObject);
	}
	
	public Board getBoardState() throws IOException {
		String rawJsonBoard = getMessage();
		return gson.fromJson(rawJsonBoard, Board.class);
	}
	
	public String getMessage() throws IOException {
		if (in == null) {
			in = new DataInputStream(client.getInputStream());
		}
		return in.readUTF();
	}
}
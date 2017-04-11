package org.server;
import java.util.Scanner;
import java.net.InetAddress;
public class BoardGameServer
{
	public static ConnectionManager cm;
	
	public static void main(String[] args)
	{		
		try {
			System.out.println(InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {
			
		}
		Thread t1 = new Thread()
		{
			public void run()
			{
				try
				{
					BoardGameServer.cm = new ConnectionManager(4242);
					cm.mainLoop();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		t1.start();
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("Board game server successfully started. Type 'help' for options.");
		for (int i = 0; i < 100000; ++i); // hacky delay

		boolean serverConsoleLoop = true;
		while(serverConsoleLoop)
		{
			System.out.print("> ");
			String check = s.nextLine();
			switch (check) {
			case "print players":
				BoardGameServer.cm.printPlayers();
				break;
			case "stop":
				BoardGameServer.cm.stop();
				serverConsoleLoop = false;
				System.out.println("Shutting down...");
				break;
			case "help":
			case "Help":
				System.out.println("HELP:");
				System.out.println("\thelp\t\tprint this menu");
				System.out.println("\tprint players\tprint out list of all players who have played on this server");
				System.out.println("\tstop\t\tshutdown the server");
			}
		}
		
		s.close();
		System.exit(0);
	}
}
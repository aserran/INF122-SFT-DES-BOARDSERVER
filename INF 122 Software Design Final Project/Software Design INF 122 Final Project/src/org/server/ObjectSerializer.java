package org.server;

import java.io.*;

public class ObjectSerializer {
	
	public static final String playerListFilename = "playerlist.data";
	
	// hardcoded for serializing Player(s)
	public static void writePlayersToFile(Object o) {
		try {
			FileOutputStream playerRecordOut = new FileOutputStream(playerListFilename);
			ObjectOutputStream objOutStream = new ObjectOutputStream(playerRecordOut);
			objOutStream.writeObject(o);
			
			objOutStream.close();
			playerRecordOut.close();
		} catch (IOException e) {
			System.err.println("Error serializing player(s) to disk");
			e.printStackTrace();
		}
	}
	
	// just in case we want to serialize things other than Player in the future
	public static void writeToFile(Object o, String filename) {
		try {
			FileOutputStream playerRecordOut = new FileOutputStream(filename);
			ObjectOutputStream objOutStream = new ObjectOutputStream(playerRecordOut);
			objOutStream.writeObject(o);
			
			objOutStream.close();
			playerRecordOut.close();
			System.out.println("Successfully serialized object to disk with filename " + filename);
		} catch (IOException e) {
			System.err.println("Error serializing object to disk");
			e.printStackTrace();
		}
	}
	
	// hardcoded for deserializing Player(s)
	public static Object readPlayersFromFile() {
		Object o = null;
		
		try {
			FileInputStream playerRecordIn = new FileInputStream(playerListFilename);
			ObjectInputStream objInStream = new ObjectInputStream(playerRecordIn);
			
			o = objInStream.readObject();
			
			objInStream.close();
			playerRecordIn.close();
		} catch (IOException e) {
			System.err.println("Error reading player(s) from disk");
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Error reading player(s) from disk");
			//e.printStackTrace();
		}
		
		return o;
	}
	
	// just in case we want to serialize things other than Player in the future
	public static Object readFromFile(String filename) {
		Object o = null;
		
		try {
			FileInputStream playerRecordIn = new FileInputStream(filename);
			ObjectInputStream objInStream = new ObjectInputStream(playerRecordIn);
			
			o = objInStream.readObject();
			
			objInStream.close();
			playerRecordIn.close();
		} catch (IOException e) {
			System.err.println("Error reading object from disk");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Error reading object from disk");
			e.printStackTrace();
		}
		
		return o;
	}

}

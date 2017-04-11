package org.server;

import java.util.*;

public class LogicFactory 
{
	public static Logic getLogic(String gameType)
	{
		if (gameType == null)
		{
			return null;
		}
		
		switch (gameType)
		{
		case "TicTacToe":
			return new TicTacToeLogic();
			
		case "Checkers":
			return new CheckersLogic();
			
		case "Othello":
			return new OthelloLogic();
		}
		
		return null; // TODO: should we throw an exception here?
	}
	
	public static String getClassList()
	{
		List<String> stringList = new ArrayList<String>(Arrays.asList("TicTacToe", "Checkers", "Othello"));
		String retString = "";
		for(String s : stringList)
		{
			retString = retString + ":"+s;
		}
		return retString.substring(1, retString.length());
	}

}

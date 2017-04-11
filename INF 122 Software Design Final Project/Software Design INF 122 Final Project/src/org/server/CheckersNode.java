package org.server;

public class CheckersNode {
	int x;	//Column
	int y;	//Row
	
	private CheckersNode NENode, NWNode, SENode, SWNode;
	
	public CheckersNode(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public String toString()
	{
		return ("Col: " + x + "|Row: " + y);
	}
}

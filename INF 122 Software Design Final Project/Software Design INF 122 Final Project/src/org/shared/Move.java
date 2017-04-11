package org.shared;

// will be subclassed for games that require more information with each move
public class Move 
{
	private int row, col, destRow, destCol;
	PieceEnum player;

	public Move(int x, int y, PieceEnum player)
	{
		this.row = y;
		this.col = x;
		this.player = player;
	}
	
	public Move(Move m, Move n)
	{
		this.row = m.getRow();
		this.col = m.getCol();
		this.player = n.getPlayer();
		this.destCol = n.getCol();
		this.destRow = n.getRow();
	}
	
	public Move(int x, int y, PieceEnum player, int destX, int destY)
	{
		this.row = y;
		this.col = x;
		this.player = player;
		this.destRow = destY;
		this.destCol = destX;
	}
	
	public int getRow() 
	{
		return row;
	}
	
	public int getCol() 
	{
		return col;
	}
	
	public int getDestRow() {
		return destRow;
	}

	public int getDestCol() {
		return destCol;
	}

	public PieceEnum getPlayer() {
		return player;
	}

	public void setPlayer(PieceEnum player) {
		this.player = player;
	}
	
}

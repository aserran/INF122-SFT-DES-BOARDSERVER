package org.shared;

public class CheckersMove extends Move{

	int destX, destY;
	
	public CheckersMove(int startX, int startY, PieceEnum player, int destX, int destY) {
		super(startX, startY, player);
		this.destX = destX;
		this.destY = destY;
		// TODO Auto-generated constructor stub
	}

	public int getDestX() {
		return destX;
	}

	public int getDestY() {
		return destY;
	}
	
	

}

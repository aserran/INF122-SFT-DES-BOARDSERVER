package org.shared;

public class Cell {
	PieceEnum cellStatus;	//PLAYER1, PLAYER2, or EMPTY

	public Cell(PieceEnum cellStatus)
	{
		this.cellStatus = cellStatus;
	}
	
	public PieceEnum getCellStatus() {
		return cellStatus;
	}

	public void setCellStatus(PieceEnum cellStatus) {
		this.cellStatus = cellStatus;
	}
	
	
}

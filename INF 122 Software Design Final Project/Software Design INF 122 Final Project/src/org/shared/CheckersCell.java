package org.shared;

public class CheckersCell extends Cell{

	boolean isKing;
	boolean explored = false;
	boolean captured = false;
	boolean hasSecondInput = true;
	
	public CheckersCell(PieceEnum cellStatus, boolean isKing) {
		super(cellStatus);
		this.isKing = isKing;
	}

	public boolean isKing() {
		return isKing;
	}

	public void setKing(boolean isKing) {
		this.isKing = isKing;
	}

	public boolean isExplored() {
		return explored;
	}

	public void setExplored(boolean explored) {
		this.explored = explored;
	}

	public boolean isCaptured() {
		return captured;
	}

	public void setCaptured(boolean captured) {
		this.captured = captured;
	}
	
	

	
}

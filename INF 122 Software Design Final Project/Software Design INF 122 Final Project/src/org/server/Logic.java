package org.server;

import org.shared.Board;
import org.shared.Move;

public abstract class Logic 
{
	/* TODO: since Board is now generic, unspecified type parameters here
	might cause problems later on */
	protected Board board; 
	
	boolean secondInput = false;
	
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public boolean isSecondInput()
	{
		return secondInput;
	}
	
	public abstract boolean checkValidMove(Move m);
	public abstract void makeMove(Move m);
	public abstract void updateBoardState();
}

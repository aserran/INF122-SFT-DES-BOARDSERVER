package org.shared;

import java.util.ArrayList;

public class Board	
{
	private ArrayList<ArrayList<Cell>> board;
	private int player1Count = 0, player2Count = 0;	//Allows for easy win-state calculation just by checking if 0
	
	private BoardState currentWinState;
	private boolean currentlyTurn;
	
	
	public Board(int numRows, int numCols) 
	{
		board = new ArrayList<ArrayList<Cell>>();
		for (int i = 0; i < numCols; i++)
		{
			board.add(new ArrayList<Cell>(numRows));
		}
		clearBoard();
		currentWinState = BoardState.NO_WIN_YET;
		currentlyTurn = true;
	}
	
	public Board(Board b)
	{
		this.board = new ArrayList<ArrayList<Cell>>(b.getBoard());
		this.player1Count = b.getPlayer1Count();
		this.player2Count = b.getPlayer2Count();
		this.currentWinState = b.getCurrentWinState();
		this.currentlyTurn = b.isCurrentlyTurn();
	}

	public boolean isCurrentlyTurn() {
		return currentlyTurn;
	}
	public void setCurrentlyTurn(boolean currentlyTurn) {
		this.currentlyTurn = currentlyTurn;
	}
	public void flipCurrentlyTurn() {
		this.currentlyTurn = !currentlyTurn;
	}
	
	public ArrayList<ArrayList<Cell>> getBoard() {
		return board;
	}
	
	public void setBoard(ArrayList<ArrayList<Cell>> board) {
		this.board = board;
	}

	public void setBoardCellStatus(int col, int row, PieceEnum cellStatus)
	{
		board.get(col).get(row).setCellStatus(cellStatus);
	}
	
	public void setBoardCell(int col, int row, Cell cell)
	{
		board.get(col).set(row, cell);
	}
	
	public Cell getBoardCell(int col, int row)
	{
		return board.get(col).get(row);
	}
	
	public PieceEnum getBoardCellStatus(int col, int row)
	{
		return board.get(col).get(row).getCellStatus();
	}
	
	public int getPlayer1Count() {
		return player1Count;
	}

	public void setPlayer1Count(int player1Count) {
		this.player1Count = player1Count;
	}

	public int getPlayer2Count() {
		return player2Count;
	}

	public void setPlayer2Count(int player2Count) {
		this.player2Count = player2Count;
	}
	
	public BoardState getCurrentWinState() {
		return this.currentWinState;
	}
	
	public void setCurrentWinState(BoardState bs)
	{
		currentWinState = bs;
	}
	
	public void clearBoard()
	{
		for (ArrayList<Cell> column : board)
		{
			for (Cell cell : column)
			{
				cell.setCellStatus(PieceEnum.EMPTY);
			}
		}
	}
	
}

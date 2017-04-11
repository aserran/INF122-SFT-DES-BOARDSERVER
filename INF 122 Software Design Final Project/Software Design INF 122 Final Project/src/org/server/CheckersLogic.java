package org.server;

import org.shared.Board;
import org.shared.BoardState;
import org.shared.Move;
import org.shared.PieceEnum;
import org.shared.CheckersCell;

public class CheckersLogic extends Logic{

	private int rows = 8, cols = 8;
	
	public CheckersLogic()
	{
		board = new Board(cols, rows);
		fillBoard();
		setupBoard();
		board.setPlayer1Count(12);
		board.setPlayer2Count(12);
		secondInput = true;
	}
	
	public void fillBoard()
	{
		for (int col = 0; col < cols; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				(board.getBoard().get(col)).add(new CheckersCell(PieceEnum.EMPTY, false));
			}
		}
	}
	
	@Override
	public boolean checkValidMove(Move m) {
		// TODO Auto-generated method stub
		int x = m.getCol();
		int y = m.getRow();
		int dx = m.getDestCol();
		int dy = m.getDestRow();
		((CheckersCell) board.getBoardCell(x, y)).setExplored(true);
		//Check if out of bounds. Should be impossible.
		if (dx > 7 || dy > 7 || dx < 0 || dy < 0)	
		{
			return false;
		}
		//Check if destination cell is valid
		if (board.getBoardCellStatus(dx, dy) != PieceEnum.EMPTY)
		{
			return false;
		}
		if ((m.getPlayer() == PieceEnum.PLAYER1 && x == 7) || (m.getPlayer() == PieceEnum.PLAYER2 && x == 0))
		{
			((CheckersCell) board.getBoardCell(x, y)).setKing(true);
		}
		//Check if moving right piece
		if (board.getBoardCellStatus(x, y) == PieceEnum.PLAYER1 && !board.isCurrentlyTurn())
		{
			return false;
		}
		else if ((Math.abs(x - dx) == 1 && Math.abs(y - dy) == 0) || Math.abs(x - dx) == 0 && Math.abs(y - dy) == 1)
		{
			return false;
		}
		else if (board.getBoardCellStatus(x, y) == PieceEnum.PLAYER2 && board.isCurrentlyTurn())
		{
			return false;
		}
		else if (board.getBoardCellStatus(x, y) == PieceEnum.EMPTY)
		{
			return false;
		}
		
		
		
		//Checks if the jump is further than a diagonal move
		if (Math.abs(x - dx) > 1 || Math.abs(y - dy) > 1)	
		{
			//Checks if destination is in the right square
			if (Math.abs(x - dx) % 2 == 0 && Math.abs(y - dy) % 2 == 0)	
			{
				//Begin processing the path
				
				//Check if a jump can be made and made by right piece. If it's player 1 or King type:
				
				//This if statement processes a player 1 jump from left to right, going diagonally NE
				if (y > 1 && x < 6 && checkOppositePlayer(board.getBoardCellStatus(x + 1, y - 1), m.getPlayer()) && board.getBoardCellStatus(x + 2, y - 2) == PieceEnum.EMPTY 
						&& (m.getPlayer() == PieceEnum.PLAYER1 || ((CheckersCell) board.getBoardCell(x, y)).isKing()) && !((CheckersCell) board.getBoardCell(x + 1, y - 1)).isExplored())
				{	
					int currX = x + 2, currY = y - 2;	//New focus cell
					if (currX == dx && currY == dy)	//Final destination
					{
						board.setBoardCell(currX, currY, new CheckersCell(m.getPlayer(), ((CheckersCell) board.getBoardCell(x, y)).isKing()));
						//Checks if there are more nodes. If either checkValidMove returns true, this is not the last move therefore not a valid move
						if (checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)) || 
								checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy))) 	
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							return false;	
						}
						//If this is a valid, final destination cell, sets the previously jumped over cell to captured
						else 
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							((CheckersCell) board.getBoardCell(x + 1, y - 1)).setCaptured(true);
							return true;
						}
					}
					else if (Math.abs(currX - dx) > 1 || Math.abs(currY - dy) > 1)	
					{
						board.setBoardCell(currX, currY, new CheckersCell(m.getPlayer(), ((CheckersCell) board.getBoardCell(x, y)).isKing()));
						//Not final destination. Create new moves and return true if either are valid paths. Checks twice for both paths.
						if (checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)) || 
								checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)))
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							//If path is valid, sets previously jumped over cell to captured status.
							((CheckersCell) board.getBoardCell(x + 1, y - 1)).setCaptured(true);
							return true;
						}
						else
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							return false;
						}
					}
				}
				//This if statement processes a player 1 jump from left to right OR king piece, going diagonally SE
				else if (x < 6 && y < 6 && checkOppositePlayer(board.getBoardCellStatus(x + 1, y + 1), m.getPlayer()) && board.getBoardCellStatus(x + 2, y + 2) == PieceEnum.EMPTY 
						&& (m.getPlayer() == PieceEnum.PLAYER1 || ((CheckersCell) board.getBoardCell(x, y)).isKing()) && !((CheckersCell) board.getBoardCell(x + 1, y + 1)).isExplored())
				{	
					int currX = x + 2, currY = y + 2;	//New focus cell
					if (currX == dx && currY == dy)	//Final destination
					{
						board.setBoardCell(currX, currY, new CheckersCell(m.getPlayer(), ((CheckersCell) board.getBoardCell(x, y)).isKing()));
						//Checks if there are more nodes. If either checkValidMove returns true, this is not the last move therefore not a valid move
						//Calls twice to update explored flag to explore other paths if there are paths.
						if (checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)) || 
								checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy))) 	
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							return false;	
						}
						//If this is a valid, final destination cell, sets the previously jumped over cell to captured
						else 
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							((CheckersCell) board.getBoardCell(x + 1, y + 1)).setCaptured(true);
							return true;
						}
					}
					else if (Math.abs(currX - dx) > 1 || Math.abs(currY - dy) > 1)
					{
						board.setBoardCell(currX, currY, new CheckersCell(m.getPlayer(), ((CheckersCell) board.getBoardCell(x, y)).isKing()));
						//Checks other moves recursively to look for the final destination.
						if (checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)) ||
								checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)))
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							//If path is valid, sets previously jumped over cell to captured status.
							((CheckersCell) board.getBoardCell(x + 1, y + 1)).setCaptured(true);
							return true;
						}
						else 
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							return false;
						}
					}
				}
				//This if statement processes a player 2 jump from right to left OR king piece, going diagonally NW
				else if (x > 1 && y > 1 && checkOppositePlayer(board.getBoardCellStatus(x - 1, y - 1), m.getPlayer()) && board.getBoardCellStatus(x - 2, y - 2) == PieceEnum.EMPTY 
						&& (m.getPlayer() == PieceEnum.PLAYER2 || ((CheckersCell) board.getBoardCell(x, y)).isKing()) && !((CheckersCell) board.getBoardCell(x - 1, y - 1)).isExplored())
				{	
					int currX = x - 2, currY = y - 2;	//New focus cell
					if (currX == dx && currY == dy)	//Final destination
					{
						board.setBoardCell(currX, currY, new CheckersCell(m.getPlayer(), ((CheckersCell) board.getBoardCell(x, y)).isKing()));
						//Checks if there are more nodes. If either checkValidMove returns true, this is not the last move therefore not a valid move
						if (checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)) ||
								checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)))
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							return false;	
						}
						//If this is a valid, final destination cell, sets the previously jumped over cell to captured
						else 
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							((CheckersCell) board.getBoardCell(x - 1, y - 1)).setCaptured(true);
							return true;
						}
					}
					else if (Math.abs(currX - dx) > 1 || Math.abs(currY - dy) > 1)
					{
						board.setBoardCell(currX, currY, new CheckersCell(m.getPlayer(), ((CheckersCell) board.getBoardCell(x, y)).isKing()));
						//Checks other moves recursively to look for the final destination.
						if (checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)) ||
								checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)))
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							//If path is valid, sets previously jumped over cell to captured status.
							((CheckersCell) board.getBoardCell(x - 1, y - 1)).setCaptured(true);
							return true;
						}
						else 
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							return false;
						}
					}
				}
				//This if statement processes a player 2 jump from right to left OR king piece, going diagonally SW
				else if (x > 1 && y < 7 && checkOppositePlayer(board.getBoardCellStatus(x - 1, y + 1), m.getPlayer()) && board.getBoardCellStatus(x - 2, y + 2) == PieceEnum.EMPTY 
						&& (m.getPlayer() == PieceEnum.PLAYER2 || ((CheckersCell) board.getBoardCell(x, y)).isKing()) && !((CheckersCell) board.getBoardCell(x - 1, y + 1)).isExplored())
				{	
					int currX = x - 2, currY = y + 2;	//New focus cell
					if (currX == dx && currY == dy)	//Final destination
					{
						board.setBoardCell(currX, currY, new CheckersCell(m.getPlayer(), ((CheckersCell) board.getBoardCell(x, y)).isKing()));
						//Checks if there are more nodes. If either checkValidMove returns true, this is not the last move therefore not a valid move
						if (checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)) ||
								checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)))
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							return false;	
						}
						//If this is a valid, final destination cell, sets the previously jumped over cell to captured
						else 
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							((CheckersCell) board.getBoardCell(x - 1, y + 1)).setCaptured(true);
							return true;
						}
					}
					else if (Math.abs(currX - dx) > 1 || Math.abs(currY - dy) > 1)
					{
						board.setBoardCell(currX, currY, new CheckersCell(m.getPlayer(), ((CheckersCell) board.getBoardCell(x, y)).isKing()));
						//Checks other moves recursively to look for the final destination.
						if (checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)) ||
								checkValidMove(new Move(currX, currY, m.getPlayer(), dx, dy)))
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							//If path is valid, sets previously jumped over cell to captured status.
							((CheckersCell) board.getBoardCell(x - 1, y + 1)).setCaptured(true);
							return true;
						}
						else 
						{
							board.setBoardCell(currX, currY, new CheckersCell(PieceEnum.EMPTY, false));
							return false;
						}
					}
					//Horizontal/Vertical move, not diagonal
					else if (((x - dx) == 0 && Math.abs(y - dy) > 0) || (Math.abs(x - dx) > 0 && Math.abs(y - dy) == 0))
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			else
			{
				//Not correct square. Not possible
				return false;	
			}
		}
		//Case for a diagonal move
		else	
		{
			//Checks if cell is empty
			if (board.getBoardCellStatus(dx, dy) == PieceEnum.EMPTY)	
			{
				//Empty
				if ((m.getPlayer() == PieceEnum.PLAYER1 && dx > x) || ((CheckersCell) board.getBoardCell(x, y)).isKing())
				{
					return true;	
				}
				else if ((m.getPlayer() == PieceEnum.PLAYER2 && dx < x) || ((CheckersCell) board.getBoardCell(x, y)).isKing())
				{
					return true;	
				}
				else
				{
					return false;
				}
			}
			else	
			{
				//Not empty
				return false;	
			}
		}
		return false;	 
	}

	@Override
	public void makeMove(Move m) {
		// Move will require it's own sub-class, containing it's own position as well as the destination's position.

		board.setBoardCell(m.getDestCol(), m.getDestRow(), board.getBoardCell(m.getCol(), m.getRow()));
		board.setBoardCell(m.getCol(), m.getRow(), new CheckersCell(PieceEnum.EMPTY, false));
		board.flipCurrentlyTurn();
		updateBoard();
		updateBoardState();
		resetCaptureExplore();
	}
	
	

	@Override
	public void updateBoardState() {
		if (board.getPlayer1Count() == 0)
		{
			board.setCurrentWinState(BoardState.LOSE);
		}
		else if (board.getPlayer2Count() == 0)
		{
			board.setCurrentWinState(BoardState.WIN);
		}
	}
	
	/*
	 * Resets captured and explored flags per each cell on the board at the end of a turn.
	 */
	public void resetCaptureExplore()
	{
		for (int col = 0; col < 7; col++)
		{
			for (int row = 0; row < 7; row++)
			{
				((CheckersCell) board.getBoardCell(col, row)).setExplored(false);
				((CheckersCell) board.getBoardCell(col, row)).setCaptured(false);
			}
		}
	}
	
	/*
	 * Updates the board to reflect opponent captures.
	 */
	public void updateBoard()
	{
		for (int col = 0; col <= 7; col++)
		{
			for (int row = 0; row < 7; row++)
			{
				if (((CheckersCell) board.getBoardCell(col, row)).isCaptured())
				{
					if (board.getBoardCellStatus(col, row) == PieceEnum.PLAYER1)
					{
						board.setPlayer1Count(board.getPlayer1Count() - 1);
					}
					else if (board.getBoardCellStatus(col, row) == PieceEnum.PLAYER2)
					{
						board.setPlayer2Count(board.getPlayer2Count() - 1);
					}
					board.setBoardCellStatus(col, row, PieceEnum.EMPTY);
					((CheckersCell) board.getBoardCell(col, row)).setKing(false);
				}
			}
		}
	}
	
		
	public boolean checkOppositePlayer(PieceEnum cell, PieceEnum player)
	{
		if (cell == player)
		{
			return false;
		}
		else if (cell == PieceEnum.EMPTY)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void setupBoard()
	{
		boolean alt = true;
		for (int col = 0; col < 3; col++)
		{
			if (col != 0)
			{
				alt = !alt;
			}
			for (int row = 0; row < 8; row++)
			{
				if (alt)
				{
					board.setBoardCellStatus(col, row, PieceEnum.PLAYER1);
					alt = !alt;
				}
				else
				{
					alt = !alt;
				}
			}
		}
		alt = false;
		for (int col = 5; col < 8; col++)
		{
			if (col != 5)
			{
				alt = !alt;
			}
			for (int row = 0; row < 8; row++)
			{
				if (alt)
				{
					board.setBoardCellStatus(col, row, PieceEnum.PLAYER2);
					alt = !alt;
				}
				else
				{
					alt = !alt;
				}
			}
		}
	}
}

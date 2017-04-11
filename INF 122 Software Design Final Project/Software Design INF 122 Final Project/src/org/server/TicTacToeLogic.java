
package org.server;
import java.util.HashMap;
import java.util.Map;

import org.shared.Board;
import org.shared.BoardState;
import org.shared.Move;
import org.shared.PieceEnum;
import org.shared.PlayerEnum;
import org.shared.Cell;

public class TicTacToeLogic extends Logic{
	int rows;
	int cols;
	Map<PlayerEnum,String> currentPlayer;
	

	public TicTacToeLogic(){
		rows = 3;
		cols = 3;
		board = new Board(rows,cols);
		fillBoard();
		Map<PlayerEnum,String> currentPlayer = new HashMap<PlayerEnum,String>();
		currentPlayer.put(PlayerEnum.PLAYER1,"X");
		currentPlayer.put(PlayerEnum.PLAYER2, "O");
	}
	
	public void fillBoard()
	{
		for (int col = 0; col < cols; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				(board.getBoard().get(col)).add(new Cell(PieceEnum.EMPTY));
			}
		}
	}
	
//	private PlayerEnum changePlayer(PlayerEnum player){
//		if (player.toString() == "PLAYER1"){
//			return PlayerEnum.PLAYER2;
//		}
//		return PlayerEnum.PLAYER1;
//	}
//	
//	private PieceEnum pieceEnumConverter(PlayerEnum pe){
//		if (pe.equals(PlayerEnum.PLAYER1)){
//			return PieceEnum.PLAYER1;
//		}
//		return PieceEnum.PLAYER2;
//	}
		

	
	@Override
	public boolean checkValidMove(Move m){
		int row = m.getRow();
		int col = m.getCol();
		//Simply returns true if the move is in bounds and if there is no existing move already on the board.
		// Shouldn't need to call this function from the main since it is called before makeMove()
		
		
		//This if statement should check if the current player can only make a move.
		
		//if ( (board.isCurrentlyTurn() && m.getPlayer().equals(PlayerEnum.PLAYER1)) || (!board.isCurrentlyTurn() && m.getPlayer().equals(PlayerEnum.PLAYER2))){

			if ((row >= 0) && (row < 3)){
				if ((col >= 0) && (col < 3)){
					if (board.getBoardCellStatus(col, row).equals(PieceEnum.EMPTY)){
						return true;
					}
				}
			}
		//}
		
		
		
		return false;
	}
	public void makeMove(Move m){
		int row = m.getRow();
		int col = m.getCol();
		
		if (checkValidMove(m)){
			board.setBoardCellStatus(col,row,m.getPlayer());
			board.flipCurrentlyTurn();
			
		}
		else{
			System.out.println("Incorrect input. Try again.");
		}
		updateBoardState();
		
	}
	public PieceEnum checkForWin(){
		//Checking Rows
		for (int i = 0; i < 3; i++){
			if ( (board.getBoardCellStatus(i,0).equals(board.getBoardCellStatus(i,1))) && 
					(board.getBoardCellStatus(i,1).equals(board.getBoardCellStatus(i,2)) && 
							!board.getBoardCellStatus(i, 0).equals(PieceEnum.EMPTY))){
				return board.getBoardCellStatus(i, 1);
			}
		}
		
		//Checking Columns
		for (int i = 0; i < 3; i++){
			if ( (board.getBoardCellStatus(0,i).equals(board.getBoardCellStatus(1,i))) && 
					(board.getBoardCellStatus(1,i).equals(board.getBoardCellStatus(2,i)) && 
							!board.getBoardCellStatus(0, i).equals(PieceEnum.EMPTY))){
				return board.getBoardCellStatus(1, i);
			}
		}
		
		//Checking first Diagonal: Going from top left to bottom right.
		if ((board.getBoardCellStatus(0, 0).equals(board.getBoardCellStatus(1, 1))) && 
				(board.getBoardCellStatus(1,1).equals(board.getBoardCellStatus(2, 2)) && 
						!board.getBoardCellStatus(0, 0).equals(PieceEnum.EMPTY))){
			return board.getBoardCellStatus(1, 1);
		}

		//Checking second Diagonal: Going from top right to bottom left.
		if ((board.getBoardCellStatus(2, 0).equals(board.getBoardCellStatus(1, 1))) && 
				(board.getBoardCellStatus(1,1).equals(board.getBoardCellStatus(0, 2)) && 
						!board.getBoardCellStatus(2, 0).equals(PieceEnum.EMPTY))){
			return board.getBoardCellStatus(1, 1);
		}
		return PieceEnum.EMPTY;
	}
	
//	public void changeTurn(){
//		if (currentPlayer == 'X'){
//			currentPlayer = 'O';
//		}
//		else{
//			currentPlayer = 'X';
//		}
//	}
	
	//Helper function to end the game if there is a tie.
	public boolean isBoardFull(){
		boolean status = true;
		for (int r = 0; r < 3; r++){
			for (int c = 0; c < 3; c++){
				if (board.getBoardCellStatus(c, r).equals(PieceEnum.EMPTY)){
					status = false;
				}
			}
		}
		return status;
	}

	@Override
	public void updateBoardState() {
		if (checkForWin() == PieceEnum.PLAYER1)
		{
			board.setCurrentWinState(BoardState.WIN);
		}
		else if (checkForWin() == PieceEnum.PLAYER2)
		{
			board.setCurrentWinState(BoardState.LOSE);
		}
		else if (isBoardFull())
		{
			board.setCurrentWinState(BoardState.TIE);
		}
	}

}


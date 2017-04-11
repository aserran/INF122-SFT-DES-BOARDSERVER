package org.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.shared.Board;
import org.shared.BoardState;
import org.shared.Cell;
import org.shared.Move;
import org.shared.PieceEnum;
import org.shared.PlayerEnum;

public class OthelloLogic extends Logic{
	int rows;
	int cols;
	Map<PlayerEnum, String> colors;
	int black_score;
	int white_score;
	
	String win_condition;
	
	private class Coordinate{
		int x_coord;
		int y_coord;
		
		public Coordinate(int x, int y){
			x_coord = x;
			y_coord = y;
		}
	}
	
	public OthelloLogic(){
		rows = 8;
		cols = 8;
		board = new Board(rows, cols);
		fillBoard();
		colors = new HashMap<PlayerEnum, String>();
		colors.put(PlayerEnum.PLAYER1, "Black");
		colors.put(PlayerEnum.PLAYER2, "White");
		black_score = 0;
		white_score = 0;
	}
	
	public void fillBoard()
	{
		for (int col = 0; col < cols; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				if((row == 3 && col == 3) || (row == 4 && col == 4))
				{
					(board.getBoard().get(col)).add(new Cell(PieceEnum.PLAYER1));		
				}		
				else if((row == 4 && col == 3) || (row == 3 && col == 4)){		
					(board.getBoard().get(col)).add(new Cell(PieceEnum.PLAYER2));		
				}		
				else{		
					(board.getBoard().get(col)).add(new Cell(PieceEnum.EMPTY));		
				}
			}
		}
	}
	
	public OthelloLogic(int r, int c, PlayerEnum player1){
		rows = r;
		cols = c;
		board = new Board(rows, cols);
		colors = new HashMap<PlayerEnum, String>();
		colors.put(player1, "Black");
		PlayerEnum player2 = flipPlayer(player1.toString().equals("PLAYER1"));
		colors.put(player2, "White");
		black_score = 0;
		white_score = 0;
	}

	@Override
	public boolean checkValidMove(Move m) {
		int row = m.getRow();
		int col = m.getCol();
		if (!board.getBoardCellStatus(col, row).equals(PieceEnum.EMPTY)){
			// Overlapping move, not valid
			return false;
		}
		else if (!checkSamePieces(row, col)){
			// Not valid move
			return false;
		}
		return true;
	}

	@Override
	public void makeMove(Move m) {
		int row = m.getRow();
		int col = m.getCol();
//		if (!board.getBoardCellStatus(col, row).equals(PieceEnum.EMPTY)){
//			// Overlapping move, not valid
//			return;
//		}
//		else if (!checkSamePieces(row, col)){
//			// Not valid move
//			return;
//		}
		flip_phase(row,col);
		board.setBoardCellStatus(col, row, pieceEnumConverter(board.isCurrentlyTurn()));
		updateBoardState();
	}

	@Override
	public void updateBoardState() {
		boolean black_moves = true;
		boolean white_moves = true;
		board.flipCurrentlyTurn();
		if (board.isCurrentlyTurn()){
			black_moves = playerHasMoves();
			white_moves = !black_moves;
		}
		else if (!board.isCurrentlyTurn()){
			white_moves = playerHasMoves();
			black_moves = !white_moves;
		}	
		if (!black_moves && !white_moves){
			// neither has moves
			if (getWinner() == PlayerEnum.PLAYER1)
			{
				board.setCurrentWinState(BoardState.WIN);
			}
			else if (getWinner() == PlayerEnum.PLAYER2)
			{
				board.setCurrentWinState(BoardState.LOSE);
			}
		}
	}
	
	public PlayerEnum getWinner(){
		if (black_score > white_score){
			if (win_condition.equals("most")){
				return PlayerEnum.PLAYER1;
			}
			else{
				return PlayerEnum.PLAYER2;
			}
		}
		else if (black_score < white_score){
			if (win_condition.equalsIgnoreCase("least")){
				return PlayerEnum.PLAYER2;
			}
			else{
				return PlayerEnum.PLAYER1;
			}
		}
		else{
			// TIE
			return null;
		}
	}
	
	private boolean playerHasMoves(){
		boolean result = false;
		for (int r = 0; r < rows; r++){
			for (int c = 0; c < cols; c++){
				if ((board.getBoardCellStatus(c, r) == PieceEnum.PLAYER1 && board.isCurrentlyTurn()) || 
						(board.getBoardCellStatus(c, r) == PieceEnum.PLAYER2 && !board.isCurrentlyTurn())){
					result = checkOpenPieces(r, c);
				}
			}
		}
		return result;
	}
	
	private boolean checkOpenPieces(int row, int col){
		ArrayList<Coordinate> line_list = get_valid_lines(row, col, PieceEnum.EMPTY);
		if (line_list.size() > 0){
			return true;
		}
		return false;
	}
	
	private boolean checkSamePieces(int row, int col){
		PieceEnum pe = pieceEnumConverter(board.isCurrentlyTurn());
		ArrayList<Coordinate> line_list = get_valid_lines(row, col, pe);
		if (line_list.size() > 0){
			return true;
		}
		return false;
	}
	
	private ArrayList<Coordinate> get_valid_lines(int row, int col, PieceEnum comp){
		ArrayList<Coordinate> return_list = new ArrayList<Coordinate>();
		
		Coordinate left_check = singleDirectionCheck(row, col, 0, -1, comp);
		Coordinate right_check = singleDirectionCheck(row, col, 0, 1, comp);
		Coordinate up_check = singleDirectionCheck(row, col, -1, 0, comp);
		Coordinate down_check = singleDirectionCheck(row, col, 1, 0, comp);
		Coordinate upright_check = singleDirectionCheck(row, col, -1, 1, comp);
		Coordinate downright_check = singleDirectionCheck(row, col, 1, 1, comp);
		Coordinate upleft_check = singleDirectionCheck(row, col, -1, -1, comp);
		Coordinate downleft_check = singleDirectionCheck(row, col, 1, -1, comp);
		ArrayList<Coordinate> temp_list = new ArrayList<>(Arrays.asList(left_check, 
				right_check, up_check, down_check, upright_check, downright_check,
				upleft_check,downleft_check));
		Coordinate temp_c = new Coordinate(-1, -1);
		for(Coordinate c: temp_list){
			if (c.x_coord!=temp_c.x_coord && c.y_coord!=temp_c.y_coord){
				return_list.add(c);
			}
		}
		return return_list;
	}
	
	private Coordinate singleDirectionCheck(int row, int col, int row_change, int col_change, PieceEnum comp){
		Coordinate valid_spot = new Coordinate(-1, -1);
		String opposite_color = flipPlayer(board.isCurrentlyTurn()).toString();
		boolean opposite_color_line = false;
		int col_index = col;
		int row_index = row;
		
		while (0 <= col_index && col_index < cols && 0 <= row_index && row_index < rows){
			if (board.getBoardCellStatus(col_index, row_index).toString().equals(opposite_color)){
				opposite_color_line = true;
			}
			else if (board.getBoardCellStatus(col_index, row_index).equals(comp) && opposite_color_line){
				valid_spot = new Coordinate(row_index, col_index);
				break;
			}
			else if (board.getBoardCellStatus(col, row).equals(board.getBoardCellStatus(col_index, row_index)) && 
					opposite_color_line && comp.equals(PieceEnum.EMPTY)){
				break;
			}
			else if ((Math.abs(row_index - row) == 1 || Math.abs(col_index - col) == 1) && !opposite_color_line){
				break;
			}
			else if (board.getBoardCellStatus(col_index, row_index).equals(PieceEnum.EMPTY) &&
					(row_index != row || col_index != col)){
				break;
			}
			row_index += row_change;
			col_index += col_change;
		}
		return valid_spot;
	}
	
	public void flip_phase(int row, int col){
		ArrayList<Coordinate> inline_pieces_list = get_valid_lines(row, col, pieceEnumConverter(board.isCurrentlyTurn()));
		for (Coordinate c : inline_pieces_list){
			flipLine(row, col, c.x_coord, c.y_coord );
		}
	}
	
	private void flipLine(int row1, int col1, int row2, int col2){
		int row_change = 0;
		int col_change = 0;
		int row_index = row1;
		int col_index = col1;
		
		if (row1 - row2 < 0){
			row_change = 1;
		}
		else if (row1 - row2 > 0){
			row_change = -1;
		}
		
		if (col1 - col2 < 0){
			col_change = 1;
		}
		else if (col1 - col2 > 0){
			col_change = -1;
		}
		
		while ((Math.min(row1, row2) <= row_index && row_index <= Math.max(row1, row2)) && 
				(Math.min(col1,  col2) <= col_index && col_index <= Math.max(col1, col2))){
			board.setBoardCellStatus(col_index, row_index, pieceEnumConverter(board.isCurrentlyTurn()));
			row_index += row_change;
			col_index += col_change;
		}
		
	}
	
	private PlayerEnum flipPlayer(Boolean player){
		if (player){
			return PlayerEnum.PLAYER2;
		}
		return PlayerEnum.PLAYER1;
	}
	
	private PieceEnum pieceEnumConverter(Boolean pe){
		if (pe){
			return PieceEnum.PLAYER1;
		}
		return PieceEnum.PLAYER2;
	}
	
	public void setWinCondition(String cond){
		win_condition = cond;
	}
}

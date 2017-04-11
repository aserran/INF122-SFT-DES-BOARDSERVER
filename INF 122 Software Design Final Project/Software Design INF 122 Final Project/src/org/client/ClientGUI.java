package org.client;

import org.shared.*;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import javax.swing.Box;

import java.io.IOException;
import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Choice;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientGUI {

	private JFrame frmInfFinalProject;
	private JTextField txtUsernamefield;
	private JTextField txtServeraddressfield;
	private BoardDisplay boardDisplay;
	
	private JLabel lblGameStatusDisplay; // for boardDisplay to reference
	private JPanel cardsPanel;
	private JMenuItem mntmStartNewGame;
	
	private String username, serverAddress, gameType;
	
	private SocketWrapper sw;

	/**
	 * Create the application.
	 */
	public ClientGUI(){//SocketWrapper sw) {
		//this.sw = sw;
		sw = new SocketWrapper();
		initialize();
	}
	
	public void makeVisible() {
		frmInfFinalProject.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInfFinalProject = new JFrame();
		frmInfFinalProject.setTitle("INF122 Final Project Client");
		frmInfFinalProject.setBounds(100, 100, 640, 470);
		frmInfFinalProject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInfFinalProject.getContentPane().setLayout(new BoxLayout(frmInfFinalProject.getContentPane(), BoxLayout.X_AXIS));
		frmInfFinalProject.setResizable(false);
		
		JPanel mainPanel = new JPanel();
		frmInfFinalProject.getContentPane().add(mainPanel);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		cardsPanel = new JPanel();
		mainPanel.add(cardsPanel, BorderLayout.CENTER);
		cardsPanel.setLayout(new CardLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		mainPanel.add(menuBar, BorderLayout.NORTH);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		Choice gameTypeChoice = new Choice();
		
		mntmStartNewGame = new JMenuItem("Start New Game");
		mntmStartNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				disconnect();
				try {
					sw.connect(serverAddress, SocketWrapper.PORT);
					gameTypeChoice.removeAll();
					getGameTypes(gameTypeChoice);		
					CardLayout cl = (CardLayout)cardsPanel.getLayout();
					cl.show(cardsPanel, "Find Game");
				} catch (IOException ex) {
					displayErrorWindow("Unable to reconnect to " + serverAddress);
					disconnect();
				}
			}
		});
		mnFile.add(mntmStartNewGame);
		
		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				disconnect();
				CardLayout cl = (CardLayout)cardsPanel.getLayout();
				cl.show(cardsPanel, "Login");
			}
		});
		mnFile.add(mntmDisconnect);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				shutdown();
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				displayAboutWindow();
			}
		});
		mnHelp.add(mntmAbout);
		
		Component glue = Box.createGlue();
		menuBar.add(glue);
		
		JPanel loginScreen = new JPanel();
		cardsPanel.add(loginScreen, "Login");
		GridBagLayout gbl_loginScreen = new GridBagLayout();
		gbl_loginScreen.columnWidths = new int[] {100, 100, 200, 100};
		gbl_loginScreen.rowHeights = new int[] {50, 50, 50, 50};
		gbl_loginScreen.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0};
		gbl_loginScreen.rowWeights = new double[]{0.0, 0.0, 0.0};
		loginScreen.setLayout(gbl_loginScreen);
		
		JLabel lblUsername = new JLabel("Username");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 0;
		loginScreen.add(lblUsername, gbc_lblUsername);
		
		txtUsernamefield = new JTextField();
		GridBagConstraints gbc_txtUsernamefield = new GridBagConstraints();
		gbc_txtUsernamefield.insets = new Insets(0, 0, 5, 0);
		gbc_txtUsernamefield.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUsernamefield.gridx = 2;
		gbc_txtUsernamefield.gridy = 0;
		loginScreen.add(txtUsernamefield, gbc_txtUsernamefield);
		txtUsernamefield.setColumns(10);
		
		JLabel lblServerAddressAnd = new JLabel("Server Address");
		GridBagConstraints gbc_lblServerAddressAnd = new GridBagConstraints();
		gbc_lblServerAddressAnd.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerAddressAnd.gridx = 1;
		gbc_lblServerAddressAnd.gridy = 1;
		loginScreen.add(lblServerAddressAnd, gbc_lblServerAddressAnd);
		
		txtServeraddressfield = new JTextField();
		GridBagConstraints gbc_txtServeraddressfield = new GridBagConstraints();
		gbc_txtServeraddressfield.insets = new Insets(0, 0, 5, 0);
		gbc_txtServeraddressfield.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtServeraddressfield.gridx = 2;
		gbc_txtServeraddressfield.gridy = 1;
		loginScreen.add(txtServeraddressfield, gbc_txtServeraddressfield);
		txtServeraddressfield.setColumns(10);
		
		JPanel findGameScreen = new JPanel();
		cardsPanel.add(findGameScreen, "Find Game");
		GridBagLayout gbl_findGameScreen = new GridBagLayout();
		gbl_findGameScreen.columnWidths = new int[] {90, 400, 90};
		gbl_findGameScreen.rowHeights = new int[] {50, 50, 50, 50, 100};
		gbl_findGameScreen.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_findGameScreen.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		findGameScreen.setLayout(gbl_findGameScreen);
		
		//Choice gameTypeChoice = new Choice();
		gameTypeChoice.setName("gameTypeChoice");
		GridBagConstraints gbc_gameTypeChoice = new GridBagConstraints();
		gbc_gameTypeChoice.fill = GridBagConstraints.HORIZONTAL;
		gbc_gameTypeChoice.anchor = GridBagConstraints.WEST;
		gbc_gameTypeChoice.insets = new Insets(0, 0, 0, 5);
		gbc_gameTypeChoice.gridx = 1;
		gbc_gameTypeChoice.gridy = 1;
		findGameScreen.add(gameTypeChoice, gbc_gameTypeChoice);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				username = txtUsernamefield.getText().trim();
				serverAddress = txtServeraddressfield.getText().trim();
				
				if (username.isEmpty() || serverAddress.isEmpty()) {
					displayErrorWindow("Invalid username or serverAddress.");
					return;
				}

				sw.setClientName(username);
				try {
					sw.connect(serverAddress, SocketWrapper.PORT);
					getGameTypes(gameTypeChoice);		
				} catch (IOException e) {
					displayErrorWindow("Unable to connect to " + serverAddress);
					return;
				}
				
				CardLayout cl = (CardLayout)cardsPanel.getLayout();
				cl.show(cardsPanel, "Find Game");
			}
		});
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.gridwidth = 2;
		gbc_btnConnect.insets = new Insets(0, 0, 0, 5);
		gbc_btnConnect.gridx = 1;
		gbc_btnConnect.gridy = 2;
		loginScreen.add(btnConnect, gbc_btnConnect);

		
		JLabel lblSelectGameType = new JLabel("Select Gametype:");
		GridBagConstraints gbc_lblSelectGameType = new GridBagConstraints();
		gbc_lblSelectGameType.anchor = GridBagConstraints.NORTH;
		gbc_lblSelectGameType.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSelectGameType.insets = new Insets(0, 0, 5, 0);
		gbc_lblSelectGameType.gridx = 1;
		gbc_lblSelectGameType.gridy = 0;
		findGameScreen.add(lblSelectGameType, gbc_lblSelectGameType);
		lblSelectGameType.setMaximumSize(new Dimension(178, 30));
		lblSelectGameType.setPreferredSize(new Dimension(178, 30));
		lblSelectGameType.setHorizontalAlignment(SwingConstants.CENTER);

		
		JButton btnFindGame = new JButton("Find Game");
		GridBagConstraints gbc_btnFindGame = new GridBagConstraints();
		gbc_btnFindGame.gridx = 1;
		gbc_btnFindGame.gridy = 2;
		findGameScreen.add(btnFindGame, gbc_btnFindGame);
		btnFindGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				gameType = gameTypeChoice.getSelectedItem();
				try {
					sw.sendMessage(username + ":" + gameType);
				} catch (IOException ex) {
					displayErrorWindow("Unable to send game information to server.");
					disconnect();
					return;
				}
				boardDisplay.setVisible(true);
				CardLayout cl = (CardLayout)cardsPanel.getLayout();
				cl.show(cardsPanel, "Display Game");
			}
		});
		
		JPanel boardDisplayScreen = new JPanel();
		cardsPanel.add(boardDisplayScreen, "Display Game");
		GridBagLayout gbl_boardDisplayScreen = new GridBagLayout();
		gbl_boardDisplayScreen.columnWidths = new int[] {400, 238};
		gbl_boardDisplayScreen.rowHeights = new int[] {15, 400};
		gbl_boardDisplayScreen.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_boardDisplayScreen.rowWeights = new double[]{0.0, 1.0};
		boardDisplayScreen.setLayout(gbl_boardDisplayScreen);
		
		lblGameStatusDisplay = new JLabel("Finding opponent...");
		GridBagConstraints gbc_lblGameDisplayGoes = new GridBagConstraints();
		gbc_lblGameDisplayGoes.anchor = GridBagConstraints.NORTH;
		gbc_lblGameDisplayGoes.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblGameDisplayGoes.insets = new Insets(0, 0, 5, 0);
		gbc_lblGameDisplayGoes.gridx = 0;
		gbc_lblGameDisplayGoes.gridy = 0;
		boardDisplayScreen.add(lblGameStatusDisplay, gbc_lblGameDisplayGoes);
		
		boardDisplay = new BoardDisplay();
		boardDisplay.setBackground(Color.WHITE);
		boardDisplay.setVisible(false);
		GridBagConstraints gbc_boardDisplay = new GridBagConstraints();
		gbc_boardDisplay.fill = GridBagConstraints.BOTH;
		gbc_boardDisplay.gridx = 0;
		gbc_boardDisplay.gridy = 1;

		boardDisplayScreen.add(boardDisplay, gbc_boardDisplay);		

	}

	private void getGameTypes(Choice gameTypeChoice) {
		ArrayList<String> gameList = new ArrayList<String>();
		try {
			String gameListFromServer = sw.getMessage();
			for (String game : gameListFromServer.split(":")) {
				gameList.add(game);
			}
		} catch (IOException e) {
			displayErrorWindow("Unable to get gametypes from server.");
			disconnect();
			return;
		}
		if (gameList.size() <= 0) {
			displayErrorWindow("Unable to get gametypes from server.");
			return;
		}
		
		for (String game : gameList) {
			gameTypeChoice.add(game);
		}
	}
	
	private void disconnect() {
		try {
			sw.close();
		} catch (IOException ex) {
			displayErrorWindow("Unable to close socket.");
			ex.printStackTrace();
		}
		boardDisplay.reset();
	}
	
	private void shutdown() {
		disconnect();
		System.exit(0);
	}
	
	private void displayAboutWindow() {
		AboutWindow about = new AboutWindow();
		about.setVisible(true);
	}
	
	private void displayErrorWindow(String message) {
		ErrorWindow error = new ErrorWindow(message);
		error.setVisible(true);
	}
	
	private class BoardDisplay extends JPanel {
		private static final long serialVersionUID = 7682621902588887503L;
		private static final int boardDisplaySize = 400;
		private static final int border = 10;
		private static final int boardLength = boardDisplaySize - 2*border;
		
		private int numCells;
		private int cellLength;
		
		private PieceEnum player;
		private Board currentBoard;
		
		private JLabel gameStatusDisplay; // reference to JLabel so BoardDisplay can manipulate its text
		private boolean currentGameIsOver;
		
		private Move firstMove = null;
		
		public BoardDisplay() {
			gameStatusDisplay = lblGameStatusDisplay;
			currentGameIsOver = false;
			initialize();
		}
		
		public void reset() {
			currentGameIsOver = false;
			currentBoard = null;
			setVisible(false);
			clearBoardDisplay(this.getGraphics());
		}
		
		private void initialize() {
			setPreferredSize(new Dimension(boardDisplaySize, boardDisplaySize));
			setSize(new Dimension(boardDisplaySize, boardDisplaySize));
			// attach mouse listener so it can see board info
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					// only allow Moves to be sent if it's your turn
					if (currentBoard == null || !currentBoard.isCurrentlyTurn() || currentGameIsOver) return;
					
					Move moveToSend = getMoveFromClick(e);
					if (moveToSend == null) return; // if the click was not within the board
					if (gameType.equals("Checkers")) {
						if (firstMove == null) {
							firstMove = moveToSend;
							return;
						} else {
							if (currentBoard.getBoardCellStatus(moveToSend.getCol(), moveToSend.getRow()) != PieceEnum.EMPTY) {
								firstMove = moveToSend;
								return;
							} else {
								Move m = new Move(firstMove, moveToSend);
								moveToSend = m;
								firstMove = null;
							}
						}
					}
					try {
						sw.sendMessageAsJson(moveToSend);
					} catch (IOException ex) {
						displayErrorWindow("Unable to send move to server.");
						ex.printStackTrace();
						disconnect();
						return;
					}
					
					// Fetch boards on a different thread so that the UI can stay responsive
					SwingWorker<Void, Void> mouseClickSwingWorker = new SwingWorker<Void, Void> () {
						@Override
						public Void doInBackground() {
							// Fetch the board with the above move applied
							updateState();
							
							/* If the move was valid, isCurrentlyTurn() returns false, meaning that it is the other player's turn
								and we need to wait for that other player to make a move and that server to send a state.
								The opponent may make any number of invalid moves, and so we need to keep updating the board until
								they make a valid move. When they do, isCurrentlyTurn() will become true, and this loop will exit.
								
								Note: this means the entire UI will be blocked until the opponent makes a valid move. We could put 
								this in a separate thread so that the rest of the UI can still be responsive but that's for once this works.
							*/
							while (!currentGameIsOver && !currentBoard.isCurrentlyTurn()) {
								updateState();
							}
							
							/* If the move was not valid, isCurrentlyTurn() returns true, and so we must exit and get another mouseEvent 
							 	and send that.
							 */
							return null;
						}
					};
					mouseClickSwingWorker.execute();
					
				}
			});
			
			// add callback for initial board fetching
			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentShown(ComponentEvent e) {
					// Fetch boards on a different thread so that the UI can stay responsive
					SwingWorker<Void, Void> componentShownSwingWorker = new SwingWorker<Void, Void> () {
						@Override
						public Void doInBackground() {
							setInitialState();
							
							/* if you are player 2, you need to wait for player 1 to make a move and then send a Board to you 
								before making your move. See above for why it's a while loop and not just an if statement
							*/
							while (!currentBoard.isCurrentlyTurn() && !currentGameIsOver) {
								updateState();
							}
							return null;
						}
					};
					componentShownSwingWorker.execute();
					
				}
			});
		}
		
		private void setInitialState() {
			// get initial board state and playerNumber
			try {
				fetchBoardState();
			} catch (IOException ex) {
				return;
			}

			numCells = currentBoard.getBoard().size();
			cellLength = boardLength / numCells;
			player = currentBoard.isCurrentlyTurn() ? PieceEnum.PLAYER1 : PieceEnum.PLAYER2;
			updateGameStatus();
			drawBoardDisplay();
		}
		
		private void updateState() {
			try {
				fetchBoardState();
			} catch (IOException ex) {
				return;
			}
			
			updateGameStatus();
			drawBoardDisplay();
			
			if (isGameOver()) {
				endGame();
				currentGameIsOver = true;
				return;
			}
		}
		
		private Move getMoveFromClick(MouseEvent e) {
			// if mouse click is outside the board
			if (e.getX() > border + boardLength || e.getX() < border
					|| e.getY() > border + boardLength || e.getY() < border) {
				return null;
			}
			
			// NOTE: these are not pixel-perfect accurate due to integer division
			int colIndex = (e.getX() - border) / cellLength;
			int rowIndex = (e.getY() - border) / cellLength;
			
			// check for corner case where bottom-right corner is exactly clicked
			// on a 10x10 board (2d array), this causes (rowIndex, colIndex) to be (10, 10) (OutOfBoundsException)
			if (colIndex == numCells || rowIndex == numCells) {
				return null;
			}
			
			return new Move(colIndex, rowIndex, player);
		}
		
		private void fetchBoardState() throws IOException {
			try {
				currentBoard = sw.getBoardState();
			} catch (IOException ex) {
				displayErrorWindow("Unable to get board from server.");
				ex.printStackTrace();
				disconnect();
				throw ex; // rethrow exception to let the caller exit; TODO: this seems like a kinda janky solution
			}
		}
		
		private void updateGameStatus() {
			// TODO: if we can fix the protocol, display opponent's name here
			String gameStatus = currentBoard.isCurrentlyTurn() ? "It's your turn!" : "It's the opponent's turn!";
			gameStatusDisplay.setText(gameStatus);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawBoardDisplay();
		}
		
		private void drawGrid(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawRect(border, border, boardLength, boardLength);
			for (int i = 0; i < numCells; ++i) {
				g.drawLine(i*cellLength + border, border, i*cellLength + border, boardLength + border);
			}
			for (int j = 0; j < numCells; ++j) {
				g.drawLine(border, j*cellLength + border, boardLength + border, j*cellLength + border);
			}
		}
		
		private void drawPieces(Graphics g, Board b) {
			for (int row = 0; row < b.getBoard().size(); ++row) {
				for (int col = 0; col < b.getBoard().get(row).size(); ++col) {
					Cell colCell = b.getBoard().get(col).get(row);
					if (colCell.getCellStatus() == PieceEnum.EMPTY) {
						continue;
					} 
					
					int xLocation = border + col * cellLength;
					int yLocation = border + row * cellLength;
					boolean isCurrentPlayer = colCell.getCellStatus() == player;
					g.setColor(Color.BLACK);
					
					switch (gameType) {
					case "TicTacToe":
						PieceDrawer.drawTicTacToePiece(g, isCurrentPlayer, xLocation, yLocation, cellLength);
						break;
					case "Othello":
						PieceDrawer.drawOthelloPiece(g, isCurrentPlayer, xLocation, yLocation, cellLength);
						break;
					case "Checkers":
						PieceDrawer.drawCheckersPiece(g, isCurrentPlayer, xLocation, yLocation, cellLength);
						break;
					}
				}
			}
		}
		
		private void clearBoardDisplay(Graphics g) {
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(Color.BLACK);
		}
		
		private void drawBoardDisplay() {
			Graphics g = this.getGraphics();
			clearBoardDisplay(g);
			drawGrid(g);
			if (currentBoard == null) return;

			drawPieces(g, currentBoard);
		}
		
		private boolean isGameOver() {
			return currentBoard.getCurrentWinState() != BoardState.NO_WIN_YET;
		}
		
		private void endGame() {
			displayGameOverWindow();
			gameStatusDisplay.setText("Go to File > Start New Game to play again");
		}
		
		private void displayGameOverWindow() {
			String message;
			switch (currentBoard.getCurrentWinState()) {
			case WIN:
				message = "You won!";
				break;
			case LOSE:
				message = "You lost!";
				break;
			case TIE:
				message = "It's a tie!";
				break;
			case NO_WIN_YET:
			default:
				displayErrorWindow("Invalid BoardState encountered.");
				return;
			}
			GameOverWindow gameOver = new GameOverWindow(message);
			gameOver.setVisible(true);
		}
	}
	
}

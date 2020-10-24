import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel implements java.io.Serializable
{
	private GameInterface gameInterface;
	private final int FRAME_HEIGHT = 480;
	private final int FRAME_WIDTH = 480;
	private final int GRID_ROWS = 8;
	private final int GRID_COLUMNS = 8;
	private Tile[][] board;
	private int[][] boardState; //displays occupation of pieces on board, using pieceIds
	
	private Piece previousPiece;
	private Piece selectedPiece;
	private boolean pieceIsSelected;
	private boolean tileIsSelected;
	private King whiteKing;
	private King blackKing;
	private FalseKing whiteFalseKing;
	private FalseKing blackFalseKing;
	
	private boolean firstPlayersTurn;
	private Player player1;
	private Player player2;
	private boolean timedMatch;
	private GameInformation infoPanel;
	private boolean computerOpponent = false;
	private ComputerOpponent computer;
	
	private boolean isDraftPhase = false;
	private Piece selectedDraftPiece;
	private boolean newDraftPieceSelected = false;
	private boolean whiteKingDrafted = false;
	private boolean blackKingDrafted = false;
	private int whiteDraftPoints = 39;
	private int blackDraftPoints = 39;
	private JLabel whitePointLabel;
	private JLabel blackPointLabel;
	
	//becomes most recently clicked tile or piece
	private int selectedDestinationRow;
	private int selectedDestinationCol;
	
	private int selectedTileRow;
	private int selectedTileCol;
	
	private boolean blackInCheck = false;
	private boolean whiteInCheck = false;
	private boolean gameOver = false;
	
	private HighlightingGrid highLightingGrid;
	private boolean highlighting;


	Board(Player player1, Player player2, GameInformation infoPanel, boolean timedMatch, boolean whiteMovesFirst, GameInterface gameInterface, boolean highlighting)
	{
		this.player1 = player1;
		this.player2 = player2;
		this.infoPanel = infoPanel;
		this.timedMatch = timedMatch;
		this.gameInterface = gameInterface;
		this.highlighting = highlighting;
		
		// highlighting stuff
		previousPiece = new Pawn("");
		previousPiece.setRow(0);
		previousPiece.setCol(0);
		
		tileIsSelected = false;
		
		if (player2.getName().equalsIgnoreCase("Computer")) 
		{
			computerOpponent = true;
			computer = new ComputerOpponent(this);
		}
		
		this.board = new Tile[8][8];
		pieceIsSelected = false;
		firstPlayersTurn = whiteMovesFirst; //indicating first player's turn
		
		boardState = new int[8][8];
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				boardState[i][j] = 0;
			}
		}
		
		//setPreferredSize(new Dimension(480, 480));
		setLayout(new GridLayout(GRID_ROWS, GRID_COLUMNS));
		
		if (highlighting)
		{
			highLightingGrid = new HighlightingGrid(board);
		}
	}
		
	void loadBoard()
	{
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				addTileListener(board[i][j]);
	}
	
	//Generates board and adds listener to each tile button
	void generateBoard()
	{
		int numTilesSetInRow = 0;
		boolean startTileWhite = true;
		
		for(int i = 0; i < 8; ++i)
		{
			for(int j = 0; j < 8; ++j)
			{
				Tile tile;
				
				if(startTileWhite == true)
				{
					if(j % 2 == 0)
					{
						tile = new Tile("whiteTile.png", i, j);
					}
					else
					{
						tile = new Tile("blackTile.png", i, j);
					}
				}
				else
				{
					if(j % 2 == 0)
					{		
						tile = new Tile("blackTile.png", i, j);
					}
					else
					{
						tile = new Tile("whiteTile.png", i, j);
					}
				}
				
				addTileListener(tile);
				
				this.board[i][j] = tile;
				add(this.board[i][j]);
				
				numTilesSetInRow++;
				
				if(numTilesSetInRow == 8)
				{
					startTileWhite = !startTileWhite;
					numTilesSetInRow = 0;
				}
			}
		}
		
		setVisible(true);
	}
		
	//adds piece to board at specified location (adds listener if piece doesn't currently have one)
	void addPiece(Piece piece, int row, int col) 
	{
		this.board[row][col].add(piece, new Integer(1), 0);
		boardState[row][col] = piece.getPieceID(); //tile is currently occupied
		
		piece.setRow(row);
		piece.setCol(col);
		
		if(piece.hasActionListener() == false)
		{
			addPieceListener(piece);
		}
	}
	
	void removePiece(int row, int col)
	{
		this.board[row][col].remove(0);
		boardState[row][col] = 0;
		revalidate();
		repaint();
	}
	
	void loadPieces()
	{
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
				if(boardState[i][j] != 0)
					addPieceListener((Piece)board[i][j].getComponent(0));
	}
	
	void addPieceListener(Piece piece)
	{
		piece.getButton().addActionListener(l -> {
			if(isDraftPhase == false)
			{
        if ((computerOpponent && !firstPlayersTurn) == false) // if it is not the computers turn
        {	
          selectedPiece = piece;

          selectedDestinationRow = piece.getRow();
          selectedDestinationCol = piece.getCol();	

          if(pieceIsSelected == true) //if attempting to move one piece on top of another and another piece is already selected
          {
            if (highlighting)
            {
              highLightingGrid.deactivateGrid();
              revalidate();
              repaint();
            }

            movePiece(selectedDestinationRow, selectedDestinationCol, false);
            pieceIsSelected = false;
          }
          else
          {	// if it's the correct players turn, allow them to select that piece
            if(selectedPiece.getPieceColor().compareTo("white") == 0 && firstPlayersTurn == true)
            {
              pieceIsSelected = true;	

              if (highlighting)
              {
                highLightingGrid.activateGrid(selectedPiece, boardState);
              }
            }
            else if(selectedPiece.getPieceColor().compareTo("black") == 0 && firstPlayersTurn == false)
            {
              pieceIsSelected = true;

              if (highlighting)
              {
                highLightingGrid.activateGrid(selectedPiece, boardState);
              }
            }
            else
            {
              System.out.println("It is not your turn");
            }
          }

          previousPiece = piece;
        }
			}
		});
			
		piece.setHasActionListener(true);
	}
	
	void addTileListener(Tile tile)
	{
		tile.getButton().addActionListener(l -> {
			if ((computerOpponent && !firstPlayersTurn) == false) // if it is not the computers turn
			{
				selectedDestinationRow = tile.getRow();
				selectedDestinationCol = tile.getCol();	
				
				if(pieceIsSelected == true)
				{
					if (highlighting)
					{
						highLightingGrid.deactivateGrid();
						revalidate();
						repaint();
					}
					movePiece(selectedDestinationRow, selectedDestinationCol, true);
				}
				pieceIsSelected = false;
				
			}
			selectedTileRow = tile.getRow();
			selectedTileCol = tile.getCol();
			
			tileIsSelected = true;
			
			
			//System.out.println("Tile (" + selectedTileRow + ", " + selectedTileCol + ")");
			
			if(isDraftPhase && newDraftPieceSelected)
			{
				//checking if valid number of points
				if(selectedDraftPiece.getPieceID() > 0)
				{
					if(selectedDraftPiece.getPieceCost() > whiteDraftPoints)
					{
						System.out.println("Insufficient funds...");
						return;
					}
				}
				else if(selectedDraftPiece.getPieceID() < 0)
				{
					if(selectedDraftPiece.getPieceCost() > blackDraftPoints)
					{
						System.out.println("Insufficient funds...");
						return;
					}
				}
				
					//if white and placed in row 6 or 7 OR if black and placed in row 0 or 1
				if((selectedDraftPiece.getPieceID() > 0 && (selectedTileRow == 6 || selectedTileRow == 7)) 
					|| selectedDraftPiece.getPieceID() < 0 && (selectedTileRow == 0 || selectedTileRow == 1))
				{
					//if king
					if(selectedDraftPiece instanceof King)
					{
						//if white
						if(selectedDraftPiece.getPieceID() == 6)
						{
							//if already on board
							if(whiteKingDrafted == true)
							{
								System.out.println("Only one White King may be drafted");
							}
							else
							{
								System.out.println("White King drafted");
								whiteKingDrafted = true;
								addPiece(selectedDraftPiece, selectedTileRow, selectedTileCol);
								newDraftPieceSelected = false;
								repaint();
							}
						}
						
						//if black
						if(selectedDraftPiece.getPieceID() == -6)
						{
							//if already on board
							if(blackKingDrafted == true)
							{
								System.out.println("Only one Black King may be drafted");
							}
							else
							{
								System.out.println("Black King drafted");
								blackKingDrafted = true;
								addPiece(selectedDraftPiece, selectedTileRow, selectedTileCol);
								newDraftPieceSelected = false;
								repaint();
							}
						}
					}
					else
					{
						addPiece(selectedDraftPiece, selectedTileRow, selectedTileCol);
						newDraftPieceSelected = false;
						repaint();
						
						if(selectedDraftPiece.getPieceID() > 0)
						{
							whiteDraftPoints -= selectedDraftPiece.getPieceCost();
						}
						else
						{
							blackDraftPoints -= selectedDraftPiece.getPieceCost();
						}
					}	
				}
				else
				{
					System.out.println("Invalid row for selected piece");
				}
				
				//updating label displaying currency
				if(selectedDraftPiece.getPieceID() > 0)
				{
					whitePointLabel.setText(String.valueOf(whiteDraftPoints));
					whitePointLabel.repaint();
				}
				else
				{
					blackPointLabel.setText(String.valueOf(blackDraftPoints));
					blackPointLabel.repaint();
				}
				
				System.out.println("Remaining points - White: " + whiteDraftPoints + " | Black: " + blackDraftPoints);
			}	
		});
	}
	
	void movePiece(int row, int col, boolean toTile)
	{
		if(toTile == true) //if moving piece to a tile
		{	
			//if the selected location is valid
			if(selectedPiece.validMove(row, col, boardState) == true) 
			{
				selectedPiece.incrementNumMoves();
				removePiece(selectedPiece.getRow(), selectedPiece.getCol());
				addPiece(selectedPiece, row, col);
				System.out.println("Number of moves for selected piece: " + selectedPiece.getNumMovesTaken());
				
				if(selectedPiece instanceof Pawn && ((computerOpponent && !firstPlayersTurn) == false))
				{
					if( ((Pawn)selectedPiece).pawnReachedPromotionRow() )
					{
						//display Promotion GUI
						PawnPromotion promotion = new PawnPromotion(selectedPiece.getPieceColor(), this, selectedPiece);
						System.out.println("Pawn made it to opposite side for promotion");
					}
				}
				else if(selectedPiece instanceof Squire) // squire promotion to knight
				{
					if (row == 0)
					{
						removePiece(selectedPiece.getRow(), selectedPiece.getCol());
						addPiece(new Knight("white"), row, col);
					}
					else if (row == 7)
					{
						removePiece(selectedPiece.getRow(), selectedPiece.getCol());
						addPiece(new Knight("black"), row, col);
					}
				}
				
				playersTurnEnd();
				firstPlayersTurn = !firstPlayersTurn;
			}
		}
		else // if moving piece on top of another piece
		{	
			// if pieces aren't the same color
			if(selectedPiece.getPieceColor().compareTo(previousPiece.getPieceColor()) != 0)
			{
				// archer has special capture rules
				if(previousPiece instanceof Archer)
				{
					if(((Archer)previousPiece).validCapture(row, col, boardState))
					{
						if (selectedPiece.getPieceID() == 10 || selectedPiece.getPieceID() == -10)
						{
							boolean kingDead = false;
							if (selectedPiece instanceof Incendiary)
							{
								Incendiary explodingPiece = (Incendiary) selectedPiece;
								kingDead = explodingPiece.explode(this, boardState, row, col);
								
								previousPiece.incrementNumMoves();
							}
							
							 
							if (kingDead)
							{
								if (firstPlayersTurn)
								{
									gameInterface.gameOver(true, player1.getName());
								}
								else
								{
									gameInterface.gameOver(true, player2.getName());
								}

								if (timedMatch)
								{
									infoPanel.gameOver();
								}
								
								return; // stop everything.
							}
						}
						else
						{
							previousPiece.incrementNumMoves();
							removePiece(row, col);
							
							// king was captured
							if (selectedPiece.getImageName().equalsIgnoreCase("gameImages/" + selectedPiece.getPieceColor() + "King.png"))
							{
								if (firstPlayersTurn)
								{
									gameInterface.gameOver(true, player1.getName());
								}
								else
								{
									gameInterface.gameOver(true, player2.getName());
								}

								if (timedMatch)
								{
									infoPanel.gameOver();
								}
								
								return; // stop everything.
							}
							
							playersTurnEnd();
							firstPlayersTurn = !firstPlayersTurn;
						}
					}
				}
				// if the selected location is valid
				else if(previousPiece.validMove(row, col, boardState) == true) 
				{
					// Devourer becomes its capture, special conditions.
					if(previousPiece instanceof Devourer)
					{
						Devourer selectedDevourer = (Devourer) previousPiece;
						boolean kingDead = selectedDevourer.devour(this, boardState, selectedPiece, previousPiece);
						
						if (kingDead)
						{
							if (firstPlayersTurn)
							{
								gameInterface.gameOver(true, player1.getName());
							}
							else
							{
								gameInterface.gameOver(true, player2.getName());
							}

							if (timedMatch)
							{
								infoPanel.gameOver();
							}
							
							return; // stop everything.
						}
						else
						{
							playersTurnEnd();
							firstPlayersTurn = !firstPlayersTurn;
						}
					}
					else if(previousPiece instanceof Incendiary || selectedPiece instanceof Incendiary) // Incendiary has special capture results
					{
						boolean kingDead = false;
						if (previousPiece instanceof Incendiary)
						{
							Incendiary explodingPiece = (Incendiary) previousPiece;
							kingDead = explodingPiece.explode(this, boardState, row, col);
						}
						else if (selectedPiece instanceof Incendiary)
						{
							Incendiary explodingPiece = (Incendiary) selectedPiece;
							kingDead = explodingPiece.explode(this, boardState, row, col);
							
							previousPiece.incrementNumMoves();
							removePiece(previousPiece.getRow(), previousPiece.getCol());
						}
						
						 
						if (kingDead)
						{
							if (firstPlayersTurn)
							{
								gameInterface.gameOver(true, player1.getName());
							}
							else
							{
								gameInterface.gameOver(true, player2.getName());
							}

							if (timedMatch)
							{
								infoPanel.gameOver();
							}
							
							return; // stop everything.
						}
						else
						{
							playersTurnEnd();
							firstPlayersTurn = !firstPlayersTurn;
						}
					}
					else // go about capturing normally
					{
						previousPiece.incrementNumMoves();
						removePiece(previousPiece.getRow(), previousPiece.getCol());
						removePiece(row, col);
						addPiece(previousPiece, row, col);
						System.out.println("Number of moves for selected piece: " + previousPiece.getNumMovesTaken());
						
						if(previousPiece instanceof Pawn  && ((computerOpponent && !firstPlayersTurn) == false))
						{
							if( ((Pawn)previousPiece).pawnReachedPromotionRow() )
							{
								//display Promotion GUI
								PawnPromotion promotion = new PawnPromotion(previousPiece.getPieceColor(), this, previousPiece);
								System.out.println("Pawn made it to opposite side for promotion");
							}
						}
						else if(previousPiece instanceof Squire) // squire promotion to knight
						{
							if (!(selectedPiece.getPieceID() == 10 || selectedPiece.getPieceID() == -10)) // if didn't take incendiary...
							{
								if (row == 0)
								{
									removePiece(previousPiece.getRow(), previousPiece.getCol());
									addPiece(new Knight("white"), row, col);
								}
								else if (row == 7)
								{
									removePiece(previousPiece.getRow(), previousPiece.getCol());
									addPiece(new Knight("black"), row, col);
								}
							}
								
						}
						
						// king was captured
						if (selectedPiece.getImageName().equalsIgnoreCase("gameImages/" + selectedPiece.getPieceColor() + "King.png"))
						{
							if (firstPlayersTurn)
							{
								gameInterface.gameOver(true, player1.getName());
							}
							else
							{
								gameInterface.gameOver(true, player2.getName());
							}

							if (timedMatch)
							{
								infoPanel.gameOver();
							}
							
							return; // stop everything.
						}
						
						playersTurnEnd();
						firstPlayersTurn = !firstPlayersTurn;
					}
					}
			}
			
			//they are the same color
			else
			{
				//case for castling
				if((previousPiece instanceof King))
				{
					if( ((King) previousPiece).validCastling(row, col, boardState, selectedPiece, previousPiece))
					{
						previousPiece.incrementNumMoves();
						removePiece(previousPiece.getRow(), previousPiece.getCol());
						removePiece(row, col);
						
						//determining new rook column
						int newRookCol = 0;
						if(col == 7)
							newRookCol = 5;
						else
							newRookCol = 2;
						
						addPiece(selectedPiece, previousPiece.getRow(), newRookCol);
						addPiece(previousPiece, row, col);
						System.out.println("Number of moves for selected piece: " + previousPiece.getNumMovesTaken());
						
						playersTurnEnd();
						firstPlayersTurn = !firstPlayersTurn;
	
					}
					
				}
				
			}
		}
		
		printBoardState();
		
		isGameOver();
		//isFalseKinginCheck();
		
		// Computer logic
		if ((computerOpponent && firstPlayersTurn == false) && !gameOver)
		{			
			
			try
			{
				selectedPiece = computer.selectPiece(boardState, blackInCheck);
				pieceIsSelected = true;
								
				if (computer.isCapturing())
				{
					System.out.println("here6");
					previousPiece = selectedPiece;
					
					selectedPiece = (Piece) board[computer.getCaptureRow()][computer.getCaptureCol()].getComponent(0);
				}
				
				System.out.println("here7");
				computer.move();
				pieceIsSelected = false;
			}
			catch (NullPointerException e)
			{
				System.out.println("No piece to move");
				gameOver = true;
			}
			catch (StackOverflowError e)
			{
				System.out.println("Failed to choose a position");
			}
			
		}
	}
	
	void printBoardState()
	{
		String printString = "";
		
		for(int i = 0; i < 8; i++)
		{
	
			for(int j = 0; j < 8; j++)
			{
				printString += boardState[i][j];
				
				if(boardState[i][j] >= 0)
				{
					printString += " ";
				}
				else
				{
					printString += "";
				}
				
			}
			
			printString += "\n";
		}
		
		System.out.println(printString);
		
		
	}
	
	Piece selectPieceFromBoard(int row, int col)
	{
		return (Piece) board[row][col].getComponent(0);
	}
	
	private void playersTurnEnd()
	{		
		if (firstPlayersTurn)
		{
			player1.moved(); // player 1 moved, so its player 2's turn
			player2.setTurn(true);
			if (timedMatch)
			{
				infoPanel.changeToPlayersTimer(player2);
			}
			else
			{
				infoPanel.setCurrentPlayer(player2);
				infoPanel.switchHighlightedPlayerName();
			}
		}
		else
		{
			player2.moved(); // player 2 moved, so its player 1's turn
			player1.setTurn(true);
			if (timedMatch)
			{
				infoPanel.changeToPlayersTimer(player1);
			}
			else
			{
				infoPanel.setCurrentPlayer(player1);
				infoPanel.switchHighlightedPlayerName();
			}
		}
	}
	
	private void isGameOver()
	{
		if (!gameOver)
		{
			updateKingAndFalseKing();
			
			this.whiteInCheck = whiteKing.isCheck(boardState, board);
			this.blackInCheck = blackKing.isCheck(boardState, board);
			
			isFalseKinginCheck();
			
			if(whiteInCheck)
			{
				if(blackInCheck)
				{
					gameInterface.playerInCheck(true, player1, player2);
				}
				else
				{
					gameInterface.playerInCheck(true, player1, null);
				}
			}
			else
			{
				if(blackInCheck)
				{
					gameInterface.playerInCheck(true, null, player2);
				}
				else
				{
					gameInterface.playerInCheck(false, null, null);
				}
			}
			
			if(whiteInCheck && whiteKing.isCheckmate(boardState, board))
			{
				gameInterface.gameOver(true, player2.getName());
				gameOver = true;
				return;
			}
			
			if(blackInCheck && blackKing.isCheckmate(boardState, board))
			{
				gameInterface.gameOver(true, player1.getName());
				gameOver = true;
				return;
			}
		}
	}
	
	private void isFalseKinginCheck() 
	{
		updateKingAndFalseKing();
		
		if(this.whiteFalseKing != null)
		{
			whiteFalseKing.inCheck(boardState,board);
		}
		
		if(blackFalseKing != null)
		{
			blackFalseKing.inCheck(boardState,board);
		}
	}
	
	private void updateKingAndFalseKing()
	{
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(this.boardState[i][j] == 6)
				{
					this.whiteKing = (King) this.board[i][j].getComponentAt(i,j);
				}
				
				if(this.boardState[i][j] == -6)
				{
					this.blackKing = (King) this.board[i][j].getComponentAt(i,j);
				}
				
				if(this.boardState[i][j] == 7)
				{
					this.whiteFalseKing = (FalseKing) this.board[i][j].getComponentAt(i,j);
				}
				
				if(this.boardState[i][j] == -7)
				{
					this.blackFalseKing = (FalseKing) this.board[i][j].getComponentAt(i,j);
				}
			}
		}
		
	}
  
	public int getSelectedTileRow()
	{
		return selectedDestinationRow;
	}
	
	public int getSelectedTileCol()
	{
		return selectedDestinationCol;
	}
	
	public boolean isTileSelected()
	{
		return tileIsSelected;
	}
	
	public void setTileSelected(boolean isTileSelected)
	{
		tileIsSelected = isTileSelected;
	}
	
	public void setDraftPhase(boolean isDraftPhase)
	{
		this.isDraftPhase = isDraftPhase;
		infoPanel.setDraftPhase(isDraftPhase);
	}
	
	public void setSelectedDraftPiece(Piece selectedDraftPiece)
	{
		this.selectedDraftPiece = selectedDraftPiece;
	}
	
	public void setNewDraftPieceSelected(boolean newDraftPieceSelected)
	{
		this.newDraftPieceSelected = newDraftPieceSelected;
	}
	
	public boolean bothKingsDrafted()
	{
		if((whiteKingDrafted && blackKingDrafted) || (whiteKingDrafted && computerOpponent))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public void setWhiteKingDrafted(boolean whiteKingDrafted)
	{
		this.whiteKingDrafted = whiteKingDrafted;
	}
	
	public void setBlackKingDrafted(boolean blackKingDrafted)
	{
		this.blackKingDrafted = blackKingDrafted;
	}
	
	public int getWhiteDraftPoints()
	{
		return whiteDraftPoints;
	}
	
	public int getBlackDraftPoints()
	{
		return blackDraftPoints;
	}
	
	public void setWhiteDraftPoints(int draftPoints)
	{
		this.whiteDraftPoints = draftPoints;
	}
	
	public void setBlackDraftPoints(int draftPoints)
	{
		this.blackDraftPoints = draftPoints;
	}

	public void setComputerDraftPoints(int draftPoints)
	{
		this.blackDraftPoints = draftPoints;

	}
	
	public void setWhitePointLabel(JLabel whitePointLabel)
	{
		this.whitePointLabel = whitePointLabel;
	}
	
	public void setBlackPointLabel(JLabel blackPointLabel)
	{
		this.blackPointLabel = blackPointLabel;
	}
	
	public int[][] getBoardState()
	{
		return boardState;
	}
}
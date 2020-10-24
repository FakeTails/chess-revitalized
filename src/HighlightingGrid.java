import javax.swing.JButton;

public class HighlightingGrid implements java.io.Serializable {

	private Tile[][] board;
	private int[] highlightedRows = new int[64];
	private int[] highlightedCols = new int[64];
	private int currentlyHightlightedlocations = 0;
	
	HighlightingGrid(Tile[][] board)
	{
		this.board = board;
	}
	
	void activateGrid(Piece piece, int[][] boardState)
	{		
		if (piece.getPieceID() == 1 || piece.getPieceID() == -1)
		{
			checkPawns(piece, boardState);
		}
		else if (piece.getPieceID() == 2 || piece.getPieceID() == -2)
		{
			checkKnights(piece, boardState);
		}
		else if (piece.getPieceID() == 3 || piece.getPieceID() == -3)
		{
			checkBishops(piece, boardState);
		}
		else if (piece.getPieceID() == 4 || piece.getPieceID() == -4)
		{
			checkRooks(piece, boardState);
		}
		else if (piece.getPieceID() == 5 || piece.getPieceID() == -5)
		{
			checkQueens(piece, boardState);
		}
		else if ((piece.getPieceID() == 6 || piece.getPieceID() == -6) || (piece.getPieceID() == 7 || piece.getPieceID() == -7))
		{
			checkKings(piece, boardState);
		}
		else if (piece.getPieceID() == 8 || piece.getPieceID() == -8)
		{
			checkArchers(piece, boardState);
		}
		else if (piece.getPieceID() == 9 || piece.getPieceID() == -9)
		{
			checkSquires(piece, boardState);
		}
		else if (piece.getPieceID() == 10 || piece.getPieceID() == -10)
		{
			checkIncendiaries(piece, boardState);
		}
		else if (piece.getPieceID() == 11 || piece.getPieceID() == -11)
		{
			checkDevourers(piece, boardState);
		}
	}
	
	private Tile makeHighlight(int row, int col) 
	{
		Tile highlight = new Tile("highLightTile.png", row, col);
		
		highlight.getButton().addActionListener(l ->{
			JButton topButton;
			Tile topTile;
			
			try // get tile button
			{
				topTile = (Tile) board[highlight.getRow()][highlight.getCol()].getComponent(board[highlight.getRow()][highlight.getCol()].getComponentCount()-1);
				topButton = topTile.getButton();
			}
			catch (ClassCastException e)
			{
				try // get Piece button 
				{
					topButton = (JButton) board[highlight.getRow()][highlight.getCol()].getComponent(board[highlight.getRow()][highlight.getCol()].getComponentCount()-1);
				}
				catch (ClassCastException f)
				{
					topButton = new JButton();
					System.out.println("ERROR - NO BUTTONS");
				}
			}
			
			topButton.doClick();
		});
		
		return highlight;
	}

	void deactivateGrid()
	{
		if (currentlyHightlightedlocations != 0)
		{			
			// remove highlights
			for (int i = 0; i < currentlyHightlightedlocations; i++)
			{
				Tile tiles = board[highlightedRows[i]][highlightedCols[i]];
				Tile tileToRemove = null;
				
				for (int j = 0; j < tiles.getComponentCount(); j++)
				{
					try
					{
						
						Tile currentTile = (Tile) tiles.getComponent(j);
						
						if (currentTile.getImageName().equalsIgnoreCase("highLightTile.png")) // if this is the tile...
						{
							tileToRemove = currentTile;
						}
					}
					catch (ClassCastException e)
					{
						// System.out.println("Not a tile, normal.");
					}
				}
				
				try
				{
					tiles.remove(tileToRemove);
				}
				catch (NullPointerException e)
				{
					// System.out.println("No tile to remove, error.");
				}
			}
			
			currentlyHightlightedlocations = 0;
		}
	}
	
	private void checkPawns(Piece piece, int[][] boardState) 
	{
		if (piece.getPieceID() == -1)
		{	
			if (piece.getRow()+1 != 8) // if pawn is not at the bottom of the board.
			{
				if (piece.getCol()+1 <= 7) // check top right. CAPTURE
				{
					if (boardState[piece.getRow()+1][piece.getCol()+1] > 0) 
					{
						Tile highlight = makeHighlight(piece.getRow()+1, piece.getCol()+1);
	
						board[piece.getRow()+1][piece.getCol()+1].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = piece.getRow()+1;
						highlightedCols[currentlyHightlightedlocations] = piece.getCol()+1;
						currentlyHightlightedlocations++;
					}
				}
				if (piece.getCol()-1 >= 0) // check top left. CAPTURE
				{
					if (boardState[piece.getRow()+1][piece.getCol()-1] > 0)
					{
						Tile highlight = makeHighlight(piece.getRow()+1, piece.getCol()-1);
						
						board[piece.getRow()+1][piece.getCol()-1].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = piece.getRow()+1;
						highlightedCols[currentlyHightlightedlocations] = piece.getCol()-1;
						currentlyHightlightedlocations++;
					}
				}
				
				
				 
				if (piece.getNumMovesTaken() == 0) // move two spaces forward if possible.
				{
					if (piece.getRow()+1 <= 7 && piece.getRow()+2 <= 7)
					{
						if (boardState[piece.getRow()+1][piece.getCol()] == 0 && boardState[piece.getRow()+2][piece.getCol()] == 0)
						{
							Tile highlight = makeHighlight(piece.getRow()+2, piece.getCol());
							
							board[piece.getRow()+2][piece.getCol()].add(highlight, 1, 1);
							
							highlightedRows[currentlyHightlightedlocations] = piece.getRow()+2;
							highlightedCols[currentlyHightlightedlocations] = piece.getCol();
							currentlyHightlightedlocations++;						
						}
					}
				}
				
				if (piece.getRow()+1 <= 7)
				{
					if (boardState[piece.getRow()+1][piece.getCol()] == 0) // move one space forward.
					{
						Tile highlight = makeHighlight(piece.getRow()+1, piece.getCol());
		
						board[piece.getRow()+1][piece.getCol()].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = piece.getRow()+1;
						highlightedCols[currentlyHightlightedlocations] = piece.getCol();
						currentlyHightlightedlocations++;
					}
				}
				
			}
		}
		else if (piece.getPieceID() == 1)
		{
			if (piece.getRow()-1 != -1) // if pawn is not at the bottom of the board.
			{
				if (piece.getCol()+1 <= 7) // check Bottom right. CAPTURE
				{
					if (boardState[piece.getRow()-1][piece.getCol()+1] < 0) 
					{
						Tile highlight = makeHighlight(piece.getRow()-1, piece.getCol()+1);
	
						board[piece.getRow()-1][piece.getCol()+1].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = piece.getRow()-1;
						highlightedCols[currentlyHightlightedlocations] = piece.getCol()+1;
						currentlyHightlightedlocations++;
					}
				}
				if (piece.getCol()-1 >= 0) // check Bottom left. CAPTURE
				{
					if (boardState[piece.getRow()-1][piece.getCol()-1] < 0)
					{
						Tile highlight = makeHighlight(piece.getRow()-1, piece.getCol()-1);
						
						board[piece.getRow()-1][piece.getCol()-1].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = piece.getRow()-1;
						highlightedCols[currentlyHightlightedlocations] = piece.getCol()-1;
						currentlyHightlightedlocations++;
					}
				}
				
				
				 
				if (piece.getNumMovesTaken() == 0) // move two spaces forward if possible.
				{
					if (piece.getRow()-1 >= 0 && piece.getRow()-2 >= 0)
					{
						if (boardState[piece.getRow()-1][piece.getCol()] == 0 && boardState[piece.getRow()-2][piece.getCol()] == 0)
						{
							Tile highlight = makeHighlight(piece.getRow()-2, piece.getCol());
							
							board[piece.getRow()-2][piece.getCol()].add(highlight, 1, 1);
							
							highlightedRows[currentlyHightlightedlocations] = piece.getRow()-2;
							highlightedCols[currentlyHightlightedlocations] = piece.getCol();
							currentlyHightlightedlocations++;						
						}
					}
				}
				
				if (piece.getRow()-1 >= 0)
				{
					if (boardState[piece.getRow()-1][piece.getCol()] == 0) // move one space forward.
					{
						Tile highlight = makeHighlight(piece.getRow()-1, piece.getCol());
		
						board[piece.getRow()-1][piece.getCol()].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = piece.getRow()-1;
						highlightedCols[currentlyHightlightedlocations] = piece.getCol();
						currentlyHightlightedlocations++;
					}
				}
			}
		}
	}

	private void checkKnights(Piece piece, int[][] boardState) 
	{
		if (piece.getPieceID() == -2)
		{
			if (((piece.getRow()-1 < 0 || piece.getCol()-2 < 0) 
					|| (piece.getRow()-1 > 7 || piece.getCol()-2 > 7)) != true) // if knight won't cross over the top left border.
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()-2;
				
				if (boardState[row][col] >= 0) // landing on white
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()-2 < 0 || piece.getCol()-1 < 0) 
					|| (piece.getRow()-2 > 7 || piece.getCol()-1 > 7)) != true)// if knight won't cross over the far top left border.
			{
				int row = piece.getRow()-2;
				int col = piece.getCol()-1;
				
				if (boardState[row][col] >= 0) // landing on white
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()-2 < 0 || piece.getCol()+1 < 0) 
					|| (piece.getRow()-2 > 7 || piece.getCol()+1 > 7)) != true) // if knight won't cross over the far bottom left border.
			{
				int row = piece.getRow()-2;
				int col = piece.getCol()+1;
				
				if (boardState[row][col] >= 0) // landing on white
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()-1 < 0 || piece.getCol()+2 < 0) 
					|| (piece.getRow()-1 > 7 || piece.getCol()+2 > 7)) != true) // if knight won't cross over the bottom left border.
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()+2;
				
				if (boardState[row][col] >= 0) // landing on white
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()+1 > 7 || piece.getCol()-2 > 7) 
					|| (piece.getRow()+1 < 0 || piece.getCol()-2 < 0)) != true) // if knight won't cross over the top right border.
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()-2;
				
				if (boardState[row][col] >= 0) // landing on white
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()+2 > 7 || piece.getCol()-1 > 7) 
					|| (piece.getRow()+2 < 0 || piece.getCol()-1 < 0)) != true)// if knight won't cross over the far top right border.
			{
				int row = piece.getRow()+2;
				int col = piece.getCol()-1;
				
				if (boardState[row][col] >= 0) // landing on white
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()+2 > 7 || piece.getCol()+1 > 7) 
					|| (piece.getRow()+2 < 0 || piece.getCol()+1 < 0)) != true) // if knight won't cross over the far bottom right border.
			{
				int row = piece.getRow()+2;
				int col = piece.getCol()+1;
				
				if (boardState[row][col] >= 0) // landing on white
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()+1 > 7 || piece.getCol()+2 > 7) 
					|| (piece.getRow()+1 < 0 || piece.getCol()+2 < 0)) != true) // if knight won't cross over the bottom right border.
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()+2;
				
				if (boardState[row][col] >= 0) // landing on white
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
		}
		else if (piece.getPieceID() == 2)
		{
			if (((piece.getRow()-1 < 0 || piece.getCol()-2 < 0) 
					|| (piece.getRow()-1 > 7 || piece.getCol()-2 > 7)) != true) // if knight won't cross over the top left border.
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()-2;
				
				if (boardState[row][col] <= 0) // landing on black
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()-2 < 0 || piece.getCol()-1 < 0) 
					|| (piece.getRow()-2 > 7 || piece.getCol()-1 > 7)) != true)// if knight won't cross over the far top left border.
			{
				int row = piece.getRow()-2;
				int col = piece.getCol()-1;
				
				if (boardState[row][col] <= 0) // landing on black
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()-2 < 0 || piece.getCol()+1 < 0) 
					|| (piece.getRow()-2 > 7 || piece.getCol()+1 > 7)) != true) // if knight won't cross over the far bottom left border.
			{
				int row = piece.getRow()-2;
				int col = piece.getCol()+1;
				
				if (boardState[row][col] <= 0) // landing on black
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()-1 < 0 || piece.getCol()+2 < 0) 
					|| (piece.getRow()-1 > 7 || piece.getCol()+2 > 7)) != true) // if knight won't cross over the bottom left border.
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()+2;
				
				if (boardState[row][col] <= 0) // landing on black
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()+1 > 7 || piece.getCol()-2 > 7) 
					|| (piece.getRow()+1 < 0 || piece.getCol()-2 < 0)) != true) // if knight won't cross over the top right border.
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()-2;
				
				if (boardState[row][col] <= 0) // landing on black
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()+2 > 7 || piece.getCol()-1 > 7) 
					|| (piece.getRow()+2 < 0 || piece.getCol()-1 < 0)) != true)// if knight won't cross over the far top right border.
			{
				int row = piece.getRow()+2;
				int col = piece.getCol()-1;
				
				if (boardState[row][col] <= 0) // landing on black
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()+2 > 7 || piece.getCol()+1 > 7) 
					|| (piece.getRow()+2 < 0 || piece.getCol()+1 < 0)) != true) // if knight won't cross over the far bottom right border.
			{
				int row = piece.getRow()+2;
				int col = piece.getCol()+1;
				
				if (boardState[row][col] <= 0) // landing on black
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (((piece.getRow()+1 > 7 || piece.getCol()+2 > 7) 
					|| (piece.getRow()+1 < 0 || piece.getCol()+2 < 0)) != true) // if knight won't cross over the bottom right border.
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()+2;
				
				if (boardState[row][col] <= 0) // landing on black
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
		}
	}

	private void checkBishops(Piece piece, int[][] boardState) 
	{
		boolean topLeftBlocked = false;
		boolean bottomLeftBlocked = false;
		boolean topRightBlocked = false;
		boolean bottomRightBlocked = false;
		
		for (int i = 1; i < 8; i++)
		{
			if (piece.getPieceID() == -3)
			{
				// if Bishop won't cross over the top left border.
				if (((piece.getRow()-i < 0 || piece.getCol()-i < 0) != true) && !topLeftBlocked) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol()-i;
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							topLeftBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						topLeftBlocked = true;
					}
				}
				
				// if Bishop won't cross over the bottom left border.
				if ((((piece.getRow()+i < 0 || piece.getCol()-i < 0) 
						|| (piece.getRow()+i > 7 || piece.getCol()-i > 7)) != true) && !bottomLeftBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol()-i;
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							bottomLeftBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						bottomLeftBlocked = true;
					}
				}
				
				// if Bishop won't cross over the top right border.
				if ((((piece.getRow()-i < 0 || piece.getCol()+i < 0) 
						|| (piece.getRow()-i > 7 || piece.getCol()+i > 7)) != true && !topRightBlocked)) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol()+i;
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							topRightBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						topRightBlocked = true;
					}
				}
				
				// if Bishop won't cross over the bottom right border.
				if (((piece.getRow()+i > 7 || piece.getCol()+i > 7) != true)  && !bottomRightBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol()+i;
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							bottomRightBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						bottomRightBlocked = true;
					}
				}
			}
			if (piece.getPieceID() == 3)
			{
				// if Bishop won't cross over the top left border.
				if (((piece.getRow()-i < 0 || piece.getCol()-i < 0) != true) && !topLeftBlocked) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol()-i;
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							topLeftBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						topLeftBlocked = true;
					}
				}
				
				// if Bishop won't cross over the bottom left border.
				if ((((piece.getRow()+i < 0 || piece.getCol()-i < 0) 
						|| (piece.getRow()+i > 7 || piece.getCol()-i > 7)) != true) && !bottomLeftBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol()-i;
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							bottomLeftBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						bottomLeftBlocked = true;
					}
				}
				
				// if Bishop won't cross over the top right border.
				if ((((piece.getRow()-i < 0 || piece.getCol()+i < 0) 
						|| (piece.getRow()-i > 7 || piece.getCol()+i > 7)) != true && !topRightBlocked)) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol()+i;
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							topRightBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						topRightBlocked = true;
					}
				}
				
				// if Bishop won't cross over the bottom right border.
				if (((piece.getRow()+i > 7 || piece.getCol()+i > 7) != true)  && !bottomRightBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol()+i;
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							bottomRightBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						bottomRightBlocked = true;
					}
				}
			}
			
			if (topLeftBlocked && topRightBlocked && bottomLeftBlocked && bottomRightBlocked)
			{
				break;
			}
		}
	}

	private void checkRooks(Piece piece, int[][] boardState) 
	{
		boolean leftBlocked = false;
		boolean rightBlocked = false;
		boolean topBlocked = false;
		boolean bottomBlocked = false;
		
		for (int i = 1; i < 8; i++)
		{
			if (piece.getPieceID() == -4)
			{
				// if Rook won't cross over the Left border.
				if ((piece.getCol()-i >= 0) && !leftBlocked) 
				{
					int row = piece.getRow();
					int col = piece.getCol()-i;
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							leftBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						leftBlocked = true;
					}
				}
				
				// if Rook won't cross over the Right border.
				if ((piece.getCol()+i <= 7) && !rightBlocked) 
				{
					int row = piece.getRow();
					int col = piece.getCol()+i;
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							rightBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						rightBlocked = true;
					}
				}
				
				// if Rook won't cross over the Top border.
				if ((piece.getRow()-i >= 0) && !topBlocked) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol();
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							topBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						topBlocked = true;
					}
				}
				
				// if Rook won't cross over the Bottom border.
				if ((piece.getRow()+i <= 7) && !bottomBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol();
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							bottomBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						bottomBlocked = true;
					}
				}
			}
			else if (piece.getPieceID() == 4)
			{
				// if Rook won't cross over the Left border.
				if ((piece.getCol()-i >= 0) && !leftBlocked) 
				{
					int row = piece.getRow();
					int col = piece.getCol()-i;
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							leftBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						leftBlocked = true;
					}
				}
				
				// if Rook won't cross over the Right border.
				if ((piece.getCol()+i <= 7) && !rightBlocked) 
				{
					int row = piece.getRow();
					int col = piece.getCol()+i;
									
					if (boardState[row][col] <= 0) // take black
					{
						if (boardState[row][col] < 0)
						{
							rightBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						rightBlocked = true;
					}
				}
				
				// if Rook won't cross over the Top border.
				if ((piece.getRow()-i >= 0) && !topBlocked) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol();
									
					if (boardState[row][col] <= 0) // take black
					{
						if (boardState[row][col] < 0)
						{
							topBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						topBlocked = true;
					}
				}
				
				// if Rook won't cross over the Bottom border.
				if ((piece.getRow()+i <= 7) && !bottomBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol();
									
					if (boardState[row][col] <= 0) // take black
					{
						if (boardState[row][col] < 0)
						{
							bottomBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						bottomBlocked = true;
					}
				}
			}
			
			if (leftBlocked && rightBlocked && topBlocked && bottomBlocked)
			{
				break;
			}
		}
	}

	private void checkQueens(Piece piece, int[][] boardState)
	{
		boolean topLeftBlocked = false;
		boolean bottomLeftBlocked = false;
		boolean topRightBlocked = false;
		boolean bottomRightBlocked = false;
		boolean leftBlocked = false;
		boolean rightBlocked = false;
		boolean topBlocked = false;
		boolean bottomBlocked = false;
		
		for (int i = 1; i < 8; i++)
		{
			if (piece.getPieceID() == -5)
			{
				if (((piece.getRow()-i < 0 || piece.getCol()-i < 0) != true) && !topLeftBlocked) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol()-i;
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							topLeftBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						topLeftBlocked = true;
					}
				}
				
				// if Queen won't cross over the bottom left border.
				if ((((piece.getRow()+i < 0 || piece.getCol()-i < 0) 
						|| (piece.getRow()+i > 7 || piece.getCol()-i > 7)) != true) && !bottomLeftBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol()-i;
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							bottomLeftBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						bottomLeftBlocked = true;
					}
				}
				
				// if Queen won't cross over the top right border.
				if ((((piece.getRow()-i < 0 || piece.getCol()+i < 0) 
						|| (piece.getRow()-i > 7 || piece.getCol()+i > 7)) != true && !topRightBlocked)) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol()+i;
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							topRightBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						topRightBlocked = true;
					}
				}
				
				// if Queen won't cross over the bottom right border.
				if (((piece.getRow()+i > 7 || piece.getCol()+i > 7) != true)  && !bottomRightBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol()+i;
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							bottomRightBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						bottomRightBlocked = true;
					}
				}
				
				// if Queen won't cross over the Left border.
				if ((piece.getCol()-i >= 0) && !leftBlocked) 
				{
					int row = piece.getRow();
					int col = piece.getCol()-i;
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							leftBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						leftBlocked = true;
					}
				}
				
				// if Queen won't cross over the Right border.
				if ((piece.getCol()+i <= 7) && !rightBlocked) 
				{
					int row = piece.getRow();
					int col = piece.getCol()+i;
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							rightBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						rightBlocked = true;
					}
				}
				
				// if Queen won't cross over the Top border.
				if ((piece.getRow()-i >= 0) && !topBlocked) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol();
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							topBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						topBlocked = true;
					}
				}
				
				// if Queen won't cross over the Bottom border.
				if ((piece.getRow()+i <= 7) && !bottomBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol();
									
					if (boardState[row][col] >= 0) // take white
					{
						if (boardState[row][col] > 0)
						{
							bottomBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						bottomBlocked = true;
					}
				}
			}
			else if (piece.getPieceID() == 5)
			{
				// if Queen won't cross over the top left border.
				if (((piece.getRow()-i < 0 || piece.getCol()-i < 0) != true) && !topLeftBlocked) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol()-i;
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							topLeftBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						topLeftBlocked = true;
					}
				}
				
				// if Queen won't cross over the bottom left border.
				if ((((piece.getRow()+i < 0 || piece.getCol()-i < 0) 
						|| (piece.getRow()+i > 7 || piece.getCol()-i > 7)) != true) && !bottomLeftBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol()-i;
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							bottomLeftBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						bottomLeftBlocked = true;
					}
				}
				
				// if Queen won't cross over the top right border.
				if ((((piece.getRow()-i < 0 || piece.getCol()+i < 0) 
						|| (piece.getRow()-i > 7 || piece.getCol()+i > 7)) != true && !topRightBlocked)) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol()+i;
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							topRightBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						topRightBlocked = true;
					}
				}
				
				// if Queen won't cross over the bottom right border.
				if (((piece.getRow()+i > 7 || piece.getCol()+i > 7) != true)  && !bottomRightBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol()+i;
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							bottomRightBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						bottomRightBlocked = true;
					}
				}
				
				// if Queen won't cross over the Left border.
				if ((piece.getCol()-i >= 0) && !leftBlocked) 
				{
					int row = piece.getRow();
					int col = piece.getCol()-i;
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							leftBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						leftBlocked = true;
					}
				}
				
				// if Queen won't cross over the Right border.
				if ((piece.getCol()+i <= 7) && !rightBlocked) 
				{
					int row = piece.getRow();
					int col = piece.getCol()+i;
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							rightBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						rightBlocked = true;
					}
				}
				
				// if Queen won't cross over the Top border.
				if ((piece.getRow()-i >= 0) && !topBlocked) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol();
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							topBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						topBlocked = true;
					}
				}
				
				// if Queen won't cross over the Bottom border.
				if ((piece.getRow()+i <= 7) && !bottomBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol();
									
					if (boardState[row][col] <= 0) // take white
					{
						if (boardState[row][col] < 0)
						{
							bottomBlocked = true;
						}
						
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					else
					{
						bottomBlocked = true;
					}
				}
			}
			
			if (topLeftBlocked && topRightBlocked && bottomLeftBlocked && bottomRightBlocked 
					&& leftBlocked && rightBlocked && topBlocked && bottomBlocked)
			{
				break;
			}
		}
	}

	private void checkKings(Piece piece, int[][] boardState)
	{
		if (piece.getPieceID() == -6 || piece.getPieceID() == -7)
		{
			// if King won't cross over the top left border.
			if (((piece.getRow()-1 < 0 || piece.getCol()-1 < 0) != true)) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()-1;
								
				if (boardState[row][col] >= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the bottom left border.
			if ((((piece.getRow()+1 < 0 || piece.getCol()-1 < 0) 
					|| (piece.getRow()+1 > 7 || piece.getCol()-1 > 7)) != true)) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()-1;
								
				if (boardState[row][col] >= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the top right border.
			if ((((piece.getRow()-1 < 0 || piece.getCol()+1 < 0) 
					|| (piece.getRow()-1 > 7 || piece.getCol()+1 > 7)) != true)) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()+1;
								
				if (boardState[row][col] >= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the bottom right border.
			if (((piece.getRow()+1 > 7 || piece.getCol()+1 > 7) != true)) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()+1;
								
				if (boardState[row][col] >= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the Left border.
			if ((piece.getCol()-1 >= 0)) 
			{
				int row = piece.getRow();
				int col = piece.getCol()-1;
								
				if (boardState[row][col] >= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the Right border.
			if ((piece.getCol()+1 <= 7))
			{
				int row = piece.getRow();
				int col = piece.getCol()+1;
								
				if (boardState[row][col] >= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the Top border.
			if ((piece.getRow()-1 >= 0)) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol();
								
				if (boardState[row][col] >= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the Bottom border.
			if ((piece.getRow()+1 <= 7)) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol();
								
				if (boardState[row][col] >= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
		}
		else if (piece.getPieceID() == 6 || piece.getPieceID() == 7)
		{
			if (((piece.getRow()-1 < 0 || piece.getCol()-1 < 0) != true)) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()-1;
								
				if (boardState[row][col] <= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the bottom left border.
			if ((((piece.getRow()+1 < 0 || piece.getCol()-1 < 0) 
					|| (piece.getRow()+1 > 7 || piece.getCol()-1 > 7)) != true)) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()-1;
								
				if (boardState[row][col] <= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the top right border.
			if ((((piece.getRow()-1 < 0 || piece.getCol()+1 < 0) 
					|| (piece.getRow()-1 > 7 || piece.getCol()+1 > 7)) != true)) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()+1;
								
				if (boardState[row][col] <= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the bottom right border.
			if (((piece.getRow()+1 > 7 || piece.getCol()+1 > 7) != true)) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()+1;
								
				if (boardState[row][col] <= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the Left border.
			if ((piece.getCol()-1 >= 0)) 
			{
				int row = piece.getRow();
				int col = piece.getCol()-1;
								
				if (boardState[row][col] <= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the Right border.
			if ((piece.getCol()+1 <= 7))
			{
				int row = piece.getRow();
				int col = piece.getCol()+1;
								
				if (boardState[row][col] <= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the Top border.
			if ((piece.getRow()-1 >= 0)) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol();
								
				if (boardState[row][col] <= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if King won't cross over the Bottom border.
			if ((piece.getRow()+1 <= 7)) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol();
								
				if (boardState[row][col] <= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
		}
	}

	private void checkArchers(Piece piece, int[][] boardState)
	{
		if ((piece.getPieceID() == 8 && piece.getRow()-1 != 5) || piece.getPieceID() == -8) // if white won't cross border, or if black...
		{
			// if Archer won't cross over the Top border.
			if ((piece.getRow()-1 >= 0)) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol();
								
				if (boardState[row][col] == 0)
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if Archer won't cross over the top left border.
			if (((piece.getRow()-1 < 0 || piece.getCol()-1 < 0) != true)) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()-1;

				if (boardState[row][col] == 0)
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if Archer won't cross over the top right border.
			if ((((piece.getRow()-1 < 0 || piece.getCol()+1 < 0) 
					|| (piece.getRow()-1 > 7 || piece.getCol()+1 > 7)) != true)) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()+1;
								
				if (boardState[row][col] == 0)
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
		}
		
		if ((piece.getPieceID() == -8 && piece.getRow()+1 != 2) || piece.getPieceID() == 8) // if black won't cross border, or if white...
		{
			// if Archer won't cross over the Bottom border.
			if ((piece.getRow()+1 <= 7)) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol();
								
				if (boardState[row][col] == 0)
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if Archer won't cross over the bottom left border.
			if ((((piece.getRow()+1 < 0 || piece.getCol()-1 < 0) 
					|| (piece.getRow()+1 > 7 || piece.getCol()-1 > 7)) != true)) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()-1;
								
				if (boardState[row][col] == 0)
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if Archer won't cross over the bottom right border.
			if (((piece.getRow()+1 > 7 || piece.getCol()+1 > 7) != true)) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()+1;
								
				if (boardState[row][col] == 0)
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
		}
		
		// if Archer won't cross over the Left border.
		if ((piece.getCol()-1 >= 0)) 
		{
			int row = piece.getRow();
			int col = piece.getCol()-1;
							
			if (boardState[row][col] == 0)
			{	
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}
		}
		
		// if Archer won't cross over the Right border.
		if ((piece.getCol()+1 <= 7))
		{
			int row = piece.getRow();
			int col = piece.getCol()+1;
							
			if (boardState[row][col] == 0)
			{	
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}
		}
		
		if (piece.getPieceID() == 8)
		{
			// if a capture is available two squares in front...
			if (boardState[piece.getRow()-2][piece.getCol()] < 0)
			{
				int row = piece.getRow()-2;
				int col = piece.getCol();
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}
			
			// if a capture is available three squares in front...
			if (boardState[piece.getRow()-3][piece.getCol()] < 0)
			{
				int row = piece.getRow()-3;
				int col = piece.getCol();
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}
			
			// if a capture is available two squares in front and one to the right...
			if (boardState[piece.getRow()-2][piece.getCol()+1] < 0)
			{
				int row = piece.getRow()-2;
				int col = piece.getCol()+1;
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}
						
			// if a capture is available one square in front and one to the right...
			if (boardState[piece.getRow()-1][piece.getCol()+1] < 0)
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()+1;
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}		
			
			// if a capture is available one square in front and two to the right...
			if (boardState[piece.getRow()-1][piece.getCol()+1] < 0)
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()+2;
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}		
			
			// if a capture is available two squares in front and one to the right...
			if (boardState[piece.getRow()-2][piece.getCol()-1] < 0)
			{
				int row = piece.getRow()-2;
				int col = piece.getCol()-1;
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}
						
			// if a capture is available one square in front and one to the right...
			if (boardState[piece.getRow()-1][piece.getCol()-1] < 0)
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()-1;
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}		
			
			// if a capture is available one square in front and two to the right...
			if (boardState[piece.getRow()-1][piece.getCol()+1] < 0)
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()-2;
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}	
		}
		else if (piece.getPieceID() == -8)
		{
			// if a capture is available two squares in front...
			if (boardState[piece.getRow()+2][piece.getCol()] > 0)
			{
				int row = piece.getRow()+2;
				int col = piece.getCol();
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}
			
			// if a capture is available three squares in front...
			if (boardState[piece.getRow()+3][piece.getCol()] > 0)
			{
				int row = piece.getRow()+3;
				int col = piece.getCol();
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}
			
			// if a capture is available two squares in front and one to the right...
			if (boardState[piece.getRow()+2][piece.getCol()+1] > 0)
			{
				int row = piece.getRow()+2;
				int col = piece.getCol()+1;
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}
						
			// if a capture is available one square in front and one to the right...
			if (boardState[piece.getRow()+1][piece.getCol()+1] > 0)
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()+1;
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}		
			
			// if a capture is available one square in front and two to the right...
			if (boardState[piece.getRow()+1][piece.getCol()+2] > 0)
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()+2;
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}		
			
			// if a capture is available two squares in front and one to the right...
			if (boardState[piece.getRow()+2][piece.getCol()-1] > 0)
			{
				int row = piece.getRow()+2;
				int col = piece.getCol()-1;
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}
						
			// if a capture is available one square in front and one to the right...
			if (boardState[piece.getRow()+1][piece.getCol()-1] > 0)
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()-1;
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}		
			
			// if a capture is available one square in front and two to the right...
			if (boardState[piece.getRow()+1][piece.getCol()-2] > 0)
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()-2;
				
				Tile highlight = makeHighlight(row, col);
				
				board[row][col].add(highlight, 1, 1);
				
				highlightedRows[currentlyHightlightedlocations] = row;
				highlightedCols[currentlyHightlightedlocations] = col;
				currentlyHightlightedlocations++;
			}	
		}
	}

	private void checkSquires(Piece piece, int[][] boardState) 
	{
		if (piece.getPieceID() == -9)
		{
			// if Squire won't cross over the bottom left border.
			if ((piece.getRow()+1 > 7 || piece.getCol()-1 < 0) != true) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()-1;
								
				if (boardState[row][col] == 0)
				{
					
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if Squire won't cross over the bottom right border.
			if ((piece.getRow()+1 > 7 || piece.getCol()+1 > 7) != true) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol()+1;
								
				if (boardState[row][col] == 0)
				{
					
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (piece.getRow()+1 <= 7)
			{
				int row = piece.getRow()+1;
				int col = piece.getCol();
								
				if (boardState[row][col] > 0) // take white
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
		}
		else if (piece.getPieceID() == 9)
		{
			// if Squire won't cross over the top left border.
			if ((piece.getRow()-1 < 0 || piece.getCol()-1 < 0) != true) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()-1;
								
				if (boardState[row][col] == 0)
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if Bishop won't cross over the top right border.
			if ((piece.getRow()-1 < 0 || piece.getCol()+1 > 7) != true) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol()+1;
								
				if (boardState[row][col] == 0)
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			if (piece.getRow()-1 >= 0)
			{
				int row = piece.getRow()-1;
				int col = piece.getCol();
								
				if (boardState[row][col] < 0) // take black
				{
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
		}
	}

	private void checkIncendiaries(Piece piece, int[][] boardState)
	{
		if (piece.getPieceID() == -10)
		{
			// if Incendiary won't cross over the Left border.
			if (piece.getCol()-1 >= 0)
			{
				int row = piece.getRow();
				int col = piece.getCol()-1;
								
				if (boardState[row][col] >= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if Incendiary won't cross over the Right border.
			if (piece.getCol()+1 <= 7)
			{
				int row = piece.getRow();
				int col = piece.getCol()+1;
								
				if (boardState[row][col] >= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if Incendiary won't cross over the Top border.
			if ((piece.getRow()-1 >= 0)) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol();
								
				if (boardState[row][col] >= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if Incendiary won't cross over the Bottom border.
			if (piece.getRow()+1 <= 7) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol();
								
				if (boardState[row][col] >= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
		}
		else if (piece.getPieceID() == 10)
		{
			// if Devourer won't cross over the Left border.
			if (piece.getCol()-1 >= 0) 
			{
				int row = piece.getRow();
				int col = piece.getCol()-1;
								
				if (boardState[row][col] <= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if Devourer won't cross over the Right border.
			if (piece.getCol()+1 <= 7)
			{
				int row = piece.getRow();
				int col = piece.getCol()+1;
								
				if (boardState[row][col] <= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if Devourer won't cross over the Top border.
			if (piece.getRow()-1 >= 0) 
			{
				int row = piece.getRow()-1;
				int col = piece.getCol();
								
				if (boardState[row][col] <= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
			
			// if Devourer won't cross over the Bottom border.
			if (piece.getRow()+1 <= 7) 
			{
				int row = piece.getRow()+1;
				int col = piece.getCol();
								
				if (boardState[row][col] <= 0) // take white
				{	
					Tile highlight = makeHighlight(row, col);
					
					board[row][col].add(highlight, 1, 1);
					
					highlightedRows[currentlyHightlightedlocations] = row;
					highlightedCols[currentlyHightlightedlocations] = col;
					currentlyHightlightedlocations++;
				}
			}
		}
	}
	
	private void checkDevourers(Piece piece, int[][] boardState)
	{
		boolean leftBlocked = false;
		boolean rightBlocked = false;
		boolean topBlocked = false;
		boolean bottomBlocked = false;
		
		if (piece.getPieceID() == -11)
		{
			for (int i = 1; i <= 2; i++)
			{
				// if Devourer won't cross over the Left border.
				if (piece.getCol()-i >= 0 && !leftBlocked)
				{
					int row = piece.getRow();
					int col = piece.getCol()-i;
									
					if (boardState[row][col] >= 0) // take white
					{	
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					
					if (boardState[row][col] != 0)
					{
						leftBlocked = true;
					}
				}
				
				// if Devourer won't cross over the Right border.
				if (piece.getCol()+i <= 7 && !rightBlocked)
				{
					int row = piece.getRow();
					int col = piece.getCol()+i;
									
					if (boardState[row][col] >= 0) // take white
					{	
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					
					if (boardState[row][col] != 0)
					{
						rightBlocked = true;
					}
				}
				
				// if Devourer won't cross over the Top border.
				if (piece.getRow()-i >= 0  && !topBlocked) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol();
									
					if (boardState[row][col] >= 0) // take white
					{	
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}

					if (boardState[row][col] != 0)
					{
						topBlocked = true;
					}
				}
				
				// if Devourer won't cross over the Bottom border.
				if (piece.getRow()+i <= 7 && !bottomBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol();
									
					if (boardState[row][col] >= 0) // take white
					{	
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					
					if (boardState[row][col] != 0)
					{
						bottomBlocked = true;
					}
				}
			}
		}
		else if (piece.getPieceID() == 11)
		{
			for (int i = 1; i <= 2; i++)
			{
				// if Devourer won't cross over the Left border.
				if (piece.getCol()-i >= 0 && !leftBlocked) 
				{
					int row = piece.getRow();
					int col = piece.getCol()-i;
									
					if (boardState[row][col] <= 0) // take white
					{	
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					
					if (boardState[row][col] != 0)
					{
						leftBlocked = true;
					}
				}
				
				// if Devourer won't cross over the Right border.
				if (piece.getCol()+i <= 7 && !rightBlocked)
				{
					int row = piece.getRow();
					int col = piece.getCol()+i;
									
					if (boardState[row][col] <= 0) // take white
					{	
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					
					if (boardState[row][col] != 0)
					{
						rightBlocked = true;
					}
				}
				
				// if Devourer won't cross over the Top border.
				if (piece.getRow()-i >= 0 && !topBlocked) 
				{
					int row = piece.getRow()-i;
					int col = piece.getCol();
									
					if (boardState[row][col] <= 0) // take white
					{	
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					
					if (boardState[row][col] != 0)
					{
						topBlocked = true;
					}
				}
				
				// if Devourer won't cross over the Bottom border.
				if (piece.getRow()+i <= 7 && !bottomBlocked) 
				{
					int row = piece.getRow()+i;
					int col = piece.getCol();
									
					if (boardState[row][col] <= 0) // take white
					{	
						Tile highlight = makeHighlight(row, col);
						
						board[row][col].add(highlight, 1, 1);
						
						highlightedRows[currentlyHightlightedlocations] = row;
						highlightedCols[currentlyHightlightedlocations] = col;
						currentlyHightlightedlocations++;
					}
					
					if (boardState[row][col] != 0)
					{
						bottomBlocked = true;
					}
				}
			}
		}
	}
}
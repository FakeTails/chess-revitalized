
public class Queen extends Piece
{
	Queen(String pieceColor)
	{
		super(pieceColor + "Queen.png");
		super.setPieceCost(9);
		
		if(pieceColor.compareTo("white") == 0)
		{
			super.setPieceID(5);
		}
		else 
		{
			super.setPieceID(-5);
		}
		
		super.setPieceCost(9);
	}
	
	public boolean validMove(int row, int col, int[][] board)
	{
		int rowTravelDistance = Math.abs(row - super.getRow());
		int colTravelDistance = Math.abs(col - super.getCol());
		
		//DIAGONAL MOVEMENT:
		if(rowTravelDistance == colTravelDistance)
		{
			if(super.getRow() > row && super.getCol() < col)
			{
				for(int i = super.getRow() - 1; i > row; i--)
				{
					for(int j = super.getCol() + 1; j < col; j++)
					{
						int diagRow = Math.abs(super.getRow() - i);
						int diagCol = Math.abs(super.getCol() - j);
						
						if(board[i][j] != 0 && diagRow == diagCol)
						{
							return false;
						}
					}
				}
			}
			//diagonally top left
			else if(super.getRow() > row && super.getCol() > col)
			{
				for(int i = super.getRow() - 1; i > row; i--)
				{
					for(int j = super.getCol() - 1; j > col; j--)
					{
						int diagRow = Math.abs(super.getRow() - i);
						int diagCol = Math.abs(super.getCol() - j);
						
						if(board[i][j] != 0 && diagRow == diagCol)
						{
							return false;
						}
					}
				}
			}
			//diagonally bottom right
			else if(super.getRow() < row && super.getCol() < col)
			{
				for(int i = super.getRow() + 1; i < row; i++)
				{
					for(int j = super.getCol() + 1; j < col; j++)
					{
						int diagRow = Math.abs(super.getRow() - i);
						int diagCol = Math.abs(super.getCol() - j);
						
						if(board[i][j] != 0 && diagRow == diagCol)
						{
							return false;
						}
					}
				}
			}
			//diagonally bottom left
			else if(super.getRow() < row && super.getCol() > col)
			{
				for(int i = super.getRow() + 1; i < row; i++)
				{
					for(int j = super.getCol(); j > col; j--)
					{
						int diagRow = Math.abs(super.getRow() - i);
						int diagCol = Math.abs(super.getCol() - j);
						
						if(board[i][j] != 0 && diagRow == diagCol)
						{
							return false;
						}
					}
				}
			}
		}
		//VERTICAL/HORIZONTAL MOVEMENT: 
		else
		{
			if(row == super.getRow() || col == super.getCol())
			{		
				//moving down
				if(super.getRow() < row)
				{
					for(int i = super.getRow() + 1; i < row; i++)
					{
						if(board[i][super.getCol()] != 0)
						{
							return false;
						}
					}
				}
				//moving up
				if(super.getRow() > row)
				{
					for(int i = super.getRow() - 1; i > row; i--)
					{
						if(board[i][super.getCol()] != 0)
						{
							return false;
						}
					}
				}
				//moving right
				if(super.getCol() < col)
				{
					for(int i = super.getCol() + 1; i < col; i++)
					{
						if(board[super.getRow()][i] != 0)
						{
							return false;
						}
					}
				}
				//moving left
				if(super.getCol() > col)
				{
					for(int i = super.getCol() - 1; i > col; i--)
					{
						if(board[super.getRow()][i] != 0)
						{
							return false;
						}
					}
				}
			}
		}
		
		//if attempting to not move it a straight line
		if(rowTravelDistance != colTravelDistance && super.getRow() != row && super.getCol() != col)
		{
			return false;
		}

		return true;
	}
}

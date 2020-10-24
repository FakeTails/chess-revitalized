
public class Rook extends Piece
{
	
	Rook(String pieceColor)
	{
		super(pieceColor + "Rook.png");
		super.setPieceCost(5);
		
		if(pieceColor.compareTo("white") == 0)
		{
			super.setPieceID(4);
		}
		else 
		{
			super.setPieceID(-4);
		}
		
		super.setPieceCost(5);
	}
	
	public boolean validMove(int row, int col, int[][] board) 
	{		
		//collision detection
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
			
			return true;
		}

		return false;
	}
}

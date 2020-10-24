import java.lang.Math;


public class Bishop extends Piece
{
	Bishop(String pieceColor)
	{
		super(pieceColor + "Bishop.png");
		super.setPieceCost(3);
		
		if(pieceColor.compareTo("white") == 0)
		{
			super.setPieceID(3);
		}
		else 
		{
			super.setPieceID(-3);
		}
		
		super.setPieceCost(3);
	}
	
	public boolean validMove(int row, int col, int[][] board)
	{		
		int rowTravelDistance = Math.abs(row - super.getRow());
		int colTravelDistance = Math.abs(col - super.getCol());
		
		
		//if the player isn't traveling diagonally in a straight line
		if(rowTravelDistance != colTravelDistance)
		{
			return false;
		}
		else
		{	//diagonally top right
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
		
		return true;
	}
}

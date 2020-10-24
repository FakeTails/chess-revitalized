
public class Knight extends Piece
{
	Knight(String pieceColor)
	{
		super(pieceColor + "Knight.png");
		super.setPieceCost(3);
		
		if(pieceColor.compareTo("white") == 0)
		{
			super.setPieceID(2);
		}
		else 
		{
			super.setPieceID(-2);
		}
		
		super.setPieceCost(3);
	}

	public boolean validMove(int row, int col, int[][] board)
	{
		if(col < super.getCol()) //moving left
		{
			if(col == super.getCol() -1)
			{
				if(row == super.getRow() - 2 || row == super.getRow() + 2)
				{
					return true;
				}
			}
			
			if(col == super.getCol() -2)
			{
				if(row == super.getRow() - 1 || row == super.getRow() + 1)
				{
						return true;
				} 
			}
		} 
		else //moving right
		{
			if(col == super.getCol() +1)
			{
				if(row == super.getRow() - 2 || row == super.getRow() + 2)
				{
					return true;
				}
			}
				
			if(col == super.getCol() +2)
			{
				if(row == super.getRow() - 1 || row == super.getRow() + 1)
				{
					return true;
				} 
			}
		}
		
		return false;
	}
}

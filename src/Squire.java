
public class Squire extends Piece {

	Squire(String pieceColor)
	{
		super(pieceColor + "Squire.png");
		super.setPieceCost(2);
		
		if(pieceColor.compareTo("white") == 0)
		{
			super.setPieceID(9);
		}
		else 
		{
			super.setPieceID(-9);
		}
		
		super.setPieceCost(2);
	}
	
	@Override
	public boolean validMove(int row, int col, int[][] board)
	{
		int rowTravel = Math.abs(row - super.getRow());
		int colTravel = Math.abs(col - super.getCol());
		
		//case for white squire
		if(super.getPieceID() > 0)
		{
			//moving in correct direction
			if(row < super.getRow())
			{
				//moving to unoccupied space
				if(rowTravel == 1 && colTravel == 1 && board[row][col] == 0)
				{
					return true;
				}
				//capturing
				else if(rowTravel == 1 && colTravel == 0 && board[row][col] != 0)
				{
					return true;
				}
				else
					return false;
			}
			//not moving in correct direction
			else
				return false;
		}
		
		//case for black squire
		else
		{
			//moving in correct direction
			if(row > super.getRow())
			{
				//moving to unoccupied space
				if(rowTravel == 1 && colTravel == 1 && board[row][col] == 0)
				{
					return true;
				}
				//capturing
				else if(rowTravel == 1 && colTravel == 0 && board[row][col] != 0)
				{
					return true;
				}
				else
					return false;
			}
			//not moving in correct direction
			else
				return false;
			
		}
	}

}

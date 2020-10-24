
public class Archer extends Piece {

	Archer(String pieceColor)
	{
		super(pieceColor + "Archer.png");
		super.setPieceCost(3);
		
		if(pieceColor.compareTo("white") == 0)
		{
			super.setPieceID(8);
		}
		else 
		{
			super.setPieceID(-8);
		}
		
		super.setPieceCost(4);
	}
	
	@Override
	public boolean validMove(int row, int col, int[][] board) 
	{
		int rowTravel = Math.abs(row - super.getRow());
		int colTravel = Math.abs(col - super.getCol());
		
		//case for white
		if(super.getPieceID() > 0)
		{
			if(rowTravel <= 1 && colTravel <= 1 && (row == 7 || row == 6))
				return true;
			else
				return false;
		}
		//case for black
		else
		{
			if(rowTravel <= 1 && colTravel <= 1 && (row == 0 || row == 1))
				return true;
			else
				return false;
		}
	}
	
	public boolean validCapture(int row, int col, int[][] board)
	{
		int rowTravel = Math.abs(row - super.getRow());
		int colTravel = Math.abs(col - super.getCol());
		int totalTravel = rowTravel + colTravel;
		//for a valid capture the distance needs to be 2 or 3
		if((totalTravel == 2 || totalTravel == 3) && board[row][col] != 0)
		{
			return true;
		}
		else
			return false;
	}

}

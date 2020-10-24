
public class Pawn extends Piece
{
	boolean movesDown;
	
	Pawn(String pieceColor)
	{
		super(pieceColor + "Pawn.png");
		super.setPieceCost(1);
		
		if(pieceColor == "black") //piece starting at top of board
		{
			movesDown = true;
		}
		else if(pieceColor == "white") //piece starting at bottom of board
		{
			movesDown = false;
		}
		
		if(pieceColor.compareTo("white") == 0)
		{
			super.setPieceID(1);
		}
		else 
		{
			super.setPieceID(-1);
		}
		
		super.setPieceCost(1);
	}
	
	public boolean pawnReachedPromotionRow()
	{
		if(movesDown == true && super.getRow() == 7)
		{
			return true;
		}
		else if(movesDown == false && super.getRow() == 0)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean validMove(int row, int col, int[][] board)
	{
		int rowTravelDistance = Math.abs(row - super.getRow());
		int colTravelDistance = Math.abs(col - super.getCol());
		
		//moving 1 space diagonally if it is occupied
		if(rowTravelDistance == 1 && colTravelDistance == 1)
		{
			//if pawn is trying to capture in correct direction
			if(movesDown == true && super.getRow() < row)
			{
				//if tile is occupied allow pawn to move diagonally
				if(board[row][col] != 0)
				{
					return true;
				}
			}
			//if pawn is trying to capture in correct direction
			if(movesDown == false && super.getRow() > row)
			{
				//if tile is occupied allow pawn to move diagonally
				if(board[row][col] != 0)
				{
					return true;
				}
			}
		}
		
		//moving vertically/horizontally
		if(super.getNumMovesTaken() == 0) 
		{	
			//can move two tiles forward first move
			if(rowTravelDistance <= 2 && super.getCol() == col && board[row][col] == 0)
			{
				if(movesDown == true && super.getRow() < row)
				{
					return true;
				}
				else if(movesDown == false && super.getRow() > row)
				{
					return true;
				}
			}
		}
		else 
		{
			if(rowTravelDistance == 1 && super.getCol() == col && board[row][col] == 0)
			{
				if(movesDown == true && super.getRow() < row)
				{
					return true;		
				}
				else if(movesDown == false && super.getRow() > row)
				{
					return true;	
				}
			}
		}
		
		return false;
	}
	
	public void setMovesDown(boolean movesDown)
	{
		this.movesDown = movesDown;
	}
}

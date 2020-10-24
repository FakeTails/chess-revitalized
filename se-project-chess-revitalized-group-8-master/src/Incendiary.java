
public class Incendiary extends Piece {

	Incendiary(String pieceColor)
	{
		super(pieceColor + "Incendiary.png");
		super.setPieceCost(6);
		
		if(pieceColor.compareTo("white") == 0)
		{
			super.setPieceID(10);
		}
		else 
		{
			super.setPieceID(-10);
		}
		
		super.setPieceCost(6);
	}
	
	@Override
	public boolean validMove(int row, int col, int[][] board) 
	{
		if (super.getRow() == row || super.getCol() == col)
		{
			boolean rowValidDistance = false;
			boolean colValidDistance = false;
			
			if(Math.abs(row - super.getRow()) < 2)
				rowValidDistance = true;
			
			if(Math.abs(col - super.getCol()) < 2)
				colValidDistance = true;
			
			if(rowValidDistance && colValidDistance)
				return true;
			else
				return false;
		}
		return false;
	}
	
	public boolean explode(Board chessBoard, int[][] boardState, int row, int col)
	{
		boolean kingDead = false;
		boolean kingDeadFromOtherIncendiary = false;
		
		// explode in all directions
		// top
		if (row-1 >= 0)
		{
			
			if (boardState[row-1][col] != 0)
			{
				// king was captured
				if (boardState[row-1][col] == 6 || boardState[row-1][col] == -6)
				{
					kingDead = true;
					chessBoard.removePiece(row-1, col);
				}
				else if (boardState[row-1][col] == 10 || boardState[row-1][col] == -10)
				{
					Incendiary anotherExplosion = (Incendiary) chessBoard.selectPieceFromBoard(row-1, col);
					kingDeadFromOtherIncendiary = anotherExplosion.explode(chessBoard, boardState, row-1, col);
				}
				else
				{
					chessBoard.removePiece(row-1, col);
				}
			}
			
			
			if (col-1 >= 0)
			{
				if (boardState[row-1][col-1] != 0)
				{
					// king was captured
					if (boardState[row-1][col-1] == 6 || boardState[row-1][col-1] == -6)
					{
						kingDead = true;
						chessBoard.removePiece(row-1, col-1);
					}
					else if (boardState[row-1][col-1] == 10 || boardState[row-1][col-1] == -10)
					{
						Incendiary anotherExplosion = (Incendiary) chessBoard.selectPieceFromBoard(row-1, col-1);
						kingDeadFromOtherIncendiary = anotherExplosion.explode(chessBoard, boardState, row-1, col-1);
					}
					else
					{
						chessBoard.removePiece(row-1, col-1);
					}
				}
			}
			
			if (col+1 <= 7)
			{
				if (boardState[row-1][col+1] != 0)
				{
					// king was captured
					if (boardState[row-1][col+1] == 6 || boardState[row-1][col+1] == -6)
					{
						kingDead = true;
						chessBoard.removePiece(row-1, col+1);
					}
					else if (boardState[row-1][col+1] == 10 || boardState[row-1][col+1] == -10)
					{
						Incendiary anotherExplosion = (Incendiary) chessBoard.selectPieceFromBoard(row-1, col+1);
						kingDeadFromOtherIncendiary = anotherExplosion.explode(chessBoard, boardState, row-1, col+1);
					}
					else
					{
						chessBoard.removePiece(row-1, col+1);
					}
					
				}
			}
		}
		
		// middle
		if (boardState[row][col] != 0)
		{
			// king was captured
			if (boardState[row][col] == 6 || boardState[row][col] == -6)
			{
				kingDead = true;
				chessBoard.removePiece(row, col); // self
			}
			else
			{
				chessBoard.removePiece(row, col); // self
			}
		}
		
		if (col+1 <= 7)
		{
			if (boardState[row][col+1] != 0)
			{
				// king was captured
				if (boardState[row][col+1] == 6 || boardState[row][col+1] == -6)
				{
					kingDead = true;
					chessBoard.removePiece(row, col+1);
				}
				else if (boardState[row][col+1] == 10 || boardState[row][col+1] == -10)
				{
					Incendiary anotherExplosion = (Incendiary) chessBoard.selectPieceFromBoard(row, col+1);
					kingDeadFromOtherIncendiary = anotherExplosion.explode(chessBoard, boardState, row, col+1);
				}
				else
				{
					chessBoard.removePiece(row, col+1);
				}
				
				
			}
		}
		
		if (col-1 >= 0)
		{
			if (boardState[row][col-1] != 0)
			{
				// king was captured
				if (boardState[row][col-1] == 6 || boardState[row][col-1] == -6)
				{
					kingDead = true;
					chessBoard.removePiece(row, col-1);
				}
				else if (boardState[row][col-1] == 10 || boardState[row][col-1] == -10)
				{
					Incendiary anotherExplosion = (Incendiary) chessBoard.selectPieceFromBoard(row, col-1);
					kingDeadFromOtherIncendiary = anotherExplosion.explode(chessBoard, boardState, row, col-1);
				}
				else
				{
					chessBoard.removePiece(row, col-1);
				}
			}
		}
		 
		 // bottom
		 if (row+1 <= 7)
		 {
			if (boardState[row+1][col] != 0)
			{
				// king was captured
				if (boardState[row+1][col] == 6 || boardState[row+1][col] == -6)
				{
					kingDead = true;
					chessBoard.removePiece(row+1, col);
				}
				else if (boardState[row+1][col] == 10 || boardState[row+1][col] == -10)
				{
					Incendiary anotherExplosion = (Incendiary) chessBoard.selectPieceFromBoard(row+1, col);
					kingDeadFromOtherIncendiary = anotherExplosion.explode(chessBoard, boardState, row+1, col);
				}
				else
				{
					chessBoard.removePiece(row+1, col);
				}
			}
			 
			if (col-1 >= 0)
			{
			 	if (boardState[row+1][col-1] != 0)
			 	{
			 		// king was captured
					if (boardState[row+1][col-1] == 6 || boardState[row+1][col-1] == -6)
					{
						kingDead = true;
						chessBoard.removePiece(row+1, col-1);
					}
					else if (boardState[row+1][col-1] == 10 || boardState[row+1][col-1] == -10)
					{
						Incendiary anotherExplosion = (Incendiary) chessBoard.selectPieceFromBoard(row+1, col-1);
						kingDeadFromOtherIncendiary = anotherExplosion.explode(chessBoard, boardState, row+1, col-1);
					}
					else
					{
						chessBoard.removePiece(row+1, col-1);
					}
			 	}
			}
				
			if (col+1 <= 7)
			{
				if (boardState[row+1][col+1] != 0)
				{
					// king was captured
					if (boardState[row+1][col+1] == 6 || boardState[row+1][col+1] == -6)
					{
						kingDead = true;
						chessBoard.removePiece(row+1, col+1);
					}
					else if (boardState[row+1][col+1] == 10 || boardState[row+1][col+1] == -10)
					{
						Incendiary anotherExplosion = (Incendiary) chessBoard.selectPieceFromBoard(row+1, col+1);
						kingDeadFromOtherIncendiary = anotherExplosion.explode(chessBoard, boardState, row+1, col+1);
					}
					else
					{
						chessBoard.removePiece(row+1, col+1);
					}
				}
			}
		 }
		 
		 if (kingDeadFromOtherIncendiary)
		 {
			 return true;
		 }
		 
		 return kingDead;
	}
}

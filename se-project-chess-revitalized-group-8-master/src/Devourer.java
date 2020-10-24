
public class Devourer extends Piece {

	Devourer(String pieceColor)
	{
		super(pieceColor + "Devourer.png");
		super.setPieceCost(6);
		
		if(pieceColor.compareTo("white") == 0)
		{
			super.setPieceID(11);
		}
		else 
		{
			super.setPieceID(-11);
		}
		
		super.setPieceCost(4);
	}
	
	@Override
	public boolean validMove(int row, int col, int[][] board) 
	{
		if (super.getRow() == row || super.getCol() == col)
		{
			boolean rowValidDistance = false;
			boolean colValidDistance = false;
			
			if(Math.abs(row - super.getRow()) < 2)
			{
				rowValidDistance = true;
			}
			
			if(Math.abs(row - super.getRow()) < 3)
			{
				// moving up
				if (row < super.getRow())
				{
					if (board[row+1][col] == 0)
					{
						rowValidDistance = true;
					}
				}
				
				// moving down
				if (row > super.getRow())
				{
					if (board[row-1][col] == 0)
					{
						rowValidDistance = true;
					}
				}
			}
			
			if(Math.abs(col - super.getCol()) < 2)
			{
					colValidDistance = true;
			}
				
			if(Math.abs(col - super.getCol()) < 3)
			{
				// moving right
				if (col < super.getCol())
				{
					if (board[row][col+1] == 0)
					{
						colValidDistance = true;
					}
				}
				
				// moving left
				if (col > super.getCol())
				{
					if (board[row][col-1] == 0)
					{
						colValidDistance = true;
					}
				}
			}
			
			if(rowValidDistance && colValidDistance)
				return true;
			else
				return false;
		}
		return false;
	}
	
	public boolean devour(Board chessBoard, int[][] boardState, Piece selectedPiece, Piece previousPiece)
	{
		boolean kingDead = false;
		Piece replacementPiece = null;
		
		if (selectedPiece.getPieceID() == 1 || selectedPiece.getPieceID() == -1)
		{
			replacementPiece = new Pawn(previousPiece.getPieceColor());
			if (previousPiece.getPieceColor().equalsIgnoreCase("black"))
			{
				Pawn k = (Pawn) replacementPiece;
				k.setMovesDown(true);
			}
		}
		else if (selectedPiece.getPieceID() == 2 || selectedPiece.getPieceID() == -2)
		{
			replacementPiece = new Knight(previousPiece.getPieceColor());
		}
		else if (selectedPiece.getPieceID() == 3 || selectedPiece.getPieceID() == -3)
		{
			replacementPiece = new Bishop(previousPiece.getPieceColor());
		}
		else if (selectedPiece.getPieceID() == 4 || selectedPiece.getPieceID() == -4)
		{
			replacementPiece = new Rook(previousPiece.getPieceColor());
		}
		else if (selectedPiece.getPieceID() == 5 || selectedPiece.getPieceID() == -5)
		{
			replacementPiece = new Queen(previousPiece.getPieceColor());
		}
		else if (selectedPiece.getPieceID() == 6 || selectedPiece.getPieceID() == -6)
		{
			replacementPiece = new King(previousPiece.getPieceColor());
		}
		else if (selectedPiece.getPieceID() == 7 || selectedPiece.getPieceID() == -7)
		{
			replacementPiece = new FalseKing(previousPiece.getPieceColor());
		}
		else if (selectedPiece.getPieceID() == 8 || selectedPiece.getPieceID() == -8)
		{
			replacementPiece = new Archer(previousPiece.getPieceColor());
		}
		else if (selectedPiece.getPieceID() == 9 || selectedPiece.getPieceID() == -9)
		{
			replacementPiece = new Squire(previousPiece.getPieceColor());
		}
		else if (selectedPiece.getPieceID() == 10 || selectedPiece.getPieceID() == -10)
		{
			replacementPiece = new Incendiary(previousPiece.getPieceColor());
		}
		else if (selectedPiece.getPieceID() == 11 || selectedPiece.getPieceID() == -11)
		{
			replacementPiece = new Devourer(previousPiece.getPieceColor());
		}
		
		
		int selectedRow = selectedPiece.getRow();
		int selectedCol = selectedPiece.getCol();
		
		if (boardState[selectedRow][selectedCol] == 6 || boardState[selectedRow][selectedCol] == -6)
		{
			kingDead = true;
		}
		
		chessBoard.removePiece(selectedRow, selectedCol);
		replacementPiece.setRow(selectedRow);
		replacementPiece.setCol(selectedCol);
		
		chessBoard.removePiece(previousPiece.getRow(), previousPiece.getCol());

		chessBoard.addPiece(replacementPiece, selectedRow, selectedCol);
		
		return kingDead;
	}
}

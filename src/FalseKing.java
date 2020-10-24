
public class FalseKing extends Piece {

	
	FalseKing(String pieceColor)
	{
		super(pieceColor + "King.png");
		super.setPieceCost(3);
		
		if(pieceColor.compareTo("white") == 0)
		{
			super.setPieceID(7);
		}
		else 
		{
			super.setPieceID(-7);
		}
		
		super.setPieceCost(2);
	}

	public boolean validMove(int row, int col, int[][] board) 
	{
		if(validMoveDistance(row, col, board))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	boolean validMoveDistance(int row, int col, int[][] board)
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
	
	void inCheck(int[][] boardState, Tile[][] board)
	{
		int[][] pieceCanCaptureKing = findPossibleCapture(boardState,board);
		if(pieceCanCaptureKing == null)
		{
			String pieceColor = super.getPieceColor();
			super.updateImage(pieceColor + "King.png");
		}
		else
		{
			String pieceColor = super.getPieceColor();
			super.updateImage(pieceColor + "Jester.png");
		}
	}
	
	int[][] findPossibleCapture(int[][] boardState, Tile[][] board) //specific for false king
	{
		Piece currentPiece;
		int[][] pieceCanCapture = new int[1][2];
				
		int neighborPieceColor = 0; //positive number = same color, negative = different color
		for(int i = 0; i < 8; i++)
		{
			pieceCanCapture[0][0] = i;
			for(int j = 0; j < 8; j++)
			{
				pieceCanCapture[0][1] = j;
				neighborPieceColor = super.getPieceID() * boardState[i][j];
				if(boardState[i][j] != 0 && neighborPieceColor < 0 && boardState[i][j] != 6 && boardState[i][j] != -6)
				{
					currentPiece = (Piece) board[i][j].getComponentAt(i, j);
					if(currentPiece.validMove(super.getRow(), super.getCol(), boardState))
					{
						return pieceCanCapture;
					}
				}
			}
		}
		return null;
	}
	
	void updatePieceImage()
	{
		String pieceColor = super.getPieceColor();
		super.updateImage(pieceColor + "Jester.png");
	}
}

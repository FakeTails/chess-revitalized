
public class King extends Piece {

	private int[] defendingPieceInfo = new int[5];
	/*
	 * defending piece's current row
	 * defending piece's current column
	 * defending piece's target row
	 * defending piece's target column
	 * defending piece's blocking / capturing / neither (0 / 1, -1)
	 */
	
	private boolean hasBeenInCheck = false;
	
	private int[][] boardState = new int[8][8];
	private Tile[][] board = new Tile[8][8];

	
	King(String pieceColor)
	{
		super(pieceColor + "King.png");
		super.setPieceCost(0);
		
		if(pieceColor.compareTo("white") == 0)
		{
			super.setPieceID(6);
		}
		else 
		{
			super.setPieceID(-6);
		}
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
	
	void inCheck()
	{		
		this.hasBeenInCheck = true;
	}
	
	boolean validCastling(int row, int col, int[][] board, Piece selectedPiece, Piece previousPiece)
	{
		if(hasBeenInCheck)
		{
			System.out.println("King has been in check can no longer castle");
			return false;
		}
		
		if((previousPiece instanceof King) && (selectedPiece instanceof Rook) && (previousPiece.getNumMovesTaken() == 0) && (selectedPiece.getNumMovesTaken() == 0))
		{
			//checking to make sure path to rook is clear
			//case for rook to right of king
			if(previousPiece.getCol() < selectedPiece.getCol())
			{
				int rookCol = selectedPiece.getCol();
				for(int i = getCol()+1; i < rookCol; i++)
				{
					if(board[row][i] != 0)
					{
						System.out.println("\n Invalid castle \n");
						return false;						
					}
				}
			}
			//case for rook to left of king
			if(previousPiece.getCol() > selectedPiece.getCol())
			{
				int rookCol = selectedPiece.getCol();
				for(int i = getCol()-1; i > rookCol; i--)
				{
					if(board[row][i] != 0)
					{
						System.out.println("\n Invalid castle \n");
						return false;						
					}
				}
			}
			return true;
		}
		else
			System.out.println("\ncan't castle anymore\n");
			return false;
	
	}
	
	boolean isCheck(int[][] boardState, Tile[][] board)
	{
		this.board = board;
		this.boardState = boardState;
		
		int[][] pieceCanCaptureKing = findPossibleCapture(super.getRow(),super.getCol(), super.getPieceID());
		if(pieceCanCaptureKing == null)
		{
			return false;
		}
		else
		{
			inCheck();
			return true;
		}
	}
	
	
	boolean isCheckmate(int[][] boardState, Tile[][] board)
	{
		this.board = board;
		this.boardState = boardState;
		
		int[][] pieceCanCaptureKing = findPossibleCapture(super.getRow(),super.getCol(), super.getPieceID());
		
		if(isSafePositionAvailable(super.getRow(),super.getCol()) == true) //safe position to move to is available
		{
			System.out.println("Safe position available");
			defendingPieceInfo[4] = -1; // set to neither
			return false; 
		}
		else
		{
			if(findPieceToCaptureCheck(pieceCanCaptureKing) != null) //piece found to capture check
			{
				System.out.println("Checking piece can be captured");
				defendingPieceInfo[4] = 1; // set to capture
				return false;
			}
			else
			{
				if(isBlockPossible() == true) //piece found to block check
				{
					System.out.println("Checking piece can be blocked");
					defendingPieceInfo[4] = 0; // set to neither
					return false; 
				}
			}
		}

		return true;
	}
	
	int[] getDefendingPieceInfo()
	{
		return defendingPieceInfo;
	}
	
	
	int[][] findPossibleCapture(int row, int col, int pieceId) //finds a piece that can capture the given piece
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
				neighborPieceColor = pieceId * this.boardState[i][j];
				if(this.boardState[i][j] != 0 && neighborPieceColor < 0)
				{
          
          try // prevent crash due to first round choice of first row of pieces
					{
						currentPiece = (Piece) this.board[i][j].getComponentAt(i, j);
					}
					catch (ClassCastException e)
					{
						return null;
					}
          
					//archer has special capture rules
					if(currentPiece instanceof Archer)
					{
						if(((Archer)currentPiece).validCapture(row, col, this.boardState))
						{
							// for computer.
							defendingPieceInfo[0] = currentPiece.getRow(); // piece row
							defendingPieceInfo[1] = currentPiece.getCol(); // piece col
							defendingPieceInfo[2] = row; // row to capture
							defendingPieceInfo[3] = col; // col to caxpture

							return pieceCanCapture;
						}
					}
					//if not an archer carry on
					else if(currentPiece.validMove(row, col, this.boardState))
					{
						// for computer.
						defendingPieceInfo[0] = currentPiece.getRow(); // piece row
						defendingPieceInfo[1] = currentPiece.getCol(); // piece col
						defendingPieceInfo[2] = row; // row to capture
						defendingPieceInfo[3] = col; // col to caxpture

						return pieceCanCapture;
					}
					
					
				}
			}
		}
		return null;
	}
	
	private boolean isSafePositionAvailable(int row, int col) //finds a position the king can move to without capture
	{
		int moveForward = row -1;
		int moveLeft = col -1;
		int moveRight = col +1;
		int moveBack = row + 1;		
		
		if(isMovingForwardSafe(moveForward, moveLeft, moveRight, col) == true)
		{
			return true;
		}
		if(isMovingBackwardSafe(moveBack, moveLeft, moveRight, col) == true)
		{
			return true;
			
		}
		if(isMovingLeftSafe(row, moveLeft) == true)
		{
			return true;
		}
		
		if(isMovingRightSafe(row, moveRight) == true)
		{
			return true;
		}
		
		return false;
	}
	
	private boolean isPositionValid(int row, int col) //checks if king can move to position, not occupied by same color piece
	{
		int neighborPieceColor = 0; //positive number = same color; negative = different color;
		
		if(this.boardState[row][col] != 0)
		{
			neighborPieceColor = super.getPieceID() * this.boardState[row][col];
			if(neighborPieceColor < 0) //different colors; valid capture
			{
				return true;
			}
			else //same color, can't move
			{
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isMovingForwardSafe(int moveForward, int moveLeft, int moveRight, int kingCol) //assuming position is not safe
	{
		if(moveForward < 0) //out of bounds
		{
			return false;
		}
		
		if(isPositionValid(moveForward, kingCol) == true && findPossibleCapture(moveForward, kingCol,super.getPieceID()) == null)
		{
			return true;
		}
			 
		if(moveLeft > -1)
		{
			if(isPositionValid(moveForward,moveLeft) && findPossibleCapture(moveForward, moveLeft,super.getPieceID()) == null)
			{
				return true;
			}
		}
			
		if(moveRight < 7)
		{
			if(isPositionValid(moveForward,moveRight) && findPossibleCapture(moveForward, moveRight,super.getPieceID()) == null)
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isMovingBackwardSafe(int moveBack, int moveLeft, int moveRight, int kingCol) //not safe
	{
		if(moveBack > 7) //out of bounds
		{
			return false;
		}
		
		if(isPositionValid(moveBack, kingCol) && findPossibleCapture(moveBack,kingCol,super.getPieceID()) == null)
		{
			return true;
		}
		
		if(moveLeft > -1)
		{
			if(isPositionValid(moveBack,moveLeft) && findPossibleCapture(moveBack, moveLeft,super.getPieceID()) == null)
			{
				return true;
			}
		}
		
		if(moveRight < 7)
		{
			if(isPositionValid(moveBack,moveRight) && findPossibleCapture(moveBack, moveRight,super.getPieceID()) == null)
			{
				return true;
			}
		}
			
		return false;
	}
	
	private boolean isMovingLeftSafe(int kingRow, int moveLeft) 
	{
		if(moveLeft < 0) //out of bounds
		{
			return false;
		}
		
		if(isPositionValid(kingRow,moveLeft) && findPossibleCapture(kingRow, moveLeft,super.getPieceID()) == null)
		{
			return true;
		}
				
		return false; 
	}
	
	private boolean isMovingRightSafe(int kingRow, int moveRight) //assuming position is safe
	{
		if(moveRight > 7) //out of bounds
		{
			return false;
		}
		
		if(isPositionValid(kingRow, moveRight) && findPossibleCapture(kingRow, moveRight,super.getPieceID()) == null)
		{
			return true;
		}
			
		return false; 
	}
	
	private int[][] findPieceToCaptureCheck(int[][] pieceCanCaptureKing)
	{
		int row = pieceCanCaptureKing[0][0];
		int col = pieceCanCaptureKing[0][1];
		int pieceId = this.boardState[row][col];
		
		return findPossibleCapture(row, col, pieceId); 
	}
	
	private boolean isBlockPossible()
	{
		int pieceColor = 0;
		Piece currentPiece;
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				pieceColor = super.getPieceID() * this.boardState[i][j]; //positive number, same color
				if(this.boardState[i][j] != 0 && pieceColor > 0 && this.boardState[i][j] != super.getPieceID())
				{
					currentPiece = (Piece) this.board[i][j].getComponentAt(i, j);
					if(isBlockFound(currentPiece))
					{
						return true;
					}
				}
			}
		}
		return false;
		
	}
	
	private boolean isBlockFound(Piece blockPiece)
	{
		int currentBoardState = 0;
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				if(blockPiece.validMove(i, j, this.boardState) == true)
				{
					currentBoardState = this.boardState[i][j];
					this.boardState[i][j] = blockPiece.getPieceID();
					if(findPossibleCapture(super.getRow(), super.getCol(), super.getPieceID()) == null)
					{
						// for computer.
						defendingPieceInfo[0] = blockPiece.getRow(); // piece row
						defendingPieceInfo[1] = blockPiece.getCol(); // piece col
						defendingPieceInfo[2] = i; // row to block
						defendingPieceInfo[3] = j; // col to block
						
						this.boardState[i][j] = currentBoardState;
						return true;
					}
					this.boardState[i][j] = currentBoardState;
				}
			}
		}
		return false;
	}

}

import java.util.Arrays;
import java.util.Random;

public class ComputerOpponent implements java.io.Serializable {

	int fyck = 0;
	private Board chessBoard;
	
	private int[][] boardState;
	private Piece selectedPiece;
	private int pieceID = 0;
	private int selectedPieceRow = -1;
	private int selectedPieceCol = -1;
	
	private int[][] capturingPieceLocations;
	private int[][] capturableLocations;
	private int currentCaptureableArrayPosition = 0;
	private int decidedMovementRow = -1;
	private int decidedMovementCol = -1;
	private Piece capturingPiece;
	private boolean noCapture;
	private boolean immobile = true;
	private Random rand;
	
	private King king = null;
	private int kingRow;
	private int kingCol;
	private boolean kingCaptured;
	
	
	ComputerOpponent(Board chessBoard)
	{
		this.chessBoard = chessBoard;
		capturableLocations = new int[500][3]; // row, col, value. 
		capturingPieceLocations = new int[500][2]; // row, col.
		Arrays.fill(capturableLocations[0], -1);
		rand = new Random();
	}
	
	Piece selectPiece(int[][] boardState, boolean blackInCheck)
	{
		currentCaptureableArrayPosition = 0;
		this.boardState = boardState;
		kingCaptured = true;
		
		/*
		// in row order moving for testing.
		for (int row = 0; row < 8; row++)
		{
			for (int col = 0; col < 8; col++)
			{
				if (boardState[row][col] < 0) // This is a selectable piece
				{
					pieceID = boardState[row][col];

					selectedPieceRow = row;
					selectedPieceCol = col;
					
					selectedPiece = chessBoard.selectPieceFromBoard(selectedPieceRow, selectedPieceCol);
					
					checkPossibleMoves();
					selectedPieceRow = -1;
					selectedPieceCol = -1;
					if (!immobile)
					{
						checkForCapture();
						return selectedPiece;
					}
				}
			}
		}
		*/
		System.out.println("here2");
		// Check every piece for possible captures.
		for (int row = 0; row < 8; row++)
		{
			for (int col = 0; col < 8; col++)
			{
				if (boardState[row][col] < 0) // This is a selectable piece
				{
					System.out.println("here8");
					pieceID = boardState[row][col];
					selectedPieceRow = row;
					selectedPieceCol = col;
					
					selectedPiece = chessBoard.selectPieceFromBoard(selectedPieceRow, selectedPieceCol);
								
					System.out.println(selectedPiece);
					checkPossibleMoves();
					
					if (pieceID == -6)
					{
						kingRow = row;
						kingCol = col;
						kingCaptured = false;
					}
				}
			}
		}
		
		System.out.println("here3");
		
		if (kingCaptured == false)
		{
			if (currentCaptureableArrayPosition == 0 || blackInCheck) // if no capture, move with randomization.
			{
				immobile = true;
				boolean[] usedCol = new boolean[8];
				boolean[] usedRow = new boolean[8];
				boolean empty = false;
				
				Arrays.fill(usedCol, false);
				Arrays.fill(usedRow, false);
				
				int randCol = rand.nextInt(8);
				int randRow = rand.nextInt(8);
				
				usedCol[randCol] = true;
				usedCol[randRow] = true;
				
				// randomized moving.
				while (immobile)
				{
					while (!empty && immobile)
					{
						if (boardState[randRow][randCol] < 0) // This is a selectable piece
						{
							pieceID = boardState[randRow][randCol];
							
							if (blackInCheck == false) // act natural
							{
								selectedPieceRow = randRow;
								selectedPieceCol = randCol;
								
								selectedPiece = chessBoard.selectPieceFromBoard(selectedPieceRow, selectedPieceCol);
								
								checkPossibleMoves();
								selectedPieceRow = -1;
								selectedPieceCol = -1;
								if (!immobile)
								{
									return selectedPiece;
								}
							}
							else // King in check, do something!!!!!!
							{
								selectedPieceRow = kingRow;
								selectedPieceCol = kingCol;
								
								king = (King) chessBoard.selectPieceFromBoard(selectedPieceRow, selectedPieceCol);
								
								int[] defendingPieceInfo = king.getDefendingPieceInfo();

								for (int i = 0; i < defendingPieceInfo.length; i++)
								{
									System.out.print(i + ": " + defendingPieceInfo[i] + " ");
								}
								System.out.println();
								
								if (defendingPieceInfo[4] == 1) // capture piece
								{
									selectedPiece = chessBoard.selectPieceFromBoard(defendingPieceInfo[0], defendingPieceInfo[1]);
									pieceID = selectedPiece.getPieceID();
									
									decidedMovementRow = defendingPieceInfo[2];
									decidedMovementCol = defendingPieceInfo[3];
									noCapture = false;
									
									return selectedPiece;
								}
								else if (defendingPieceInfo[4] == 0) // block piece
								{									
									selectedPiece = chessBoard.selectPieceFromBoard(defendingPieceInfo[0], defendingPieceInfo[1]);
									pieceID = selectedPiece.getPieceID();
									
									decidedMovementRow = defendingPieceInfo[2];
									decidedMovementCol = defendingPieceInfo[3];
									noCapture = true;
									
									return selectedPiece;
								}
								else // no defenders, move.
								{
									currentCaptureableArrayPosition = 0;
									selectedPiece = chessBoard.selectPieceFromBoard(kingRow, kingCol);
									pieceID = -6;
									
									checkPossibleMoves();
									
									if (currentCaptureableArrayPosition > 0)
									{
										checkForCapture();
									}
									
									return selectedPiece;
								}
							}
						}
						
						empty = true;
						for (int i = 0; i < usedCol.length; i++)
						{
							if (usedCol[i] == false)
							{
								empty = false;
							}
						}
						
						// find unused random col
						while (usedCol[randCol] == true && !empty)
						{
							randCol = rand.nextInt(8);
						}
						usedCol[randCol] = true;
					}
					
					// find unused random row
					while (usedRow[randRow] == true)
					{
						randRow = rand.nextInt(8);
					}
					usedRow[randRow] = true;
					
					// reset values
					Arrays.fill(usedCol, false);
					empty = false;
				}
			}
			else
			{
				return checkForCapture();
			}
		}
		
		System.out.println("NULL RETURN VALUE IN COMPUTER SELECTPIECE");
		
		return null; // big error.
	}
	
	void checkPossibleMoves()
	{
		fyck++;
		System.out.println("here... " + fyck);
		immobile = false;
		
		switch (pieceID)
		{
			case -1: // Pawn
			{
				checkPawnMoves();
				return;
			}
			case -2: // Knight
			{
				checkKnightMoves();
				return;
			}
			case -3: // Bishop
			{
				checkBishopMoves();
				return;
			}
			case -4: // Rook
			{
				checkRookMoves();
				return;
			}
			case -5: // Queen
			{
				checkQueenMoves();
				return;
			}
			case -6: // King
			{
				checkKingMoves();
				return;
			}
			case -7: // false King
			{
				checkJesterMoves();
				return;
			}
			case -8: // Archer
			{
				checkArcherMoves();
				return;
			}
			case -9: // Squire
			{
				checkSquireMoves();
				return;
			}
			case -10: // Incendiary
			{
				checkIncendiaryMoves();
				return;
			}
			case -11: // Devourer
			{
				checkDevourerMoves();
				return;
			}
		}
	}
	
	void move()
	{
		if (!noCapture)
		{
			System.out.println(capturingPiece + " " + capturingPiece.getRow()  + " " + capturingPiece.getCol()  + " " +  decidedMovementRow + " " + decidedMovementCol + " " + noCapture);
		}
		else
		{
			System.out.println(decidedMovementRow + " " + decidedMovementCol + " " + noCapture);

		}
		System.out.println("here5");
		chessBoard.movePiece(decidedMovementRow, decidedMovementCol, noCapture);
		
		if (pieceID == -1 && decidedMovementRow == 7) // if pawn is at the other end of the board...
		{
			chessBoard.removePiece(decidedMovementRow, decidedMovementCol);
			chessBoard.addPiece(new Queen("black"), decidedMovementRow, decidedMovementCol); // change to queen.
		}
	}
	
	Piece checkForCapture()
	{	
		System.out.println("here4");
		int highestValueCapture = 0;
		int highestValueCaptureLoc = 0;
		
		for (int i = 0; i < currentCaptureableArrayPosition; i++)
		{
			Piece currentPiece = chessBoard.selectPieceFromBoard(capturableLocations[i][0], capturableLocations[i][1]);
			
			// if king
			if (currentPiece.getPieceID() == 6)
			{
				highestValueCapture = 1000;
				highestValueCaptureLoc = i;
			}
			else
			{
				if (currentPiece.getPieceCost() > highestValueCapture) // if piece value is higher than the last,
				{
					highestValueCapture = currentPiece.getPieceCost();
					highestValueCaptureLoc = i;
				}
			}
		}
		
		// there is something to capture.
		if (highestValueCapture != 0)
		{
			decidedMovementRow = capturableLocations[highestValueCaptureLoc][0];
			decidedMovementCol = capturableLocations[highestValueCaptureLoc][1];
			noCapture = false;	
			capturingPiece = chessBoard.selectPieceFromBoard(capturingPieceLocations[highestValueCaptureLoc][0], capturingPieceLocations[highestValueCaptureLoc][1]);
			return capturingPiece;
		}
		else
		{
			System.out.println("NULL RETURN VALUE IN COMPUTER CHECKFORCAPTURE");

			return null;
		}
	}
	
	boolean isCapturing()
	{
		return !noCapture; // return not noCapture to get capture state.
	}
	
	int getCaptureRow()
	{
		return decidedMovementRow;
	}
	
	int getCaptureCol()
	{
		return decidedMovementCol;
	}

	void checkPawnMoves()
	{
		if (selectedPieceRow+1 != 8) // if pawn is not at the bottom of the board.
		{
			// if not out of bounds, check for two possible captures.
			if (selectedPieceCol-1 >= 0 && selectedPieceCol+1 <= 7) 
			{
				// both have capturable pieces.
				if (boardState[selectedPieceRow+1][selectedPieceCol+1] > 0 && boardState[selectedPieceRow+1][selectedPieceCol-1] > 0)
				{
					// right piece is worth more than left piece, capture it.
					if (boardState[selectedPieceRow+1][selectedPieceCol+1] > boardState[selectedPieceRow+1][selectedPieceCol-1]) 
					{
						capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
						capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
						
						capturableLocations[currentCaptureableArrayPosition][0] = selectedPieceRow+1;
						capturableLocations[currentCaptureableArrayPosition][1] = selectedPieceCol+1;
						capturableLocations[currentCaptureableArrayPosition][2] = boardState[selectedPieceRow+1][selectedPieceCol+1];
						currentCaptureableArrayPosition++;
						return;
					}
					else // left piece worth more, capture it.
					{
						capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
						capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
						
						capturableLocations[currentCaptureableArrayPosition][0] = selectedPieceRow+1;
						capturableLocations[currentCaptureableArrayPosition][1] = selectedPieceCol-1;
						capturableLocations[currentCaptureableArrayPosition][2] = boardState[selectedPieceRow+1][selectedPieceCol-1];
						currentCaptureableArrayPosition++;
						return;
					}
				}
			}
			
			// one capture available
			if (selectedPieceCol+1 <= 7) // if not out of bounds, check for possible capture.
			{
				if (boardState[selectedPieceRow+1][selectedPieceCol+1] > 0) 
				{
					capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
					capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
					
					capturableLocations[currentCaptureableArrayPosition][0] = selectedPieceRow+1;
					capturableLocations[currentCaptureableArrayPosition][1] = selectedPieceCol+1;
					capturableLocations[currentCaptureableArrayPosition][2] = boardState[selectedPieceRow+1][selectedPieceCol+1];
					currentCaptureableArrayPosition++;
					return;
				}
			}
			if (selectedPieceCol-1 >= 0) // if not out of bounds, check for possible capture.
			{
				if (boardState[selectedPieceRow+1][selectedPieceCol-1] > 0)
				{
					capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
					capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
					
					capturableLocations[currentCaptureableArrayPosition][0] = selectedPieceRow+1;
					capturableLocations[currentCaptureableArrayPosition][1] = selectedPieceCol-1;
					capturableLocations[currentCaptureableArrayPosition][2] = boardState[selectedPieceRow+1][selectedPieceCol-1];
					currentCaptureableArrayPosition++;
					return;
				}
			}
			 
			// no captures available, move.
			if (selectedPiece.getNumMovesTaken() == 0) // move two spaces forward if possible.
			{
				if (selectedPieceRow+1 <= 7 && selectedPieceRow+2 <= 7)
				{
					if (boardState[selectedPieceRow+1][selectedPieceCol] == 0 && boardState[selectedPieceRow+2][selectedPieceCol] == 0)
					{
						decidedMovementRow = selectedPieceRow+2; 
						decidedMovementCol = selectedPieceCol;
						noCapture = true;
						return;
					}
				}
				
			}
			
			if (selectedPieceRow+1 <= 7)
			{
				if (boardState[selectedPieceRow+1][selectedPieceCol] == 0) // move one space forward.
				{
					decidedMovementRow = selectedPieceRow+1;
					decidedMovementCol = selectedPieceCol;
					noCapture = true;
					return;
				}
			}
		}
		
		// no available move, return to choose a different piece.
		immobile = true;
		return;
	}
	
	void checkKnightMoves()
	{
		int[][] possibleMoves = new int[8][3];
		/* possible moves cheat sheet:
		 * 0 = row
		 * 1 = col
		 * 2 = value of piece (for capture)
		 */
		Arrays.fill(possibleMoves[0], -100);
		Arrays.fill(possibleMoves[2], 0);
		
		if (((selectedPieceRow-1 < 0 || selectedPieceCol-2 < 0) 
				|| (selectedPieceRow-1 > 7 || selectedPieceCol-2 > 7)) != true) // if knight won't cross over the top left border.
		{
			possibleMoves[0][0] = selectedPieceRow-1; 
			possibleMoves[0][1] = selectedPieceCol-2;
			possibleMoves[0][2] = boardState[selectedPieceRow-1][selectedPieceCol-2];
		}
		
		if (((selectedPieceRow-2 < 0 || selectedPieceCol-1 < 0) 
				|| (selectedPieceRow-2 > 7 || selectedPieceCol-1 > 7)) != true)// if knight won't cross over the far top left border.
		{
			possibleMoves[1][0] = selectedPieceRow-2; 
			possibleMoves[1][1] = selectedPieceCol-1;
			possibleMoves[1][2] = boardState[selectedPieceRow-2][selectedPieceCol-1];
		}
		
		if (((selectedPieceRow-2 < 0 || selectedPieceCol+1 < 0) 
				|| (selectedPieceRow-2 > 7 || selectedPieceCol+1 > 7)) != true) // if knight won't cross over the far bottom left border.
		{
			possibleMoves[2][0] = selectedPieceRow-2; 
			possibleMoves[2][1] = selectedPieceCol+1;
			possibleMoves[2][2] = boardState[selectedPieceRow-2][selectedPieceCol+1];
		}
		
		if (((selectedPieceRow-1 < 0 || selectedPieceCol+2 < 0) 
				|| (selectedPieceRow-1 > 7 || selectedPieceCol+2 > 7)) != true) // if knight won't cross over the bottom left border.
		{
			possibleMoves[3][0] = selectedPieceRow-1; 
			possibleMoves[3][1] = selectedPieceCol+2;
			possibleMoves[3][2] = boardState[selectedPieceRow-1][selectedPieceCol+2];
		}
		
		if (((selectedPieceRow+1 > 7 || selectedPieceCol-2 > 7) 
				|| (selectedPieceRow+1 < 0 || selectedPieceCol-2 < 0)) != true) // if knight won't cross over the top right border.
		{
			possibleMoves[4][0] = selectedPieceRow+1; 
			possibleMoves[4][1] = selectedPieceCol-2;
			possibleMoves[4][2] = boardState[selectedPieceRow+1][selectedPieceCol-2];
		}
		
		if (((selectedPieceRow+2 > 7 || selectedPieceCol-1 > 7) 
				|| (selectedPieceRow+2 < 0 || selectedPieceCol-1 < 0)) != true)// if knight won't cross over the far top right border.
		{
			possibleMoves[5][0] = selectedPieceRow+2; 
			possibleMoves[5][1] = selectedPieceCol-1;
			possibleMoves[5][2] = boardState[selectedPieceRow+2][selectedPieceCol-1];
		}
		
		if (((selectedPieceRow+2 > 7 || selectedPieceCol+1 > 7) 
				|| (selectedPieceRow+2 < 0 || selectedPieceCol+1 < 0)) != true) // if knight won't cross over the far bottom right border.
		{
			possibleMoves[6][0] = selectedPieceRow+2; 
			possibleMoves[6][1] = selectedPieceCol+1;
			possibleMoves[6][2] = boardState[selectedPieceRow+2][selectedPieceCol+1];
		}
		
		if (((selectedPieceRow+1 > 7 || selectedPieceCol+2 > 7) 
				|| (selectedPieceRow+1 < 0 || selectedPieceCol+2 < 0)) != true) // if knight won't cross over the bottom right border.
		{
			possibleMoves[7][0] = selectedPieceRow+1; 
			possibleMoves[7][1] = selectedPieceCol+2;
			possibleMoves[7][2] = boardState[selectedPieceRow+1][selectedPieceCol+2];
		}
						
		int highestValueCapture = 0;
		int highestValueCaptureLoc = -1;
		boolean validMoveDetected = false;
		
		for (int i = 0; i < 8; i++)
		{
			if (possibleMoves[i][0] != -100)
			{
				validMoveDetected = true;
			}
			
			if (possibleMoves[i][2] > highestValueCapture)
			{
				highestValueCapture = possibleMoves[i][2];
				highestValueCaptureLoc = i;
			}
		}
		
		// there is something to capture.
		if (highestValueCaptureLoc != -1)
		{
			capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
			capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
			
			capturableLocations[currentCaptureableArrayPosition][0] = possibleMoves[highestValueCaptureLoc][0];
			capturableLocations[currentCaptureableArrayPosition][1] = possibleMoves[highestValueCaptureLoc][1];
			capturableLocations[currentCaptureableArrayPosition][2] = possibleMoves[highestValueCaptureLoc][2];
			currentCaptureableArrayPosition++;			
			return;
		}
		else if (validMoveDetected)// nothing to capture, try to move.
		{
			int randMove = rand.nextInt(8);

			while (possibleMoves[randMove][0] == -100 || possibleMoves[randMove][2] != 0)
			{
				randMove = rand.nextInt(8);
			}
			
			decidedMovementRow = possibleMoves[randMove][0]; 
			decidedMovementCol = possibleMoves[randMove][1];
			noCapture = true;					
			return;
		}

		immobile = true;
		return;
	}

	void checkBishopMoves()
	{
		boolean topLeftBlocked = false;
		boolean bottomLeftBlocked = false;
		boolean topRightBlocked = false;
		boolean bottomRightBlocked = false;
		
		int[][] possibleMoves = new int[15][3];
		Arrays.fill(possibleMoves[0], -100);
		Arrays.fill(possibleMoves[2], 0);
		int currentPossibleMoves = 0;
		int pieceValue;
		
		// check all directions
		for (int i = 1; i < 8; i++)
		{
			// if Bishop won't cross over the top left border.
			if (((selectedPieceRow-i < 0 || selectedPieceCol-i < 0) != true) && !topLeftBlocked) 
			{
				pieceValue = boardState[selectedPieceRow-i][selectedPieceCol-i];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow-i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol-i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow-i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol-i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					topLeftBlocked = true;
				}
				else
				{
					topLeftBlocked = true;
				}
			}
			
			// if Bishop won't cross over the bottom left border.
			if ((((selectedPieceRow+i < 0 || selectedPieceCol-i < 0) 
					|| (selectedPieceRow+i > 7 || selectedPieceCol-i > 7)) != true) && !bottomLeftBlocked) 
			{
				pieceValue = boardState[selectedPieceRow+i][selectedPieceCol-i];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow+i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol-i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow+i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol-i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					bottomLeftBlocked = true;
				}
				else
				{
					bottomLeftBlocked = true;
				}
			}
			
			// if Bishop won't cross over the top right border.
			if ((((selectedPieceRow-i < 0 || selectedPieceCol+i < 0) 
					|| (selectedPieceRow-i > 7 || selectedPieceCol+i > 7)) != true && !topRightBlocked)) 
			{
				pieceValue = boardState[selectedPieceRow-i][selectedPieceCol+i];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow-i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol+i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow-i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol+i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					topRightBlocked = true;
				}
				else
				{
					topRightBlocked = true;
				}
			}
			
			// if Bishop won't cross over the bottom right border.
			if (((selectedPieceRow+i > 7 || selectedPieceCol+i > 7) != true)  && !bottomRightBlocked) 
			{
				pieceValue = boardState[selectedPieceRow+i][selectedPieceCol+i];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow+i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol+i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow+i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol+i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					bottomRightBlocked = true;
				}
				else
				{
					bottomRightBlocked = true;
				}
			}
		}
		
		if (currentPossibleMoves != 0) // if there is a possible move...
		{
			
			int highestValueCapture = 0;
			int highestValueCaptureLoc = -1;
			
			for (int i = 0; i < currentPossibleMoves; i++)
			{
				if (possibleMoves[i][2] > highestValueCapture)
				{
					highestValueCapture = possibleMoves[i][2];
					highestValueCaptureLoc = i;
				}
			}
			
			// there is something to capture.
			if (highestValueCaptureLoc != -1)
			{
				capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
				capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
				
				capturableLocations[currentCaptureableArrayPosition][0] = possibleMoves[highestValueCaptureLoc][0];
				capturableLocations[currentCaptureableArrayPosition][1] = possibleMoves[highestValueCaptureLoc][1];
				capturableLocations[currentCaptureableArrayPosition][2] = possibleMoves[highestValueCaptureLoc][2];
				currentCaptureableArrayPosition++;			
				return;
			}
			
			// nothing to capture, try to move.
			int randMove = rand.nextInt(currentPossibleMoves);

			while (possibleMoves[randMove][0] == -100 || possibleMoves[randMove][2] != 0)
			{
				randMove = rand.nextInt(currentPossibleMoves);
			}
			
			decidedMovementRow = possibleMoves[randMove][0]; 
			decidedMovementCol = possibleMoves[randMove][1];
			noCapture = true;					
			return;
		}
		
		immobile = true;
		return;
	}

	void checkRookMoves()
	{
		boolean leftBlocked = false;
		boolean rightBlocked = false;
		boolean topBlocked = false;
		boolean bottomBlocked = false;
		
		int[][] possibleMoves = new int[15][3];
		Arrays.fill(possibleMoves[0], -100);
		Arrays.fill(possibleMoves[2], 0);
		int currentPossibleMoves = 0;
		int pieceValue;
		
		// check all directions
		for (int i = 1; i < 8; i++)
		{
			// if Rook won't cross over the Left border.
			if ((selectedPieceCol-i >= 0) && !leftBlocked) 
			{
				pieceValue = boardState[selectedPieceRow][selectedPieceCol-i];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol-i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol-i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					leftBlocked = true;
				}
				else
				{
					leftBlocked = true;
				}
			}
			
			// if Rook won't cross over the Right border.
			if ((selectedPieceCol+i <= 7) && !rightBlocked) 
			{
				pieceValue = boardState[selectedPieceRow][selectedPieceCol+i];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol+i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol+i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					rightBlocked = true;
				}
				else
				{
					rightBlocked = true;
				}
			}
			
			// if Rook won't cross over the Top border.
			if ((selectedPieceRow-i >= 0) && !topBlocked) 
			{
				pieceValue = boardState[selectedPieceRow-i][selectedPieceCol];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow-i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow-i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					topBlocked = true;
				}
				else
				{
					topBlocked = true;
				}
			}
			
			// if Rook won't cross over the Bottom border.
			if ((selectedPieceRow+i <= 7) && !bottomBlocked) 
			{
				pieceValue = boardState[selectedPieceRow+i][selectedPieceCol];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow+i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow+i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					bottomBlocked = true;
				}
				else
				{
					bottomBlocked = true;
				}
			}
		}
		
		if (currentPossibleMoves != 0) // if there is a possible move...
		{
			int highestValueCapture = 0;
			int highestValueCaptureLoc = -1;
			
			for (int i = 0; i < currentPossibleMoves; i++)
			{
				if (possibleMoves[i][2] > highestValueCapture)
				{
					highestValueCapture = possibleMoves[i][2];
					highestValueCaptureLoc = i;
				}
			}

			// there is something to capture.
			if (highestValueCaptureLoc != -1)
			{
				capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
				capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
				
				capturableLocations[currentCaptureableArrayPosition][0] = possibleMoves[highestValueCaptureLoc][0];
				capturableLocations[currentCaptureableArrayPosition][1] = possibleMoves[highestValueCaptureLoc][1];
				capturableLocations[currentCaptureableArrayPosition][2] = possibleMoves[highestValueCaptureLoc][2];
				currentCaptureableArrayPosition++;			
				return;
			}
			
			// nothing to capture, try to move.
			int randMove = rand.nextInt(currentPossibleMoves);

			while (possibleMoves[randMove][0] == -100 || possibleMoves[randMove][2] != 0)
			{
				randMove = rand.nextInt(currentPossibleMoves);
			}
			
			decidedMovementRow = possibleMoves[randMove][0]; 
			decidedMovementCol = possibleMoves[randMove][1];
			noCapture = true;					
			return;
		}
		
		immobile = true;
		return;
	}

	void checkQueenMoves()
	{
		boolean topLeftBlocked = false;
		boolean bottomLeftBlocked = false;
		boolean topRightBlocked = false;
		boolean bottomRightBlocked = false;
		boolean leftBlocked = false;
		boolean rightBlocked = false;
		boolean topBlocked = false;
		boolean bottomBlocked = false;
		
		int[][] possibleMoves = new int[30][3];
		Arrays.fill(possibleMoves[0], -100);
		Arrays.fill(possibleMoves[2], 0);
		int currentPossibleMoves = 0;
		int pieceValue;
		
		// check all directions
		for (int i = 1; i < 8; i++)
		{
			// if Queen won't cross over the top left border.
			if (((selectedPieceRow-i < 0 || selectedPieceCol-i < 0) != true) && !topLeftBlocked) 
			{
				pieceValue = boardState[selectedPieceRow-i][selectedPieceCol-i];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow-i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol-i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow-i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol-i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					topLeftBlocked = true;
				}
				else
				{
					topLeftBlocked = true;
				}
			}
			
			// if Queen won't cross over the bottom left border.
			if ((((selectedPieceRow+i < 0 || selectedPieceCol-i < 0) 
					|| (selectedPieceRow+i > 7 || selectedPieceCol-i > 7)) != true) && !bottomLeftBlocked) 
			{
				pieceValue = boardState[selectedPieceRow+i][selectedPieceCol-i];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow+i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol-i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow+i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol-i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					bottomLeftBlocked = true;
				}
				else
				{
					bottomLeftBlocked = true;
				}
			}
			
			// if Queen won't cross over the top right border.
			if ((((selectedPieceRow-i < 0 || selectedPieceCol+i < 0) 
					|| (selectedPieceRow-i > 7 || selectedPieceCol+i > 7)) != true && !topRightBlocked)) 
			{
				pieceValue = boardState[selectedPieceRow-i][selectedPieceCol+i];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow-i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol+i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow-i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol+i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					topRightBlocked = true;
				}
				else
				{
					topRightBlocked = true;
				}
			}
			
			// if Queen won't cross over the bottom right border.
			if (((selectedPieceRow+i > 7 || selectedPieceCol+i > 7) != true)  && !bottomRightBlocked) 
			{
				pieceValue = boardState[selectedPieceRow+i][selectedPieceCol+i];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow+i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol+i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow+i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol+i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					bottomRightBlocked = true;
				}
				else
				{
					bottomRightBlocked = true;
				}
			}
			
			// if Queen won't cross over the Left border.
			if ((selectedPieceCol-i >= 0) && !leftBlocked) 
			{
				pieceValue = boardState[selectedPieceRow][selectedPieceCol-i];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol-i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol-i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					leftBlocked = true;
				}
				else
				{
					leftBlocked = true;
				}
			}
			
			// if Queen won't cross over the Right border.
			if ((selectedPieceCol+i <= 7) && !rightBlocked) 
			{
				pieceValue = boardState[selectedPieceRow][selectedPieceCol+i];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol+i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol+i;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					rightBlocked = true;
				}
				else
				{
					rightBlocked = true;
				}
			}
			
			// if Queen won't cross over the Top border.
			if ((selectedPieceRow-i >= 0) && !topBlocked) 
			{
				pieceValue = boardState[selectedPieceRow-i][selectedPieceCol];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow-i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow-i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					topBlocked = true;
				}
				else
				{
					topBlocked = true;
				}
			}
			
			// if Queen won't cross over the Bottom border.
			if ((selectedPieceRow+i <= 7) && !bottomBlocked) 
			{
				pieceValue = boardState[selectedPieceRow+i][selectedPieceCol];
				
				if (pieceValue == 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow+i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				else if (pieceValue > 0)
				{
					possibleMoves[currentPossibleMoves][0] = selectedPieceRow+i;
					possibleMoves[currentPossibleMoves][1] = selectedPieceCol;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
					bottomBlocked = true;
				}
				else
				{
					bottomBlocked = true;
				}
			}
			
			
		}
		
		if (currentPossibleMoves != 0) // if there is a possible move...
		{
			int highestValueCapture = 0;
			int highestValueCaptureLoc = -1;
			
			for (int i = 0; i < currentPossibleMoves; i++)
			{
				if (possibleMoves[i][2] > highestValueCapture)
				{
					highestValueCapture = possibleMoves[i][2];
					highestValueCaptureLoc = i;
				}
			}

			// there is something to capture.
			if (highestValueCaptureLoc != -1)
			{
				capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
				capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
				
				capturableLocations[currentCaptureableArrayPosition][0] = possibleMoves[highestValueCaptureLoc][0];
				capturableLocations[currentCaptureableArrayPosition][1] = possibleMoves[highestValueCaptureLoc][1];
				capturableLocations[currentCaptureableArrayPosition][2] = possibleMoves[highestValueCaptureLoc][2];
				currentCaptureableArrayPosition++;			
				return;
			}
			
			// nothing to capture, try to move.
			int randMove = rand.nextInt(currentPossibleMoves);

			while (possibleMoves[randMove][0] == -100 || possibleMoves[randMove][2] != 0)
			{
				randMove = rand.nextInt(currentPossibleMoves);
			}
			
			decidedMovementRow = possibleMoves[randMove][0]; 
			decidedMovementCol = possibleMoves[randMove][1];
			noCapture = true;					
			return;
		}

		immobile = true;
		return;
	}
	
	void checkKingMoves()
	{	
		int[][] possibleMoves = new int[8][3];
		Arrays.fill(possibleMoves[0], -100);
		Arrays.fill(possibleMoves[2], 0);
		int currentPossibleMoves = 0;
		int pieceValue;
		
		// check all directions
		// if King won't cross over the top left border.
		if (((selectedPieceRow-1 < 0 || selectedPieceCol-1 < 0) != true)) 
		{
			pieceValue = boardState[selectedPieceRow-1][selectedPieceCol-1];
			
			if (pieceValue >= 0 && pieceValue != 10)
			{
				possibleMoves[currentPossibleMoves][0] = selectedPieceRow-1;
				possibleMoves[currentPossibleMoves][1] = selectedPieceCol-1;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the bottom left border.
		if ((((selectedPieceRow+1 < 0 || selectedPieceCol-1 < 0) 
				|| (selectedPieceRow+1 > 7 || selectedPieceCol-1 > 7)) != true)) 
		{
			pieceValue = boardState[selectedPieceRow+1][selectedPieceCol-1];
			
			if (pieceValue >= 0 && pieceValue != 10)
			{
				possibleMoves[currentPossibleMoves][0] = selectedPieceRow+1;
				possibleMoves[currentPossibleMoves][1] = selectedPieceCol-1;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the top right border.
		if ((((selectedPieceRow-1 < 0 || selectedPieceCol+1 < 0) 
				|| (selectedPieceRow-1 > 7 || selectedPieceCol+1 > 7)) != true)) 
		{
			pieceValue = boardState[selectedPieceRow-1][selectedPieceCol+1];
			
			if (pieceValue >= 0 && pieceValue != 10)
			{
				possibleMoves[currentPossibleMoves][0] = selectedPieceRow-1;
				possibleMoves[currentPossibleMoves][1] = selectedPieceCol+1;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the bottom right border.
		if (((selectedPieceRow+1 > 7 || selectedPieceCol+1 > 7) != true)) 
		{
			pieceValue = boardState[selectedPieceRow+1][selectedPieceCol+1];
			
			if (pieceValue >= 0 && pieceValue != 10)
			{
				possibleMoves[currentPossibleMoves][0] = selectedPieceRow+1;
				possibleMoves[currentPossibleMoves][1] = selectedPieceCol+1;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the Left border.
		if ((selectedPieceCol-1 >= 0)) 
		{
			pieceValue = boardState[selectedPieceRow][selectedPieceCol-1];
			
			if (pieceValue >= 0 && pieceValue != 10)
			{
				possibleMoves[currentPossibleMoves][0] = selectedPieceRow;
				possibleMoves[currentPossibleMoves][1] = selectedPieceCol-1;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the Right border.
		if ((selectedPieceCol+1 <= 7)) 
		{
			pieceValue = boardState[selectedPieceRow][selectedPieceCol+1];
			
			if (pieceValue >= 0 && pieceValue != 10)
			{
				possibleMoves[currentPossibleMoves][0] = selectedPieceRow;
				possibleMoves[currentPossibleMoves][1] = selectedPieceCol+1;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the Top border.
		if ((selectedPieceRow-1 >= 0)) 
		{
			pieceValue = boardState[selectedPieceRow-1][selectedPieceCol];
			
			if (pieceValue >= 0 && pieceValue != 10)
			{
				possibleMoves[currentPossibleMoves][0] = selectedPieceRow-1;
				possibleMoves[currentPossibleMoves][1] = selectedPieceCol;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the Bottom border.
		if ((selectedPieceRow+1 <= 7)) 
		{
			pieceValue = boardState[selectedPieceRow+1][selectedPieceCol];
			
			if (pieceValue >= 0 && pieceValue != 10)
			{
				possibleMoves[currentPossibleMoves][0] = selectedPieceRow+1;
				possibleMoves[currentPossibleMoves][1] = selectedPieceCol;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		if (currentPossibleMoves != 0) // if there is a possible move...
		{
			// prevent moving to a location in check.
			int[][] notCheckMoves = new int[8][3]; 
			Arrays.fill(notCheckMoves[0], -100);
			Arrays.fill(notCheckMoves[2], 0);
			int currentNotCheckMoves = 0;
			
			for (int i = 0; i < currentPossibleMoves; i++)
			{
				try
				{
					if (king.findPossibleCapture(possibleMoves[i][0], possibleMoves[i][1], -6) == null)
					{								
						notCheckMoves[currentNotCheckMoves][0] = possibleMoves[i][0];
						notCheckMoves[currentNotCheckMoves][1] = possibleMoves[i][1];
						notCheckMoves[currentNotCheckMoves][2] = possibleMoves[i][2];
						currentNotCheckMoves++;
						
					}
				}
				catch (NullPointerException e)
				{
					
				}
				
			}
			
			// if replacement isn't empty, but is different from original...
			if (currentNotCheckMoves != 0 && (currentPossibleMoves != currentNotCheckMoves))
			{
				possibleMoves = notCheckMoves;
				currentPossibleMoves = currentNotCheckMoves;
			}
	
			int highestValueCapture = 0;
			int highestValueCaptureLoc = -1;
			
			for (int i = 0; i < currentPossibleMoves; i++)
			{
				if (possibleMoves[i][2] > highestValueCapture)
				{
					highestValueCapture = possibleMoves[i][2];
					highestValueCaptureLoc = i;
				}
			}

			// there is something to capture.
			if (highestValueCaptureLoc != -1)
			{
				capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
				capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
				
				capturableLocations[currentCaptureableArrayPosition][0] = possibleMoves[highestValueCaptureLoc][0];
				capturableLocations[currentCaptureableArrayPosition][1] = possibleMoves[highestValueCaptureLoc][1];
				capturableLocations[currentCaptureableArrayPosition][2] = possibleMoves[highestValueCaptureLoc][2];
				currentCaptureableArrayPosition++;
				return;
			}
			
			// nothing to capture, try to move.
			int randMove = rand.nextInt(currentPossibleMoves);
			
			while (possibleMoves[randMove][0] == -100 || possibleMoves[randMove][2] != 0)
			{
				randMove = rand.nextInt(currentPossibleMoves);
			}
			
			decidedMovementRow = possibleMoves[randMove][0];
			decidedMovementCol = possibleMoves[randMove][1];

			noCapture = true;
			return;
		}
		
		immobile = true;
		return;
	}

	void checkJesterMoves()
	{
		int[][] possibleMoves = new int[8][3];
		Arrays.fill(possibleMoves[0], -100);
		Arrays.fill(possibleMoves[2], 0);
		int currentPossibleMoves = 0;
		int pieceValue;
		
		// if King won't cross over the top left border.
		if (((selectedPieceRow-1 < 0 || selectedPieceCol-1 < 0) != true)) 
		{
			int row = selectedPieceRow-1;
			int col = selectedPieceCol-1;			
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] >= 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the bottom left border.
		if ((((selectedPieceRow+1 < 0 || selectedPieceCol-1 < 0) 
				|| (selectedPieceRow+1 > 7 || selectedPieceCol-1 > 7)) != true)) 
		{
			int row = selectedPieceRow+1;
			int col = selectedPieceCol-1;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] >= 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the top right border.
		if ((((selectedPieceRow-1 < 0 || selectedPieceCol+1 < 0) 
				|| (selectedPieceRow-1 > 7 || selectedPieceCol+1 > 7)) != true)) 
		{
			int row = selectedPieceRow-1;
			int col = selectedPieceCol+1;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] >= 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the bottom right border.
		if (((selectedPieceRow+1 > 7 || selectedPieceCol+1 > 7) != true)) 
		{
			int row = selectedPieceRow+1;
			int col = selectedPieceCol+1;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] >= 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the Left border.
		if ((selectedPieceCol-1 >= 0)) 
		{
			int row = selectedPieceRow;
			int col = selectedPieceCol-1;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] >= 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the Right border.
		if ((selectedPieceCol+1 <= 7))
		{
			int row = selectedPieceRow;
			int col = selectedPieceCol+1;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] >= 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the Top border.
		if ((selectedPieceRow-1 >= 0)) 
		{
			int row = selectedPieceRow-1;
			int col = selectedPieceCol;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] >= 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if King won't cross over the Bottom border.
		if ((selectedPieceRow+1 <= 7)) 
		{
			int row = selectedPieceRow+1;
			int col = selectedPieceCol;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] >= 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		if (currentPossibleMoves != 0) // if there is a possible move...
		{
			int highestValueCapture = 0;
			int highestValueCaptureLoc = -1;
			
			for (int i = 0; i < currentPossibleMoves; i++)
			{
				if (possibleMoves[i][2] > highestValueCapture)
				{
					highestValueCapture = possibleMoves[i][2];
					highestValueCaptureLoc = i;
				}
			}

			// there is something to capture.
			if (highestValueCaptureLoc != -1)
			{
				capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
				capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
				
				capturableLocations[currentCaptureableArrayPosition][0] = possibleMoves[highestValueCaptureLoc][0];
				capturableLocations[currentCaptureableArrayPosition][1] = possibleMoves[highestValueCaptureLoc][1];
				capturableLocations[currentCaptureableArrayPosition][2] = possibleMoves[highestValueCaptureLoc][2];
				currentCaptureableArrayPosition++;			
				return;
			}
			
			// nothing to capture, try to move.
			int randMove = rand.nextInt(currentPossibleMoves);

			while (possibleMoves[randMove][0] == -100 || possibleMoves[randMove][2] != 0)
			{
				randMove = rand.nextInt(currentPossibleMoves);
			}
			
			decidedMovementRow = possibleMoves[randMove][0]; 
			decidedMovementCol = possibleMoves[randMove][1];
			noCapture = true;					
			return;
		}

		immobile = true;
		return;
	}
	
	void checkArcherMoves()
	{
		int[][] possibleMoves = new int[20][3];
		Arrays.fill(possibleMoves[0], -100);
		Arrays.fill(possibleMoves[2], 0);
		int currentPossibleMoves = 0;
		int pieceValue;
		
		// if Archer won't cross over the Top border.
		if ((selectedPieceRow-1 >= 0)) 
		{
			int row = selectedPieceRow-1;
			int col = selectedPieceCol;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] == 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if Archer won't cross over the top left border.
		if (((selectedPieceRow-1 < 0 || selectedPieceCol-1 < 0) != true)) 
		{
			int row = selectedPieceRow-1;
			int col = selectedPieceCol-1;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] == 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if Archer won't cross over the top right border.
		if ((((selectedPieceRow-1 < 0 || selectedPieceCol+1 > 7)) != true)) 
		{
			int row = selectedPieceRow-1;
			int col = selectedPieceCol+1;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] == 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
	
		if (selectedPieceRow+1 != 2) // if archer won't cross bottom border...
		{
			// if Archer won't cross over the Bottom border.
			if ((selectedPieceRow+1 <= 7)) 
			{
				int row = selectedPieceRow+1;
				int col = selectedPieceCol;
				pieceValue = boardState[row][col];
				
				if (boardState[row][col] == 0)
				{
					possibleMoves[currentPossibleMoves][0] = row;
					possibleMoves[currentPossibleMoves][1] = col;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
			}
			
			// if Archer won't cross over the bottom left border.
			if ((((selectedPieceRow+1 > 7 || selectedPieceCol-1 < 0)) != true)) 
			{
				int row = selectedPieceRow+1;
				int col = selectedPieceCol-1;
				pieceValue = boardState[row][col];
				
				if (boardState[row][col] == 0)
				{
					possibleMoves[currentPossibleMoves][0] = row;
					possibleMoves[currentPossibleMoves][1] = col;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
			}
			
			// if Archer won't cross over the bottom right border.
			if (((selectedPieceRow+1 > 7 || selectedPieceCol+1 > 7) != true)) 
			{
				int row = selectedPieceRow+1;
				int col = selectedPieceCol+1;
				pieceValue = boardState[row][col];
				
				if (boardState[row][col] == 0)
				{
					possibleMoves[currentPossibleMoves][0] = row;
					possibleMoves[currentPossibleMoves][1] = col;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
			}
		}
		
		// if Archer won't cross over the Left border.
		if ((selectedPieceCol-1 >= 0)) 
		{
			int row = selectedPieceRow;
			int col = selectedPieceCol-1;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] == 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if Archer won't cross over the Right border.
		if ((selectedPieceCol+1 <= 7))
		{
			int row = selectedPieceRow;
			int col = selectedPieceCol+1;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] == 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// Capturing
		
		// if a capture is available two squares in front...
		if (boardState[selectedPieceRow+2][selectedPieceCol] > 0)
		{
			int row = selectedPieceRow+2;
			int col = selectedPieceCol;
			pieceValue = boardState[row][col];
			
			possibleMoves[currentPossibleMoves][0] = row;
			possibleMoves[currentPossibleMoves][1] = col;
			possibleMoves[currentPossibleMoves][2] = pieceValue;
			currentPossibleMoves++;
		}
		
		// if a capture is available three squares in front...
		if (boardState[selectedPieceRow+3][selectedPieceCol] > 0)
		{
			int row = selectedPieceRow+3;
			int col = selectedPieceCol;
			pieceValue = boardState[row][col];
			
			possibleMoves[currentPossibleMoves][0] = row;
			possibleMoves[currentPossibleMoves][1] = col;
			possibleMoves[currentPossibleMoves][2] = pieceValue;
			currentPossibleMoves++;
		}
		
		if (selectedPieceCol+1 <= 7)
		{
			// if a capture is available two squares in front and one to the right...
			if (boardState[selectedPieceRow+2][selectedPieceCol+1] > 0)
			{
				int row = selectedPieceRow+2;
				int col = selectedPieceCol+1;
				pieceValue = boardState[row][col];
				
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
						
			// if a capture is available one square in front and one to the right...
			if (boardState[selectedPieceRow+1][selectedPieceCol+1] > 0)
			{
				int row = selectedPieceRow+1;
				int col = selectedPieceCol+1;
				pieceValue = boardState[row][col];
				
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}	
		}
		
		if (selectedPieceCol+2 <= 7)
		{
			// if a capture is available one square in front and two to the right...
			if (boardState[selectedPieceRow+1][selectedPieceCol+2] > 0)
			{
				int row = selectedPieceRow+1;
				int col = selectedPieceCol+2;
				pieceValue = boardState[row][col];
				
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}	
		}
		
		if (selectedPieceCol-1 >= 0)
		{
			// if a capture is available two squares in front and one to the right...
			if (boardState[selectedPieceRow+2][selectedPieceCol-1] > 0)
			{
				int row = selectedPieceRow+2;
				int col = selectedPieceCol-1;
				pieceValue = boardState[row][col];
				
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
			
			// if a capture is available one square in front and one to the right...
			if (boardState[selectedPieceRow+1][selectedPieceCol-1] > 0)
			{
				int row = selectedPieceRow+1;
				int col = selectedPieceCol-1;
				pieceValue = boardState[row][col];
				
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}	
		}
		
		if (selectedPieceCol-2 > 0)
		{
			// if a capture is available one square in front and two to the right...
			if (boardState[selectedPieceRow+1][selectedPieceCol-2] > 0)
			{
				int row = selectedPieceRow+1;
				int col = selectedPieceCol-2;
				pieceValue = boardState[row][col];
				
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}	
		}
		
		if (currentPossibleMoves != 0) // if there is a possible move...
		{
			int highestValueCapture = 0;
			int highestValueCaptureLoc = -1;
			
			for (int i = 0; i < currentPossibleMoves; i++)
			{
				if (possibleMoves[i][2] > highestValueCapture)
				{
					highestValueCapture = possibleMoves[i][2];
					highestValueCaptureLoc = i;
				}
			}

			// there is something to capture.
			if (highestValueCaptureLoc != -1)
			{
				capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
				capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
				
				capturableLocations[currentCaptureableArrayPosition][0] = possibleMoves[highestValueCaptureLoc][0];
				capturableLocations[currentCaptureableArrayPosition][1] = possibleMoves[highestValueCaptureLoc][1];
				capturableLocations[currentCaptureableArrayPosition][2] = possibleMoves[highestValueCaptureLoc][2];
				currentCaptureableArrayPosition++;			
				return;
			}
			
			// nothing to capture, try to move.
			int randMove = rand.nextInt(currentPossibleMoves);

			while (possibleMoves[randMove][0] == -100 || possibleMoves[randMove][2] != 0)
			{
				randMove = rand.nextInt(currentPossibleMoves);
			}
			
			decidedMovementRow = possibleMoves[randMove][0]; 
			decidedMovementCol = possibleMoves[randMove][1];
			noCapture = true;					
			return;
		}

		immobile = true;
		return;
	}
	
	void checkSquireMoves()
	{
		int[][] possibleMoves = new int[3][3];
		Arrays.fill(possibleMoves[0], -100);
		Arrays.fill(possibleMoves[2], 0);
		int currentPossibleMoves = 0;
		int pieceValue;
		
		// if Squire won't cross over the bottom left border.
		if (selectedPieceRow+1 <= 7 && selectedPieceCol-1 >= 0)
		{
			System.out.println("here9");
			int row = selectedPieceRow+1;
			int col = selectedPieceCol-1;
			pieceValue = boardState[row][col];
							
			if (boardState[row][col] == 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if Squire won't cross over the bottom right border.
		if (selectedPieceRow+1 <= 7 && selectedPieceCol+1 <= 7) 
		{
			System.out.println("here10");
			int row = selectedPieceRow+1;
			int col = selectedPieceCol+1;
			pieceValue = boardState[row][col];
							
			if (boardState[row][col] == 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// capture
		if (selectedPieceRow+1 <= 7)
		{
			
			int row = selectedPieceRow+1;
			int col = selectedPieceCol;
			pieceValue = boardState[row][col];
							
			if (boardState[row][col] > 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		if (currentPossibleMoves != 0) // if there is a possible move...
		{
			System.out.println("here12");
			int highestValueCapture = 0;
			int highestValueCaptureLoc = -1;
			
			for (int i = 0; i < currentPossibleMoves; i++)
			{
				if (possibleMoves[i][2] > highestValueCapture)
				{
					highestValueCapture = possibleMoves[i][2];
					highestValueCaptureLoc = i;
				}
			}

			// there is something to capture.
			if (highestValueCaptureLoc != -1)
			{
				capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
				capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
				
				capturableLocations[currentCaptureableArrayPosition][0] = possibleMoves[highestValueCaptureLoc][0];
				capturableLocations[currentCaptureableArrayPosition][1] = possibleMoves[highestValueCaptureLoc][1];
				capturableLocations[currentCaptureableArrayPosition][2] = possibleMoves[highestValueCaptureLoc][2];
				currentCaptureableArrayPosition++;			
				return;
			}
			
			// nothing to capture, try to move.
			int randMove = rand.nextInt(currentPossibleMoves);

			while (possibleMoves[randMove][0] == -100 || possibleMoves[randMove][2] != 0)
			{
				randMove = rand.nextInt(currentPossibleMoves);
			}
			
			decidedMovementRow = possibleMoves[randMove][0]; 
			decidedMovementCol = possibleMoves[randMove][1];
			noCapture = true;					
			return;
		}

		immobile = true;
		return;
	}

	void checkIncendiaryMoves()
	{
		int[][] possibleMoves = new int[4][3];
		Arrays.fill(possibleMoves[0], -100);
		Arrays.fill(possibleMoves[2], 0);
		int currentPossibleMoves = 0;
		int pieceValue;
		
		if (selectedPieceCol-1 >= 0)
		{
			int row = selectedPieceRow;
			int col = selectedPieceCol-1;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] >= 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if Incendiary won't cross over the Right border.
		if (selectedPieceCol+1 <= 7)
		{
			int row = selectedPieceRow;
			int col = selectedPieceCol+1;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] >= 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if Incendiary won't cross over the Top border.
		if ((selectedPieceRow+1 >= 0)) 
		{
			int row = selectedPieceRow+1;
			int col = selectedPieceCol;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] >= 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		// if Incendiary won't cross over the Bottom border.
		if (selectedPieceRow+1 <= 7) 
		{
			int row = selectedPieceRow+1;
			int col = selectedPieceCol;
			pieceValue = boardState[row][col];
			
			if (boardState[row][col] >= 0)
			{
				possibleMoves[currentPossibleMoves][0] = row;
				possibleMoves[currentPossibleMoves][1] = col;
				possibleMoves[currentPossibleMoves][2] = pieceValue;
				currentPossibleMoves++;
			}
		}
		
		if (currentPossibleMoves != 0) // if there is a possible move...
		{
			int highestValueCapture = 0;
			int highestValueCaptureLoc = -1;
			
			for (int i = 0; i < currentPossibleMoves; i++)
			{
				if (possibleMoves[i][2] > highestValueCapture)
				{
					highestValueCapture = possibleMoves[i][2];
					highestValueCaptureLoc = i;
				}
			}

			// there is something to capture.
			if (highestValueCaptureLoc != -1)
			{
				capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
				capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
				
				capturableLocations[currentCaptureableArrayPosition][0] = possibleMoves[highestValueCaptureLoc][0];
				capturableLocations[currentCaptureableArrayPosition][1] = possibleMoves[highestValueCaptureLoc][1];
				capturableLocations[currentCaptureableArrayPosition][2] = possibleMoves[highestValueCaptureLoc][2];
				currentCaptureableArrayPosition++;			
				return;
			}
			
			// nothing to capture, try to move.
			int randMove = rand.nextInt(currentPossibleMoves);

			while (possibleMoves[randMove][0] == -100 || possibleMoves[randMove][2] != 0)
			{
				randMove = rand.nextInt(currentPossibleMoves);
			}
			
			decidedMovementRow = possibleMoves[randMove][0]; 
			decidedMovementCol = possibleMoves[randMove][1];
			noCapture = true;					
			return;
		}

		immobile = true;
		return;
	}

	void checkDevourerMoves()
	{
		int[][] possibleMoves = new int[8][3];
		Arrays.fill(possibleMoves[0], -100);
		Arrays.fill(possibleMoves[2], 0);
		int currentPossibleMoves = 0;
		int pieceValue;
		
		boolean leftBlocked = false;
		boolean rightBlocked = false;
		boolean topBlocked = false;
		boolean bottomBlocked = false;
		
		for (int i = 1; i <= 2; i++)
		{
			// if Devourer won't cross over the Left border.
			if (selectedPieceCol-i >= 0 && !leftBlocked)
			{
				int row = selectedPieceRow;
				int col = selectedPieceCol-i;
				pieceValue = boardState[row][col];
				
				if (boardState[row][col] >= 0)
				{
					possibleMoves[currentPossibleMoves][0] = row;
					possibleMoves[currentPossibleMoves][1] = col;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				
				if (boardState[row][col] != 0)
				{
					leftBlocked = true;
				}
			}
			
			// if Devourer won't cross over the Right border.
			if (selectedPieceCol+i <= 7 && !rightBlocked)
			{
				int row = selectedPieceRow;
				int col = selectedPieceCol+i;
				pieceValue = boardState[row][col];
				
				if (boardState[row][col] >= 0)
				{
					possibleMoves[currentPossibleMoves][0] = row;
					possibleMoves[currentPossibleMoves][1] = col;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				
				if (boardState[row][col] != 0)
				{
					rightBlocked = true;
				}
			}
			
			// if Devourer won't cross over the Top border.
			if (selectedPieceRow-i >= 0  && !topBlocked) 
			{
				int row = selectedPieceRow-i;
				int col = selectedPieceCol;
				pieceValue = boardState[row][col];
				
				if (boardState[row][col] >= 0)
				{
					possibleMoves[currentPossibleMoves][0] = row;
					possibleMoves[currentPossibleMoves][1] = col;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}

				if (boardState[row][col] != 0)
				{
					topBlocked = true;
				}
			}
			
			// if Devourer won't cross over the Bottom border.
			if (selectedPieceRow+i <= 7 && !bottomBlocked) 
			{
				int row = selectedPieceRow+i;
				int col = selectedPieceCol;
				pieceValue = boardState[row][col];
				
				if (boardState[row][col] >= 0)
				{
					possibleMoves[currentPossibleMoves][0] = row;
					possibleMoves[currentPossibleMoves][1] = col;
					possibleMoves[currentPossibleMoves][2] = pieceValue;
					currentPossibleMoves++;
				}
				
				if (boardState[row][col] != 0)
				{
					bottomBlocked = true;
				}
			}
		}
		
		if (currentPossibleMoves != 0) // if there is a possible move...
		{
			int highestValueCapture = 0;
			int highestValueCaptureLoc = -1;
			
			for (int i = 0; i < currentPossibleMoves; i++)
			{
				if (possibleMoves[i][2] > highestValueCapture)
				{
					highestValueCapture = possibleMoves[i][2];
					highestValueCaptureLoc = i;
				}
			}

			// there is something to capture.
			if (highestValueCaptureLoc != -1)
			{
				capturingPieceLocations[currentCaptureableArrayPosition][0] = selectedPieceRow;
				capturingPieceLocations[currentCaptureableArrayPosition][1] = selectedPieceCol;
				
				capturableLocations[currentCaptureableArrayPosition][0] = possibleMoves[highestValueCaptureLoc][0];
				capturableLocations[currentCaptureableArrayPosition][1] = possibleMoves[highestValueCaptureLoc][1];
				capturableLocations[currentCaptureableArrayPosition][2] = possibleMoves[highestValueCaptureLoc][2];
				currentCaptureableArrayPosition++;			
				return;
			}
			
			// nothing to capture, try to move.
			int randMove = rand.nextInt(currentPossibleMoves);

			while (possibleMoves[randMove][0] == -100 || possibleMoves[randMove][2] != 0)
			{
				randMove = rand.nextInt(currentPossibleMoves);
			}
			
			decidedMovementRow = possibleMoves[randMove][0]; 
			decidedMovementCol = possibleMoves[randMove][1];
			noCapture = true;					
			return;
		}

		immobile = true;
		return;
	}
}
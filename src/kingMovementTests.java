import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class kingMovementTests {

	private static int board[][];
	private static King king;
	
	@BeforeEach
	void setUp() throws Exception {
		king = new King("white");
		king.setRow(5);
		king.setCol(4);
		
		board = new int[][] {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 6, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{4, 0, 0, 0, 0, 0, 0, 4}
		};
	}

	

	@Test
	void kingCanMoveLeft()
	{
		assertEquals(true, king.validMove(5, 3, board));		
	}
	
	@Test
	void kingCanMoveRight()
	{
		assertEquals(true, king.validMove(5, 5, board));
	}
	
	@Test
	void kingCanMoveForward()
	{
		assertEquals(true, king.validMove(4, 4, board));		
	}
	
	@Test
	void kingCanMoveBackwards()
	{
		assertEquals(true, king.validMove(6, 4, board));
	}
	
	@Test
	void kingCanMoveForwardLeft()
	{
		assertEquals(true, king.validMove(4, 3, board));		
	}
	
	@Test
	void kingCanMoveForwardRight()
	{
		assertEquals(true, king.validMove(4, 5, board));		
	}
	
	@Test
	void kingCanMoveBackwardsLeft()
	{
		assertEquals(true, king.validMove(6, 3, board));
	}
	
	@Test
	void kingCanMoveBackwardsRight()
	{
		assertEquals(true, king.validMove(6, 5, board));
	}
	
	@Test
	void kingCanPerformValidCastle()
	{
		Piece selectedPiece = new Rook("white");
		selectedPiece.setCol(7);
		selectedPiece.setRow(7);
		Piece previousPiece = king;
		assertEquals(true, king.validCastling(7, 7, board, selectedPiece, previousPiece));
	}
	
	@Test
	void kingCantPerformInValidCastleDueToNumOfMoves()
	{
		Piece selectedPiece = new Rook("white");
		selectedPiece.setCol(7);
		selectedPiece.setRow(7);
		selectedPiece.incrementNumMoves();
		Piece previousPiece = king;
		assertEquals(false, king.validCastling(7, 7, board, selectedPiece, previousPiece));
	}
	
	@Test
	void kingCantPerformInvalidCastleDueToIncorrectPiece()
	{
		Piece selectedPiece = new Queen("white");
		selectedPiece.setCol(7);
		selectedPiece.setRow(7);
		Piece previousPiece = king;
		assertEquals(false, king.validCastling(7, 7, board, selectedPiece, previousPiece));
	}
	
	@Test
	void kingCantPerformInValidCastleDueToBlockedPath()
	{
		Piece selectedPiece = new Rook("white");
		selectedPiece.setCol(7);
		selectedPiece.setRow(7);
		board[7][6] = 6;
		Piece previousPiece = king;
		assertEquals(false, king.validCastling(7, 7, board, selectedPiece, previousPiece));
	}
	
	
}

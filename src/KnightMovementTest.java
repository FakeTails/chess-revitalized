import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class KnightMovementTest {

	private static int board[][];
	private static Knight whiteKnight;
	private static Knight blackKnight;
		
	@BeforeAll
	static void PieceAndBoardSetUp()
	{
		whiteKnight = new Knight("white");
		whiteKnight.setRow(5);
		whiteKnight.setCol(3);
		
		blackKnight = new Knight("black");
		blackKnight.setRow(2);
		blackKnight.setCol(5);
		
		board = new int[][] {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, -2, 0, -1},
			{0, 0, 0, 0, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 2, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0}
		};
	}
	
	@Test
	void knightCanMoveForwardAndRight()
	{
		assertEquals(true,whiteKnight.validMove(3, 4, board));
	}
	
	@Test
	void knightCanMoveForwardAndLeft()
	{
		assertEquals(true,whiteKnight.validMove(3, 2, board));
	}
	
	@Test
	void knightCanMoveBackwardAndRight()
	{
		assertEquals(true,whiteKnight.validMove(7, 4, board));
	}

	@Test
	void knightCanMoveBackwardAndLeft()
	{
		assertEquals(true,whiteKnight.validMove(7, 2, board));
	}
	
	@Test
	void knightCanMoveRightAndForward()
	{
		assertEquals(true,whiteKnight.validMove(4, 5, board));
	}
	
	@Test
	void knightCanMoveRightAndBackward()
	{
		assertEquals(true,whiteKnight.validMove(6, 5, board));
	}
	
	@Test
	void knightCanMoveLeftAndForward()
	{
		assertEquals(true,whiteKnight.validMove(4, 1, board));
	}
	
	@Test
	void knightCanMoveLeftAndBackward()
	{
		assertEquals(true,whiteKnight.validMove(6, 1, board));
	}
	
	@Test
	void knightCanJumpOverSameColorPiece()
	{
		assertEquals(true,blackKnight.validMove(1, 7, board));
	}
	
	@Test
	void knightCanJumpOverOpponentPiece()
	{
		assertEquals(true,blackKnight.validMove(4, 6, board));
	}
	
	@Test
	void knightCannotMoveThreeSpacesToRight()
	{
		assertEquals(false,whiteKnight.validMove(5, 6, board));
	}
	
	@Test
	void knightCannotMoveOnDiagonal()
	{
		assertEquals(false,whiteKnight.validMove(7, 1, board));
	}
}

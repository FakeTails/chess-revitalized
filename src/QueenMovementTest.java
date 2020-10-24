import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class QueenMovementTest 
{

	private static int board[][];
	private static Queen queen;
	private boolean isValidMove;
	
	@BeforeAll
	static void init()
	{
		queen = new Queen("white");
		queen.setRow(4);
		queen.setCol(4);
		
		board = new int[][] {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 5, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 1, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0}
		};
	}
	
	@Test
	void queenCanMoveHorizontallyToTheLeft() 
	{
		isValidMove = queen.validMove(4, 2, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void queenCanMoveHorizontallyToTheRight() 
	{
		isValidMove = queen.validMove(4, 5, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void queenCanMoveVerticallyToTheTop() 
	{
		isValidMove = queen.validMove(2, 4, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void queenCanMoveVerticallyToTheBottom() 
	{
		isValidMove = queen.validMove(5, 4, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void queenCannotJumpOverPiecesHorizontallyToTheLeft()
	{
		isValidMove = queen.validMove(4, 0, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void queenCannotJumpOverPiecesHorizontallyToTheRight()
	{
		isValidMove = queen.validMove(4, 7, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void queenCannotJumpOverPiecesVerticallyToTheTop()
	{
		isValidMove = queen.validMove(0, 4, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void queenCannotJumpOverPiecesVerticallyToTheBottom()
	{
		isValidMove = queen.validMove(7, 4, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void queenCanMoveDiagonallyToTheTopLeft() 
	{
		isValidMove = queen.validMove(3, 3, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void queenCannotJumpOverPiecesDiagonallyToTheTopLeft() 
	{
		isValidMove = queen.validMove(1, 1, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void queenCanMoveDiagonallyToTheTopRight() 
	{
		isValidMove = queen.validMove(3, 5, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void queenCannotJumpOverPiecesDiagonallyToTheTopRight() 
	{
		isValidMove = queen.validMove(1, 7, board);
		assertEquals(false, isValidMove);
	
	}
	
	@Test
	void queenCanMoveDiagonallyToTheBottomLeft() 
	{
		isValidMove = queen.validMove(5, 3, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void queenCannotJumpOverPiecesDiagonallyToTheBottomLeft() 
	{
		isValidMove = queen.validMove(7, 1, board);
		assertEquals(false, isValidMove);
		
	}
	
	@Test
	void queenCanMoveDiagonallyToTheBottomRight() 
	{
		isValidMove = queen.validMove(5, 5, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void queenCannotJumpOverPiecesDiagonallyToTheBottomRight() 
	{
		isValidMove = queen.validMove(7, 7, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void queenMustMoveInStraightLines()
	{
		isValidMove = queen.validMove(2, 7, board);
		assertEquals(false, isValidMove);
		
		isValidMove = queen.validMove(2, 0, board);
		assertEquals(false, isValidMove);
		
		isValidMove = queen.validMove(6, 1, board);
		assertEquals(false, isValidMove);
		
		isValidMove = queen.validMove(6, 7, board);
		assertEquals(false, isValidMove);
		
		isValidMove = queen.validMove(5, 6, board);
		assertEquals(false, isValidMove);
		
		isValidMove = queen.validMove(5, 2, board);
		assertEquals(false, isValidMove);
	
		isValidMove = queen.validMove(2, 3, board);
		assertEquals(false, isValidMove);
		
		isValidMove = queen.validMove(2, 5, board);
		assertEquals(false, isValidMove);
		
	}
}

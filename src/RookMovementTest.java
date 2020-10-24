import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RookMovementTest 
{

	private static int board[][];
	private static Rook rook;
	private boolean isValidMove;
	
	@BeforeAll
	static void init()
	{
		rook = new Rook("white");
		rook.setRow(4);
		rook.setCol(4);
		
		board = new int[][] {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 1, 0, 0, 4, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0}
		};
	}
	
	@Test
	void rookCanMoveHorizontallyToTheLeft() 
	{
		isValidMove = rook.validMove(4, 2, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void rookCanMoveHorizontallyToTheRight() 
	{
		isValidMove = rook.validMove(4, 5, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void rookCanMoveVerticallyToTheTop() 
	{
		isValidMove = rook.validMove(2, 4, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void rookCanMoveVerticallyToTheBottom() 
	{
		isValidMove = rook.validMove(5, 4, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void rookCannotMoveDiagonallyToTheTopLeft()
	{
		isValidMove = rook.validMove(1, 1, board);
		assertEquals(false, isValidMove);
		
		isValidMove = rook.validMove(2, 2, board);
		assertEquals(false, isValidMove);
		
		isValidMove = rook.validMove(3, 3, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void rookCannotMoveDiagonallyToTheTopRight()
	{
		isValidMove = rook.validMove(3, 5, board);
		assertEquals(false, isValidMove);
		
		isValidMove = rook.validMove(2, 6, board);
		assertEquals(false, isValidMove);
		
		isValidMove = rook.validMove(1, 7, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void rookCannotMoveDiagonallyToTheBottomRight()
	{
		isValidMove = rook.validMove(5, 5, board);
		assertEquals(false, isValidMove);
		
		isValidMove = rook.validMove(6, 6, board);
		assertEquals(false, isValidMove);
		
		isValidMove = rook.validMove(7, 7, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void rookCannotMoveDiagonallyToTheBottomLeft()
	{
		isValidMove = rook.validMove(5, 3, board);
		assertEquals(false, isValidMove);
		
		isValidMove = rook.validMove(6, 2, board);
		assertEquals(false, isValidMove);
		
		isValidMove = rook.validMove(7, 1, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void rookCannotJumpOverPiecesHorizontallyToTheLeft()
	{
		isValidMove = rook.validMove(4, 0, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void rookCannotJumpOverPiecesHorizontallyToTheRight()
	{
		isValidMove = rook.validMove(4, 7, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void rookCannotJumpOverPiecesVerticallyToTheTop()
	{
		isValidMove = rook.validMove(0, 4, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void rookCannotJumpOverPiecesVerticallyToTheBottom()
	{
		isValidMove = rook.validMove(7, 4, board);
		assertEquals(false, isValidMove);
	}
}

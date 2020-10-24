import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BishopMovementTest 
{

	private static int board[][];
	private static Bishop bishop;
	private boolean isValidMove;
	
	@BeforeAll
	static void init()
	{
	
		bishop = new Bishop("white");
		bishop.setRow(4);
		bishop.setCol(4);
		
		board = new int[][] {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 3, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 1, 0},
			{0, 0, 0, 0, 0, 0, 0, 0}
		};
	}
	
	@Test
	void bishopCanMoveDiagonallyToTheTopLeft() 
	{
		isValidMove = bishop.validMove(3, 3, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void bishopCannotJumpOverPiecesDiagonallyToTheTopLeft() 
	{
		isValidMove = bishop.validMove(1, 1, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void bishopCanMoveDiagonallyToTheTopRight() 
	{
		isValidMove = bishop.validMove(3, 5, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void bishopCannotJumpOverPiecesDiagonallyToTheTopRight() 
	{
		isValidMove = bishop.validMove(1, 7, board);
		assertEquals(false, isValidMove);
	
	}
	
	@Test
	void bishopCanMoveDiagonallyToTheBottomLeft() 
	{
		isValidMove = bishop.validMove(5, 3, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void bishopCannotJumpOverPiecesDiagonallyToTheBottomLeft() 
	{
		isValidMove = bishop.validMove(7, 1, board);
		assertEquals(false, isValidMove);
		
	}
	
	@Test
	void bishopCanMoveDiagonallyToTheBottomRight() 
	{
		isValidMove = bishop.validMove(5, 5, board);
		assertEquals(true, isValidMove);
	}
	
	@Test
	void bishopCannotJumpOverPiecesDiagonallyToTheBottomRight() 
	{
		isValidMove = bishop.validMove(7, 7, board);
		assertEquals(false, isValidMove);
	
	}
	
	@Test
	void bishopCannotMoveHorizontallyToTheLeft()
	{
		isValidMove = bishop.validMove(4, 0, board);
		assertEquals(false, isValidMove);
		
		isValidMove = bishop.validMove(4, 1, board);
		assertEquals(false, isValidMove);
		
		isValidMove = bishop.validMove(4, 2, board);
		assertEquals(false, isValidMove);
		
		isValidMove = bishop.validMove(4, 3, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void bishopCannotMoveHorizontallyToTheRight()
	{
		isValidMove = bishop.validMove(4, 5, board);
		assertEquals(false, isValidMove);
		
		isValidMove = bishop.validMove(4, 6, board);
		assertEquals(false, isValidMove);
		
		isValidMove = bishop.validMove(4, 7, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void bishopCannotMoveVerticallyToTheTop()
	{
		isValidMove = bishop.validMove(0, 4, board);
		assertEquals(false, isValidMove);
		
		isValidMove = bishop.validMove(1, 4, board);
		assertEquals(false, isValidMove);
		
		isValidMove = bishop.validMove(2, 4, board);
		assertEquals(false, isValidMove);
		
		isValidMove = bishop.validMove(3, 4, board);
		assertEquals(false, isValidMove);
	}
	
	@Test
	void bishopCannotMoveVerticallyToTheBottom()
	{
		isValidMove = bishop.validMove(5, 4, board);
		assertEquals(false, isValidMove);
		
		isValidMove = bishop.validMove(6, 4, board);
		assertEquals(false, isValidMove);
		
		isValidMove = bishop.validMove(7, 4, board);
		assertEquals(false, isValidMove);
	}
}

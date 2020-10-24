import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SquireMovementTest {

	private Squire squire;
	private int board[][];
	@BeforeEach
	void setUp() throws Exception {
		squire = new Squire("white");
		squire.setRow(5);
		squire.setCol(4);
		
		board = new int[][] {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 7, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{4, 0, 0, 0, 0, 0, 0, 4}
		};
	}

	
	@Test
	void canMoveForwardRight()
	{		
		assertEquals(true, squire.validMove(4, 5, board));
	}

	@Test
	void canMoveForwardLeft()
	{		
		assertEquals(true, squire.validMove(4, 3, board));
	}
	
	@Test
	void canCaptureForwards()
	{
		assertEquals(true, squire.validMove(4, 4, board));
	}
	
	@Test
	void cantCaptureDiagonal()
	{
		board[4][5] = 1;
		assertEquals(false, squire.validMove(4, 5, board));
	}
	
	@Test
	void cantMoveBackwards()
	{
		assertEquals(false, squire.validMove(6, 3, board));
	}
}

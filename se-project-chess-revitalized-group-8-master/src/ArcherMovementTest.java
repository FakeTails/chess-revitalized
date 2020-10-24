import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArcherMovementTest {
	private int board[][];
	private Archer archer;

	@BeforeEach
	void setUp() throws Exception {
		archer = new Archer("white");
		archer.setRow(7);
		archer.setCol(4);
		
		board = new int[][] {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{4, 0, 0, 0, 8, 0, 0, 4}
		};
	}

	
	@Test
	void archerCanMoveLeft()
	{
		assertEquals(true, archer.validMove(7, 3, board));		
	}
	
	@Test
	void archerCanMoveRight()
	{
		assertEquals(true, archer.validMove(7, 5, board));
	}
	
	@Test
	void archerCanMoveForward()
	{
		assertEquals(true, archer.validMove(6, 4, board));		
	}
	
	@Test
	void archerCanMoveBackwards()
	{
		archer.setRow(6);
		assertEquals(true, archer.validMove(7, 4, board));
	}
	
	@Test
	void archerCanMoveForwardLeft()
	{
		archer.setRow(7);
		assertEquals(true, archer.validMove(6, 3, board));		
	}
	
	@Test
	void archerCanMoveForwardRight()
	{
		archer.setRow(7);
		assertEquals(true, archer.validMove(6, 5, board));		
	}
	
	@Test
	void archerCanMoveBackwardsLeft()
	{
		archer.setRow(6);
		assertEquals(true, archer.validMove(7, 3, board));
	}
	
	@Test
	void archerCanMoveBackwardsRight()
	{
		archer.setRow(6);
		assertEquals(true, archer.validMove(7, 5, board));
	}
	
	@Test
	void archerCantMovePastHomeRows()
	{
		archer.setRow(6);
		assertEquals(false, archer.validMove(5, 4, board));
	}
	
	@Test
	void archerCanCaptureFrom2SpacesAway()
	{
		board[5][4] = 1;
		assertEquals(true,archer.validCapture(5, 4, board));
	}
	
	@Test
	void archerCanCaptureFrom3SpacesAway()
	{
		board[4][4] = 1;
		assertEquals(true,archer.validCapture(4, 4, board));
	}
	
	@Test
	void archerCantCaptureFrom1SpaceAway()
	{
		board[6][4] = 1;
		assertEquals(false,archer.validCapture(6, 4, board));
	}

}

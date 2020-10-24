import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncendiaryMovementTest {

	private Incendiary incendiary;
	private int board[][];

	@BeforeEach
	void setUp() throws Exception {
		incendiary = new Incendiary("white");
		incendiary.setRow(5);
		incendiary.setCol(4);
		
		board = new int[][] {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 10, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{4, 0, 0, 0, 0, 0, 0, 4}
		};
	}

	

	@Test
	void incendiaryCanMoveLeft()
	{
		assertEquals(true, incendiary.validMove(5, 3, board));		
	}
	
	@Test
	void incendiaryCanMoveRight()
	{
		assertEquals(true, incendiary.validMove(5, 5, board));
	}
	
	@Test
	void incendiaryCanMoveForward()
	{
		assertEquals(true, incendiary.validMove(4, 4, board));		
	}
	
	@Test
	void incendiaryCanMoveBackwards()
	{
		assertEquals(true, incendiary.validMove(6, 4, board));
	}
}

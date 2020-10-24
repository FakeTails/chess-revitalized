import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DevourerMovementTest {

	private Devourer incendiary;
	private int board[][];

	@BeforeEach
	void setUp() throws Exception {
		incendiary = new Devourer("white");
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
	
	@Test
	void incendiaryCanMoveTwoSpacesLeft()
	{
		assertEquals(true, incendiary.validMove(5, 2, board));		
	}
	
	@Test
	void incendiaryCanMoveTwoSpacesRight()
	{
		assertEquals(true, incendiary.validMove(5, 6, board));
	}
	
	@Test
	void incendiaryCanMoveTwoSpacesForward()
	{
		assertEquals(true, incendiary.validMove(3, 4, board));		
	}
	
	@Test
	void incendiaryCanMoveTwoSpacesBackwards()
	{
		assertEquals(true, incendiary.validMove(7, 4, board));
	}
}

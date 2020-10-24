import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PawnTest {

	private static int board[][];
	private static Pawn whitePawn;
	private static Pawn blackPawn;
	
    @BeforeEach
	void setUp() throws Exception {
		whitePawn = new Pawn("white");
		whitePawn.setRow(6);
		whitePawn.setCol(4);
		
		blackPawn = new Pawn("black");
		blackPawn.setRow(1);
		blackPawn.setCol(4);
		board = new int[][] {
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, -1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0}
		};
	}

	

	@Test
	void pawnCanMoveLeft()
	{
		assertEquals(false, whitePawn.validMove(5, 3, board));
		assertEquals(false, blackPawn.validMove(1, 3, board));
		
	}
	
	@Test
	void pawnCanMoveRight()
	{
		assertEquals(false, whitePawn.validMove(5, 5, board));
		assertEquals(false, blackPawn.validMove(1, 5, board));
	}
	
	@Test
	void pawnCanMoveForward()
	{
		assertEquals(true, whitePawn.validMove(4, 4, board));	
		assertEquals(true, blackPawn.validMove(2, 4, board));	
	}
	
	@Test
	void pawnCanMoveBackwards()
	{
		assertEquals(false, whitePawn.validMove(6, 4, board));
		assertEquals(false, blackPawn.validMove(0, 4, board));
	}
	
	@Test
	void pawnCanMoveForwardLeft()
	{
		assertEquals(false, whitePawn.validMove(4, 3, board));	
		assertEquals(false, blackPawn.validMove(2, 3, board));
	}
	
	@Test
	void pawnCanMoveForwardRight()
	{
		assertEquals(false, whitePawn.validMove(4, 5, board));	
		assertEquals(false, blackPawn.validMove(2, 5, board));	
	}
	
	@Test
	void pawnCanMoveBackwardsLeft()
	{
		assertEquals(false, whitePawn.validMove(6, 3, board));
		assertEquals(false, blackPawn.validMove(0, 3, board));
	}
	
	@Test
	void pawnCanMoveBackwardsRight()
	{
		assertEquals(false, whitePawn.validMove(6, 5, board));
		assertEquals(false, blackPawn.validMove(0, 5, board));
	}	
	
	@Test
	void pawnCanJumpTwoTiles()
	{
		assertEquals(true, whitePawn.validMove(4, 4, board));
		assertEquals(true, blackPawn.validMove(3, 4, board));
	}	
}

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PlayerTest {

	private static Player player;
	
	@BeforeAll
	static void createPlayer()
	{
		player = new Player("Player 1", null, "10:00", null);
	}
	
	@Test
	void playerCreatedSuccessfully() {
		assertEquals(player.getName(), "Player 1");
		assertEquals(player.getTimeLimit(), "10:00");
	}
	
	@Test
	void playerMovedAndMovesIncrimented() {
		int moves = player.getMoves();
		
		player.moved();
		
		assertEquals(player.getMoves(), moves + 1);
	}
	
	@Test
	void playerMovedAnTurnEnded() {
		player.setTurn(true);

		assertEquals(player.getTurn(), true);
		
		player.moved();
		
		assertEquals(player.getTurn(), false);
	}

}

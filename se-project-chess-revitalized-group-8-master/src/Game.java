import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

public class Game extends JFrame implements java.io.Serializable
{
	boolean isDraftGame;
	boolean computerPlaying = false;
	public Game(boolean timedMatch, String timeLimit, boolean whiteMovesFirst, boolean computerOpponent, boolean draftPhase, boolean highlighting)
	{
		// Display a title.
		setTitle("Chess: Revitalized");
		computerPlaying = computerOpponent;
        // Specify an action for the close button.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set size
		//setMinimumSize(new Dimension(620,530));
		
		if(draftPhase)
		{
			setSize(860, 530);
		}
		else
		{
			setSize(620,530);
		}
		
		setResizable(false);
		
    // Create Game interface Which holds the game and its functions.
		GameInterface gameInterface = new GameInterface(timedMatch, timeLimit, computerOpponent, whiteMovesFirst, this, draftPhase, highlighting);

    // Add components.
    add(gameInterface);
    setVisible(true);

	}
	
	boolean isDraftGame()
	{
		return isDraftGame;
	}
	
	void load(GameInterface gameInterface)
	{
		add(gameInterface);
		setVisible(true);
	}
	
	void gameOver(boolean winner, String playerName)
	{
		StatsTracker tracker = new StatsTracker();
		
		if (playerName.equalsIgnoreCase("Player 1") && computerPlaying == false)
        {
            tracker.addWin(playerName);
            tracker.addLoss("Computer");
            System.out.println("p1 win comp false");
        }
		else if (playerName.equalsIgnoreCase("Player 1") && computerPlaying == true)
        {
            tracker.addWin(playerName);
            tracker.addLoss("Player 2");
            System.out.println("p1 win computer true");
        }
        else if (playerName.equalsIgnoreCase("Player 2"))
        {
            tracker.addWin(playerName);
            tracker.addLoss("Player 1");
        }
        else if (playerName.equalsIgnoreCase("Computer"))
        {        	
        		tracker.addWin(playerName);
                tracker.addLoss("Player 1");
                System.out.println("PC WIN");
        }
		
		final JFrame gameOverFrame = new JFrame();
		gameOverFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JDialog gameOverDialog = new JDialog(gameOverFrame, true);
		gameOverDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		gameOverDialog.setTitle("Gameover!");
		gameOverDialog.setLayout(new GridLayout(3, 1));
		
		JLabel gameOverText = new JLabel(playerName + " wins!", SwingConstants.CENTER);
		gameOverText.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		if (!winner)
		{
			gameOverText.setText("Time expired for " + playerName + "!");
		}
		
		JButton playAgain = new JButton("Return to Menu");
        JButton exit = new JButton("Exit");

		playAgain.addActionListener(l -> {
        	this.dispose();
        	
        	gameOverDialog.dispose();
        	
        	new Menu(); // restart menu for new game.
        	
        	return;
        });
		
		exit.addActionListener(l -> {
        	System.exit(0);
    	});
	
        gameOverDialog.add(gameOverText);
        gameOverDialog.add(playAgain);
        gameOverDialog.add(exit);
		gameOverDialog.pack();
		gameOverDialog.setLocationRelativeTo(this);
		gameOverDialog.setVisible(true);
	}
}
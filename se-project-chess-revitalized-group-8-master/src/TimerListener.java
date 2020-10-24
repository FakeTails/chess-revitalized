import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

class TimerListener implements ActionListener, java.io.Serializable {

	private int seconds = 00;
	private int minutes = 00;
	private JLabel timerLabel;
	private Player player;
	
	TimerListener(Player player, JLabel timerLabel, int minutes, int seconds)
    {
		this.player = player;
    	this.timerLabel = timerLabel;
    	this.minutes = minutes;
    	this.seconds = seconds;
    	
    	
    }
	
    public void actionPerformed(ActionEvent e) {
        String text = String.format("%02d:%02d",
                minutes, seconds);
            
        if (seconds == 0)
	    {
	    	minutes--;
	    	seconds = 59;
	    }
    	else
    	{
    		seconds--;
    	}
        
        // setting the time to the label that was created earlier.
    	timerLabel.setText(text);
    	if (text.equalsIgnoreCase("00:00"))
    	{
    		player.gameOver();
    	}
    }
}

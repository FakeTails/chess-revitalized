import javax.swing.JLabel;
import javax.swing.Timer;

class Player implements java.io.Serializable{
    	
	private int numMoves = 0;
	private String name;
	private boolean playersTurn = true;
	private final int TIME_DELAY = 1000; // milliseconds
	private Timer timer;
	private String timeLimit;
	private GameInterface gameInterface;
	private TimerListener timerListener;

	public Player(String name, JLabel infoPanel, String timeLimit, GameInterface gameInterface)
	{
		this.name = name;
		this.timeLimit = timeLimit;
		this.gameInterface = gameInterface;
		
		String[] times = timeLimit.split(":");
		int minutes = Integer.parseInt(times[0]);
		int seconds = Integer.parseInt(times[1]);
		
		timerListener = new TimerListener(this, infoPanel, minutes, seconds);
		
		// Create timer and attach to Time Panel for output.
		timer = new Timer(TIME_DELAY, timerListener );
	}
	
	void moved()
	{
		numMoves++;
		playersTurn = false;
	}
	
	int getMoves()
	{
		return numMoves;
	}
	
	Timer getTime()
	{
		return timer;
	}
	
	void setTurn(Boolean turn)
	{
		playersTurn = turn;
	}
	
	Boolean getTurn()
	{
		return playersTurn;
	}
	
	String getName()
	{
		return name;
	}
	
	public String getTimeLimit()
	{
		return timeLimit;
	}
		
	public void gameOver()
	{
		gameInterface.gameOver(false, name);
	}
}
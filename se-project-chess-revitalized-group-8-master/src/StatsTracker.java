import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class StatsTracker {
	private static int p1Wins = 0;
	private static int p2Wins = 0;
	private static int pcWins = 0;
	private static int p1Loss = 0;
	private static int p2Loss = 0;
	private static int pcLoss = 0;
	private static int p1Moves = 0;
	private static int p2Moves = 0;
	private static int pcMoves = 0;
	public StatsTracker() {
		loadStats();
	}
	public void addWin(String player)
	{
		
		if (player.equalsIgnoreCase("Player 1")) {
			p1Wins++;
		}
		else if (player.equalsIgnoreCase("Player 2")) {
			p2Wins++;
		}
		else if (player.equalsIgnoreCase("Computer")) {
			pcWins++;			
		}
		System.out.println(""+p1Wins+" " + p2Wins + " " + pcWins);
		printToFile();	
		
	}
	public void addLoss(String player)
	{
		
		if (player.equalsIgnoreCase("Player 1")) {
			p1Loss++;
		}
		else if (player.equalsIgnoreCase("Player 2")) {
			p2Loss++;
		}
		else if (player.equalsIgnoreCase("Computer")) {
			pcLoss++;
		}
		System.out.println("Losses "+p1Loss+" " + p2Loss + " " + pcLoss);
		printToFile();		
	}
	public static void addMove(String player)
	{
		if (player.equalsIgnoreCase("Player 1")) {
			p1Moves++;
			}
			else if (player.equalsIgnoreCase("Player 2")) {
				p2Moves++;
			}
			else if (player.equalsIgnoreCase("Computer")) {
				pcMoves++;
			}
		printToFile();
		
	}

	public int getWin(String player)
	{
		if (player.equalsIgnoreCase("Player 1")) {
				return p1Wins;
		}
		else if (player.equalsIgnoreCase("Player 2")) {
				return p2Wins;
		}
		else  {
				return pcWins;
		}
	}
	public int getLoss(String player)
	{
		if (player.equalsIgnoreCase("Player 1")) {
				return p1Loss;
		}
		else if (player.equalsIgnoreCase("Player 2")) {
				return p2Loss;
		}
		else  {
				return pcLoss;
		}
	}
	
	static void printToFile()
	{
		//loadStats()   // loading status before upading it to make sure to get the latest ones.
		try // saving it into a file
		{
			FileOutputStream outfs = new FileOutputStream("stats.txt");
			PrintWriter out = new PrintWriter(outfs);
			out.printf("Player 1,"+p1Wins+","+p1Loss+"\n");
			out.printf("Player 2,"+p2Wins+","+p2Loss+"\n");
			out.printf("Computer,"+pcWins+","+pcLoss+"\n");
			System.out.println("Data saved to file.");
			out.flush();
		} catch (Exception e) {
			System.out.println(e.getClass().getName());
			System.out.println(e.getMessage());
		}
	}
	
	static void loadStats()
	{
		try {
			FileInputStream infs = new FileInputStream("stats.txt");
			Scanner in = new Scanner(infs);			
			while (in.hasNextLine()) {
				String inputLine = in.nextLine();
				String[] inputParse = inputLine.split(","); // splitting to get loot name and value
				if (inputParse[0].equalsIgnoreCase("Player 1")) {
					p1Wins = Integer.parseInt(inputParse[1]);
					p1Loss = Integer.parseInt(inputParse[2]);
				}
				else if (inputParse[0].equalsIgnoreCase("Player 2")) {
					p2Wins = Integer.parseInt(inputParse[1]);
					p2Loss = Integer.parseInt(inputParse[2]);
				}
				else if (inputParse[0].equalsIgnoreCase("Computer")) {
					pcWins = Integer.parseInt(inputParse[1]);
					pcLoss = Integer.parseInt(inputParse[2]);
				}

			}
		}
		catch (Exception e)
		{
			System.out.print(e.getMessage());
		}
	}
	  
		
	

}

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


class GameInformation extends JPanel implements java.io.Serializable{
	
	private Timer timer;
	private Player player1;
	private Player player2;
	private String currentPlayer = "";
	transient private GameInterface gameInterface;
	
	private JPanel player1Panel;
	private JLabel player1TimeLabel;
	private JLabel player2TimeLabel;
	private JPanel player2Panel;
	private JButton saveJButton;
	
	private boolean timedMatch;
	private boolean whiteMovesFirst;
	private boolean draftPhase;
	 
	GameInformation(Boolean timedMatch, String timeLimit, boolean whiteMovesFirst, boolean draftPhase, GameInterface gameInterface)
	{
		this.timedMatch = timedMatch;
		this.whiteMovesFirst = whiteMovesFirst;
		this.draftPhase = draftPhase;
		setLayout(new GridLayout(14,0));
		
		player1Panel = new JPanel();
		player2Panel = new JPanel();
		
		this.gameInterface = gameInterface;
		
		saveJButton = new JButton("Save");
		saveJButton.setBorder(new LineBorder(Color.WHITE));
		addSaveActionListener();
		if(draftPhase == true)
		{
			saveJButton.setEnabled(false);
		}
					
		if (whiteMovesFirst) // currently player 1 is always white.
		{
			currentPlayer = "Player 1";
		}
		else
		{
			currentPlayer = "Player 2";
		}
		
		if (timedMatch)
		{
			player1TimeLabel = new JLabel(timeLimit, SwingConstants.CENTER);
			player1TimeLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
			
			player2TimeLabel = new JLabel(timeLimit, SwingConstants.CENTER);
			player2TimeLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
			
			addTimedComponets();
		}
		else
		{
			addUntimedComponets();
		}
	}
	 
	private void addTimedComponets()
	{
		// Player 2 name panel
		player2Panel.add(new JLabel("Player 2", SwingConstants.CENTER));
		add(player2Panel);

		// Player 2 running timer label
		add(player2TimeLabel);
		
		addExtraComponets(false, null, null);		
		
		// Player 1 running timer label
		add(player1TimeLabel);
		
		// Player 1 name panel
		player1Panel.add(new JLabel("Player 1", SwingConstants.CENTER));
		add(player1Panel);
		
		// Set background of current players turn
		if (currentPlayer.equalsIgnoreCase("Player 1"))
		{
			player1Panel.setBackground(Color.LIGHT_GRAY);
		}
		else
		{
			player2Panel.setBackground(Color.LIGHT_GRAY);
		}
	}
	
	private void addUntimedComponets()
	{
		// Player 2 name panel
		player2Panel.add(new JLabel("Player 2", SwingConstants.CENTER));
		add(player2Panel);
		
		addExtraComponets(false, null, null);
		
		// Player 1 name panel
		player1Panel.add(new JLabel("Player 1", SwingConstants.CENTER));			
		add(player1Panel);
		
		// Set background of current players turn
		if (currentPlayer.equalsIgnoreCase("Player 1"))
		{
			player1Panel.setBackground(Color.LIGHT_GRAY);
		}
		else
		{
			player2Panel.setBackground(Color.LIGHT_GRAY);
		}
	}
	
	void addExtraComponets(boolean playerInCheck, Player player1, Player player2)
	{
		if(draftPhase == false)
		{
			//saveJButton.setEnabled( true );
		}
		if (timedMatch)
		{
			if (playerInCheck == false)
			{
				// padding panels to make player timers appear on their sides
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel().add(saveJButton));
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
			}
			else
			{
				addCheckComponets(player1, player2);
			}
		}
		else
		{
			if (playerInCheck == false)
			{
				// padding panels to make player names appear on their sides
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel().add(saveJButton));
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
			}
			else
			{
				addCheckComponets(player1, player2);				
			}
		}
		
	}
	
	void addSaveActionListener()
	{
		saveJButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (timedMatch)
				{
					timer.stop();
				}
				
				
				// parent component of the dialog
				JFrame parentFrame = new JFrame();
				
				File currentDirectory = new File(System.getProperty("user.dir"));
				JFileChooser fileChooser = new JFileChooser(currentDirectory, FileSystemView.getFileSystemView());
				
				fileChooser.setDialogTitle("Choose directory to save in");   
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int userSelection = fileChooser.showSaveDialog(parentFrame);
				
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    String fileName = fileToSave.getAbsolutePath() + "\\chessgame.ser";
				    System.out.println("Save as file: " + fileName);
				    
				    try
				    {
				    	FileOutputStream fileOut = new FileOutputStream(fileName);						    
						ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
						objectOut.writeObject(gameInterface);
						
						fileOut.close();
						objectOut.close();
						
						if (timedMatch)
						{
							timer.start();
						}
					} 
				    catch (IOException e1) 
				    {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    
				}
				System.out.println("Saved");
			}
	
		});
	}
	
	void addCheckComponets(Player player1, Player player2)
	{
		JPanel blackCheckPanel = new JPanel();
		blackCheckPanel.add(new JLabel("Check", SwingConstants.CENTER));
		blackCheckPanel.setBackground(Color.GRAY);
		
		JPanel whiteCheckPanel = new JPanel();
		whiteCheckPanel.add(new JLabel("Check", SwingConstants.CENTER));
		whiteCheckPanel.setBackground(Color.GRAY);
		
		if (timedMatch)
		{
			if (player1 != null && player2 != null) // both in check
			{
				add(blackCheckPanel);
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel().add(saveJButton));
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(whiteCheckPanel);
			}
			else if (player1 != null) // player 1 in check
			{
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel().add(saveJButton));
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(whiteCheckPanel);
			}
			else if (player2 != null) // player 2 in check
			{
				add(blackCheckPanel);			
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel().add(saveJButton));
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
			}
		}
		else	
		{
			if (player1 != null && player2 != null) // both in check
			{
				add(blackCheckPanel);
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel().add(saveJButton));
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(whiteCheckPanel);
			}
			else if (player1 != null) // player 1 in check
			{
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel().add(saveJButton));
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(whiteCheckPanel);
			}
			else if (player2 != null) // player 2 in check
			{
				add(blackCheckPanel);				
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel().add(saveJButton));
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
			}
		}
	}

	void startTimer()
	{
		if (timer != null)
		{
			timer.start();
		}
	}
	
	void stopTimer()
	{
		timer.stop();
	}
	
	void loadTimer(GameInterface gameInterface)
	{
		this.gameInterface = gameInterface;
		addSaveActionListener();
		
		if(timedMatch)
		{			
			if(currentPlayer == player1.getName())
				timer = player1.getTime();
			else
				timer = player2.getTime();
			
			timer.start();			
		}
	}
	
	void changeToPlayersTimer(Player player)
	{
		setCurrentPlayer(player);
		
		if (timer != null)
		{
			timer.stop();
		}
		timer = player.getTime();
		timer.start();
		
		switchHighlightedPlayerName();
	}
	
	void switchHighlightedPlayerName()
	{
		if (currentPlayer.equalsIgnoreCase("Player 1"))
		{
			player1Panel.setBackground(Color.LIGHT_GRAY);
			player2Panel.setBackground(Color.WHITE);
		}
		else
		{
			player1Panel.setBackground(Color.WHITE);
			player2Panel.setBackground(Color.LIGHT_GRAY);
		}
	}
	
	JLabel getPlayersTimerLabel(String player)
	{
		if (player.equalsIgnoreCase("Player 2"))
		{
			return player2TimeLabel;
		}
		else
		{
			return player1TimeLabel;
		}
		
	}
	
	void setCurrentPlayer(Player player)
	{
		currentPlayer = player.getName();
	}
	
	void gameOver()
	{
		timer.stop();
	}
	
	void playerInCheck(boolean playerInCheck, Player player1, Player player2)
	{	
		if (timedMatch)
		{
			add(player2Panel);
			add(player2TimeLabel);
			addExtraComponets(playerInCheck, player1, player2);
			add(player1TimeLabel);
			add(player1Panel);
		}
		else
		{
			add(player2Panel);
			addExtraComponets(playerInCheck, player1, player2);
			add(player1Panel);
		}
	}

	void passPlayers(Player player1, Player player2)
	{
		this.player1 = player1;
		this.player2 = player2;
		
		if (player2.getName().equalsIgnoreCase("Computer"))
		{
			computerOpponentSetup();
		}
	}
	
	void setDraftPhase(boolean draftPhase)
	{
		this.draftPhase = draftPhase;
	}
	
	void computerOpponentSetup()
	{
		if (whiteMovesFirst)
		{
			player2Panel.removeAll();
			player2Panel.add(new JLabel("Computer", SwingConstants.CENTER));
			add(player2Panel);
			
			if (timedMatch)
			{
				// Player 2 running timer label
				add(player2TimeLabel);
				
				addExtraComponets(false, null, null);		
				
				// Player 1 running timer label
				add(player1TimeLabel);
				
				// Player 1 name panel
				add(player1Panel);
				
				// Set background of current players turn
				if (currentPlayer.equalsIgnoreCase("Player 1"))
				{
					player1Panel.setBackground(Color.LIGHT_GRAY);
				}
				else
				{
					player2Panel.setBackground(Color.LIGHT_GRAY);
				}
			}
			else
			{
				addExtraComponets(false, null, null);		
				
				// Player 1 name panel
				add(player1Panel);
				
				// Set background of current players turn
				if (currentPlayer.equalsIgnoreCase("Player 1"))
				{
					player1Panel.setBackground(Color.LIGHT_GRAY);
				}
				else
				{
					player2Panel.setBackground(Color.LIGHT_GRAY);
				}
			}
		}
	}
}
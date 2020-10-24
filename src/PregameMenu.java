import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PregameMenu {
	
	private JRadioButton noComputerButton;
	
	private JRadioButton whiteButton;
	
	private JRadioButton tenMinutes;
	private JRadioButton fiveMinutes;
	
	private JRadioButton timedButton;	
	private JRadioButton untimedButton;	
	
	private JRadioButton noDraftButton;

	private JRadioButton yesHighlightButton;

	private JButton startButton;
	
	PregameMenu(Menu menu)
	{
		// Options Frame
		final JFrame optionsFrame = new JFrame();	    

		
		// Options Panel
		JPanel optionsMenuPanel = new JPanel();
		
		// set Option Panel Layout
	    optionsMenuPanel.setLayout(new GridLayout(6,6));
			
	    // Computer Panel
		JPanel computerMenuPanel = createComputerMenuPanel();        
        
		// Turn Panel
		JPanel turnMenuPanel = createTurnMenuPanel();
        
		// Timer Panel
		JPanel timerMenuPanel = createTimerMenuPanel();
		
		// Time Limit Panel
		JPanel timeLimitMenuPanel = createTimeLimitMenuPanel();
		
		// Draft Panel
		JPanel draftMenuPanel = createDraftMenuPanel();
		
		// Highlighting Panel
		JPanel highlightingMenuPanel = createHighlightingMenuPanel();
		
		// Start Button Panel
		JPanel startButtonPanel =  createStartButtonPanel();
		
		// add to Option Panel
		optionsMenuPanel.add(computerMenuPanel);
		optionsMenuPanel.add(turnMenuPanel);
		optionsMenuPanel.add(timerMenuPanel);
		optionsMenuPanel.add(draftMenuPanel);
		optionsMenuPanel.add(highlightingMenuPanel);
	    optionsMenuPanel.add(startButtonPanel);
	        
	    // action listeners for radio buttons
	    ActionListener timedButtonListener = new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent actionEvent) 
	        {
	
	          optionsMenuPanel.remove(draftMenuPanel);
	          optionsMenuPanel.remove(startButtonPanel);
	
	          optionsMenuPanel.add(timeLimitMenuPanel);
	          optionsMenuPanel.add(draftMenuPanel);
	          optionsMenuPanel.add(highlightingMenuPanel);
	          optionsMenuPanel.add(startButtonPanel);
	
	            optionsMenuPanel.setLayout(new GridLayout(7,6));
	          optionsFrame.pack();
	          optionsMenuPanel.repaint();
	          optionsMenuPanel.revalidate();
	        }
	    };
	        
	    ActionListener untimedButtonListener = new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent actionEvent) 
	        {
	          optionsMenuPanel.remove(timeLimitMenuPanel);
	
	            optionsMenuPanel.setLayout(new GridLayout(6,6));
	          optionsFrame.pack();
	          optionsMenuPanel.repaint();
	          optionsMenuPanel.revalidate();
	        }
	    };
        
        // attach action listeners
        timedButton.addActionListener(timedButtonListener);
        untimedButton.addActionListener(untimedButtonListener);
        
        // add everything to Option Frame
        optionsFrame.add(optionsMenuPanel);
        optionsFrame.pack();
        optionsFrame.setLocationRelativeTo(menu);
        optionsFrame.setResizable(false);
        optionsFrame.setVisible(true);
        
        
        // action listener for start button
        startButton.addActionListener(l -> 
        {
        	boolean computerOpponent;
        	boolean whiteMovesFirst;
        	boolean timedMatch;
        	String timeLimit;
        	boolean draftPhase;
        	boolean highlight;
        	
        	// Computer opponent logic
        	if (noComputerButton.isSelected())
        	{
        		computerOpponent = false;
        	}
        	else
        	{
        		computerOpponent = true;
        	}
        	
        	// Turn determination logic
        	if (whiteButton.isSelected())
        	{
        		whiteMovesFirst = true;
        	}
        	else
        	{
        		whiteMovesFirst = false;
        	}
        	
        	// Timer use determination logic
        	if (timedButton.isSelected())
        	{
        		timedMatch = true;
                optionsMenuPanel.remove(startButton);
                optionsMenuPanel.add(timeLimitMenuPanel);
                optionsMenuPanel.add(startButton);
        	}
        	else 
        	{
        		timedMatch = false;
        	}
        	
        	// Time Limit determination logic
        	if (tenMinutes.isSelected())
        	{
        		timeLimit = "10:00";
        	}
        	else if (fiveMinutes.isSelected())
        	{
        		timeLimit = "05:00";
        	}
        	else
        	{
        		timeLimit = "02:00";
        	}
        	
        	// Draft determination logic
        	if (noDraftButton.isSelected())
        	{
        		draftPhase = false;
        	}
        	else
        	{
        		draftPhase = true;
        	}
        	
        	// highlight determination logic
        	if (yesHighlightButton.isSelected())
        	{
        		highlight = true;
        	}
        	else
        	{
        		highlight = false;
        	}
        	
        	new Game(timedMatch, timeLimit, computerOpponent, whiteMovesFirst, draftPhase, highlight);
        	optionsFrame.setVisible(false);
        	menu.setVisible(false);
        });
	}
	
	private JPanel createComputerMenuPanel()
	{
		JPanel computerMenuPanel = new JPanel();
		
		// create radio buttons
		JLabel computerOpponentLabel = new JLabel("Computer opponent:");
		noComputerButton = new JRadioButton("No");
		noComputerButton.setSelected(true);
		JRadioButton yesComputerButton = new JRadioButton("Yes");
		
		// create radio button group
		ButtonGroup computerGroup = new ButtonGroup();
		computerGroup.add(noComputerButton);
		computerGroup.add(yesComputerButton);
		
		// add to Turn Panel
		computerMenuPanel.add(computerOpponentLabel);
		computerMenuPanel.add(noComputerButton);
		computerMenuPanel.add(yesComputerButton);
		
		return computerMenuPanel;
	}
	
	private JPanel createTurnMenuPanel()
	{
		JPanel turnMenuPanel = new JPanel();
		
		// create radio buttons
		JLabel firstMoveLabel = new JLabel("First move:");
		whiteButton = new JRadioButton("White");
		whiteButton.setSelected(true);
		JRadioButton blackButton = new JRadioButton("Black");
		
		// create radio button group
		ButtonGroup turnGroup = new ButtonGroup();
		turnGroup.add(whiteButton);
		turnGroup.add(blackButton);
		
		// add to Turn Panel
		turnMenuPanel.add(firstMoveLabel);
		turnMenuPanel.add(whiteButton);
		turnMenuPanel.add(blackButton);
		
		return turnMenuPanel;
	}
	
	private JPanel createTimerMenuPanel()
	{
		JPanel timerMenuPanel = new JPanel();
		
		// create radio buttons
		JLabel timingTypeLabel = new JLabel("Type:");
		timedButton = new JRadioButton("Timed Game");
		untimedButton = new JRadioButton("Un-Timed Game");
		untimedButton.setSelected(true);
		
		// create radio button group
		ButtonGroup timerGroup = new ButtonGroup();
		timerGroup.add(timedButton);
		timerGroup.add(untimedButton);
		
		// add to Timer Panel
		timerMenuPanel.add(timingTypeLabel);
		timerMenuPanel.add(untimedButton);
		timerMenuPanel.add(timedButton);
		
		return timerMenuPanel;
	}
	
	private JPanel createTimeLimitMenuPanel()
	{
		JPanel timeLimitMenuPanel = new JPanel();
		
		// create radio buttons
		JLabel timeLimitLabel = new JLabel("Time Limit:");
		tenMinutes = new JRadioButton("10:00");
		tenMinutes.setSelected(true);
		fiveMinutes = new JRadioButton("5:00");
		JRadioButton twoMinutes = new JRadioButton("2:00");
		
		// create radio button group
		ButtonGroup timeLimitGroup = new ButtonGroup();
		timeLimitGroup.add(tenMinutes);
		timeLimitGroup.add(fiveMinutes);
		timeLimitGroup.add(twoMinutes);
		
		// add to Time Limit Panel
		timeLimitMenuPanel.add(timeLimitLabel);
		timeLimitMenuPanel.add(tenMinutes);
		timeLimitMenuPanel.add(fiveMinutes);
		timeLimitMenuPanel.add(twoMinutes);

		return timeLimitMenuPanel;
	}
	
	private JPanel createDraftMenuPanel()
	{
		JPanel draftMenuPanel = new JPanel();
		
		// create radio buttons
		JLabel draftLabel = new JLabel("Draft phase:");
		noDraftButton = new JRadioButton("No");
		noDraftButton.setSelected(true);
		JRadioButton yesButton = new JRadioButton("Yes");

		
		// create radio button group
		ButtonGroup draftGroup = new ButtonGroup();
		draftGroup.add(noDraftButton);
		draftGroup.add(yesButton);
		
		// add to Time Limit Panel
		draftMenuPanel.add(draftLabel);
		draftMenuPanel.add(noDraftButton);
		draftMenuPanel.add(yesButton);

		return draftMenuPanel;
	}
	
	private JPanel createHighlightingMenuPanel()
	{
		JPanel highlightingMenuPanel = new JPanel();
		
		// create radio buttons
		JLabel highlightLabel = new JLabel("Movement Highlighting:");
		yesHighlightButton = new JRadioButton("Yes");
		yesHighlightButton.setSelected(true);
		JRadioButton noButton = new JRadioButton("No");

		
		// create radio button group
		ButtonGroup highlightGroup = new ButtonGroup();
		highlightGroup.add(yesHighlightButton);
		highlightGroup.add(noButton);
		
		// add to Time Limit Panel
		highlightingMenuPanel.add(highlightLabel);
		highlightingMenuPanel.add(yesHighlightButton);
		highlightingMenuPanel.add(noButton);

		return highlightingMenuPanel;
	}
	
	private JPanel createStartButtonPanel()
	{
		JPanel startButtonPanel =  new JPanel();

		startButton = new JButton("Start!");
		startButtonPanel.add(new JPanel());
		startButtonPanel.add(startButton);
		startButtonPanel.add(new JPanel());
		
		return startButtonPanel;
	}

	public void setIconImage(BufferedImage read) {
		// TODO Auto-generated method stub
		
	}
}
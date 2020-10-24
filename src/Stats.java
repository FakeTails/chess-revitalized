import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class Stats extends JFrame{
	 public Stats() {
	        // Display a title.
	        setTitle("Player Stats");
	        // setting icon
	        try {
	        	this.setIconImage(ImageIO.read(new File("icon.png")));
	        }
	        catch (Exception e)
	        {
	        	
	        }
	        // Specify an action for the close button.
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        // Set size
	        setMinimumSize(new Dimension(600, 520));
	        
	        setSize(600, 520);
	        // setResizable(false);

	        JPanel panel = new JPanel();
	        panel.setPreferredSize(new Dimension(325, 175));
	        panel.setLayout(new FlowLayout());        // setting the buttons in flow layout	      
	        StatsTracker tracker = new StatsTracker(); // loading the stats before displaying      
	        
	        JTextArea textArea = new JTextArea();
	        textArea.append(String.format( "%-10s | %-10s | %-10s | WinRate \n" +
	        		"%-10s | %-12s | %-10s     | %.2f%% \n" + 
	        		"%-10s | %-12s | %-10s      | %.2f%% \n"+
	        		"%-8s| %-12s  | %-10s       | %.2f%% \n"
	        		, "Name","Wins","Losses", "Player 1",tracker.getWin("Player 1"),tracker.getLoss("Player 1"),((double)tracker.getWin("Player 1")/((double)tracker.getWin("Player 1")+(double)tracker.getLoss("Player 1")))*100.0,"Player 2",tracker.getWin("Player 2"),tracker.getLoss("Player 2"),((double)tracker.getWin("Player 2")/((double)tracker.getWin("Player 2")+(double)tracker.getLoss("Player 2")))*100.0,"Computer",tracker.getWin("Computer"),tracker.getLoss("Computer"),((double)tracker.getWin("Computer")/((double)tracker.getWin("Computer")+(double)tracker.getLoss("Computer")))*100.0));
	    	textArea.setEditable(false);
	        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);	  
	        panel.add(textArea);
	        add(panel);	
	        
	        setVisible(true);
	 }
}

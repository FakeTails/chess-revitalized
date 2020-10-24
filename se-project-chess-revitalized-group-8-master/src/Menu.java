import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileSystemView;


public class Menu extends JFrame {

	BufferedImage bufferedImage;
    public Menu() {
        // Display a title.
        setTitle("Chess: Revitalized");
        
        // Specify an action for the close button.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set size
        setMinimumSize(new Dimension(600, 520));
        
        setSize(600, 520);
        // setResizable(false);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(325, 175));
        panel.setLayout(new FlowLayout());        // setting the buttons in flow layout
        
        // making it looks prettier
        

        try {
        	
        	//this.setIconImage(Toolkit.getDefaultToolkit().getImage("Icon.png"));
        }
        catch (Exception e)
        {
        	
        }
        JLabel background=new JLabel(new ImageIcon("background.jpg"));

        // creating new buttons
        JButton startGame = new JButton("Start New Game");         
        JButton loadGame = new JButton("Load Game"); 
        JButton highScore = new JButton("Statistics");
        
        new StatsTracker(); // loading the stats
        // giving actions to each button
        startGame.addActionListener(l -> {           
            System.out.println("Opening Pre-game Menu...");
           new PregameMenu(this);
        });
        
        loadGame.addActionListener(l -> { 
        	
        	// parent component of the dialog
			JFrame parentFrame = new JFrame();
			 try {
				 parentFrame.setIconImage(ImageIO.read(new File("icon.png")));
		        }
		        catch (Exception e)
		        {
		        	
		        }
			File currentDirectory = new File(System.getProperty("user.dir"));
			JFileChooser fileChooser = new JFileChooser(currentDirectory, FileSystemView.getFileSystemView());
			
			fileChooser.setDialogTitle("Choose directory to open chessgame.ser from");   
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			
			int userSelection = fileChooser.showOpenDialog(parentFrame);
			
			if (userSelection == JFileChooser.APPROVE_OPTION) {
			    File fileToSave = fileChooser.getSelectedFile();
			    String fileName = fileToSave.getAbsolutePath() + "\\chessgame.ser";
			    System.out.println("Opening file: " + fileName);
			    
			    try
			    {
			    	FileInputStream fileIn = new FileInputStream(fileName);						    
					ObjectInputStream objectIn = new ObjectInputStream(fileIn);
					GameInterface gameInterface = (GameInterface)objectIn.readObject();
					
					
					fileIn.close();
					objectIn.close();
					
					gameInterface.getChessBoard().loadBoard();
					gameInterface.getChessBoard().loadPieces();
					gameInterface.getGameInfo().loadTimer(gameInterface);
					gameInterface.getGame().load(gameInterface);
					this.setVisible(false);
					
				} 
			    catch (Exception e) 
			    {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
            System.out.println("This is the Load Game button.");
        });
        
        highScore.addActionListener(l -> {  
        	new Stats();
        });
        
        // adding the buttons into the panel
        panel.add(startGame);          
        panel.add(loadGame);
        panel.add(highScore);  
        panel.add(background);
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        add(panel);
       this.pack();
        setVisible(true);
    }    
}
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

public class Tile extends JLayeredPane
{
	
	private final int HEIGHT = 60;
	private final int WIDTH = 60;
	
	private String imageName;
	private ImageIcon tileImage;
	private JButton tileButton;
	private int row;
	private int col;
	
	
	public Tile(String imageName, int row, int col)
	{
		this.row = row;
		this.col = col;
		
		setSize(HEIGHT, WIDTH);

		this.imageName = imageName;
		
		generateButton("gameImages/" + imageName);
		
		add(tileButton, new Integer(0), 0);
	}
	
	public void generateButton(String imageName)
	{
		tileImage = new ImageIcon(imageName);
		tileButton = new JButton();
		tileButton.setSize(HEIGHT, WIDTH);
		tileButton.setBorder(BorderFactory.createEmptyBorder()); 
		tileButton.setBorderPainted(false);
		tileButton.setIcon(tileImage);
		tileButton.setContentAreaFilled(false);
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public JButton getButton()
	{
		return tileButton;
	}
	
	public String getImageName()
	{
		return imageName;
	}
}

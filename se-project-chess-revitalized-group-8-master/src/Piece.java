import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * Piece IDs:
 * 
 * White
 * Pawn: 1
 * Knight: 2
 * Bishop: 3
 * Rook: 4
 * Queen: 5
 * King: 6
 * FalseKing: 7
 * Archer: 8
 * Squire: 9
 * Incendiary: 10
 * Devourer: 11
 * 
 * Black
 * Pawn: -1
 * Knight: -2
 * Bishop: -3
 * Rook: -4
 * Queen: -5
 * King: -6
 * FalseKing: -7
 * Archer: -8
 * Squire: -9
 * Incendiary: -10
 * Devourer: -11
 */


public abstract class Piece extends JPanel
{
	
	private final int HEIGHT = 60;
	private final int WIDTH = 60; 
	
	private ImageIcon pieceImage;
	private JButton pieceButton;
	private int row;
	private int col;
	String pieceColor;
	private boolean isSelected; //may not be needed
	private String imageName;
	private boolean isCaptured;
	private int numMovesTaken;
	private int pieceCost;
	
	private int pieceID;
	
	private boolean hasActionListener;
	
	public Piece(String imageName)
	{
		isSelected = false;
		isCaptured = false;
		hasActionListener = false;
		
		//regular expression to split imageName for pieceColor
		String[] splitImageName = imageName.split("(?=[A-Z])");
		pieceColor = splitImageName[0];
		
		numMovesTaken = 0;
		
		setSize(HEIGHT, WIDTH);
		setOpaque(false);
		
		this.imageName = "gameImages/" + imageName;
		generateButton(this.imageName);
		add(this.pieceButton);
	}
	
	public abstract boolean validMove(int row, int col, int[][] board);
	
	public void generateButton(String imageName)
	{
		pieceImage = new ImageIcon(imageName);
		pieceButton = new JButton();
		pieceButton.setSize(HEIGHT, WIDTH);
		pieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		pieceButton.setBorderPainted(false);
		pieceButton.setIcon(this.pieceImage);
		pieceButton.setContentAreaFilled(false);
	}
	
	public void updateImage(String imageName)
	{
		this.imageName = "gameImages/" + imageName;
		this.pieceImage = new ImageIcon(this.imageName);
		this.pieceButton.setIcon(this.pieceImage);
		add(this.pieceButton);
	}
	
	public void setPieceID(int id)
	{
		pieceID = id;
	}
	
	public int getPieceID()
	{
		return pieceID;
	}
	
	public void setSelected(boolean selected)
	{
		isSelected = selected;
	}
	
	public JButton getButton()
	{
		return pieceButton;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public void setRow(int row)
	{
		this.row = row;
	}
	
	public void setCol(int col)
	{
		this.col = col;
	}
	
	public String getPieceColor()
	{
		return pieceColor;
	}
	
	
	public String getImageName()
	{
		return imageName;
	}
	
	public boolean hasActionListener()
	{
		return hasActionListener;
	}
	
	public void setHasActionListener(boolean hasActionListener)
	{
		this.hasActionListener = hasActionListener;
	}
	
	public void incrementNumMoves()
	{
		numMovesTaken++;
	}
	
	public int getNumMovesTaken()
	{
		return numMovesTaken;
	}
	
	public void setPieceCost(int cost)
	{
		pieceCost = cost;
	}
	
	public int getPieceCost()
	{
		return pieceCost;
	}
}

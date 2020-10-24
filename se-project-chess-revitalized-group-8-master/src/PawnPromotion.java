import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class PawnPromotion
{
	//constructor that is passed color
	private final int HEIGHT = 60;
	private final int WIDTH = 60; 
	
	private JButton knightPieceButton;
	private JButton bishopPieceButton;
	private JButton rookPieceButton;
	private JButton queenPieceButton;
	private Piece selectedPromotionPiece;
	private JFrame promotionFrame;
	private JDialog promotionDialog;
	
	public PawnPromotion(String pieceColor, Board board, Piece selectedPiece)
	{
		promotionFrame = new JFrame();
		generateButtons(pieceColor, board, selectedPiece);
		
		promotionDialog = new JDialog(promotionFrame, true);
		promotionDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		promotionDialog.setTitle("Pawn Promotion");
		promotionDialog.setLayout(new GridLayout(1, 4));
		
		promotionDialog.add(knightPieceButton);
		promotionDialog.add(bishopPieceButton);
		promotionDialog.add(rookPieceButton);
		promotionDialog.add(queenPieceButton);
		
		promotionDialog.pack();
		promotionDialog.setLocationRelativeTo(board);
		promotionDialog.setVisible(true);
	}
	
	public void generateButtons(String pieceColor, Board board, Piece selectedPiece)
	{
		ImageIcon knightPieceImage = new ImageIcon("gameImages/" + pieceColor + "Knight.png");
		knightPieceButton = new JButton();
		knightPieceButton.setSize(HEIGHT, WIDTH);
		knightPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		knightPieceButton.setBorderPainted(false);
		knightPieceButton.setIcon(knightPieceImage);
		knightPieceButton.setContentAreaFilled(false);
		
		knightPieceButton.addActionListener(l -> {
			selectedPromotionPiece = new Knight(pieceColor);
			board.removePiece(selectedPiece.getRow(), selectedPiece.getCol());
			selectedPromotionPiece.setRow(selectedPiece.getRow());
			selectedPromotionPiece.setCol(selectedPiece.getCol());
			board.addPiece(selectedPromotionPiece, selectedPiece.getRow(), selectedPiece.getCol());
			promotionFrame.dispose();
		});
		
		ImageIcon bishopPieceImage = new ImageIcon("gameImages/" + pieceColor + "Bishop.png");
		bishopPieceButton = new JButton();
		bishopPieceButton.setSize(HEIGHT, WIDTH);
		bishopPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		bishopPieceButton.setBorderPainted(false);
		bishopPieceButton.setIcon(bishopPieceImage);
		bishopPieceButton.setContentAreaFilled(false);
		
		bishopPieceButton.addActionListener(l -> {
			selectedPromotionPiece = new Bishop(pieceColor);
			board.removePiece(selectedPiece.getRow(), selectedPiece.getCol());
			selectedPromotionPiece.setRow(selectedPiece.getRow());
			selectedPromotionPiece.setCol(selectedPiece.getCol());
			board.addPiece(selectedPromotionPiece, selectedPiece.getRow(), selectedPiece.getCol());
			promotionFrame.dispose();
		});
		
		ImageIcon rookPieceImage = new ImageIcon("gameImages/" + pieceColor + "Rook.png");
		rookPieceButton = new JButton();
		rookPieceButton.setSize(HEIGHT, WIDTH);
		rookPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		rookPieceButton.setBorderPainted(false);
		rookPieceButton.setIcon(rookPieceImage);
		rookPieceButton.setContentAreaFilled(false);
		
		rookPieceButton.addActionListener(l -> {
			selectedPromotionPiece = new Rook(pieceColor);
			board.removePiece(selectedPiece.getRow(), selectedPiece.getCol());
			selectedPromotionPiece.setRow(selectedPiece.getRow());
			selectedPromotionPiece.setCol(selectedPiece.getCol());
			board.addPiece(selectedPromotionPiece, selectedPiece.getRow(), selectedPiece.getCol());
			promotionFrame.dispose();
		});
		
		ImageIcon queenPieceImage = new ImageIcon("gameImages/" + pieceColor + "Queen.png");
		queenPieceButton = new JButton();
		queenPieceButton.setSize(HEIGHT, WIDTH);
		queenPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		queenPieceButton.setBorderPainted(false);
		queenPieceButton.setIcon(queenPieceImage);
		queenPieceButton.setContentAreaFilled(false);
		
		queenPieceButton.addActionListener(l -> {
			selectedPromotionPiece = new Queen(pieceColor);
			board.removePiece(selectedPiece.getRow(), selectedPiece.getCol());
			selectedPromotionPiece.setRow(selectedPiece.getRow());
			selectedPromotionPiece.setCol(selectedPiece.getCol());
			board.addPiece(selectedPromotionPiece, selectedPiece.getRow(), selectedPiece.getCol());
			promotionFrame.dispose();
		});
		
		
	}
}

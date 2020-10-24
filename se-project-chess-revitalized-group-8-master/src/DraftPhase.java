import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Random;



public class DraftPhase implements java.io.Serializable
{
	Board chessBoard;
	boolean draftPieceSelected;
	Piece selectedPiece;
	
	JPanel draftPanel;
	JPanel[][] pieceButtonHolder;
	
	int selectedPiecePointCost;
	
	boolean pieceIsSelected;
	boolean tileIsSelected;
	
	int numWhitePieces;
	int numBlackPieces;
	
	JLabel whiteDraftPointsLabel;
	JLabel blackDraftPointsLabel;
	
	JLabel whiteCoinLabel;
	JLabel blackCoinLabel;
	
	GameInformation infoPanel;
	
	boolean playerOneTurn;
	boolean draftPhase;
	TemplateMenu templateMenu;
	boolean timedGame;
	
	private Random rand;
	boolean computerOpponent;
	boolean computerKingAdded = false;
	boolean whiteKingAdded = false;
	
	DraftPhase(boolean whiteMovesFirst, Board board, boolean timedGame, boolean computerOpponent)
	{
		this.timedGame = timedGame;
		this.computerOpponent = computerOpponent;
		rand = new Random();
		
		pieceIsSelected = false;
		tileIsSelected = false;
		
		if(whiteMovesFirst)
		{
			playerOneTurn = true;
		}
		else
		{
			playerOneTurn = false;
		}
		
		chessBoard = board;
		templateMenu = new TemplateMenu(chessBoard, this);
		
		draftPanel = new JPanel();
		pieceButtonHolder = new JPanel[8][4];
		
		whiteDraftPointsLabel = new JLabel(String.valueOf(chessBoard.getWhiteDraftPoints()));
		whiteDraftPointsLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		whiteDraftPointsLabel.setBorder(BorderFactory.createEmptyBorder(18, 1, 1, 1));
		
		blackDraftPointsLabel = new JLabel(String.valueOf(chessBoard.getBlackDraftPoints()));
		blackDraftPointsLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		blackDraftPointsLabel.setBorder(BorderFactory.createEmptyBorder(18, 1, 1, 1));
		
		whiteCoinLabel = new JLabel();
		ImageIcon coin = new ImageIcon("gameImages/coin.png");
		whiteCoinLabel.setIcon(coin);
		
		blackCoinLabel = new JLabel();
		blackCoinLabel.setIcon(coin);
		
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				pieceButtonHolder[i][j] = new JPanel();
				pieceButtonHolder[i][j].setSize(60, 60);				
				draftPanel.add(pieceButtonHolder[i][j]);				
			}
		}	
		
		draftPanel.setLayout(new GridLayout(8,4));
		draftPanel.setBackground(Color.WHITE);
        draftPanel.setBorder(BorderFactory.createRaisedBevelBorder()); 
        draftPanel.setPreferredSize(new Dimension(240, 520));
        
        pieceButtonHolder[4][0].add(whiteCoinLabel);
        pieceButtonHolder[4][1].add(whiteDraftPointsLabel);
        
        
        if (!computerOpponent)
        {
        	pieceButtonHolder[3][0].add(blackCoinLabel);
        	pieceButtonHolder[3][1].add(blackDraftPointsLabel);
        }
        
        chessBoard.setWhitePointLabel(whiteDraftPointsLabel);
		chessBoard.setBlackPointLabel(blackDraftPointsLabel);
  
        
		generateButtons();
		
		draftPhase = true;
	
	}
	
	
	public void generateButtons()
	{
		JButton pieceCostButton = new JButton("Values");
		pieceCostButton.addActionListener(l -> {
			JFrame pieceValueFrame = new JFrame();
			pieceValueFrame.setSize(400,300);
			pieceValueFrame.setLayout(new GridLayout(3,1));
			pieceValueFrame.setResizable(false);
			
			JLabel valuesLabel = new JLabel("Piece values", SwingConstants.CENTER);
			valuesLabel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
			
			JPanel pieceValues = new JPanel();
			pieceValues.setLayout(new GridLayout(3,2));
			
			pieceValues.add(new JLabel("Pawn: 1", SwingConstants.CENTER));
			pieceValues.add(new JLabel("Knight: 3", SwingConstants.CENTER));
			pieceValues.add(new JLabel("Rook: 5", SwingConstants.CENTER));
			pieceValues.add(new JLabel("Bishop: 3", SwingConstants.CENTER));
			pieceValues.add(new JLabel("Queen: 9", SwingConstants.CENTER));
			pieceValues.add(new JLabel("King: 0", SwingConstants.CENTER));
			
			pieceValues.add(new JLabel("Archer: 4", SwingConstants.CENTER));
			pieceValues.add(new JLabel("Squire: 2", SwingConstants.CENTER));
			pieceValues.add(new JLabel("Incendiary: 6", SwingConstants.CENTER));
			pieceValues.add(new JLabel("False King: 3", SwingConstants.CENTER));
			pieceValues.add(new JLabel("Devourer: 4", SwingConstants.CENTER));
			
			pieceValues.setBorder(BorderFactory.createLoweredSoftBevelBorder());
			
//			JButton close = new JButton("Close");
//			close.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			pieceValueFrame.add(valuesLabel);
			pieceValueFrame.add(pieceValues);
//			pieceValueFrame.add(close);

			pieceValueFrame.setLocationRelativeTo(chessBoard);
			pieceValueFrame.setVisible(true);

//	        close.addActionListener(k -> {
//	        	pieceValueFrame.dispose();
//	        });
		});
		
		pieceButtonHolder[3][3].add(pieceCostButton);
		
		JButton whiteTemplateButton = new JButton("Template");
		whiteTemplateButton.addActionListener(l -> {
			templateMenu.generateWhiteTemplateFrame();
		});
		
		JButton blackTemplateButton = new JButton("Template");
		blackTemplateButton.addActionListener(l -> {
			templateMenu.generateBlackTemplateFrame();
		});
		
		JButton finalizeDraftButton = new JButton("Begin");
		finalizeDraftButton.addActionListener(l -> {
			if(chessBoard.bothKingsDrafted())
			{
				chessBoard.setDraftPhase(false);
				System.out.println("Draft completed...");
				if (computerOpponent)
				{
					computerDraft();
				}
				
				if (timedGame)
				{
					infoPanel.startTimer();
				}
				
				try 
				{
					templateMenu.dispose();
				}
				catch (Exception e)
				{
					// just incase this is here.
				}
				
				pieceButtonHolder[3][3].remove(pieceCostButton);
				pieceButtonHolder[3][3].repaint();
				pieceButtonHolder[3][3].revalidate();
				
				pieceButtonHolder[2][3].remove(blackTemplateButton);
				pieceButtonHolder[2][3].repaint();
				pieceButtonHolder[2][3].revalidate();
				
				pieceButtonHolder[5][3].remove(whiteTemplateButton);
				pieceButtonHolder[5][3].repaint();
				pieceButtonHolder[5][3].revalidate();
				
				pieceButtonHolder[4][3].remove(finalizeDraftButton);
				pieceButtonHolder[4][3].repaint();
				pieceButtonHolder[4][3].revalidate();
			}
			else
			{
				System.out.println("Both Kings must be drafted to beign");
			}
		});
		
		pieceButtonHolder[4][3].add(finalizeDraftButton);
		
		if (!computerOpponent)
		{
			pieceButtonHolder[2][3].add(blackTemplateButton);
		}
		pieceButtonHolder[5][3].add(whiteTemplateButton);
		
		ImageIcon whiteFalseKingPieceImage = new ImageIcon("gameImages/whiteJester.png");
		JButton whiteFalseKingPieceButton = new JButton();
		whiteFalseKingPieceButton.setSize(60, 60);
		whiteFalseKingPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		whiteFalseKingPieceButton.setBorderPainted(false);
		whiteFalseKingPieceButton.setIcon(whiteFalseKingPieceImage);
		whiteFalseKingPieceButton.setContentAreaFilled(false);
		
		whiteFalseKingPieceButton.addActionListener(l -> {
			selectedPiece = new FalseKing("white");
			selectedPiecePointCost = 3;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
				
		ImageIcon whiteSquirePieceImage = new ImageIcon("gameImages/whiteSquire.png");
		JButton whiteSquirePieceButton = new JButton();
		whiteSquirePieceButton.setSize(60, 60);
		whiteSquirePieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		whiteSquirePieceButton.setBorderPainted(false);
		whiteSquirePieceButton.setIcon(whiteSquirePieceImage);
		whiteSquirePieceButton.setContentAreaFilled(false);
		
		whiteSquirePieceButton.addActionListener(l -> {
			selectedPiece = new Squire("white");
			selectedPiecePointCost = 3;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon whiteIncendiaryPieceImage = new ImageIcon("gameImages/whiteIncendiary.png");
		JButton whiteIncendiaryPieceButton = new JButton();
		whiteIncendiaryPieceButton.setSize(60, 60);
		whiteIncendiaryPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		whiteIncendiaryPieceButton.setBorderPainted(false);
		whiteIncendiaryPieceButton.setIcon(whiteIncendiaryPieceImage);
		whiteIncendiaryPieceButton.setContentAreaFilled(false);
		
		whiteIncendiaryPieceButton.addActionListener(l -> {
			selectedPiece = new Incendiary("white");
			selectedPiecePointCost = 3;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon whiteDevourerPieceImage = new ImageIcon("gameImages/whiteDevourer.png");
		JButton whiteDevourerPieceButton = new JButton();
		whiteDevourerPieceButton.setSize(60, 60);
		whiteDevourerPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		whiteDevourerPieceButton.setBorderPainted(false);
		whiteDevourerPieceButton.setIcon(whiteDevourerPieceImage);
		whiteDevourerPieceButton.setContentAreaFilled(false);
		
		whiteDevourerPieceButton.addActionListener(l -> {
			selectedPiece = new Devourer("white");
			selectedPiecePointCost = 4;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon whiteArcherPieceImage = new ImageIcon("gameImages/whiteArcher.png");
		JButton whiteArcherPieceButton = new JButton();
		whiteArcherPieceButton.setSize(60, 60);
		whiteArcherPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		whiteArcherPieceButton.setBorderPainted(false);
		whiteArcherPieceButton.setIcon(whiteArcherPieceImage);
		whiteArcherPieceButton.setContentAreaFilled(false);
		
		whiteArcherPieceButton.addActionListener(l -> {
			selectedPiece = new Archer("white");
			selectedPiecePointCost = 3;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		
		ImageIcon whiteKnightPieceImage = new ImageIcon("gameImages/whiteKnight.png");
		JButton whiteKnightPieceButton = new JButton();
		whiteKnightPieceButton.setSize(60, 60);
		whiteKnightPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		whiteKnightPieceButton.setBorderPainted(false);
		whiteKnightPieceButton.setIcon(whiteKnightPieceImage);
		whiteKnightPieceButton.setContentAreaFilled(false);
		
		whiteKnightPieceButton.addActionListener(l -> {
			selectedPiece = new Knight("white");
			selectedPiecePointCost = 3;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon whiteBishopPieceImage = new ImageIcon("gameImages/whiteBishop.png");
		JButton whiteBishopPieceButton = new JButton();
		whiteBishopPieceButton.setSize(60, 60);
		whiteBishopPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		whiteBishopPieceButton.setBorderPainted(false);
		whiteBishopPieceButton.setIcon(whiteBishopPieceImage);
		whiteBishopPieceButton.setContentAreaFilled(false);
		
		whiteBishopPieceButton.addActionListener(l -> {
			selectedPiece = new Bishop("white");
			selectedPiecePointCost = 3;		
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
			});
		
		
		ImageIcon whiteRookPieceImage = new ImageIcon("gameImages/whiteRook.png");
		JButton whiteRookPieceButton = new JButton();
		whiteRookPieceButton.setSize(60, 60);
		whiteRookPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		whiteRookPieceButton.setBorderPainted(false);
		whiteRookPieceButton.setIcon(whiteRookPieceImage);
		whiteRookPieceButton.setContentAreaFilled(false);
		
		whiteRookPieceButton.addActionListener(l -> {
			selectedPiece = new Rook("white");
			selectedPiecePointCost = 5;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon whitePawnPieceImage = new ImageIcon("gameImages/whitePawn.png");
		JButton whitePawnPieceButton = new JButton();
		whitePawnPieceButton.setSize(60, 60);
		whitePawnPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		whitePawnPieceButton.setBorderPainted(false);
		whitePawnPieceButton.setIcon(whitePawnPieceImage);
		whitePawnPieceButton.setContentAreaFilled(false);
		
		whitePawnPieceButton.addActionListener(l -> {
			selectedPiece = new Pawn("white");
			selectedPiecePointCost = 1;		
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon whiteKingPieceImage = new ImageIcon("gameImages/whiteKing.png");
		JButton whiteKingPieceButton = new JButton();
		whiteKingPieceButton.setSize(60, 60);
		whiteKingPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		whiteKingPieceButton.setBorderPainted(false);
		whiteKingPieceButton.setIcon(whiteKingPieceImage);
		whiteKingPieceButton.setContentAreaFilled(false);
		
		whiteKingPieceButton.addActionListener(l -> {
			selectedPiece = new King("white");
			selectedPiecePointCost = 0;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon whiteQueenPieceImage = new ImageIcon("gameImages/whiteQueen.png");
		JButton whiteQueenPieceButton = new JButton();
		whiteQueenPieceButton.setSize(60, 60);
		whiteQueenPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		whiteQueenPieceButton.setBorderPainted(false);
		whiteQueenPieceButton.setIcon(whiteQueenPieceImage);
		whiteQueenPieceButton.setContentAreaFilled(false);
		
		whiteQueenPieceButton.addActionListener(l -> {
			selectedPiece = new Queen("white");
			selectedPiecePointCost = 9;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		ImageIcon blackFalseKingPieceImage = new ImageIcon("gameImages/blackJester.png");
		JButton blackFalseKingPieceButton = new JButton();
		blackFalseKingPieceButton.setSize(60, 60);
		blackFalseKingPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		blackFalseKingPieceButton.setBorderPainted(false);
		blackFalseKingPieceButton.setIcon(blackFalseKingPieceImage);
		blackFalseKingPieceButton.setContentAreaFilled(false);
		
		blackFalseKingPieceButton.addActionListener(l -> {
			selectedPiece = new FalseKing("black");
			selectedPiecePointCost = 3;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
				
		ImageIcon blackSquirePieceImage = new ImageIcon("gameImages/blackSquire.png");
		JButton blackSquirePieceButton = new JButton();
		blackSquirePieceButton.setSize(60, 60);
		blackSquirePieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		blackSquirePieceButton.setBorderPainted(false);
		blackSquirePieceButton.setIcon(blackSquirePieceImage);
		blackSquirePieceButton.setContentAreaFilled(false);
		
		blackSquirePieceButton.addActionListener(l -> {
			selectedPiece = new Squire("black");
			selectedPiecePointCost = 3;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon blackIncendiaryPieceImage = new ImageIcon("gameImages/blackIncendiary.png");
		JButton blackIncendiaryPieceButton = new JButton();
		blackIncendiaryPieceButton.setSize(60, 60);
		blackIncendiaryPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		blackIncendiaryPieceButton.setBorderPainted(false);
		blackIncendiaryPieceButton.setIcon(blackIncendiaryPieceImage);
		blackIncendiaryPieceButton.setContentAreaFilled(false);
		
		blackIncendiaryPieceButton.addActionListener(l -> {
			selectedPiece = new Incendiary("black");
			selectedPiecePointCost = 3;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon blackDevourerPieceImage = new ImageIcon("gameImages/blackDevourer.png");
		JButton blackDevourerPieceButton = new JButton();
		blackDevourerPieceButton.setSize(60, 60);
		blackDevourerPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		blackDevourerPieceButton.setBorderPainted(false);
		blackDevourerPieceButton.setIcon(blackDevourerPieceImage);
		blackDevourerPieceButton.setContentAreaFilled(false);
		
		blackDevourerPieceButton.addActionListener(l -> {
			selectedPiece = new Devourer("black");
			selectedPiecePointCost = 4;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon blackArcherPieceImage = new ImageIcon("gameImages/blackArcher.png");
		JButton blackArcherPieceButton = new JButton();
		blackArcherPieceButton.setSize(60, 60);
		blackArcherPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		blackArcherPieceButton.setBorderPainted(false);
		blackArcherPieceButton.setIcon(blackArcherPieceImage);
		blackArcherPieceButton.setContentAreaFilled(false);
		
		blackArcherPieceButton.addActionListener(l -> {
			selectedPiece = new Archer("black");
			selectedPiecePointCost = 3;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});

		
		ImageIcon blackKnightPieceImage = new ImageIcon("gameImages/blackKnight.png");
		JButton blackKnightPieceButton = new JButton();
		blackKnightPieceButton.setSize(60, 60);
		blackKnightPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		blackKnightPieceButton.setBorderPainted(false);
		blackKnightPieceButton.setIcon(blackKnightPieceImage);
		blackKnightPieceButton.setContentAreaFilled(false);
		
		blackKnightPieceButton.addActionListener(l -> {
			selectedPiece = new Knight("black");
			selectedPiecePointCost = 3;		
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon blackBishopPieceImage = new ImageIcon("gameImages/blackBishop.png");
		JButton blackBishopPieceButton = new JButton();
		blackBishopPieceButton.setSize(60, 60);
		blackBishopPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		blackBishopPieceButton.setBorderPainted(false);
		blackBishopPieceButton.setIcon(blackBishopPieceImage);
		blackBishopPieceButton.setContentAreaFilled(false);
		
		blackBishopPieceButton.addActionListener(l -> {
			selectedPiece = new Bishop("black");
			selectedPiecePointCost = 3;		
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		
		ImageIcon blackRookPieceImage = new ImageIcon("gameImages/blackRook.png");
		JButton blackRookPieceButton = new JButton();
		blackRookPieceButton.setSize(60, 60);
		blackRookPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		blackRookPieceButton.setBorderPainted(false);
		blackRookPieceButton.setIcon(blackRookPieceImage);
		blackRookPieceButton.setContentAreaFilled(false);
		
		blackRookPieceButton.addActionListener(l -> {
			selectedPiece = new Rook("black");
			selectedPiecePointCost = 5;		
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon blackPawnPieceImage = new ImageIcon("gameImages/blackPawn.png");
		JButton blackPawnPieceButton = new JButton();
		blackPawnPieceButton.setSize(60, 60);
		blackPawnPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		blackPawnPieceButton.setBorderPainted(false);
		blackPawnPieceButton.setIcon(blackPawnPieceImage);
		blackPawnPieceButton.setContentAreaFilled(false);
		
		blackPawnPieceButton.addActionListener(l -> {
			selectedPiece = new Pawn("black");
			selectedPiecePointCost = 1;	
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon blackKingPieceImage = new ImageIcon("gameImages/blackKing.png");
		JButton blackKingPieceButton = new JButton();
		blackKingPieceButton.setSize(60, 60);
		blackKingPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		blackKingPieceButton.setBorderPainted(false);
		blackKingPieceButton.setIcon(blackKingPieceImage);
		blackKingPieceButton.setContentAreaFilled(false);
		
		blackKingPieceButton.addActionListener(l -> {
			selectedPiece = new King("black");
			selectedPiecePointCost = 0;		
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		ImageIcon blackQueenPieceImage = new ImageIcon("gameImages/blackQueen.png");
		JButton blackQueenPieceButton = new JButton();
		blackQueenPieceButton.setSize(60, 60);
		blackQueenPieceButton.setBorder(BorderFactory.createEmptyBorder(-7, 1, 1, 1));
		blackQueenPieceButton.setBorderPainted(false);
		blackQueenPieceButton.setIcon(blackQueenPieceImage);
		blackQueenPieceButton.setContentAreaFilled(false);
		
		blackQueenPieceButton.addActionListener(l -> {
			selectedPiece = new Queen("black");
			selectedPiecePointCost = 9;
			
			chessBoard.setNewDraftPieceSelected(true);
			chessBoard.setSelectedDraftPiece(selectedPiece);	
		});
		
		if (!computerOpponent)
		{
			pieceButtonHolder[0][0].add(blackQueenPieceButton);
			pieceButtonHolder[0][1].add(blackKingPieceButton);
			pieceButtonHolder[1][0].add(blackRookPieceButton);
			pieceButtonHolder[1][1].add(blackBishopPieceButton);
			pieceButtonHolder[2][0].add(blackPawnPieceButton);
			pieceButtonHolder[2][1].add(blackKnightPieceButton);
			
			pieceButtonHolder[2][2].add(blackDevourerPieceButton);
			pieceButtonHolder[1][2].add(blackSquirePieceButton);
			pieceButtonHolder[1][3].add(blackArcherPieceButton);
			pieceButtonHolder[0][2].add(blackFalseKingPieceButton);
			pieceButtonHolder[0][3].add(blackIncendiaryPieceButton);
		}
		
		pieceButtonHolder[5][0].add(whitePawnPieceButton);
		pieceButtonHolder[5][1].add(whiteKnightPieceButton);
		pieceButtonHolder[6][0].add(whiteRookPieceButton);
		pieceButtonHolder[6][1].add(whiteBishopPieceButton);
		pieceButtonHolder[7][0].add(whiteQueenPieceButton);
		pieceButtonHolder[7][1].add(whiteKingPieceButton);
		
		pieceButtonHolder[5][2].add(whiteDevourerPieceButton);
		pieceButtonHolder[6][2].add(whiteSquirePieceButton);
		pieceButtonHolder[6][3].add(whiteArcherPieceButton);
		pieceButtonHolder[7][2].add(whiteFalseKingPieceButton);
		pieceButtonHolder[7][3].add(whiteIncendiaryPieceButton);
		
	}
	
	public JPanel getDraftPanel()
	{
		return draftPanel;
	}
	
	public void passInfoPanel(GameInformation infoPanel)
	{
		this.infoPanel = infoPanel;
	}
	
	public void setWhitePointsPanel(String points)
	{
		whiteDraftPointsLabel.setText(points);;
	}
	
	public void setBlackPointsPanel(String points)
	{
		blackDraftPointsLabel.setText(points);;
	}
	
	void computerDraft()
	{
		boolean[][] takenTiles = new boolean[2][8];
		int randPiece = -6; 	// start with king
		int randRow = 0; 		// on back row
		int randCol = rand.nextInt(8);
		
		Piece addPiece = null; 
		
		Arrays.fill(takenTiles[0], false);
		Arrays.fill(takenTiles[1], false);
		
		addPiece = selectBlackPiece(randPiece);
		takenTiles[randRow][randCol] = true;
		chessBoard.addPiece(addPiece, randRow, randCol);
		randPiece = (rand.nextInt(11) + 1) * -1; // 1 - 11, negative
		
		for (int i = 1; i < 16; i++) // start at 1 because king is already added.
		{
			while (takenTiles[randRow][randCol] == true)
			{
				randRow = rand.nextInt(2);
				randCol = rand.nextInt(8);
			}
			
			addPiece = selectBlackPiece(randPiece); // get random piece
			if (addPiece.getPieceID() == 6)
			{
				return;
			}
			else
			{
				takenTiles[randRow][randCol] = true; // set array location as taken
				chessBoard.addPiece(addPiece, randRow, randCol); // add piece to board
				randPiece = (rand.nextInt(11) + 1) * -1; // 1 - 11, negative
			}
		}
	}

	private Piece selectBlackPiece(int randID)
	{
		int pieceID = randID;
		Piece replacementPiece = null;
		boolean insufficientFunds = true;
		boolean done = false;
		int count = 0;
		
		int draftPoints = chessBoard.getBlackDraftPoints();
		
		while (insufficientFunds && !done)
		{
			count++;
			if (pieceID == -1 && draftPoints -1 >= 0)
			{
				replacementPiece = new Pawn("black");
				insufficientFunds = false;		
				chessBoard.setComputerDraftPoints(draftPoints -1);
			}
			else if (pieceID == -2 && draftPoints -3 >= 0)
			{
				replacementPiece = new Knight("black");
				insufficientFunds = false;		
				chessBoard.setComputerDraftPoints(draftPoints -3);
			}
			else if (pieceID == -3 && draftPoints -3 >= 0)
			{
				replacementPiece = new Bishop("black");
				insufficientFunds = false;		
				chessBoard.setComputerDraftPoints(draftPoints -3);
			}
			else if (pieceID == -4 && draftPoints -5 >= 0)
			{
				replacementPiece = new Rook("black");
				insufficientFunds = false;		
				chessBoard.setComputerDraftPoints(draftPoints -5);
			}
			else if (pieceID == -5 && draftPoints -9 >= 0)
			{
				replacementPiece = new Queen("black");
				insufficientFunds = false;		
				chessBoard.setComputerDraftPoints(draftPoints -9);
			}
			else if (pieceID == -6)
			{
				// never add another king
				if (!computerKingAdded)
				{
					replacementPiece = new King("black");
					insufficientFunds = false;
					computerKingAdded = true;
				}
				
			}
			else if (pieceID == -7 && draftPoints -3 >= 0)
			{
				replacementPiece = new FalseKing("black");
				insufficientFunds = false;		
				chessBoard.setComputerDraftPoints(draftPoints -3);
			}
			else if (pieceID == -8 && draftPoints -4 >= 0)
			{
				replacementPiece = new Archer("black");
				insufficientFunds = false;		
				chessBoard.setComputerDraftPoints(draftPoints -4);
			}
			else if (pieceID == -9  && draftPoints -2 >= 0)
			{
				replacementPiece = new Squire("black");
				insufficientFunds = false;		
				chessBoard.setComputerDraftPoints(draftPoints -2);
			}
			else if (pieceID == -10  && draftPoints -6 >= 0)
			{
				replacementPiece = new Incendiary("black");
				insufficientFunds = false;		
				chessBoard.setComputerDraftPoints(draftPoints -6);
			}
			else if (pieceID == -11  && draftPoints -4 >= 0)
			{
				replacementPiece = new Devourer("black");
				insufficientFunds = false;		
				chessBoard.setComputerDraftPoints(draftPoints -4);
			}
			
			if (insufficientFunds)
			{
				pieceID = (rand.nextInt(15) + 1) * -1; // 1 - 11, negative
			}
			
			if (count >= 5 || draftPoints == 0)
			{
				done = true;
			}
		} 
		
		if (done)
		{
			return new King("white");
		}
		
		return replacementPiece;
	}
}
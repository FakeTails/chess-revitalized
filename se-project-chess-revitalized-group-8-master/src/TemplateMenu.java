import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;


public class TemplateMenu {

	private Board chessBoard;
	private DraftPhase draft;
	private JFrame templateFrame;
	private Random rand;
	private boolean whiteKingAdded = false;
	private boolean blackKingAdded = false;
	
	public TemplateMenu(Board chessBoard, DraftPhase draft)
	{
		this.chessBoard = chessBoard;
		this.draft = draft;
		rand = new Random();
	}
	
	public void generateWhiteTemplateFrame()
	{
		//JOptionPane templateFrame = new JOptionPane();

		try
		{
			templateFrame.dispose();
		}
		catch (Exception e)
		{
			// don't give a fuck.
		}
		
		templateFrame = new JFrame();
		templateFrame.setSize(400,300);
		templateFrame.setLayout(new GridLayout(3,1));
		templateFrame.setResizable(false);
		
		JLabel templateLabel = new JLabel("Templates", SwingConstants.CENTER);
		templateLabel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		
		JPanel templates = new JPanel();
		templates.setLayout(new GridLayout(1,4));
		
		JButton standardTemplate = new JButton();
		ImageIcon standardTemplateIcon = new ImageIcon("gameImages/StandardTemplate.png");
		standardTemplate.setIcon(standardTemplateIcon);
		standardTemplate.setContentAreaFilled(false);
		
		standardTemplate.addActionListener(k -> {
			int[][] boardState = chessBoard.getBoardState();
			// remove white rows
			for (int i = 6; i < 8; i++)
        	{
        		for (int j = 0; j < 8; j++)
        		{
        			if (boardState[i][j] != 0)
        			{
        				chessBoard.removePiece(i, j);
        			}
        		}
        	}
			
			whiteStandardTemplate();
			chessBoard.setWhiteDraftPoints(0);
			
			draft.setWhitePointsPanel("0");
			chessBoard.setWhiteKingDrafted(true);
        });

		
		JButton clearTemplate = new JButton();
		ImageIcon clearTemplateIcon = new ImageIcon("gameImages/ClearTemplate.png");
		clearTemplate.setIcon(clearTemplateIcon);
		clearTemplate.setContentAreaFilled(false);
		
		clearTemplate.addActionListener(k -> {
			int[][] boardState = chessBoard.getBoardState();
			// remove white rows
			for (int i = 6; i < 8; i++)
        	{
        		for (int j = 0; j < 8; j++)
        		{
        			if (boardState[i][j] != 0)
        			{
        				chessBoard.removePiece(i, j);
        			}
        		}
        	}
			
			chessBoard.setWhiteDraftPoints(39);

			draft.setWhitePointsPanel("39");
			chessBoard.setWhiteKingDrafted(false);
        });
		
		JButton randTemplate = new JButton();
		ImageIcon randTemplateIcon = new ImageIcon("gameImages/RandomTemplate.png");
		randTemplate.setIcon(randTemplateIcon);
		randTemplate.setContentAreaFilled(false);
		
		randTemplate.addActionListener(k -> {
			int[][] boardState = chessBoard.getBoardState();
			// remove white rows
			for (int i = 6; i < 8; i++)
        	{
        		for (int j = 0; j < 8; j++)
        		{
        			if (boardState[i][j] != 0)
        			{
        				chessBoard.removePiece(i, j);
        			}
        		}
        	}
			
			chessBoard.setWhiteDraftPoints(39);
			whiteKingAdded = false;
			randomWhiteDraft();
			
			draft.setWhitePointsPanel(Integer.toString(chessBoard.getWhiteDraftPoints()));
			chessBoard.setWhiteKingDrafted(true);
        });
		
		
		templates.add(standardTemplate);
		templates.add(clearTemplate);
		templates.add(randTemplate);

		
		templates.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		
		templateFrame.add(templateLabel);
		templateFrame.add(templates);

		templateFrame.setLocationRelativeTo(chessBoard);
		templateFrame.setVisible(true);
	}
	
	public void generateBlackTemplateFrame()
	{
		//JOptionPane templateFrame = new JOptionPane();

		try
		{
			templateFrame.dispose();
		}
		catch (Exception e)
		{
			// don't give a fuck.
		}
		
		templateFrame = new JFrame();
		templateFrame.setSize(400,300);
		templateFrame.setLayout(new GridLayout(3,1));
		templateFrame.setResizable(false);
		
		JLabel templateLabel = new JLabel("Templates", SwingConstants.CENTER);
		templateLabel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		
		JPanel templates = new JPanel();
		templates.setLayout(new GridLayout(1,4));
		
		
		JButton standardTemplate = new JButton();
		ImageIcon standardTemplateIcon = new ImageIcon("gameImages/BlackStandardTemplate.png");
		standardTemplate.setIcon(standardTemplateIcon);
		standardTemplate.setContentAreaFilled(false);

		standardTemplate.addActionListener(k -> {
			int[][] boardState = chessBoard.getBoardState();
			// remove white rows
			for (int i = 0; i < 2; i++)
        	{
        		for (int j = 0; j < 8; j++)
        		{
        			if (boardState[i][j] != 0)
        			{
        				chessBoard.removePiece(i, j);
        			}
        		}
        	}
			
			blackStandardTemplate();
			chessBoard.setBlackDraftPoints(0);
			
			draft.setBlackPointsPanel("0");
			chessBoard.setBlackKingDrafted(true);
        });
		
		JButton clearTemplate = new JButton();
		ImageIcon clearTemplateIcon = new ImageIcon("gameImages/ClearTemplate.png");
		clearTemplate.setIcon(clearTemplateIcon);
		clearTemplate.setContentAreaFilled(false);

		clearTemplate.addActionListener(k -> {
			int[][] boardState = chessBoard.getBoardState();
			// remove white rows
			for (int i = 0; i < 2; i++)
        	{
        		for (int j = 0; j < 8; j++)
        		{
        			if (boardState[i][j] != 0)
        			{
        				chessBoard.removePiece(i, j);
        			}
        		}
        	}
			
			chessBoard.setBlackDraftPoints(39);

			draft.setBlackPointsPanel("39");
			chessBoard.setBlackKingDrafted(false);
        });
		
		
		JButton randTemplate = new JButton();
		ImageIcon randTemplateIcon = new ImageIcon("gameImages/RandomTemplate.png");
		randTemplate.setIcon(randTemplateIcon);
		randTemplate.setContentAreaFilled(false);
		
		randTemplate.addActionListener(k -> {
			int[][] boardState = chessBoard.getBoardState();
			// remove black rows
			for (int i = 0; i < 2; i++)
        	{
        		for (int j = 0; j < 8; j++)
        		{
        			if (boardState[i][j] != 0)
        			{
        				chessBoard.removePiece(i, j);
        			}
        		}
        	}
			
			chessBoard.setBlackDraftPoints(39);
			blackKingAdded = false;
			randomBlackDraft();
			
			draft.setBlackPointsPanel(Integer.toString(chessBoard.getBlackDraftPoints()));
			chessBoard.setBlackKingDrafted(true);
        });
		
		templates.add(standardTemplate);
		templates.add(clearTemplate);
		templates.add(randTemplate);
		
		templates.setBorder(BorderFactory.createLoweredSoftBevelBorder());

		templateFrame.add(templateLabel);
		templateFrame.add(templates);

		templateFrame.setLocationRelativeTo(chessBoard);
		templateFrame.setVisible(true);
	}
	
	
	private void whiteStandardTemplate()
	{
		Pawn whitePawn1 = new Pawn("white");
		chessBoard.addPiece(whitePawn1, 6, 0);
		
		Pawn whitePawn2 = new Pawn("white");
		chessBoard.addPiece(whitePawn2, 6, 1);
		
		Pawn whitePawn3 = new Pawn("white");
		chessBoard.addPiece(whitePawn3, 6, 2);
		
		Pawn whitePawn4 = new Pawn("white");
		chessBoard.addPiece(whitePawn4, 6, 3);
		
		Pawn whitePawn5 = new Pawn("white");
		chessBoard.addPiece(whitePawn5, 6, 4);
		
		Pawn whitePawn6 = new Pawn("white");
		chessBoard.addPiece(whitePawn6, 6, 5);
		
		Pawn whitePawn7 = new Pawn("white");
		chessBoard.addPiece(whitePawn7, 6, 6);
		
		Pawn whitePawn8 = new Pawn("white");
		chessBoard.addPiece(whitePawn8, 6, 7);
		
		Rook whiteRook1 = new Rook("white");
		chessBoard.addPiece(whiteRook1, 7, 0);
		
		Rook whiteRook2 = new Rook("white");
		chessBoard.addPiece(whiteRook2, 7, 7);
		
		Knight whiteKnight1 = new Knight("white");
		chessBoard.addPiece(whiteKnight1, 7, 1);
		
		Knight whiteKnight2 = new Knight("white");
		chessBoard.addPiece(whiteKnight2, 7, 6);
		
		Bishop whiteBishop1 = new Bishop("white");
		chessBoard.addPiece(whiteBishop1, 7, 2);
		
		Bishop whiteBishop2 = new Bishop("white");
		chessBoard.addPiece(whiteBishop2, 7, 5);
		
		Queen whiteQueen = new Queen("white");
		chessBoard.addPiece(whiteQueen, 7, 3);
		
		King whiteKing = new King("white");
		chessBoard.addPiece(whiteKing, 7, 4);
	}
	
	private void blackStandardTemplate()
	{
		Pawn blackPawn1 = new Pawn("black");
		chessBoard.addPiece(blackPawn1, 1, 0);
		
		Pawn blackPawn2 = new Pawn("black");
		chessBoard.addPiece(blackPawn2, 1, 1);
		
		Pawn blackPawn3 = new Pawn("black");
		chessBoard.addPiece(blackPawn3, 1, 2);
		
		Pawn blackPawn4 = new Pawn("black");
		chessBoard.addPiece(blackPawn4, 1, 3);
		
		Pawn blackPawn5 = new Pawn("black");
		chessBoard.addPiece(blackPawn5, 1, 4);
		
		Pawn blackPawn6 = new Pawn("black");
		chessBoard.addPiece(blackPawn6, 1, 5);
		
		Pawn blackPawn7 = new Pawn("black");
		chessBoard.addPiece(blackPawn7, 1, 6);
		
		Pawn blackPawn8 = new Pawn("black");
		chessBoard.addPiece(blackPawn8, 1, 7);
		
		Rook blackRook1 = new Rook("black");
		chessBoard.addPiece(blackRook1, 0, 0);
		
		Rook blackRook2 = new Rook("black");
		chessBoard.addPiece(blackRook2, 0, 7);
		
		Knight blackKnight1 = new Knight("black");
		chessBoard.addPiece(blackKnight1, 0, 1);
		
		Knight blackKnight2 = new Knight("black");
		chessBoard.addPiece(blackKnight2, 0, 6);
		
		Bishop blackBishop1 = new Bishop("black");
		chessBoard.addPiece(blackBishop1, 0, 2);
		
		Bishop blackBishop2 = new Bishop("black");
		chessBoard.addPiece(blackBishop2, 0, 5);
		
		Queen blackQueen = new Queen("black");
		chessBoard.addPiece(blackQueen, 0, 3);
		
		King blackKing = new King("black");
		chessBoard.addPiece(blackKing, 0, 4);
	}
	
	public void dispose()
	{
		templateFrame.dispose();
	}
	
	void randomWhiteDraft()
	{
		boolean[][] takenTiles = new boolean[8][8];
		int randPiece = 6; 	// start with king
		int randRow = 7; 		// on back row
		int randCol = rand.nextInt(8);
		
		Piece addPiece = null; 
		
		Arrays.fill(takenTiles[0], false);
		Arrays.fill(takenTiles[1], false);
		
		addPiece = selectWhitePiece(randPiece);
		takenTiles[randRow][randCol] = true;
		chessBoard.addPiece(addPiece, randRow, randCol);
		randPiece = (rand.nextInt(11) + 1); // 1 - 11, positive
		
		for (int i = 1; i < 16; i++) // start at 1 because king is already added.
		{
			while (takenTiles[randRow][randCol] == true)
			{
				randRow = rand.nextInt(2) + 6; // white
				randCol = rand.nextInt(8);
			}
			
			addPiece = selectWhitePiece(randPiece); // get random piece
			if (addPiece.getPieceID() == 6)
			{
				break;
			}
			else
			{
				takenTiles[randRow][randCol] = true; // set array location as taken
				chessBoard.addPiece(addPiece, randRow, randCol); // add piece to board
				randPiece = (rand.nextInt(11) + 1); // 1 - 11, positive
			}
		}
	}
	
	private Piece selectWhitePiece(int randID)
	{
		int pieceID = randID;
		Piece replacementPiece = null;
		boolean insufficientFunds = true;
		boolean done = false;
		int count = 0;
		
		int draftPoints = chessBoard.getWhiteDraftPoints();
		
		while (insufficientFunds && !done)
		{
			count++;
			if (pieceID == 1 && draftPoints -1 >= 0)
			{
				replacementPiece = new Pawn("white");
				insufficientFunds = false;		
				chessBoard.setWhiteDraftPoints(draftPoints -1);
			}
			else if (pieceID == 2 && draftPoints -3 >= 0)
			{
				replacementPiece = new Knight("white");
				insufficientFunds = false;		
				chessBoard.setWhiteDraftPoints(draftPoints -3);
			}
			else if (pieceID == 3 && draftPoints -3 >= 0)
			{
				replacementPiece = new Bishop("white");
				insufficientFunds = false;		
				chessBoard.setWhiteDraftPoints(draftPoints -3);
			}
			else if (pieceID == 4 && draftPoints -5 >= 0)
			{
				replacementPiece = new Rook("white");
				insufficientFunds = false;		
				chessBoard.setWhiteDraftPoints(draftPoints -5);
			}
			else if (pieceID == 5 && draftPoints -9 >= 0)
			{
				replacementPiece = new Queen("white");
				insufficientFunds = false;		
				chessBoard.setWhiteDraftPoints(draftPoints -9);
			}
			else if (pieceID == 6)
			{
				// never add another king
				if (!whiteKingAdded)
				{
					replacementPiece = new King("white");
					insufficientFunds = false;
					whiteKingAdded = true;
				}
				
			}
			else if (pieceID == 7 && draftPoints -3 >= 0)
			{
				replacementPiece = new FalseKing("white");
				insufficientFunds = false;		
				chessBoard.setWhiteDraftPoints(draftPoints -3);
			}
			else if (pieceID == 8 && draftPoints -4 >= 0)
			{
				replacementPiece = new Archer("white");
				insufficientFunds = false;		
				chessBoard.setWhiteDraftPoints(draftPoints -4);
			}
			else if (pieceID == 9  && draftPoints -2 >= 0)
			{
				replacementPiece = new Squire("white");
				insufficientFunds = false;		
				chessBoard.setWhiteDraftPoints(draftPoints -2);
			}
			else if (pieceID == 10  && draftPoints -6 >= 0)
			{
				replacementPiece = new Incendiary("white");
				insufficientFunds = false;		
				chessBoard.setWhiteDraftPoints(draftPoints -6);
			}
			else if (pieceID == 11  && draftPoints -4 >= 0)
			{
				replacementPiece = new Devourer("white");
				insufficientFunds = false;		
				chessBoard.setWhiteDraftPoints(draftPoints -4);
			}
			
			if (insufficientFunds)
			{
				pieceID = (rand.nextInt(15) + 1); // 1 - 11, positive
			}
			
			if (count >= 10 || draftPoints == 0)
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
	
	void randomBlackDraft()
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
				randRow = rand.nextInt(2); // white
				randCol = rand.nextInt(8);
			}
			
			addPiece = selectBlackPiece(randPiece); // get random piece
			if (addPiece.getPieceID() == -6)
			{
				break;
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
				chessBoard.setBlackDraftPoints(draftPoints -1);
			}
			else if (pieceID == -2 && draftPoints -3 >= 0)
			{
				replacementPiece = new Knight("black");
				insufficientFunds = false;		
				chessBoard.setBlackDraftPoints(draftPoints -3);
			}
			else if (pieceID == -3 && draftPoints -3 >= 0)
			{
				replacementPiece = new Bishop("black");
				insufficientFunds = false;		
				chessBoard.setBlackDraftPoints(draftPoints -3);
			}
			else if (pieceID == -4 && draftPoints -5 >= 0)
			{
				replacementPiece = new Rook("black");
				insufficientFunds = false;		
				chessBoard.setBlackDraftPoints(draftPoints -5);
			}
			else if (pieceID == -5 && draftPoints -9 >= 0)
			{
				replacementPiece = new Queen("black");
				insufficientFunds = false;		
				chessBoard.setBlackDraftPoints(draftPoints -9);
			}
			else if (pieceID == -6)
			{
				// never add another king
				if (!blackKingAdded)
				{
					replacementPiece = new King("black");
					insufficientFunds = false;
					blackKingAdded = true;
				}
				
			}
			else if (pieceID == -7 && draftPoints -3 >= 0)
			{
				replacementPiece = new FalseKing("black");
				insufficientFunds = false;		
				chessBoard.setBlackDraftPoints(draftPoints -3);
			}
			else if (pieceID == -8 && draftPoints -4 >= 0)
			{
				replacementPiece = new Archer("black");
				insufficientFunds = false;		
				chessBoard.setBlackDraftPoints(draftPoints -4);
			}
			else if (pieceID == -9  && draftPoints -2 >= 0)
			{
				replacementPiece = new Squire("black");
				insufficientFunds = false;		
				chessBoard.setBlackDraftPoints(draftPoints -2);
			}
			else if (pieceID == -10  && draftPoints -6 >= 0)
			{
				replacementPiece = new Incendiary("black");
				insufficientFunds = false;		
				chessBoard.setBlackDraftPoints(draftPoints -6);
			}
			else if (pieceID == -11  && draftPoints -4 >= 0)
			{
				replacementPiece = new Devourer("black");
				insufficientFunds = false;		
				chessBoard.setBlackDraftPoints(draftPoints -4);
			}
			
			if (insufficientFunds)
			{
				pieceID = (rand.nextInt(15) + 1) * -1; // 1 - 11, negative
			}
			
			if (count >= 10 || draftPoints == 0)
			{
				done = true;
			}
		} 
		
		if (done)
		{
			return new King("black");
		}
		
		return replacementPiece;
	}
}

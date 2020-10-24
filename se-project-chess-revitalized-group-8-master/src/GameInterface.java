import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;


import javax.swing.BorderFactory;
import javax.swing.JPanel;

class GameInterface extends JPanel implements java.io.Serializable
{	
	private static final long serialVersionUID = 1234567L;
	private Player player1;
	private Player player2;
	private GameInformation infoPanel;
	private Board chessBoard;
	private transient DraftPhase draft;
	private JPanel draftPanel;
	private boolean timedMatch;
	private String timeLimit = "10:00";
	private Game gameController;
	
	GameInterface(boolean timedMatch, String timeLimit, boolean whiteMovesFirst, boolean computerOpponent, Game gameController, boolean draftPhase, boolean highlighting)
	{
		this.timedMatch = timedMatch;
		this.timeLimit = timeLimit;
		this.gameController = gameController;
		
        // Create a Border layout manager.
        setLayout(new BorderLayout());

        // Create Information panel
        infoPanel = new GameInformation(timedMatch, timeLimit, whiteMovesFirst, draftPhase, this);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createRaisedBevelBorder()); 
        infoPanel.setPreferredSize(new Dimension(120, 520));
        
        // Create players
        player1 = new Player("Player 1", infoPanel.getPlayersTimerLabel("Player 1"), timeLimit, this);
        
        if (computerOpponent)
        {
            player2 = new Player("Computer", infoPanel.getPlayersTimerLabel("Player 2"), timeLimit, this);
            
            infoPanel.removeAll();
        	infoPanel.passPlayers(player1, player2);
        	infoPanel.revalidate();
    		infoPanel.repaint();
        }
        else
        {
            player2 = new Player("Player 2", infoPanel.getPlayersTimerLabel("Player 2"), timeLimit, this);
            
        	infoPanel.passPlayers(player1, player2);
        }
        
        
        // Create chess board
        chessBoard = new Board(player1, player2, infoPanel, timedMatch, whiteMovesFirst, this, highlighting);
        chessBoard.setBorder(BorderFactory.createRaisedBevelBorder()); 
        chessBoard.generateBoard();
        System.out.println("Board Generated");
        
        // Add the components to the panel.
        add(chessBoard, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);
        
        
        if(draftPhase)
        {
        	draft = new DraftPhase(whiteMovesFirst, chessBoard, timedMatch, computerOpponent);
        	add(draft.getDraftPanel(), BorderLayout.WEST);
        	chessBoard.setDraftPhase(true); //modifies how tiles behave
        }
        else
        {
        	generateStandardFormation(chessBoard);
          
        	//test formations
	        //generateDevourerTest();						// remove
	        //generateIncendiaryTest();						// remove
	        //generateArcherTest();							// remove
	        //generatePawnPromotionFormation(chessBoard);	// remove
	        //generateCheckMate(chessBoard);				// remove
	        //generateSquireTest(chessBoard);				// remove
	        //generateFormationWithFalseKing(chessBoard);	// remove
	        //generateHighlightFormation(chessBoard);     	// remove
        }
  
        if (timedMatch)
        {
          // Start Timer
          infoPanel.changeToPlayersTimer(player1);
          if (whiteMovesFirst)
          {
        	  infoPanel.changeToPlayersTimer(player1);
          }
          else
          {
        	  infoPanel.changeToPlayersTimer(player2);
          }
        }
        
        if(timedMatch && draftPhase)
    	{
    		infoPanel.stopTimer();
    		draft.passInfoPanel(infoPanel);
    	}
  }   

	void gameOver(boolean winner, String playerName)
	{
		// game over stuff to be added here
		if (timedMatch)
		{
			infoPanel.gameOver();
		}
		System.out.println("Game Over...");
		gameController.gameOver(winner, playerName);
	}
	
	//REMOVE 
	//GENERATE ARCHER TESTING FORMATION
	void generateDevourerTest()
	{
		Devourer whiteDevourer = new Devourer("white");
		chessBoard.addPiece(whiteDevourer, 7, 4);
		
		Devourer blackDevourer = new Devourer("black");
		chessBoard.addPiece(blackDevourer, 0,2);
		
		Pawn whitePawn1 = new Pawn("white");
		chessBoard.addPiece(whitePawn1, 3, 2);
		
		Pawn whitePawn2 = new Pawn("white");
		chessBoard.addPiece(whitePawn2, 2, 2);
		
		Pawn blackPawn1 = new Pawn("black");
		chessBoard.addPiece(blackPawn1, 5, 4);
		
		Pawn blackPawn2 = new Pawn("black");
		chessBoard.addPiece(blackPawn2, 4, 4);
		
		King whiteKing = new King("white");
		chessBoard.addPiece(whiteKing, 7, 6);
		
		King blackKing = new King("black");
		chessBoard.addPiece(blackKing, 0, 4);
	}
	
	//REMOVE 
	//GENERATE ARCHER TESTING FORMATION
	void generateIncendiaryTest()
	{
		Incendiary whiteIncendiary = new Incendiary("white");
		chessBoard.addPiece(whiteIncendiary, 7, 4);
		
		Incendiary blackIncendiary = new Incendiary("black");
		chessBoard.addPiece(blackIncendiary, 0,2);
		
		Pawn blackPawn1 = new Pawn("black");
		chessBoard.addPiece(blackPawn1, 5, 4);
		
		Pawn blackPawn2 = new Pawn("black");
		chessBoard.addPiece(blackPawn2, 4, 4);
		
		King whiteKing = new King("white");
		chessBoard.addPiece(whiteKing, 7, 6);
		
		King blackKing = new King("black");
		chessBoard.addPiece(blackKing, 0, 4);
	}
	
	//REMOVE 
	//GENERATE ARCHER TESTING FORMATION
	void generateArcherTest()
	{
		Archer whiteArcher = new Archer("white");
		chessBoard.addPiece(whiteArcher, 7, 4);
		
		Pawn whitePawn1 = new Pawn("white");
		chessBoard.addPiece(whitePawn1, 3, 2);
		
		Pawn whitePawn2 = new Pawn("white");
		chessBoard.addPiece(whitePawn2, 2, 2);
		
		Archer blackArcher = new Archer("black");
		chessBoard.addPiece(blackArcher, 0,2);
		
		Pawn blackPawn1 = new Pawn("black");
		chessBoard.addPiece(blackPawn1, 5, 4);
		
		Pawn blackPawn2 = new Pawn("black");
		chessBoard.addPiece(blackPawn2, 4, 4);
		
		King whiteKing = new King("white");
		chessBoard.addPiece(whiteKing, 7, 6);
		
		King blackKing = new King("black");
		chessBoard.addPiece(blackKing, 0, 4);
	}
	
	//REMOVE 
	//GENERATE HIGHLIGHT TESTING FORMATION
	void generateHighlightFormation(Board chessBoard)
	{
		Pawn whitePawn1 = new Pawn("white");
		chessBoard.addPiece(whitePawn1, 1, 3);
		
		Knight whiteKnight1 = new Knight("white");
		chessBoard.addPiece(whiteKnight1, 7, 1);
		
		Rook whiteRook2 = new Rook("white");
		chessBoard.addPiece(whiteRook2, 7, 7);
		
		Bishop whiteBishop2 = new Bishop("white");
		chessBoard.addPiece(whiteBishop2, 7, 5);
		
		Queen whiteQueen = new Queen("white");
		chessBoard.addPiece(whiteQueen, 7, 3);
		
		King whiteKing = new King("white");
		chessBoard.addPiece(whiteKing, 7, 4);
		
		King blackKing = new King("black");
		chessBoard.addPiece(blackKing, 0, 4);
		
		Pawn blackPawn1 = new Pawn("black");
		chessBoard.addPiece(blackPawn1, 6, 2);
		
	}
	
	//REMOVE 
	//GENERATE PAWN TESTING FORMATION
	void generatePawnPromotionFormation(Board chessBoard)
	{
		Pawn whitePawn1 = new Pawn("white");
		chessBoard.addPiece(whitePawn1, 1, 3);
		
		Pawn whitePawn2 = new Pawn("white");
		chessBoard.addPiece(whitePawn2, 2, 6);
		
		Pawn blackPawn1 = new Pawn("black");
		chessBoard.addPiece(blackPawn1, 6, 2);
		
		Pawn blackPawn2 = new Pawn("black");
		chessBoard.addPiece(blackPawn2, 5, 6);
		
	}
	
	//REMOVE 
	//GENERATE FALSE KING TESTING FORMATION
	void generateFormationWithFalseKing(Board chessBoard)
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
		
		FalseKing whiteFalseKing = new FalseKing("white");
		chessBoard.addPiece(whiteFalseKing, 6, 7);
		
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
		
		FalseKing blackFalseKing = new FalseKing("black");
		chessBoard.addPiece(blackFalseKing, 1, 7);
		
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
	
	//REMOVE 
	//GENERATE CHECKMATE TESTING FORMATION
	void generateCheckMate(Board chessBoard)
	{
		Pawn whitePawn1 = new Pawn("white");
		chessBoard.addPiece(whitePawn1,1,3);
		
		Pawn whitePawn2 = new Pawn("white");
		chessBoard.addPiece(whitePawn2,2,6);
		
		King whiteKing = new King("white");
		chessBoard.addPiece(whiteKing, 7, 4);
		
		Pawn blackPawn1 = new Pawn("black");
		chessBoard.addPiece(blackPawn1,6,2);
		
		Pawn blackPawn2 = new Pawn("black");
		chessBoard.addPiece(blackPawn2,5,6);
		
		King blackKing = new King("black");
		chessBoard.addPiece(blackKing, 0, 4);
		
	}
	
	//REMOVE 
	//GENERATE SQUIRE TESTING FORMATION
	void generateSquireTest(Board chessBoard)
	{
		Squire whiteSquire = new Squire("white");
		chessBoard.addPiece(whiteSquire, 6, 4);
		Squire blackSquire = new Squire("black");
		chessBoard.addPiece(blackSquire, 1, 4);
		
		Pawn whitePawn = new Pawn("white");
		chessBoard.addPiece(whitePawn, 2, 4);
		
		Pawn blackPawn = new Pawn("black");
		chessBoard.addPiece(blackPawn, 5, 4);
		
		King whiteKing = new King("white");
		chessBoard.addPiece(whiteKing, 7, 4);
		
		King blackKing = new King("black");
		chessBoard.addPiece(blackKing, 0, 4);		
		
	}
	
	void generateStandardFormation(Board chessBoard)
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
	
	
	Board getChessBoard()
	{
		return this.chessBoard;
	}
	
	Game getGame()
	{
		return gameController;
	}
	
	GameInformation getGameInfo()
	{
		return infoPanel;
	}
	
	void playerInCheck(boolean playerInCheck, Player player1, Player player2)
	{
		infoPanel.removeAll();
		infoPanel.playerInCheck(playerInCheck, player1, player2);
		infoPanel.revalidate();
		infoPanel.repaint();
	}
}
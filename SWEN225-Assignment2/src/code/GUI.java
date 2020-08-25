package code;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import code.Game.GameState;



public class GUI implements Observer{
	private static int scale = 1;
	private static int menuHeight = 30;
	private static int boardHeight = 400;
	private static int boardWidth = 400;
	private static int cardsWidth = 200;
	private static int interactionHeight = 200;
	
	private static Dimension windowDimension = new Dimension(cardsWidth+boardWidth+50, boardHeight+interactionHeight+50);
	private static Dimension menuDimension = new Dimension(cardsWidth+boardWidth, menuHeight);
	private static Dimension boardDimension = new Dimension(boardWidth, boardHeight);
	private static Dimension cardDimension = new Dimension(cardsWidth, boardHeight);
	private static Dimension interactionDimension = new Dimension(cardsWidth+boardWidth, interactionHeight);

	
	private Game game; 
	
	JFrame frame;	//root component of the GUI
	BoardPanel boardPanel;//Board, upon which the tileset and character/weapon icons are drawn

	SwitchPanel interactionPanel;

	public GUI(Board b, Game g) {//TODO REMOVE BOARD DEPENDENCY FROM CONSTRUCTOR AS SOON AS POSSIBLE
		
		
		this.game = g;
		frame = new JFrame("CLUEDO");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// lock window size if needed
		// TODO decide how to handle
		frame.setResizable(false);
		

		// make menu bar
		// TODO changing colour not working
		JMenuBar menuBar = new CluedoMenuBar();
		menuBar.setOpaque(true);
		menuBar.setBackground(Color.GREEN);
     	menuBar.setPreferredSize(menuDimension);

		// make game (enclosing) panel
		JPanel gamePanel = new JPanel();
		gamePanel.setOpaque(true);
		gamePanel.setBackground(Color.RED);
		gamePanel.setPreferredSize(windowDimension);

		// make init (enclosing) panel
		JPanel initPanel = new InitialisationPanel();
		initPanel.setOpaque(true);
		initPanel.setBackground(Color.PINK);
		initPanel.setPreferredSize(windowDimension);
		
		// make cards panel
		JPanel cardsPanel = new CardsPanel();
		cardsPanel.setOpaque(true);
		cardsPanel.setBackground(Color.YELLOW);
		cardsPanel.setPreferredSize(cardDimension);
		
		// make board panel
		boardPanel = new BoardPanel(b);//TODO REMOVE BOARD AS A REQUIREMENT AS SOON AS POSSIBLE
		boardPanel.setOpaque(true);
		boardPanel.setBackground(Color.ORANGE);
		boardPanel.setPreferredSize(boardDimension);
		
		// make interaction panel
		interactionPanel = new InteractionPanel(new CardLayout());
		interactionPanel.setOpaque(true);
		interactionPanel.setBackground(Color.BLUE);
		interactionPanel.setPreferredSize(interactionDimension);
		interactionPanel.switchToView("Moving");
				
		// add stuff to frame
		frame.setJMenuBar(menuBar);
		
		gamePanel.setLayout(new BorderLayout(0,0));
		gamePanel.add(cardsPanel, BorderLayout.WEST);
		gamePanel.add(boardPanel, BorderLayout.CENTER);
		gamePanel.add(interactionPanel, BorderLayout.SOUTH);
		
		// set up panel to switch between init and gameplay
		SwitchPanel panelSwitch = new SwitchPanel(new CardLayout());
		panelSwitch.add(initPanel, "Game Setup");
		panelSwitch.add(gamePanel, "Gameplay");
		frame.getContentPane().add(panelSwitch);
		panelSwitch.switchToView("Gameplay");
		
		// display
		frame.pack();
		frame.setVisible(true);
		doPlayerSetup();
		
		}
	

	@Override
	public void update(Observable o, Object arg) {
		
		switch(((Game)o).getGameState()) {
		case ACCUSING:
			interactionPanel.switchToView("Accusing");
			break;
		case SUGGESTING:
			interactionPanel.switchToView("Suggesting");
			break;
		case MOVING:
			interactionPanel.switchToView("Moving");
			break;
		case EXITING:
			interactionPanel.switchToView("Exiting");
			break;
		}
		
	}

	public void drawBoard(Board board, ArrayList<Location> cellsToHighlight){
		boardPanel.drawBoard(board, cellsToHighlight);
	}
	
	public void doPlayerSetup() {
		
		Object[] possibilities = {2, 3, 4, 5, 6};
		int numPlayers = (Integer)JOptionPane.showInputDialog(
		                    frame,
		                    "How many players will there be?",
		                    "Setup",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    possibilities,
		                    2);

		game.setNumPlayers(numPlayers);
		
		for(int i=0; i<numPlayers; i++) {
			String playerName = (String)JOptionPane.showInputDialog(
                    frame,
                    ("What is Player " + (i+1) + "'s name?"),
                    "Setup",
                    JOptionPane.OK_CANCEL_OPTION,
                    null,
                    null,
                    null);
			
			String playerCharacter = (String)JOptionPane.showInputDialog(
                    frame,
                    ("What character would you like to play, " + playerName+"?"),
                    "Setup",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    game.getUnassignedCharacters(),
                    null);
			
			game.createPlayer(playerName, playerCharacter);
		}
	}

}
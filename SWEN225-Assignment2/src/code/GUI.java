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



public class GUI extends JFrame implements Observer{
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

	public Player currentPlayer = null;
	
	BoardPanel boardPanel;//Board, upon which the tileset and character/weapon icons are drawn

	InteractionPanel interactionPanel;
	SwitchPanel panelSwitch;
	CardsPanel cardsPanel;
	
	CluedoMenuBar menuBar;
	private InputCollector collector;

	public GUI() {
		
		
		
		this.setTitle("CLUEDO");
		//frame = new JFrame("CLUEDO");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// lock window size if needed
		// TODO decide how to handle
		this.setResizable(false);
		

		// make menu bar
		// TODO changing colour not working
		menuBar = new CluedoMenuBar();
		menuBar.setOpaque(true);
		menuBar.setBackground(Color.GREEN);
     	menuBar.setPreferredSize(menuDimension);

     	// make init (enclosing) panel
		JPanel initPanel = new InitialisationPanel();
		initPanel.setOpaque(true);
		//initPanel.setBackground(Color.PINK);
		initPanel.setPreferredSize(windowDimension);
		
		
				
		// add stuff to frame
		this.setJMenuBar(menuBar);
		
		
		
		// set up panel to switch between init and gameplay
		panelSwitch = new SwitchPanel(new CardLayout());
		panelSwitch.add(initPanel, "Game Setup");
		
		this.getContentPane().add(panelSwitch);
		panelSwitch.switchToView("Game Setup");
		
		// display
		this.pack();
		this.setVisible(true);
		//doPlayerSetup();
		
		}
	
	
	public void addGame(Game g) {
		// if there is no current game
		if(this.game==null) {
			this.game = g;
		// if there is a current game, confirm discarding it
		}else {
			int exitChoice = JOptionPane.showConfirmDialog(
				    this.getParent(),
				    "Are you sure you wish to discard the current game?",
				    "Start New Game",
				    JOptionPane.YES_NO_OPTION);
			if(exitChoice==0) {
				this.game = g;
			}
		}
		
		
	}
	
	public void doGamePanelsSetup() {
		
		// make game (enclosing) panel
		JPanel gamePanel = new JPanel();
		gamePanel.setOpaque(true);
		gamePanel.setBackground(Color.RED);
		gamePanel.setPreferredSize(windowDimension);
				
		// make cards panel
		cardsPanel = new CardsPanel();
		cardsPanel.setOpaque(true);
		//cardsPanel.setBackground(Color.YELLOW);
		cardsPanel.setPreferredSize(cardDimension);
				
		// make board panel
		boardPanel = new BoardPanel(game.getBoard(),this);
		boardPanel.setOpaque(true);
		boardPanel.setBackground(Color.ORANGE);
		boardPanel.setPreferredSize(boardDimension);
				
		// make interaction panel
		interactionPanel = new InteractionPanel(new CardLayout());
		interactionPanel.setOpaque(true);
		//interactionPanel.setBackground(Color.GRAY);
		interactionPanel.setPreferredSize(interactionDimension);
		interactionPanel.switchToView("Moving");
		
		gamePanel.setLayout(new BorderLayout(0,0));
		gamePanel.add(cardsPanel, BorderLayout.WEST);
		gamePanel.add(boardPanel, BorderLayout.CENTER);
		gamePanel.add(interactionPanel, BorderLayout.SOUTH);
		
		panelSwitch.add(gamePanel, "Gameplay");
		
		
		// display
		this.pack();
		this.setVisible(true);
		
	}
	
	public void addCollector(InputCollector c) {
		this.collector = c;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof GameState) {
			switch(((Game)o).getGameState()) {
			case SETTING_UP:
				doPlayerSetup((Game)o);
				doGamePanelsSetup();
				panelSwitch.switchToView("Gameplay");
				break;
			case ACCUSING:
				interactionPanel.switchToView("Accusing");
				break;
			case SUGGESTING:
				interactionPanel.switchToView("Suggesting");
				collector.setWorkStateTo(Game.WorkState.WAITING);
				break;
			case MOVING:
				interactionPanel.switchToView("Moving");
				break;
			case EXITING:
				interactionPanel.switchToView("Exiting");
				break;
			case CONFIRMING_SUGGESTION:
				interactionPanel.switchToView("Confirming Suggestion");
				interactionPanel.suggestionConfirmingPanel.resetText();
				cardsPanel.disableInvalidCards((Game)o);
				break;
			}
		}else if(arg instanceof Player) {
			menuBar.updatePlayerLabel((Player)arg);
			interactionPanel.updatePlayer((Player)arg);
			cardsPanel.updateCards((Game)o, (Player)arg);
			
		}else if(arg instanceof Integer){
			interactionPanel.updateMovesLeft((Integer) arg);
		}
		
	}

	public void drawBoard(Board board, ArrayList<Location> cellsToHighlight){
		boardPanel.drawBoard(board, cellsToHighlight);
	}
	
	public void doPlayerSetup(Game g) {
		
		Object[] possibilities = {2, 3, 4, 5, 6};
		int numPlayers = (Integer)JOptionPane.showInputDialog(
		                    this,
		                    "How many players will there be?",
		                    "Setup",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    possibilities,
		                    2);

		g.setNumPlayers(numPlayers);
		
		for(int i=0; i<numPlayers; i++) {
			String playerName = (String)JOptionPane.showInputDialog(
                    this,
                    ("What is Player " + (i+1) + "'s name?"),
                    "Setup",
                    JOptionPane.OK_CANCEL_OPTION,
                    null,
                    null,
                    null);
			
			String playerCharacter = (String)JOptionPane.showInputDialog(
                    this,
                    ("What character would you like to play, " + playerName+"?"),
                    "Setup",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    game.getUnassignedCharacters(),
                    null);
			
			g.createPlayer(playerName, playerCharacter);
		}
	}

	public Player getPlayer(){
		return currentPlayer;
	}

	public Game getGame(){ return game; }

	public boolean isCollectorWaiting(){return (collector.getWorkState()== Game.WorkState.WAITING);}

	public void setCollectorState(Game.WorkState s){
		collector.setWorkStateTo(s);
	}

	public void setCollectorInput(Object o){
		collector.addInput(o);
	}

}
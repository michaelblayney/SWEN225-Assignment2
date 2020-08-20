package code;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.concurrent.TimeUnit;

import javax.swing.*;



public class GUI {
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


	public GUI() {
		JFrame frame = new JFrame("CLUEDO");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// lock window size if needed
		// TODO decide how to handle
		// frame.setResizable(false);


		// make menu bar
		// TODO changing colour not working
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setBackground(Color.GREEN);
     	menuBar.setPreferredSize(menuDimension);
		JMenuItem menuItemNew = new JMenuItem("New Game");
		JMenuItem menuItemExit = new JMenuItem("Exit");
     	menuBar.add(menuItemNew);
     	menuBar.add(menuItemExit);


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
		JPanel boardPanel = new BoardPanel();
		boardPanel.setOpaque(true);
		boardPanel.setBackground(Color.ORANGE);
		boardPanel.setPreferredSize(boardDimension);

		// make interaction panel
		JPanel interactionPanel = new InteractionPanel();
		interactionPanel.setOpaque(true);
		interactionPanel.setBackground(Color.BLUE);
		interactionPanel.setPreferredSize(interactionDimension);

		// add stuff to frame
		frame.setJMenuBar(menuBar);

		gamePanel.setLayout(new BorderLayout(0,0));
		gamePanel.add(cardsPanel, BorderLayout.WEST);
		gamePanel.add(boardPanel, BorderLayout.CENTER);
		gamePanel.add(interactionPanel, BorderLayout.SOUTH);

		SwitchPanel panelSwitch = new SwitchPanel(new CardLayout());
		panelSwitch.add(initPanel, "Game Setup");
		panelSwitch.add(gamePanel, "Gameplay");
		frame.getContentPane().add(panelSwitch);
		((CardLayout) panelSwitch.getLayout()).show(panelSwitch, "Game Setup");

		// display
		frame.pack();
		frame.setVisible(true);

		}

}
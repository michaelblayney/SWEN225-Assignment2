package code;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

import code.Game.GameState;

/* Custom JPanel class to display cards in current player's hand
 *  and possibly receive mouse input.
 *  
 *  gotta figure out how to get access to the game in order to use the getcurrentplayer()
 *  to get the cards.
 */
public class CardsPanel extends JPanel implements Observer, ActionListener{
	

	private static int cardsWidth = 200;
	private static int cardsHeight = 400;
	private static int topGap = 70;
	private static Dimension cardHolder = new Dimension(cardsWidth-20, 100);
	JPanel wCards, cCards, lCards;

	JPanel currentPlayerTest;
	Player currentPlayer;
	Game game;
	Graphics g;
	GUI gui;
	
	ArrayList<Card> wList;
	ArrayList<Card> cList;
	ArrayList<Card> lList;
	
	ArrayList<Card> validCards;
	
	public CardsPanel() {
		
		
		
		JButton button;
		JLabel label;
		
		this.setLayout(new GridBagLayout());
		
		label = new JLabel("Cards");
		
		wCards = new JPanel();
		cCards = new JPanel();
		lCards = new JPanel();
		
		wCards.setBackground(Color.WHITE);
		wCards.setPreferredSize(cardHolder);
		cCards.setBackground(Color.WHITE);
		cCards.setPreferredSize(cardHolder);
		lCards.setBackground(Color.WHITE);
		lCards.setPreferredSize(cardHolder);
		
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridheight = 8;
		constraints.gridwidth = 3;
		
		constraints.weightx = 0;
		constraints.weighty = 1;	
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 0, 0, 0);
		constraints.anchor = GridBagConstraints.PAGE_START;
		add(label, constraints);

		label = new JLabel("Your hand:");
		constraints.weightx = 1;	
		constraints.weighty = 1;
		constraints.gridx = 0;	
		constraints.gridy = 1;
		constraints.insets = new Insets(15, 0, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(label, constraints);
		
		label = new JLabel("Weapons:");
		constraints.gridx = 0;	
		constraints.gridy = 2;
		constraints.insets = new Insets((topGap), 0, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(label, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets((topGap) + (cardHolder.height*0) + 15, 10, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(wCards, constraints);
		
		label = new JLabel("Characters:");
		constraints.gridx = 0;	
		constraints.gridy = 4;
		constraints.insets = new Insets((topGap) + cardHolder.height + 20, 0, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(label, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.insets = new Insets((topGap) + cardHolder.height + 35, 10, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(cCards, constraints);
		
		label = new JLabel("Locations:");
		constraints.gridx = 0;	
		constraints.gridy = 6;
		constraints.insets = new Insets((topGap) + (cardHolder.height * 2) + 40, 0, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(label, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.insets = new Insets((topGap) + (cardHolder.height * 2) + 55, 10, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(lCards, constraints);
		
		
		 
		  
		//testing uncomment to test panel gets player
		/*
		currentPlayerTest = new JPanel();
		currentPlayerTest.setPreferredSize(new Dimension(100,30));
		constraints.insets = new Insets(-30,0,0,0);
		constraints.anchor = GridBagConstraints.SOUTH;
		add(currentPlayerTest, constraints);
		*/
		
	}
	
	/*
	 * TODO: actually need to find out how to get this panel to update in the first place.
	 * TODO: find out how to get the current player.
	 * */
	public void updateCards(Game g, Player p) {
		
		
		wCards.removeAll();
		cCards.removeAll();
		lCards.removeAll();
		//currentPlayerTest.removeAll(); // uncomment to test panel gets player
		
		currentPlayer = p;
		if (currentPlayer == null) {
			this.repaint();
			this.revalidate();
			return; 
		}
		
		
		//currentPlayerTest.add(new JLabel(p.getIRLname()));  //uncomment to test panel gets player
		
		ArrayList<Card> hand = currentPlayer.getCards();
		wList = new ArrayList<Card>(); //weapons
		cList = new ArrayList<Card>(); //character
		lList = new ArrayList<Card>(); //locations/rooms
		
		//sort hand into lists of weapons, characters and locations
		
		for (Card c : hand) {
			if (c instanceof WeaponCard) {
				wList.add(c);
				
			} else if(c instanceof CharacterCard) {
				cList.add(c);
			} else if(c instanceof RoomCard) {
				lList.add(c);
			}
		}
		
		
		
		drawCards(wList, wCards);
		drawCards(cList, cCards);
		drawCards(lList, lCards);
		
		
		//Test stuff down here - delete later
		/*
		//weapon cards
		g = wCards.getGraphics();
		g.setColor(Color.black);
		g.fillRect(10, 5, 20, 40);
		
		//character cards
		g = cCards.getGraphics();
		g.setColor(Color.black);
		g.fillRect(10, 5, 20, 40);
		
		//location cards
		g = lCards.getGraphics();
		g.setColor(Color.black);
		g.fillRect(10, 5, 20, 40);
		*/
		
		//dont know if this is how redrawing these things work
		this.repaint();
		this.revalidate();
		
	}
	
	/**
	 * Private method called by the updateCards Method that "draws" the cards/ writes the names of cards from a given list into a given panel*/
	private void drawCards(ArrayList<Card> l, JPanel p) {
		
		//All this is currently untested.
		
		GridBagConstraints constraints = new GridBagConstraints(); //used to put text where i want it to go
		JButton cardButton;  //just using text for now, can change later if buttons or radios are wanted
		int x = 5; 	//inset value for the x axis
		int y = 10;	//inset value for the y axis
		
		for (int i = 0; i <l.size() ; i++) {
			Card c = l.get(i);
			if (i == 3) { x = 95; y = 10;} //adjusts the x and y values when reaching the 4th card to move across 1 and back to the top
			constraints.insets = new Insets(y, x, 0, 0); 
			cardButton = new JButton(c.getName()); 
			cardButton.addActionListener(this);
			p.add(cardButton, constraints); 
			if(validCards!=null&&!validCards.contains(c)) {
				cardButton.setEnabled(false);
			}
			
			y += 30; // pushes the y inset value for the next card (if there is one.)
		}
	}
	
	/*idea is to update when currentPlayer is changed in the Game, I think, don't think this is actually how it's supposed to work, think cardspanel would
	 * need to be added as an observer in the Game.
	 * 
	 * Might be in the GUI.update() where a switch-case could be made to for (arg instanceof Player) that calls the cardspanel.updateCards(Player p) somehow?
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof Player) {
			this.updateCards((Game)o, (Player) arg);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO suggesting?
		// e = the name of the card
		
		((GUI) this.getParent().getParent().getParent().getParent().getParent().getParent()).setCollectorInput(new Card(e.getActionCommand()));
		((GUI) this.getParent().getParent().getParent().getParent().getParent().getParent()).setCollectorState(Game.WorkState.NOT_WAITING);
	
		
	}
	//TODO restrict card choices somehow
	public void disableInvalidCards(Game g) {
		validCards = g.getValidSuggestCards();
	
	}

	
}

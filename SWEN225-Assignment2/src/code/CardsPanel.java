package code;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

/* Custom JPanel class to display cards in current player's hand
 *  and possibly receive mouse input.
 *  
 *  gotta figure out how to get access to the game in order to use the getcurrentplayer()
 *  to get the cards.
 */
public class CardsPanel extends JPanel {
	

	private static int cardsWidth = 200;
	private static int cardsHeight = 400;
	private static int topGap = 70;
	private static Dimension cardHolder = new Dimension(cardsWidth-20, 100);
	JPanel wCards, cCards, lCards;
	Player currentPlayer;
	Game game;
	Graphics g;
	
	public CardsPanel() {
		
		
		
		JButton button;
		JLabel label;
		
		this.setLayout(new GridBagLayout());
		
		label = new JLabel("Cards");
		
		wCards = new JPanel();
		cCards = new JPanel();
		lCards = new JPanel();
		
		wCards.setBackground(Color.red);
		wCards.setPreferredSize(cardHolder);
		cCards.setBackground(Color.green);
		cCards.setPreferredSize(cardHolder);
		lCards.setBackground(Color.BLUE.brighter());
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
		
		
	}
	
	/*
	 * TODO: actually need to find out how to get this panel to update in the first place.
	 * TODO: find out how to get the current player.
	 * */
	public void updateCards() {
		
		this.getParent().getParent().getParent().getParent();
		currentPlayer = game.getCurrentPlayer();
		if (currentPlayer == null) return; //minor safeguard
		
		ArrayList<Card> hand = currentPlayer.getCards();
		ArrayList<Card> wList = new ArrayList<Card>(); //weapons
		ArrayList<Card> cList = new ArrayList<Card>(); //character
		ArrayList<Card> lList = new ArrayList<Card>(); //locations/rooms
		
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
		
		this.revalidate();
	}
	
	/**
	 * Private method called by the updateCards Method that draws the cards/ writes the names of cards from a given list into a given panel*/
	private void drawCards(ArrayList<Card> l, JPanel p) {
		
		//All this is currently untested.
		
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel text;  //just using text for now, can change later
		int x = 5;
		int y = 10;
		
		for (int i = 0; i <l.size() ; i++) {
			Card c = l.get(i);
			if (i == 3) { x = 95; y = 10;} //adjusts the x and y values when reaching the 4th card to move across 1 and back to the top
			constraints.insets = new Insets(y, x, 0, 0); // set where the text 
			text = new JLabel(c.getName()); //put card name in label
			p.add(text, constraints); 
			y += 30;
		}
	}
	
}

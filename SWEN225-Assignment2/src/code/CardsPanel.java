package code;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;

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
	private static int topGap = 40;
	private static Dimension cardHolder = new Dimension(cardsWidth-20, 50);
	JPanel wCards, cCards, lCards;
	
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
		constraints.insets = new Insets(40, 0, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(label, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(55, 10, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(wCards, constraints);
		
		label = new JLabel("Characters:");
		constraints.gridx = 0;	
		constraints.gridy = 4;
		constraints.insets = new Insets((topGap*2) + cardHolder.height, 0, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(label, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.insets = new Insets((topGap*2) + cardHolder.height + 15, 10, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(cCards, constraints);
		
		label = new JLabel("Locations:");
		constraints.gridx = 0;	
		constraints.gridy = 6;
		constraints.insets = new Insets((topGap*3) + (cardHolder.height * 2), 0, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(label, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.insets = new Insets((topGap*3) + (cardHolder.height * 2) + 15, 10, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(lCards, constraints);
		
	}
	
}

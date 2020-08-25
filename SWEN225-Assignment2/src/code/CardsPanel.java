package code;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
	
	public CardsPanel() {
		
		JButton button;
		JLabel label;
		
		
		this.setLayout(new GridBagLayout());
		
		label = new JLabel("Cards");
		
		
		
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridheight = 8;
		constraints.gridwidth = 3;
		
		constraints.weightx = 0;
		constraints.weighty = 1;	// Removing vertical blank space
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 0, 0, 0);
		constraints.anchor = GridBagConstraints.PAGE_START;
		add(label, constraints);

		label = new JLabel("Your hand:");
		constraints.weightx = 1;	// Removing horizontal blank space
		constraints.weighty = 1;
		constraints.gridx = 0;	
		constraints.gridy = 1;
		constraints.insets = new Insets(15, 0, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(label, constraints);
		
		label = new JLabel("Weapons:");
		constraints.gridx = 0;	
		constraints.gridy = 2;

		constraints.insets = new Insets(30, 0, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(label, constraints);
		
		
	}
	
}

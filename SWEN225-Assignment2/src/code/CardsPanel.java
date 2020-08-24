package code;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
		
		
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		//constraints.insets = new Insets(5,290,0,0);  //Hardcoding now as having issues with finding width
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(label, constraints);

		label = new JLabel("Your hand:");
		constraints.weightx = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		//constraints.insets = new Insets(5,210,0,0);  //Hardcoding now as having issues with finding width
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(label, constraints);

		button = new JButton("Yes");
		constraints.gridwidth = 2;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(button, constraints);

		button = new JButton("No");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weighty = 1;
		constraints.gridx = 1;
		constraints.gridy = 2;
		add(button, constraints);
	}
	
}

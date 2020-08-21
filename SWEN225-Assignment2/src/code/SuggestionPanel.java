package code;

import javax.swing.*;
import java.awt.*;

public class SuggestionPanel extends JPanel {

	public SuggestionPanel() {

		JButton button;
		JLabel label;
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
//		if (shouldFill) {
//			//natural height, maximum width
//			c.fill = GridBagConstraints.HORIZONTAL;
//		}

		label = new JLabel("Suggesting");
		constraints.gridwidth = 1;
		constraints.weightx = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		//constraints.insets = new Insets(5,290,0,0);  //Hardcoding now as having issues with finding width
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(label, constraints);

		label = new JLabel("Would you like to  make a suggestion?");
		constraints.weightx = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		//constraints.insets = new Insets(5,210,0,0);  //Hardcoding now as having issues with finding width
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(label, constraints);

		button = new JButton("Yes");
		constraints.gridwidth = 2;
		constraints.weightx = 0.5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(button, constraints);

		button = new JButton("No");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.5;
		constraints.gridx = 1;
		constraints.gridy = 2;
		add(button, constraints);
//
//		button = new JButton("Button 3");
//		constraints.fill = GridBagConstraints.HORIZONTAL;
//		constraints.weightx = 0.5;
//		constraints.gridx = 2;
//		constraints.gridy = 1;
//		this.add(button, constraints);
//
//		button = new JButton("Long-Named Button 4");
//		constraints.fill = GridBagConstraints.HORIZONTAL;
//		constraints.ipady = 40;      //make this component tall
//		constraints.weightx = 0.0;
//		constraints.gridwidth = 3;
//		constraints.gridx = 1;
//		constraints.gridy = 2;
//		this.add(button, constraints);

		button = new JButton("Make Suggestion");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipady = 5;       //reset to default
		constraints.weighty = 1.0;   //request any extra vertical space
		constraints.anchor = GridBagConstraints.PAGE_END; //bottom of space
		constraints.insets = new Insets(10,10,10,10);  //top padding
		constraints.gridx = 1;       //aligned with button 2
		constraints.gridwidth = 2;   //2 columns wide
		constraints.gridy = 3;       //third row
		button.setEnabled(false);
		this.add(button, constraints);


	}
}

package code;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class NumPlayersDialog extends JDialog {

	public NumPlayersDialog() {
		
		JLabel numLabel = new JLabel("How many people want to play?");
		add(numLabel);
		
		String[] numOptions = { "2", "3", "4", "5", "6"};
		JComboBox numPlayersSelector = new JComboBox(numOptions);
		//numPlayersSelector.addActionListener(this);
		add(numPlayersSelector);
	}
	
	
}

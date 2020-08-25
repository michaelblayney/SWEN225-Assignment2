package code;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/* Custom JPanel class to handle the game setup eg. taking player names
 * and character selections.
 */
public class InitialisationPanel extends JPanel implements ActionListener {

	public InitialisationPanel() {
	
	JButton button = new JButton("Set up Game");
	add(button);
	button.addActionListener(this);
	
	JLabel numLabel = new JLabel("How many people want to play?");
	add(numLabel);
	
	String[] numOptions = { "2", "3", "4", "5", "6"};
	JComboBox numPlayersSelector = new JComboBox(numOptions);
	numPlayersSelector.addActionListener(this);
	add(numPlayersSelector);
	
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			
		
		if(this.getParent() instanceof SwitchPanel)
			((SwitchPanel) this.getParent()).switchToView("Gameplay");
	
		
	}
	
}

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
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(this.getParent() instanceof SwitchPanel)
			((SwitchPanel) this.getParent()).switchToGame();
	
	}
	
}

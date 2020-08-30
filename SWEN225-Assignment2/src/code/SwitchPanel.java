package code;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

/* Custom JPanel class to handle switching between initialisation
 * and gameplay.
 */
public class SwitchPanel extends JPanel {


	
	public SwitchPanel(CardLayout cardLayout) {
		setLayout(cardLayout);
	}


	public void switchToView(String s) {
		((CardLayout) getLayout()).show(this, s);
	}

    public void updatePlayer(Player arg) {

    }

	public void updateMovesLeft(Integer arg) {

	}
}

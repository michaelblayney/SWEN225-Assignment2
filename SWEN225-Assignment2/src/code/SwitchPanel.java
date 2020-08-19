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


	public void switchToGame() {
		((CardLayout) getLayout()).show(this, "Gameplay");
	}
	
	public void switchToInit() {
		((CardLayout) getLayout()).show(this, "Game Setup");
	}
	
}

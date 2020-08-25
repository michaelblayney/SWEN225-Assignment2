package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/* Custom JMenuBar class
 * TODO implement "New Game"
 * TODO figure out how to change colour?
 */
public class CluedoMenuBar extends JMenuBar implements ActionListener{

	private JButton menuItemExit;
	private JButton menuItemNew;
	
	public CluedoMenuBar() {
 	
 	/*menuItemNew = new JButton("New Game");
 	menuItemNew.addActionListener(this);
 	add(menuItemNew);*/
		
	//JLabel currPlayerLabel = new JLabel(currentPlayer + "'s turn");
 	
	menuItemExit = new JButton("Exit");
	menuItemExit.addActionListener(this);
 	add(menuItemExit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(menuItemExit)) {
			int exitChoice = JOptionPane.showConfirmDialog(
				    this.getParent(),
				    "Are you sure you wish to exit?",
				    "Exit CLUEDO",
				    JOptionPane.YES_NO_OPTION);
			if(exitChoice==0) {
				System.exit(0);
			}
		}/*else if(e.getSource().equals(menuItemNew)) {
			((GUI)this.getParent().getParent()).;
		}*/
		
	}
}
	

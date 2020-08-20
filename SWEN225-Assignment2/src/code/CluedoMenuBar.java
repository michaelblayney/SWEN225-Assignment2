package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/* Custom JMenuBar class
 * TODO implement "New Game"
 * TODO figure out how to change colour?
 */
public class CluedoMenuBar extends JMenuBar implements ActionListener{

	private JButton menuItemExit;
	private JButton menuItemNew;
	
	public CluedoMenuBar() {
 	
 	menuItemNew = new JButton("New Game");
 	menuItemNew.addActionListener(this);
 	add(menuItemNew);
 	
	menuItemExit = new JButton("Exit");
	menuItemExit.addActionListener(this);
 	add(menuItemExit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(menuItemExit)) {
			System.exit(0);
		}
		
	}
}
	

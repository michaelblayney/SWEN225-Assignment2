package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitPanel extends JPanel implements ActionListener {

	Player currentPlayer;
	String currentPlayerName = "";
	int movesLeft = 6;
	int roll = 9;
	JLabel nameLabel;

	public ExitPanel() {
		createExitPanel();
	}

	public void createExitPanel(){
		JLabel titleLabel, promptLabel;
		JButton exit1, exit2, exit3, exit4, endButton;
		GridBagConstraints constraints = new GridBagConstraints();

		setLayout(new GridBagLayout());

		titleLabel = new JLabel("Exiting");
		constraints.weighty = 1;	// Removing vertical blank space
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 0, 0, 0);
		constraints.anchor = GridBagConstraints.PAGE_START;
		add(titleLabel, constraints);

		promptLabel = new JLabel("Select which exit you would like to take");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 20;	// Move it up towards the title
		constraints.insets = new Insets(5, 0, 0, 0); // Reset insets
		constraints.anchor = GridBagConstraints.PAGE_START;	// Reset anchor
		add(promptLabel, constraints);

		exit1 = new JButton("Exit (1)");
		exit1.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.insets = new Insets(0, 0, 0, 0);	// Reset insets
		add(exit1, constraints);

		exit2 = new JButton("Exit (2)");
		exit1.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.insets = new Insets(0, 0, 0, 0);	// Reset insets
		add(exit2, constraints);

		exit3 = new JButton("Exit (3)");
		exit1.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.insets = new Insets(0, 0, 0, 0);	// Reset insets
		add(exit3, constraints);
		exit3.getModel().setEnabled(false); //TODO Implement these

		exit4 = new JButton("Exit (4)");
		exit1.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.insets = new Insets(0, 0, 30, 0);	// Reset insets
		add(exit4, constraints);
		exit4.getModel().setEnabled(false);

		endButton = new JButton("End turn");
		endButton.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.weighty = 1;
		constraints.weightx = 100;
		constraints.insets = new Insets(0, 0, 10, 10); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.LAST_LINE_END;	// Anchor right
		add(endButton, constraints);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

//			if(this.getParent() instanceof InteractionPanel)
//				((InteractionPanel) this.getParent()).switchToView("Moving");

		if(e.getActionCommand().equals("End turn")){
			if(this.getParent().getParent().getParent().getParent().getParent().getParent().getParent() instanceof GUI) {
				((GUI) this.getParent().getParent().getParent().getParent().getParent().getParent().getParent()).setCollectorInput('f');
				((GUI) this.getParent().getParent().getParent().getParent().getParent().getParent().getParent()).setCollectorState(Game.WorkState.NOT_WAITING);
			}
		}
	}

	public void updatePlayerName(Player p){
		currentPlayer = p;
	}
}

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
		JLabel titleLabel, promptLabel, rollLabel, movesLabel;
		JButton exit1, exit2, exit3, exit4;
		GridBagConstraints constraints = new GridBagConstraints();

		setLayout(new GridBagLayout());

		titleLabel = new JLabel("Exiting");
		constraints.weighty = 1;	// Removing vertical blank space
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 0, 0, 0);
		constraints.anchor = GridBagConstraints.PAGE_START;
		add(titleLabel, constraints);

		nameLabel = new JLabel(currentPlayerName + "'s turn");
		constraints.weightx = 1;	// Removing horizontal blank space
		constraints.gridx = 0;	// Both in the same grid square, just anchored differently
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 5, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		add(nameLabel, constraints);

		promptLabel = new JLabel("Select which exit you would like to take");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 20;	// Move it up towards the title
		constraints.insets = new Insets(5, 0, 0, 0); // Reset insets
		constraints.anchor = GridBagConstraints.PAGE_START;	// Reset anchor
		add(promptLabel, constraints);


		//Dont know if we need these while exiting
		rollLabel = new JLabel("You rolled: " + roll);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 100;	// Move it up towards the title
		constraints.insets = new Insets(4, 7, 0, 0); // Reset insets
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	// Reset anchor
		add(rollLabel, constraints);

		movesLabel = new JLabel("Moves left: " + movesLeft);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weighty = 5000;	// Move it up towards the title
		constraints.insets = new Insets(0, 6, 0, 0); // Reset insets
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	// Reset anchor
		add(movesLabel, constraints);

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
		exit3.disable();

		exit4 = new JButton("Exit (4)");
		exit1.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.anchor = GridBagConstraints.PAGE_START;
		constraints.insets = new Insets(0, 0, 30, 0);	// Reset insets
		add(exit4, constraints);
		exit4.disable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

//			if(this.getParent() instanceof InteractionPanel)
//				((InteractionPanel) this.getParent()).switchToView("Moving");
	}

	public void updatePlayerName(Player p){
		nameLabel.setText(p.getIRLname() + "'s turn");
	}
}

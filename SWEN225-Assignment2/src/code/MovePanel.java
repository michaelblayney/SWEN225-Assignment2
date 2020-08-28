package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovePanel extends JPanel implements ActionListener {

	String currentPlayerName = "Michael";	// Just for now
	int movesLeft = 6;
	int roll = 9;

	public MovePanel() {
		createMovePanel();
	}

	public void createMovePanel(){
		JLabel titleLabel, promptLabel, nameLabel, rollLabel, movesLabel;
		JButton northButton, southButton, eastButton, westButton, endButton, enterRoomButton;
		GridBagConstraints constraints = new GridBagConstraints();

		setLayout(new GridBagLayout());

		titleLabel = new JLabel("Moving");
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

		promptLabel = new JLabel("(Click a button or click on a tile to move to that location)");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 20;	// Move it up towards the title
		constraints.insets = new Insets(5, 0, 0, 0); // Reset insets
		constraints.anchor = GridBagConstraints.PAGE_START;	// Reset anchor
		add(promptLabel, constraints);

		rollLabel = new JLabel("You rolled: " + roll);
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 100;	// Move it up towards the title
		constraints.insets = new Insets(3, 7, 0, 0); // Reset insets
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	// Reset anchor
		add(rollLabel, constraints);

		movesLabel = new JLabel("Moves left: " + movesLeft);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weighty = 200;	// Move it up towards the title
		constraints.insets = new Insets(5, 0, 0, 0); // Reset insets
		constraints.anchor = GridBagConstraints.PAGE_START;	// Reset anchor
		add(movesLabel, constraints);

		northButton = new JButton("^");
		northButton.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weighty = 1;
		constraints.insets = new Insets(0, 0, 10, 10); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor right
		add(northButton, constraints);

		southButton = new JButton("v");
		southButton.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weighty = 1;
		constraints.insets = new Insets(0, 0, 10, 10); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor right
		add(southButton, constraints);

		eastButton = new JButton(">");
		eastButton.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weighty = 1;
		constraints.insets = new Insets(0, 130, 10, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor right
		add(eastButton, constraints);

		westButton = new JButton("<");
		westButton.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weighty = 1;
		constraints.insets = new Insets(0, 0, 10, 150); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor right
		add(westButton, constraints);

		endButton = new JButton("End turn");
		endButton.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.weighty = 1;
		constraints.insets = new Insets(0, 0, 10, 10); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.LAST_LINE_END;	// Anchor right
		add(endButton, constraints);

		enterRoomButton = new JButton("*Enters a room* (Testing)");
		enterRoomButton.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.weighty = 1;
		constraints.insets = new Insets(0, 0, 10, 100); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.LAST_LINE_END;	// Anchor right
		add(enterRoomButton, constraints);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if(e.getActionCommand().equals("*Enters a room* (Testing)")){
//			if(this.getParent() instanceof InteractionPanel)
//				((InteractionPanel) this.getParent()).switchToView("Suggesting");
//		}

		if(e.getActionCommand().equals("End turn")){
			if(this.getParent() instanceof InteractionPanel)
				((InteractionPanel) this.getParent()).switchToView("Moving");
		}
	}
}

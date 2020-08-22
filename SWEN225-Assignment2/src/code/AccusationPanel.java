package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccusationPanel extends JPanel implements ActionListener {

	JPanel questionPanel, characterPanel, roomPanel, weaponPanel;
	String currentPlayerName = "Michael";	// Just for now

	public AccusationPanel() {
		setLayout(new CardLayout()); // The SuggestionPanel has multiple panels it switches between
		createQuestionPanel();
		add(questionPanel, "Question");
		createCharacterPanel();
		add(characterPanel, "Character");
		createRoomPanel();
		add(roomPanel, "Room");
		createWeaponPanel();
		add(weaponPanel, "Weapon");
	}

	public void createQuestionPanel(){
		questionPanel = new JPanel();
		JLabel titleLabel, promptLabel, nameLabel;
		JButton yesButton, noButton;
		GridBagConstraints constraints = new GridBagConstraints();

		questionPanel.setLayout(new GridBagLayout());

		titleLabel = new JLabel("Accusing");
		constraints.weighty = 1;	// Removing vertical blank space
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 0, 0, 0);
		constraints.anchor = GridBagConstraints.PAGE_START;
		questionPanel.add(titleLabel, constraints);

		nameLabel = new JLabel(currentPlayerName + "'s turn");
		constraints.weightx = 1;	// Removing horizontal blank space
		constraints.gridx = 0;	// Both in the same grid square, just anchored differently
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 5, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		questionPanel.add(nameLabel, constraints);

		promptLabel = new JLabel("Would you like to make an accusation?");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 20;	// Move it up towards the title
		constraints.insets = new Insets(5, 0, 0, 0); // Reset insets
		constraints.anchor = GridBagConstraints.PAGE_START;	// Reset anchor
		questionPanel.add(promptLabel, constraints);

		yesButton = new JButton("Yes");
		yesButton.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weighty = 100; // Move it up as far as possible
		constraints.insets = new Insets(0, 0, 0, 80);	// Separate buttons as they are in same square
		questionPanel.add(yesButton, constraints);

		noButton = new JButton("No");
		noButton.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weighty = 100;
		constraints.insets = new Insets(0, 80, 0, 0);	// Separate buttons as they are in same square
		questionPanel.add(noButton, constraints);
	}

	public void createCharacterPanel(){
		characterPanel = new JPanel();
		JLabel titleLabel, promptLabel, nameLabel;
		JRadioButton mrsWhiteRB, mrGreenRB, mrsPeacockRB, profPlumRB, msScarletRB, colMustardRB;
		JButton confirmCharacter;
		GridBagConstraints constraints = new GridBagConstraints();

		characterPanel.setLayout(new GridBagLayout());

		titleLabel = new JLabel("Accusing");
		constraints.weighty = 1;	// Removing vertical blank space
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 0, 0, 0);
		constraints.anchor = GridBagConstraints.PAGE_START;
		characterPanel.add(titleLabel, constraints);

		nameLabel = new JLabel(currentPlayerName + "'s turn");
		constraints.weightx = 1;	// Removing horizontal blank space
		constraints.gridx = 0;	// Both in the same grid square, just anchored differently
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 5, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		characterPanel.add(nameLabel, constraints);

		promptLabel = new JLabel("Who do you accuse?");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 20;	// Move it up towards the title
		constraints.insets = new Insets(5, 0, 0, 0); // Reset insets
		constraints.anchor = GridBagConstraints.PAGE_START;	// Reset anchor
		characterPanel.add(promptLabel, constraints);

		mrsWhiteRB = new JRadioButton("Mrs. White");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weighty = 100;	// Affects the distance between 2 rows of radio buttons
		constraints.insets = new Insets(15, 120, 0, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	// Anchor left
		characterPanel.add(mrsWhiteRB, constraints);

		mrGreenRB = new JRadioButton("Mr. Green");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(15, 0, 0, 10); //
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor middle
		characterPanel.add(mrGreenRB, constraints);

		mrsPeacockRB = new JRadioButton("Mrs. Peacock");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(15, 0, 0, 121); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;	// Anchor right
		characterPanel.add(mrsPeacockRB, constraints);

		profPlumRB = new JRadioButton("Professor Plum");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weighty = 200;	//Push the two rows of radio buttons up
		constraints.insets = new Insets(0, 120, 0, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	// Anchor left
		characterPanel.add(profPlumRB, constraints);

		msScarletRB = new JRadioButton("Miss Scarlet");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(0, 14, 0, 10); // Temp fix on alignment errors
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor middle
		characterPanel.add(msScarletRB, constraints);

		colMustardRB = new JRadioButton("Colonel Mustard");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(0, 0, 0, 100); // Temp fix on alignment errors
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;	// Anchor right
		characterPanel.add(colMustardRB, constraints);

		confirmCharacter = new JButton("Accuse selected character");
		confirmCharacter.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weighty = 1;
		constraints.insets = new Insets(0, 0, 10, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor right
		characterPanel.add(confirmCharacter, constraints);
	}

	public void createRoomPanel(){
		roomPanel = new JPanel();
		JLabel titleLabel, promptLabel, nameLabel;
		JRadioButton BallRB, ConservatoryRB, BilliardRB, LibraryRB, StudyRB, HallRB, LoungeRB, DiningRB, KitchenRB;
		JButton confirmRoom;
		GridBagConstraints constraints = new GridBagConstraints();

		roomPanel.setLayout(new GridBagLayout());

		titleLabel = new JLabel("Accusing");
		constraints.weighty = 1;	// Removing vertical blank space
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 0, 0, 0);
		constraints.anchor = GridBagConstraints.PAGE_START;
		roomPanel.add(titleLabel, constraints);

		nameLabel = new JLabel(currentPlayerName + "'s turn");
		constraints.weightx = 1;	// Removing horizontal blank space
		constraints.gridx = 0;	// Both in the same grid square, just anchored differently
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 5, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		roomPanel.add(nameLabel, constraints);

		promptLabel = new JLabel("What room was the murder in?");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 20;	// Move it up towards the title
		constraints.insets = new Insets(5, 0, 0, 0); // Reset insets
		constraints.anchor = GridBagConstraints.PAGE_START;	// Reset anchor
		roomPanel.add(promptLabel, constraints);

		BallRB = new JRadioButton("Ball Room");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weighty = 100;	// Affects the distance between 2 rows of radio buttons
		constraints.insets = new Insets(15, 120, 0, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	// Anchor left
		roomPanel.add(BallRB, constraints);

		ConservatoryRB = new JRadioButton("Conservatory");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(15, 0, 0, 10); //
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor middle
		roomPanel.add(ConservatoryRB, constraints);

		BilliardRB = new JRadioButton("Billiard Room");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(15, 0, 0, 121); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;	// Anchor right
		roomPanel.add(BilliardRB, constraints);

		LibraryRB = new JRadioButton("Library");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weighty = 200;	//Push the two rows of radio buttons up
		constraints.insets = new Insets(3, 120, 0, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	// Anchor left
		roomPanel.add(LibraryRB, constraints);

		StudyRB = new JRadioButton("Study");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(3, 0, 0, 58); // Temp fix on alignment errors
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor middle
		roomPanel.add(StudyRB, constraints);

		HallRB = new JRadioButton("Hall");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(3, 0, 0, 179); // Temp fix on alignment errors
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;	// Anchor right
		roomPanel.add(HallRB, constraints);

		//
		LoungeRB = new JRadioButton("Lounge");
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weighty = 800;	//Push the two rows of radio buttons up
		constraints.insets = new Insets(0, 120, 10, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	// Anchor left
		roomPanel.add(LoungeRB, constraints);

		DiningRB = new JRadioButton("Dining Room");
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.insets = new Insets(0, 0, 10, 12); // Temp fix on alignment errors
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor middle
		roomPanel.add(DiningRB, constraints);

		KitchenRB = new JRadioButton("Kitchen");
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.insets = new Insets(0, 0, 10, 157); // Temp fix on alignment errors
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;	// Anchor right
		roomPanel.add(KitchenRB, constraints);
		//

		confirmRoom = new JButton("Accuse selected room");
		confirmRoom.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.weighty = 1;
		constraints.insets = new Insets(0, 0, 10, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor right
		roomPanel.add(confirmRoom, constraints);
	}

	public void createWeaponPanel(){
		weaponPanel = new JPanel();
		JLabel titleLabel, promptLabel, nameLabel;
		JRadioButton candleRB, pipeRB, daggerRB, revolverRB, ropeRB, spannerRB;
		JButton confirmWeapon;
		GridBagConstraints constraints = new GridBagConstraints();

		weaponPanel.setLayout(new GridBagLayout());

		titleLabel = new JLabel("Accusing");
		constraints.weighty = 1;	// Removing vertical blank space
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 0, 0, 0);
		constraints.anchor = GridBagConstraints.PAGE_START;
		weaponPanel.add(titleLabel, constraints);

		nameLabel = new JLabel(currentPlayerName + "'s turn");
		constraints.weightx = 1;	// Removing horizontal blank space
		constraints.gridx = 0;	// Both in the same grid square, just anchored differently
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 5, 0, 0);
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		weaponPanel.add(nameLabel, constraints);

		promptLabel = new JLabel("What weapon did they use?");
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weighty = 20;	// Move it up towards the title
		constraints.insets = new Insets(5, 0, 0, 0); // Reset insets
		constraints.anchor = GridBagConstraints.PAGE_START;	// Reset anchor
		weaponPanel.add(promptLabel, constraints);

		candleRB = new JRadioButton("Candlestick");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.weighty = 100;	// Affects the distance between 2 rows of radio buttons
		constraints.insets = new Insets(15, 120, 0, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	// Anchor left
		weaponPanel.add(candleRB, constraints);

		pipeRB = new JRadioButton("Lead Pipe");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(15, 0, 0, 10); //
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor middle
		weaponPanel.add(pipeRB, constraints);

		daggerRB = new JRadioButton("Dagger");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(15, 0, 0, 121); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;	// Anchor right
		weaponPanel.add(daggerRB, constraints);

		revolverRB = new JRadioButton("Revolver");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weighty = 200;	//Push the two rows of radio buttons up
		constraints.insets = new Insets(0, 120, 0, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	// Anchor left
		weaponPanel.add(revolverRB, constraints);

		ropeRB = new JRadioButton("Rope");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(0, 0, 0, 36); // Temp fix on alignment errors
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor middle
		weaponPanel.add(ropeRB, constraints);

		spannerRB = new JRadioButton("Spanner");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(0, 0, 0, 116); // Temp fix on alignment errors
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;	// Anchor right
		weaponPanel.add(spannerRB, constraints);

		confirmWeapon = new JButton("Make accusation");
		confirmWeapon.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weighty = 1;
		constraints.insets = new Insets(0, 0, 10, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor right
		weaponPanel.add(confirmWeapon, constraints);
	}

	public void switchPanelTo(String s) {
		((CardLayout) getLayout()).show(this, s);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Yes"))
			switchPanelTo("Character");

		if(e.getActionCommand().equals("No"))
			if(this.getParent() instanceof InteractionPanel)
				((InteractionPanel) this.getParent()).switchToView("Exiting");

		if(e.getActionCommand().equals("Accuse selected character"))	// A bit round about but doing it for now
			switchPanelTo("Room");

		if(e.getActionCommand().equals("Accuse selected room"))
			switchPanelTo("Weapon");

		if(e.getActionCommand().equals("Make accusation"))
				if(this.getParent() instanceof InteractionPanel)
					((InteractionPanel) this.getParent()).switchToView("Exiting");
	}
	
}

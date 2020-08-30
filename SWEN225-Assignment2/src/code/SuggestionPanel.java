package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuggestionPanel extends JPanel implements ActionListener {

	//TODO Fix bug where you loop suggesting weapons

	JPanel questionPanel, characterPanel, weaponPanel, cardSelectPanel;
	Player currentPlayer;
	JRadioButton mrsWhiteRB, mrGreenRB, mrsPeacockRB, profPlumRB, msScarletRB, colMustardRB;
	JRadioButton candleRB, pipeRB, daggerRB, revolverRB, ropeRB, spannerRB;
	String suggestedCharacterName, suggestedWeaponName;


	public SuggestionPanel() {
		setLayout(new CardLayout()); // The SuggestionPanel has multiple panels it switches between
		createQuestionPanel();
		add(questionPanel, "Question");
		//switchPanelTo("Question");
		createCharacterPanel();
		add(characterPanel, "Character");
		//switchPanelTo("Character");
		createWeaponPanel();
		add(weaponPanel, "Weapon");
		//switchPanelTo("Weapon");
	}

	public void createQuestionPanel(){
		questionPanel = new JPanel();
		JLabel titleLabel, promptLabel;
		JButton yesButton, noButton;
		GridBagConstraints constraints = new GridBagConstraints();

		questionPanel.setLayout(new GridBagLayout());

		titleLabel = new JLabel("Suggesting");
		constraints.weighty = 1;	// Removing vertical blank space
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 0, 0, 0);
		constraints.anchor = GridBagConstraints.PAGE_START;
		questionPanel.add(titleLabel, constraints);

		promptLabel = new JLabel("Would you like to make a suggestion?");
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
		ButtonGroup characterRadioButtons = new ButtonGroup();
		JLabel titleLabel, promptLabel;
		JButton confirmCharacter;
		GridBagConstraints constraints = new GridBagConstraints();

		characterPanel.setLayout(new GridBagLayout());

		titleLabel = new JLabel("Suggesting");
		constraints.weighty = 1;	// Removing vertical blank space
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 0, 0, 0);
		constraints.anchor = GridBagConstraints.PAGE_START;
		characterPanel.add(titleLabel, constraints);

		constraints.weightx = 1;

		promptLabel = new JLabel("Who do you suggest?");
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
		characterRadioButtons.add(mrsWhiteRB);
		if(currentPlayer != null && currentPlayer.getCharacter().getName().equals("Mrs. White")) mrsWhiteRB.disable();

		mrGreenRB = new JRadioButton("Mr. Green");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(15, 0, 0, 10); //
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor middle
		characterPanel.add(mrGreenRB, constraints);
		characterRadioButtons.add(mrGreenRB);
		if(currentPlayer != null && currentPlayer.getCharacter().getName().equals("Mr. Green")) mrGreenRB.disable();

		mrsPeacockRB = new JRadioButton("Mrs. Peacock");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(15, 0, 0, 121); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;	// Anchor right
		characterPanel.add(mrsPeacockRB, constraints);
		characterRadioButtons.add(mrsPeacockRB);
		if(currentPlayer != null && currentPlayer.getCharacter().getName().equals("Mrs. Peacock")) mrsPeacockRB.disable();

		profPlumRB = new JRadioButton("Professor Plum");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weighty = 200;	//Push the two rows of radio buttons up
		constraints.insets = new Insets(0, 120, 0, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	// Anchor left
		characterPanel.add(profPlumRB, constraints);
		characterRadioButtons.add(profPlumRB);
		if(currentPlayer != null && currentPlayer.getCharacter().getName().equals("Professor Plum")) profPlumRB.disable();

		msScarletRB = new JRadioButton("Miss Scarlett");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(0, 14, 0, 10); // Temp fix on alignment errors
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor middle
		characterPanel.add(msScarletRB, constraints);
		characterRadioButtons.add(msScarletRB);


		colMustardRB = new JRadioButton("Colonel Mustard");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(0, 0, 0, 100); // Temp fix on alignment errors
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;	// Anchor right
		characterPanel.add(colMustardRB, constraints);
		characterRadioButtons.add(colMustardRB);
		if(currentPlayer != null && currentPlayer.getCharacter().getName().equals("Colonel Mustard")) colMustardRB.disable();

		confirmCharacter = new JButton("Suggest selected character");
		confirmCharacter.addActionListener(this);
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.weighty = 1;
		constraints.insets = new Insets(0, 0, 10, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor right
		characterPanel.add(confirmCharacter, constraints);
	}

	public void createWeaponPanel(){
		weaponPanel = new JPanel();
		ButtonGroup weaponRadioButtons = new ButtonGroup();
		JLabel titleLabel, promptLabel;
		JButton confirmWeapon;
		GridBagConstraints constraints = new GridBagConstraints();

		weaponPanel.setLayout(new GridBagLayout());

		titleLabel = new JLabel("Suggesting");
		constraints.weighty = 1;	// Removing vertical blank space
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(5, 0, 0, 0);
		constraints.anchor = GridBagConstraints.PAGE_START;
		weaponPanel.add(titleLabel, constraints);

		constraints.weightx = 1;

		promptLabel = new JLabel("What weapon do you suggest?");
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
		weaponRadioButtons.add(candleRB);

		pipeRB = new JRadioButton("Lead Pipe");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(15, 0, 0, 10); //
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor middle
		weaponPanel.add(pipeRB, constraints);
		weaponRadioButtons.add(pipeRB);

		daggerRB = new JRadioButton("Dagger");
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(15, 0, 0, 121); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;	// Anchor right
		weaponPanel.add(daggerRB, constraints);
		weaponRadioButtons.add(daggerRB);

		revolverRB = new JRadioButton("Revolver");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.weighty = 200;	//Push the two rows of radio buttons up
		constraints.insets = new Insets(0, 120, 0, 0); // Push towards horizontal centre line
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;	// Anchor left
		weaponPanel.add(revolverRB, constraints);
		weaponRadioButtons.add(revolverRB);

		ropeRB = new JRadioButton("Rope");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(0, 0, 0, 36); // Temp fix on alignment errors
		constraints.anchor = GridBagConstraints.PAGE_START;	// Anchor middle
		weaponPanel.add(ropeRB, constraints);
		weaponRadioButtons.add(ropeRB);

		spannerRB = new JRadioButton("Spanner");
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(0, 0, 0, 116); // Temp fix on alignment errors
		constraints.anchor = GridBagConstraints.FIRST_LINE_END;	// Anchor right
		weaponPanel.add(spannerRB, constraints);
		weaponRadioButtons.add(spannerRB);

		confirmWeapon = new JButton("Suggest selected weapon");
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
		if (e.getActionCommand().equals("Yes")){
			switchPanelTo("Character");
		}

		if(e.getActionCommand().equals("No")){
			if(this.getParent().getParent().getParent().getParent().getParent().getParent().getParent() instanceof GUI) {
				((GUI) this.getParent().getParent().getParent().getParent().getParent().getParent().getParent()).setCollectorInput(null);
				((GUI) this.getParent().getParent().getParent().getParent().getParent().getParent().getParent()).setCollectorState(Game.WorkState.NOT_WAITING);
			}
		}

		if(e.getActionCommand().equals("Suggest selected character")) {
			//Get this character's card and add it to the combo
			if(mrsWhiteRB.isSelected()){
				suggestedCharacterName = "Mrs. White";
			} else if(mrGreenRB.isSelected()){
				suggestedCharacterName = "Mr. Green";
			} else if(mrsPeacockRB.isSelected()){
				suggestedCharacterName = "Mrs. Peacock";
			} else if(profPlumRB.isSelected()){
				suggestedCharacterName = "Prof. Plum";
			} else if(msScarletRB.isSelected()){
				suggestedCharacterName = "Miss Scarlett";
			} else if(colMustardRB.isSelected()){
				suggestedCharacterName = "Col. Mustard";
			}

			switchPanelTo("Weapon");
		}

		if(e.getActionCommand().equals("Suggest selected weapon")){
			//Get this weapon's card and add it to the combo
			//Get this character's card and add it to the combo
			if(candleRB.isSelected()){
				suggestedWeaponName = "Candlestick";
			} else if(pipeRB.isSelected()){
				suggestedWeaponName = "Lead pipe";
			} else if(daggerRB.isSelected()){
				suggestedWeaponName = "Dagger";
			} else if(revolverRB.isSelected()){
				suggestedWeaponName = "Revolver";
			} else if(ropeRB.isSelected()){
				suggestedWeaponName = "Rope";
			} else if(spannerRB.isSelected()){
				suggestedWeaponName = "Spanner";
			}

//			if(this.getParent().getParent().getParent().getParent().getParent().getParent().getParent() instanceof GUI) {
//				((GUI) this.getParent().getParent().getParent().getParent().getParent().getParent().getParent()).getGame().getBoard().characters[];
//			}

			//finalise combo and send card combo back to GUI
			CardCombination suggested = new CardCombination(
						null,
						new CharacterCard(suggestedCharacterName, null),
						new WeaponCard(suggestedWeaponName, null));

			if(this.getParent().getParent().getParent().getParent().getParent().getParent().getParent() instanceof GUI) {
				//System.out.println("Sending card combo");
				((GUI) this.getParent().getParent().getParent().getParent().getParent().getParent().getParent()).setCollectorInput(suggested);
				((GUI) this.getParent().getParent().getParent().getParent().getParent().getParent().getParent()).setCollectorState(Game.WorkState.NOT_WAITING);
			}
		}
	}

	public void updatePlayerName(Player p){
		currentPlayer = p;

		//Disable the radiobutton for the current player's character
		mrsWhiteRB.getModel().setEnabled(true);
		mrGreenRB.getModel().setEnabled(true);
		mrsPeacockRB.getModel().setEnabled(true);
		profPlumRB.getModel().setEnabled(true);
		msScarletRB.getModel().setEnabled(true);
		colMustardRB.getModel().setEnabled(true);
		if(p.getCharacter().getName().equals("Mrs. White")) {
			mrsWhiteRB.getModel().setEnabled(false);
		} else if(p.getCharacter().getName().equals("Mr. Green")) {
			mrGreenRB.getModel().setEnabled(false);
		} else if(p.getCharacter().getName().equals("Mrs. Peacock")) {
			mrsPeacockRB.getModel().setEnabled(false);
		} else if(p.getCharacter().getName().equals("Prof. Plum")) {
			profPlumRB.getModel().setEnabled(false);
		} else if(p.getCharacter().getName().equals("Miss Scarlett")) {
			msScarletRB.getModel().setEnabled(false);
		} else if(p.getCharacter().getName().equals("Col. Mustard")) {
			colMustardRB.getModel().setEnabled(false);
		}
	}

}

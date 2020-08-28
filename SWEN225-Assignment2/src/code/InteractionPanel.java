package code;

import java.awt.*;

import javax.swing.*;

/* Custom JPanel class to hold the misc interaction components.
 * Would probably be a good idea to make this a switcher/CardLayout
 * panel like SwitchPanel between the different possible interactions
 * (eg. suggesting, moving) with more custom panels for each mode.
 */
public class InteractionPanel extends SwitchPanel {

	public CardCombination suggestionCards = null;
	public CardCombination accusationCards = null;
	public char moveDirection = '\u0000';
	public int exitDoor = -1;
	public enum DesiredGameState {SUGGESTING, ACCUSING, MOVING, EXITING};
	public DesiredGameState desiredGameState = null;

	public InteractionPanel(CardLayout cardLayout) {
		super(cardLayout);

		initialisePanels();
	}

	public void initialisePanels(){
		removeAll();

		JPanel accusationPanel = new AccusationPanel();
		JPanel suggestionPanel = new SuggestionPanel();
		JPanel movePanel = new MovePanel();
		JPanel exitPanel = new ExitPanel();

		add(accusationPanel, "Accusing");
		add(suggestionPanel, "Suggesting");
		add(movePanel, "Moving");
		add(exitPanel, "Exiting");
	}

	public void switchToView(String s) {
		//initialisePanels();
		((CardLayout) getLayout()).show(this, s);
	}

}

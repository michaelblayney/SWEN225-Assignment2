package code;

import java.awt.CardLayout;

import javax.swing.JPanel;

/* Custom JPanel class to hold the misc interaction components.
 * Would probably be a good idea to make this a switcher/CardLayout
 * panel like SwitchPanel between the different possible interactions
 * (eg. suggesting, moving) with more custom panels for each mode.
 */
public class InteractionPanel extends SwitchPanel {

	public InteractionPanel(CardLayout cardLayout) {
		super(cardLayout);
		
		JPanel accusationPanel = new AccusationPanel();
		JPanel suggestionPanel = new SuggestionPanel();
		JPanel movePanel = new MovePanel();
		JPanel exitPanel = new ExitPanel();
		
		add(accusationPanel, "Accusing");
		add(suggestionPanel, "Suggesting");
		add(movePanel, "Moving");
		add(exitPanel, "Exiting");
		
	}

}

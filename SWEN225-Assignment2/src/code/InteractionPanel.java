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
	public Player currentPlayer;
	JPanel accusationPanel = new AccusationPanel();
	JPanel suggestionPanel;
	JPanel movePanel;
	JPanel exitPanel;
	int movesLeft = 0;

	public InteractionPanel(CardLayout cardLayout) {
		super(cardLayout);

		initialisePanels();
	}

	public void initialisePanels(){
		removeAll();

		accusationPanel = new AccusationPanel();
		suggestionPanel = new SuggestionPanel();
		movePanel = new MovePanel();
		exitPanel = new ExitPanel();

		add(accusationPanel, "Accusing");
		add(suggestionPanel, "Suggesting");
		add(movePanel, "Moving");
		add(exitPanel, "Exiting");
	}

	public void switchToView(String s) {
		initialisePanels();
		if(currentPlayer != null && currentPlayer.getCharacter() != null) updatePlayer(currentPlayer);
		//updateMovesLeft(movesLeft);
		((CardLayout) getLayout()).show(this, s);
	}

	public void updatePlayer(Player p){
		currentPlayer = p;
		((AccusationPanel) accusationPanel).updatePlayerName(p);
		((SuggestionPanel) suggestionPanel).updatePlayerName(p);
		((MovePanel) movePanel).updatePlayerName(p);
		((ExitPanel) exitPanel).updatePlayerName(p);
	}

	public void updateMovesLeft(Integer i){
		movesLeft = i;
		((MovePanel) movePanel).updateMovesLeft(movesLeft);
	}

}

package code;

import java.util.ArrayList;

public class Player {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Player Associations
	private String nameIRL;
	private Game game;
	private Character character;
	private ArrayList<Card> cards;
	private boolean isEliminated;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Player(Game aGame, Character aCharacter, String nameIRL) {
		if (!setGame(aGame)) {
			throw new RuntimeException(
					"Unable to create Player due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		boolean didAddCharacter = setCharacter(aCharacter);
		if (!didAddCharacter) {
			throw new RuntimeException(
					"Unable to create player due to character. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		cards = new ArrayList<Card>();
		isEliminated = false;
		this.nameIRL = nameIRL;
	}
	
	/* Old constuctor, does not include name*/
	
	public Player(Game aGame, Character aCharacter) {
		if (!setGame(aGame)) {
			throw new RuntimeException(
					"Unable to create Player due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		boolean didAddCharacter = setCharacter(aCharacter);
		if (!didAddCharacter) {
			throw new RuntimeException(
					"Unable to create player due to character. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		cards = new ArrayList<Card>();
		isEliminated = false;
	}
	
	public boolean isEliminated() {
		return isEliminated;
	}
	
	public void eliminate() {
		isEliminated = true;
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	/* Code from template association_GetOne */
	public Game getGame() {
		return game;
	}

	/* Code from template association_GetOne */
	public Character getCharacter() {
		return character;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setGame(Game aNewGame) {
		boolean wasSet = false;
		if (aNewGame != null) {
			game = aNewGame;
			wasSet = true;
		}
		return wasSet;
	}

	/* Code from template association_SetOneToOptionalOne */
	public boolean setCharacter(Character aNewCharacter) {
		boolean wasSet = false;
		if (aNewCharacter == null) {
			// Unable to setCharacter to null, as player must always be associated to a
			// character
			return wasSet;
		}

		Player existingPlayer = aNewCharacter.getPlayer();
		if (existingPlayer != null && !equals(existingPlayer)) {
			// Unable to setCharacter, the current character already has a player, which
			// would be orphaned if it were re-assigned
			return wasSet;
		}

		Character anOldCharacter = character;
		character = aNewCharacter;
		character.setPlayer(this);

		if (anOldCharacter != null) {
			anOldCharacter.setPlayer(null);
		}
		wasSet = true;
		return wasSet;
	}

	/* Add one card to player's hand*/
	public void addCard(Card c) {
		cards.add(c);
	}

	/* Add a list of cards to plyer's hand */
	public void addCards(ArrayList<Card> c) {
		cards.addAll(c);
	}

	/* return player's hand */
	public ArrayList<Card> getCards() {
		return cards;
	}

	public void delete() {
		game = null;
		Character existingCharacter = character;
		character = null;
		if (existingCharacter != null) {
			existingCharacter.setPlayer(null);
		}
	}

}
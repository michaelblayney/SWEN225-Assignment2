package code;

import java.util.*;

public class CardCombination {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// CardCombination Associations
	private Game game;
	private RoomCard room;
	private CharacterCard character;
	private WeaponCard weapon;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public CardCombination(Game aGame) {
		if (!setGame(aGame)) {
			throw new RuntimeException(
					"Unable to create CardCombination due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	/* Create a new card combination with cards*/
	public CardCombination(RoomCard room, CharacterCard character, WeaponCard weapon) {
		this.room = room;
		this.character = character;
		this.weapon = weapon;
	}
	
	

	// ------------------------
	// INTERFACE
	// ------------------------
	/* Code from template association_GetOne */
	public Game getGame() {
		return game;
	}

	public RoomCard getRoom() {
		return room;
	}

	public CharacterCard getCharacter() {
		return character;
	}

	public WeaponCard getWeapon() {
		return weapon;
	}

	/* Given a list of cards, return the subset of cards that match this combination */
	public ArrayList<Card> getMatchingCards(ArrayList<Card> originalList){
		ArrayList<Card> subList = new ArrayList<Card>();
		for(Card c : originalList) {
			if(c.equals(room)||c.equals(character)||c.equals(weapon)) {
				subList.add(c);
			}
		}
		return subList;
	}

	public String toString() {
		if(room != null && character != null && weapon != null){
			return "[Room: "+room.getName()+", Character: "+character.getName()+", Weapon: "+weapon.getName()+"]";
		} else return "Incomplete combination";
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

	public void delete() {
		game = null;
	}

}
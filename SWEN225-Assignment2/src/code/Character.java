package code;

public class Character extends MoveablePiece {

  //Character Associations
  private Player player;//If null, means it's not played by a character.
  private CharacterCard characterCard;



  /**
   * Constructor to use if the character is controlled by a player.
   * @param aName
   * @param p
   */
  //Don't think we need this, as to create a player we need a character, so this use case would not arrise
//  public Character(String aName, Player p){
//    super(aName);
//    player=p;
//  }

  /**
   * Constructor to use if a character is an "NPC".
   * @param aName
   */
  public Character(String aName){
    super(aName);
    player=null;
  }

  public Character(String aName, int x, int y)
  {
    super(aName);
    this.teleportToCoordinate(x,y);
  }

  public void setCharacterCard(CharacterCard c){ characterCard=c; }
  public CharacterCard getCharacterCard()
  {
    return characterCard;
  }
  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    if(player != null) return true;
    return false;
  }
  
  public String toString() {
	  return getName();
  }

  public void setPlayer(Player p) {
    player= p;
  }
}

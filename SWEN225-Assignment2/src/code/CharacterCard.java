package code;
public class CharacterCard extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RoomCard Associations
  private Character character;

  //------------------------
  // CONSTRUCTOR
  //------------------------


  public CharacterCard(String aName, Character c)
  {
    super(aName);
    this.character = c;


  }

  //------------------------
  // INTERFACE
  //------------------------
  public Character getCharacter(){ return character;
  }



}

package code;

public class Card {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	String name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Card(String aName)
  {
    this.name=aName;
  }



  //------------------------
  // INTERFACE
  //------------------------

  public String getName()
  {
    return name;
  }

  /* Returns true if object is a card with the same name*/
  public boolean equals(Object o) {
	  if(((Card)o).getName().equals(name)) {
		  return true;
	  }
	  return false;
  }

  /* Returns true if object is a card OR a Room, Character or Weapon with the same name */
  public boolean matches(Object o) {
	  if(((Card)o).getName().equals(name)) {
		  return true;
	  }else if (((Room)o).getName().equals(name)) {
		  return true;
	  }else if (((MoveablePiece)o).getName().equals(name)) {
		  return true;
	  }
	  return false;
  }


}

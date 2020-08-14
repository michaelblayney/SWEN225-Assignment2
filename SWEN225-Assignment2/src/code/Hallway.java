package code;

public class Hallway extends Location {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

/**
*We don't need to be able to see the board, so this constructor should work just fine.
*/
  public Hallway(int x, int y)
  {
    super(x,y);
  }

	public Hallway(Board aBoard) {
		super(aBoard);
	}

	// ------------------------
	// INTERFACE
	// ------------------------


}

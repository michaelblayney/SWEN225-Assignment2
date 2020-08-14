package code;

public class MoveablePiece {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// MoveablePiece Attributes
	private String name;
	private int x;
	private int y;
	private Room currentRoom;//If null, means it's in a hallway. It not null, means it's in a room and shouldn't be displayed using coordinates.

	// MoveablePiece Associations
	private Board board;

  public MoveablePiece(String aName)
  {
    name = aName;

  }


	public MoveablePiece(String aName, Board aBoard) {
		name = aName;
		if (!setBoard(aBoard)) {
			throw new RuntimeException(
					"Unable to create MoveablePiece due to aBoard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setName(String aName) {
		boolean wasSet = false;
		name = aName;
		wasSet = true;
		return wasSet;
	}

	public int getX(){ return x; }
	public int getY(){ return y; }
	public void setXandY(int xinput, int yinput){x=xinput;y=yinput;}
	public boolean isInRoom(){ return currentRoom!=null;}
	public Room getRoom(){return currentRoom;}
	public void setRoom(Room r){currentRoom=r;}

	public String getName() {
		return name;
	}

	/* Code from template association_GetOne */
	public Board getBoard() {
		return board;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setBoard(Board aNewBoard) {
		boolean wasSet = false;
		if (aNewBoard != null) {
			board = aNewBoard;
			wasSet = true;
		}
		return wasSet;
	}

	/**
	 * moves the object into the room in question. Only changes the object's internally stored room.
	 * @param r
	 */
	public void teleportToRoom(Room r){
		currentRoom=r;
		//x=-1;
		//y=-1;
	}

	public void teleportToExit(int x, int y){
		//currentRoom=null;
		//x=-1;
		//y=-1;
	}
	
	public void teleportToCoordinate(int x, int y){
		currentRoom=null;
		this.x=x;
		this.y=y;
	}

	public void delete() {
		board = null;
	}

	public String toString() {
		return super.toString() + "[" + "name" + ":" + getName() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "board = "
				+ (getBoard() != null ? Integer.toHexString(System.identityHashCode(getBoard())) : "null");
	}
}

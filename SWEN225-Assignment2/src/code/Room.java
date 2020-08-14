package code;

public class Room extends Location {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Room Attributes
	private String name;
	//private int[] exits;//Shouldn't be necessary as each room is a "room tile", will instead be stored in the Board class.
	private boolean isDoor;
	
	// Room Associations
	private RoomCard roomCard;

  	public Room(String aName, int x, int y){
  		super(x,y);
    name = aName;
	  isDoor=false;
  	}
	public Room(String aName, Boolean doorStatus, int x, int y){
		super(x,y);
		name = aName;
		isDoor=doorStatus;
	}


  //------------------------
  // INTERFACE
  //------------------------

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setName(String aName) {
		boolean wasSet = false;
		name = aName;
		wasSet = true;
		return wasSet;
	}

	public String getName() {
		return name;
	}

	public boolean isDoor(){return isDoor;}
	
	//public int[] getExits() {return exits;}

	/* Code from template association_GetOne */
	public RoomCard getRoomCard() {
		return roomCard;
	}

	public String toString() {
		return super.toString() + "[" + "name" + ":" + getName() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "roomCard = "
				+ (getRoomCard() != null ? Integer.toHexString(System.identityHashCode(getRoomCard())) : "null");
	}
}

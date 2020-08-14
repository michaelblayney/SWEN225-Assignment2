package code;
public class RoomCard extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RoomCard Associations
  private Room room;

  //------------------------
  // CONSTRUCTOR
  //------------------------


  public RoomCard(String aName)
  {
    super(aName);
    //this.room=aRoom;
    //Room class is specifically a room tile. Should not be stored in roomcard, board will handle the rooms.

  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Room getRoom()
  {
    return room;
  }

}

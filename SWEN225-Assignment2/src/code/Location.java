package code;
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  private MoveablePiece containedPiece;
  private int x, y;//X and Y coordinates, useful for drawing to rooms exits particularly.


  //Location Associations
  private Board board;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Board doesn't *need* the individual locations to be able to see it - board just stores and changes them.
   * However, locations now store their own coordinates for the sake of drawing them.
   */
  public Location(int xpos, int ypos){
    x=xpos;
    y=ypos;
  }
  
  public boolean hasPiece() {
	  return containedPiece!=null;
  }
  

  /**
   * If for whatever reason you need to store the board in the location, use this constructor instead.
   * @param b
   */
  public Location(Board b){
  board=b;
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */

  public void storePiece(MoveablePiece m){
    containedPiece=m;
  }

  public MoveablePiece getPiece(){
    return containedPiece;
  }
  public int getX(){ return x; }
  public int getY(){ return y; }
  /**
   * Removes the piece stored inside this location. Returns it in the call, though this is optional and can be treated as a Void method instead.
   * @return
   */
  public MoveablePiece removePiece(){
    MoveablePiece toReturn=containedPiece;
    containedPiece=null;
    return toReturn;
  }



  public Board getBoard()
  {
    return board;
  }




}

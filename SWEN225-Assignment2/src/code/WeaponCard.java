package code;
public class WeaponCard extends Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RoomCard Associations
  private Weapon weapon;

  //------------------------
  // CONSTRUCTOR
  //------------------------


  public WeaponCard(String aName, Weapon w)
  {
    super(aName);
    this.weapon = w;


  }

  //------------------------
  // INTERFACE
  //------------------------
  public Weapon getWeapon()
  {
    return weapon;
  }

}

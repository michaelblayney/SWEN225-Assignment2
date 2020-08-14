package code;
public class Weapon extends MoveablePiece
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Weapon Associations
  private WeaponCard weaponCard;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Weapon(String aName){
    super(aName);
  }

  public void setWeaponCard(WeaponCard wc){
    weaponCard=wc;
  }


  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public WeaponCard getWeaponCard()
  {
    return weaponCard;
  }



}

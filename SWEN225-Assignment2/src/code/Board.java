package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Board {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	Character[] characters = new Character[6];
	Weapon[] weapons = new Weapon[6];
	HashMap<String, Weapon> weaponMap = new HashMap<>();
	HashMap<String, Character> characterMap = new HashMap<>();
	HashMap<String, ArrayList<Location>> exitMap= new HashMap<>();
	HashMap<String, ArrayList<Room>> roomMap = new HashMap<>();
	HashMap<String, ArrayList<MoveablePiece>> roomContentsMap = new HashMap<>();
	Location[][] cells;//25 high, 24 wide
	// Board Associations
	private Game game;

	//Constants
	static final int width=24;
	static final int height=25;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Board(Game aGame, String[] roomNamesToInit) {
		game=aGame;
		for(String s:roomNamesToInit){
			exitMap.put(s, new ArrayList<>());
			roomMap.put(s, new ArrayList<>());
			roomContentsMap.put(s, new ArrayList<>());
			//System.out.println("DEBUG: REGISTERING ROOM:"+s);
		}
		cells = loadMap();

	}

	private Location[][] loadMap(){
		Location[][] toReturn = new Location[width][height];//One-letter initiation guide since the map's going to be hard-coded:


		//try {
			Scanner sc = new Scanner(getClass().getResourceAsStream("map.txt"));
			//System.out.println("Map loaded:");
			for (int i = 0; i < height; i++) {
				String line = sc.nextLine();

				for (int j = 0; j < width; j++) {
					toReturn[j][i] = loadLocationFromChar(line.charAt(j), j, i);
					//System.out.print(line.charAt(j));//Debug purposes only.
				}
				//System.out.println("");//Debug purposes only

			}
			sc.close();
			return toReturn;
		//}catch(IOException e){System.out.println("ERROR LOADING MAP:"+e);}
		//System.out.println("CRITICAL ERROR LOADING MAP");//If it didn't return in the try/catch function then something has gone horribly wrong.
		
		//return null;
	}

	public Location loadLocationFromChar(char c, int x, int y){
		//1-9 are room tiles, used for 'storage'. The special character of each number represents a door
		//IE: 1:Ball room, !: Ball room door, 2: Conservatory, @: Conservatory door
		//1:Ball room 2:Conservatory 3:Billiard room 4:Library 5:Study
		//6:Hall 7:Lounge 8:Dining Room 9:Kitchen
		//n: Null space, can't be walked into
		//h: hallway, free access to walk into
		//r: Room space. Not a doorway, but should be rendered as "empty space" rather than a wall.
		boolean doorStatus=false;
		String name;
		switch (c){
			case 'n':
				return null;
			case 'h':
				return new Hallway(x, y);

			case '!':
				doorStatus=true;
			case '1':
				name="Ball Room";
				break;

			case '@':
				doorStatus=true;
			case '2':
				name="Conservatory";
				break;

			case '#':
				doorStatus=true;
			case '3':
				name="Billiard Room";
				break;

			case '$':
				doorStatus=true;
			case '4':
				name="Library";
				break;

			case '%':
				doorStatus=true;
			case '5':
				name="Study";
				break;

			case '^':
				doorStatus=true;
			case '6':
				name="Hall";
				break;

			case '&':
				doorStatus=true;
			case '7':
				name="Lounge";
				break;

			case '*':
				doorStatus=true;
			case '8':
				name="Dining Room";
				break;

			case '(':
				doorStatus=true;
			case '9':
				name="Kitchen";
				break;

			default: System.out.println("ERROR! UNEXPECTED TYPE! TRIED TO GENERATE A LOCATION USING INVALID CHAR:"+c);
			return null;
		}
		if(doorStatus){
			Location door= (new Room(name, true, x, y));
			//System.out.println("DEBUG: ADDING DOOR FOR:"+name);
			exitMap.get(name).add(door);//Register the door under the list of room exits!

			return door;
		} else{
			Room notDoor=new Room(name, x, y);
			roomMap.get(name).add(notDoor);//Register the room under the room tile map - pretty self explanatory
			return notDoor;
		}


	}

	public Location getCell(int xVal, int yVal){
		return cells[xVal][yVal];
	}

	public void setCell(int xVal, int yVal, Location l){
		cells[xVal][yVal]=l;
	}

	// ------------------------
	// INTERFACE
	// ------------------------
	/* Code from template association_GetOne */
	public Game getGame() {
		return game;
	}

	/* Code from template association_SetUnidirectionalOne */
	public boolean setGame(Game aNewGame) {
		boolean wasSet = false;
		if (aNewGame != null) {
			game = aNewGame;
			wasSet = true;
		}
		return wasSet;
	}

	/**
	 * For use during init. Stub method for now.
	 */
	public void addCharacter(Character c, int xpos, int ypos) {
		for(int i = 0; i < characters.length; i++) {
			//At the first empty slot in the array, add this character
			if(characters[i] == null) {
				characters[i] = c;
				break;}}

				characterMap.put(c.getName(), c);
				c.teleportToCoordinate(xpos, ypos);
				cells[xpos][ypos].storePiece(c);
	}

	/**
	 * For use during init. Stub method for now.
	 */
	public void addWeapon(Weapon w) {
			registerWeaponWithArray(w);

				weaponMap.put(w.getName(),w);//Register the weapon with the other weapons
				for(String s:roomContentsMap.keySet()){//For every room's contents
					if(roomContentsMap.get(s).isEmpty()){//If there's nothing in that room

						addToRoom(w,s);//Add the weapon to the room!
						w.setRoom(roomMap.get(s).get(0));//Assigns the weapon to this room by giving it a room tile. It can be given any room tile, specifics don't matter.
						break;//BREAK AFTER THE WEAPON'S ADDED.
					}
				}


	}

	private void registerWeaponWithArray(Weapon w){
		for(int i = 0; i < weapons.length; i++) {
			//At the first empty slot in the array, add this character
			if(weapons[i] == null) {
				weapons[i] = w;
				break;}}
	}


	public ArrayList<Location> getAvailableExits(Player p){
		if(p.getCharacter().isInRoom()) {
			String roomname = p.getCharacter().getRoom().getName();
			return exitMap.get(roomname);
		}
		return null;
	}

	public Character[] getCharacters() {
		return characters;
	}
	
	//TODO This method should return true or false depending on whether they are in a room or not
	//Think this is the right class for this method?
	public boolean isPlayerInRoom(Player p) {
		return p.getCharacter().isInRoom();
		//return(cells[p.getCharacter().getX()][p.getCharacter().getY()] instanceof Room); //Broken alternative method.
	}

	/**
	 * Returns the room that the given player is in.
	 * The room itself may be useless, it may be more useful to use .getName() on the room.
	 * @param p
	 * @return
	 */
	public Room getRoomPlayerIsIn(Player p) {
		return p.getCharacter().getRoom();
	}
	
	//TODO Move the given player by 1, in direction 'n', 's', 'e', or 'w' as indicated by given char
	//Should be preceeded by calling isPlayerMoveValid(Player p, char c)
	//SHOULD *NOT* BE USED FOR EXITING A ROOM!
	public void movePlayer(Player p, char c) {
		if(isPlayerMoveValid(p,c)==false)//This needs to be called from the game method, checks internally anyway incase of mis-use.
			return;
		int playX= p.getCharacter().getX();//playX rather than charX to stop confusion between character and directional char
		int playY= p.getCharacter().getY();

		Location currentCell=cells[playX][playY];
		Location newCell = cells[playX+xDirFromChar(c)][playY+yDirFromChar(c)];//The new cell, adjacent to the current one.


		if(newCell instanceof Room){
			//This ASSUMES that the player is not in a room when they enter the room. This assumption should be correct unless something goes horribly wrong.
			currentCell.removePiece();
			addToRoom(p.getCharacter(),((Room) newCell).getName());



		}else{//Cell-to-cell logic:
			//System.out.println("DEBUG: CURRENT CELL:"+currentCell);
			//System.out.println("DEBUG: NEW CELL:"+newCell);
MoveablePiece temp = currentCell.removePiece();
			newCell.storePiece(temp);//Removes the character from the old cell and puts them in the new one simultaneously.
			p.getCharacter().teleportToCoordinate(playX+xDirFromChar(c),playY+yDirFromChar(c));
		}

	}

	public boolean isPlayerMoveValid(Player p, char c){

		int newX=p.getCharacter().getX()+xDirFromChar(c);
		int newY=p.getCharacter().getY()+yDirFromChar(c);
		if(newX<0||newY<0||newX>=width||newY>=height)//If out of bounds, immediately return false rather than letting an error ensue.
			return false;

		Location cellToCheck=cells[newX][newY];
		//System.out.println("DEBUG: IsMoveValid - Current cell:"+cells[p.getCharacter().getX()][p.getCharacter().getY()]);
		//System.out.println("DEBUG: IsMoveValid - New cell:"+cellToCheck);
		if(cellToCheck==null|| (cellToCheck.getPiece()!=null))//Null cells are walls that we absolutely cannot walk into.
			return false;
		if(cellToCheck instanceof Room){
			Room roomToCheck = (Room) cellToCheck;//Cast to room so we can call room methods on it
			if(roomToCheck.isDoor()){//TODO: Implement complex wall-checking logic here later?
				return true;
			} else{//Trying to walk through an implied wall to get into a room, do not allow
				return false;
			}
		}
		return true;
	}

	/**
	 * sub-function which returns the change in x direction from the inputted character.
	 * An improvement may be an enum.
	 * @param c
	 * @return
	 */
	public int xDirFromChar(char c){
		switch(c){
			case 'n': //North and south are here as they're still valid inputs!
			case 's':
			return 0;
			case 'e':
				return 1;
			case 'w':
				return -1;
			default:
				System.out.println("ERROR! INVALID MOVEMENT CHARACTER:"+c);
				return 0;
		}
	}

	/**
	 * sub-function which returns the change in y direction from the inputted character.
	 * An improvement may be an enum.
	 * @param c
	 * @return
	 */
	public int yDirFromChar(char c){
		switch(c){
			case 'n':
				return -1;
			case 's':
				return 1;
			case 'e': //East and west are here so that it won't see an invalid input and signal an error.
			case 'w':
			return 0;
			default:
				System.out.println("ERROR! INVALID MOVEMENT CHARACTER:"+c);
				return 0;
		}
	}

	/**
	 * To be used by suggest/accuse. Teleports the player to the requested room by roomname
	 * @param p
	 * @param roomname
	 */
	public void movePlayerTo(Player p, String roomname) {
		/*if(p.getCharacter().isInRoom())
			removeFromRoom(p.getCharacter(), p.getCharacter().getRoom().getName());//If player is in room, remove.
		addToRoom(p.getCharacter(), roomname);*/
		moveCharacterTo(p.getCharacter().getName(), roomname);
	}
	
	public void moveCharacterTo(String charactername, String roomname) {
		Character c = characterMap.get(charactername);
		if(c.isInRoom()){
			removeFromRoom(c, c.getRoom().getName());//If player is in room, remove.
		}else {
			removeFromHallway(c, c.getX(), c.getY());
		}
		addToRoom(c, roomname);
	}

	/**
	 * To be used by suggest/accuse
	 * @param weaponName
	 * @param roomName
	 */
	public void moveWeaponTo(String weaponName, String roomName){

		Weapon w = weaponMap.get(weaponName);
		if(w.isInRoom()&&w.getRoom().getName().equals(roomName)) return;//If the object's already in the room, then just return!
		if(w.isInRoom()){
		removeFromRoom(w, w.getRoom().getName());}
		addToRoom(w,roomName);//Remove the weapon from the old room and add it to the new one in one motion.
	}

	/**
	 * This method SHOULD NOT be called on a piece which is already in a room. Use removeFromRoom first!
	 * This method adds a piece (character/weapon) to a room, putting them in a free cell and then
	 * @param m
	 * @param roomname
	 */
	public void addToRoom(MoveablePiece m, String roomname){
		roomContentsMap.get(roomname).add(m);
		for(Room r:roomMap.get(roomname)) {//For every room tile in the given room
			if(!r.hasPiece()) {//If it's unoccupied, place this piece into the cell. Then break.
				r.storePiece(m);
				m.setRoom(r);
				m.setXandY(r.getX(),r.getY());
				break;
			}
		}
	}

	public MoveablePiece removeFromRoom(MoveablePiece m, String roomname){
		roomContentsMap.get(roomname).remove(m);
		//System.out.println("RemoveFromRoom, state of MoveablePiece:"+m);
		//System.out.println("RemoveFromRoom, removing from cell at x:"+m.getX()+", y:"+m.getY());
		cells[m.getX()][m.getY()].removePiece();
		m.setRoom(null);
		return m;
	}
	
	public MoveablePiece removeFromHallway(Character m, int x, int y){
		cells[x][y].removePiece();
		//System.out.println("RemoveFromRoom, state of MoveablePiece:"+m);
		//System.out.println("RemoveFromRoom, removing from cell at x:"+m.getX()+", y:"+m.getY());
		cells[m.getX()][m.getY()].removePiece();
		m.setRoom(null);
		return m;
	}

	/**
	 * Pulls the player out of a room and puts them at a given exit. BE AWARE, THIS WILL PUT THEM IN THE DOOR TILE.
	 * The door tile is usually not accessible. Once they exit a room they will have to physically "leave".
	 * @param p
	 * @param exit
	 */
	public boolean vacatePlayerFromRoom(Player p, Location exit){
		//Room door = (Room)exit;
		//removeFromRoom(p.getCharacter(),door.getName());
		//int playerx = p.getCharacter().getX();
		//int playery = p.getCharacter().getY();
		//cells[playerx][playery].removePiece();//Ensure the character isn't in the old cell
		//p.getCharacter().teleportToExit(exit.getX(),exit.getY());//TODO test
		
		// "pushing into open hallway" code. TODO maybe think of something better??
		int exitX = exit.getX();
		int exitY = exit.getY();
		
		if(cells[exitX][exitY-1].getClass().equals(Hallway.class)&&cells[exitX][exitY-1].getPiece()==null) {
			
			pushPlayerToHallway(p, cells[exitX][exitY-1]);
			return true;
		}else if(cells[exitX][exitY+1].getClass().equals(Hallway.class)&&cells[exitX][exitY+1].getPiece()==null) {
			//p.getCharacter().teleportToCoordinate(exitX+1,exitY-1);
			pushPlayerToHallway(p, cells[exitX][exitY+1]);
			return true;
		}else if(cells[exitX-1][exitY].getClass().equals(Hallway.class)&&cells[exitX-1][exitY].getPiece()==null) {
			//p.getCharacter().teleportToCoordinate(exitX-1,exitY+1);
			pushPlayerToHallway(p, cells[exitX-1][exitY]);
			return true;
		}else if(cells[exitX+1][exitY].getClass().equals(Hallway.class)&&cells[exitX+1][exitY].getPiece()==null) {
			//p.getCharacter().teleportToCoordinate(exitX+1,exitY+1);
			pushPlayerToHallway(p, cells[exitX+1][exitY]);
			return true;
		}else {
			return false;
		}
	}
	
	public void pushPlayerToHallway(Player p, Location h) {
		MoveablePiece m = p.getCharacter().getRoom().removePiece();
		h.storePiece(m);
		m.teleportToCoordinate(h.getX(), h.getY());
	}

	public void delete() {
		game = null;
	}

	/**
	 * Returns all of the cells as they are at present so that the UI can draw them.
	 * @return
	 */
	public Location[][] getCellsToDraw() {
		return cells;
	}
}

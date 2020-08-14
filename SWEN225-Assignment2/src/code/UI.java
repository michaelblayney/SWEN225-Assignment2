package code;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// UI Associations
	private Game game;
	
	char[][] base;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public UI(Game aGame) {
		if (!setGame(aGame)) {
			throw new RuntimeException(
					"Unable to create UI due to aGame. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
		}
		this.loadBase();
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

	public void delete() {
		game = null;
	}
	
	public void print(String s) {
		System.out.print(s);
	}
	
	public void println(String s) {
		System.out.println(s);
	}
	
	
	//TODO This should draw the ASCII art of the game board and all characters, weapons, etc
	/** Draws a very simple text based visual of the board using single characters to represent board tiles and pieces.*/
	public void drawBoard(Board board, ArrayList<Location> cellsToHighlight) {
		Boolean isExiting=true;
		if(cellsToHighlight==null)
			isExiting=false;//This keeps track of whether or not to check for "exit overrides"


		if (base.length == 0) {
			// quick boundary check, shouldn't actually ever trigger
			System.out.println("Can't draw board, base array not initiallised! Check loadBase(). ");
			return;
		}
		
		/* The idea for drawing the board is using the 2D Location Array "cells" in the Board
		 * and from each "cell"/Location check what is in that location to see what to print.*/
		for (int y = 0 ; y < board.height ; y++) {
			
			for (int x = 0 ; x < board.width ; x++) {
				
				Location cell = board.cells[x][y]; // get the Location from the array

				if(isExiting&&isExitPresent(cellsToHighlight,x,y)!=-1){//Highest priority is "overriding cells", AKA drawing exits
					System.out.print(ANSI_RED+(isExitPresent(cellsToHighlight,x,y)+1)+ANSI_RESET);//The +1 is to match up with the choices the UI offers



				} else if (cell == null) {
					//if the Location is null it means it is an out of bounds tile
					//System.out.print(base[y][x]);
					System.out.print(" ");//Null space, so leave it blank!
				} else if (!cell.hasPiece()) {
					//if the Location isn't null but doesn't contain a piece, it is a room or a hallway tile
					if (cell instanceof Hallway) {System.out.print("h");}
					else if (cell instanceof Room) { 
						//Cell is confirmed to be a room, so the cast is safe
						Room roomOrDoor = (Room) cell;
						if(roomOrDoor.isDoor()){//If it's a door, render it.
							System.out.print(ANSI_CYAN);
							System.out.print(doorToRoom(roomOrDoor));
							System.out.print(ANSI_RESET);
						}else//If it's not a door, and there's nothing inside it, then we're not interested in showing it.
							System.out.print(" ");
						//System.out.print(base[y][x]);
						}
					//if it isn't a hallway or room tile, is an unrecognized tile 
					else {System.out.print("?");}
				} else {
					/* the location has a piece and the name is checked against the cases to print the appropriate
					 * char, at the moment all characters are printed with a C to indicate it is a character and
					 * weapons are printed as a W, discuss with group what chars to use to make them unique or if a
					 * better drawing method should be put together*/
					String name = cell.getPiece().getName();
					
					switch (name) {
						case "Miss Scarlett":
							System.out.print(ANSI_RED+"S"+ANSI_RESET);
							break;
							
						case "Col. Mustard":
							System.out.print(ANSI_YELLOW+"M"+ANSI_RESET);
							break;
							
						case "Mrs. White":
							System.out.print(ANSI_WHITE+"W"+ANSI_RESET);
							break;
							
						case "Mr. Green":
							System.out.print(ANSI_GREEN+"G"+ANSI_RESET);
							break;
							
						case "Mrs. Peacock":
							System.out.print(ANSI_BLUE+"P"+ANSI_RESET);
							break;
							
						case "Prof. Plum":
							System.out.print(ANSI_PURPLE+"p"+ANSI_RESET);
							break;
							
						case "Candlestick":
							System.out.print(ANSI_RED+"C"+ANSI_RESET);
							break;
							
						case "Dagger":
							System.out.print(ANSI_RED+"D"+ANSI_RESET);
							break;
							
						case "Lead pipe":
							System.out.print(ANSI_RED+"L"+ANSI_RESET);
							break;
							
						case "Revolver":
							System.out.print(ANSI_RED+"R"+ANSI_RESET);
							break;
							
						case "Rope":
							System.out.print(ANSI_RED+"r"+ANSI_RESET);
							break;
							
						case "Spanner":
							System.out.print(ANSI_RED+"s"+ANSI_RESET);
							break;
							
						default: System.out.print("?");
						break;
					}
				}
				
				
			}
			System.out.print("            ");//Gap between map and right-hand info.
			//This part of the UI is the "right hand side", entirely seperate to the map
			System.out.print(rightHandSideInfo(y));
			
			System.out.println();//End of the line/row
		}
	}

	/**
	 * Gives information about the board, changes based on which line it's on.
	 * effectively an int switch moved to a seperate method to make the UI class tidier.
	 * @param y
	 * @return
	 */
	public String rightHandSideInfo(int y){
		String toReturn="";
		switch(y){
			case 0:
			return "Room ID keys:";
			case 1:
				return ANSI_CYAN+y+ANSI_RESET+": Ball room";
			case 2:
				return ANSI_CYAN+y+ANSI_RESET+": Conservatory";
			case 3:
				return ANSI_CYAN+y+ANSI_RESET+": Billiard Room";
			case 4:
				return ANSI_CYAN+y+ANSI_RESET+": Library";
			case 5:
				return ANSI_CYAN+y+ANSI_RESET+": Study";
			case 6:
				return ANSI_CYAN+y+ANSI_RESET+": Hall";
			case 7:
				return ANSI_CYAN+y+ANSI_RESET+": Lounge";
			case 8:
				return ANSI_CYAN+y+ANSI_RESET+": Dining Room";
			case 9:
				return ANSI_CYAN+y+ANSI_RESET+": Kitchen";

			case 11:
				return "Weapon ID keys:";
			case 12:
				return ANSI_RED+'C'+ANSI_RESET+": Candlestick";
			case 13:
				return ANSI_RED+'D'+ANSI_RESET+": Dagger";
			case 14:
				return ANSI_RED+'L'+ANSI_RESET+": Lead Pipe";
			case 15:
				return ANSI_RED+'R'+ANSI_RESET+": Revolver";
			case 16:
				return ANSI_RED+'r'+ANSI_RESET+": Rope";
			case 17:
				return ANSI_RED+'s'+ANSI_RESET+": Spanner";
			
			case 19:
				return "Character:";
			case 20:
				return ANSI_WHITE+'W'+ANSI_RESET+": Mrs. White";
			case 21:
				return ANSI_GREEN+'G'+ANSI_RESET+": Mr. Green";
			case 22:
				return ANSI_BLUE+'P'+ANSI_RESET+": Mrs. Peacock";
			case 23:
				return ANSI_PURPLE+'p'+ANSI_RESET+": Prof. Plum";
			case 24:
				return ANSI_RED+'S'+ANSI_RESET+": Miss. Scarlett";
			case 25:
				return ANSI_YELLOW+'M'+ANSI_RESET+": Col. Mustard";


			default:
		}
		return toReturn;
	}
	
	// Gets an input from System.in and returns it, or -1 if it is an invalid int
	// Loops until valid int is found
	// Note doesnt allow you to enter int of -1
	public int scanInt(Scanner input) {
		String s = "";
		int i = -1;
		while (i == -1) {
			s = input.nextLine();
			try { // Integer input
				i = Integer.parseInt(s);
			} catch (NumberFormatException e) { // Non integer input
				System.out.println("Please input a valid Integer");
			}
		}
		return i;
	}

	// Same as scanInt() but allows you to add a range, and loops until an integer in that range is found
	public int scanInt(int min, int max, Scanner input) {
		String s = "";
		int i = -1;
		while (i == -1) {
			s = input.nextLine();
			try { // Integer input
				i = Integer.parseInt(s);
				if (i < min || i > max) {
					println("Please input an Integer between " + min + " and " + max);
					i = -1;
				}
			} catch (NumberFormatException e) { // Non integer input
				println("Please input a valid Integer");
				i = -1;
			}
		}
		return i;
	}
	
	// Gets an input from System.in and returns it, or a null char if it's invalid
	// Loops until valid char is found
	public char scanChar(Scanner input) {
		char c = '\u0000'; //Null value for char
		while (c == '\u0000') {
			try { // Char input
				c = input.nextLine().charAt(0);
			} catch (InputMismatchException e) { // Non char input
				println("Please input a valid Character");
			}
		}
		return c;
	}
	
	// Same as scanChar() but allows you to add input an array of expected chars as a parameter
	//It will loop until there is a valid and expected character input
	public char scanChar(char[] expectedChars, Scanner input) {
		char c = '\u0000'; //Null value for char
		while (c == '\u0000') {
			try { // Char input
				c = input.nextLine().charAt(0);
				boolean validChar = false;
				for(int i = 0; i < expectedChars.length; i ++) {
					if(c == expectedChars[i]) validChar = true;
				}
				if(!validChar) {
					print("Please enter one of the following Characters: ");
					print("" + expectedChars[0]);
					for(int i = 1; i < expectedChars.length; i++) print(", " + expectedChars[i]);
					println("");
					c = '\u0000';
				}
			} catch (InputMismatchException e) { // Non char input
				println("Please input a valid Character");
			}
		}
		return c;
	}

	/**
	 * checks a given list of exits
	 * If it matches the x and y coordinates, then this returns the index
	 * Returns -1 if no exit matches the given coordinates
	 * @param x
	 * @param y
	 * @return
	 */
	private int isExitPresent(ArrayList<Location> exitList, int x, int y){
		int toReturn=-1;
		for(int i=0; i<exitList.size(); i++) {
			if (exitList.get(i).getX() == x && exitList.get(i).getY() == y){
				toReturn = i;
				break;
			}
		}
		return toReturn;
	}

	/**
	 * Set up as it's own method to make it prettier
	 * Converts from a "door" to a "room number". Basically a single large switch.
	 * @param r
	 * @return
	 */
	private char doorToRoom(Room r){
		String switchCandidate = r.getName();
		switch(switchCandidate){

			case "Ball Room":
				return '1';
			case "Conservatory":
				return '2';
			case "Billiard Room":
				return '3';
			case "Library":
				return '4';
			case "Study":
				return '5';
			case "Hall":
				return '6';
			case "Lounge":
				return '7';
			case "Dining Room":
				return '8';
			case "Kitchen":
				return '9';
			default:
				return '?';//Something's gone wrong!
		}

	}
	
	/** Loads a base map for the board as a 2d array of chars.
	 *  Array is for use in drawing the board./SWEN225-Assignment1/src/code/map.txt
	 *  Uses the map.txt file and is hard coded to the dimensions 24X25
	 * */
	private void loadBase() {
		base = new char[25][24];
		// this method is an altered copy from the Board's loadMap() method.
		/*try {*/
			Scanner sc = new Scanner(getClass().getResourceAsStream("map.txt"));
			//System.out.println("Map loaded:");
			for (int y = 0; y < 25; y++) {
				String line = sc.nextLine();

				for (int x = 0; x < 24; x++) {
					base[y][x] = line.charAt(x);
					
				}
				

			}
			
		/*} catch(IOException e){System.out.println("ERROR LOADING MAP:"+e);}*/
		
	}
}

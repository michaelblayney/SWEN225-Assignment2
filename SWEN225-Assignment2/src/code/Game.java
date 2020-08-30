package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
//import com.sun.tools.sjavac.server.SysInfo;

public class Game extends Observable{

	public enum GameState { SETTING_UP, SUGGESTING, ACCUSING, MOVING, EXITING};
	public enum WorkState { WAITING, NOT_WAITING };
	//private WorkState workState;
	private GameState gameState;	// please only use setGameStateTo() to modify this - it has the notify built in
	
	// Constants
	int maxNumOfPlayers = 6;
	int minNumOfPlayers = 2;

	// For NOW, these are hard-coded. It may be beneficial to replace them with
	// enums.
	private final String[] weaponNames = { "Candlestick", "Lead pipe", "Dagger", "Revolver", "Rope", "Spanner" };
	private final String[] characterNames = { "Mrs. White", "Mr. Green", "Mrs. Peacock", "Prof. Plum", "Miss Scarlett", "Col. Mustard" };
	private final int[] charXCoords={9,14,23,23,7,0};
	private final int[] charYCoords={0,0,6,19,24,17};
	private final String[] roomNames = { "Ball Room", "Conservatory", "Billiard Room", "Library", "Study", "Hall", "Lounge", "Dining Room", "Kitchen" };

	// Variables/fields

	private UI ui;
	private GUI gui;
	private InputCollector collector;
	private Board board;
	private boolean gameFinished = false; // If set to true, immediately kills the game loop.
	private CardCombination murderSolution;
	private Player[] players;
	private Scanner scan;
	private int numPlayers;
	private ArrayList<Card> dealDeck;
	private Player currentPlayer;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Game(GUI gui) throws InterruptedException {
		this.gui = gui;
		gui.addGame(this);
		this.init();
		this.doGameLoop(); //Need to start game loop once the gameplay panel has been switched to
	}

	// ------------------------
	// INTERFACE
	// ------------------------

//	public void delete() {
//
//	}

	/**
	 * Initializes and instantiates everything as-needed. FOR NOW, this includes the
	 * hard-coded values for weapon/room/character names. Set up the
	 * moveablepiece/character FIRST, then use them to initialize the card. Not the
	 * other way around.
	 */
	private void init() {
		setBoard(new Board(this, roomNames));
		ui = new UI(this);
		collector = new InputCollector();
		collector.setWorkStateTo(WorkState.NOT_WAITING);
		gui.addCollector(collector);

		cardInit();

		this.addObserver(gui);
		setGameStateTo(GameState.SETTING_UP);
		
		setCurrentPlayerTo(players[0]);
		setGameStateTo(GameState.MOVING);
		// Getting number of players
		//ui.println("CLUEDO");
		//ui.println("How many people are playing?");

		//numPlayers = ui.scanInt(minNumOfPlayers, maxNumOfPlayers, scan);

		//ui.println("Num of players: " + numPlayers);

		// Creating Players, and assigning the players to characters
		//String[] playerNames = {"Jim, Harry, Vlad"};
		
		//createPlayer("Vlad");

		//dealCards();
		//notifyObservers();
	}

	/* Creates all cards and the weapons and characters.
	 * Sets up murder scenario.
	 * Note that this method does not deal the remaining cards because characters,
	 * then players must be created first. */
	private void cardInit() {
		scan = new Scanner(System.in);

		ArrayList<WeaponCard> weaponDeck = new ArrayList<>();
		ArrayList<CharacterCard>characterDeck = new ArrayList<>();
		ArrayList<RoomCard>roomDeck = new ArrayList<>();
		for (String s : roomNames) {//Rooms need to be registered before weapons!
			//Room r = new Room(s); //Room class is for the room tile specifically. SHOULD NOT INIT HERE.
			RoomCard rc = new RoomCard(s);
			roomDeck.add(rc);// 'Room' isn't a moveable piece, so it isn't added to the board.
		}

		for (String s : weaponNames) {
			Weapon w = new Weapon(s);
			WeaponCard wc = new WeaponCard(s, w);
			getBoard().addWeapon(w);
			weaponDeck.add(wc);

		}
		for (int i=0; i<characterNames.length; i++) {
			Character c = new Character(characterNames[i]);
			CharacterCard cc = new CharacterCard(characterNames[i], c);
			getBoard().addCharacter(c, charXCoords[i],charYCoords[i]);
			characterDeck.add(cc);
		}


		//create murder scenario
		Random rand = new Random();
		WeaponCard murderWeapon = weaponDeck.remove(rand.nextInt(weaponDeck.size()));
		CharacterCard murderCharacter = characterDeck.remove(rand.nextInt(characterDeck.size()));
		RoomCard murderRoom = roomDeck.remove(rand.nextInt(roomDeck.size()));
		murderSolution = new CardCombination(murderRoom, murderCharacter, murderWeapon);
		//ui.println(murderSolution.toString());

		//put remaining cards into deck to dealt out once players are created
		dealDeck = new ArrayList<>();
		dealDeck.addAll(weaponDeck);
		dealDeck.addAll(characterDeck);
		dealDeck.addAll(roomDeck);
	}

	/* Deals out the cards to players */
	private void dealCards() {
		Random rand = new Random();
		boolean finishedDealing = false;
		while(!finishedDealing){
			for(Player p : players) {
				if(!finishedDealing) {
					Card c = dealDeck.remove(rand.nextInt(dealDeck.size()));
					p.addCard(c);
					finishedDealing = dealDeck.isEmpty();
				}
			}
		} 
		
	}

	/* old createPlayers, initialises players without names*/
	private void createPlayers(int numPlayers) {
		players = new Player[numPlayers];
		for(int i = 0; i < numPlayers; i++) { //Asking each player which character they want to be
			int index = 0;
			HashMap<Integer, Integer> indexTable = new HashMap<>();

			ui.println("-------------------");
			ui.println("Player " + (i + 1) + " please select your character");
			//Displaying all the characters without players
			for(int j = 0; j < getBoard().characters.length; j++) {
				if(!getBoard().characters[j].hasPlayer()) {
					index += 1;
					indexTable.put(index, j);
					ui.println(index + ". " + getBoard().characters[j]);
				}
			}

			int selection = ui.scanInt(1, index, scan);
			Player player = new Player(this, getBoard().characters[indexTable.get(selection)]);
			getBoard().characters[indexTable.get(selection)].setPlayer(player);

			//Add player to players
			for(int j = 0; j < players.length; j++) {
				//At the first empty slot in the array, add this character
				if(players[j] == null) {
					players[j] = player;
					break;
				}
			}

			ui.println("Player " + (i + 1) + " has chosen: " + getBoard().characters[indexTable.get(selection)].toString());
		}

		//Move Mrs. Scarlet to the front of players
		Player missSPlayer = null;
		int index = -1;

		for(int i = 0; i < players.length; i++) {//Checking to see if someone selected Miss Scarlett and finding the index if possible
			if(players[i].getCharacter().getName().equals("Miss Scarlett")) {
				missSPlayer = players[i];
				index = i;
				break;
			}
		}
		if(missSPlayer != null) { //If there is a player that selected Miss Scarlett, put them at the front of the player turn array
			System.arraycopy(players, 0, players, 1, index);
			players[0] = missSPlayer;
		}
	}
	
	/* create a single player and add to list of players */
	public void createPlayer(String playerName, String playerCharacterName) {
		
		Character playerCharacter = null;
		
		for(Character c : getBoard().characters) {
			if(c.getName().equals(playerCharacterName)) {
				playerCharacter = c;
				break;
			}
		}
		
		Player newPlayer = new Player(this, playerCharacter, playerName);
		playerCharacter.setPlayer(newPlayer);
	
		//Add player to players
		for(int j = 0; j < players.length; j++) {
			//At the first empty slot in the array, add this character
			if(players[j] == null) {
				players[j] = newPlayer;
				break;
			}
		}

		

		//Move Mrs. Scarlet to the front of players
		Player missSPlayer = null;
		int index = -1;

		for(int i = 0; i < players.length; i++) {//Checking to see if someone selected Miss Scarlett and finding the index if possible
			if(players[i]==null) {
				break;
			}
			if(players[i].getCharacter().getName().equals("Miss Scarlett")) {
				missSPlayer = players[i];
				index = i;
				break;
			}
		}
		if(missSPlayer != null) { //If there is a player that selected Miss Scarlett, put them at the front of the player turn array
			System.arraycopy(players, 0, players, 1, index);
			players[0] = missSPlayer;
		}
	}


	private void doGameLoop() throws InterruptedException {
		System.out.println("Doing game loop, num of Players: " + players.length);
		int whichPlayersTurn = 0;
		while (!gameFinished) {
			
			//If there is only one player left in the game they win
			int remainingPlayerCount = 0;
			int remainingPlayerIndex = -1;
			for(int i = 0; i < players.length; i++) {
				if(!players[i].isEliminated()) {
					remainingPlayerCount += 1;
					remainingPlayerIndex = i;
				}
			}
			if(remainingPlayerCount <= 1 && remainingPlayerIndex != -1) {
				winGame(players[remainingPlayerIndex]);
				break;
			}
			
			//Getting correct player whom is taking the turn
			setCurrentPlayerTo(players[whichPlayersTurn]);
			
			if(!currentPlayer.isEliminated()) {
				ui.println("----------------------------");
				ui.println("[" + currentPlayer.getCharacter().getName() + "'s turn]");
				doTurn(currentPlayer);
			} 
			
				//Loops the players turn once the final player has had theirs
				if(whichPlayersTurn + 1 >= numPlayers) whichPlayersTurn = 0;
				else whichPlayersTurn += 1;
			
		}
	}

	private void doTurn(Player currentPlayer) throws InterruptedException {
		char[] validYesNoChars = {'y', 'n'};
		char[] validMoveChars = {'n', 's', 'e', 'w', 'f'};
		boolean hasSuggested = false;
		boolean startedInHall = false;
		
		ui.println("");
		displayPlayerCards(currentPlayer);
		ui.println("");
		
		//ui.drawBoard(board, null);
		gui.drawBoard(getBoard(), null);
		ui.println("");
		
		int movesLeft = RollDice();
		ui.println("You rolled: " + movesLeft);

		setGameStateTo(GameState.MOVING);
		collector.setWorkStateTo(WorkState.WAITING);
		
		//Main turn loop
		while(movesLeft > 0) {
			//Update the board
			//board.update(this);

			// ---------------
			// If player is in a room
			// ---------------
			if(getBoard().isPlayerInRoom(currentPlayer)) {
				if(startedInHall) movesLeft = 0;


				//Suggestion
				if(!hasSuggested) {
					//GameState switching and GUI updating
					setGameStateTo(GameState.SUGGESTING);
					//gui.update(this, "Suggesting");
					collector.setWorkStateTo(WorkState.WAITING);
					while(collector.getWorkState().equals(WorkState.WAITING)) {
						Thread.sleep(100);
						//System.out.println("Waiting");
					}
					if(collector.getInput() instanceof CardCombination){
						doSuggest(currentPlayer, (CardCombination) collector.getInput());
					} else {

					}
					hasSuggested = true;
				}


				//Accusation
				//GameState switching and GUI updating
				setGameStateTo(GameState.ACCUSING);
				//gui.update(this, "Accusing");
				collector.setWorkStateTo(WorkState.WAITING);
				while(collector.getWorkState().equals(WorkState.WAITING)) {
					Thread.sleep(100);
					//System.out.println("Waiting");
				}
				boolean accuseResult = false;
				if(collector.getInput() instanceof CardCombination){
					accuseResult = doAccuse(currentPlayer, (CardCombination) collector.getInput());
				}
				if(accuseResult) {
					winGame(currentPlayer);
				} else {
					break;
				}

				
				//Leave room
				if(movesLeft > 0) {
					System.out.println("Heading into Leave room");
					//GameState switching and GUI updating
					setGameStateTo(GameState.EXITING);
					//gui.update(this, "Exiting");
					collector.setWorkStateTo(WorkState.WAITING);
					while(collector.getWorkState().equals(WorkState.WAITING)) {
						Thread.sleep(100);
						//System.out.println("Waiting");
					}

					if(collector.getInput() instanceof java.lang.Character){
						if(((java.lang.Character) collector.getInput()) == 'f'){
							movesLeft = 0;
						}
					}

					if(collector.getInput() instanceof Integer){
						boolean isFinished = leaveRoom(currentPlayer, (Integer) collector.getInput());
						movesLeft -= 1;
					}

				}
			} else {
				// -------------
				// If player is NOT in a room
				// ---------------
				//Move player or end turn
				//GameState switching and GUI updating
				setGameStateTo(GameState.MOVING);
				//gui.update(this, "Moving");
				gui.update(this, movesLeft);

				collector.setWorkStateTo(WorkState.WAITING);
				while(collector.getWorkState().equals(WorkState.WAITING)) {
					Thread.sleep(100);
					//System.out.println("Waiting");
				}
				if(collector.getInput() instanceof java.lang.Character) { //Direction n s e w
					char c = (java.lang.Character) collector.getInput();
					if(c == 'f'){
						//End turn
						movesLeft = 0;
					} else {
						if(getBoard().isPlayerMoveValid(currentPlayer, c)){ //If the move entered is valid
						getBoard().movePlayer(currentPlayer, c);
						gui.drawBoard(getBoard(), null);
						movesLeft -= 1;
						}
					}
				}

				startedInHall = true;
			}
		}
		gui.update(this, 0);
		ui.println("----------------------------");
		ui.println("END OF TURN");
		TimeUnit.SECONDS.sleep(2);
	}
	
	/* Displays the cards in the player's hand */
	private void displayPlayerCards(Player currentPlayer) {
		ui.println("Your cards are:");
		for(Card c : currentPlayer.getCards()) {
			ui.println("- " + c.getName());
		}
	}
	
	
	/* returns true if accusation was correct, false if it was not & player was eliminated */
	private boolean doAccuse(Player currentPlayer, CardCombination combo) {
//		//Character accusation
//		ui.println("Accusation:");
//		ui.println("Select who dunnit:");
//		for(int i = 0; i < getBoard().characters.length; i ++) {
//			ui.println((i + 1) + ". " + getBoard().characters[i]);
//		}
//		int accusedCharacter = ui.scanInt(1, getBoard().characters.length, scan) - 1;
//
//		String accusedCharacterName = getBoard().characters[accusedCharacter].getName();
//
//
//		//Room accusation
//
//		ui.println("Accusation: " + accusedCharacterName + " committed the murder in the ...");
//
//		ui.println("Select what room the murder was commited in: ");
//		for(int i = 0; i < roomNames.length; i ++) {
//			ui.println((i + 1) + ". " + roomNames[i]);
//		}
//		int accusedRoom = ui.scanInt(1, roomNames.length, scan) - 1;
//		String accusedRoomName = roomNames[accusedRoom];
//
//
//		//Weapon accusation
//		ui.println("Accusation: " + accusedCharacterName + " committed the murder in the " + accusedRoomName +" with a ...");
//		ui.println("Select the murder weapon:");
//		for(int i = 0; i < weaponNames.length; i ++) {
//			ui.println((i + 1) + ". " + weaponNames[i]);
//		}
//		int accusedWeapon = ui.scanInt(1, weaponNames.length, scan) - 1;
//		String accusedWeaponName = getBoard().weapons[accusedWeapon].getName();
//
//
//		//Final accusation
//
//		ui.println("|Final Accusation: " + accusedCharacterName + " committed the murder in the " + accusedRoomName + " with a " + accusedWeaponName +   ".|");

		//Store in appropriate structure and check against the murderSolution

		String roomName = combo.getRoom().name;
		String characterName = combo.getCharacter().name;
		String weaponName = combo.getWeapon().name;

		int characterIndex = 0;
		int weaponIndex = 0;

		for(int i = 0; i < board.characters.length; i++){
			if(board.characters[i].getName().equals(characterName)){
				characterIndex = i;
				break;
			}
		}

		for(int i = 0; i < board.weapons.length; i++){
			if(board.weapons[i].getName().equals(weaponName)){
				weaponIndex = i;
				break;
			}
		}

		CardCombination finalAccusation = new CardCombination(
				new RoomCard(roomName),
				new CharacterCard(characterName, getBoard().characters[characterIndex]),
				new WeaponCard(weaponName, getBoard().weapons[weaponIndex]));
		
		//Player wins the game if they're correct
		if(finalAccusation.equals(murderSolution)) {
			ui.println("You've solved the murder. You win!");
			return true;
			
		//Remove player from the game if wrong
		}else {
			currentPlayer.eliminate();
			ui.println("You have made a false accusation. You have been eliminated.");
			//TODO Pop up saying elim
			return false;
		}
	}

	private void doSuggest(Player currentPlayer, CardCombination combo) { //TODO Link to card panel

		String characterName = combo.getCharacter().name;
		String weaponName = combo.getWeapon().name;

		//Get player room
		Room suggestedRoom = getBoard().getRoomPlayerIsIn(currentPlayer);
		String suggestedRoomName = suggestedRoom.getName();

		//Find array indexes of character and weapon
		int characterIndex = 0;
		int weaponIndex = 0;

		for(int i = 0; i < board.characters.length; i++){
			if(board.characters[i].getName().equals(characterName)){
				characterIndex = i;
				break;
			}
		}

		for(int i = 0; i < board.weapons.length; i++){
			if(board.weapons[i].getName().equals(weaponName)){
				weaponIndex = i;
				break;
			}
		}

		CardCombination finalSuggestion = new CardCombination(
			new RoomCard(suggestedRoomName),
			new CharacterCard(characterName, board.characters[characterIndex]),
			new WeaponCard(weaponName, board.weapons[weaponIndex]));

		//summon character and weapon to room
		getBoard().moveWeaponTo(finalSuggestion.getWeapon().name, finalSuggestion.getRoom().name);
		getBoard().moveCharacterTo(finalSuggestion.getCharacter().name, finalSuggestion.getRoom().name);

		//make list of players starting from next player
		Player[] suggestionPlayers = new Player[players.length];

		//find the currentPlayer's position in list
		int currPlayerIndex = 0;
		for(int i=0; i<players.length; i++) {
			if(currentPlayer.equals(players[i])) {
				currPlayerIndex = i;
				break;
			}
		}
		//rotate array
		for(int x = 0; x < players.length; x++){
			  suggestionPlayers[(x+players.length-currPlayerIndex) % players.length] = players[x];
			}

		//iterate through players and get the matching cards from their hand
		//first player in array is current player so skip them
		for(int k=1; k<suggestionPlayers.length; k++) {
			//player chooses a card from matching to show (if none, skip them)
			Player p = suggestionPlayers[k];
			//returns list of cards in player's hand that match suggestion
			ArrayList<Card> matchingCards = finalSuggestion.getMatchingCards(p.getCards());
			if(matchingCards.isEmpty()) {
				//player has no matching cards, skip them
				ui.println(p.getCharacter().getName() + " doesn't have any of the suggested cards.");
			}else if(matchingCards.size()==1) {
				//player has one matching card, show it
				ui.println(p.getCharacter().getName() + " shows you the card: " + matchingCards.get(0).getName());
				return;
			}else {
				//player chooses a card from matching to show
				ui.println("-------------------");
				ui.println(p.getCharacter().getName() + " please select which card to show " + players[currPlayerIndex].getCharacter().getName());
				for(int j = 0; j < matchingCards.size(); j ++) {
					ui.println((j + 1) + ". " + matchingCards.get(j).getName());
				}
				int chosenCard = ui.scanInt(1, matchingCards.size(), scan) - 1;
				ui.println("-------------------");
				ui.println(p.getCharacter().getName() + " shows " + players[currPlayerIndex].getCharacter().getName() + " the card: " + matchingCards.get(chosenCard).getName());
				return;
			}
		}
		ui.println("No one has any of the suggested cards.");
		//setWorkStateTo(WorkState.NOT_WAITING);
	}

	private boolean leaveRoom(Player currentPlayer, int exit) {
		ArrayList<Location> exits = getBoard().getAvailableExits(currentPlayer);
		int numOfExits = exits.size();
		//ui.drawBoard(board, exits);
		gui.drawBoard(getBoard(), exits);
		//ui.print("Which exit would you like to take? [");
		//Printing valid exits
		 //ui.print("Exit (1)");
		//for(int i = 1; i < numOfExits; i++) ui.print(", Exit (" + (i + 1) +")");
		//ui.println("], or 0 to end turn");
		//int exit = ui.scanInt(0, numOfExits, scan);
//		if(exit == 0) {
//			return true;
//		}
		getBoard().vacatePlayerFromRoom(currentPlayer, exits.get(exit-1));//Uses -1 as the array starts from 0, but the questions start from 1.
		//ui.drawBoard(board, null);
		gui.drawBoard(getBoard(),null);
		return false;
	}

	private void winGame(Player currentPlayer) {
		ui.println("GAME OVER");
		ui.println(currentPlayer.getCharacter().getName() + " WINS!");
		gameFinished = true;
		//TODO Pop up displaying winner (Probably will just exit on confirmation)
	}
	
	// Method to get the sum of 2 rolled dice
	public int RollDice() {
		int die1 = (int) (Math.random() * 6 + 1);
		int die2 = (int) (Math.random() * 6 + 1);
		return die1 + die2;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setNumPlayers(int n) {
		numPlayers = n;
		players = new Player[numPlayers];
	}
	
	public Object[] getUnassignedCharacters() {
		ArrayList<String> unassigned = new ArrayList<String>();
		for(Character c : getBoard().characters) {
			if(!c.hasPlayer()) {
				unassigned.add(c.getName());
			}
		}
		return unassigned.toArray();
		
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	
	
	public void setGameStateTo(GameState state) {
		gameState = state;
		this.setChanged();
		System.out.println("Game state: " + gameState);
		notifyObservers(gameState);
	}
	
	/*public void setWorkStateTo(WorkState state) {
		workState = state;
		this.setChanged();
		notifyObservers(workState);
	}*/
	
	public void setCurrentPlayerTo(Player player) {
		currentPlayer = player;
		this.setChanged();
		notifyObservers(currentPlayer);
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	

}

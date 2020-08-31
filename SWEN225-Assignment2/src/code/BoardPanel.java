package code;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/* Custom JPanel class to draw board and possibly receive mouse input.
 * 
 */
public class BoardPanel extends JPanel implements MouseListener{

	private Point clickedPoint;
	private int cellWidth;
	private int cellHeight;
	private Board boardField;//TODO REMOVE THIS WHEN ABLE
	private static final int lineWidth=2;
	private GUI gui;//Stores the GUI. Since it's all part of the controller, should be fine.


	//Temporary constructor
	public BoardPanel(Board b, GUI g){
		boardField=b;
		gui=g;
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//System.out.println("Raw X: "+e.getX());
		//System.out.println("Raw Y: "+e.getY());
		// TODO Auto-generated method stub
		//System.out.println("Coord X: "+(e.getX()/cellWidth));
		//System.out.println("Coord Y: "+(e.getY()/cellHeight));
		clickedPoint = new Point(e.getX()/cellWidth, e.getY()/cellHeight);
		if(gui.isCollectorWaiting()&& (gui.getGame().getGameState()== Game.GameState.MOVING)){//If the program is waiting for an input...
			char output = getUserMovementOneTile(boardField, gui.getGame().getCurrentPlayer());
			if(output=='f')
				return;
			//Else, the given result is valid and a move can occur.
			gui.setCollectorInput(output);
			gui.setCollectorState(Game.WorkState.NOT_WAITING);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Draws the provided board, as well as optionally up to four exits.
	 * The exit locations are provided in the arrayList, if null it means no exits need to be drawn.
	 * @param board
	 * @param cellsToHighlight
	 */
	public void drawBoard(Board board, ArrayList<Location> cellsToHighlight){
		Graphics g = this.getGraphics();//Gets the graphics object so we can work with it directly.

		//Panel's size is 400x400
		//Cluedo has 24x25 tileset grid
		//16 pixels per tile in optimal conditions.
		//Maybe add a set of null tiles so it's 25x25?
		cellHeight=(int) (this.getSize().getHeight()/25);
		cellWidth=(int) (this.getSize().getWidth()/25);
		for(int i=0; i<25; i++){
			for(int j=0; j<25; j++){
				//g.drawRect(i*cellWidth, j*cellHeight, cellWidth-1,cellHeight-1);
				selectivePaintSquare(i*cellWidth, j*cellHeight, cellWidth,cellHeight, g, null, i, j);//TODO ADD BOARD INPUT HERE
			} 
		}

	}

	@Override
	public void paintComponent(Graphics g){//This is a one-time print!
		super.paintComponent(g);

		//Panel's size is 400x400
		//Cluedo has 24x25 tileset grid
		//16 pixels per tile in optimal conditions.
		//Maybe add a set of null tiles so it's 25x25?
		cellHeight=(int) (this.getSize().getHeight()/25);
		cellWidth=(int) (this.getSize().getWidth()/25);
		for(int i=0; i<25; i++){
			for(int j=0; j<25; j++){
				//g.drawRect(i*cellWidth, j*cellHeight, cellWidth-1,cellHeight-1);
				selectivePaintSquare(i*cellWidth, j*cellHeight, cellWidth,cellHeight, g, null, i, j);
			}
		}

		}

	/**
	 * Selectively paint one single tile on the board, with the following logic:
	 * If the square is null or out-of-bounds (null), then draw it as entirely black.
	 * If it's a hall, draw it in white
	 * If it's a room, draw it in grey.
	 * Then, use the following logic for walls (borders of the square)
	 * If the tile is a door, draw borders only against null tiles.
	 * If neighbour is the same type of tile OR a door, do not draw a border
	 * Else, draw a border/wall.
	 * @param xTopLeft
	 * @param yTopLeft
	 * @param cellWidth
	 * @param cellHeight
	 * @param g
	 * @param cellX
	 * @param cellY
	 */
	public void selectivePaintSquare(int xTopLeft, int yTopLeft, int cellWidth, int cellHeight, Graphics g, Board b, int cellX, int cellY){
		//Location[][] cells = b.getCellsToDraw(); //TODO Implement this when able! Should be passed the board on every draw
		Location[][] cells = boardField.getCellsToDraw();
		Location thisCell;
		if(cellX>=cells.length || cellY>=cells[0].length)
			thisCell=null;
		else
			thisCell=cells[cellX][cellY];
		if(thisCell==null){//Case one, the cell is null - so draw it as black entirely, then move on.
			g.setColor(Color.black);
			g.fillRect(xTopLeft,yTopLeft,cellWidth,cellHeight);
			return;
		}
		if(thisCell instanceof Hallway)
			g.setColor(Color.lightGray);
		else if (thisCell instanceof Room)
			g.setColor(Color.darkGray);
		else
			System.out.println("CRITICAL ERROR! TRIED TO PRINT A ROOM TILE OF UNKNOWN TYPE AT COORDINATES X:"+cellX+", Y:"+cellY);

		g.fillRect(xTopLeft,yTopLeft,cellWidth,cellHeight);//Correct color's been chosen, now draw the "background".


		//Beginning of wall-drawing logic (I'm so sorry. Will tidy up later if possible)
		if(thisCell instanceof Room)
			if(((Room) thisCell).isDoor())
				return;//If it's a door, it won't draw any "walls", so return.

		g.setColor(Color.black);//When drawing walls, set color to black.
		//Top wall
		if(cellX>=cells.length || cellY-1<0 || cells[cellX][cellY-1]==null)//If top wall would be null, draw line.
			g.drawLine(xTopLeft, yTopLeft, xTopLeft+cellWidth, yTopLeft);
		else if (cells[cellX][cellY-1].getClass().equals(thisCell.getClass())|| ((cells[cellX][cellY-1] instanceof Room) && ((Room)cells[cellX][cellY-1]).isDoor())){
			//Do nothing! This happens if the compared tile is the same as this one, or if it's a doorway.
		} else//Else, they're definitely different types, so draw a wall
			g.drawLine(xTopLeft, yTopLeft, xTopLeft+cellWidth, yTopLeft);


		//Left wall
		if(cellX-1<0 || cellY>=cells[0].length || cells[cellX-1][cellY]==null)//If left wall would be null, draw line.
			g.drawLine(xTopLeft, yTopLeft, xTopLeft, yTopLeft+cellHeight);
		else if (cells[cellX-1][cellY].getClass().equals(thisCell.getClass())|| ((cells[cellX-1][cellY] instanceof Room) && ((Room)cells[cellX-1][cellY]).isDoor())){
			//Do nothing! This happens if the compared tile is the same as this one, or if it's a doorway.
		} else//Else, they're definitely different types, so draw a wall
			g.drawLine(xTopLeft, yTopLeft, xTopLeft, yTopLeft+cellHeight);

		//Right wall
		if(cellX+1>=cells.length || cellY>=cells[0].length || cells[cellX+1][cellY]==null)//If right wall would be null, draw line.
			g.drawLine(xTopLeft+cellWidth, yTopLeft, xTopLeft+cellWidth, yTopLeft+cellHeight);
		else if (cells[cellX+1][cellY].getClass().equals(thisCell.getClass())|| ((cells[cellX+1][cellY] instanceof Room) && ((Room)cells[cellX+1][cellY]).isDoor())){
			//Do nothing! This happens if the compared tile is the same as this one, or if it's a doorway.
		} else//Else, they're definitely different types, so draw a wall
			g.drawLine(xTopLeft+cellWidth, yTopLeft, xTopLeft+cellWidth, yTopLeft+cellHeight);

		//Bottom wall
		if(cellX>=cells.length || cellY+1>=cells[0].length || cells[cellX][cellY+1]==null) {//If top wall would be null, draw line.
			g.drawLine(xTopLeft, yTopLeft + cellHeight, xTopLeft + cellWidth, yTopLeft + cellHeight);
		}
		else if (cells[cellX][cellY+1].getClass().equals(thisCell.getClass())|| ((cells[cellX][cellY+1] instanceof Room) && ((Room)cells[cellX][cellY+1]).isDoor())){
			//Do nothing! This happens if the compared tile is the same as this one, or if it's a doorway.
		} else//Else, they're definitely different types, so draw a wall
			g.drawLine(xTopLeft, yTopLeft+cellHeight, xTopLeft+cellWidth, yTopLeft+cellHeight);

		//End of wall-drawing logic
		
		MoveablePiece pieceToDraw = thisCell.getPiece();
		if(pieceToDraw!=null){
			if(pieceToDraw instanceof Character){
				//Draw the underlying colour, then the border around it.
				//For characters, this is a circle. so get the character's color with a switch.
				String charName = pieceToDraw.getName();
				switch (charName){
					case "Miss Scarlett":
						g.setColor(Color.red);
						break;

					case "Col. Mustard":
						g.setColor(Color.yellow);
						break;

					case "Mrs. White":
						g.setColor(Color.white);
						break;

					case "Mr. Green":
						g.setColor(Color.green);
						break;

					case "Mrs. Peacock":
						g.setColor(Color.blue);
						break;

					case "Prof. Plum":
						g.setColor(Color.magenta);
						break;
					default:
						System.out.println("ERROR! TRIED TO DRAW INVALID CHARACTER:"+charName);
				}//Correct colour is selected, now draw the symbol.
				g.fillOval(xTopLeft+cellWidth/4,yTopLeft+cellHeight/4,cellWidth/2,cellHeight/2);
				//If this character is the current character, draw a black circle around them!
				Game gam = boardField.getGame();
				Player p = gam.getCurrentPlayer();
				MoveablePiece currentCharacter = p.getCharacter();
				if(pieceToDraw==currentCharacter) {
					g.setColor(Color.black);
					int circleWidth= (int) (cellWidth/1.25);
					int circleHeight= (int) (cellHeight/1.25);
					g.drawOval(xTopLeft+cellWidth/8,yTopLeft+cellWidth/8,circleWidth,circleHeight);
				}

			} else if (pieceToDraw instanceof Weapon){
				g.setColor(Color.red);
				String weaponName = pieceToDraw.getName();

				switch(weaponName){
					case "Candlestick":
						g.drawString("C", xTopLeft+cellWidth/2, yTopLeft+cellHeight);
						break;

					case "Dagger":
						g.drawString("D", xTopLeft+cellWidth/2, yTopLeft+cellHeight);
						break;

					case "Lead pipe":
						g.drawString("L", xTopLeft+cellWidth/2, yTopLeft+cellHeight);
						break;

					case "Revolver":
						g.drawString("R", xTopLeft+cellWidth/2, yTopLeft+cellHeight);
						break;

					case "Rope":
						g.drawString("r", xTopLeft+cellWidth/2, yTopLeft+cellHeight);
						break;

					case "Spanner":
						g.drawString("S", xTopLeft+cellWidth/2, yTopLeft+cellHeight);
						break;
					default:
						System.out.println("CRITICAL ERROR! TRIED TO DRAW NON-WEAPON:"+pieceToDraw.getName());
				}


			} else{
				System.out.println("CRITICAL ERROR! TRIED TO DRAW PIECE:"+pieceToDraw+" WHICH IS NEITHER A CHARACTER NOR A WEAPON.");
			}

		}

		g.setColor(Color.red);//for debugging. Anything which is drawn which SHOULDN'T be drawn will be in red.

	}

	public char getUserMovementOneTile(Board b, Player currentPlayer){
		//boolean continueLoop=true;
		char toReturn='f';
		MoveablePiece character = currentPlayer.getCharacter();
		int playerx=character.getX();
		int playery=character.getY();
		//System.out.println("Character x:"+playerx+". Click x:"+clickedPoint.x);
		//System.out.println("Character y:"+playery+". Click y:"+clickedPoint.y);
			//"If clicked point is adjacent to the character, check if the tile is valid, then return.
				if((clickedPoint.x==playerx && clickedPoint.y==playery+1) && isValidAdjacentMove(b, currentPlayer, 0, 1))
					return 's';
				if((clickedPoint.x==playerx && clickedPoint.y==playery-1) && isValidAdjacentMove(b, currentPlayer, 0, -1))
					return 'n';
				if((clickedPoint.x==playerx+1 && clickedPoint.y==playery) && isValidAdjacentMove(b, currentPlayer, 1, 0))
					return 'e';
				if(clickedPoint.x==playerx-1 && clickedPoint.y==playery && isValidAdjacentMove(b, currentPlayer, -1, 0))
					return 'w';


		return toReturn;
	}

	/**
	 * Checks the change in x and y coordinates given and sees if it's a valid move given the current player's position
	 * Only considers single-space moves, no diagonals.
	 * @param b
	 * @param currentPlayer
	 * @param xchange
	 * @param ychange
	 * @return
	 */
	private boolean isValidAdjacentMove(Board b, Player currentPlayer, int xchange, int ychange){
		MoveablePiece currentChar=currentPlayer.getCharacter();
		Location[][] cells = b.getCellsToDraw();
		int xToCheck=currentChar.getX()+xchange;
		int yToCheck=currentChar.getY()+ychange;
		//System.out.println("Running isValidAdjacentMove!");

		if(cells.length>xToCheck && xToCheck >= 0 && cells[0].length>yToCheck && yToCheck>=0){//If the spot on the cell array is valid
			//System.out.println("IsValidMove sees the given coordinates as valid!");
			Location checkedCell=cells[xToCheck][yToCheck];
			if(checkedCell==null)
				return false;
			if(checkedCell instanceof Hallway)
				return true;
			if(checkedCell instanceof Room){
				Room doorCheck = (Room) checkedCell;
				return doorCheck.isDoor();//If it's a door, moving is valid. Else, it's not.
			}

		}

		return false;
	}




}

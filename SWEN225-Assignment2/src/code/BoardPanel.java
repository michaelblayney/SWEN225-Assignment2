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



	private Board boardField;//TODO REMOVE THIS WHEN ABLE
	private static final int lineWidth=2;


	//Temporary constructor
	public BoardPanel(Board b){
		boardField=b;
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
		System.out.println("X: "+e.getX());
		System.out.println("Y: "+e.getY());
		// TODO Auto-generated method stub
		
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


	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//TODO Move this to the drawBoard method when done because this is TEMPORARY.
		//TODO Particularly, it must be passed in a current snapshot of the board.
		//Particularly, this is just going to be in effect until enough of the rest of the program works so that the board can be called from the game class (as currently it is not)


		//Panel's size is 400x400
		//Cluedo has 24x25 tileset grid
		//16 pixels per tile in optimal conditions.
		//Maybe add a set of null tiles so it's 25x25?
		int cellHeight=(int) (this.getSize().getHeight()/25);
		int cellWidth=(int) (this.getSize().getWidth()/25);
		for(int i=0; i<25; i++){
			for(int j=0; j<25; j++){
				//g.drawRect(i*cellWidth, j*cellHeight, cellWidth-1,cellHeight-1);
				selectivePaintSquare(i*cellWidth, j*cellHeight, cellWidth,cellHeight, g, null, i, j);//TODO ADD BOARD INPUT HERE
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





}

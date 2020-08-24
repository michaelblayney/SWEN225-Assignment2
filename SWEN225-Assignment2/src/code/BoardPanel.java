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

	//TODO Start with drawing the board every time it's called. Simple enough.


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
		//Particularly, this is just going to be in effect until enough of the rest of the program works so that the board can be called from the game class (as currently it is not)


		//Panel's size is 400x400
		//Cluedo has 24x25 tileset grid
		//16 pixels per tile in optimal conditions.
		//Maybe add a set of null tiles so it's 25x25?
		int cellHeight=(int) (this.getSize().getHeight()/25);
		int cellWidth=(int) (this.getSize().getWidth()/25);
		for(int i=0; i<25; i++){
			for(int j=0; j<25; j++){
				g.drawRect(i*cellWidth, j*cellHeight, cellWidth-1,cellHeight-1);
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

	}



}

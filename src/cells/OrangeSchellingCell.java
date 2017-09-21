package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Madhavi
 */
public class OrangeSchellingCell extends Cell{
	
	private int rowNum;
	private int colNum;
	private Rectangle block;
	private ArrayList<Cell> neighbors;
	private int threshold;
	private int width;
	private int height;
	
	public OrangeSchellingCell(int myRowNum, int myColNum, int width, int height) {
		super(myRowNum, myColNum, width, height);
		block.setFill(Color.DARKORANGE);
	}
	
	public void setThreshold(int t) {
		threshold = t;
	}
	
	public boolean isNeighbor(int otherRowNum, int otherColNum) {
		return super.isNeighbor8(otherRowNum, otherColNum);
	}
	/* (non-Javadoc)
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 * Overrides superclass method to move cell to random empty location if not satisfied with current location.
	 */
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		double numBlue = (double)getNumBlueNeighbors();
		double numOrange = (double)getNumOrangeNeighbors();
		boolean satisfied = numOrange/(numOrange+numBlue) >= threshold;
		if(!satisfied) {
			if(!moveToRandomPlace(emptySpots, grid)) {
				grid.addToNewGrid(this);
			}
		}
		else {
			grid.addToNewGrid(this);
		}
	}
}

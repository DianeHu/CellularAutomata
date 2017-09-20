package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Madhavi
 */
public class BlueSchellingCell extends Cell{
private double threshold;
	
	public BlueSchellingCell(int myRowNum, int myColNum, int w, int h) {
		super(myRowNum, myColNum, w, h);
		setColor(Color.NAVY);
	}
	
	public void setThreshold(double t) {
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
		boolean satisfied = numBlue/(numOrange+numBlue) >= threshold;
		if(!satisfied) {
			if(!moveToRandomPlace(emptySpots, grid)) {
				grid.addToNewGrid(this);
			}
		}
		else {
			grid.addToNewGrid(this);
		}
	}
	
	public boolean isNeighbor(int otherRowNum, int otherColNum) {
		return super.isNeighbor8(otherRowNum, otherColNum);
	}
}

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
	private int rowNum;
	private int colNum;
	private Rectangle block;
	private ArrayList<Cell> neighbors;
	private double satisfiedThreshold = 0.3;
	
	public BlueSchellingCell(int myRowNum, int myColNum, int width, int height) {
		super(myRowNum, myColNum, width, height);
		block.setFill(Color.NAVY);
	}
	
	/* (non-Javadoc)
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 * Overrides superclass method to move cell to random empty location if not satisfied with current location.
	 */
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		double numBlue = (double)getNumBlueNeighbors();
		double numOrange = (double)getNumOrangeNeighbors();
		boolean satisfied = numBlue/(numOrange+numBlue) >= satisfiedThreshold;
		if(!satisfied) {
			moveToRandomEmptySpace(emptySpots, grid);
		}
	}
}

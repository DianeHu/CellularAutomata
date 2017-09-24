package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Madhavi Rajiv
 * 
 *         This class implements the action of the orange cell type in the
 *         Segregation simulation
 */
public class OrangeSchellingCell extends Cell {

	private double threshold;

	public OrangeSchellingCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.DARKORANGE);
	}

	public OrangeSchellingCell() {
		super();
		setColor(Color.DARKORANGE);
	}

	/**
	 * @param t
	 *            Sets the threshold proportion for being unsatisfied as t
	 */
	public void setThreshold(double t) {
		threshold = t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		OrangeSchellingCell newCell = new OrangeSchellingCell();
		newCell.setThreshold(threshold);
		return newCell;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#isNeighbor(int, int, int, int)
	 */
	@Override
	public boolean isNeighbor(int otherRowNum, int otherColNum, int numRows, int numCols) {
		return super.isNeighbor8(otherRowNum, otherColNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid) Overrides
	 * superclass method to move cell to random empty location if not satisfied with
	 * current location.
	 */
	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		double numBlue = (double) getNumBlueNeighbors();
		double numOrange = (double) getNumOrangeNeighbors();
		boolean satisfied = numOrange / (numOrange + numBlue) >= threshold | numOrange + numBlue == 0;
		if (!satisfied) {
			if (!moveToRandomPlace(emptySpots, grid)) {
				grid.addToNewGrid(this);
			}
		} else {
			grid.addToNewGrid(this);
		}
	}

}

package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Madhavi Rajiv
 * 
 *         This class implements the action of the blue cell type in the
 *         Segregation simulation
 */
public class BlueSchellingCell extends Cell {
	private double threshold;

	public BlueSchellingCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.NAVY);
	}

	public BlueSchellingCell() {
		super();
		setColor(Color.NAVY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		BlueSchellingCell newCell = new BlueSchellingCell();
		newCell.setThreshold(threshold);
		return newCell;
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
		boolean satisfied = numBlue / (numOrange + numBlue) >= threshold | numOrange + numBlue == 0;
		if (!satisfied) {
			if (!moveToRandomPlace(emptySpots, grid)) {
				grid.addToNewGrid(this);
			}
		} else {
			grid.addToNewGrid(this);
		}
	}

}

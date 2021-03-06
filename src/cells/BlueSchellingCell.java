package cells;

import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Madhavi Rajiv
 * 
 *         This class implements the action of the blue cell type in the
 *         Segregation simulation
 */
public class BlueSchellingCell extends Cell {
	private static final Color BLUE_SCHELLING_COLOR = Color.NAVY;
	private double threshold;
	
	public BlueSchellingCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(BLUE_SCHELLING_COLOR);
	}


	public BlueSchellingCell() {
		super();
		setColor(BLUE_SCHELLING_COLOR);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		BlueSchellingCell newCell = new BlueSchellingCell();
		newCell.setThreshold(threshold, 0, 0);
		return newCell;
	}
	
	@Override
	public Cell changeType() {
		OrangeSchellingCell newCell = new OrangeSchellingCell(this.getRow(), this.getCol());
		return newCell;
	}

	/**
	 * @param t
	 *            Sets the threshold proportion for being unsatisfied as t
	 */
	public void setThreshold(double t, double unused1, double unused2) {
		threshold = t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid) Overrides
	 * superclass method to move cell to random empty location if not satisfied with
	 * current location.
	 */
	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		double numBlue = (double) getNumNeighborsOfType(new BlueSchellingCell());
		double numOrange = (double) getNumNeighborsOfType(new OrangeSchellingCell());
		boolean satisfied = numBlue / (numOrange + numBlue) >= threshold | numOrange + numBlue == 0;
		segregationMove(emptySpots, grid, satisfied);
	}

}

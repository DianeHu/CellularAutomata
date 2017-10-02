package cells;

import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Madhavi Rajiv
 * 
 *         This class implements the action of the orange cell type in the
 *         Segregation simulation
 */
public class OrangeSchellingCell extends Cell {

	private static final Color ORANGE_SCHELLING_COLOR = Color.DARKORANGE;
	private double threshold;
	
	public OrangeSchellingCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(ORANGE_SCHELLING_COLOR);
	}

	public OrangeSchellingCell() {
		super();
		setColor(ORANGE_SCHELLING_COLOR);
	}
	
	/**
	 * @param t
	 *            Sets the threshold proportion for being unsatisfied as t
	 */
	public void setThreshold(double t, double unused1, double unused2) {
		threshold = t;
	}

	@Override
	public Cell changeType() {
		BlueSchellingCell newCell = new BlueSchellingCell(this.getRow(), this.getCol());
		return newCell;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		OrangeSchellingCell newCell = new OrangeSchellingCell();
		newCell.setThreshold(threshold, 0, 0);
		return newCell;
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
		boolean satisfied = numOrange / (numOrange + numBlue) >= threshold | numOrange + numBlue == 0;
		segregationMove(emptySpots, grid, satisfied);
	}


}

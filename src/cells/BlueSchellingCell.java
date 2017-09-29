package cells;

import java.util.ArrayList;
import java.util.List;

import cellManager.Grid;
import cellManager.RectangleGrid;
import gridPatches.ForagingLand;
import javafx.beans.value.ChangeListener;
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
		newCell.setThreshold(threshold, 0, 0);
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

package cells;

import java.util.ArrayList;
import java.util.List;

import cellManager.Grid;
import cellManager.RectangleGrid;
import gridPatches.ForagingLand;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 * 
 *         Implements cell type land in spreading wildfire simulation.
 */
public class EmptyLandCell extends Cell {

	/**
	 * Since there is only one threshold value for all EmptyLandCells (all cells
	 * grow new trees with the same likelihood), and the value stays constant across
	 * the simulation, this variable is static. Also addresses issue of new cells
	 * not being instantiated with appropriate threshold values.
	 */
	private static double probGrow;
	
	public EmptyLandCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.BROWN);
	}

	/**
	 * Constructor that does not specify row or column number.
	 */
	public EmptyLandCell() {
		super();
		setColor(Color.BROWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		EmptyLandCell newCell = new EmptyLandCell();
		newCell.setThreshold(probGrow);
		return newCell;
	}

	/**
	 * @param num
	 * 
	 *            Setter for probGrow.
	 */
	public void setThreshold(double num) {
		probGrow = num;
	}

	/**
	 * @return Returns true if by a certain probability threshold a new tree should
	 *         grow.
	 */
	private boolean shouldGrow() {
		double test = Math.random();
		if (test < probGrow) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 * 
	 * Overrides superclass moveCell method. If a new tree should grow (determined
	 * by random generated value, then a new tree replaces the current spot,
	 * otherwise the current emptyLandCell persists into the next grid.
	 */
	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		if (shouldGrow()) {
			createNewCellOfType(new TreeCell(),grid);
		} else {
			grid.addToNewGrid(this);
		}
	}
}

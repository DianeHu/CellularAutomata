package cells;

import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 * 
 *         Implements cell type burning tree in wildfire simulation.
 */
public class BurningTreeCell extends Cell {

	/**
	 * @param myRowNum
	 * @param myColNum
	 * 
	 *            Constructor for BurningTreeCell that takes in a row and column
	 *            number.
	 */
	public BurningTreeCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.DARKORANGE);
	}

	/**
	 * Constructor for BurningTreeCell that does not specify row or column number.
	 */
	public BurningTreeCell() {
		super();
		setColor(Color.DARKORANGE);
	}

	/* (non-Javadoc)
	 * @see cells.Cell#changeType()
	 * 
	 * Changes type of cell from tree to burningtreecell on user mouse click.
	 */
	@Override
	public Cell changeType() {
		TreeCell newCell = new TreeCell(this.getRow(), this.getCol());
		return newCell;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		BurningTreeCell newCell = new BurningTreeCell();
		return newCell;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#setThreshold(double, double, double)
	 * 
	 * No thresholds are set for a burning cell. Included as the main method is
	 * abstract.
	 */
	@Override
	public void setThreshold(double a, double b, double c) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 * 
	 * Overrides superclass moveCell method to burn out BurningTreeCells at each
	 * step.
	 */
	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		createNewCellOfType(new EmptyLandCell(), grid);
	}

}

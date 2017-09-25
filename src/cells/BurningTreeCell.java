package cells;

import java.util.ArrayList;

import cellManager.Grid;
import cellManager.RectangleGrid;
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
	 * @see cells.Cell#isNeighbor(int, int)
	 * 
	 * This method overrides the superclass method to only account for the compass
	 * directions (North, South, East, West) as neighbors.
	 */
	@Override
	public boolean isNeighbor(int otherRowNum, int otherColNum, int numRows, int numCols) {
		return super.isNeighbor4(otherRowNum, otherColNum);
	}

	/**
	 * @param root
	 *            Replaces the current burning cell with an empty cell.
	 */
	public void burnOut(Grid grid) {
		EmptyLandCell newCell = new EmptyLandCell(getRow(), getCol());
		grid.addToNewGrid(newCell);
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
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		burnOut(grid);
	}

}

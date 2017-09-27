package cells;

import java.util.ArrayList;
import java.util.List;

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


	/**
	 * @param root
	 *            Replaces the current burning cell with an empty cell.
	 */
	public void burnOut(Grid newGrid) {
		EmptyLandCell newCell = new EmptyLandCell(getRow(), getCol());
		newGrid.addToNewGrid(newCell);
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
		burnOut(grid);
	}

}

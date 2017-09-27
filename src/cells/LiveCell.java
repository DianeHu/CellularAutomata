package cells;

import java.util.ArrayList;
import java.util.List;

import cellManager.Grid;
import cellManager.RectangleGrid;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 * 
 *         Implements cell type live for game-of-life simulation.
 */
public class LiveCell extends Cell {

	/**
	 * @param myRowNum
	 * @param myColNum
	 * 
	 *            Constructor that takes in a specific row and column number.
	 */
	public LiveCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.DARKCYAN);
	}

	/**
	 * Constructor that does not specify row or column number.
	 */
	public LiveCell() {
		super();
		setColor(Color.DARKCYAN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		Cell newCell = new LiveCell();
		return newCell;
	}

	/**
	 * @param root
	 *            Checks the number of live neighbors. If number of live neighbors
	 *            indicates over or underpopulation, then replace the current live
	 *            cell with a dead one. Subsequently resets the number of live
	 *            neighbors to zero.
	 */
	private void dieOut(Grid grid) {
		Cell newCell = new DeadCell(this.getRow(), this.getCol());
		grid.addToNewGrid(newCell);
	}

	/**
	 * @return Returns whether or not a live cell should die based on over or
	 *         underpopulation.
	 */
	private boolean shouldDie() {
		if (checkNumLiveNeighbors() < 2 || checkNumLiveNeighbors() > 3) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 * 
	 * Overrides Cell superclass moveCell to direct LiveCell behavior. LiveCell dies
	 * when in state of over or underpopulation, otherwise lives into the next
	 * generation.
	 */
	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		if (shouldDie()) {
			dieOut(grid);
		} else {
			grid.addToNewGrid(this);
		}
	}
}

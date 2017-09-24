package cells;

import java.util.ArrayList;


import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 */
public class LiveCell extends Cell {

	public LiveCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.DARKCYAN);
	}
	
	public LiveCell() {
		super();
		setColor(Color.DARKCYAN);
	}

	@Override
	public boolean isNeighbor(int otherRowNum, int otherColNum, int numRows, int numCols) {
		return super.isNeighbor8(otherRowNum, otherColNum);
	}
	
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
	private void dieOut(Grid newGrid) {
		Cell newCell = new DeadCell(this.getRow(), this.getCol());
		newGrid.addToNewGrid(newCell);
	}

	private boolean shouldDie() {
		if (checkNumLiveNeighbors() < 2 || checkNumLiveNeighbors() > 3) {
			return true;
		}
		return false;
	}

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		if (shouldDie()) {
			dieOut(grid);
		} else {
			grid.addToNewGrid(this);
		}
	}
}

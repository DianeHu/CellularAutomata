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
 *         Implements cell type live for game-of-life simulation.
 */
public class LiveCell extends Cell {

	
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
	 * @return Returns whether or not a live cell should die based on over or
	 *         underpopulation.
	 */
	private boolean shouldDie() {
		if ((getNumNeighborsOfType(new LiveCell())) < 2 || getNumNeighborsOfType(new LiveCell()) > 3) {
			return true;
		}
		return false;
	}
	
	@Override
	public void setThreshold(double t, double unused1, double unused2) {
		// do nothing
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
			createNewCellOfType(new DeadCell(),grid);
		} else {
			grid.addToNewGrid(this);
		}
	}
}

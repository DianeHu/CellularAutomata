package cells;

import java.util.ArrayList;
import java.util.List;

import cellManager.Grid;
import cellManager.RectangleGrid;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 * 
 *         Implements cell type dead in simulation type game-of-life.
 */
public class DeadCell extends Cell {

	/**
	 * @param myRowNum
	 * @param myColNum
	 * 
	 *            Makes new DeadCell, extension of superclass Cell.
	 */
	public DeadCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.BLACK);
	}

	/**
	 * Constructor for DeadCell that does not specify row or column number
	 */
	public DeadCell() {
		super();
		setColor(Color.BLACK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		Cell newCell = new DeadCell();
		return newCell;
	}

	/**
	 * @param root
	 *            Replaces dead cell with live one.
	 */
	private void resurrectCell(Grid grid) {
		Cell newCell = new LiveCell(this.getRow(), this.getCol());
		grid.addToNewGrid(newCell);
	}

	/**
	 * @return
	 * 
	 * 		Checks the number of live neighbors. If the number is exactly three,
	 *         then returns true--cell should be replaced with live one, as if by
	 *         reproduction.
	 */
	private boolean shouldResurrect() {
		if (getNumNeighborsOfType(new LiveCell()) == 3) {
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 * 
	 * Overrides superclass moveCell method. If cell should be resurrect, then
	 * replace with live one, otherwise keep dead cell in next grid.
	 */
	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		if (shouldResurrect()) {
			resurrectCell(grid);
		} else {
			grid.addToNewGrid(this);
		}
	}
}

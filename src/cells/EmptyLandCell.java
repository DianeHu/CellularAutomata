package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 */
public class EmptyLandCell extends Cell {

	private double probGrow = 0.1;

	/**
	 * @param myRowNum
	 * @param myColNum
	 * 
	 *            Makes new EmptyLandCell, extension of superclass Cell.
	 */
	public EmptyLandCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.BROWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#isSurroundingNeighbor(int, int) This method overrides the
	 * superclass method to only account for the compass directions (North, South,
	 * East, West) as neighbors
	 */
	@Override
	public boolean isNeighbor(int otherRowNum, int otherColNum) {
		return super.isNeighbor4(otherRowNum, otherColNum);
	}

	/**
	 * @param root
	 * @param cell
	 *            Place a new tree in the current empty spot's location.
	 */
	private void growTree(Grid newGrid) {
		Cell newCell = new TreeCell(this.getRow(), this.getCol());
		newGrid.addToNewGrid(newCell);
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
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		if (shouldGrow()) {
			growTree(grid);
		} else {
			grid.addToNewGrid(this);
		}
	}
}

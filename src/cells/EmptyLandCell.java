package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 */
public class EmptyLandCell extends Cell {

	private double probGrow = 0.1;

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
	 *            For a cell that is empty, if a random generated value is contained
	 *            within the probability of a tree growing, place a new tree in the
	 *            current empty spot's location.
	 */
	private void growTree(Grid newGrid) {
		Cell newCell = new TreeCell(this.getRow(), this.getCol());
		newGrid.addToNewGrid(newCell);
	}

	private boolean shouldGrow() {
		double test = Math.random();
		if (test < probGrow) {
			return true;
		}
		return false;
	}

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		if (shouldGrow()) {
			growTree(grid);
		} else {
			grid.addToNewGrid(this);
		}
	}
}

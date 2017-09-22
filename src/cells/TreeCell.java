package cells;

import java.util.ArrayList;


import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 */
public class TreeCell extends Cell {

	private double probCatch = .5;

	public TreeCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.FORESTGREEN);
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
	 *            If current cell experiences a threat of fire, then if a random
	 *            generated value is bounded within the severity of the catch times
	 *            the probability of catching fire, the current cell starts to burn.
	 */
	private void burn(Grid newGrid) {
		double test = Math.random();
		if (test < getNumBurningNeighbors() * probCatch) {
			Cell newCell = new BurningTreeCell(this.getRow(), this.getCol());
			newGrid.addToNewGrid(newCell);
		} else {
			newGrid.addToNewGrid(this);
		}
	}

	/**
	 * For every cell in the current cell's neighbors, if there is a burning cell,
	 * indicate there is a threat of fire, and increase severity by number of
	 * burning cells.
	 */
	private boolean checkFireThreat() {
		for (Cell cell : getNeighbors()) {
			if (cell instanceof BurningTreeCell) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param root
	 * @param cell
	 *            For a cell that is empty, if a random generated value is contained
	 *            within the probability of a tree growing, place a new tree in the
	 *            current empty spot's location.
	 */

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		if (checkFireThreat()) {
			burn(grid);
		} else {
			grid.addToNewGrid(this);
		}
	}
}

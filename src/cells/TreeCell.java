package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 */
public class TreeCell extends Cell {

	private double probCatch;

	/**
	 * @param myRowNum
	 * @param myColNum
	 * 
	 *            Makes new TreeCell, extension of superclass Cell.
	 */
	public TreeCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.FORESTGREEN);
	}
	
	public TreeCell() {
		super();
		setColor(Color.FORESTGREEN);
	}
	
	@Override
	public void setThreshold(double num) {
		probCatch = num;
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
	 *            If cell doesn't burn, then cell persists into next grid.
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
	 * indicate there is a threat of fire.
	 */
	private boolean checkFireThreat() {
		for (Cell cell : getNeighbors()) {
			if (cell instanceof BurningTreeCell) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 * 
	 * Overrides superclass moveCell method. If there is a fire threat, the tree
	 * burns. Otherwise, the tree persists into the next grid if there is no fire
	 * threat.
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

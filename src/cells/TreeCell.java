package cells;

import java.util.ArrayList;

import cellManager.Grid;
import cellManager.RectangleGrid;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 * 
 *         Implements cell type tree in spreading wildfire simulation.
 */
public class TreeCell extends Cell {

	/**
	 * Since there is only one threshold value for all TreeCells (all cells burn
	 * with the same likelihood), and the value stays constant across the
	 * simulation, this variable is static. Also addresses issue of new cells not
	 * being instantiated with appropriate threshold values.
	 */
	private static double probCatch;

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

	/**
	 * Constructor that does not specify row and column number.
	 */
	public TreeCell() {
		super();
		setColor(Color.FORESTGREEN);
	}

	/**
	 * @param num
	 * 
	 *            Setter for probCatch.
	 */
	public void setThreshold(double num) {
		probCatch = num;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		TreeCell newCell = new TreeCell();
		newCell.setThreshold(probCatch);
		return newCell;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#isSurroundingNeighbor(int, int) This method overrides the
	 * superclass method to only account for the compass directions (North, South,
	 * East, West) as neighbors
	 */
	@Override
	public boolean isNeighbor(int otherRowNum, int otherColNum, int numRows, int numCols) {
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

package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 */
public class BurningTreeCell extends Cell {

	/**
	 * @param myRowNum
	 * @param myColNum
	 * 
	 *            Makes new BurningTreeCell, extension of superclass Cell.
	 */
	public BurningTreeCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.DARKORANGE);
	}
	
	public BurningTreeCell() {
		super();
		setColor(Color.DARKORANGE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#isNeighbor(int, int)
	 * 
	 * This method overrides the superclass method to only account for the compass
	 * directions (North, South, East, West) as neighbors.
	 */
	@Override
	public boolean isNeighbor(int otherRowNum, int otherColNum) {
		return super.isNeighbor4(otherRowNum, otherColNum);
	}

	/**
	 * @param root
	 *            Replaces the current burning cell with an EmptyLandCell.
	 */
	private void burnOut(Grid newGrid) {
		Cell newCell = new EmptyLandCell(this.getRow(), this.getCol());
		changeCellType(newGrid, newCell);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 * 
	 * Overrides superclass moveCell method. BurningTreeCells burn out at each step.
	 */
	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		burnOut(grid);
	}
}

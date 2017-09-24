package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Diane Hu
 */
public class BurningTreeCell extends Cell{
	
	private static double probGrow;
	
	public BurningTreeCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.DARKORANGE);
	}
	
	public BurningTreeCell() {
		super();
		setColor(Color.DARKORANGE);
	}
	
	@Override
	public Cell copy() {
		BurningTreeCell newCell = new BurningTreeCell();
		newCell.setThreshold(probGrow);
		return newCell;
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
	public boolean isNeighbor(int otherRowNum, int otherColNum, int numRows, int numCols) {
		return super.isNeighbor4(otherRowNum, otherColNum);
	}
	/**
	 * @param root
	 * Replaces the current burning cell with an empty cell.
	 */
	public void burnOut(Grid newGrid) {
		EmptyLandCell newCell = new EmptyLandCell(getRow(), getCol());
		newCell.setThreshold(probGrow);
		changeCellType(newGrid, newCell);
	}
	

	public void setThreshold(double num) {
		probGrow = num;
	}

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		burnOut(grid);
	}

}

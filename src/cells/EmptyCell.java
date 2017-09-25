package cells;

import java.util.ArrayList;

import cellManager.Grid;
import cellManager.RectangleGrid;
import javafx.scene.paint.Color;

/**
 * @author Madhavi
 * @author Diane Hu
 * 
 *         This class describes the behavior of an EmptyCell which is a stand in
 *         for a vacancy in a grid. This Cell doesn't actually do much; it just
 *         works as a signal for the fact that another cell type could move into
 *         its location.
 */
public class EmptyCell extends Cell {

	/**
	 * @param myRowNum
	 * @param myColNum
	 * 
	 *            Constructor for a cell that specifies row and column number
	 */
	public EmptyCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.FLORALWHITE);
	}

	/**
	 * Constructor for a cell that does not specify row or column number
	 */
	public EmptyCell() {
		super();
		setColor(Color.FLORALWHITE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		Cell newCell = new EmptyCell();
		return newCell;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#isNeighbor(int, int, int, int)
	 * 
	 * Overrides isNeighbor method to return all 8 adjacent neighbors
	 */
	public boolean isNeighbor(int otherRowNum, int otherColNum, int numRows, int numCols) {
		return super.isNeighbor8(otherRowNum, otherColNum);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 * 
	 * Included as a do-nothing method, since in the cell superclass moveCell is
	 * abstracted. Empty cells don't move, so no behavior is necessary
	 */
	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		// do nothing
	}

}

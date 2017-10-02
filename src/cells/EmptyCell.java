package cells;

import java.util.List;

import cellManager.Grid;
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

	@Override
	public Cell changeType() {
		return this;
		// TODO
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
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 * 
	 * Included as a do-nothing method, since in the cell superclass moveCell is
	 * abstracted. Empty cells don't move, so no behavior is necessary
	 */
	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		// do nothing
	}


	@Override
	public void setThreshold(double a, double b, double c) {
		// do nothing
	}

}

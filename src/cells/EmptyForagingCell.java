package cells;

import java.util.ArrayList;
import java.util.List;

import cellManager.Grid;
import cellManager.RectangleGrid;
import gridPatches.ForagingLand;
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
public class EmptyForagingCell extends Cell {

	
	public EmptyForagingCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.CYAN);
	}


	/**
	 * Constructor for a cell that does not specify row or column number
	 */
	public EmptyForagingCell() {
		super();
		setColor(Color.CYAN);
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
		grid.addToNewGrid(this);
	}


	@Override
	public void setThreshold(double a, double b, double c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Cell changeType() {
		// TODO Auto-generated method stub
		return null;
	}


	

}
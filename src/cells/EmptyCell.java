package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Madhavi
 * This class describes the behavior of an EmptyCell which is a stand in for a vacancy in a grid.
 * This Cell doesn't actually do much; it just works as a signal for the fact that another cell type
 * could move into its location.
 */
public class EmptyCell extends Cell {

	public EmptyCell(int myRowNum, int myColNum) {
		super(myRowNum,myColNum);	
		setColor(Color.FLORALWHITE);
	}
	
	
	/* (non-Javadoc)
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		Cell newCell = new EmptyCell();
		return newCell;
	}

	public EmptyCell() {
		super();
		setColor(Color.FLORALWHITE);
	}
	
	public boolean isNeighbor(int otherRowNum, int otherColNum, int numRows, int numCols) {
		return super.isNeighbor8(otherRowNum, otherColNum);
	}
	
	/* (non-Javadoc)
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 */
	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		//do nothing
	}
	
}

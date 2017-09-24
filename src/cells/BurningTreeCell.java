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
	
	public BurningTreeCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.DARKORANGE);
	}
	
	/**
	 * @param root
	 * Replaces the current burning cell with an empty cell.
	 */
	public void burnOut(Grid newGrid) {
		Cell newCell = new EmptyCell(getRow(), getCol());
		changeCellType(newGrid, newCell);
	}

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		burnOut(grid);
	}

	@Override
	public boolean isNeighbor(int otherRowNum, int otherColNum, int numRows, int numCols) {
		return super.isNeighbor4(otherRowNum, otherColNum);
	}
}

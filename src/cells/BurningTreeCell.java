package cells;

import java.util.ArrayList;


import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 */
public class BurningTreeCell extends Cell {

	public BurningTreeCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.DARKORANGE);
	}
	
	@Override
	public boolean isNeighbor(int otherRowNum, int otherColNum) {
		return super.isNeighbor4(otherRowNum, otherColNum);
	}

	/**
	 * @param root
	 *            Replaces the current burning cell with an empty cell.
	 */
	private void burnOut(Grid newGrid) {
		Cell newCell = new EmptyLandCell(this.getRow(), this.getCol());
		changeCellType(newGrid, newCell);
	}

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		burnOut(grid);
	}
}

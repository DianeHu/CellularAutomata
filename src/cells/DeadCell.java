package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Diane Hu
 */
public class DeadCell extends Cell {

	public DeadCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.WHITE);
	}

	/**
	 * @param root
	 *            Checks the number of live neighbors. If the number is exactly
	 *            three, the dead cell is replaced with a live one, as if by
	 *            reproduction. Subsequently resets the number of live neighbors to
	 *            zero.
	 */
	public void resurrectCell(Grid newGrid) {
		Cell newCell = new LiveCell(getRow(), getCol());
		newGrid.addToNewGrid(newCell);
	}

	private boolean shouldResurrect() {
		if (checkNumLiveNeighbors() == 3) {
			return true;
		}
		return false;
	}

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		if(shouldResurrect()) {
			resurrectCell(grid);
		} else {
			grid.addToNewGrid(this);
		}
	}

	@Override
	public boolean isNeighbor(int otherRowNum, int otherColNum) {
		return super.isNeighbor8(otherRowNum, otherColNum);
	}
}

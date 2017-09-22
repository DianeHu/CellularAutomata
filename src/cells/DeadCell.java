package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 */
public class DeadCell extends Cell {

	public DeadCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.WHITE);
	}
	
	@Override
	public boolean isNeighbor(int otherRowNum, int otherColNum) {
		return super.isNeighbor8(otherRowNum, otherColNum);
	}

	/**
	 * @param root
	 *            Checks the number of live neighbors. If the number is exactly
	 *            three, the dead cell is replaced with a live one, as if by
	 *            reproduction. Subsequently resets the number of live neighbors to
	 *            zero.
	 */
	private void resurrectCell(Grid newGrid) {
		Cell newCell = new LiveCell(this.getRow(), this.getCol());
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
		if (shouldResurrect()) {
			resurrectCell(grid);
		} else {
			grid.addToNewGrid(this);
		}
	}
}

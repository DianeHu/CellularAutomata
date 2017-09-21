package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Diane Hu
 */
public class DeadCell extends Cell{
	
	private boolean resurrected = false;
	
	public DeadCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.WHITE);
	}
	
	/**
	 * @param root
	 * Checks the number of live neighbors. If the number is exactly three, the dead cell is replaced with a live one, as if by reproduction.
	 * Subsequently resets the number of live neighbors to zero.
	 */
	public void resurrectCell(Grid newGrid) {
		checkNumLiveNeighbors();
		if(getNumLiveNeighbors() == 3) {
			Cell newCell = new LiveCell(getRow(), getCol());
			changeCellType(newGrid, newCell);
			setNumLiveNeighbors(0);
			resurrected = true;
		}
	}

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		resurrectCell(grid);
		if(resurrected == false) {
			grid.addToNewGrid(this);
		}
	}
}

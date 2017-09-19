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
	
	private int rowNum;
	private int colNum;
	private Rectangle block;
	private ArrayList<Cell> neighbors;
	private int numLiveNeighbors = 0;
	private int width;
	private int height;
	private boolean resurrected = false;
	
	public DeadCell(int myRowNum, int myColNum, int width, int height) {
		super(myRowNum, myColNum, width, height);
		block.setFill(Color.WHITE);
	}
	
	/**
	 * @param root
	 * Checks the number of live neighbors. If the number is exactly three, the dead cell is replaced with a live one, as if by reproduction.
	 * Subsequently resets the number of live neighbors to zero.
	 */
	public void resurrectCell(Grid newGrid) {
		checkNumLiveNeighbors(this);
		if(numLiveNeighbors == 3) {
			Cell newCell = new LiveCell(this.rowNum, this.colNum, width, height);
			changeCellType(newGrid, newCell);
			numLiveNeighbors = 0;
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

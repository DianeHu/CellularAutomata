package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Diane Hu
 */
public class LiveCell extends Cell{
	
	private int rowNum;
	private int colNum;
	private Rectangle block;
	private ArrayList<Cell> neighbors;
	private int numLiveNeighbors = 0;
	private int width;
	private int height;
	
	public LiveCell(int myRowNum, int myColNum, int width, int height) {
		super(myRowNum, myColNum, width, height);
		block.setFill(Color.DARKCYAN);
	}

	/**
	 * @param root
	 * Checks the number of live neighbors. If number of live neighbors indicates over or underpopulation, then replace the current live cell with
	 * a dead one. Subsequently resets the number of live neighbors to zero.
	 */
	public void dieOut(Grid newGrid) {
		checkNumLiveNeighbors(this);
		if(numLiveNeighbors < 2 || numLiveNeighbors > 3) {
			Cell newCell = new DeadCell(this.rowNum, this.colNum, width, height);
			changeCellType(newGrid, newCell);
		}
		numLiveNeighbors = 0;
	}

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		dieOut(grid);
	}
}






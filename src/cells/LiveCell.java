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
	private boolean dead = false;
	
	public LiveCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.DARKCYAN);
	}

	/**
	 * @param root
	 * Checks the number of live neighbors. If number of live neighbors indicates over or underpopulation, then replace the current live cell with
	 * a dead one. Subsequently resets the number of live neighbors to zero.
	 */
	public void dieOut(Grid newGrid) {
		checkNumLiveNeighbors();
		if(getNumLiveNeighbors() < 2 || getNumLiveNeighbors() > 3) {
			Cell newCell = new DeadCell(getRow(), getCol());
			changeCellType(newGrid, newCell);
			setNumLiveNeighbors(0);
			dead = true;
		}
	}

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		dieOut(grid);
		if(dead == false) {
			grid.addToNewGrid(this);
		}
	}
}


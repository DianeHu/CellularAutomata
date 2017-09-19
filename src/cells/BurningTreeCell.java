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
	private int rowNum;
	private int colNum;
	private Rectangle block;
	private int width;
	private int height;
	
	public BurningTreeCell(int myRowNum, int myColNum, int width, int height) {
		super(myRowNum, myColNum, width, height);
		block.setFill(Color.DARKORANGE);
	}
	
	/**
	 * @param root
	 * Replaces the current burning cell with an empty cell.
	 */
	public void burnOut(Grid newGrid) {
		Cell newCell = new EmptyCell(this.rowNum, this.colNum, width, height);
		changeCellType(newGrid, newCell);
	}

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		burnOut(grid);
	}
}

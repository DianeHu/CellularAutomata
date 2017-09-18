package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LiveCell extends Cell{
	
	private int rowNum;
	private int colNum;
	private Rectangle block;
	private ArrayList<Cell> neighbors;
	private int numLiveNeighbors;
	private int width;
	private int height;
	
	public LiveCell(int myRowNum, int myColNum, int width, int height) {
		super(myRowNum, myColNum, width, height);
		block.setFill(Color.DARKCYAN);
	}

	public void dieOut(Group root) {
		checkNumLiveNeighbors();
		if(numLiveNeighbors < 2 || numLiveNeighbors > 3) {
			Cell newCell = new DeadCell(rowNum, colNum, width, height);
			changeCellType(root, this, newCell);
		}
	}
	
	private void checkNumLiveNeighbors() {
		for(Cell cell : neighbors) {
			if(cell instanceof LiveCell) {
				numLiveNeighbors++;
			}
		}
	}
}






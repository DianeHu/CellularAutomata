package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DeadCell extends Cell{
	
	private int rowNum;
	private int colNum;
	private Rectangle block;
	private ArrayList<Cell> neighbors;
	private int numLiveNeighbors = 0;
	private int width;
	private int height;
	
	public DeadCell(int myRowNum, int myColNum, int width, int height) {
		super(myRowNum, myColNum, width, height);
		block.setFill(Color.WHITE);
	}
	
	public void resurrectCell(Group root) {
		checkNumLiveNeighbors(this);
		if(numLiveNeighbors == 3) {
			Cell newCell = new LiveCell(rowNum, colNum, width, height);
			changeCellType(root, this, newCell);
		}
		numLiveNeighbors = 0;
	}
}






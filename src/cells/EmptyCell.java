package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.paint.Color;

public class EmptyCell extends Cell {

	public EmptyCell(int myRowNum, int myColNum) {
		super(myRowNum,myColNum);	
		setColor(Color.FLORALWHITE);
	}
	
	@Override
	public Cell copy() {
		Cell newCell = new EmptyCell();
		return newCell;
	}

	public EmptyCell() {
		super();
		setColor(Color.FLORALWHITE);
	}
	
	public boolean isNeighbor(int otherRowNum, int otherColNum, int numRows, int numCols) {
		return super.isNeighbor8(otherRowNum, otherColNum);
	}
	
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		//grid.addToNewGrid(this);
	}
	
}

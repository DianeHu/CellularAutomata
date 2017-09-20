package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EmptyCell extends Cell {

	
	public EmptyCell(int myRowNum, int myColNum) {
		super(myRowNum,myColNum);		
	}
	
	public boolean isNeighbor(int otherRowNum, int otherColNum) {
		return super.isNeighbor8(otherRowNum, otherColNum);
	}

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		// TODO Auto-generated method stub
		
	}
}

package cells;

import java.util.ArrayList;

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
	
	public void moveCell() {
		//do nothing
	}
	

}

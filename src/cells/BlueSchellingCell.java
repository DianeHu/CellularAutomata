package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BlueSchellingCell extends Cell{
	private int rowNum;
	private int colNum;
	private Rectangle block;
	private ArrayList<Cell> neighbors;
	
	public BlueSchellingCell(int myRowNum, int myColNum, int width, int height) {
		super(myRowNum, myColNum, width, height);
		block.setFill(Color.NAVY);
	}
	
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		double numBlue = (double)getNumBlueNeighbors();
		double numOrange = (double)getNumOrangeNeighbors();
		boolean satisfied = numBlue/(numOrange+numBlue) >= 0.3;
		if(!satisfied) {
			moveToRandomEmptySpace(emptySpots, grid);
		}
	}
}

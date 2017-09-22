package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BlueSchellingCell extends Cell{
	private double threshold;
	
	public BlueSchellingCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.NAVY);
	}
	
	public void setThreshold(double t) {
		threshold = t;
	}
	
	public boolean isNeighbor(int otherRowNum, int otherColNum) {
		return super.isNeighbor8(otherRowNum, otherColNum);
	}
	
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		double numBlue = (double)getNumBlueNeighbors();
		double numOrange = (double)getNumOrangeNeighbors();
		boolean satisfied = numBlue/(numOrange+numBlue) >= threshold;
		if(!satisfied) {
			if(!moveToRandomPlace(emptySpots, grid)) {
				grid.addToNewGrid(this);
			}
		}
		else {
			grid.addToNewGrid(this);
		}
	}



}

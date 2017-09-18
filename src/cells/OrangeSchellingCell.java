package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OrangeSchellingCell extends Cell{
	
	private int rowNum;
	private int colNum;
	private Rectangle block;
	private ArrayList<Cell> neighbors;
	private int threshold;
	private int width;
	private int height;
	
	public OrangeSchellingCell(int myRowNum, int myColNum, int width, int height) {
		super(myRowNum, myColNum, width, height);
		block.setFill(Color.DARKORANGE);
	}
	
	public int getRow() {
		return super.getRow();
	}
	
	public int getCol() {
		return super.getCol();
	}
	
	public void setThreshold(int t) {
		threshold = t;
	}
	
	public boolean isNeighbor(int otherRowNum, int otherColNum) {
		return super.isNeighbor8(otherRowNum, otherColNum);
	}
	
	public void drawCell(Group root) {
		super.drawCell(root);
	}
	
	public void setNeighbors(ArrayList<Cell> n) {
		super.setNeighbors(n);
	}
	
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		double numBlue = (double)getNumBlueNeighbors();
		double numOrange = (double)getNumOrangeNeighbors();
		boolean satisfied = numOrange/(numOrange+numBlue) >= threshold;
		if(!satisfied) {
			moveToRandomPlace(emptySpots, grid);
		}
	}
	
	protected boolean moveToRandomPlace(ArrayList<Cell> emptySpots, Grid grid) {
		return super.moveToRandomPlace(emptySpots, grid);
	}

	
	protected int getNumBlueNeighbors() {
		return super.getNumBlueNeighbors();
	}
	
	protected int getNumOrangeNeighbors() {
		return super.getNumOrangeNeighbors();
	}
	
}

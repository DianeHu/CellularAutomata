package cells;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EmptyCell extends Cell {
	private int rowNum;
	private int colNum;
	private Rectangle block;
	private ArrayList<Cell> neighbors;
	
	public EmptyCell(int myRowNum, int myColNum, int width, int height) {
		super(myRowNum, myColNum, width, height);
		block.setFill(Color.FLORALWHITE);
	}
	
	public int getRow() {
		return super.getRow();
	}
	
	public int getCol() {
		return super.getCol();
	}
	
	public boolean isSurroundingNeighbor(int otherRowNum, int otherColNum) {
		return super.isSurroundingNeighbor(otherRowNum, otherColNum);
	}
	
	public void drawCell(Group root) {
		super.drawCell(root);
	}
	
	public void setNeighbors(ArrayList<Cell> n) {
		super.setNeighbors(n);
	}
	
	public void moveCell() {
		//do nothing
	}
	

}

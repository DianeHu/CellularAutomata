package cells;

import java.util.ArrayList;

import java.util.Random;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public abstract class Cell {
	
	private int rowNum;
	private int colNum;
	private int myWidth;
	private int myHeight;
	private Rectangle myCell;
	private ArrayList<Cell> neighbors;
	
	public Cell(int myRowNum, int myColNum, int width, int height) {
		rowNum = myRowNum;
		colNum = myColNum;
		myWidth = width;
		myHeight = height;
		myCell = new Rectangle((rowNum-1)*width, (colNum-1)*height, width, height);
		neighbors = new ArrayList<Cell>();
	}
	
	public int getRow() {
		return rowNum;
	}
	
	public int getCol() {
		return colNum;
	}
	
	public boolean isSurroundingNeighbor(int otherRowNum, int otherColNum) {
		if(Math.abs(rowNum-otherRowNum)<=1 && Math.abs(colNum-otherColNum)<=1) {
			return true;
		}
		return false;
	}
		
	public void drawCell(Group root) {
		myCell.setX((rowNum-1)*myWidth);
		myCell.setY((colNum-1)*myHeight);
		root.getChildren().add(myCell);
	}
	
	public void killCell(Group root) {
		int previousRowNum = rowNum;
		int previousColNum = colNum;
		root.getChildren().remove(myCell);
		Cell newCell = createEmptyCell(previousRowNum, previousColNum);
		root.getChildren().add(newCell.getParent());
	}
	
	public Cell createEmptyCell(int rowNum, int colNum) {
		Cell newCell = new EmptyCell(rowNum, colNum, myWidth, myHeight);
		return newCell;
	}
	
	public void changeCellType(Group root, Cell previousCell, Cell newCell) {
		int previousRowNum = previousCell.getRow();
		int previousColNum = previousCell.getCol();
		root.getChildren().remove(previousCell.getParent());
		newCell.colNum = previousColNum;
		newCell.rowNum = previousRowNum;
		root.getChildren().add(newCell.getParent());
	}
	
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		//do nothing
	}
	
	public Node getParent() {
		return this.getParent();
	}
	
	public void setNeighbors(ArrayList<Cell> n) {
		neighbors = n;
	}
	
	protected int getNumBlueNeighbors() {
		int sum = 0;
		for(Cell c: neighbors) {
			if(c instanceof BlueSchellingCell) {
				sum++;
			}
		}
		return sum;
	}
	
	protected int getNumOrangeNeighbors() {
		int sum = 0;
		for(Cell c: neighbors) {
			if(c instanceof OrangeSchellingCell) {
				sum++;
			}
		}
		return sum;
	}
	
	protected void moveToRandomEmptySpace(ArrayList<Cell> emptySpots, Grid grid) {
		boolean moved = false;
		while(!moved) {
			int numEmptySpaces = emptySpots.size();
			Random rand = new Random(); 
			Cell testLoc = emptySpots.get(rand.nextInt(numEmptySpaces));
			if(grid.newGridContainsCellAt(testLoc.getRow(),testLoc.getCol())) {
				emptySpots.remove(testLoc);
			}
			else {
				rowNum = testLoc.getRow(); colNum = testLoc.getCol();
				grid.addToNewGrid(this);
				moved = true;
			}
		}
	}
	
}

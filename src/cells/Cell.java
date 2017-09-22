package cells;

import java.util.ArrayList;
import java.util.Random;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Cell {
	
	private int rowNum;
	private int colNum;
	private ArrayList<Cell> neighbors;
	private Color col;
	
	public Cell(int myRowNum, int myColNum) {
		rowNum = myRowNum;
		colNum = myColNum;
		neighbors = new ArrayList<Cell>();
	}
	
	
	/**
	 * Sets the color for a cell type
	 * @param c is the Color associated with the cell type
	 */
	protected void setColor(Color c) {
		col = c;
	}
	
	/**
	 * @return the color associated with the cell type
	 */
	public Color getColor() {
		return col;
	}
	
	//new
	public void setRow(int row) {
		rowNum = row;
	}
	//new
	public void setCol(int col) {
		colNum = col;
	}

	public int getRow() {
		return rowNum;
	}
	
	public int getCol() {
		return colNum;
	}
	
	//changed
	public boolean isNeighbor8(int otherRowNum, int otherColNum) {
		if(Math.abs(rowNum-otherRowNum)<=1 & Math.abs(colNum-otherColNum)<=1) {
			if(!(otherRowNum==rowNum && otherColNum==colNum)) {
				return true;
			}
		}
		return false;
	}
	

	public abstract boolean isNeighbor(int otherRowNum, int otherColNum);
	
	//changed
	public boolean isNeighbor4(int otherRowNum, int otherColNum) {
		if((Math.abs(rowNum-otherRowNum)==1 & colNum==otherColNum)
				| (Math.abs(colNum-otherColNum)==1 & rowNum==otherRowNum)) {
			return true;
		}
		return false;
	}
	
	public void killCell() {
	}
	
	public void createCell() {
	}
	
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		//do nothing
	}
	
	public void setNeighbors(ArrayList<Cell> n) {
		neighbors = n;
	}
	
	//new 
	protected ArrayList<Cell> getNeighbors(){
		return neighbors;
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
	
	protected boolean moveToRandomPlace(ArrayList<Cell> spots, Grid grid) {
		boolean moved = false;
		while(!moved) {
			int numEmptySpaces = spots.size();
			if(numEmptySpaces==0) {
				break;
			}
			Random rand = new Random(); 
			Cell testLoc = spots.get(rand.nextInt(numEmptySpaces));
			if(grid.newGridContainsCellAt(testLoc.getRow(),testLoc.getCol())) {
				spots.remove(testLoc);
			}
			else {
				rowNum = testLoc.getRow(); colNum = testLoc.getCol();
				grid.addToNewGrid(this);
				moved = true;
			}
		}
		return moved;
	}
	
	/**
	 * @return a list of a cell's empty neighbors
	 */
	protected ArrayList<Cell> getEmptyNeighbors(){
		ArrayList<Cell> emptyNeighbors = new ArrayList<Cell>();
		for(Cell c: neighbors) {
			if(c instanceof EmptyCell) {
				emptyNeighbors.add(c);
			}
		}
		return emptyNeighbors;
	}


	
}

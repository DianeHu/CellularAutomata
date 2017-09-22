package cells;

import java.util.ArrayList;

import java.util.Random;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Madhavi
 * @author Diane Hu
 */
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
	
	/**
	 * @return Returns column number of the cell
	 */
	public int getCol() {
		return colNum;
	}

	
	
	
	/**
	 * @param otherRowNum
	 * @param otherColNum
	 * @return Returns whether or not a cell at an indicated position is a neighbor of the current cell. Neighbor defined as any of the eight
	 * surrounding positions of a cell, i.e., including positions diagonal to the current one.
	 */
	public abstract boolean isNeighbor(int otherRowNum, int otherColNum);
	
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
	
	public boolean isNeighbor4(int otherRowNum, int otherColNum) {
		if((Math.abs(rowNum-otherRowNum)<=1 & colNum==otherColNum)
				| (Math.abs(colNum-otherColNum)<=1 & rowNum==otherRowNum)) {
			return true;
		}
		return false;
	}

	
	/**
	 * @param root
	 * @param previousCell
	 * @param newCell
	 * Changes the cell at a certain location to a new cell. Can be used to switch type of cell at any location.
	 */
	public void changeCellType(Grid newGrid, Cell newCell) {
		int previousRowNum = this.getRow();
		int previousColNum = this.getCol();
		newCell.colNum = previousColNum;
		newCell.rowNum = previousRowNum;
		newGrid.addToNewGrid(newCell);
	}
	
	/**
	 * @param emptySpots
	 * @param grid
	 * Abstract method that each subclass calls to move cell.
	 */
	public abstract void moveCell(ArrayList<Cell> emptySpots, Grid grid);
	
	/**
	 * @return Returns parent node of cell.
	 */
	public Node getParent() {
		return this.getParent();
	}
	
	/**
	 * @param n
	 * Sets neighbor list of cell to given list.
	 */
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
	
	
	/**
	 * @return Returns number of orange neighbors of a cell in the Segregation Schelling simulation.
	 */
	protected int getNumOrangeNeighbors() {
		int sum = 0;
		for(Cell c: neighbors) {
			if(c instanceof OrangeSchellingCell) {
				sum++;
			}
		}
		return sum;
	}
	
	protected int getNumBurningNeighbors() {
		int sum = 0;
		for(Cell c: neighbors) {
			if(c instanceof BurningTreeCell) {
				sum++;
			}
		}
		return sum;
	}
	
	/**
	 * @param emptySpots
	 * @param grid
	 * Moves cell to random empty location in grid.
	 */
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

    protected int checkNumLiveNeighbors() {
		int numLiveNeighbors = 0;
		for(Cell c : neighbors) {
			if(c instanceof LiveCell) {
				numLiveNeighbors++;
			}
		}
		return numLiveNeighbors;
	}
	
	protected ArrayList<Cell> getNeighbors(){
		return neighbors;
	}
	
}

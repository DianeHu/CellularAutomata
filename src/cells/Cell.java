package cells;

import java.util.ArrayList;

import java.util.Random;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 * @author Madhavi Rajiv
 * @author Diane Hu
 * This class outlines the functionality of a general Cell type.
 * The Cell needs to know how to move, interact with its neighbors, and
 * tell whether a cell at a certain location is its neighbor.
 */

public abstract class Cell {
	
	private int rowNum;
	private int colNum;
	private ArrayList<Cell> neighbors;
	private Color col;
	
	
	/**
	 * @param myRowNum
	 * @param myColNum
	 * The cell is initialized with its location. The constructor also initializes the list
	 * of neighbors.
	 */
	public Cell(int myRowNum, int myColNum) {
		rowNum = myRowNum;
		colNum = myColNum;
		neighbors = new ArrayList<Cell>();
	}
	
	/**
	 * This constructor initializes a cell without its location.
	 */
	public Cell() {
		neighbors = new ArrayList<Cell>();
	}
	
	/**
	 * @returns a cell copied in terms of parameters from the original cell
	 */
	public abstract Cell copy();
	
	/**
	 * Sets the color for a cell type
	 * @param c is the Color associated with the cell type
	 */
	protected void setColor(Color c) {
		col = c;
	}
	
	public Node getNode() {
		return this.getNode();
	}
	
	/**
	 * @return the color associated with the cell type
	 */
	public Color getColor() {
		return col;
	}
	
	
	/**
	 * @param row
	 * Sets the row location of a cell
	 */
	public void setRow(int row) {
		rowNum = row;
	}

	/**
	 * @param col
	 * Sets the column location of a cell
	 */
	public void setCol(int col) {
		colNum = col;
	}

	/**
	 * @return Returns the row location of a cell
	 */
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
	 * @return Returns whether or not a cell at an indicated position is a neighbor of the current cell. 
	 */
	public abstract boolean isNeighbor(int otherRowNum, int otherColNum, int numRows, int numCols);
	
	
	/**
	 * @param otherRowNum
	 * @param otherColNum
	 * @return Returns whether the cell at the given position is a neighbor for simulations
	 * where neighbors consist of all 8 surrounding adjacent cells.
	 */
	public boolean isNeighbor8(int otherRowNum, int otherColNum) {
		return (Math.abs(rowNum-otherRowNum)<=1 & Math.abs(colNum-otherColNum)<=1) 
				& !(otherRowNum==rowNum && otherColNum==colNum);
	}
	
	/**
	 * @param otherRowNum
	 * @param otherColNum
	 * @return Returns whether the cell at the given position is a neighbor for simulations
	 * where neighbors consist of neighbors in North, South, East, and West directions.
	 */
	public boolean isNeighbor4(int otherRowNum, int otherColNum) {
		return (Math.abs(rowNum-otherRowNum)==1 & colNum==otherColNum)
				| (Math.abs(colNum-otherColNum)==1 & rowNum==otherRowNum);
	}
	
	
	/**
	 * @param otherRowNum
	 * @param otherColNum
	 * @param numRows
	 * @param numCols
	 * @return Returns whether the cell at the given position is a neighbor for simulations
	 * where neighbors consist of neighbors in North, South, East, and West directions, or 
	 * wrapped neighbors in the same direction.
	 */
	public boolean isNeighborTorus(int otherRowNum, int otherColNum, int numRows, int numCols) {
		boolean horizontalWrapping = rowNum==otherRowNum & ((colNum==0 & otherColNum==numCols-1)|(colNum==numCols-1 & otherColNum==0));
		boolean verticalWrapping = colNum==otherColNum & ((rowNum==0 & otherRowNum==numRows-1)|(rowNum==numRows-1 & otherRowNum==0));
		return isNeighbor4(otherRowNum,otherColNum)|horizontalWrapping|verticalWrapping;
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
	 * Abstract method that each subclass calls to move cell and have cell interact with 
	 * neighbors. This method changes based on the cell subclass and the rules of the simulation type.
	 */
	public abstract void moveCell(ArrayList<Cell> emptySpots, Grid grid);
	
	/**
	 * @param n
	 * Sets neighbor list of cell to given list.
	 */
	public void setNeighbors(ArrayList<Cell> n) {
		neighbors = n;
	}
	
	/**
	 * @return the number of neighbors which are instances of BlueSchellingCell
	 */
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
	 * @return the number of neighbors which are instances of OrangeSchellingCell
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
	
	/**
	 * @return the number of neighbors which are instances of BurningTreeCell
	 */
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
	 * Moves cell to random empty location in grid; these empty locations
	 * are given in the list emptySpots.
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

    /**
     * @return the number of neighbors which are instances of LiveCell
     */
    protected int checkNumLiveNeighbors() {
		int numLiveNeighbors = 0;
		for(Cell c : neighbors) {
			if(c instanceof LiveCell) {
				numLiveNeighbors++;
			}
		}
		return numLiveNeighbors;
	}
	
    /**
     * @return the list of neighbors
     */
	protected ArrayList<Cell> getNeighbors(){
		return neighbors;
	}
	
}


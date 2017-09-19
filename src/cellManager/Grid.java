package cellManager;

import java.io.File;
import java.util.ArrayList;

import cells.Cell;
import cells.EmptyCell;
import javafx.scene.Group;

/**
 * @author Diane Hu
 * @author Madhavi
 */
public class Grid {
	
private Group root;
private Cell[][] currentGrid;
private Cell[][] newGrid;
private Cell[][] emptyGrid;
private File xml;
private int numRows;
private int numCols;
private int cellWidth;
private int cellHeight;

	public Grid(Group r, File f) {
		root = r;
		xml = f;
		
	}
	
	/**
	 * Initializes grid with values read from XML file.
	 */
	public void initialize() {
		XMLReader reader = new XMLReader(xml);
		numRows = reader.getNumRows();
		numCols = reader.getNumCols();
		createEmptyGrid();
		currentGrid = emptyGrid;
		newGrid = emptyGrid;
		// read from xml to create initial state
	}
	
	/**
	 * Creates a grid with only empty cells which can be used to initialize
	 *  newgrid and current grid
	 */	
	private void createEmptyGrid() {
		for(int i = 0; i<numRows; i++) {
			for(int j = 0; j<numCols; j++) {
				emptyGrid[i][j]=new EmptyCell(i,j,cellWidth,cellHeight);
			}
		}
	}
/**
	 * This methods sets the list of neighbors for each cell by checking
	 *  which of its adjacent cells are considered neighbors by the algorithm
	 */
	
	private void setNeighbors() {
		//TODO
		//go through each cell and inform its list of neighbors
		for(int i = 0; i<numRows; i++) {
			for(int j = 0; j<numCols; j++) {
				Cell c = currentGrid[i][j];
				setNeighborsForCell(c);
			}
		}		
	}
	
	private void setNeighborsForCell(Cell cell) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		int row = cell.getRow(); 
		int col = cell.getCol();
		//top
		if(row!=0 & cell.isNeighbor(row+1,col)) {
			neighbors.add(currentGrid[row-1][col]);
		}
		//bottom
		if(row!=(numRows-1) & cell.isNeighbor(row-1,col)) {
			neighbors.add(currentGrid[row+1][col]);
		}
		//left
		if(col!=0 & cell.isNeighbor(row,col-1)) {
			neighbors.add(currentGrid[row][col-1]);
		}
		//right
		if(col!=(numCols-1) & cell.isNeighbor(row,col+1)) {
			neighbors.add(currentGrid[row][col+1]);
		}
		//upper right
		if(col!=(numCols-1) & row!=0 & cell.isNeighbor(row+1,col+1)) {
			neighbors.add(currentGrid[row-1][col+1]);
		}
		//lower right
		if(row!=(numCols-1) & col!=(numCols-1) & cell.isNeighbor(row-1,col+1)) {
			neighbors.add(currentGrid[row+1][col+1]);
		}
		//upper left
		if(row!=0 & col!=0 & cell.isNeighbor(row+1,col-1)) {
			neighbors.add(currentGrid[row-1][col-1]);
		}
		//lower left
		if(row!=(numCols-1) & col!=0 & cell.isNeighbor(row-1,col-1)) {
			neighbors.add(currentGrid[row+1][col-1]);
		}
		cell.setNeighbors(neighbors);
	}
	
	public void createsNewGrid() {
		setNeighbors();
		ArrayList<Cell> emptyCells = getEmptyCells();
		for(int i = 0; i<numRows; i++) {
			for(int j = 0; j<numCols; j++) {
				Cell c = currentGrid[i][j];
				c.moveCell(emptyCells,this);
			}
		}
	}
	
	/**
	 * @return Returns empty cells of current grid.
	 */
	private ArrayList<Cell> getEmptyCells(){
		ArrayList<Cell> emptyCells = new ArrayList<Cell>();
		for(int i = 0; i<numRows; i++) {
			for(int j = 0; j<numCols; j++) {
				Cell c = currentGrid[i][j];
				if(c instanceof EmptyCell) {
					emptyCells.add(c);
				}
			}
		}
		return emptyCells;
	}
	
	public void update() {
		currentGrid = newGrid;
		newGrid = new Cell[numRows][numCols];
		for(int i = 0; i<numRows; i++) {
			for(int j = 0; j<numCols; j++) {
				Cell c = currentGrid[i][j];
				c.drawCell(root);
			}
		}
	}

	/**
	 * @param rownum
	 * @param colnum
	 * @return Tests if the new grid has a non-empty cell at a certain location, returns true/false.
	 */
	public boolean newGridContainsCellAt(int rownum, int colnum) {
		if(newGrid[rownum][colnum] instanceof EmptyCell) {
			return false;
		}
		return true;
	}
	
	/**
	 * @param rownum
	 * @param colnum
	 * @return Tests if the new grid has a SharkCell at a certain location, returns true/false.
	 */
	public boolean newGridContainsSharkAt(int rownum, int colnum) {
		if(newGrid[rownum][colnum] instanceof SharkCell) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * @param c
	 * Adds cell to new grid at cell's designated location.
	 */
	public void addToNewGrid(Cell c) {
		newGrid[c.getRow()][c.getCol()] = c;
	}
}


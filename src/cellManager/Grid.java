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
private File xml;
private int numRows;
private int numCols;

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
		currentGrid = new Cell[numRows][numCols];
		newGrid = new Cell[numRows][numCols];
		// read from xml to create initial state
	}
	
	
	/**
	 * Sets neighbor list of each cell in grid.
	 * TODO: how to determine index out of bounds.
	 */
	private void setNeighbors() {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				ArrayList<Cell> cellNeighborList = new ArrayList<Cell>();
				Cell below = currentGrid[i - 1][j];
				Cell above = currentGrid[i + 1][j];
				Cell left = currentGrid[i][j - 1];
				Cell right = currentGrid[i][j + 1];
				Cell upperRightDiagonal = currentGrid[i + 1][j + 1];
				Cell upperLeftDiagonal = currentGrid[i + 1][j - 1];
				Cell lowerRightDiagonal = currentGrid[i - 1][j + 1];
				Cell lowerLeftDiagonal = currentGrid[i - 1][j - 1];
				Cell current = currentGrid[i][j];
				cellNeighborList.add(below);
				cellNeighborList.add(above);
				cellNeighborList.add(left);
				cellNeighborList.add(right);
				cellNeighborList.add(upperRightDiagonal);
				cellNeighborList.add(upperLeftDiagonal);
				cellNeighborList.add(lowerRightDiagonal);
				cellNeighborList.add(lowerLeftDiagonal);
				current.setNeighbors(cellNeighborList);
			}
		}
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
	 * @param c
	 * Adds cell to new grid at cell's designated location.
	 */
	public void addToNewGrid(Cell c) {
		newGrid[c.getRow()][c.getCol()] = c;
	}
}


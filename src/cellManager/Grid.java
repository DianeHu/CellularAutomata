package cellManager;

import java.io.File;
import java.util.ArrayList;

import cells.Cell;
import cells.EmptyCell;
import javafx.scene.Group;

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
	
	public void initialize() {
		XMLReader reader = new XMLReader(xml);
		numRows = reader.getNumRows();
		numCols = reader.getNumCols();
		currentGrid = new Cell[numRows][numCols];
		newGrid = new Cell[numRows][numCols];
		// read from xml to create initial state
	}
	
	private void setNeighbors() {
		//go through each cell and inform its list of neighbors
		
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

	public boolean newGridContainsCellAt(int rownum, int colnum) {
		return false;
	}
	
	public void addToNewGrid(Cell c) {
		
	}
}

package cellManager;

import java.io.File;
import java.util.ArrayList;

import cells.BlueSchellingCell;
import cells.Cell;
import cells.EmptyCell;
import cells.OrangeSchellingCell;
import cells.SharkCell;
import javafx.scene.Group;

/**
 * @author Madhavi
 *
 */
/**
 * @author Madhavi
 *
 */
/**
 * @author Madhavi
 *
 */
/**
 * @author Madhavi
 *
 */
/**
 * @author Madhavi
 *
 */
public class Grid {
public static final int SIZE = 400;
private Group root;
private Cell[][] currentGrid;
private Cell[][] newGrid;
private Cell[][] emptyGrid;
private File xml;
private int numRows;
private int numCols;
private int cellWidth;
private int cellHeight;

	public Grid(Group r) {
		root = r;
		//xml = f;
		
	}
	
	public void initialize() {
		/*XMLReader reader = new XMLReader(xml);
		numRows = reader.getNumRows();
		numCols = reader.getNumCols();*/
		numRows = 5;
		numCols = 5;
		cellWidth = SIZE/numCols;
		cellHeight = SIZE/numRows;
		createEmptyGrid();
		currentGrid = emptyGrid;
		setInitialStates();
		newGrid = emptyGrid;
		// read from xml to create initial state
	}
	
	private void setInitialStates() {
		char[][] states ={{'b','b','o','o','o'},
				    		  {'o','b',' ',' ','b'},
				    		  {' ',' ','o','b','b'},
				    		  {'o','o','b','o',' '},
				    		  {'b',' ','o','b','o'}
		};
		for(int i = 0; i<numRows; i++) {
			for(int j = 0; i<numCols; j++) {
				if(states[i][j]=='o') {
					Cell c = new OrangeSchellingCell(i,j,cellWidth,cellHeight);
					currentGrid[i][j]= c;
					c.drawCell(root);
				}
				if(states[i][j]=='b') {
					Cell c = new BlueSchellingCell(i,j,cellWidth,cellHeight);
					currentGrid[i][j]= c;
					c.drawCell(root);
				}
				if(states[i][j]==' ') {
					Cell c = new EmptyCell(i,j,cellWidth,cellHeight);
					currentGrid[i][j]= c;
					c.drawCell(root);
				}
			}			
		}
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
		for(int i = -1; i<2; i++) {
			for(int j = -1; j<2; j++) {
				if(row+i<numRows & row+i>-1 & col+j<numCols & col+j>-1) {
					if(cell.isNeighbor(row+i,col+j)) {
						neighbors.add(currentGrid[row+i][col+j]);
					}
				}
			}
		}		
		cell.setNeighbors(neighbors);
		/*//top
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
		cell.setNeighbors(neighbors);*/
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
		newGrid = emptyGrid;
		for(int i = 0; i<numRows; i++) {
			for(int j = 0; j<numCols; j++) {
				Cell c = currentGrid[i][j];
				c.drawCell(root);
			}
		}
		
	}

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
	
	public void addToNewGrid(Cell c) {
		newGrid[c.getRow()][c.getCol()] = c;
	}
	
	//new
	/**
	 * @param c is a cell which is removed from the newGrid
	 * This method removes a cell from the newGrid assuming that the cell has been added
	 * to its location in the newGrid already. The calling of the method in the FishCell
	 * class takes care of this assumption.
	 */
	public void removeFromNewGrid(Cell c) {
		newGrid[c.getRow()][c.getCol()] = new EmptyCell(c.getRow(),c.getCol(),cellWidth,cellHeight);
	}
}

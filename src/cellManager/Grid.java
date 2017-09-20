package cellManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import cells.BlueSchellingCell;
import cells.Cell;
import cells.EmptyCell;
import cells.OrangeSchellingCell;
//import cells.SharkCell;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Grid {
public static final int SIZE = 400;
private Group root;
private Cell[][] currentGrid;
private Cell[][] newGrid;
private Cell[][] emptyGrid;
private Rectangle[][] blocks;
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
		
		currentGrid = new Cell[numRows][numCols];
		newGrid = new Cell[numRows][numCols];
		emptyGrid = new Cell[numRows][numCols];
		blocks = new Rectangle[numRows][numCols];
		
		createEmptyGrid();
		currentGrid = emptyGrid.clone();
		setRectangles();
		setInitialStates();
		newGrid = emptyGrid.clone();

		// read from xml to create initial state
	}
	
	private void setRectangles() {
		
		for(int i = 0; i<numRows; i++) {
			for(int j = 0; j<numCols; j++) {
				Rectangle r = new Rectangle(j*cellWidth,i*cellHeight,cellWidth,cellHeight);
				r.setStroke(Color.DARKGREY);
				root.getChildren().add(r);
				blocks[i][j]=r;
			}
		}
		
	}
	
	private void setInitialStates() {
/*		char[][] states ={{'b','b','o','o','o'},
				    		  {'o','b',' ',' ','b'},
				    		  {' ',' ','o','b','b'},
				    		  {'o','o','b','o',' '},
				    		  {'b',' ','o','b','o'}};*/
		
		char[][] states ={{'o','o','o','o','o'},
	    		  			{'o','b','o','o','o'},
	    		  			{'o','o','o','o','o'},
	    		  			{'o','o',' ','o',' '},
	    		  			{' ',' ','o',' ','o'}};
		int numBlue = 0;
		for(int i = 0; i<numRows; i++) {
			for(int j = 0; j<numCols; j++) {
				if(states[i][j]=='o') {
					OrangeSchellingCell c = new OrangeSchellingCell(i,j);
					currentGrid[i][j]= c;
					c.setThreshold(.3);
					blocks[i][j].setFill(c.getColor());
					
				}
				if(states[i][j]=='b') {
					BlueSchellingCell c = new BlueSchellingCell(i,j);
					currentGrid[i][j]= c;
					c.setThreshold(.3);
					blocks[i][j].setFill(c.getColor());
					numBlue++;
				}
				if(states[i][j]==' ') {
					Cell c = new EmptyCell(i,j);
					currentGrid[i][j]= c;
					blocks[i][j].setFill(c.getColor());
				}
				
			}
			
		}
		System.out.println("init blue" + numBlue);
	}
	
	/**
	 * Creates a grid with only empty cells which can be used to initialize
	 *  newgrid and current grid
	 */	
	private void createEmptyGrid() {
		for(int i = 0; i<numRows; i++) {
			for(int j = 0; j<numCols; j++) {
				emptyGrid[i][j]=new EmptyCell(i,j);
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
					if(cell.isNeighbor8(row+i,col+j)) {
						neighbors.add(currentGrid[row+i][col+j]);
					}
				}
			}
		}		
		cell.setNeighbors(neighbors);
	}
	
	public void createsNewGrid() {
		setNeighbors();
		ArrayList<Cell> emptyCells = getEmptyCells();
		ArrayList<Cell> visitedCells = new ArrayList<Cell>();
		for(int i = 0; i<currentGrid.length; i++) {
			for(int j = 0; j<currentGrid[i].length; j++) {
				Cell c = currentGrid[i][j];
				if(c instanceof BlueSchellingCell) {
					c.moveCell(emptyCells,this);
					System.out.println(c.getRow() + " " + c.getCol());
					visitedCells.add(c);
				}				
			}
		}
		System.out.println(visitedCells.size());
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
		System.out.println(emptyCells.size());
		return emptyCells;
	}
	
	public void update(Group r) {
		currentGrid = newGrid.clone();
		newGrid = emptyGrid.clone();
		//root.getChildren().clear();
		//removeCellsFromScreen();
		for(int i = 0; i<numRows; i++) {
			for(int j = 0; j<numCols; j++) {
				Cell c = currentGrid[j][i];
				//c.drawCell(r);
				blocks[i][j].setFill(c.getColor());
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
	/*public boolean newGridContainsSharkAt(int rownum, int colnum) {
		if(newGrid[rownum][colnum] instanceof SharkCell) {
			return false;
		}
		return true;
	}*/
	
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
		newGrid[c.getRow()][c.getCol()] = new EmptyCell(c.getRow(),c.getCol());
	}
}

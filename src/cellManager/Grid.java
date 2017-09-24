package cellManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import XMLClasses.GridConfiguration;
import cells.BlueSchellingCell;
import cells.BurningTreeCell;
import cells.Cell;
import cells.DeadCell;
import cells.EmptyCell;
import cells.EmptyLandCell;
import cells.FishCell;
import cells.LiveCell;
import cells.OrangeSchellingCell;
import cells.SharkCell;
import cells.TreeCell;
import cellsociety_team08.Simulation;
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
	private GridConfiguration gridConfig;
	private int numRows;
	private int numCols;
	private int cellWidth;
	private int cellHeight;
	private String simulationType = "Wator";
	private Map<Character, Cell> segregation = new HashMap<>();
	private Map<Character, Cell> gameOfLife = new HashMap<>();
	private Map<Character, Cell> spreadingWildfire = new HashMap<>();
	private Map<Character, Cell> waTor = new HashMap<>();
	private Map<Character, Cell> simMap = new HashMap<>();

	public Grid(Group r, GridConfiguration g) {
		root = r;
		gridConfig = g;
	}

	private void createMaps() {
		Cell bCell = new BlueSchellingCell();
		bCell.setThreshold(gridConfig.getSegregationThreshold());
		
		Cell oCell = new OrangeSchellingCell();
		oCell.setThreshold(gridConfig.getSegregationThreshold());
		
		Cell tCell = new TreeCell();
		tCell.setThreshold(gridConfig.getProbCatch());
		
		Cell bTCell = new BurningTreeCell();
		
		Cell eCell = new EmptyCell();
		
		Cell eLCell = new EmptyLandCell();
		eLCell.setThreshold(gridConfig.getProbGrow());
		
		Cell lCell = new LiveCell();
		
		Cell dCell = new DeadCell();
		
		FishCell fCell = new FishCell();
		fCell.setBreedTurns(gridConfig.getFishBreedTurns());
		
		SharkCell sCell = new SharkCell();
		sCell.setBreedTurns(gridConfig.getSharkBreedTurns());
		sCell.setStarveTurns(gridConfig.getSharkStarveTurns());
		

		segregation.put('b', bCell);
		segregation.put('o', oCell);
		segregation.put('e', eCell);

		gameOfLife.put('l', lCell);
		gameOfLife.put('d', dCell);

		spreadingWildfire.put('t', tCell);
		spreadingWildfire.put('b', bTCell);
		spreadingWildfire.put('e', eLCell);
		
		waTor.put('f',fCell);
		waTor.put('s', sCell);
	}

	private void setCurrSimulationMap() {
		switch (simulationType) {
		case ("Segregation"):
			simMap = segregation;
			break;
		case ("SpreadingWildfire"):
			simMap = spreadingWildfire;
			break;
		case ("Wator"):
			simMap = waTor;
			break;
		case ("GameOfLife"):
			simMap = gameOfLife;
			break;
		}
	}

	public void initialize() {
		createMaps();
		setCurrSimulationMap();
		numRows = gridConfig.getNumRows();
		numCols = gridConfig.getNumCols();
		cellWidth = SIZE / numCols;
		cellHeight = SIZE / numRows;

		currentGrid = new Cell[numRows][numCols];
		newGrid = new Cell[numRows][numCols];
		blocks = new Rectangle[numRows][numCols];

		empty(currentGrid);
		empty(newGrid);
		setRectangles();
		setInitialStates();

		// read from xml to create initial state
	}

	private void empty(Cell[][] grid) {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				grid[i][j] = new EmptyCell(i, j);
			}
		}
	}

	/**
	 * Creates a grid with only empty cells which can be used to initialize newgrid
	 * and current grid
	 */
	private void createEmptyGrid() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				emptyGrid[i][j] = new EmptyCell(i, j);
			}
		}
	}

	private void setRectangles() {

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Rectangle r = new Rectangle(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
				r.setStroke(Color.DARKGREY);
				root.getChildren().add(r);
				blocks[i][j] = r;
			}
		}

	}

	private void setInitialStates() {

		char[][] states = gridConfig.getCellConfiguration();
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Cell c = simMap.get(states[i][j]).copy();
				c.setRow(i);
				c.setCol(j);
				currentGrid[i][j] = c;
				blocks[i][j].setFill(c.getColor());
			}
		}

	}

	/**
	 * This methods sets the list of neighbors for each cell by checking which of
	 * its adjacent cells are considered neighbors by the algorithm
	 */

	private void setNeighbors() {
		// TODO
		// go through each cell and inform its list of neighbors
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
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
					if(cell.isNeighbor(row+i,col+j,numRows,numCols)) {
						neighbors.add(currentGrid[row+i][col+j]);
					}
				}
			}
		}
		if(cell.getRow()==0) {
			if(cell.isNeighbor(numRows-1, cell.getCol(), numRows, numCols)) {
				neighbors.add(currentGrid[numRows-1][cell.getCol()]);
			}
		}
		if(cell.getRow()==numRows-1) {
			if(cell.isNeighbor(0, cell.getCol(), numRows, numCols)) {
				neighbors.add(currentGrid[0][cell.getCol()]);
			}
		}
		if(cell.getCol()==0) {
			if(cell.isNeighbor(cell.getRow(), numCols-1, numRows, numCols)) {
				neighbors.add(currentGrid[cell.getRow()][numCols-1]);
			}
		}
		if(cell.getCol()==numCols-1) {
			if(cell.isNeighbor(cell.getRow(), 0, numRows, numCols)) {
				neighbors.add(currentGrid[cell.getRow()][0]);
			}
		}
		cell.setNeighbors(neighbors);
	}

	public void createsNewGrid() {
		setNeighbors();
		for (int i = 0; i < currentGrid.length; i++) {
			for (int j = 0; j < currentGrid[i].length; j++) {
				ArrayList<Cell> empty = getEmptyCells();
				Cell c = currentGrid[i][j];
				c.moveCell(empty, this);
			}
		}
	}

	private ArrayList<Cell> getEmptyCells() {
		ArrayList<Cell> emptyCells = new ArrayList<Cell>();
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Cell c = currentGrid[i][j];
				if (c instanceof EmptyCell) {
					emptyCells.add(c);
				}
			}
		}
		return emptyCells;
	}

	public void update(Group r) {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Cell c = newGrid[i][j];
				blocks[i][j].setFill(c.getColor());
				currentGrid[i][j] = newGrid[i][j];
			}
		}
		empty(newGrid);
	}

	public boolean newGridContainsCellAt(int rownum, int colnum) {
		return !(newGrid[rownum][colnum] instanceof EmptyCell);
	}

	/**
	 * @param rownum
	 * @param colnum
	 * @return Tests if the new grid has a SharkCell at a certain location, returns
	 *         true/false.
	 */
	public boolean newGridContainsSharkAt(int rownum, int colnum) {
		return newGrid[rownum][colnum] instanceof SharkCell;
	}

	/**
	 * @param rownum
	 * @param colnum
	 * @return Tests if the new grid has a SharkCell at a certain location, returns
	 *         true/false.
	 */
	/*
	 * public boolean newGridContainsSharkAt(int rownum, int colnum) {
	 * if(newGrid[rownum][colnum] instanceof SharkCell) { return false; } return
	 * true; }
	 */

	public void addToNewGrid(Cell c) {
		newGrid[c.getRow()][c.getCol()] = c;
	}

	// new
	/**
	 * @param c
	 *            is a cell which is removed from the newGrid This method removes a
	 *            cell from the newGrid assuming that the cell has been added to its
	 *            location in the newGrid already. The calling of the method in the
	 *            FishCell class takes care of this assumption.
	 */
	public void removeFromNewGrid(Cell c) {
		newGrid[c.getRow()][c.getCol()] = new EmptyCell(c.getRow(), c.getCol());
	}

	/**
	 * @param row
	 * @param col
	 * @return The cell at a certain location in newGrid
	 */
	public Cell getCellInNewGridAt(int row, int col) {
		return newGrid[row][col];
	}

}

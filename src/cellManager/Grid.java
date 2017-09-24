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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Madhavi Rajiv
 * @author Diane Hu This class creates manages the different cells in the
 *         simulation, and organizes their updates in parallel. It takes in a
 *         GridConfiguration object to get information from the XML file about
 *         the nature of the simulation, and takes in a Group root to edit the
 *         scene based on the states of the cells.
 */
public class Grid {

	public static final int SIZE = 400;
	private Group root;
	private Cell[][] currentGrid;
	private Cell[][] newGrid;
	private Rectangle[][] blocks;
	private GridConfiguration gridConfig;
	private int numRows;
	private int numCols;
	private int cellWidth;
	private int cellHeight;
	private String simulationType;
	private Map<Character, Cell> segregation = new HashMap<>();
	private Map<Character, Cell> gameOfLife = new HashMap<>();
	private Map<Character, Cell> spreadingWildfire = new HashMap<>();
	private Map<Character, Cell> waTor = new HashMap<>();
	private Map<Character, Cell> simMap = new HashMap<>();
	private GridPane pane = new GridPane();

	/**
	 * @param r
	 *            - This is the root used to edit scenes
	 * @param g
	 *            - This is the GridConfiguration used to get information from the
	 *            XML file
	 */
	public Grid(Group r, GridConfiguration g) {
		root = r;
		gridConfig = g;
	}

	/**
	 * Maps different cell types to different characters based on the simulation
	 * type. The names of each map refer to the simulation types. Each cell mapped
	 * to a character has its parameters set as well so that these parameters can be
	 * passed on when the cell is copied later.
	 */
	private void createMaps() {
		BlueSchellingCell bCell = new BlueSchellingCell();
		bCell.setThreshold(gridConfig.getSegregationThreshold());

		OrangeSchellingCell oCell = new OrangeSchellingCell();
		oCell.setThreshold(gridConfig.getSegregationThreshold());

		TreeCell tCell = new TreeCell();
		tCell.setThreshold(gridConfig.getProbCatch());

		BurningTreeCell bTCell = new BurningTreeCell();

		EmptyCell eCell = new EmptyCell();

		EmptyLandCell eLCell = new EmptyLandCell();
		eLCell.setThreshold(gridConfig.getProbGrow());

		LiveCell lCell = new LiveCell();

		DeadCell dCell = new DeadCell();

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

		waTor.put('f', fCell);
		waTor.put('s', sCell);
		waTor.put('e', eCell);
	}

	/**
	 * Switches which map is being used to map characters to cell types based off of
	 * the simulation string read from the XML file
	 */
	private void setCurrSimulationMap() {
		simulationType = gridConfig.getSimulationType();
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

	/**
	 * @return Returns the gridpane initialized. Creates the mappings of characters
	 *         to cells, sets the appropriate maps, and sets up the 2D arrays
	 *         backing the gridpane.
	 */
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

	/**
	 * @param grid-
	 *            a 2D matrix of cells Takes in a 2D matrix of cells and sets them
	 *            all to EmptyCells, effectively zeroing out the matrix
	 */
	private void empty(Cell[][] grid) {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				grid[i][j] = new EmptyCell(i, j);
			}
		}
	}

	/**
	 * Creates the a grid of blank rectangles which represents the cells.
	 */
	private void setRectangles() {

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Rectangle r = new Rectangle(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
				r.setStroke(Color.DARKGREY);
				blocks[i][j] = r;
			}
		}

	}

	/**
	 * Colors in the grid of rectangles based off of which cell type exists at each
	 * location in the initial state read from the XML file through GridConfig.
	 * Updates the gridpane with the rectangle colors, and displays the gridpane.
	 */
	private void setInitialStates() {

		char[][] states = gridConfig.getCellConfiguration();

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Cell c = simMap.get(states[i][j]).copy();
				c.setRow(i);
				c.setCol(j);
				currentGrid[i][j] = c;
				blocks[i][j].setFill(c.getColor());
				GridPane.setConstraints(blocks[i][j], j, i);
				pane.getChildren().add(blocks[i][j]);
			}
		}

		root.getChildren().add(pane);
	}

	/**
	 * This methods sets the list of neighbors for each cell by checking which of
	 * its adjacent cells are considered neighbors by the algorithm used for its
	 * respective cell type.
	 */

	private void setNeighbors() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Cell c = currentGrid[i][j];
				setNeighborsForCell(c);
			}
		}
	}

	/**
	 * @param cell
	 *            is an individual Cell type This method sets a list of neighbors
	 *            for a single cell.
	 * 
	 */
	private void setNeighborsForCell(Cell cell) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		getAdjacentNeighbors(cell, neighbors);
		getWrappedNeighbors(cell, neighbors);
		cell.setNeighbors(neighbors);
	}

	/**
	 * @param cell
	 * @param neighbors
	 *            This method takes in a cell and edits its list of neighbors to
	 *            include adjacent neighbors which are neighbors according to the
	 *            cell type's isNeighbor() algorithm.
	 */
	private void getAdjacentNeighbors(Cell cell, ArrayList<Cell> neighbors) {
		int row = cell.getRow();
		int col = cell.getCol();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (row + i < numRows & row + i > -1 & col + j < numCols & col + j > -1) {
					if (cell.isNeighbor(row + i, col + j, numRows, numCols)) {
						neighbors.add(currentGrid[row + i][col + j]);
					}
				}
			}
		}
	}

	/**
	 * @param cell
	 * @param neighbors
	 *            This method takes in a cell and edits its list of neighbors to
	 *            include wrapped neighbors which are neighbors according to the
	 *            cell type's isNeighbor() algorithm.
	 */
	private void getWrappedNeighbors(Cell cell, ArrayList<Cell> neighbors) {
		if (cell.getRow() == 0) {
			if (cell.isNeighbor(numRows - 1, cell.getCol(), numRows, numCols)) {
				neighbors.add(currentGrid[numRows - 1][cell.getCol()]);
			}
		}
		if (cell.getRow() == numRows - 1) {
			if (cell.isNeighbor(0, cell.getCol(), numRows, numCols)) {
				neighbors.add(currentGrid[0][cell.getCol()]);
			}
		}
		if (cell.getCol() == 0) {
			if (cell.isNeighbor(cell.getRow(), numCols - 1, numRows, numCols)) {
				neighbors.add(currentGrid[cell.getRow()][numCols - 1]);
			}
		}
		if (cell.getCol() == numCols - 1) {
			if (cell.isNeighbor(cell.getRow(), 0, numRows, numCols)) {
				neighbors.add(currentGrid[cell.getRow()][0]);
			}
		}
	}

	/**
	 * This method goes through each cell and has it perform its interactions in
	 * order to create the new grid, a 2D matrix of cells which describes the next
	 * state.
	 */
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

	/**
	 * @return a list of all the empty cells in the current configuration of cells
	 */
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

	/**
	 * Moves on to the next state of the simulation by setting the current state
	 * equal to the new state which has been created earlier, and then emptying out
	 * the new state so that it can be built up again.
	 */
	public void update() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Cell c = newGrid[i][j];
				blocks[i][j].setFill(c.getColor());
				currentGrid[i][j] = newGrid[i][j];
			}
		}
		empty(newGrid);
	}

	/**
	 * @param rownum
	 * @param colnum
	 * @return a boolean describing whether a non-empty cell exists at the location
	 *         specified by rownum and colnum
	 */
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
	 * @param c
	 *            Takes in a cell c and adds it to newGrid, which describes the
	 *            newState
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

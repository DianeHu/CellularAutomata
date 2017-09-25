package cellManager;

import java.util.ArrayList;
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
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * @author Madhavi Rajiv
 * @author Diane Hu This class creates manages the different cells in the
 *         simulation, and organizes their updates in parallel. It takes in a
 *         GridConfiguration object to get information from the XML file about
 *         the nature of the simulation, and takes in a Group root to edit the
 *         scene based on the states of the cells.
 */
public abstract class Grid {

	public static final int SIZE = 400;
	private Group root;
	private Cell[][] currentGrid;
	private Cell[][] newGrid;
	private Shape[][] blocks;
	private GridConfiguration gridConfig;
	private int numRows;
	private int numCols;
	private double cellWidth;
	private double cellHeight;
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
	 * @return the root used to add shapes to the scene
	 */
	protected Group getRoot() {
		return root;
	}
	
	/**
	 * @param shapes- takes in a list of shapes to set blocks to
	 */
	protected void setBlocks(Shape[][] shapes) {
		blocks = new Shape[numRows][numCols];
		for(int i = 0; i<numRows; i++) {
			for(int j = 0; j<numCols; j++) {
				blocks[i][j] = shapes[i][j];
			}
		}
	}
	
	/**
	 * @return the GridConfiguration used to get information from the XML file
	 */
	protected GridConfiguration getGridConfig() {
		return gridConfig;
	}

	/**
	 * Maps different cell types to different characters based on the simulation
	 * type. The names of each map refer to the simulation types. Each cell mapped
	 * to a character has its parameters set as well so that these parameters can be
	 * passed on when the cell is copied later.
	 */
	protected void createMaps() {
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
	protected void setCurrSimulationMap() {
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
	 * @return The map mapping each character to a cell type
	 */
	protected Map<Character,Cell> getSimMap() {
		return simMap;
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
		empty(currentGrid);
		empty(newGrid);
		setShapes();
		setInitialStates();
	}
	
	/**
	 * @return the number of rows in the grid
	 */
	protected int getNumRows() {
		return numRows;
	}
	
	
	/**
	 * @param n is used to set the number of rows
	 */
	protected void setNumRows(int n) {
		numRows = n;
	}
	
	/**
	 * @return the number of cols in the grid
	 */
	protected int getNumCols() {
		return numCols;
	}
	
	
	/**
	 * @param n is used to set the number of rows
	 */
	protected void setNumCols(int n) {
		numCols = n;
	}
	
	/**
	 * @return the calculated width of the cell
	 */
	protected double getCellWidth() {
		return cellWidth;
	}
	
	/**
	 * @param width is the calculated cellWidth
	 */
	protected void setCellWidth(double width) {
		cellWidth = width;
	}
	
	/**
	 * @return the calculated height of the cell
	 */
	protected double getCellHeight() {
		return cellHeight;
	}
	
	/**
	 * @param height is the calculated cellHeight
	 */
	protected void setCellHeight(double height) {
		cellWidth = height;
	}
	
	/**
	 * @return the grid of cells describing the current state
	 */
	protected Cell[][] getCurrentGrid() {
		return currentGrid;
	}
	
	/**
	 * @return the grid of cells describing the next state
	 */
	protected Cell[][] getNewGrid() {
		return newGrid;
	}
	
	/**
	 * This methods sets the list of neighbors for each cell by checking which of
	 * its adjacent cells are considered neighbors by the algorithm used for its
	 * respective cell type.
	 */
	protected abstract void setNeighbors();



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
	 * Creates the a grid of blank shapes which represents the cells.
	 */
	protected abstract void setShapes();

	/**
	 * Colors in the grid of rectangles based off of which cell type exists at each
	 * location in the initial state read from the XML file through GridConfig.
	 * Updates the gridpane with the rectangle colors, and displays the gridpane.
	 */
	protected void setInitialStates() {

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

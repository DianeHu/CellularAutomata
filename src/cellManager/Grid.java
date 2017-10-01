package cellManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import XMLClasses.GridConfiguration;
import XMLClasses.SpreadingWildfireConfiguration;
import XMLClasses.ForagingAntsConfiguration;
import cells.AntCell;
import cells.AntGroupCell;
import cells.BlueRPSCell;
import cells.BlueSchellingCell;
import cells.BurningTreeCell;
import cells.Cell;
import cells.DeadCell;
import cells.EmptyCell;
import cells.EmptyLandCell;
import cells.FishCell;
import cells.GreenRPSCell;
import cells.LiveCell;
import cells.OrangeSchellingCell;
import cells.RedRPSCell;
import cells.SharkCell;
import cells.TreeCell;
import cells.WhiteRPSCell;
import gridPatches.ForagingLand;
import javafx.scene.Group;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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

	public static final int SIZE = 350;
	private Group root;
	private Cell[][] currentGrid;
	private Cell[][] newGrid;
	private Shape[][] blocks;
	private GridConfiguration gridConfig;
	private int numRows;
	private int numCols;
	private double gridCellCount;
	private double cellWidth;
	private double cellHeight;
	private String simulationType= "ForagingAnts";
	private Map<Character, Cell> segregation = new HashMap<>();
	private Map<Character, Cell> gameOfLife = new HashMap<>();
	private Map<Character, Cell> spreadingWildfire = new HashMap<>();
	private Map<Character, Cell> waTor = new HashMap<>();
	private Map<Character, Cell> foragingAnts = new HashMap<>();
	private Map<Character, Cell> simMap = new HashMap<>();
	private Map<String, String> segConfigStringMap = new HashMap<>();
	private Map<String, String> gOLConfigStringMap = new HashMap<>();
	private Map<String, String> fireConfigStringMap = new HashMap<>();
	private Map<String, String> watorConfigStringMap = new HashMap<>();
	private Map<String, String> antConfigStringMap = new HashMap<>();
	private Map<Character, Double> probabilityMap = new HashMap<>();
	private Map<String, String> simulationConfigStringMap = new HashMap<>();
	private Pane pane = new GridPane();
	private Map<String, Integer> countMap = new HashMap<>();
	private ScrollPane gridScroll;
	private final ScrollBar sc = new ScrollBar();
	private ForagingLand land;
	public boolean currentlyPaused;
	private int[] homeLoc = new int[2];
	private int[] foodLoc = new int[2];
	private Boolean maxNeighbors;
	private Boolean isStroke;
	private Boolean isToroidal;

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
	
	public void setPaused(Boolean b) {
		currentlyPaused = b;
	}
	
	public void setMaxNeighbors(Boolean b) {
		maxNeighbors = b;
	}
	
	public void setIsStroke(Boolean b) {
		isStroke = b;
	}
	
	public void setIsToroidal(Boolean b) {
		isToroidal = b;
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

	public double percentTree() {
		return countMap.get("cells.TreeCell") / gridCellCount;
	}

	public double percentBurning() {
		return countMap.get("cells.BurningTreeCell") / gridCellCount;
	}

	public double percentLand() {
		return countMap.get("cells.EmptyLandCell") / gridCellCount;
	}

	public double percentSharks() {
		return countMap.get("cells.SharkCell") / gridCellCount;
	}

	public double percentFish() {
		return countMap.get("cells.FishCell") / gridCellCount;
	}

	public double percentBS() {
		return countMap.get("cells.LiveCell") / gridCellCount;
	}

	public double percentOS() {
		return countMap.get("cells.OrangeSchellingCell") / gridCellCount;
	}

	public double percentEmpty() {
		return countMap.get("cells.EmptyCell") / gridCellCount;
	}

	public double percentLive() {
		return countMap.get("cells.LiveCell") / gridCellCount;
	}

	public double percentDead() {
		return countMap.get("cells.DeadCell") / gridCellCount;
	}

	/**
	 * Maps different cell types to different characters based on the simulation
	 * type. The names of each map refer to the simulation types. Each cell mapped
	 * to a character has its parameters set as well so that these parameters can be
	 * passed on when the cell is copied later.
	 */
	protected void createMaps() {
		BlueSchellingCell bCell = new BlueSchellingCell();
		OrangeSchellingCell oCell = new OrangeSchellingCell();
		EmptyCell eCell = new EmptyCell();
		TreeCell tCell = new TreeCell();
		BurningTreeCell bTCell = new BurningTreeCell();
		EmptyLandCell eLCell = new EmptyLandCell();
		
		/*LiveCell lCell = new LiveCell();
		DeadCell dCell = new DeadCell();*/
		GreenRPSCell gcell = new GreenRPSCell();
		RedRPSCell rcell = new RedRPSCell();
		BlueRPSCell blcell = new BlueRPSCell();
		WhiteRPSCell wcell = new WhiteRPSCell();
		
		FishCell fCell = new FishCell();
		SharkCell sCell = new SharkCell();	
		
		AntGroupCell aCell = new AntGroupCell();

		segregation.put('b', bCell);
		segregation.put('o', oCell);
		segregation.put('e', eCell);

  		/*gameOfLife.put('l', lCell);
  		gameOfLife.put('d', dCell);*/
		gameOfLife.put('g', gcell);
		gameOfLife.put('r', rcell);
		gameOfLife.put('b', blcell);
		gameOfLife.put('w', wcell);
		
		spreadingWildfire.put('t', tCell);
		spreadingWildfire.put('b', bTCell);
		spreadingWildfire.put('e', eLCell);

		waTor.put('f', fCell);
		waTor.put('s', sCell);
		waTor.put('e', eCell);

		foragingAnts.put('a',aCell);
		foragingAnts.put('e',eCell);
		initExportMap();
		
		initCountMap();
	}

	private void initExportMap() {
		segConfigStringMap.put("cells.BlueSchellingCell", "b");
		segConfigStringMap.put("cells.OrangeSchellingCell", "o");
		segConfigStringMap.put("cells.EmptyCell", "e");
		gOLConfigStringMap.put("cells.LiveCell", "l");
		gOLConfigStringMap.put("cells.DeadCell", "d");
		fireConfigStringMap.put("cells.TreeCell", "t");
		fireConfigStringMap.put("cells.BurningTreeCell", "b");
		fireConfigStringMap.put("cells.EmptyLandCell", "e");
		watorConfigStringMap.put("cells.FishCell", "f");
		watorConfigStringMap.put("cells.SharkCell", "s");
		watorConfigStringMap.put("cells.EmptyCell", "e");
		antConfigStringMap.put("cells.AntGroupCell", "a");
		antConfigStringMap.put("cells.EmptyCell", "e");
	}

	private void initCountMap() {
		countMap.put("cells.BlueSchellingCell", 0);
		countMap.put("cells.OrangeSchellingCell", 0);
		countMap.put("cells.LiveCell", 0);
		countMap.put("cells.DeadCell", 0);
		countMap.put("cells.TreeCell", 0);
		countMap.put("cells.BurningTreeCell", 0);
		countMap.put("cells.EmptyLandCell", 0);
		countMap.put("cells.EmptyCell", 0);
		countMap.put("cells.SharkCell", 0);
		countMap.put("cells.FishCell", 0);
	}
	
	public String getSimType() {
		return simulationType;
	}
	
	public void setSimType(String s) {
		simulationType = s;
	}

	/**
	 * Switches which map is being used to map characters to cell types based off of
	 * the simulation string read from the XML file
	 */
	protected void setCurrSimulationMap() {
		switch (simulationType) {
		case ("Segregation"):
			simMap = segregation;
			simulationConfigStringMap = segConfigStringMap;
			break;
		case ("SpreadingWildfire"):
			simMap = spreadingWildfire;
			simulationConfigStringMap = fireConfigStringMap;
			break;
		case ("Wator"):
			simMap = waTor;
			simulationConfigStringMap = watorConfigStringMap;
			break;
		case ("GameOfLife"):
			simMap = gameOfLife;
			simulationConfigStringMap = gOLConfigStringMap;
			break;
		case ("ForagingAnts"):
			simMap = foragingAnts;
			simulationConfigStringMap = antConfigStringMap;
			homeLoc[0]= ((ForagingAntsConfiguration) gridConfig).getHomeLocX();
			homeLoc[1] = ((ForagingAntsConfiguration) gridConfig).getHomeLocY();
			
			foodLoc[0]= ((ForagingAntsConfiguration) gridConfig).getFoodLocX();
			foodLoc[1] = ((ForagingAntsConfiguration) gridConfig).getFoodLocY();
			land = new ForagingLand(getNumRows(),getNumCols(),
					homeLoc, foodLoc);
			break;
		}
	}
	
	/**
	 * @return The map mapping each character to a cell type
	 */
	protected Map<Character,Cell> getSimMap() {
		return simMap;
	}

	
	private void updateCounts(Cell c) {
		//countMap.put(c.getClass().getName(), countMap.get(c.getClass().getName()) + 1);
	}

	/**
	 * @return Returns the gridpane initialized. Creates the mappings of characters
	 *         to cells, sets the appropriate maps, and sets up the 2D arrays
	 *         backing the gridpane.
	 */
	public void initialize() {
		numRows = gridConfig.getNumRows();
		numCols = gridConfig.getNumCols();
		/*numRows = 10;
		numCols = 10;*/
		gridCellCount = numRows * numCols;
		createMaps();
		setCurrSimulationMap();
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
	public int getNumRows() {
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
	public int getNumCols() {
		return numCols;
	}
	
	public String getGridConfig() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				String currType = simulationConfigStringMap.get(
						currentGrid[i][j].getClass().getName());
				s.append(currType);
			}
		}
		return s.toString();
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
	protected void setNeighbors() {
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumCols(); j++) {
				Cell c = getCurrentGrid()[i][j];
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
	protected abstract void setNeighborsForCell(Cell cell);

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
				initializeCell(i, j, c);
			}
		}
		root.getChildren().add(pane);
	}
	
	private void initializeCell(int i, int j, Cell c) {
		c.setRow(i);
		c.setCol(j);
		c.setLand(land);
		currentGrid[i][j] = c;
		blocks[i][j].setFill(c.getColor());
		Color stroke;
		if(land!=null) {
			stroke = land.strokeColorAtLocation(i, j);
			blocks[i][j].setStrokeWidth(3.0);
		}
		else{
			stroke = Color.DARKGREY;
		}
		blocks[i][j].setStroke(stroke);
		GridPane.setConstraints(blocks[i][j], j, i);
		pane.getChildren().add(blocks[i][j]);
	}


	/**
	 * This method goes through each cell and has it perform its interactions in
	 * order to create the new grid, a 2D matrix of cells which describes the next
	 * state.
	 */
	public void createsNewGrid(double threshold1, double threshold2, double threshold3) {
		setNeighbors();
		for (int i = 0; i < currentGrid.length; i++) {
			for (int j = 0; j < currentGrid[i].length; j++) {
				ArrayList<Cell> empty = getEmptyCells();
				Cell c = currentGrid[i][j];
				//c.setThreshold(threshold1, threshold2, threshold3);
				c.setThreshold(.3, 0, 0);
				updateCounts(c);
				c.moveCell(empty, this);
				if(land!=null) {
					land.evaporate();
				}
			}
		}
	}
	
	public void createPausedGrid(double threshold1, double threshold2, double threshold3) {
		for(int i = 0; i < currentGrid.length; i++) {
			for(int j = 0; j < currentGrid[i].length; j++) {
				Cell c = currentGrid[i][j];
				blocks[i][j].setOnMouseClicked(e->changeState(c));
				c.setThreshold(threshold1, threshold2, threshold3);
				newGrid[i][j] = c;
				updateCounts(c);
			}
		}
	}
	
	private void changeState(Cell c) {
		currentGrid[c.getRow()][c.getCol()] = (c.changeType());
		addToNewGrid(c.changeType());
		System.out.println("changed state");
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
		for(Entry<String, Integer> entry : countMap.entrySet()) {
			countMap.put(entry.getKey(), 0);
		}
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Cell c = newGrid[i][j];
				c.setRow(i);c.setCol(j);
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
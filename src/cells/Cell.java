package cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cellManager.Grid;
import gridPatches.ForagingLand;
import javafx.scene.paint.Color;

/**
 * @author Madhavi
 * @author Diane Hu This class outlines the functionality of a general Cell
 *         type. The Cell needs to know how to move, interact with its
 *         neighbors, and tell whether a cell at a specific location is its
 *         neighbor. Since there is no generic cell, the class is abstract. 
 */

public abstract class Cell {

	private int rowNum;
	private int colNum;
	private List<Cell> neighbors;
	private Color col;
	private ForagingLand land;
	private int level;

	/**
	 * @param myRowNum
	 * @param myColNum
	 *            The cell is initialized with its location. The constructor also
	 *            initializes the list of neighbors, and also takes in  ForagingLand
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
	 * 
	 * @param c
	 *            is the Color associated with the cell type
	 */
	protected void setColor(Color c) {
		col = c;
	}
	
	public abstract void setThreshold(double a, double b, double c);
	
	public abstract Cell changeType();

	/**
	 * @return the color associated with the cell type
	 */
	public Color getColor() {
		return col;
	}

	/**
	 * @param row
	 *            Sets the row location of a cell
	 */
	public void setRow(int row) {
		rowNum = row;
	}
	
	/**
	 * @param l
	 * Set the ForagingLand for Foraging Ant simulation
	 */
	public void setLand(ForagingLand l) {
		land = l;
	}

	/**
	 * @param col
	 *            Sets the column location of a cell
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
	 * @param emptySpots
	 * @param grid
	 *            Abstract method that each subclass calls to move cell and have
	 *            cell interact with neighbors. This method changes based on the
	 *            cell subclass and the rules of the simulation type.
	 */
	public abstract void moveCell(List<Cell> emptySpots, Grid grid);

	/**
	 * @param n
	 *            Sets neighbor list of cell to given list.
	 */
	public void setNeighbors(List<Cell> n) {
		neighbors = n;
	}
	
	/**
	 * @param cell
	 * @return the number of neighbors a cell has of the type of the 
	 * cell passed in through the parameter
	 */
	protected int getNumNeighborsOfType(Cell cell) {
		int sum = 0;
		for (Cell c : neighbors) {
			if (c.getClass().getName()==cell.getClass().getName()) {
				sum++;
			}
		}
		return sum;
	}

	/**
	 * @param emptySpots
	 * @param grid
	 *            Moves cell to random empty location in grid; these empty locations
	 *            are given in the list emptySpots.
	 */
	protected boolean moveToRandomPlace(List<Cell> spots, Grid grid) {
		boolean moved = false;
		while (!moved) {
			int numEmptySpaces = spots.size();
			if (numEmptySpaces == 0) {
				break;
			}
			Random rand = new Random();
			Cell testLoc = spots.get(rand.nextInt(numEmptySpaces));
			if (grid.newGridContainsCellAt(testLoc.getRow(), testLoc.getCol())) {
				spots.remove(testLoc);
			} else {
				setRow(testLoc.getRow());
				setCol(testLoc.getCol());
				grid.addToNewGrid(this);
				moved = true;
			}
		}
		return moved;
	}

	/**
	 * @return a list of a cell's empty neighbors
	 */
	protected List<Cell> getEmptyNeighbors() {
		List<Cell> emptyNeighbors = new ArrayList<Cell>();
		for (Cell c : neighbors) {
			if (c instanceof EmptyCell) {
				emptyNeighbors.add(c);
			}
		}
		return emptyNeighbors;
	}

	/**
	 * @return the list of neighbors
	 */
	protected List<Cell> getNeighbors() {
		return neighbors;
	}
	
	/**
	 * @param c
	 * @param grid
	 * Replaces the cell's grid location with a new cell c
	 */
	protected void createNewCellOfType(Cell c, Grid grid) {
		c.setRow(getRow());
		c.setCol(getCol());
		grid.addToNewGrid(c);	
	}
	
	/**
	 * @param emptySpots
	 * @param grid
	 * @param satisfied
	 * Takes in a list of empty spots, a grid, and a boolean that determines whether
	 * the cell is satisfied in its current position. This method determines how a 
	 * segregation cell moves based on whether its satistied and whether there are 
	 * empty locations left in the grid.
	 */
	protected void segregationMove(List<Cell> emptySpots, Grid grid, boolean satisfied) {
		if (!satisfied) {
			if (!moveToRandomPlace(emptySpots, grid)) {
				grid.addToNewGrid(this);
			}
		} else {
			grid.addToNewGrid(this);
		}
	}
	
	/**
	 * @return Returns the ForagingLand (needed for Foraging Ants) associated with the cell
	 */
	protected ForagingLand getLand() {
		return land;
	}
	
	/**
	 * @return Returns a cell chosen randomly from a cell's list of neighbors
	 */
	protected Cell chooseRandomNeighbor() {
		int numNeigh = neighbors.size();
		Random rand = new Random();
		return(neighbors.get(rand.nextInt(numNeigh)));		
	}
	
	/**
	 * @param n
	 * Sets a cell's level (needed for Rock Paper Scissors) to n
	 */
	protected void setLevel(int n) {
		level = n;
	}
	
	/**
	 * @return Returns the cell's level (needed for Rock Paper Scissors);
	 */
	protected int getLevel() {
		return level;
	}

}

package cells;

import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Madhavi Rajiv
 * 
 *         This class describes the way a cell of fish type in Wator interacts
 *         with its surroundings.
 */
public class FishCell extends Cell {
	private static final Color FISH_COLOR = Color.PALEGREEN;
	private static double breedTurns;
	// This variable was set to be static because the variable needs to be the same
	// for all instances of FishCell, and passing the variable as new fish were 
	// created turned out to be too error prone to keep the value constant 
	// among all fish at each time step.
	private int numTurns;
	private boolean eaten;
	
	public FishCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(FISH_COLOR);
	}

	public FishCell() {
		super();
		setColor(FISH_COLOR);
	}

	@Override
	public FishCell copy() {
		FishCell newCell = new FishCell();
		newCell.setBreedTurns(breedTurns);
		return newCell;
	}

	/**
	 * @param n
	 *            Sets the number of turns before the fish breeds as n
	 */
	public void setBreedTurns(double n) {
		breedTurns = n;
	}

	/**
	 * @param emptySpots
	 * @param grid
	 *            This method allows the fish to reproduce into an empty spot in the
	 *            list of empty spots. The Grid object parameter is used to update
	 *            the newGrid.
	 */
	private void breed(List<Cell> emptySpots, Grid grid) {
		FishCell newfish = new FishCell();
		newfish.setRow(getRow()); newfish.setCol(getCol());
		if (moveToRandomPlace(emptySpots, grid)) {
			numTurns = -1;
			grid.addToNewGrid(newfish);
		}

	}

	/**
	 * This method is called by sharks to eat a fish. It sets a boolean eaten to
	 * true so that in the future the fish won't add itself to the newGrid. It also
	 * checks to see if the newGrid contains itself by checking if the cell in its
	 * location is a fish type. If this is true, the fish removes itself form the
	 * newGrid.
	 * 
	 * @param grid
	 */
	public void die(Grid grid) {
		eaten = true;
		if (grid.getCellInNewGridAt(getRow(), getCol()) instanceof FishCell) {
			grid.removeFromNewGrid(this);
		}
	}
	
	@Override
	public Cell changeType() {
		SharkCell newCell = new SharkCell(this.getRow(), this.getCol());
		return newCell;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 */
	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		List<Cell> emptyNeighbors = getEmptyNeighbors();
		if (numTurns >= breedTurns) {
			breed(emptyNeighbors, grid);
		}
		if (!eaten && numTurns != -1) {
			if (!moveToRandomPlace(emptyNeighbors, grid)) {
				grid.addToNewGrid(this);
			}
		}
		numTurns++;
	}

	@Override
	public void setThreshold(double bTurns, double unused1, double unused2) {
		breedTurns = bTurns;
	}
}
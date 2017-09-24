package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Madhavi Rajiv
 * 
 *         This class describes the way a cell of fish type in Wator interacts
 *         with its surroundings.
 */
public class FishCell extends Cell {
	private static int breedTurns;
	private int numTurns;
	private boolean eaten;

	public FishCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.PALEGREEN);
	}

	public FishCell() {
		super();
		setColor(Color.PALEGREEN);
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
	public void setBreedTurns(int n) {
		breedTurns = n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#isNeighbor(int, int, int, int)
	 */
	@Override
	public boolean isNeighbor(int otherRowNum, int otherColNum, int numRows, int numCols) {
		return super.isNeighborTorus(otherRowNum, otherColNum, numRows, numCols);
	}

	/**
	 * @param emptySpots
	 * @param grid
	 *            This method allows the fish to reproduce into an empty spot in the
	 *            list of empty spots. The Grid object parameter is used to update
	 *            the newGrid.
	 */
	private void breed(ArrayList<Cell> emptySpots, Grid grid) {
		FishCell newfish = new FishCell(getRow(), getCol());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 */
	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		ArrayList<Cell> emptyNeighbors = getEmptyNeighbors();
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
}
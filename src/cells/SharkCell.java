package cells;

import java.util.ArrayList;
import java.util.Random;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Madhavi Rajiv
 * 
 *         This class describes the way a cell of shark type in Wator interacts
 *         with its surroundings.
 */
public class SharkCell extends Cell {
	private static int breedTurns;
	private static int starveTurns;
	private int numBreedTurns;
	private int numStarveTurns;

	public SharkCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.SLATEGREY);
	}

	public SharkCell() {
		super();
		setColor(Color.SLATEGREY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#copy()
	 */
	@Override
	public SharkCell copy() {
		SharkCell newCell = new SharkCell();
		newCell.setBreedTurns(breedTurns);
		newCell.setStarveTurns(starveTurns);
		return newCell;
	}

	/**
	 * @param n
	 *            This sets the number of turns a shark needs to take before it can
	 *            breed to n
	 */
	public void setBreedTurns(int n) {
		breedTurns = n;
	}

	/**
	 * @param n
	 *            This sets the number of turns a shark takes without eating before
	 *            it starves to n
	 */
	public void setStarveTurns(int n) {
		starveTurns = n;
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
	 *            This method allows the shark to reproduce into an empty spot in
	 *            the list of empty spots. The Grid object parameter is used to
	 *            update the newGrid.
	 */
	private void breed(ArrayList<Cell> emptySpots, Grid grid) {
		SharkCell newshark = new SharkCell(getRow(), getCol());
		if (moveToRandomPlace(emptySpots, grid)) {
			numBreedTurns = -1;
			grid.addToNewGrid(newshark);
		}
	}

	/**
	 * @param fish
	 * @param grid
	 * @return This method goes through a list of fish, fish, and randomly chooses
	 *         one of them to eat. grid is used to check for collisions by seeing if
	 *         another shark has already eaten the fish.
	 */
	public boolean eatFish(ArrayList<FishCell> fish, Grid grid) {
		boolean moved = false;
		while (!moved) {
			int numFish = fish.size();
			if (numFish == 0) {
				break;
			}
			Random rand = new Random();
			FishCell testFish = fish.get(rand.nextInt(numFish));
			if (grid.newGridContainsSharkAt(testFish.getRow(), testFish.getCol())) {
				fish.remove(testFish);
			} else {
				setRow(testFish.getRow());
				setCol(testFish.getCol());
				testFish.die(grid);
				grid.addToNewGrid(this);
				moved = true;
			}
		}
		return moved;
	}

	/**
	 * @return a list of fish included in the neighbors
	 */
	private ArrayList<FishCell> getNeighboringFish() {
		ArrayList<FishCell> neighborfish = new ArrayList<FishCell>();
		for (Cell c : getNeighbors()) {
			if (c instanceof FishCell) {
				FishCell f = (FishCell) c;
				neighborfish.add(f);
			}
		}
		return neighborfish;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cells.Cell#moveCell(java.util.ArrayList, cellManager.Grid)
	 */
	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		if (numStarveTurns < starveTurns) {
			ArrayList<Cell> emptyNeighbors = getEmptyNeighbors();
			ArrayList<FishCell> neighborfish = getNeighboringFish();

			if (eatFish(neighborfish, grid)) {
				numStarveTurns = 0;
			} else {
				if (numBreedTurns >= breedTurns) {
					breed(emptyNeighbors, grid);
				}
				if (numBreedTurns != -1) {
					moveToRandomPlace(emptyNeighbors, grid);
				}
				numStarveTurns++;
			}

			numBreedTurns++;
		}
	}
}
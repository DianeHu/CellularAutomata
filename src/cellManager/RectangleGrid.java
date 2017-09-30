package cellManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import XMLClasses.GridConfiguration;
import XMLClasses.SpreadingWildfireConfiguration;
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
import java.util.List;

import XMLClasses.GridConfiguration;
import cells.Cell;
import javafx.scene.Group;
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
public class RectangleGrid extends Grid {

	public static final int SIZE = 400;
	private Rectangle[][] blocks;
	private boolean toroidal = false;
	private boolean maxNeighbors = true;

	/**
	 * @param r
	 *            - This is the root used to edit scenes
	 * @param g
	 *            - This is the GridConfiguration used to get information from the
	 *            XML file
	 */
	public RectangleGrid(Group r, GridConfiguration g) {
		super(r, (GridConfiguration) g);
	}

	/**
	 * Creates the a grid of blank rectangles which represents the cells.
	 */
	protected void setShapes() {
		blocks = new Rectangle[getNumRows()][getNumCols()];
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumCols(); j++) {
				Rectangle r = new Rectangle(j * getCellWidth(), i * getCellHeight(), getCellWidth(), getCellHeight());
				r.setStroke(Color.DARKGREY);
				blocks[i][j] = r;
			}
		}
		setBlocks(blocks);
	}

	private double countBSCell() {
		double count = 0;
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumCols(); j++) {
				Cell c = getCurrentGrid()[i][j];
				if (c instanceof BlueSchellingCell) {
					count++;
				}
			}
		}
		return count;
	}

	public double percentBS() {
		return countBSCell() / (getNumRows() * getNumCols());
	}

	public double percentOS() {
		return 1 - percentBS();
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
	protected void setNeighborsForCell(Cell cell) {
		List<Cell> neighbors = new ArrayList<Cell>();
		getAdjacentNeighbors(cell, neighbors);
		if (toroidal) {
			getWrappedNeighbors(cell, neighbors);
		}
		cell.setNeighbors(neighbors);
	}

	/**
	 * @param cell
	 * @param neighbors
	 *            This method takes in a cell and edits its list of neighbors to
	 *            include adjacent neighbors which are neighbors according to the
	 *            cell type's isNeighbor() algorithm.
	 */
	private void getAdjacentNeighbors(Cell cell, List<Cell> neighbors) {
		int row = cell.getRow();
		int col = cell.getCol();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (row + i < getNumRows() & row + i > -1 & (col + j) < getNumCols() & (col + j) > -1) {
					if (isNeighborAdjacent(cell.getRow(), cell.getCol(), row + i, col + j)) {
						neighbors.add(getCurrentGrid()[row + i][col + j]);
					}
				}
			}
		}
	}

	private boolean isNeighborAdjacent(int currentRow, int currentCol, int otherRow, int otherCol) {
		if (maxNeighbors) {
			return (Math.abs(currentRow - otherRow) <= 1 && Math.abs(currentCol - otherCol) <= 1)
					& !(otherRow == currentRow && otherCol == currentCol);
		} else {
			return (Math.abs(currentRow - otherRow) == 1 & currentCol == otherCol)
					| (Math.abs(currentCol - otherCol) == 1 & currentRow == otherRow);
		}
	}

	/**
	 * @param cell
	 * @param neighbors
	 *            This method takes in a cell and edits its list of neighbors to
	 *            include wrapped neighbors which are neighbors according to the
	 *            cell type's isNeighbor() algorithm.
	 */
	private void getWrappedNeighbors(Cell cell, List<Cell> neighbors) {
		verticalWrapping(cell, neighbors);
		horizontalWrapping(cell, neighbors);
	}

	protected void horizontalWrapping(Cell cell, List<Cell> neighbors) {
		if (cell.getCol() == 0) {

			neighbors.add(getCurrentGrid()[cell.getRow()][getNumCols() - 1]);
			if (maxNeighbors) {
				if (cell.getRow() != 0) {
					neighbors.add(getCurrentGrid()[cell.getRow() - 1][getNumCols() - 1]);
				}
				if (cell.getRow() != getNumRows() - 1) {
					neighbors.add(getCurrentGrid()[cell.getRow() + 1][getNumCols() - 1]);
				}
			}
		}
		if (cell.getCol() == getNumCols() - 1) {
			neighbors.add(getCurrentGrid()[cell.getRow()][0]);
			if (maxNeighbors) {
				if (cell.getRow() != 0) {
					neighbors.add(getCurrentGrid()[cell.getRow() - 1][0]);
				}
				if (cell.getRow() != getNumRows() - 1) {
					neighbors.add(getCurrentGrid()[cell.getRow() + 1][0]);
				}
			}
		}
	}

	protected void verticalWrapping(Cell cell, List<Cell> neighbors) {
		if (cell.getRow() == 0) {
			neighbors.add(getCurrentGrid()[getNumRows() - 1][cell.getCol()]);
			if (maxNeighbors) {
				if (cell.getCol() != 0) {
					neighbors.add(getCurrentGrid()[getNumRows() - 1][cell.getCol() - 1]);
				}
				if (cell.getCol() != getNumCols() - 1) {
					neighbors.add(getCurrentGrid()[getNumRows() - 1][cell.getCol() + 1]);
				}
			}
		}
		if (cell.getRow() == getNumRows() - 1) {
			neighbors.add(getCurrentGrid()[0][cell.getCol()]);
			if (maxNeighbors) {
				if (cell.getCol() != 0) {
					neighbors.add(getCurrentGrid()[0][cell.getCol() - 1]);
				}
				if (cell.getCol() != getNumCols() - 1) {
					neighbors.add(getCurrentGrid()[0][cell.getCol() + 1]);
				}
			}
		}
	}

}

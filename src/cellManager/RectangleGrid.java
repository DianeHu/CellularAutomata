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
 * This class is a subclass of Grid which creates rectangles as cell shapes
 * and gets neighbors based on the shape of the rectangles.
 */
public class RectangleGrid extends Grid {

	public static final int SIZE = 400;
	private Rectangle[][] blocks;

	/**
	 * @param r
	 *            - This is the root used to edit scenes
	 * @param inputConfiguration
	 *            - This is the GridConfiguration used to get information from the
	 *            XML file
	 */
	public RectangleGrid(Group r, Object inputConfiguration) {
		super(r, (GridConfiguration) inputConfiguration);
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


	/**
	 * @param currentRow
	 * @param currentCol
	 * @param otherRow
	 * @param otherCol
	 * @return Takes in a Cell's location and a potential neighbor's location to 
	 * determine whether the potential neighbor cell is adjacent 
	 */
	@Override
	protected boolean isNeighborAdjacent(int currentRow, int currentCol, int otherRow, int otherCol) {
		if (getMaxNeighbors()) {
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
	@Override
	protected void getWrappedNeighbors(Cell cell, List<Cell> neighbors) {
		verticalWrapping(cell, neighbors);
		horizontalWrapping(cell, neighbors);
	}

	/**
	 * @param cell
	 * @param neighbors
	 * Updates a cell's list of neighbors to include neighbors from horizontal wrapping
	 */
	private void horizontalWrapping(Cell cell, List<Cell> neighbors) {
		if (cell.getCol() == 0) {

			neighbors.add(getCurrentGrid()[cell.getRow()][getNumCols() - 1]);
			if (getMaxNeighbors()) {
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
			if (getMaxNeighbors()) {
				if (cell.getRow() != 0) {
					neighbors.add(getCurrentGrid()[cell.getRow() - 1][0]);
				}
				if (cell.getRow() != getNumRows() - 1) {
					neighbors.add(getCurrentGrid()[cell.getRow() + 1][0]);
				}
			}
		}
	}

	/**
	 * @param cell
	 * @param neighbors
	 * Updates a cell's list of neighbors to include neighbors from vertical wrapping
	 */
	private void verticalWrapping(Cell cell, List<Cell> neighbors) {
		if (cell.getRow() == 0) {
			neighbors.add(getCurrentGrid()[getNumRows() - 1][cell.getCol()]);
			if (getMaxNeighbors()) {
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
			if (getMaxNeighbors()) {
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

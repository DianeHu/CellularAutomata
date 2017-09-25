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
public class RectangleGrid extends Grid{

	public static final int SIZE = 400;
	private Rectangle[][] blocks;

	/**
	 * @param r
	 *            - This is the root used to edit scenes
	 * @param g
	 *            - This is the GridConfiguration used to get information from the
	 *            XML file
	 */
	public RectangleGrid(Group r, GridConfiguration g) {
		super(r,g);
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
				if (row + i < getNumRows() & row + i > -1 & col + j < getNumCols() & col + j > -1) {
					if (cell.isNeighbor(row + i, col + j, getNumRows(), getNumCols())) {
						neighbors.add(getCurrentGrid()[row + i][col + j]);
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
			if (cell.isNeighbor(getNumRows() - 1, cell.getCol(), getNumRows(), getNumCols())) {
				neighbors.add(getCurrentGrid()[getNumRows() - 1][cell.getCol()]);
			}
		}
		if (cell.getRow() == getNumRows() - 1) {
			if (cell.isNeighbor(0, cell.getCol(), getNumRows(), getNumCols())) {
				neighbors.add(getCurrentGrid()[0][cell.getCol()]);
			}
		}
		if (cell.getCol() == 0) {
			if (cell.isNeighbor(cell.getRow(), getNumCols() - 1, getNumRows(), getNumCols())) {
				neighbors.add(getCurrentGrid()[cell.getRow()][getNumCols() - 1]);
			}
		}
		if (cell.getCol() == getNumCols() - 1) {
			if (cell.isNeighbor(cell.getRow(), 0, getNumRows(), getNumCols())) {
				neighbors.add(getCurrentGrid()[cell.getRow()][0]);
			}
		}
	}

}

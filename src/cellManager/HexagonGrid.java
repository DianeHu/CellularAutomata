package cellManager;

import java.util.ArrayList;
import java.util.List;

import XMLClasses.GridConfiguration;
import cells.Cell;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * @author Madhavi Rajiv
 * @author Diane Hu This class creates manages the different cells in the
 *         simulation, and organizes their updates in parallel. It takes in a
 *         GridConfiguration object to get information from the XML file about
 *         the nature of the simulation, and takes in a Group root to edit the
 *         scene based on the states of the cells.
 */
public class HexagonGrid extends Grid{

	public static final int SIZE = 400;
	private Polygon[][] blocks;
	private boolean toroidal = false;
	private boolean maxNeighbors = true;


	/**
	 * @param r
	 *            - This is the root used to edit scenes
	 * @param g
	 *            - This is the GridConfiguration used to get information from the
	 *            XML file
	 */
	public HexagonGrid(Group r, GridConfiguration g) {
		super(r,g);
	}

	/**
	 * Creates the a grid of blank rectangles which represents the cells.
	 */
	protected void setShapes() {
		blocks = new Polygon[getNumRows()][getNumCols()];
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumCols(); j++) {
				Polygon h = new Polygon();
				h.setStroke(Color.DARKGREY);
				createHexagon(h,i,j);
				blocks[i][j] = h;
			}
		}
		setBlocks(blocks);
	}
	
	private void createHexagon(Polygon hexagon, int row, int col) {
		double side = (double)getCellHeight()/2;
		double center = (double)getCellWidth()/2;
		hexagon.getPoints().addAll(new Double[] {
				0.0,0.0,
				side,0.0,
				side+side/2,center,
				side,center*2,
				0.0,center*2,
				-side/2,center
			});
		if(col%2!=0) {
			hexagon.setTranslateY(getCellHeight()/2);
		}
		hexagon.setTranslateX(-.25*(double)(getCellWidth())*col);
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
		if(toroidal) {
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
				if (row + i < getNumRows() & row + i > -1 & col + j < getNumCols() & col + j > -1) {
					if (isNeighborAdjacent(cell.getRow(),cell.getCol(),row + i, col + j)){
						neighbors.add(getCurrentGrid()[row + i][col + j]);
					}
				}
			}
		}
	}
	
	private boolean isNeighborAdjacent(int currentRow, int currentCol, int otherRow, int otherCol) {
		if(maxNeighbors) {
			if(currentCol%2!=0) {//downcurve
				//left,right,up, lower left, lower right,down
				return (Math.abs(currentRow - otherRow) <= 1 & Math.abs(currentCol - otherCol) <= 1)
						& !(otherRow == currentRow & otherCol == currentCol) 
						& !(Math.abs(currentRow - otherRow)==1 & currentCol-otherCol==-1);
			}
			//upcurve
			else {
				//left, right, up, down, upper left, upper right
				return (Math.abs(currentRow - otherRow) <= 1 & Math.abs(currentCol - otherCol) <= 1)
						& !(otherRow == currentRow & otherCol == currentCol) 
						& !(Math.abs(currentRow - otherRow)==1 & currentCol-otherCol==1);
			}
		}
		else { //upcurve- left, right, upper left, upper right 
			boolean leftOrRight = (currentRow == otherRow) | (Math.abs(currentCol-otherCol)==1);
			boolean upperLeftOrUpperRight = (currentRow-otherRow==-1) | (Math.abs(currentCol-otherCol)==1);
			boolean lowerLeftOrLowerRight = (currentRow-otherRow==1) | (Math.abs(currentCol-otherCol)==1);
			if(currentCol%2!=0) {
				return leftOrRight | lowerLeftOrLowerRight;
			}
			else {
				return leftOrRight | upperLeftOrUpperRight;
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
	private void getWrappedNeighbors(Cell cell, List<Cell> neighbors) {
		verticalWrapping(cell, neighbors);
		horizontalWrapping(cell, neighbors);
	}

	protected void horizontalWrapping(Cell cell, List<Cell> neighbors) {
		if (cell.getCol() == 0) {		
			neighbors.add(getCurrentGrid()[cell.getRow()][getNumCols()-1]);
			if(cell.getRow()!=getNumRows()-1) {
				neighbors.add(getCurrentGrid()[cell.getRow()+1][getNumCols()-1]);
			}
		}
		if (cell.getCol() == getNumCols() - 1) {
			neighbors.add(getCurrentGrid()[cell.getRow()][0]);
			if(maxNeighbors) {
				if(cell.getCol()%2!=0) {
					if(cell.getRow()!=0) {
						neighbors.add(getCurrentGrid()[cell.getRow()-1][0]);
					}
				}
				else {
					if(cell.getRow()!=getNumRows()-1) {
						neighbors.add(getCurrentGrid()[cell.getRow()+1][0]);
					}
				}
			}
		}
	}

	protected void verticalWrapping(Cell cell, List<Cell> neighbors) {
		if (cell.getRow() == 0) {
			if(maxNeighbors) {
				neighbors.add(getCurrentGrid()[getNumRows()-1][cell.getCol()]);
			}
			if(cell.getCol()%2==0) {
				if(cell.getCol()!=0) {
					neighbors.add(getCurrentGrid()[getNumRows()-1][cell.getCol()-1]);
				}
				if(cell.getCol()!=getNumCols()-1) {
					neighbors.add(getCurrentGrid()[getNumRows()-1][cell.getCol()+1]);
				}
			}
		}
		if (cell.getRow() == getNumRows() - 1) {
			if(maxNeighbors) {
				neighbors.add(getCurrentGrid()[0][cell.getCol()]);
			}
			if(cell.getCol()%2!=0) {
				if(cell.getCol()!=0) {
					neighbors.add(getCurrentGrid()[0][cell.getCol()-1]);
				}
				if(cell.getCol()!=getNumCols()-1) {
					neighbors.add(getCurrentGrid()[0][cell.getCol()+1]);
				}
			}
		}
	}

}

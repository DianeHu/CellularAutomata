package cellManager;

import java.util.List;

import XMLClasses.GridConfiguration;
import cells.Cell;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * @author Madhavi Rajiv
 * This class is a subclass of Grid which creates hexagons as cell shapes
 * and gets neighbors based on the shape of the hexagon.
 */
public class HexagonGrid extends Grid{

	public static final int SIZE = 400;
	private Polygon[][] blocks;


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
	 * Creates a grid of blank polygons which represents the cells.
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
	
	/**
	 * @param hexagon
	 * @param row
	 * @param col
	 * Takes in a polygon at a certain location, and adds points to create
	 * a hexagonal shape based on the polygon's location.
	 */
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
	 * @param currentRow
	 * @param currentCol
	 * @param otherRow
	 * @param otherCol
	 * @return Takes in a Cell's location and a potential neighbor's location to 
	 * determine whether the potential neighbor cell is adjacent 
	 */
	@Override
	protected boolean isNeighborAdjacent(int currentRow, int currentCol, int otherRow, int otherCol) {
		if(getMaxNeighbors()) {
			if(currentCol%2!=0) {
				return (Math.abs(currentRow - otherRow) <= 1 & Math.abs(currentCol - otherCol) <= 1)
						& !(otherRow == currentRow & otherCol == currentCol) 
						& !(Math.abs(currentRow - otherRow)==1 & currentCol-otherCol==-1);
			}
			else {
				return (Math.abs(currentRow - otherRow) <= 1 & Math.abs(currentCol - otherCol) <= 1)
						& !(otherRow == currentRow & otherCol == currentCol) 
						& !(Math.abs(currentRow - otherRow)==1 & currentCol-otherCol==1);
			}
		}
		else {
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
			neighbors.add(getCurrentGrid()[cell.getRow()][getNumCols()-1]);
			if(cell.getRow()!=getNumRows()-1) {
				neighbors.add(getCurrentGrid()[cell.getRow()+1][getNumCols()-1]);
			}
		}
		if (cell.getCol() == getNumCols() - 1) {
			neighbors.add(getCurrentGrid()[cell.getRow()][0]);
			if(getMaxNeighbors()) {
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

	/**
	 * @param cell
	 * @param neighbors
	 * Updates a cell's list of neighbors to include neighbors from vertical wrapping
	 */
	private void verticalWrapping(Cell cell, List<Cell> neighbors) {
		if (cell.getRow() == 0) {
			if(getMaxNeighbors()) {
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
			if(getMaxNeighbors()) {
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

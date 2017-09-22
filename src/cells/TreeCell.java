package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Diane Hu
 */
public class TreeCell extends Cell{

	private double probCatch = .5;
	private double probGrow = 0.01;
	
	public TreeCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.FORESTGREEN);
	}
	
	/* (non-Javadoc)
	 * @see cells.Cell#isSurroundingNeighbor(int, int)
	 * This method overrides the superclass method to only account for the compass directions (North, South, East, West) as neighbors
	 */
	@Override
	public boolean isNeighbor(int otherRowNum, int otherColNum) {
		return super.isNeighbor4(otherRowNum, otherColNum);
	}
	
	/**
	 * @param root
	 * If current cell experiences a threat of fire, then if a random generated value is bounded within the severity of the catch times the 
	 * probability of catching fire, the current cell starts to burn.
	 */
	public void burn(Grid newGrid) {
		Cell newCell = new BurningTreeCell(getRow(), getCol());
		newGrid.addToNewGrid(newCell);
	}
	
	/**
	 * For every cell in the current cell's neighbors, if there is a burning cell, indicate there is a threat of fire, and increase severity by
	 * number of burning cells.
	 */
	public boolean checkFireThreat() {
		for(Cell cell : getNeighbors()) {
			if(cell instanceof BurningTreeCell) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param root
	 * @param cell
	 * For a cell that is empty, if a random generated value is contained within the probability of a tree growing, place a new tree in the current
	 * empty spot's location.
	 */
	public void growTree(Grid newGrid, Cell cell) {
		double test = Math.random();
		if(test < probGrow) {
			Cell newCell = new TreeCell(cell.getRow(), cell.getCol());
			newGrid.addToNewGrid(newCell);
		}
	}

	@Override
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		for(Cell c : emptySpots) {
			growTree(grid, c);
		}
		if(checkFireThreat()) {
			double test = Math.random();
			if(test < getNumBurningNeighbors() * probCatch) {
				burn(grid);
			}
		} else {
			grid.addToNewGrid(this);
		}
	}
}

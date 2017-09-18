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
	private int rowNum;
	private int colNum;
	private Rectangle block;
	private ArrayList<Cell> neighbors;
	private boolean fireThreat = false;
	private double probCatch = .15;
	private double threatSeverity = 1;
	private double probGrow = .15;
	private int width;
	private int height;
	
	public TreeCell(int myRowNum, int myColNum, int width, int height) {
		super(myRowNum, myColNum, width, height);
		block.setFill(Color.FORESTGREEN);
	}
	
	/* (non-Javadoc)
	 * @see cells.Cell#isSurroundingNeighbor(int, int)
	 * This method overrides the superclass method to only account for the compass directions (North, South, East, West) as neighbors
	 */
	@Override
	public boolean isSurroundingNeighbor(int otherRowNum, int otherColNum) {
		if((Math.abs(rowNum-otherRowNum)<=1 && (colNum == otherColNum)) || (Math.abs(colNum-otherColNum)<=1 && (rowNum == otherRowNum))) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param root
	 * If current cell experiences a threat of fire, then if a random generated value is bounded within the severity of the catch times the 
	 * probability of catching fire, the current cell starts to burn.
	 */
	public void burn(Group root) {
		if(fireThreat) {
			double test = Math.random();
			if(test < threatSeverity * probCatch) {
				Cell newCell = new BurningTreeCell(rowNum, colNum, width, height);
				changeCellType(root, this, newCell);
			}
		}
	}
	
	/**
	 * For every cell in the current cell's neighbors, if there is a burning cell, indicate there is a threat of fire, and increase severity by
	 * number of burning cells.
	 */
	public void checkFireThreat() {
		for(Cell cell : neighbors) {
			if(cell instanceof BurningTreeCell) {
				fireThreat = true;
				threatSeverity++;
			}
		}
	}
	
	/**
	 * @param root
	 * @param cell
	 * For a cell that is empty, if a random generated value is contained within the probability of a tree growing, place a new tree in the current
	 * empty spot's location.
	 */
	public void growTree(Group root, Cell cell) {
		if(cell instanceof EmptyCell) {
			double test = Math.random();
			if(test < probGrow) {
				Cell newCell = new TreeCell(rowNum, colNum, width, height);
				changeCellType(root, this, newCell);
			}
		}
	}
}

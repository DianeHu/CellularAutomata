package cells;

import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Madhavi
 * This class represents a blue cell in a Rock Paper Scissors simulation.
 * It keeps track of when the cell is eaten, or when it eats.
 *
 */
public class BlueRPSCell extends Cell {
	private static final Color BLUE_RPS_COLOR = Color.BLUE;
	private boolean isDead;
	
	public BlueRPSCell() {
		super();
		setLevel(0);
		setColor(BLUE_RPS_COLOR);
	}
	
	/* (non-Javadoc)
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		return new BlueRPSCell();
	}

	/* (non-Javadoc)
	 * @see cells.Cell#setThreshold(double, double, double)
	 */
	@Override
	public void setThreshold(double a, double b, double c) {
		//do nothing
		
	}

	/* (non-Javadoc)
	 * @see cells.Cell#changeType()
	 * This method is called when a cell is eaten so that it can revert
	 * to the type of the cell which ate it.
	 */
	@Override
	public Cell changeType() {
		RedRPSCell newCell = new RedRPSCell();
		newCell.setRow(getRow());newCell.setCol(getCol());
		isDead = true;
		return newCell;
	}

	/* (non-Javadoc)
	 * @see cells.Cell#moveCell(java.util.List, cellManager.Grid)
	 * The cell moves by deciding to either eat or be eaten by a randomly
	 * chosen neighbor. 
	 */
	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		if(!isDead) {
			grid.addToNewGrid(this);
		}
		Cell c = chooseRandomNeighbor();
		if(c instanceof RedRPSCell) {
			//get eaten
			createNewCellOfType(new RedRPSCell(),grid);
		}
		if(c instanceof GreenRPSCell) {
			//eat
			grid.addToNewGrid(c.changeType());
			setLevel(0);
		}
		
	}

}

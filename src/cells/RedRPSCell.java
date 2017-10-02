package cells;

import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;
/**
 * @author Madhavi
 * This class represents a red cell in a Rock Paper Scissors simulation.
 * It keeps track of when the cell is eaten, or when it eats.
 *
 */
public class RedRPSCell extends Cell {

private static final Color RED_RPS = Color.RED;
private boolean isDead;
	public RedRPSCell() {
		super();
		setLevel(0);
		setColor(RED_RPS);
	}
	
	/* (non-Javadoc)
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		return new RedRPSCell();
	}

	/* (non-Javadoc)
	 * @see cells.Cell#setThreshold(double, double, double)
	 */
	@Override
	public void setThreshold(double a, double b, double c) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see cells.Cell#changeType()
	 * This method is called when a cell is eaten so that it can revert
	 * to the type of the cell which ate it.
	 */
	@Override
	public Cell changeType() {
		GreenRPSCell newCell = new GreenRPSCell();
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
		if(c instanceof GreenRPSCell) {
			//get eaten
			createNewCellOfType(new GreenRPSCell(),grid);
		}
		if(c instanceof BlueRPSCell) {
			//eat
			grid.addToNewGrid(c.changeType());
			setLevel(0);
		}		
	}

}

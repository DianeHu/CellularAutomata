package cells;

import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Madhavi
 * This class represents a green cell in a Rock Paper Scissors simulation.
 * It keeps track of when the cell is eaten, or when it eats.
 *
 */
public class GreenRPSCell extends Cell {
	private static final Color GREEN_RPS_COLOR = Color.GREEN;
	private boolean isDead;
	public GreenRPSCell() {
		super();
		setLevel(0);
		setColor(GREEN_RPS_COLOR);
	}
	@Override
	public Cell copy() {
		// TODO Auto-generated method stub
		return new GreenRPSCell();
	}

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
		// TODO Auto-generated method stub
		BlueRPSCell newCell = new BlueRPSCell();
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
		if(c instanceof BlueRPSCell) {
			createNewCellOfType(new BlueRPSCell(),grid);
		}
		if(c instanceof RedRPSCell) {
			grid.addToNewGrid(c.changeType());
			setLevel(0);
		}
	}

}

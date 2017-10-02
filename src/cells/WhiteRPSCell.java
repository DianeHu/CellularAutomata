package cells;

import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Madhavi
 * This class represents a white cell in a Rock Paper Scissors simulation.
 * It keeps track of when the cell becomes a colored cell.
 *
 */
public class WhiteRPSCell extends Cell {


	private static final Color WHITE_RPS = Color.WHITE;


	public WhiteRPSCell() {
		super();
		setLevel(0);
		setColor(WHITE_RPS);
	}
	/* (non-Javadoc)
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		// TODO Auto-generated method stub
		return new WhiteRPSCell();
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
	 */
	@Override
	public Cell changeType() {
		BlueRPSCell newCell = new BlueRPSCell();
		newCell.setRow(getRow());newCell.setCol(getCol());
		return newCell;
	}


	/* (non-Javadoc)
	 * @see cells.Cell#moveCell(java.util.List, cellManager.Grid)
	 * In this move method, the white cell changes to a colored one
	 * if its randomly chosen neighbor is a colored cell.
	 */
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		grid.addToNewGrid(this);
		Cell c = chooseRandomNeighbor();		
		if(c.getLevel()<10) {
			if(c instanceof BlueRPSCell) {
				Cell newcell = new BlueRPSCell();
				newcell.setLevel(c.getLevel()+1);;
				createNewCellOfType(newcell,grid);
			}		
			if(c instanceof RedRPSCell) {
				Cell newcell = new RedRPSCell();
				newcell.setLevel(c.getLevel()+1);;
				createNewCellOfType(newcell,grid);
			}
			
			if(c instanceof BlueRPSCell) {
				Cell newcell = new RedRPSCell();
				newcell.setLevel(c.getLevel()+1);;
				createNewCellOfType(newcell,grid);
			}
		}
	}

}

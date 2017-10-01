package cells;

import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

public class BlueRPSCell extends Cell {
	private boolean isDead;
	public BlueRPSCell() {
		super();
		setLevel(0);
		setColor(Color.BLUE);
	}
	
	@Override
	public Cell copy() {
		return new BlueRPSCell();
	}

	@Override
	public void setThreshold(double a, double b, double c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cell changeType() {
		RedRPSCell newCell = new RedRPSCell();
		newCell.setRow(getRow());newCell.setCol(getCol());
		isDead = true;
		return newCell;
	}

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

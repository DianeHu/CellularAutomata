package cells;

import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

public class RedRPSCell extends Cell {
private boolean isDead;
	public RedRPSCell() {
		super();
		setLevel(0);
		setColor(Color.RED);
	}
	public Cell copy() {
		return new RedRPSCell();
	}

	@Override
	public void setThreshold(double a, double b, double c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cell changeType() {
		GreenRPSCell newCell = new GreenRPSCell();
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

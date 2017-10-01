package cells;

import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

public class GreenRPSCell extends Cell {
	private boolean isDead;
	public GreenRPSCell() {
		super();
		setLevel(0);
		setColor(Color.GREEN);
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

	@Override
	public Cell changeType() {
		// TODO Auto-generated method stub
		BlueRPSCell newCell = new BlueRPSCell();
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
		if(c instanceof BlueRPSCell) {
			//get eaten
			createNewCellOfType(new BlueRPSCell(),grid);
		}
		if(c instanceof RedRPSCell) {
			//eat
			grid.addToNewGrid(c.changeType());
			setLevel(0);
		}

	}

}

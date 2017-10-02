package cells;

import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

public class WhiteRPSCell extends Cell {

	public WhiteRPSCell() {
		super();
		setLevel(0);
		setColor(Color.WHITE);
	}
	@Override
	public Cell copy() {
		// TODO Auto-generated method stub
		return new WhiteRPSCell();
	}

	@Override
	public void setThreshold(double a, double b, double c) {
		// TODO Auto-generated method stub
	}

	@Override
	public Cell changeType() {
		BlueRPSCell newCell = new BlueRPSCell();
		newCell.setRow(getRow());newCell.setCol(getCol());
		return newCell;
	}

	@Override
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

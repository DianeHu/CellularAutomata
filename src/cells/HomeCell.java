package cells;

import java.util.List;

import cellManager.Grid;
import gridPatches.ForagingLand;
import javafx.scene.paint.Color;

public class HomeCell extends Cell{
	
	private ForagingLand land;
	
	public HomeCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.PLUM);
		setStrokeColor(getColor());
	}
	
	public HomeCell() {
		super();
		setColor(Color.GOLDENROD);
		setStrokeColor(getColor());
	}

	public void setLand(ForagingLand l) {
		land = l;
	}
	
	@Override
	public Cell copy() {
		HomeCell newCell = new HomeCell();
		return newCell;
	}
	
	@Override
	public Cell changeType() {
		return this;
		// TODO
	}

	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setThreshold(double t, double unused1, double unused2) {
		// TODO Auto-generated method stub
	}

}

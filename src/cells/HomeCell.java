package cells;

import java.util.List;

import cellManager.Grid;
import gridPatches.ForagingLand;
import javafx.scene.paint.Color;

public class HomeCell extends Cell implements ForagingCell,LocationCell{
	
	private ForagingLand land;
	
	public HomeCell(int myRowNum, int myColNum, ForagingLand l) {
		super(myRowNum, myColNum);
		setColor(Color.PLUM);
		land = l;
		
	}
	
	public HomeCell() {
		super();
		setColor(Color.GOLDENROD);
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
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		// TODO Auto-generated method stub
		
	}

}

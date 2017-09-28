package cells;

import java.util.List;

import cellManager.Grid;
import gridPatches.ForagingLand;
import javafx.scene.paint.Color;

public class FoodCell extends Cell implements ForagingCell,LocationCell{
	
	private ForagingLand land;
	
	public FoodCell(int myRowNum, int myColNum, ForagingLand l) {
		super(myRowNum, myColNum,l);
		setColor(Color.GOLDENROD);
		land = l;
	}
	
	public FoodCell() {
		super();
		setColor(Color.GOLDENROD);
	}
	
	public void setLand(ForagingLand l) {
		land = l;
	}

	@Override
	public Cell copy() {
		FoodCell newCell = new FoodCell();
		return newCell;
	}

	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		// TODO Auto-generated method stub
		
	}

}
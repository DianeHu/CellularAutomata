package cells;

import java.util.List;

import cellManager.Grid;
import gridPatches.ForagingLand;
import javafx.scene.paint.Color;

public class FoodCell extends Cell{
	
	private ForagingLand land;

	public FoodCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.GOLDENROD);
		setStrokeColor(getColor());
	}
	
	public FoodCell() {
		super();
		setColor(Color.GOLDENROD);
		setStrokeColor(getColor());
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

	@Override
	public void setThreshold(double a, double b, double c) {
		// TODO Auto-generated method stub
		
	}

}
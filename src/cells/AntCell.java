package cells;

import java.util.List;

import cellManager.Grid;
import gridPatches.ForagingLand;
import javafx.scene.paint.Color;

public class AntCell extends Cell{

	private ForagingLand land;
	private boolean facingVertPositive;
	private boolean facingHorizontalPositive;
	private boolean goingHome;
	
	public AntCell(int myRowNum, int myColNum){
		super(myRowNum, myColNum);
		setColor(Color.DARKRED);
	}
	
	public AntCell() {
		super();
		setColor(Color.DARKRED);
	}

	@Override
	public Cell copy() {
		AntCell newCell = new AntCell();
		return newCell;
	}

	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		// TODO Auto-generated method stub
		List<Integer> coordinates = land.getNewCoordinates(facingVertPositive,facingHorizontalPositive,goingHome);
		int newrow = coordinates.get(0); int newcol = coordinates.get(1);
		
		
	}

	@Override
	public void setThreshold(double a, double b, double c) {
		// do nothing
	}

}

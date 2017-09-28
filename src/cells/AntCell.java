package cells;

import java.util.List;

import cellManager.Grid;
import gridPatches.ForagingLand;
import javafx.scene.paint.Color;

public class AntCell extends Cell implements ForagingCell{

	private ForagingLand land;
	private boolean facingVertPositive;
	private boolean facingHorizontalPositive;
	private boolean goingHome;
	
	public AntCell(int myRowNum, int myColNum, ForagingLand l){
		super(myRowNum, myColNum,l);
		setColor(Color.DARKRED);
		land = l;
	}
	
	public void setLand(ForagingLand l) {
		land = l;
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

}

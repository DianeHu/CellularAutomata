package cells;

import java.util.List;

import cellManager.Grid;
import gridPatches.ForagingLand;
import javafx.scene.paint.Color;

public class AntCell extends Cell{

	private boolean facingVertPositive;
	private boolean facingHorizontalPositive;
	private boolean hasFood;
	
	public AntCell(int myRowNum, int myColNum){
		super(myRowNum, myColNum);
	}
	
	public AntCell() {
		super();
	}

	@Override
	public Cell copy() {
		AntGroupCell newCell = new AntGroupCell();
		return newCell;
	}

	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		getLand().addPheromones(getRow(),getCol(),hasFood);
		List<Integer> coordinates = getLand().getNewCoordinates(facingVertPositive,facingHorizontalPositive,hasFood);		
		int newrow = coordinates.get(0); int newcol = coordinates.get(1);		
		updateCurrentGroup(grid, newrow, newcol);	
		moveToNewGroup(grid, newrow, newcol);
		
	}

	protected void moveToNewGroup(Grid grid, int newrow, int newcol) {
		if(!(grid.getCellInNewGridAt(newrow, newcol) instanceof AntGroupCell)) {
			AntGroupCell newgroup= new AntGroupCell(newrow, newcol);
			newgroup.setLand(getLand());
			newgroup.addAnt(this);
			grid.addToNewGrid(newgroup);
		}
		else {
			AntGroupCell existingGroup = (AntGroupCell)grid.getCellInNewGridAt(newrow, newcol);
			existingGroup.addAnt(this);
		}
		if(getLand().atHome(newrow,newcol)) {
			hasFood = false;
			//top off
		}
		if(getLand().atFoodSource(newrow,newcol)) {
			hasFood = true;
			//top off
		}
	}

	protected void updateCurrentGroup(Grid grid, int newrow, int newcol) {
		if(!(newrow==getRow() & newcol==getCol())) {
			if(grid.getCellInNewGridAt(getRow(), getCol()) instanceof AntGroupCell) {
				AntGroupCell group= (AntGroupCell)grid.getCellInNewGridAt(getRow(), getCol());
				group.removeAnt(this);
				if(group.getNumAnts()==0) {
					grid.removeFromNewGrid(group);
				}
			}
		}
	}

}
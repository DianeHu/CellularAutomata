package cells;

import java.util.ArrayList;
import java.util.List;

import cellManager.Grid;
import gridPatches.ForagingLand;

public class AntCell extends Cell {

	private int[] direction;
	
	private boolean hasFood;
	private int maxAnts;
	
	public AntCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setInitialDirection();
	}
	
	protected void setMaxAnts(int m) {
		maxAnts = m;
	}

	protected void setInitialDirection() {
		direction = new int[2];
		while(!(direction[0] == 0 & direction[1] == 0)) {
			direction[0] = randomDir();
			direction[1] = randomDir();
		}
	}
	


	protected int randomDir() {
		if(Math.random()<.33) {
			return 0;
		}
		else if(Math.random()<.66){
			return 1;
		}
		else {
			return -1;
		}
	}

	public AntCell() {
		super();
		setInitialDirection();
	}

	@Override
	public Cell copy() {
		AntGroupCell newCell = new AntGroupCell();
		return newCell;
	}
	
	private List<Cell> getDirectionNeighbors() {
		List<Cell> dirNeigh = new ArrayList<Cell>();
		boolean isNeighbor;
		for(Cell c: getNeighbors()) {
			int row = getRow(); 
			int col = getCol();
			int r2 = c.getRow(); 
			int c2 = c.getCol();
			isNeighbor = r2==row+direction[0] & c2==col+direction[1];
			if(direction[0]==1 & direction[1]==0) {
				isNeighbor = isNeighbor|
						(r2==row+1 & c2==col+1)|
						(r2==row+1 & c2==col-1);
			}
			if(direction[0]==1 & direction[1]==1) {
				isNeighbor = isNeighbor|
						(r2==row & c2==col+1)|
						(r2==row+1 & c2==col);
			}
			if(direction[0]==0 & direction[1]==1) {
				isNeighbor = isNeighbor|
						(r2==row-1 & c2==col+1)|
						(r2==row+1 & c2==col+1);
			}
			if(direction[0]==-1 & direction[1]==1) {
				isNeighbor = isNeighbor|
						(r2==row-1 & c2==col)|
						(r2==row & c2==col+1);
			}
			if(direction[0]==-1 & direction[1]==-1) {
				isNeighbor = isNeighbor|
						(r2==row & c2==col-1)|
						(r2==row-1 & c2==col);
			}
			if(direction[0]==1 & direction[1]==-1) {
				isNeighbor = isNeighbor|
						(r2==row & c2==col-1)|
						(r2==row+1 & c2==col);
			}
			if(direction[0]==0 & direction[1]==-1) {
				isNeighbor = isNeighbor|
						(r2==row-1 & c2==col)|
						(r2==row+1 & c2==col);
			}
			if(direction[0]==-1 & direction[1]==0) {
				isNeighbor = isNeighbor|
						(r2==row & c2==col+1)|
						(r2==row & c2==col-1);
			}
			
			addCellIf(isNeighbor,c,dirNeigh);
		}
		return dirNeigh;
	}

	private void addCellIf(boolean condition, Cell c, List<Cell> neighbors) {
		if(condition) {
			neighbors.add(c);
		}	
	}

	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		getLand().addPheromones(getRow(), getCol(), hasFood,getNeighbors());
		List<Integer> coordinates = getLand().getNewCoordinates(getDirectionNeighbors(),hasFood,maxAnts,this);
		int newrow = coordinates.get(0);
		int newcol = coordinates.get(1);
		if(newrow==getRow() & newcol==getCol()) {
			coordinates = getLand().getNewCoordinates(getNeighbors(),hasFood,maxAnts,this);
			newrow = coordinates.get(0);
			newcol = coordinates.get(1);
		}
		if(!(newrow==getRow() & newcol==getCol())) {
			direction[0] = newrow - getRow();
			if(Math.abs(direction[0])>1) {
				direction[0] = -1*(Integer.signum(newrow-getRow()));
			}
			direction[1] = newcol - getCol();
			if(Math.abs(direction[1])>1) {
				direction[1] = -1*(Integer.signum(newcol-getCol()));
			}
		}
		updateCurrentGroup(grid, newrow, newcol);
		moveToNewGroup(grid, newrow, newcol);

	}

	@Override
	public void setThreshold(double a, double b, double c) {
		// do nothing
	}

	@Override
	public Cell changeType() {
		return this;
		// TODO Auto-generated method stub
	}

	protected void moveToNewGroup(Grid grid, int newrow, int newcol) {
		if (!(grid.getCellInNewGridAt(newrow, newcol) instanceof AntGroupCell)) {
			AntGroupCell newgroup = new AntGroupCell(newrow, newcol);
			newgroup.setLand(getLand());
			newgroup.addAnt(this);
			grid.addToNewGrid(newgroup);
		} else {
			AntGroupCell existingGroup = (AntGroupCell) grid.getCellInNewGridAt(newrow, newcol);
			existingGroup.addAnt(this);
		}
		if (getLand().atHome(newrow, newcol)) {
			hasFood = false;
			getLand().topOffHome();
			// top off
		}
		if (getLand().atFoodSource(newrow, newcol)) {
			hasFood = true;
			getLand().topOffFood();
			// top off
		}
	}

	protected void updateCurrentGroup(Grid grid, int newrow, int newcol) {
		if (!(newrow == getRow() & newcol == getCol())) {
			if (grid.getCellInNewGridAt(getRow(), getCol()) instanceof AntGroupCell) {
				AntGroupCell group = (AntGroupCell) grid.getCellInNewGridAt(getRow(), getCol());
				group.removeAnt(this);
				if (group.getNumAnts() == 0) {
					grid.removeFromNewGrid(group);
					grid.addToNewGrid(new EmptyForagingCell(getRow(),getCol()));
				}
			}
		}
	}
}

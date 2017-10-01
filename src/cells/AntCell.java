package cells;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cellManager.Grid;

public class AntCell extends Cell {

	private int[] direction;
	
	private boolean hasFood;
	private double maxAnts;

	public AntCell() {
		super();
		setInitialDirection();
	}
	
	protected void setMaxAnts(double m) {
		maxAnts = m;
	}

	protected void setInitialDirection() {
		direction = new int[2];
		while(direction[0] == 0 & direction[1] == 0) {
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

	@Override
	public Cell copy() {
		AntGroupCell newCell = new AntGroupCell();
		return newCell;
	}
	
	private List<Cell> getDirectionNeighbors() {
		List<Cell> dirNeigh = new ArrayList<Cell>();	
		for(Cell c: getNeighbors()) {
			boolean toTheSide = false;
			int row = getRow(); 
			int col = getCol();
			int r2 = c.getRow(); 
			int c2 = c.getCol();
			boolean isDirectlyInFront = r2==row+direction[0] & c2==col+direction[1];
			if(direction[0]==1 & direction[1]==0) {
				toTheSide = (r2==row+1 & c2==col+1)|
						(r2==row+1 & c2==col-1);
				//System.out.println(toTheSide + " toTheSide for" + r2 + " " +c2);
			}
			if(direction[0]==1 & direction[1]==1) {
				toTheSide = (r2==row & c2==col+1)|
						(r2==row+1 & c2==col);
				//System.out.println(toTheSide + "toTheSide for" + r2 + " " +c2);
			}
			if(direction[0]==0 & direction[1]==1) {
				toTheSide = (r2==row-1 & c2==col+1)|
						(r2==row+1 & c2==col+1);
				//System.out.println(toTheSide + "toTheSide for" + r2 + " " +c2);
			}
			if(direction[0]==-1 & direction[1]==1) {
				toTheSide = (r2==row-1 & c2==col)|
						(r2==row & c2==col+1);
				//System.out.println(toTheSide + "toTheSide for" + r2 + " " +c2);
			}
			if(direction[0]==-1 & direction[1]==-1) {
				toTheSide = (r2==row & c2==col-1)|
						(r2==row-1 & c2==col);
				//System.out.println(toTheSide + "toTheSide for" + r2 + " " +c2);
			}
			if(direction[0]==1 & direction[1]==-1) {
				toTheSide = (r2==row & c2==col-1)|
						(r2==row+1 & c2==col);
				//System.out.println(toTheSide + "toTheSide for" + r2 + " " +c2);
			}
			if(direction[0]==0 & direction[1]==-1) {
				toTheSide = (r2==row-1 & c2==col)|
						(r2==row+1 & c2==col);
				//System.out.println(toTheSide + "toTheSide for" + r2 + " " +c2);
			}
			if(direction[0]==-1 & direction[1]==0) {
				toTheSide = (r2==row & c2==col+1)|
						(r2==row & c2==col-1);
				//System.out.println(toTheSide + "toTheSide for" + r2 + " " +c2);
			}

			boolean isNeighbor = isDirectlyInFront | toTheSide;
			//System.out.println(r2 + " " + c2 + " " + isNeighbor);
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
		//System.out.println("numneighbors " + getDirectionNeighbors().size());
		//System.out.println("directions " + Arrays.toString(direction));
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
		}
		if (getLand().atFoodSource(newrow, newcol)) {
			hasFood = true;
			getLand().topOffFood();
		}
	}

	protected void updateCurrentGroup(Grid grid, int newrow, int newcol) {
		if (!(newrow == getRow() & newcol == getCol())) {
			if (grid.getCellInNewGridAt(getRow(), getCol()) instanceof AntGroupCell) {
				AntGroupCell group = (AntGroupCell) grid.getCellInNewGridAt(getRow(), getCol());
				group.removeAnt(this);
				if (group.getNumAnts() != 0) {
					grid.addToNewGrid(group);
				}
			}
		}
	}
}

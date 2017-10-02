package cells;

import java.util.ArrayList;
import java.util.List;

import cellManager.Grid;

/**
 * @author Madhavi
 * This class is meant to represent an individual ant. Only AntGroupCell has access to AntCell,
 * unlike for other Cells, the Grid class doesn't have access to AntCells. This AntCell is
 * dependent on the location and parameter of the AntGroupCell. It keeps track of its direction,
 * and whether it has food and is headed home, or whether its looking for food.
 */
public class AntCell extends Cell {

	private int[] direction;
	
	private boolean hasFood;
	private double maxAnts;

	public AntCell() {
		super();
		setInitialDirection();
	}
	
	/**
	 * @param m
	 * Sets the max number of ants in a neighboring cell before it won't move into
	 * that cell.
	 */
	protected void setMaxAnts(double m) {
		maxAnts = m;
	}

	/**
	 * Randomizes the initial direction vector.
	 */
	protected void setInitialDirection() {
		direction = new int[2];
		while(direction[0] == 0 & direction[1] == 0) {
			direction[0] = randomDir();
			direction[1] = randomDir();
		}
	}
	
	/**
	 * @return Returns a random choice of 1, -1, or 0 to represent a direction
	 */
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

	/* (non-Javadoc)
	 * @see cells.Cell#copy()
	 */
	@Override
	public Cell copy() {
		AntGroupCell newCell = new AntGroupCell();
		return newCell;
	}
	
	/**
	 * @return Returns a list of neighbor cells in the direction the ant is facing.
	 */
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
			}
			if(direction[0]==1 & direction[1]==1) {
				toTheSide = (r2==row & c2==col+1)|
						(r2==row+1 & c2==col);
			}
			if(direction[0]==0 & direction[1]==1) {
				toTheSide = (r2==row-1 & c2==col+1)|
						(r2==row+1 & c2==col+1);
			}
			if(direction[0]==-1 & direction[1]==1) {
				toTheSide = (r2==row-1 & c2==col)|
						(r2==row & c2==col+1);
			}
			if(direction[0]==-1 & direction[1]==-1) {
				toTheSide = (r2==row & c2==col-1)|
						(r2==row-1 & c2==col);
			}
			if(direction[0]==1 & direction[1]==-1) {
				toTheSide = (r2==row & c2==col-1)|
						(r2==row+1 & c2==col);
			}
			if(direction[0]==0 & direction[1]==-1) {
				toTheSide = (r2==row-1 & c2==col)|
						(r2==row+1 & c2==col);
			}
			if(direction[0]==-1 & direction[1]==0) {
				toTheSide = (r2==row & c2==col+1)|
						(r2==row & c2==col-1);
			}

			boolean isNeighbor = isDirectlyInFront | toTheSide;
			addCellIf(isNeighbor,c,dirNeigh);
		}
		return dirNeigh;
	}

	/**
	 * @param condition
	 * @param c
	 * @param neighbors
	 * If a condition is true, this method will add a Cell c to a list of Cells
	 * called neighbors.
	 */
	private void addCellIf(boolean condition, Cell c, List<Cell> neighbors) {
		if(condition) {
			neighbors.add(c);
		}	
	}

	/* (non-Javadoc)
	 * @see cells.Cell#moveCell(java.util.List, cellManager.Grid)
	 * In this move method, the AntCell drops pheromones,
	 * uses ForagingLand to determine where to move, and the AntGroupCell it belongs
	 * to if necessary.
	 */
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
	}

	/**
	 * @param grid
	 * @param newrow
	 * @param newcol
	 * This method uses the information of the new location it will be moving to in 
	 * order to navigate the changes in relevant AntGroupCells. The AntCell adds itself to
	 * the new AntGroupCell, creating it and adding it to the grid if an
	 * AntGroupCell doesn't already exist in the new location. The AntCell also
	 * tops off pheromones if the new location is at home or at the food location.
	 */
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

	/**
	 * @param grid
	 * @param newrow
	 * @param newcol
	 * This method has the AntCell remove itself from the current Group it's in if
	 * it's changing location.
	 */
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

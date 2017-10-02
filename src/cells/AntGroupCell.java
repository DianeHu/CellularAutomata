package cells;

import java.util.ArrayList;
import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

/**
 * @author Madhavi
 * This cell class is actually a group of AntCells in one location. AntCells are
 * entirely dependent on this class, as it is the only one Grid has access to. 
 * This class keeps track of all the inidividual ants and makes sure they have
 * the right location, maxAnts number, and that they move during each time step.
 */
public class AntGroupCell extends Cell{


	private static final Color ANTGROUPCELL_COLOR = Color.INDIANRED;
	private List<AntCell> ants = new ArrayList<AntCell>();
	private double maxAnts;

	
	public AntGroupCell(int myRowNum, int myColNum){
		super(myRowNum, myColNum);
		setColor(ANTGROUPCELL_COLOR);
	}
	
	public AntGroupCell() {
		super();
		setColor(ANTGROUPCELL_COLOR);
		ants.add(new AntCell());
	}

	@Override
	public Cell copy() {
		AntGroupCell newCell = new AntGroupCell();
		return newCell;
	}
	

	/* (non-Javadoc)
	 * @see cells.Cell#moveCell(java.util.List, cellManager.Grid)
	 * This move method just sets the parameters for all the individual ants
	 * and then calls their moveCell method.
	 */
	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		if(ants.size()>0) {
			for(AntCell ant: ants) {
				ant.setRow(getRow());
				ant.setCol(getCol());
				ant.setLand(getLand());
				ant.setNeighbors(getNeighbors());
				ant.setMaxAnts(maxAnts);
				ant.moveCell(emptySpots,grid);
			}
		}
	}
	
	/**
	 * @param a
	 * This method adds an AntCell a to the AntGroupCell
	 */
	public void addAnt(AntCell a) {
		ants.add(a);
		a.setRow(getRow());a.setCol(getCol());
	}
	
	/**
	 * @param a
	 * This method removes an AntCell a from the AntGroupCell
	 */
	public void removeAnt(AntCell a) {
		ants.remove(a);
	}
	
	/**
	 * @return Returns the number of ants in the AntGroupCell
	 */
	public int getNumAnts() {
		return ants.size();
	}

	/* (non-Javadoc)
	 * @see cells.Cell#setThreshold(double, double, double)
	 * The threshold is the maximum number of ants in a neighboring group
	 * before an individual ant wouldn't want to move to that spot.
	 */
	@Override
	public void setThreshold(double a, double b, double c) {
		maxAnts = a;
	}


	/* (non-Javadoc)
	 * @see cells.Cell#changeType()
	 */
	@Override
	public Cell changeType() {
		EmptyCell newCell = new EmptyCell(this.getRow(), this.getCol());
		return newCell;
	}

}

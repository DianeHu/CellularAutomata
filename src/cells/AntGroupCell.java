package cells;

import java.util.ArrayList;
import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

public class AntGroupCell extends Cell{


	private List<AntCell> ants;
	private double maxAnts;

	
	public AntGroupCell(int myRowNum, int myColNum){
		super(myRowNum, myColNum);
		setColor(Color.INDIANRED);
		List<AntCell> ants = new ArrayList<AntCell>();
	}
	
	public AntGroupCell() {
		super();
		setColor(Color.DARKRED);
		List<AntCell> ants = new ArrayList<AntCell>();
		ants.add(new AntCell());
		
	}

	@Override
	public Cell copy() {
		AntGroupCell newCell = new AntGroupCell();
		return newCell;
	}
	

	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		setStrokeIfAtLocation();
		for(AntCell ant: ants) {
			ant.setRow(getRow());
			ant.setCol(getCol());
			ant.setLand(getLand());
			ant.setNeighbors(getNeighbors());
			ant.moveCell(emptySpots,grid);
		}
		
	}
	
	public void addAnt(AntCell a) {
		ants.add(a);
		a.setRow(getRow());a.setCol(getCol());
		setColor(getColor()); //make darker
	}
	
	public void removeAnt(AntCell a) {
		ants.remove(a);
		setColor(getColor()); //make lighter
	}
	
	public int getNumAnts() {
		return ants.size();
	}

	@Override
	public void setThreshold(double a, double b, double c) {
		maxAnts = a;
	}

	@Override
	public Cell changeType() {
		// TODO Auto-generated method stub
		return null;
	}

}
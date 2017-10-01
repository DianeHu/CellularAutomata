package cells;

import java.util.ArrayList;
import java.util.List;

import cellManager.Grid;
import javafx.scene.paint.Color;

public class AntGroupCell extends Cell{


	private List<AntCell> ants = new ArrayList<AntCell>();
	private double maxAnts;

	
	public AntGroupCell(int myRowNum, int myColNum){
		super(myRowNum, myColNum);
		setColor(Color.INDIANRED);
	}
	
	public AntGroupCell() {
		super();
		setColor(Color.INDIANRED);
		ants.add(new AntCell());
	}

	@Override
	public Cell copy() {
		AntGroupCell newCell = new AntGroupCell();
		return newCell;
	}
	

	@Override
	public void moveCell(List<Cell> emptySpots, Grid grid) {
		System.out.println("row " + getRow() + " col " + getCol() + " size "+ ants.size());
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
		if(ants.size()>1) {
			setColor(Color.MAROON);
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

package gridPatches;

import java.util.ArrayList;
import java.util.List;

import cells.AntGroupCell;
import cells.Cell;

public class ForagingLand implements Land{
	PheromoneLocation[][] foodPheromones;
	PheromoneLocation[][] homePheromones;
	int[] homeLoc; //[r,c]
	int[] foodLoc; //[r,c]
	
	public ForagingLand(int numRows, int numCols, int[] home, int[] food){
		foodPheromones = new PheromoneLocation[numRows][numCols];
		homePheromones = new PheromoneLocation[numRows][numCols];
		initializePheromones();
		homeLoc[0] = home[0]; homeLoc[1] = home[1];
		foodLoc[0] = food[0]; foodLoc[1] = food[1];
	}
	
	private void initializePheromones() {
		for(int i = 0; i<homePheromones[0].length; i++) {
			for(int j = 0; j<homePheromones.length; j++) {
				homePheromones[i][j] = new PheromoneLocation(100);
				foodPheromones[i][j] = new PheromoneLocation(100);
			}
		}		
	}

	public List<Integer> getNewCoordinates(List<Cell> neighbors,boolean goingHome, int maxAnts, Cell callingCell) {
		List<Integer> coordinates = new ArrayList<Integer>();
		int homePher; int foodPher; int max = 0;
		boolean moved = false;
		for(Cell c:neighbors) {
			homePher = homePheromones[c.getRow()][c.getCol()].getNumPheromones();
			foodPher = foodPheromones[c.getRow()][c.getCol()].getNumPheromones();
			if(!(c instanceof AntGroupCell & ((AntGroupCell) c).getNumAnts()>=maxAnts)) {
				if(goingHome) {
					if(homePher>=max) {
						max = homePher; 
						setCellCoordinates(coordinates, c);
					}
				}
				else {
					if(foodPher>=max) {
						max = foodPher; 
						setCellCoordinates(coordinates, c);
					}
				}			
				moved = true;
			}
			
		}
		if(!moved) {
			setCellCoordinates(coordinates,callingCell);
		}
		
		return coordinates;
	}

	private void setCellCoordinates(List<Integer> coordinates, Cell c) {
		coordinates.add(0,c.getRow());
		coordinates.add(1,c.getRow());
	}
	
	public boolean atHome(int row, int col) {
		return row==homeLoc[0] & col==homeLoc[1];
	}
	
	public boolean atFoodSource(int row, int col) {
		return row==foodLoc[0] & col==foodLoc[1];
	}
	
	public void addPheromones(int row, int col,boolean hasFood) {
		if(hasFood) {
			foodPheromones[row][col].addNew();
		}
		else {
			homePheromones[row][col].addNew();
		}
	}
	
	public void evaporate() {
		for(int i = 0; i<homePheromones[0].length; i++) {
			for(int j = 0; j<homePheromones.length; j++) {
				homePheromones[i][j].evaporate();
				foodPheromones[i][j].evaporate();
			}
		}
	}
	
}

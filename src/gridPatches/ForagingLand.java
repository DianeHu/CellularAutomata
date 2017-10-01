package gridPatches;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cells.AntGroupCell;
import cells.Cell;
import javafx.scene.paint.Color;

public class ForagingLand implements Land{
	private static final int EVAPO_RATE = 100;
	private static final int MAX_NUM_PHER = 10;
	PheromoneLocation[][] foodPheromones;
	PheromoneLocation[][] homePheromones;
	int[] homeLoc = new int[2]; //[r,c]
	int[] foodLoc= new int[2];
	
	public ForagingLand(int numRows, int numCols,int[] home, int[] food){
		foodPheromones = new PheromoneLocation[numRows][numCols];
		homePheromones = new PheromoneLocation[numRows][numCols];
		initializePheromones();
		homeLoc = home;
		foodLoc = food;
		topOffFood();
		topOffHome();
	}
	
	private void initializePheromones() {
		for(int i = 0; i<homePheromones[0].length; i++) {
			for(int j = 0; j<homePheromones.length; j++) {
				homePheromones[i][j] = new PheromoneLocation(EVAPO_RATE);
				foodPheromones[i][j] = new PheromoneLocation(EVAPO_RATE);
			}
		}		
	}

	public List<Integer> getNewCoordinates(List<Cell> neighbors,boolean goingHome, double maxAnts, Cell callingCell) {
		List<Integer> coordinates = new ArrayList<Integer>(2);
		int homePher; int foodPher; int max = 0;
		boolean moved = false;
		for(Cell c:neighbors) {
			homePher = homePheromones[c.getRow()][c.getCol()].getNumPheromones();
			foodPher = foodPheromones[c.getRow()][c.getCol()].getNumPheromones();
			boolean tooManyAnts = false;
			if(c instanceof AntGroupCell) {
				tooManyAnts =  ((AntGroupCell) c).getNumAnts()>=maxAnts;
			}
			if(!tooManyAnts) {
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
						System.out.println(Arrays.toString(coordinates.toArray()));
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
		coordinates.add(0, c.getRow());
		coordinates.add(1, c.getCol());
	}
	
	public boolean atHome(int row, int col) {
		return row==homeLoc[0] & col==homeLoc[1];
	}
	
	public boolean atFoodSource(int row, int col) {
		return row==foodLoc[0] & col==foodLoc[1];
	}
	
	public void addPheromones(int row, int col,boolean hasFood, List<Cell> neighbors) {
		if(hasFood) {
			PheromoneLocation l = foodPheromones[row][col];
			int desired = getDesiredNumPheromones(foodPheromones,neighbors);
			addPheromonesUntilAt(l, desired);
		}
		else {
			PheromoneLocation l = homePheromones[row][col];
			int desired = getDesiredNumPheromones(homePheromones,neighbors);
			addPheromonesUntilAt(l, desired);
		}
	}
	
	private int getDesiredNumPheromones(PheromoneLocation pher[][],List<Cell> neighbors) {
		int max = 0;
		for(Cell c: neighbors) {
			if(pher[c.getRow()][c.getCol()].getNumPheromones()>=max) {
				max = pher[c.getRow()][c.getCol()].getNumPheromones();
			}
		}
		if(max>1) {
			return max-1;
		}
		return 0;
	}

	public void evaporate() {
		for(int i = 0; i<homePheromones[0].length; i++) {
			for(int j = 0; j<homePheromones.length; j++) {
				homePheromones[i][j].evaporate();
				foodPheromones[i][j].evaporate();
			}
		}
	}
	
	public void topOffHome() {
		PheromoneLocation h = homePheromones[homeLoc[0]][homeLoc[1]];
		addPheromonesUntilAt(h,MAX_NUM_PHER);
	}

	protected void addPheromonesUntilAt(PheromoneLocation h, int num) {
		while(h.getNumPheromones()<num) {
			h.addNew();
		}
	}
	
	public void topOffFood() {
		PheromoneLocation f = foodPheromones[foodLoc[0]][foodLoc[1]];
		addPheromonesUntilAt(f,MAX_NUM_PHER);
	}
	
	public Color strokeColorAtLocation(int row, int col) {
		if(atHome(row,col)) {
			return Color.CORAL;
		}
		if(atFoodSource(row,col)) {
			return Color.CHARTREUSE;
		}
		else {
			return Color.DARKGREY;
		}
	}
}

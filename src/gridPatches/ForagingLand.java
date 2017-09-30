package gridPatches;

import java.util.ArrayList;
import java.util.List;

public class ForagingLand implements Land{
	int[][] foodPheromones;
	int[][] homePheromones;
	int[] homeLoc;
	int[] foodLoc;
	
	public ForagingLand(int numRows, int numCols, int[] home, int[] food){
		foodPheromones = new int[numRows][numCols];
		homePheromones = new int[numRows][numCols];
		homeLoc[0] = home[0]; homeLoc[1] = home[1];
		foodLoc[0] = food[0]; foodLoc[1] = food[1];
	}
	
	public List<Integer> getNewCoordinates(boolean verticalPos, boolean horizontalPos , boolean goingHome) {
		List<Integer> coordinates = new ArrayList<Integer>();
		return coordinates;
	}
	
	public boolean atHome(int row, int col) {
		return row==homeLoc[0] & col==homeLoc[1];
	}
	
	public boolean atFoodSource(int row, int col) {
		return row==foodLoc[0] & col==foodLoc[1];
	}
	
	public void addPheromones(int row, int col,boolean hasFood) {
		if(hasFood) {
			foodPheromones[row][col]++;
		}
		else {
			homePheromones[row][col]++;
		}
	}
}

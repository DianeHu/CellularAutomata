package gridPatches;

import java.util.ArrayList;
import java.util.List;

public class ForagingLand implements Land{
	
	char[][] homefoodlocation;
	int[][] foodPheromones;
	int[][] homePheromones;
	
	public ForagingLand(int numRows, int numCols){
		homefoodlocation = new char[numRows][numCols];
		foodPheromones = new int[numRows][numCols];
		homePheromones = new int[numRows][numCols];
	}
	
	public List<Integer> getNewCoordinates(boolean verticalPos, boolean horizontalPos , boolean goingHome) {
		List<Integer> coordinates = new ArrayList<Integer>();
		return coordinates;
	}
}

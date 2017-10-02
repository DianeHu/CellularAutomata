package gridPatches;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cells.AntGroupCell;
import cells.Cell;
import javafx.scene.paint.Color;

/**
 * @author Madhavi
 * This class keeps track of permanent locations and pheromone concentrations 
 * throughout the grid. The AntCell class uses this ForagingLand in order to 
 * determine where to move, and how many pheromones to put down. The Grid class uses
 * ForagingLand to determine where home and food are so that it can use SetStroke
 * to mark these locations.
 */
public class ForagingLand{
	private static final int EVAPO_RATE = 100;
	private static final int MAX_NUM_PHER = 10;
	PheromoneLocation[][] foodPheromones;
	PheromoneLocation[][] homePheromones;
	int[] homeLoc = new int[2]; //[r,c]
	int[] foodLoc= new int[2];
	
	/**
	 * @param numRows
	 * @param numCols
	 * @param home
	 * @param food
	 * The constructor for ForagingLand takes in the number of rows and columns in order
	 * to initialize grids keeping track of the two different kinds of pheromones. It also
	 * takes in home and food locations as coordinates.
	 */
	public ForagingLand(int numRows, int numCols,int[] home, int[] food){
		foodPheromones = new PheromoneLocation[numRows][numCols];
		homePheromones = new PheromoneLocation[numRows][numCols];
		initializePheromones();
		homeLoc = home;
		foodLoc = food;
		topOffFood();
		topOffHome();
	}
	
	/**
	 * This method creates PheromoneLocation objects at each location 
	 * in the pheromone grids.
	 */
	private void initializePheromones() {
		for(int i = 0; i<homePheromones[0].length; i++) {
			for(int j = 0; j<homePheromones.length; j++) {
				homePheromones[i][j] = new PheromoneLocation(EVAPO_RATE);
				foodPheromones[i][j] = new PheromoneLocation(EVAPO_RATE);
			}
		}		
	}

	/**
	 * @param neighbors
	 * @param goingHome
	 * @param maxAnts
	 * @param callingCell
	 * @return Returns a List of Integers which function as coordinates for the 
	 * AntCell's next location.
	 * This method uses an AntCell's neighbors, objective, and maxAnts parameter 
	 * along with ForagingLand's information about pheromones in order to determine
	 * where the AntCell which called this method, callingCell, will go next.
	 */
	public List<Integer> getNewCoordinates(List<Cell> neighbors,boolean goingHome, double maxAnts, Cell callingCell) {
		List<Integer> coordinates = new ArrayList<Integer>(2);
		int homePher; int foodPher; int max = 0;
		boolean moved = false;
		ArrayList<Cell> possibleDirections = new ArrayList<Cell>();
		for(Cell c:neighbors) {
			homePher = homePheromones[c.getRow()][c.getCol()].getNumPheromones();
			foodPher = foodPheromones[c.getRow()][c.getCol()].getNumPheromones();
			boolean tooManyAnts = false;
			if(c instanceof AntGroupCell) {
				tooManyAnts =  ((AntGroupCell) c).getNumAnts()>=maxAnts;
			}
			if(!tooManyAnts) {
				if(goingHome) {
					max = updateMaxList(possibleDirections,homePher, max, c);
				}
				else {
					max = updateMaxList(possibleDirections,foodPher, max, c);
				}			
				moved = true;
			}							
		}
		if(!moved) {
			setCellCoordinates(coordinates,callingCell);
		}
		if(possibleDirections.size()!=0) {
			chooseRandomCoordinatesFromMaxCells(coordinates,possibleDirections);	
		}
		else {
			setCellCoordinates(coordinates,callingCell);
		}
		return coordinates;
	}

	/**
	 * @param coord
	 * @param possDir
	 * This method edits a list functioning as coordinates by choosing the row and column
	 * of a cell randomly chosen from a list of cells in possible directions for the AntCell 
	 * to go in.
	 */
	private void chooseRandomCoordinatesFromMaxCells(List<Integer> coord, ArrayList<Cell> possDir) {
		int numPossCells = possDir.size();
		Random rand = new Random();
		Cell newLoc = possDir.get(rand.nextInt(numPossCells));
		setCellCoordinates(coord,newLoc);		
	}

	/**
	 * @param possDir
	 * @param numPher
	 * @param max
	 * @param c
	 * @return This method returns the max number of pheromones encountered in a neighbor
	 * while also updating the list of possible direction cells to include Cell c if the
	 * number of pheromones in its location is greater than or equal to the preexisting max.
	 */
	protected int updateMaxList(List<Cell> possDir, int numPher, int max, Cell c) {
		if(numPher==max) {
			possDir.add(c);
		}
		if(numPher>max) {
			possDir.clear();
			possDir.add(c);
			max = numPher; 
		}
		return max;
	}

	/**
	 * @param coordinates
	 * @param c
	 * This method edits a List of Integers so that its first 2 numbers
	 * are the coordinates of a Cell c.
	 */
	private void setCellCoordinates(List<Integer> coordinates, Cell c) {
		coordinates.add(0, c.getRow());
		coordinates.add(1, c.getCol());
	}
	
	/**
	 * @param row
	 * @param col
	 * @return Returns a boolean describing whether the location described by
	 * row and col is home
	 */
	public boolean atHome(int row, int col) {
		return row==homeLoc[0] & col==homeLoc[1];
	}
	
	/**
	 * @param row
	 * @param col
	 * @return Returns a boolean describing whether the location described by
	 * row and col is food source.
	 */
	public boolean atFoodSource(int row, int col) {
		return row==foodLoc[0] & col==foodLoc[1];
	}
	
	/**
	 * @param row
	 * @param col
	 * @param hasFood
	 * @param neighbors
	 * This method takes in a location, the objective of the AntCell calling the method,
	 * and a list of the AntCell's neighbors so that it can determine how many pheromones
	 * to add to the location.
	 */
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
	
	/**
	 * @param pher
	 * @param neighbors
	 * @return This method takes in the grid for either home or food pheromones,
	 * and uses an AntCell's neighbors to determine how many pheromones are desired
	 * at the AntCell's location.
	 */
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

	/**
	 * This method causes the pheromones at each location to evaporate after a certain point.
	 */
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

package gridPatches;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Madhavi
 * This class is used to keep track of pheromones at a certain location. It is encapsulated
 * within ForagingLand. It keeps track of how many pheromones there are at the location,
 * and how old each of them is, so that when they get too old, they evaporate.
 */
public class PheromoneLocation {

private List<Integer> pheromones;
private int evapoRate;

	/**
	 * @param evap
	 * An instance of this class is instantiated by initializing evapoRate, the number
	 * of turns a pheromone lasts before evaporating. The class also contains a list
	 * of integers to keep track of each individual pheromones and its age.
	 */
	public PheromoneLocation(int evap){		
		pheromones = new ArrayList<Integer>();
		evapoRate = evap;
	}
	
	 /**
	 * This method adds a new pheromone to the list of integers. The integer 
	 * symbolizes how old the pheromone is, which is why as a new pheromone is added
	 * it's added as a 0.
	 */
	protected void addNew(){
		 pheromones.add(0);
	}
	 
	/**
	 * This method ages each pheromone with each time step, and removes pheromones
	 * which have evaporated.
	 */
	protected void evaporate() {
		for(Integer i: pheromones) {
			i++;
			if(i>=evapoRate) {
				pheromones.remove(i);
			}
		}
	}
	
	/**
	 * @return Returns the number of pheromones at a PheromoneLocation
	 */
	protected int getNumPheromones() {
		return pheromones.size();
	}
}

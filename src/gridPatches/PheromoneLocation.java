package gridPatches;

import java.util.ArrayList;
import java.util.List;

public class PheromoneLocation {

private List<Integer> pheromones;
private int evapoRate;

	public PheromoneLocation(int evap){		
		pheromones = new ArrayList<Integer>();
		evapoRate = evap;
	}
	
	 protected void addNew(){
		 pheromones.add(0);
	}
	 
	protected void evaporate() {
		for(Integer i: pheromones) {
			i++;
			if(i>=evapoRate) {
				pheromones.remove(i);
			}
		}
	}
	
	protected int getNumPheromones() {
		return pheromones.size();
	}
}

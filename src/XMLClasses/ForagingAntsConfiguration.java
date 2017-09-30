package XMLClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForagingAntsConfiguration extends GridConfiguration {

	public ForagingAntsConfiguration(Map<String, String> dataValues) {
		super(dataValues);
	}
	// returns number of max ants for Foraging Ant simulation
    public double getMaxAnts () {
        return Double.parseDouble(GridConfiguration.getMyDataValues().get("maxAnts"));
    }
    // returns number of turns shark breed for Wator simulation
    public double getEvaporationRate () {
        return Double.parseDouble(GridConfiguration.getMyDataValues().get("evaporationRate"));
    }
    // returns number of turns shark starve for Wator simulation
    public int[] getHomeLoc () {
        
    	String[] coordinatesString = GridConfiguration.getMyDataValues().get("homeLoc").split("\\a");
    	int[] coordinates = new int[coordinatesString.length];
    	for(int i = 0;i<coordinatesString.length;i++)
    	{
    		coordinates[i] = Integer.parseInt(coordinatesString[i]);
    	}
    	return coordinates;
    }
    
public int[] getFoodLoc () {
        
    	String[] coordinatesString = GridConfiguration.getMyDataValues().get("foodLoc").split("\\a");
    	int[] coordinates = new int[coordinatesString.length];
    	for(int i = 0;i<coordinatesString.length;i++)
    	{
    		coordinates[i] = Integer.parseInt(coordinatesString[i]);
    	}
    	return coordinates;
    }


}
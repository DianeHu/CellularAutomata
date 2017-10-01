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
    
    // returns number of turns shark starve for Wator simulation
    public int getHomeLocX () {
        
    	return Integer.parseInt(GridConfiguration.getMyDataValues().get("homeLocX"));
    }
    public int getHomeLocY () {
        
    	return Integer.parseInt(GridConfiguration.getMyDataValues().get("homeLocY"));
    }
    
    public int getFoodLocX () {
        
    	return Integer.parseInt(GridConfiguration.getMyDataValues().get("foodLocX"));
    }
    public int getFoodLocY () {
        
    	return Integer.parseInt(GridConfiguration.getMyDataValues().get("foodLocY"));
    }


}
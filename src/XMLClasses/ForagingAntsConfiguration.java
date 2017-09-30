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
    public int[] getForagingLandX () {
        
    	String[] xCoordinatesString = GridConfiguration.getMyDataValues().get("foragingLandX").split("\\a");
    	int[] xCoordinates = new int[xCoordinatesString.length];
    	for(int i = 0;i<xCoordinatesString.length;i++)
    	{
    		xCoordinates[i] = Integer.parseInt(xCoordinatesString[i]);
    	}
    	return xCoordinates;
    }
    
public int[] getForagingLandY () {
        
    	String[] yCoordinatesString = GridConfiguration.getMyDataValues().get("foragingLandY").split("\\a");
    	int[] yCoordinates = new int[yCoordinatesString.length];
    	for(int i = 0;i<yCoordinatesString.length;i++)
    	{
    		yCoordinates[i] = Integer.parseInt(yCoordinatesString[i]);
    	}
    	return yCoordinates;
    }


}
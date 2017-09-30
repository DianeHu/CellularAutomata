package XMLClasses;

import java.util.Map;

public class SpreadingWildfireConfiguration extends GridConfiguration{

	public SpreadingWildfireConfiguration(Map<String, String> dataValues) {
		super(dataValues);
	}
	
	// returns probability of catching on fire for BurningTree simulation
    public double getProbCatch () {
        return Double.parseDouble(GridConfiguration.getMyDataValues().get("probCatch"));
    }
    // returns probability of a tree regrowing for BurningTree simulation
    public double getProbGrow () {
        return Double.parseDouble(GridConfiguration.getMyDataValues().get("probGrow"));
    }

}

package XMLClasses;

import java.util.Map;

public class WatorConfiguration extends GridConfiguration {

	public WatorConfiguration(Map<String, String> dataValues) {
		super(dataValues);
	}
	// returns number of turns fish breed for Wator simulation
    public double getFishBreedTurns () {
        return Double.parseDouble(GridConfiguration.getMyDataValues().get("fishBreedTurns"));
    }
    // returns number of turns shark breed for Wator simulation
    public double getSharkBreedTurns () {
        return Double.parseDouble(GridConfiguration.getMyDataValues().get("sharkBreedTurns"));
    }
    // returns number of turns shark starve for Wator simulation
    public double getSharkStarveTurns () {
        return Double.parseDouble(GridConfiguration.getMyDataValues().get("sharkStarveTurns"));
    }


}

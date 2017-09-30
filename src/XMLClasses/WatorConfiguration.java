package XMLClasses;

import java.util.Map;

public class WatorConfiguration extends GridConfiguration {

	public WatorConfiguration(Map<String, String> dataValues) {
		super(dataValues);
		GridConfiguration.addToDataFields("fishBreedTurns");
		GridConfiguration.addToDataFields("sharkBreedTurns");
		GridConfiguration.addToDataFields("sharkStarveTurns");
	}
	// returns number of turns fish breed for Wator simulation
    public int getFishBreedTurns () {
        return Integer.parseInt(getMyDataValues().get("fishBreedTurns"));
    }
    // returns number of turns shark breed for Wator simulation
    public int getSharkBreedTurns () {
        return Integer.parseInt(getMyDataValues().get("sharkBreedTurns"));
    }
    // returns number of turns shark starve for Wator simulation
    public int getSharkStarveTurns () {
        return Integer.parseInt(getMyDataValues().get("sharkStarveTurns"));
    }


}

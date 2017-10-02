package XMLClasses;

import java.util.Map;

/**
 * @author Tyler Yam This is a subclass of the GridConfiguration superclass for
 *         the Wator simulation This class sets up how the XML file should be
 *         formatted for the Wator simulation and allow those values to be
 *         accessed by the back end
 */
public class WatorConfiguration extends GridConfiguration {

	/**
	 * @param dataValues
	 *            Constructor for WatorConfiguration
	 */
	public WatorConfiguration(Map<String, String> dataValues) {
		super(dataValues);
	}

	/**
	 * @return This is a getter for the number of turns fish breed for Wator
	 *         simulation
	 */
	public double getFishBreedTurns() {
		return Double.parseDouble(GridConfiguration.getMyDataValues().get("fishBreedTurns"));
	}

	/**
	 * @return This is a getter for the number of turns shark breed for Wator
	 *         simulation
	 */
	public double getSharkBreedTurns() {
		return Double.parseDouble(GridConfiguration.getMyDataValues().get("sharkBreedTurns"));
	}

	/**
	 * @return This is a getter for the number of turns shark starve for Wator
	 *         simulation
	 */
	public double getSharkStarveTurns() {
		return Double.parseDouble(GridConfiguration.getMyDataValues().get("sharkStarveTurns"));
	}

}

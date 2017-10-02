package XMLClasses;

import java.util.Map;

/**
 * @author Tyler Yam This is a subclass of the GridConfiguration superclass for
 *         the Foraging Ants simulation This class sets up how the XML file
 *         should be formatted for the Foraging Ants simulation and allow those
 *         values to be accessed by the back end
 */
public class ForagingAntsConfiguration extends GridConfiguration {

	/**
	 * @param dataValues
	 *            Constructor for ForagingAntsConfiguration
	 */
	public ForagingAntsConfiguration(Map<String, String> dataValues) {
		super(dataValues);
	}

	/**
	 * @return Returns the maxAnts for Foraging Ant simulation
	 */
	public double getMaxAnts() {
		return Double.parseDouble(GridConfiguration.getMyDataValues().get("maxAnts"));
	}

	/**
	 * @return Returns X location for home for Foraging Ant simulation
	 */
	public int getHomeLocX() {

		return Integer.parseInt(GridConfiguration.getMyDataValues().get("homeLocX"));
	}

	/**
	 * @return Returns Y location for home for Foraging Ant simulation
	 */
	public int getHomeLocY() {

		return Integer.parseInt(GridConfiguration.getMyDataValues().get("homeLocY"));
	}

	/**
	 * @return Returns X location for food for Foraging Ant simulation
	 */
	public int getFoodLocX() {

		return Integer.parseInt(GridConfiguration.getMyDataValues().get("foodLocX"));
	}

	/**
	 * @return Returns Y location for food for Foraging Ant simulation
	 */
	public int getFoodLocY() {

		return Integer.parseInt(GridConfiguration.getMyDataValues().get("foodLocY"));
	}

}
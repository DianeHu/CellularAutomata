package XMLClasses;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Tyler Yam This is a subclass of the GridConfiguration superclass for
 *         the Spreading Wildfire simulation This class sets up how the XML file
 *         should be formatted for the Spreading Wildfire simulation and allow
 *         those values to be accessed by the back end
 */
public class SpreadingWildfireConfiguration extends GridConfiguration {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/XMLLabels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	/**
	 * @param dataValues
	 *            Constructor for SpreadingWildfireConfiguration
	 */
	public SpreadingWildfireConfiguration(Map<String, String> dataValues) {
		super(dataValues);
	}

	/**
	 * @return Returns probability of catching on fire for BurningTree simulation
	 */
	public double getProbCatch() {
		return Double.parseDouble(GridConfiguration.getMyDataValues().get(myResources.getString("probCatch")));
	}

	/**
	 * @return Returns probability of a tree regrowing for BurningTree simulation
	 */
	public double getProbGrow() {
		return Double.parseDouble(GridConfiguration.getMyDataValues().get(myResources.getString("probGrow")));
	}

}

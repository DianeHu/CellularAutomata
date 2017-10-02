package XMLClasses;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Tyler Yam This is a subclass of the GridConfiguration superclass for
 *         the Segregation simulation This class sets up how the XML file should
 *         be formatted for the Segregation simulation and allow those values to
 *         be accessed by the back end
 */
public class SegregationConfiguration extends GridConfiguration {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/XMLLabels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	/**
	 * @param dataValues
	 *            Constructor for SegregationConfiguration
	 */
	public SegregationConfiguration(Map<String, String> dataValues) {
		super(dataValues);

	}

	/**
	 * @return This is a getter for Segregation Threshold
	 */
	public double getSegregationThreshold() {
		return Double
				.parseDouble(GridConfiguration.getMyDataValues().get(myResources.getString("segregationThreshold")));
	}

}

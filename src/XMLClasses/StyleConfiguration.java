package XMLClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Tyler Yam Based on code from Robert C. Duvall This class is used to
 *         store data from an XML to be used for Style configurations
 */
public class StyleConfiguration {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/XMLLabels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	public static final String DATA_TYPE = myResources.getString("StyleConfiguration");
	public static List<String> myDataFields = new ArrayList<>(
			Arrays.asList(new String[] { myResources.getString("gridShape"), myResources.getString("edgeShape"),
					myResources.getString("neighborType") }));
	private static Map<String, String> myDataValues;

	/**
	 * @param dataValues
	 *            Constructor for StyleConfiguration
	 */
	public StyleConfiguration(Map<String, String> dataValues) {
		myDataValues = dataValues;
	}

	/**
	 * @return This is a getter for the data values for the StyleConfiguration
	 */
	public static Map<String, String> getMyDataValues() {
		return myDataValues;
	}

	/**
	 * @return This is a getter for the Grid shape
	 */
	public String getGridShape() {
		return getMyDataValues().get(myResources.getString("gridShape"));
	}

	/**
	 * @return This is a getter for the Edge shape
	 */
	public String getEdgeShape() {
		return getMyDataValues().get(myResources.getString("edgeShape"));
	}

	/**
	 * @return This is a getter for the neighbor type
	 */
	public String getNeighborType() {
		return getMyDataValues().get(myResources.getString("neighborType"));
	}
}
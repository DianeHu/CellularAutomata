package XMLClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Tyler Yam Based on code from Robert C. Duvall This class is used to
 *         store data from an XML to be used for Style configurations
 */
public class StyleConfiguration {

	public static final String DATA_TYPE = "StyleConfiguration";
	public static List<String> myDataFields = new ArrayList<>(
			Arrays.asList(new String[] { "gridShape", "edgeShape", "neighborType" }));
	private static Map<String, String> myDataValues;

	/**
	 * @param dataValues
	 * Constructor for StyleConfiguration
	 */
	public StyleConfiguration(Map<String, String> dataValues) {
		myDataValues = dataValues;
	}

	/**
	 * @return
	 * This is a getter for the data values for the StyleConfiguration
	 */
	public static Map<String, String> getMyDataValues() {
		return myDataValues;
	}


	/**
	 * @return
	 * This is a getter for the Grid shape
	 */
	public String getGridShape() {
		return getMyDataValues().get("gridShape");
	}

	/**
	 * @return
	 * This is a getter for the Edge shape
	 */
	public String getEdgeShape() {
		return getMyDataValues().get("edgeShape");
	}


	/**
	 * @return
	 * This is a getter for the neighbor type
	 */
	public String getNeighborType() {
		return getMyDataValues().get("neighborType");
	}
}
package XMLClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Tyler Yam Based on code from Robert C. Duvall. This is a super class
 *         that stores data from an XML to be used for grid configurations
 */
public abstract class GridConfiguration {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/XMLLabels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	public static final String DATA_TYPE = (myResources.getString("GridConfiguration"));
	private static Map<String, String> myDataValues;

	private static List<String> myDataFields = new ArrayList<>(
			Arrays.asList(new String[] { myResources.getString("numRows"), myResources.getString("numCols"),
					myResources.getString("cellConfiguration") }));

	/**
	 * @param dataValues
	 *            Constructor for GridConfiguration
	 */
	public GridConfiguration(Map<String, String> dataValues) {
		myDataValues = dataValues;
	}

	/**
	 * @return This is a getter method for the data fields
	 */
	public static List<String> getMyDataFields() {
		return myDataFields;
	}

	/**
	 * @param s
	 *            This is a method to add the data fields specific to the subclasses
	 *            to the overall GridConfiguration
	 */
	public static void addToDataFields(List<String> s) {
		getMyDataFields().addAll(s);
	}

	/**
	 * @param s
	 *            This is a method to remove the data fields specific to the
	 *            subclasses to the overall GridConfiguration so that different
	 *            configurations can be created and run at the same time.
	 */
	public static void removeFromDataFields(List<String> s) {
		getMyDataFields().removeAll(s);
	}

	/**
	 * @return This is a getter for number of rows for overall GridConfiguration
	 */
	public int getNumRows() {
		return Integer.parseInt(getMyDataValues().get(myResources.getString("numRows")));
	}

	/**
	 * @return This is a getter for the number of columns for the overall
	 *         GridConfiguration
	 */
	public int getNumCols() {
		return Integer.parseInt(getMyDataValues().get(myResources.getString("numCols")));
	}

	/**
	 * @return This is a getter for the cell configuration using an input from the
	 *         XML as one string and converts it into a 2d Character array
	 */
	public char[][] getCellConfiguration() {
		char[][] cellConfiguration = new char[getNumRows()][getNumCols()];
		List<Character> cellChars = new ArrayList<Character>();
		for (char c : getMyDataValues().get(myResources.getString("cellConfiguration")).toCharArray()) {
			cellChars.add(c);
		}
		int count = 0;
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumCols(); j++) {

				cellConfiguration[i][j] = cellChars.get(count);
				count++;
			}
		}
		return cellConfiguration;
	}

	/**
	 * @return This is a getter for the data values from the XML files
	 */
	public static Map<String, String> getMyDataValues() {
		return myDataValues;
	}
}

package XMLClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class GridConfiguration {
	/**
	 * @author Tyler Yam Based on code from Robert C. Duvall This class is used to
	 *         store data from an XML to be used for grid configurations
	 */
	// Data file name to represent this object type
	public static final String DATA_TYPE = "GridConfiguration";
	private static Map<String, String> myDataValues;
	
	// List of names of data fields
	private static List<String> myDataFields = new ArrayList<>(
			Arrays.asList(new String[] { "numRows", "numCols", "cellConfiguration" }));

	public GridConfiguration(Map<String, String> dataValues) {
		myDataValues = dataValues;
	}

	public static List<String> getMyDataFields() {
		return myDataFields;
	}

	public static void addToDataFields(List<String> s) {
		getMyDataFields().addAll(s);
	}

	public static void removeFromDataFields(List<String> s) {
		getMyDataFields().removeAll(s);
	}

	// returns number of rows
	public int getNumRows() {
		return Integer.parseInt(getMyDataValues().get("numRows"));
	}

	// returns number of columns
	public int getNumCols() {
		return Integer.parseInt(getMyDataValues().get("numCols"));
	}

	// returns 2D array for cells placement in the grid
	public char[][] getCellConfiguration() {
		char[][] cellConfiguration = new char[getNumRows()][getNumCols()];
		List<Character> cellChars = new ArrayList<Character>();
		for (char c : getMyDataValues().get("cellConfiguration").toCharArray()) {
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

	public static Map<String, String> getMyDataValues() {
		return myDataValues;
	}
}

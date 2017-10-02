package XMLClasses;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

/**
 * @author Tyler Yam This is a subclass of the XMLReader for the Foraging Ants
 *         simulation This class allows a data file for the Foraging Ants
 *         simulation to be read as a specific XML format
 */
public class ForagingAntsReader extends XMLReader {

	/**
	 * This is a constructor for the ForagingAntsReader
	 */
	public ForagingAntsReader() {
		super();
	}

	/**
	 * @param dataFile
	 * @return This method returns a ForagingAntsConfiguration with data values
	 *         taken from the data file input
	 */
	public ForagingAntsConfiguration getGridConfiguration(File dataFile) {
		Element root = getRootElement(dataFile);
		List<String> ForagingAntsThresholds = new ArrayList<>(
				Arrays.asList(new String[] { "maxAnts", "homeLocX", "homeLocY", "foodLocX", "foodLocY", }));
		if (!isValidFile(root, GridConfiguration.DATA_TYPE)) {
			throw new XMLException("XML file does not represent %s", GridConfiguration.DATA_TYPE);
		}

		Map<String, String> results = new HashMap<>();
		GridConfiguration.addToDataFields(ForagingAntsThresholds);
		for (String field : GridConfiguration.getMyDataFields()) {
			results.put(field, getTextValue(root, field));
		}
		GridConfiguration.removeFromDataFields(ForagingAntsThresholds);
		return new ForagingAntsConfiguration(results);
	}
}

package XMLClasses;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.w3c.dom.Element;

public class WatorReader extends XMLReader {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/XMLLabels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	public WatorReader() {
		super();
	}

	public WatorConfiguration getGridConfiguration(File dataFile) {
		List<String> watorThresholds = new ArrayList<>(
				Arrays.asList(new String[] { myResources.getString("fishBreedTurns"),
						myResources.getString("sharkBreedTurns"), myResources.getString("sharkStarveTurns") }));
		Element root = getRootElement(dataFile);
		if (!isValidFile(root, GridConfiguration.DATA_TYPE)) {
			throw new XMLException("XML file does not represent %s", GridConfiguration.DATA_TYPE);
		}

		Map<String, String> results = new HashMap<>();
		GridConfiguration.addToDataFields(watorThresholds);
		for (String field : GridConfiguration.getMyDataFields()) {
			results.put(field, getTextValue(root, field));
		}
		GridConfiguration.removeFromDataFields(watorThresholds);
		return new WatorConfiguration(results);
	}
}

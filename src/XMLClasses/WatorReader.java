package XMLClasses;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

public class WatorReader extends XMLReader {
	public WatorReader() {
		super();
	}

	public WatorConfiguration getGridConfiguration(File dataFile) {
		List<String> watorThresholds = new ArrayList<>(
				Arrays.asList(new String[] { "fishBreedTurns", "sharkBreedTurns", "sharkStarveTurns" }));
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

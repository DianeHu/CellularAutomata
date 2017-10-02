package XMLClasses;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

public class GameOfLifeReader extends XMLReader {
	public GameOfLifeReader() {
		super();
	}

	public GameOfLifeConfiguration getGridConfiguration(File dataFile) {
		Element root = getRootElement(dataFile);
		if (!isValidFile(root, GridConfiguration.DATA_TYPE)) {
			throw new XMLException("XML file does not represent %s", GridConfiguration.DATA_TYPE);
		}
		
		Map<String, String> results = new HashMap<>();
		for (String field : GridConfiguration.getMyDataFields()) {
			results.put(field, getTextValue(root, field));
		}
		return new GameOfLifeConfiguration(results);
	}
}

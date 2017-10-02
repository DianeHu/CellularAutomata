package XMLClasses;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

public class StyleReader extends XMLReader {
	public StyleReader() {
		super();
	}

	public StyleConfiguration getStyleConfiguration(File dataFile) {
		Element root = getRootElement(dataFile);
		if (!isValidFile(root, StyleConfiguration.DATA_TYPE)) {
			throw new XMLException("XML file does not represent %s", StyleConfiguration.DATA_TYPE);
		}

		Map<String, String> results = new HashMap<>();
		for (String field : StyleConfiguration.myDataFields) {
			results.put(field, getTextValue(root, field));
		}
		return new StyleConfiguration(results);
	}

}
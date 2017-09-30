package XMLClasses;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

public class ForagingAntsReader extends XMLReader {
	public ForagingAntsReader()
	{
		super();
	}
	public ForagingAntsConfiguration getGridConfiguration (File dataFile) {
        Element root = getRootElement(dataFile);
        if (! isValidFile(root, GridConfiguration.DATA_TYPE)) {
            throw new XMLException("XML file does not represent %s", GridConfiguration.DATA_TYPE);
        }
        // Stores the data with its field for gridConfiguration
        Map<String, String> results = new HashMap<>();
        GridConfiguration.addToDataFields("maxAnts");
		GridConfiguration.addToDataFields("evaporationRate");
		GridConfiguration.addToDataFields("foragingLandsX");
		GridConfiguration.addToDataFields("foragingLandsY");
        for (String field : GridConfiguration.myDataFields) {
            results.put(field, getTextValue(root, field));
        }
       return new ForagingAntsConfiguration(results);
    }
}

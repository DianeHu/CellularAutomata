package XMLClasses;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

public class ForagingAntsReader extends XMLReader {
	public ForagingAntsReader()
	{
		super();
	}
	public ForagingAntsConfiguration getGridConfiguration (File dataFile) {
        Element root = getRootElement(dataFile);
        List<String> ForagingAntsThresholds = new ArrayList<>(Arrays.asList(new String[]{
				"maxAnts",
		        "homeLocX",
		        "homeLocY",
		        "foodLocX",
		        "foodLocY",
		    }));
        if (! isValidFile(root, GridConfiguration.DATA_TYPE)) {
            throw new XMLException("XML file does not represent %s", GridConfiguration.DATA_TYPE);
        }
        // Stores the data with its field for gridConfiguration
        Map<String, String> results = new HashMap<>();
        GridConfiguration.addToDataFields(ForagingAntsThresholds );
        for (String field : GridConfiguration.getMyDataFields()) {
            results.put(field, getTextValue(root, field));
        }
        GridConfiguration.removeFromDataFields(ForagingAntsThresholds );
        return new ForagingAntsConfiguration(results);
    }
}

package XMLClasses;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StyleConfiguration {
	/**
	 * @author Tyler Yam
	 * Based on code from Robert C. Duvall
	 * This class is used to store data from an XML to be used for grid configurations
	 */
	 // Data file name to represent this object type
    public static final String DATA_TYPE = "StyleConfiguration";
    // List of names of data fields
    public static List<String> myDataFields = new ArrayList<>(Arrays.asList(new String[]{
    	"gridShape",
        "edgeShape",
        "neighborType"
    }));

    // Stores actual data values
    private static Map<String, String> myDataValues;


    public StyleConfiguration (Map<String, String> dataValues) {
        myDataValues=dataValues;
    }
    
    public static Map<String, String> getMyDataValues() {
		return myDataValues;
	}
    
    // returns shape of grid
    public String getGridShape () {
        return getMyDataValues().get("gridShape");
    }
    // returns shape of grid
    public String getEdgeShape () {
        return getMyDataValues().get("edgeShape");
    }
    
    // returns shape of grid
    public String getNeighborType () {
        return getMyDataValues().get("neighborType");
    }
}    
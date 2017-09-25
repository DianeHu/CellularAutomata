package XMLClasses;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GridConfiguration {
	/**
	 * @author Tyler Yam
	 * Based on code from Robert C. Duvall
	 * This class is used to store data from an XML to be used for grid configurations
	 */
	 // Data file name to represent this object type
    public static final String DATA_TYPE = "GridConfiguration";
    // List of names of data fields
    public static final List<String> DATA_FIELDS = Arrays.asList(new String[] {
        "simulationType",
    	"numRows",
        "numCols",
        "cellConfiguration",
        "probCatch",
        "probGrow",
        "segregationThreshold",
        "fishBreedTurns",
        "sharkBreedTurns",
        "sharkStarveTurns",
    });

    // Stores actual data values
    private Map<String, String> myDataValues;


    public GridConfiguration (Map<String, String> dataValues) {
        myDataValues = dataValues;
    }

    // returns simulation type
    public String getSimulationType () {
        return myDataValues.get(DATA_FIELDS.get(0));
    }
    // returns number of rows
    public int getNumRows () {
        return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(1)));
    }
    // returns number of columns
    public int getNumCols () {
        return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(2)));
    }
    // returns 2D array for cells placement in the grid
    public char[][] getCellConfiguration () {
        char[][] cellConfiguration = new char[getNumRows()][getNumCols()];
    	List<Character> cellChars = new ArrayList<Character>();
    	for(char c: myDataValues.get(DATA_FIELDS.get(3)).toCharArray())
    	{
    		cellChars.add(c);
    	}
    	int count = 0;
        for(int i = 0;i<getNumRows();i++)
    	{
    		for(int j = 0; j<getNumCols();j++)
    		{
    			
    			cellConfiguration[i][j] = cellChars.get(count);
    			count++;
    		}
    	}
    	return cellConfiguration;
    }
    // returns probability of catching on fire for BurningTree simulation
    public double getProbCatch () {
        return Double.parseDouble(myDataValues.get(DATA_FIELDS.get(4)));
    }
    // returns probability of a tree regrowing for BurningTree simulation
    public double getProbGrow () {
        return Double.parseDouble(myDataValues.get(DATA_FIELDS.get(5)));
    }
    // returns threshold value for segregation simulation
    public double getSegregationThreshold () {
        return Double.parseDouble(myDataValues.get(DATA_FIELDS.get(6)));
    }
    // returns number of turns fish breed for Wator simulation
    public int getFishBreedTurns () {
        return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(7)));
    }
    // returns number of turns shark breed for Wator simulation
    public int getSharkBreedTurns () {
        return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(8)));
    }
    // returns number of turns shark starve for Wator simulation
    public int getSharkStarveTurns () {
        return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(9)));
    }
}

package XMLClasses;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GridConfiguration {
	
	 // name in data file that will indicate it represents data for this type of object
    public static final String DATA_TYPE = "GridConfiguration";
    // field names expected to appear in data file holding values for this object
    // simple way to create an immutable list
    public static final List<String> DATA_FIELDS = Arrays.asList(new String[] {
        "numRows",
        "numCols",
        "cellConfiguration",
    });

    // specific data values for this instance
    private Map<String, String> myDataValues;


    public GridConfiguration (Map<String, String> dataValues) {
        myDataValues = dataValues;
    }

    // provide alternate ways to access data values if needed
    public int getNumRows () {
        return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(0)));
    }

    public int getNumCols () {
        return Integer.parseInt(myDataValues.get(DATA_FIELDS.get(1)));
    }

    public char[][] getCellConfiguration () {
        char[][] cellConfiguration = new char[getNumRows()][getNumCols()];
    	List<Character> cellChars = new ArrayList<Character>();
    	for(char c: myDataValues.get(DATA_FIELDS.get(2)).toCharArray())
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

    @Override
    public String toString () {
        StringBuilder result = new StringBuilder();
        result.append("GridConfiguration {\n");
        for (Map.Entry<String, String> e : myDataValues.entrySet()) {
            result.append("  "+e.getKey()+"='"+e.getValue()+"',\n");
        }
        result.append("}\n");
        return result.toString();
    }
}

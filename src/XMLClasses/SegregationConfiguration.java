package XMLClasses;

import java.util.Map;

public class SegregationConfiguration extends GridConfiguration {

	public SegregationConfiguration(Map<String, String> dataValues) {
		super(dataValues);
	}
	// returns threshold value for segregation simulation
    public double getSegregationThreshold () {
        return Double.parseDouble(GridConfiguration.getMyDataValues().get("segregationThreshold"));
    }

}

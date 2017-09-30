package XMLClasses;

import java.util.Map;

public class SegregationConfiguration extends GridConfiguration {

	public SegregationConfiguration(Map<String, String> dataValues) {
		super(dataValues);
		GridConfiguration.addToDataFields("segregationThreshold");
	}
	// returns threshold value for segregation simulation
    public double getSegregationThreshold () {
        return Double.parseDouble(getMyDataValues().get("segregationThreshold"));
    }

}

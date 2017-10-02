package XMLClasses;

import java.util.Map;

/**
 * @author Tyler Yam This is a subclass of the GridConfiguration superclass for
 *         the RPS simulation This class sets up how the XML file should be
 *         formatted for the RPS simulation and allow those values to be
 *         accessed by the back end
 */
public class RPSConfiguration extends GridConfiguration {

	/**
	 * @param dataValues
	 *            Constructor for RPSConfiguration
	 */
	public RPSConfiguration(Map<String, String> dataValues) {
		super(dataValues);
	}

}

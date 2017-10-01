package XMLClasses;

import java.util.Map;

/**
 * @author Tyler Yam
 * This is a subclass of the GridConfiguration superclass for the Game of Life simulation
 * This class sets up how the XML file should be formatted for the Game of Life simulation
 * and allow those values to be accessed by the back end
 */
public class GameOfLifeConfiguration extends GridConfiguration {

	/**
	 * @param dataValues
	 * Constructor for Game of Life
	 */
	public GameOfLifeConfiguration(Map<String, String> dataValues) {
		super(dataValues);
	}

}

package simulationDrivers;

import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Tyler Yam This class creates the error messages that is implemented
 *         throughout all of program
 */
public class ErrorMessages {
	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
	
	/**
	 * @param message
	 *            This method creates the error with the error message passed in as
	 *            a string to portray the error
	 */
	public static void createErrors(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myResources.getString("error"));
		alert.setContentText(message);
		alert.showAndWait();
	}
}

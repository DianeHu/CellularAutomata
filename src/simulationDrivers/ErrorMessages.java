package simulationDrivers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author Tyler Yam This class creates the error messages that is implemented
 *         throughout all of program
 */
public class ErrorMessages {

	/**
	 * @param message
	 *            This method creates the error with the error message passed in as
	 *            a string to portray the error
	 */
	public static void createErrors(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(message);
		alert.showAndWait();
	}
}

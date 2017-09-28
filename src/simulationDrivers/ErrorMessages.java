package simulationDrivers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorMessages {
	
	public static void createErrors(String message)
	{
		Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("ErrorTitle");
	    alert.setContentText(message);
	    alert.showAndWait();
	}

}

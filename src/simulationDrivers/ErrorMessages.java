package simulationDrivers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorMessages {
	
	public static void createErrors(String message)
	{
		Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Error");
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	public static void createWarning(String message)
	{
		Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle("WarningTitle");
	    alert.setContentText(message);
	    alert.showAndWait();
	}

}

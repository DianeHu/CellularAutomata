package simulationDrivers;

import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorMessages {
	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
	
	public static void createErrors(String message)
	{
		Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle(myResources.getString("error"));
	    alert.setContentText(message);
	    alert.showAndWait();
	}
	public static void createWarning(String message)
	{
		Alert alert = new Alert(AlertType.WARNING);
	    alert.setTitle(myResources.getString("warning"));
	    alert.setContentText(message);
	    alert.showAndWait();
	}

}

package simulationDrivers;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Diane Hu
 * @author Tyler Yam
 * 
 *         Class for creating buttons used in simulation main class.
 */
public class SimulationButtons {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	public static void makeButtonV(String name, EventHandler<ActionEvent> handler, VBox vbox, int screenSize) {
		Button sampleButton = new Button(name);
		sampleButton.setOnAction(handler);
		vbox.getChildren().add(sampleButton);
		sampleButton.setTranslateY(screenSize);

	}
	
	public static void makeTextField(String content, VBox vbox, int screenSize) {
		TextField sampleText = new TextField();
		sampleText.setPromptText(content);
		vbox.getChildren().add(sampleText);
		sampleText.setTranslateY(screenSize);
	}

	public static void makeButtonH(String name, EventHandler<ActionEvent> handler, HBox hbox, int screenSize) {
		Button sampleButton = new Button(name);
		sampleButton.setOnAction(handler);
		hbox.getChildren().add(sampleButton);
	}

}
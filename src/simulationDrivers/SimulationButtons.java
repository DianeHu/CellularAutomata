package simulationDrivers;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Diane Hu
 * @author Tyler Yam Class for creating buttons used in simulation main class.
 */
public class SimulationButtons {

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	/**
	 * @param name
	 * @param handler
	 * @param vbox
	 * @param screenSize
	 *            This method creates buttons that are added to a VBox that does not
	 *            need to be stored in the main or front end classes
	 */
	public static void makeButtonV(String name, EventHandler<ActionEvent> handler, VBox vbox, int screenSize) {
		Button sampleButton = makeReturnableButtonV(name, handler, vbox, screenSize);
	}

	/**
	 * @param name
	 * @param handler
	 * @param hbox
	 * @param screenSize
	 *            This method creates buttons that are added to a HBox that does not
	 *            need to be stored in the main or front end classes
	 */
	public static void makeButtonH(String name, EventHandler<ActionEvent> handler, HBox hbox, int screenSize) {
		Button sampleButton = makeReturnableButtonH(name, handler, hbox, screenSize);
	}

	/**
	 * @param name
	 * @param handler
	 * @param vbox
	 * @param screenSize
	 * @return This method creates buttons that are added to a VBox that need to be
	 *         stored in the main or front end classes
	 */
	public static Button makeReturnableButtonV(String name, EventHandler<ActionEvent> handler, VBox vbox,
			int screenSize) {
		Button sampleButton = new Button(name);
		sampleButton.setOnAction(handler);
		vbox.getChildren().add(sampleButton);
		sampleButton.setTranslateY(screenSize);
		return sampleButton;
	}

	/**
	 * @param name
	 * @param handler
	 * @param hbox
	 * @param screenSize
	 * @return This method creates buttons that are added to a HBox that need to be
	 *         stored in the main or front end classes
	 */
	public static Button makeReturnableButtonH(String name, EventHandler<ActionEvent> handler, HBox hbox,
			int screenSize) {
		Button sampleButton = new Button(name);
		sampleButton.setOnAction(handler);
		hbox.getChildren().add(sampleButton);
		return sampleButton;
	}

	/**
	 * @param content
	 * @param vbox
	 * @param screenSize
	 *            This method creates TextFields that are added to a VBox that does
	 *            not need to be stored in the main or front end classes
	 */
	public static void makeTextFieldV(String content, VBox vbox, int screenSize) {
		TextField sampleText = makeReturnableTextFieldV(content, vbox, screenSize);
	}

	/**
	 * @param content
	 * @param vbox
	 * @param screenSize
	 * @return This method creates TextFields that are added to a VBox that need to
	 *         be stored in the main or front end classes
	 */
	public static TextField makeReturnableTextFieldV(String content, VBox vbox, int screenSize) {
		TextField sampleText = new TextField();
		sampleText.setPromptText(content);
		vbox.getChildren().add(sampleText);
		sampleText.setTranslateY(screenSize);
		return sampleText;
	}

	/**
	 * @param content
	 * @param hbox
	 * @param screenSize
	 * @return This method creates TextBox that are added to a HBox that need to be
	 *         stored in the main or front end classes
	 */
	public static TextField makeReturnableTextFieldH(String content, HBox hbox, int screenSize) {
		TextField sampleText = new TextField();
		sampleText.setPromptText(content);
		hbox.getChildren().add(sampleText);
		return sampleText;
	}

}
package simulationDrivers;

import java.util.ResourceBundle;

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

	private static final int OFFSET = 7;
	private static Button fileChooserButton;
	private static Button startButton;
	private static Button pauseButton;
	private static Button resumeButton;
	private static Button fasterButton;
	private static Button slowerButton;
	private static Button resetButton;
	private static Button stepButton;
	private static TextField threshold;
	private static Button submit;
	private static int SCREEN_SIZE = 200 + OFFSET;

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	/**
	 * @param hbox
	 *            Creates start and filechooser buttons, with appropriate
	 *            padding/spacing
	 */
	public static void initializeTop(HBox hbox) {
		fileChooserButton = new Button(myResources.getString("choose"));
		hbox.getChildren().add(fileChooserButton);
		startButton = new Button(myResources.getString("start"));
		// startButton.setTranslateX(200);
		hbox.getChildren().add(startButton);
		hbox.setPadding(new Insets(OFFSET));
		hbox.setSpacing(OFFSET);
	}

	/**
	 * @param vbox
	 *            Creates functional buttons that change the way the simulation
	 *            runs.
	 */
	public static void initializeRight(VBox vbox) {
		vbox.setPadding(new Insets(OFFSET));
		vbox.setSpacing(OFFSET);

		pauseButton = new Button(myResources.getString("pauseButton"));
		vbox.getChildren().add(pauseButton);
		pauseButton.setTranslateY(SCREEN_SIZE - OFFSET * 5);

		resumeButton = new Button(myResources.getString("resumeButton"));
		resumeButton.setTranslateY(SCREEN_SIZE - OFFSET * 4);
		vbox.getChildren().add(resumeButton);

		fasterButton = new Button(myResources.getString("fasterButton"));
		vbox.getChildren().add(fasterButton);
		fasterButton.setTranslateY(SCREEN_SIZE - OFFSET * 3);

		slowerButton = new Button(myResources.getString("slowerButton"));
		vbox.getChildren().add(slowerButton);
		slowerButton.setTranslateY(SCREEN_SIZE - OFFSET * 2);

		resetButton = new Button(myResources.getString("resetButton"));
		vbox.getChildren().add(resetButton);
		resetButton.setTranslateY(SCREEN_SIZE - OFFSET);

		stepButton = new Button(myResources.getString("stepButton"));
		vbox.getChildren().add(stepButton);
		stepButton.setTranslateY(SCREEN_SIZE);
		
		threshold = new TextField();
		threshold.setPromptText("Input threshold");
		vbox.getChildren().add(threshold);
		threshold.setTranslateY(OFFSET*3-SCREEN_SIZE);
		
		submit = new Button("Submit");
		vbox.getChildren().add(submit);
		submit.setTranslateY(OFFSET*4-SCREEN_SIZE);
	}

}
package cellsociety_team08;

import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
	private static int SCREEN_SIZE = 200 + OFFSET;

	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	public static void initializeTop(HBox hbox) {
		fileChooserButton = new Button(myResources.getString("choose"));
		hbox.getChildren().add(fileChooserButton);
		startButton = new Button(myResources.getString("start"));
		// startButton.setTranslateX(200);
		hbox.getChildren().add(startButton);
		hbox.setPadding(new Insets(OFFSET));
		hbox.setSpacing(OFFSET);
	}

	public static void initializeRight(VBox vbox) {
		vbox.setPadding(new Insets(OFFSET));
		vbox.setSpacing(OFFSET);
		
		pauseButton = new Button(myResources.getString("pauseButton"));
		vbox.getChildren().add(pauseButton);
		pauseButton.setTranslateY(SCREEN_SIZE - OFFSET*5);

		resumeButton = new Button(myResources.getString("resumeButton"));
		resumeButton.setTranslateY(SCREEN_SIZE - OFFSET*4);
		vbox.getChildren().add(resumeButton);

		fasterButton = new Button(myResources.getString("fasterButton"));
		vbox.getChildren().add(fasterButton);
		fasterButton.setTranslateY(SCREEN_SIZE - OFFSET*3);

		slowerButton = new Button(myResources.getString("slowerButton"));
		vbox.getChildren().add(slowerButton);
		slowerButton.setTranslateY(SCREEN_SIZE - OFFSET*2);

		resetButton = new Button(myResources.getString("resetButton"));
		vbox.getChildren().add(resetButton);
		resetButton.setTranslateY(SCREEN_SIZE - OFFSET);

		stepButton = new Button(myResources.getString("stepButton"));
		vbox.getChildren().add(stepButton);
		stepButton.setTranslateY(SCREEN_SIZE);
	}

}
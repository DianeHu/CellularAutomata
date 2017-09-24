package cellsociety_team08;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SimulationButtons {
	
	private static Button fileChooserButton;
	private static Button startButton;
	private static Button pauseButton;
	private static Button resumeButton;
	private static Button fasterButton;
	private static Button slowerButton;
	private static Button resetButton;
	private static Button stepButton;
	
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
	
	public static void initializeTop(HBox hbox)
	{
		fileChooserButton = new Button(myResources.getString("choose"));
		hbox.getChildren().add(fileChooserButton);
		startButton = new Button(myResources.getString("start"));
		//startButton.setTranslateX(200);
		hbox.getChildren().add(startButton);
	}
	
	public static void initializeRight(VBox vbox)
	{
		pauseButton = new Button(myResources.getString("pauseButton"));
		vbox.getChildren().add(pauseButton);
		
		resumeButton = new Button(myResources.getString("resumeButton"));
		//resumeButton.setTranslateY(30);
		vbox.getChildren().add(resumeButton);
		
		fasterButton = new Button(myResources.getString("fasterButton"));
		vbox.getChildren().add(fasterButton);
		//fasterButton.setTranslateY(60);
		
		slowerButton = new Button(myResources.getString("slowerButton"));
		vbox.getChildren().add(slowerButton);
		//slowerButton.setTranslateY(90);
		
		resetButton = new Button(myResources.getString("resetButton"));
		vbox.getChildren().add(resetButton);
		resetButton.setTranslateY(120);
		
		stepButton = new Button(myResources.getString("stepButton"));
		vbox.getChildren().add(stepButton);
		stepButton.setTranslateY(150);
	}
	
	
}
package cellsociety_team08;

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
	
	public static void initializeTop(HBox hbox)
	{
		fileChooserButton = new Button("Choose XML file for configuration");
		hbox.getChildren().add(fileChooserButton);
		startButton = new Button("Start Simulation");
		//startButton.setTranslateX(200);
		hbox.getChildren().add(startButton);
	}
	
	public static void initializeRight(VBox vbox)
	{
		pauseButton = new Button("Pause");
		vbox.getChildren().add(pauseButton);
		
		resumeButton = new Button("Resume");
		//resumeButton.setTranslateY(30);
		vbox.getChildren().add(resumeButton);
		
		fasterButton = new Button("Faster");
		vbox.getChildren().add(fasterButton);
		//fasterButton.setTranslateY(60);
		
		slowerButton = new Button("Slower");
		vbox.getChildren().add(slowerButton);
		//slowerButton.setTranslateY(90);
		
		resetButton = new Button("Reset");
		root.getChildren().add(resetButton);
		resetButton.setTranslateY(120);
		
		stepButton = new Button("Step");
		root.getChildren().add(stepButton);
		stepButton.setTranslateY(150);
	}
	
	
}
package cellsociety_team08;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class SimulationButtons {
	
	private static Button fileChooserButton;
	private static Button startButton;
	private static Button pauseButton;
	private static Button resumeButton;
	private static Button fasterButton;
	private static Button slowerButton;
	private static Button resetButton;
	private static Button stepButton;
	
	public static void initializeTop(Group root)
	{
		fileChooserButton = new Button("Choose XML file for configuration");
		root.getChildren().add(fileChooserButton);
		startButton = new Button("Start Simulation");
		startButton.setTranslateX(200);
		root.getChildren().add(startButton);
	}
	
	public static void initializeRight(Group root)
	{
		pauseButton = new Button("Pause");
		root.getChildren().add(pauseButton);
		
		resumeButton = new Button("Resume");
		resumeButton.setTranslateY(30);
		root.getChildren().add(resumeButton);
		
		fasterButton = new Button("Faster");
		root.getChildren().add(fasterButton);
		fasterButton.setTranslateY(60);
		
		slowerButton = new Button("Slower");
		root.getChildren().add(slowerButton);
		slowerButton.setTranslateY(90);
		
		resetButton = new Button("Reset");
		root.getChildren().add(resetButton);
		resetButton.setTranslateY(120);
		
		stepButton = new Button("Step");
		root.getChildren().add(stepButton);
		stepButton.setTranslateY(150);
	}
	
	
}
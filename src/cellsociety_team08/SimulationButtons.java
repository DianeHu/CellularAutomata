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
		pauseButton = new Button("Choose XML file for configuration");
		root.getChildren().add(fileChooserButton);
		resumeButton = new Button("Start Simulation");
		resumeButton.setTranslateY(200);
		root.getChildren().add(startButton);
	}
	
	
}
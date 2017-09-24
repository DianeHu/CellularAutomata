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
	
	public static void initialize(Group root)
	{
		fileChooserButton = new Button("Choose XML file for configuration");
		root.getChildren().add(fileChooserButton);
		startButton = new Button("Start Simulation");
		startButton.setTranslateX(200);
		root.getChildren().add(startButton);
	}
	
	
}
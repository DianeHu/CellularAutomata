package simulationDrivers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * @author Diane Hu
 * @author Tyler Yam
 * 
 *         Class for creating buttons used in simulation main class.
 */
public class SimulationButtons {

	public static void makeButtonV(String name, EventHandler<ActionEvent> handler, VBox vbox, int screenSize, int offset)
	{
		Button sampleButton = new Button(name);
		sampleButton.setOnAction(handler);
		vbox.getChildren().add(sampleButton);
		sampleButton.setTranslateY(screenSize);
		
	}
	public static void makeButtonH(String name, EventHandler<ActionEvent> handler, HBox hbox, int screenSize, int offset)
	{
		Button sampleButton = new Button(name);
		sampleButton.setOnAction(handler);
		hbox.getChildren().add(sampleButton);
	}

}
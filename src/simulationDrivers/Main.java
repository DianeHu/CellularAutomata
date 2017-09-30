package simulationDrivers;

import XMLClasses.GridConfiguration;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
	private static GridConfiguration g = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Simulation segSim = new WatorSimulation(g);
		// TODO: put sim type into new popup screen, right now
		// any simulation can technically run another (no errors thrown)
		// since simtype gotten from xml
		segSim.start(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package simulationDrivers;

import XMLClasses.GridConfiguration;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
	private static GridConfiguration g = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Simulation segSim = new SpreadingWildfireSimulation(g);
		segSim.start(primaryStage);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

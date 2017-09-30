package simulationDrivers;

import XMLClasses.GameOfLifeConfiguration;
import XMLClasses.GridConfiguration;
import XMLClasses.SegregationConfiguration;
import XMLClasses.SpreadingWildfireConfiguration;
import XMLClasses.WatorConfiguration;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	
	private static GridConfiguration g = null;
	private static WatorConfiguration wG = null;
	private static SpreadingWildfireConfiguration sWG = null;
	private static GameOfLifeConfiguration gofC = null;
	private static SegregationConfiguration sC = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Simulation segSim = new WatorSimulation(wG);
		//Simulation segSim = new SegregationSimulation(sC);
		// TODO: put sim type into new popup screen, right now
		// any simulation can technically run another (no errors thrown)
		// since simtype gotten from xml
		segSim.start(primaryStage);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

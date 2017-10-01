package simulationDrivers;

import XMLClasses.GridConfiguration;
import XMLClasses.SegregationReader;

import java.io.File;

import XMLClasses.ForagingAntsConfiguration;
import XMLClasses.ForagingAntsReader;
import cellManager.Grid;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * 
 * @author Tyler Yam
 * @author Diane Hu This class holds most of the front end processing. This
 *         class essentially runs the simulations.
 */
public class ForagingAntsSimulation extends Simulation {

	private TextField threshold;
	private double maxAnts;
	private ForagingAntsConfiguration XMLConfiguration = null;
	
	public ForagingAntsSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}
	
	@Override
	protected Graph createGraph(Grid g) {
		return new ForagingAntsGraph(g);
	}
	
	@Override
	protected GridConfiguration setInputConfig(File dataFile) {
		XMLConfiguration = new ForagingAntsReader().getGridConfiguration(dataFile);
		return XMLConfiguration;
	}
	
	@Override
	protected void setUpThresholds() {
		maxAnts = XMLConfiguration.getMaxAnts();
	}
	
	@Override
	public Simulation copy() {
		ForagingAntsConfiguration fC = null;
		return new ForagingAntsSimulation(fC, sampleGrid);
	}
	
	@Override
	protected void makeSimSpecificFields(Stage s) {
		threshold = SimulationButtons.makeReturnableTextFieldV("Input maxAnts", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e->userSetThreshold(), vboxRight, 3*OFFSET-SCREEN_SIZE);
	}

	/*private void save(String sT, String nR, String nC, String cC, String pC, String pG, String sT1, String fB,
			String sB, String sS) {
		XMLOutput = new XMLExporter(sT, nR, nC, cC, pC, pG, sT1, fB, sB, sS);
		XMLOutput.buildXML();
	}*/

	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(maxAnts, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}
	
	@Override
	protected void step(double elapsedTime) {
		if(isPaused == false) {
			
		} else {
			sampleGrid.createPausedGrid(maxAnts, 0, 0);
			g.updateGraph();
			sampleGrid.update();
		}
	}

	@Override
	protected void userSetThreshold() {
		maxAnts = Double.parseDouble(threshold.getText());
	}

}
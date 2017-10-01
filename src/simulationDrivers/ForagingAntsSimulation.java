package simulationDrivers;

import XMLClasses.GridConfiguration;
import XMLClasses.SegregationReader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
	private TextField antConc;
	private TextField emptyConc;
	private Map<Character, Double> concMap = new HashMap<>();
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
		antConc = SimulationButtons.makeReturnableTextFieldV("Set ant concentration", vboxLeft, -LEFT_OFFSET);
		emptyConc = SimulationButtons.makeReturnableTextFieldV("Set empty concentration", vboxLeft, -LEFT_OFFSET);
	}
	
	@Override
	protected void setConcentrations() {
		concMap.put('a', Double.parseDouble(antConc.getText()));
		concMap.put('e', Double.parseDouble(emptyConc.getText()));
		sampleGrid.setConcMap(concMap);
		setConc.setDisable(true);
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
		if(!(threshold.getText().length()==0))
		{
			maxAnts = Double.parseDouble(threshold.getText());
		}
		else
			ErrorMessages.createErrors("Not Enough Inputs");
	}

}
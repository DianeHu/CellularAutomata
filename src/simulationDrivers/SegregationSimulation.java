package simulationDrivers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import XMLClasses.GridConfiguration;
import XMLClasses.SegregationConfiguration;
import XMLClasses.SegregationExporter;
import XMLClasses.SegregationReader;
import cellManager.Grid;
import cellManager.RectangleGrid;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * 
 * @author Tyler Yam
 * @author Diane Hu This class holds most of the front end processing. This
 *         class essentially runs the simulations.
 */
public class SegregationSimulation extends Simulation {

	private TextField threshold;
	private double satisfiedThreshold;
	private int numRows;
	private int numCols;
	private SegregationConfiguration sC = null;
	
	public SegregationSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}
	
	@Override
	protected void setUpThresholds() {
		numRows = sampleGrid.getNumRows();
		numCols = sampleGrid.getNumCols();
		satisfiedThreshold = sC.getSegregationThreshold();
	}
	
	@Override
	protected GridConfiguration setInputConfig(File dataFile) {
		sC = new SegregationReader().getGridConfiguration(dataFile);
		return sC;
	}
	
	@Override
	protected Graph createGraph(Grid g) {
		return new SegregationGraph(g);
	}
	
	@Override
	public Simulation copy() {
		SegregationConfiguration sC = null;
		return new SegregationSimulation(sC, sampleGrid);
	}
	
	@Override
	protected void makeSimSpecificFields(Stage s) {
		saveButton=SimulationButtons.makeReturnableButtonH("Save", e->save(Integer.toString(numRows), 
				Integer.toString(numCols), sampleGrid.getGridConfig(), Double.toString(satisfiedThreshold)), hboxTop, SCREEN_SIZE);
		threshold = SimulationButtons.makeReturnableTextFieldV("Input threshold", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e->userSetThreshold(), vboxRight, 3*OFFSET-SCREEN_SIZE);
	}

	private void save(String nR, String nC, String cC, String sT) {
		new SegregationExporter(nR, nC, cC, sT).buildXML();
	}

	@Override
	protected void manualStep() {
		//sampleGrid.createsNewGrid(satisfiedThreshold, 0, 0);
		sampleGrid.createsNewGrid(.3, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}
	
	@Override
	protected void step(double elapsedTime) {
		if(isPaused == false) {
			manualStep();
			//stepButton.setDisable(true);
		} else {
			sampleGrid.createPausedGrid(satisfiedThreshold, 0, 0);
			g.updateGraph();
			sampleGrid.update();
		}
	}

	@Override
	protected void userSetThreshold() {
		if(!(threshold.getText().length()==0))
		{
			satisfiedThreshold = Double.parseDouble(threshold.getText());
		}
		else
			ErrorMessages.createErrors("Not Enough Inputs");
	}

}
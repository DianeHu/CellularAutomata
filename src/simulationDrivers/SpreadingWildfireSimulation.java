package simulationDrivers;

import XMLClasses.GridConfiguration;
import XMLClasses.SpreadingWildfireConfiguration;
import XMLClasses.SpreadingWildfireExporter;
import cellManager.Grid;
import cellManager.RectangleGrid;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Tyler Yam
 * @author Diane Hu This class holds most of the front end processing. This
 *         class essentially runs the simulations.
 */
public class SpreadingWildfireSimulation extends Simulation {

	private TextField probGrow;
	private TextField probCatch;
	private double growthProbability;
	private double catchProbability;
	private int numRows;
	private int numCols;
	
	public SpreadingWildfireSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}
	
	@Override
	public Simulation copy() {
		SpreadingWildfireConfiguration sWC = null;
		return new SpreadingWildfireSimulation(sWC, sampleGrid);
	}
	
	@Override
	protected Graph createGraph(Grid g) {
		return new SpreadingWildfireGraph(g);
	}
	
	@Override
	protected void setUpThresholds() {
		numRows = sampleGrid.getNumRows();
		numCols = sampleGrid.getNumCols();
		growthProbability = ((SpreadingWildfireConfiguration) XMLConfiguration).getProbGrow();
		catchProbability = ((SpreadingWildfireConfiguration) XMLConfiguration).getProbCatch();
	}
	
	@Override
	protected void makeSimSpecificFields(Stage s) {
		SimulationButtons.makeButtonH("Save", e->save(Integer.toString(numRows), 
				Integer.toString(numCols), sampleGrid.getGridConfig(), 
				Double.toString(catchProbability), Double.toString(growthProbability)), hboxTop, SCREEN_SIZE);
		probGrow = SimulationButtons.makeReturnableTextFieldV("Input probGrow", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		probCatch = SimulationButtons.makeReturnableTextFieldV("Input probCatch", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e->userSetThreshold(), vboxRight, 3*OFFSET-SCREEN_SIZE);
	}
	
	@Override
	protected void userSetThreshold() {
		growthProbability = Double.parseDouble(probGrow.getText());
		catchProbability = Double.parseDouble(probCatch.getText());
	}

	private void save(String nR, String nC, String cC, String pC, String pG) {
		XMLOutput = new SpreadingWildfireExporter(nR, nC, cC, pC, pG);
		((SpreadingWildfireExporter) XMLOutput).buildXML();
	}
	
	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(growthProbability, catchProbability, 0);
		g.updateGraph();
		sampleGrid.update();
	}
	
	@Override
	protected void step(double elapsedTime) {
		if(isPaused == false) {
			manualStep();
		} else {
			sampleGrid.createPausedGrid(growthProbability, catchProbability, 0);
			g.updateGraph();
			sampleGrid.update();
		}
	}
}
package simulationDrivers;

import XMLClasses.GridConfiguration;
import XMLClasses.SpreadingWildfireConfiguration;
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
	
	public SpreadingWildfireSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}
	
	@Override
	public Simulation copy() {
		SpreadingWildfireConfiguration sWC = null;
		return new SpreadingWildfireSimulation(sWC, sampleGrid);
	}
	@Override
	protected void setUpThresholds() {
		growthProbability = ((SpreadingWildfireConfiguration) XMLConfiguration).getProbGrow();
		catchProbability = ((SpreadingWildfireConfiguration) XMLConfiguration).getProbCatch();
	}
	
	@Override
	protected void makeSimSpecificFields(Stage s) {
		probGrow = SimulationButtons.makeReturnableTextFieldV("Input probGrow", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		probCatch = SimulationButtons.makeReturnableTextFieldV("Input probCatch", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e->userSetThreshold(), vboxRight, 3*OFFSET-SCREEN_SIZE);
	}
	
	@Override
	protected void userSetThreshold() {
		growthProbability = Double.parseDouble(probGrow.getText());
		catchProbability = Double.parseDouble(probCatch.getText());
	}

	/*private void save(String sT, String nR, String nC, String cC, String pC, String pG, String sT1, String fB,
			String sB, String sS) {
		XMLOutput = new XMLExporter(sT, nR, nC, cC, pC, pG, sT1, fB, sB, sS);
		XMLOutput.buildXML();
	}*/
	
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
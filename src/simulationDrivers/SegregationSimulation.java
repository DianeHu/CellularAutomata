package simulationDrivers;

import XMLClasses.GridConfiguration;
import XMLClasses.SegregationConfiguration;
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
public class SegregationSimulation extends Simulation {

	private TextField threshold;
	private double satisfiedThreshold;
	
	public SegregationSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}
	
	@Override
	protected void setUpThresholds() {
		satisfiedThreshold = ((SegregationConfiguration) XMLConfiguration).getSegregationThreshold();
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
		threshold = SimulationButtons.makeReturnableTextFieldV("Input threshold", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e->userSetThreshold(), vboxRight, 3*OFFSET-SCREEN_SIZE);
	}

	/*private void save(String sT, String nR, String nC, String cC, String pC, String pG, String sT1, String fB,
			String sB, String sS) {
		XMLOutput = new XMLExporter(sT, nR, nC, cC, pC, pG, sT1, fB, sB, sS);
		XMLOutput.buildXML();
	}*/

	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(satisfiedThreshold, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}
	
	@Override
	protected void step(double elapsedTime) {
		if(isPaused == false) {
			manualStep();
		} else {
			sampleGrid.createPausedGrid(satisfiedThreshold, 0, 0);
			g.updateGraph();
			sampleGrid.update();
		}
	}

	@Override
	protected void userSetThreshold() {
		satisfiedThreshold = Double.parseDouble(threshold.getText());
	}

}
package simulationDrivers;

import XMLClasses.GridConfiguration;
import javafx.scene.control.Button;
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
	private double growthProbability = 0.5;
	private double catchProbability = 0.5;
	private Button submit;
	
	public SpreadingWildfireSimulation(GridConfiguration gC) {
		super(gC);
	}
	
	@Override
	protected void makeSimSpecificFields(Stage s) {
		probGrow = SimulationButtons.makeReturnableTextField("Input probGrow", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		probCatch = SimulationButtons.makeReturnableTextField("Input probCatch", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e->userSetThreshold(), vboxRight, 3*OFFSET-SCREEN_SIZE);
	}
	
	@Override
	protected void userSetThreshold() {
		growthProbability = Double.parseDouble(probGrow.getText());
		growthProbability = Double.parseDouble(probCatch.getText());
	}

	/*private void save(String sT, String nR, String nC, String cC, String pC, String pG, String sT1, String fB,
			String sB, String sS) {
		XMLOutput = new XMLExporter(sT, nR, nC, cC, pC, pG, sT1, fB, sB, sS);
		XMLOutput.buildXML();
	}*/
	
	@Override
	protected void step(double elapsedTime) {
		sampleGrid.createsNewGrid(growthProbability, catchProbability, 0);
		g.updateGraph();
		sampleGrid.update();
	}
}
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
public class SegregationSimulation extends Simulation {

	private TextField threshold;
	private double satisfiedThreshold = 0.5;
	private Button submit;
	
	public SegregationSimulation(GridConfiguration gC) {
		super(gC);
	}
	
	@Override
	protected void makeButtons(Stage s) {
		SimulationButtons.makeButtonH("Choose XML File for Configuration", e -> openFile(s), hboxTop, SCREEN_SIZE);
		SimulationButtons.makeButtonH("Start Simulation", e -> startMethod(s), hboxTop, SCREEN_SIZE);
		//SimulationButtons.makeButtonH("Save", e -> save(simType, nRows, nCols, cellConfig, pCatch, pGrow, segThreshold,
		//		fBreedTurns, sBreedTurns, sStarveTurns), hboxTop, SCREEN_SIZE);

		SimulationButtons.makeButtonV("Pause", e -> pause(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Resume", e -> resume(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Speed Up", e -> faster(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Slow Down", e -> slower(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Reset", e -> reset(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Step", e -> manualStep(), vboxRight, SCREEN_SIZE);

		threshold = SimulationButtons.makeReturnableTextField("Input threshold", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e->userSetThreshold(), vboxRight, 3*OFFSET-SCREEN_SIZE);
	}

	/*private void save(String sT, String nR, String nC, String cC, String pC, String pG, String sT1, String fB,
			String sB, String sS) {
		XMLOutput = new XMLExporter(sT, nR, nC, cC, pC, pG, sT1, fB, sB, sS);
		XMLOutput.buildXML();
	}*/

	@Override
	protected void step(double elapsedTime) {
		sampleGrid.createsNewGrid(satisfiedThreshold, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}

	@Override
	protected void userSetThreshold() {
		satisfiedThreshold = Double.parseDouble(threshold.getText());
	}

}
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
public class WatorSimulation extends Simulation {

	private TextField fishBreed;
	private TextField sharkBreed;
	private TextField sharkStarve;
	private double fBreedTurns = 5;
	private double sBreedTurns = 5;
	private double starveTurns = 5;
	private Button submit;
	
	public WatorSimulation(GridConfiguration gC) {
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

		fishBreed = SimulationButtons.makeReturnableTextField("Input fishBreed", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		sharkBreed = SimulationButtons.makeReturnableTextField("Input sharkBreed", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		sharkStarve = SimulationButtons.makeReturnableTextField("Input sharkStarve", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e->userSetThreshold(), vboxRight, 3*OFFSET-SCREEN_SIZE);
	}
	
	@Override
	protected void userSetThreshold() {
		fBreedTurns = Double.parseDouble(fishBreed.getText());
		sBreedTurns = Double.parseDouble(sharkBreed.getText());
		starveTurns = Double.parseDouble(sharkStarve.getText());
	}

	/*private void save(String sT, String nR, String nC, String cC, String pC, String pG, String sT1, String fB,
			String sB, String sS) {
		XMLOutput = new XMLExporter(sT, nR, nC, cC, pC, pG, sT1, fB, sB, sS);
		XMLOutput.buildXML();
	}*/
	
	@Override
	protected void step(double elapsedTime) {
		sampleGrid.createsNewGrid(fBreedTurns, sBreedTurns, starveTurns);
		g.updateGraph();
		sampleGrid.update();
	}
}
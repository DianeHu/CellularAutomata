package simulationDrivers;

import XMLClasses.GridConfiguration;
import XMLClasses.WatorConfiguration;
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
	private double fBreedTurns;
	private double sBreedTurns;
	private double starveTurns;
	
	public WatorSimulation(WatorConfiguration gC) {
		super(gC);
	}
	
	@Override
	protected void setUpThresholds() {
		fBreedTurns = ((WatorConfiguration) XMLConfiguration).getFishBreedTurns();
		sBreedTurns = ((WatorConfiguration) XMLConfiguration).getSharkBreedTurns();
		starveTurns = ((WatorConfiguration) XMLConfiguration).getSharkStarveTurns();
	}
	
	@Override
	protected void makeSimSpecificFields(Stage s) {
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
	protected void manualStep() {
		sampleGrid.createsNewGrid(fBreedTurns, sBreedTurns, starveTurns);
		g.updateGraph();
		sampleGrid.update();
	}
	
	@Override
	protected void step(double elapsedTime) {
		if(isPaused == false) {
			manualStep();
		} else {
			sampleGrid.createPausedGrid(fBreedTurns, sBreedTurns, starveTurns);
			g.updateGraph();
			sampleGrid.update();
		}
	}
}
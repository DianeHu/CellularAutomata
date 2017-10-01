package simulationDrivers;

import XMLClasses.GridConfiguration;
import XMLClasses.WatorConfiguration;
import XMLClasses.WatorExporter;
import XMLClasses.XMLExporter;
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
public class WatorSimulation extends Simulation {

	private TextField fishBreed;
	private TextField sharkBreed;
	private TextField sharkStarve;
	private double fBreedTurns;
	private double sBreedTurns;
	private double starveTurns;
	private int numRows;
	private int numCols;
	
	public WatorSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}
	
	@Override
	protected void setUpThresholds() {
		numRows = sampleGrid.getNumRows();
		numCols = sampleGrid.getNumCols();
		fBreedTurns = ((WatorConfiguration) XMLConfiguration).getFishBreedTurns();
		sBreedTurns = ((WatorConfiguration) XMLConfiguration).getSharkBreedTurns();
		starveTurns = ((WatorConfiguration) XMLConfiguration).getSharkStarveTurns();
	}
	
	@Override
	protected Graph createGraph(Grid g) {
		return new WatorGraph(g);
	}
	
	@Override
	public Simulation copy() {
		WatorConfiguration wG = null;
		return new WatorSimulation(wG, sampleGrid);
	}
	
	@Override
	protected void makeSimSpecificFields(Stage s) {
		SimulationButtons.makeButtonH("Save", e->save(Integer.toString(numRows), 
				Integer.toString(numCols), sampleGrid.getGridConfig(), 
				Double.toString(fBreedTurns), Double.toString(sBreedTurns),
				Double.toString(starveTurns)), hboxTop, SCREEN_SIZE);
		fishBreed = SimulationButtons.makeReturnableTextFieldV("Input fishBreed", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		sharkBreed = SimulationButtons.makeReturnableTextFieldV("Input sharkBreed", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		sharkStarve = SimulationButtons.makeReturnableTextFieldV("Input sharkStarve", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e->userSetThreshold(), vboxRight, 3*OFFSET-SCREEN_SIZE);
	}
	
	@Override
	protected void userSetThreshold() {
		fBreedTurns = Double.parseDouble(fishBreed.getText());
		sBreedTurns = Double.parseDouble(sharkBreed.getText());
		starveTurns = Double.parseDouble(sharkStarve.getText());
	}

	private void save(String nR, String nC, String cC, String fB, String sB, String sS) {
		XMLOutput = new WatorExporter(nR, nC, cC, fB, sB, sS);
		((WatorExporter) XMLOutput).buildXML();
	}
	
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
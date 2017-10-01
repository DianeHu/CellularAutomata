package simulationDrivers;

import java.io.File;

import XMLClasses.GridConfiguration;
import XMLClasses.RPSConfiguration;
import XMLClasses.RPSExporter;
import XMLClasses.RPSReader;
import cellManager.Grid;
import javafx.stage.Stage;

/**
 * 
 * @author Tyler Yam
 * @author Diane Hu This class holds most of the front end processing. This
 *         class essentially runs the simulations.
 */
public class RPSSimulation extends Simulation {

	private int numRows;
	private int numCols;
	private RPSConfiguration XMLConfiguration = null;

	public RPSSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}

	@Override
	protected void setUpThresholds() {
		numRows = sampleGrid.getNumRows();
		numCols = sampleGrid.getNumCols();
	}

	@Override
	protected GridConfiguration setInputConfig(File dataFile) {
		XMLConfiguration = new RPSReader().getGridConfiguration(dataFile);
		return XMLConfiguration;
	}

	@Override
	protected Graph createGraph(Grid g) {
		// TODO
		return new GameOfLifeGraph(g);
	}

	@Override
	public Simulation copy() {
		RPSConfiguration rpsConfig = null;
		return new RPSSimulation(rpsConfig, sampleGrid);
	}

	@Override
	protected void step(double elapsedTime) {
		if (isPaused == false) {
			manualStep();
		} else {
			sampleGrid.createPausedGrid(0, 0, 0);
			g.updateGraph();
			sampleGrid.update();
		}
	}

	@Override
	protected void userSetThreshold() {
		// do nothing
	}

	@Override
	protected void makeSimSpecificFields(Stage s) {
		saveButton = SimulationButtons.makeReturnableButtonH("Save",
				e -> save(Integer.toString(numRows), Integer.toString(numCols), sampleGrid.getGridConfig()), hboxTop,
				SCREEN_SIZE);
	}

	private void save(String nR, String nC, String cC) {
		new RPSExporter(nR, nC, cC).buildXML();
	}

	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(0, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}
}
package simulationDrivers;

import java.io.File;
import java.util.ResourceBundle;

import XMLClasses.GridConfiguration;
import XMLClasses.RPSConfiguration;
import XMLClasses.RPSExporter;
import XMLClasses.RPSReader;
import cellManager.Grid;
import javafx.stage.Stage;

/**
 * 
 * @author Diane Hu This class is a subclass of the Simulation superclass. Runs
 *         the Rock Paper Scissors simulation. Used through the main simulation
 *         picker by typing in "RPS." Depends on the XML classes, and indirectly
 *         through the superclass, on the Grid classes.
 */
public class RPSSimulation extends Simulation {

	private int numRows;
	private int numCols;
	private RPSConfiguration XMLConfiguration = null;
	
	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	/**
	 * @param gC
	 * @param g
	 * 
	 *            Constructor using superclass constructor.
	 */
	public RPSSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#setUpThresholds()
	 * 
	 * Sets up the simulation specific thresholds. In this case, uses none other
	 * than row and column number, since RPS does not have any thresholds the user
	 * would be able to set.
	 */
	@Override
	protected void setUpThresholds() {
		numRows = sampleGrid.getNumRows();
		numCols = sampleGrid.getNumCols();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#setInputConfig(java.io.File)
	 * 
	 * Returns an appropriate XML configuration for the superclass file chooser to
	 * read.s
	 */
	@Override
	protected GridConfiguration setInputConfig(File dataFile) {
		XMLConfiguration = new RPSReader().getGridConfiguration(dataFile);
		return XMLConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#createGraph(cellManager.Grid)
	 * 
	 * Creates the appropriate graph specific to this simulation.
	 */
	@Override
	protected Graph createGraph(Grid g) {
		return new RPSGraph(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#copy()
	 * 
	 * Returns a copy of the current simulation.
	 */
	@Override
	public Simulation copy() {
		RPSConfiguration rpsConfig = null;
		return new RPSSimulation(rpsConfig, sampleGrid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#step(double)
	 * 
	 * Step method for updating the simulation. If the simulation isn't paused, it
	 * steps through as usual using the manualStep call, but if it is, it will stop
	 * all cell movement except for user click directed state changes. Red cells
	 * change to green cells, green cells change to blue cells, blue cells change to
	 * red, and white cells change to blue.
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#userSetThreshold()
	 * 
	 * Sets user specific thresholds; in this case, none. Users do not set any
	 * thresholds in this simulation, but the method must be included as it is
	 * abstracted in the superclass. Thus, it's a do-nothing method.
	 */
	@Override
	protected void userSetThreshold() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#makeSimSpecificFields(javafx.stage.Stage)
	 * 
	 * Makes simulation specific textfields/buttons. In this case, only a save
	 * button--no thresholds to submit or set.
	 */
	@Override
	protected void makeSimSpecificFields(Stage s) {
		saveButton = SimulationButtons.makeReturnableButtonH("Save",
				e -> save(Integer.toString(numRows), Integer.toString(numCols), sampleGrid.getGridConfig()), hboxTop,
				SCREEN_SIZE);
	}

	/**
	 * @param numRows
	 * @param numCols
	 * @param cellConfig
	 * 
	 *            Saves the state of the current simulation in the file called
	 *            GridConfigurationOutput
	 */
	private void save(String numRows, String numCols, String cellConfig) {
		new RPSExporter(numRows, numCols, cellConfig).buildXML();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#manualStep()
	 * 
	 * Method dictating regular/normal step behavior, updating cell states/movement.
	 */
	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(0, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}
}
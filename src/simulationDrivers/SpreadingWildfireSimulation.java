package simulationDrivers;

import java.io.File;

import XMLClasses.GridConfiguration;
import XMLClasses.SpreadingWildfireConfiguration;
import XMLClasses.SpreadingWildfireExporter;
import XMLClasses.SpreadingWildfireReader;
import cellManager.Grid;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Diane Hu This is a subclass simulation type of the superclass
 *         Simulation. Instantiates a SpreadingWildfire simulation. Used through
 *         the main simulation picker by typing in "SpreadingWildfire" into the
 *         textfield. Depends on the XML classes, and indirectly through the
 *         main superclass, on the Grid classes.
 */
public class SpreadingWildfireSimulation extends Simulation {

	private TextField probGrow;
	private TextField probCatch;
	private double growthProbability;
	private double catchProbability;
	private int numRows;
	private int numCols;
	private SpreadingWildfireConfiguration XMLConfiguration;

	/**
	 * @param gC
	 * @param g
	 * 
	 *            Constructor using superclass constructor.
	 */
	public SpreadingWildfireSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
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
		SpreadingWildfireConfiguration sWC = null;
		return new SpreadingWildfireSimulation(sWC, sampleGrid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#setInputConfig(java.io.File)
	 * 
	 * Returns an appropriate XML configuration for the main simulation class to
	 * read in its filechooser.
	 */
	@Override
	protected GridConfiguration setInputConfig(File dataFile) {
		XMLConfiguration = new SpreadingWildfireReader().getGridConfiguration(dataFile);
		return XMLConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#createGraph(cellManager.Grid)
	 * 
	 * Creates an appropriate graph for the current simulation type.
	 */
	@Override
	protected Graph createGraph(Grid g) {
		return new SpreadingWildfireGraph(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#setUpThresholds()
	 * 
	 * Sets up spreadingwildfire specific thresholds: a growth probability for empty
	 * land to grow trees, and a catch probability for trees to catch fire from
	 * neighboring trees (amplified in the cell classes by how many surrounding
	 * trees are on fire)
	 */
	@Override
	protected void setUpThresholds() {
		numRows = sampleGrid.getNumRows();
		numCols = sampleGrid.getNumCols();
		growthProbability = XMLConfiguration.getProbGrow();
		catchProbability = XMLConfiguration.getProbCatch();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#makeSimSpecificFields(javafx.stage.Stage)
	 * 
	 * Makes the simulation specific textfields and buttons: a save exporting
	 * button, and textfields/a submit button necessary to allow a user to set
	 * growth and catch probabilities mid-simulation.
	 */
	@Override
	protected void makeSimSpecificFields(Stage s) {
		saveButton = SimulationButtons
				.makeReturnableButtonH("Save",
						e -> save(Integer.toString(numRows), Integer.toString(numCols), sampleGrid.getGridConfig(),
								Double.toString(catchProbability), Double.toString(growthProbability)),
						hboxTop, SCREEN_SIZE);
		probGrow = SimulationButtons.makeReturnableTextFieldV("Input probGrow", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		probCatch = SimulationButtons.makeReturnableTextFieldV("Input probCatch", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e -> userSetThreshold(), vboxRight,
				3 * OFFSET - SCREEN_SIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#userSetThreshold()
	 * 
	 * If the user set threshold fields aren't empty, the probabilities of the
	 * simulation are updated correctly, unless the user hasn't set all the
	 * appropriate thresholds, in which case an error dialog is thrown.
	 */
	@Override
	protected void userSetThreshold() {
		if (!(probGrow.getText().length() == 0) || !(probCatch.getText().length() == 0)) {
			growthProbability = Double.parseDouble(probGrow.getText());
			catchProbability = Double.parseDouble(probCatch.getText());
		} else
			ErrorMessages.createErrors("Not Enough Inputs");
	}

	/**
	 * @param numRows
	 * @param numCols
	 * @param cellConfig
	 * @param probCatch
	 * @param probGrow
	 * 
	 *            Saves the current state of the simulation in the file called
	 *            GridConfigurationExport
	 */
	private void save(String numRows, String numCols, String cellConfig, String probCatch, String probGrow) {
		new SpreadingWildfireExporter(numRows, numCols, cellConfig, probCatch, probGrow).buildXML();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#manualStep()
	 * 
	 * Method directing normal grid updating behavior.
	 */
	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(growthProbability, catchProbability, 0);
		g.updateGraph();
		sampleGrid.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#step(double)
	 * 
	 * Method directing overall updating behavior--if simulation isn't paused,
	 * update as usual, otherwise on pause, doesn't update cell movement except for
	 * state changes directed by user mouse click. Trees change into burning trees,
	 * land grows trees, and burning trees burn out into empty land.
	 */
	@Override
	protected void step(double elapsedTime) {
		if (isPaused == false) {
			manualStep();
		} else {
			sampleGrid.createPausedGrid(growthProbability, catchProbability, 0);
			g.updateGraph();
			sampleGrid.update();
		}
	}
}
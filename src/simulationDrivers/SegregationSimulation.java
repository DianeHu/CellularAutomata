package simulationDrivers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import XMLClasses.GridConfiguration;
import XMLClasses.SegregationConfiguration;
import XMLClasses.SegregationExporter;
import XMLClasses.SegregationReader;
import cellManager.Grid;
import cellManager.RectangleGrid;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Diane Hu
 * 
 *         This class is a subclass of the main simulation class. It
 *         instantiates a simulation of type Segregation. Used through the main
 *         GUI simulation picker by typing in "Segregation" into the Textfield.
 *         Depends on the XML classes, and indirectly through the main
 *         simulation class on the Grid classes.
 */
public class SegregationSimulation extends Simulation {

	private TextField threshold;
	private double satisfiedThreshold;
	private int numRows;
	private int numCols;
	private SegregationConfiguration sC = null;

	/**
	 * @param gC
	 * @param g
	 * 
	 *            Constructor using superclass constructor.
	 */
	public SegregationSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#setUpThresholds() Sets up simulation
	 * specific thresholds: in this case, a satisfied threshold determining whether
	 * or not cells want to move based on concentration of like neighbors.
	 */
	@Override
	protected void setUpThresholds() {
		numRows = sampleGrid.getNumRows();
		numCols = sampleGrid.getNumCols();
		satisfiedThreshold = sC.getSegregationThreshold();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#setInputConfig(java.io.File) Creates and
	 * returns an appropriate XML configuration for the main class to use in the
	 * filechooser.
	 */
	@Override
	protected GridConfiguration setInputConfig(File dataFile) {
		sC = new SegregationReader().getGridConfiguration(dataFile);
		return sC;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#createGraph(cellManager.Grid)
	 * 
	 * Creates the appropriate graph type for the current simulation.
	 */
	@Override
	protected Graph createGraph(Grid g) {
		return new SegregationGraph(g);
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
		SegregationConfiguration sC = null;
		return new SegregationSimulation(sC, sampleGrid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#makeSimSpecificFields(javafx.stage.Stage)
	 * 
	 * Makes the simulation specific textfields/buttons--in this case, a save
	 * button, and a textfield/submit button to allow users to set/reset the
	 * satisfied threshold mid-simulation.
	 */
	@Override
	protected void makeSimSpecificFields(Stage s) {
		saveButton = SimulationButtons.makeReturnableButtonH("Save", e -> save(Integer.toString(numRows),
				Integer.toString(numCols), sampleGrid.getGridConfig(), Double.toString(satisfiedThreshold)), hboxTop,
				SCREEN_SIZE);
		threshold = SimulationButtons.makeReturnableTextFieldV("Input threshold", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e -> userSetThreshold(), vboxRight,
				3 * OFFSET - SCREEN_SIZE);
	}

	/**
	 * @param numRows
	 * @param numCols
	 * @param cellConfig
	 * @param satThresh
	 * 
	 *            Saves the state of the current simulation in a file called
	 *            GridConfigurationOutput
	 */
	private void save(String numRows, String numCols, String cellConfig, String satThresh) {
		new SegregationExporter(numRows, numCols, cellConfig, satThresh).buildXML();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#manualStep()
	 * 
	 * Method directing the normal step behavior of the simulation.
	 */
	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(satisfiedThreshold, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#step(double)
	 * 
	 * Step method directing the overall update behavior of the simulation. If not
	 * paused, the simulation steps through as usual, else if paused, all cell
	 * movements pause except for state changes directed by user clicks. Orange
	 * cells change to blue, blue to orange, and empty cells have no state change
	 * behavior.
	 */
	@Override
	protected void step(double elapsedTime) {
		if (isPaused == false) {
			manualStep();
			// stepButton.setDisable(true);
		} else {
			sampleGrid.createPausedGrid(satisfiedThreshold, 0, 0);
			g.updateGraph();
			sampleGrid.update();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#userSetThreshold()
	 * 
	 * Method directing what happens when the user sets their own thresholds not
	 * through the XML. If all fields have been input, the satisfied threshold is
	 * updated appropriately.
	 */
	@Override
	protected void userSetThreshold() {
		if (!(threshold.getText().length() == 0)) {
			satisfiedThreshold = Double.parseDouble(threshold.getText());
		} else
			ErrorMessages.createErrors("Not Enough Inputs");
	}

}
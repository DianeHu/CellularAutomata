package simulationDrivers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import XMLClasses.GameOfLifeConfiguration;
import XMLClasses.GameOfLifeExporter;
import XMLClasses.GameOfLifeReader;
import XMLClasses.GridConfiguration;
import XMLClasses.SegregationReader;
import cellManager.Grid;
import cellManager.RectangleGrid;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Diane Hu GameOfLifeSimulation is a subclass of the Simulation
 *         superclass. Instantiates a simulation of type GameOfLife. Used by
 *         typing in GameOfLife through the main simulation picker, and runs a
 *         Game Of Life cellular automaton. Depends on the XML classes, and
 *         through the superclass, the Grid classes.
 */
public class GameOfLifeSimulation extends Simulation {

	private int numRows;
	private int numCols;
	private GameOfLifeConfiguration XMLConfiguration = null;

	/**
	 * @param gC
	 * @param g
	 * 
	 *            Constructor using superclass constructor.
	 */
	public GameOfLifeSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#setUpThresholds()
	 * 
	 * Sets up simulation specific thresholds. In this case, there are none other
	 * than row and column numbers--Game Of Life has no thresholds of its own.
	 * Conway's rules dictate all behavior.
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
	 * returns a new XML configuraton of the appropriate type, for the superclass
	 * file reader to use in reading in grid configurations.
	 */
	@Override
	protected GridConfiguration setInputConfig(File dataFile) {
		XMLConfiguration = new GameOfLifeReader().getGridConfiguration(dataFile);
		return XMLConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#createGraph(cellManager.Grid) Creates the
	 * appropriate graph type for this simulation.
	 */
	@Override
	protected Graph createGraph(Grid g) {
		return new GameOfLifeGraph(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#copy()
	 * 
	 * Creates a copy of the current simulation.
	 */
	@Override
	public Simulation copy() {
		GameOfLifeConfiguration golC = null;
		return new GameOfLifeSimulation(golC, sampleGrid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#step(double)
	 * 
	 * This method steps the simulation (updates). If it's not paused, it steps
	 * through as usual through the manualStep call. If it's paused, it will pause
	 * all cell movement except for the state changes made through user clicks. Live
	 * cells go dead, dead go live.
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
	 * A do nothing user setting threshold method. The superclass method is
	 * abstracted, so it must be implemented here, but since GameOfLife has not
	 * specific thresholds, this method does nothing.
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
	 * Makes a save button for XML exporting capabilities.
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
	 *            GridConfigurationExport.
	 */
	private void save(String numRows, String numCols, String cellConfig) {
		new GameOfLifeExporter(numRows, numCols, cellConfig).buildXML();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#manualStep()
	 * 
	 * This method calls the normal step behavior, instantiating the next step of
	 * the current simulation.
	 */
	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(0, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}
}
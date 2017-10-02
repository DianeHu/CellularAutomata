package simulationDrivers;

import java.io.File;

import XMLClasses.ForagingAntsConfiguration;
import XMLClasses.ForagingAntsExporter;
import XMLClasses.ForagingAntsReader;
import XMLClasses.GridConfiguration;
import cellManager.Grid;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Diane Hu Simulation subclass that instantiates a ForagingAnt type
 *         simulation. Depends on the XML classes. Used through the main GUI--if
 *         "ForagingAnts" is typed, a user will be able to run a ForagingAnts
 *         simulation. Run with a ForagingAnts XML file--see TestForagingAnts as
 *         an example, or test run with that file.
 */
public class ForagingAntsSimulation extends Simulation {

	private TextField threshold;
	private double maxAnts;
	private ForagingAntsConfiguration XMLConfiguration = null;
	private int numRows;
	private int numCols;
	private int homeLocX;
	private int homeLocY;
	private int foodLocX;
	private int foodLocY;

	/**
	 * @param gC
	 * @param g
	 *            Constructor that uses the superclass constructor.
	 */
	public ForagingAntsSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#createGraph(cellManager.Grid) Creates a
	 * ForagingAntsGraph.
	 */
	@Override
	protected Graph createGraph(Grid g) {
		return new ForagingAntsGraph(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#setInputConfig(java.io.File) Returns a
	 * ForagingAntsConfiguration for the superclass filereader to use.
	 */
	@Override
	protected GridConfiguration setInputConfig(File dataFile) {
		XMLConfiguration = new ForagingAntsReader().getGridConfiguration(dataFile);
		return XMLConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#setUpThresholds() Sets up ant specific
	 * thresholds, including home and food location, and max number of ant groups.
	 */
	@Override
	protected void setUpThresholds() {
		numRows = sampleGrid.getNumRows();
		numCols = sampleGrid.getNumCols();
		homeLocX = XMLConfiguration.getHomeLocX();
		homeLocY = XMLConfiguration.getHomeLocY();
		foodLocX = XMLConfiguration.getFoodLocX();
		foodLocY = XMLConfiguration.getFoodLocY();
		maxAnts = XMLConfiguration.getMaxAnts();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#copy() Returns a copy of this specific
	 * simulation with the appropriate grid and configuration.
	 */
	@Override
	public Simulation copy() {
		ForagingAntsConfiguration fC = null;
		return new ForagingAntsSimulation(fC, sampleGrid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#makeSimSpecificFields(javafx.stage.Stage)
	 * Makes the simulation specific textfields and buttons. In this case, foraging
	 * ants needs a max ants threshold setting button, and a submit button for that
	 * threshold.
	 */
	@Override
	protected void makeSimSpecificFields(Stage s) {
		saveButton = SimulationButtons.makeReturnableButtonH("Save",
				e -> save(Integer.toString(numRows), Integer.toString(numCols), sampleGrid.getGridConfig(),
						Double.toString(maxAnts), Integer.toString(homeLocX), Integer.toString(homeLocY),
						Integer.toString(foodLocX), Integer.toString(foodLocY)),
				hboxTop, SCREEN_SIZE);
		threshold = SimulationButtons.makeReturnableTextFieldV("Input maxAnts", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e -> userSetThreshold(), vboxRight,
				3 * OFFSET - SCREEN_SIZE);
	}

	/**
	 * @param numRows
	 * @param numCols
	 * @param cellConfig
	 * @param maxAnts
	 * @param homeLocX
	 * @param homeLocY
	 * @param foodLocX
	 * @param foodLocY
	 * 
	 *            Saves the current simulation configuration in the file called
	 *            GridConfigurationOutput
	 */
	private void save(String numRows, String numCols, String cellConfig, String maxAnts, String homeLocX,
			String homeLocY, String foodLocX, String foodLocY) {
		new ForagingAntsExporter(numRows, numCols, cellConfig, maxAnts, homeLocX, homeLocY, foodLocX, foodLocY)
				.buildXML();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#manualStep() Steps through the simulation
	 * for one step--updating the graph, creating a new grid state.
	 */
	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(maxAnts, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#step(double) Method controlling regular
	 * stepping. If the simulation isn't paused, it steps through as usual, but if
	 * it is, the grid doesn't update cell movement--only cell state changes, i.e.,
	 * if a user clicks on a cell it changes state. In this case, ants go empty, and
	 * empty cells have no change state behavior.
	 */
	@Override
	protected void step(double elapsedTime) {
		if (isPaused == false) {
			manualStep();
		} else {
			sampleGrid.createPausedGrid(maxAnts, 0, 0);
			g.updateGraph();
			sampleGrid.update();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#userSetThreshold()
	 * 
	 * Sets the simulation specific threshold of max number of ants in an ant group.
	 */
	@Override
	protected void userSetThreshold() {
		if (!(threshold.getText().length() == 0)) {
			maxAnts = Double.parseDouble(threshold.getText());
		} else
			ErrorMessages.createErrors("Not Enough Inputs");
	}

}
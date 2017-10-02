package simulationDrivers;

import java.io.File;

import XMLClasses.GridConfiguration;
import XMLClasses.WatorConfiguration;
import XMLClasses.WatorExporter;
import XMLClasses.WatorReader;
import cellManager.Grid;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @Diane Hu
 * 
 *        Subclass of Simulation superclass. Creates a simulation object for the
 *        wator cellular automata. Used in the main GUI simulation picker by
 *        typing in "Wator" into the textfield. Depends on the XML classes, and
 *        through the superclass, indirectly on the Grid classes.
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
	private WatorConfiguration XMLConfiguration = null;

	/**
	 * @param gC
	 * @param g
	 * 
	 *            Constructor using superclass constructor
	 */
	public WatorSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#setUpThresholds()
	 * 
	 * Sets up the correct thresholds specific to this simulation, including fish
	 * and shark breed turns, and shark starve turns.
	 */
	@Override
	protected void setUpThresholds() {
		numRows = sampleGrid.getNumRows();
		numCols = sampleGrid.getNumCols();
		fBreedTurns = XMLConfiguration.getFishBreedTurns();
		sBreedTurns = XMLConfiguration.getSharkBreedTurns();
		starveTurns = XMLConfiguration.getSharkStarveTurns();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#createGraph(cellManager.Grid)
	 * 
	 * Creates the correct graph type for this simulation.
	 */
	@Override
	protected Graph createGraph(Grid g) {
		return new WatorGraph(g);
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
		WatorConfiguration wG = null;
		return new WatorSimulation(wG, sampleGrid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#makeSimSpecificFields(javafx.stage.Stage)
	 * 
	 * Makes the simulation specific textfields and buttons. In this case,
	 * textfields/submit buttons to allow users to set shark and fish thresholds
	 * mid-simulation, and a save button for XML exporting.
	 */
	@Override
	protected void makeSimSpecificFields(Stage s) {
		saveButton = SimulationButtons.makeReturnableButtonH("Save",
				e -> save(Integer.toString(numRows), Integer.toString(numCols), sampleGrid.getGridConfig(),
						Double.toString(fBreedTurns), Double.toString(sBreedTurns), Double.toString(starveTurns)),
				hboxTop, SCREEN_SIZE);
		fishBreed = SimulationButtons.makeReturnableTextFieldV("Input fishBreed", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		sharkBreed = SimulationButtons.makeReturnableTextFieldV("Input sharkBreed", vboxRight,
				3 * OFFSET - SCREEN_SIZE);
		sharkStarve = SimulationButtons.makeReturnableTextFieldV("Input sharkStarve", vboxRight,
				3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e -> userSetThreshold(), vboxRight,
				3 * OFFSET - SCREEN_SIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#setInputConfig(java.io.File)
	 * 
	 * Creates and returns an appropriate XML configuration for the superclass file
	 * chooser to use.
	 */
	@Override
	protected GridConfiguration setInputConfig(File dataFile) {
		XMLConfiguration = new WatorReader().getGridConfiguration(dataFile);
		return XMLConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#userSetThreshold()
	 * 
	 * Updates the user set thresholds if all three thresholds are filled out
	 * appropriately.
	 */
	@Override
	protected void userSetThreshold() {
		if (!(fishBreed.getText().length() == 0) || !(sharkBreed.getText().length() == 0)
				|| !(sharkStarve.getText().length() == 0)) {
			fBreedTurns = Double.parseDouble(fishBreed.getText());
			sBreedTurns = Double.parseDouble(sharkBreed.getText());
			starveTurns = Double.parseDouble(sharkStarve.getText());
		} else
			ErrorMessages.createErrors("Not Enough Inputs");
	}

	/**
	 * @param numRows
	 * @param numCols
	 * @param cellConfig
	 * @param fishBreed
	 * @param sharkBreed
	 * @param sharkStarve
	 * 
	 *            Saves the state of the current simulation in a file called
	 *            GridConfigurationOutput
	 */
	private void save(String numRows, String numCols, String cellConfig, String fishBreed, String sharkBreed,
			String sharkStarve) {
		try {
			new WatorExporter(numRows, numCols, cellConfig, fishBreed, sharkBreed, sharkStarve).buildXML();
		} catch (NullPointerException e) {
			ErrorMessages.createErrors("No Configuration to Save");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#manualStep()
	 * 
	 * Method directing the normal update behavior of the simulation.
	 */
	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(fBreedTurns, sBreedTurns, starveTurns);
		g.updateGraph();
		sampleGrid.update();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Simulation#step(double)
	 * 
	 * Method directing the overall update behavior of the simulation. If not
	 * paused, the simulation steps as usual, otherwise, all cell movement stops
	 * except for that directed by user mouse clicks. Sharks change into fish, fish
	 * into sharks, and empty/water cells have no click directed behavior.
	 */
	@Override
	protected void step(double elapsedTime) {
		if (isPaused == false) {
			manualStep();
		} else {
			sampleGrid.createPausedGrid(fBreedTurns, sBreedTurns, starveTurns);
			g.updateGraph();
			sampleGrid.update();
		}
	}
}
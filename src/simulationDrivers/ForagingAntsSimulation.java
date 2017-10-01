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
 * 
 * @author Tyler Yam
 * @author Diane Hu This class holds most of the front end processing. This
 *         class essentially runs the simulations.
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

	public ForagingAntsSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}

	@Override
	protected Graph createGraph(Grid g) {
		return new ForagingAntsGraph(g);
	}

	@Override
	protected GridConfiguration setInputConfig(File dataFile) {
		XMLConfiguration = new ForagingAntsReader().getGridConfiguration(dataFile);
		return XMLConfiguration;
	}

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

	@Override
	public Simulation copy() {
		ForagingAntsConfiguration fC = null;
		return new ForagingAntsSimulation(fC, sampleGrid);
	}

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

	private void save(String nR, String nC, String cC, String mA, String hLX, String hLY, String fLX, String fLY) {
		new ForagingAntsExporter(nR, nC, cC, mA, hLX, hLY, fLX, fLY).buildXML();
	}

	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(maxAnts, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}

	@Override
	protected void step(double elapsedTime) {
		if (isPaused == false) {

		} else {
			sampleGrid.createPausedGrid(maxAnts, 0, 0);
			g.updateGraph();
			sampleGrid.update();
		}
	}

	@Override
	protected void userSetThreshold() {
		if (!(threshold.getText().length() == 0)) {
			maxAnts = Double.parseDouble(threshold.getText());
		} else
			ErrorMessages.createErrors("Not Enough Inputs");
	}

}
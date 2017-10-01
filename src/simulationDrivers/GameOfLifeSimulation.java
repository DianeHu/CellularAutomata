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
 * @author Tyler Yam
 * @author Diane Hu This class holds most of the front end processing. This
 *         class essentially runs the simulations.
 */
public class GameOfLifeSimulation extends Simulation {
	
	private int numRows;
	private int numCols;
	private TextField liveConc;
	private TextField deadConc;
	private Map<Character, Double> concMap = new HashMap<>();
	private GameOfLifeConfiguration XMLConfiguration = null;
	
	public GameOfLifeSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}
	
	@Override
	protected void setUpThresholds() {
		numRows = sampleGrid.getNumRows();
		numCols = sampleGrid.getNumCols();
	}
	
	@Override
	protected GridConfiguration setInputConfig(File dataFile) {
		XMLConfiguration = new GameOfLifeReader().getGridConfiguration(dataFile);
		return XMLConfiguration;
	}
	
	@Override
	protected Graph createGraph(Grid g) {
		return new GameOfLifeGraph(g);
	}
	
	@Override
	public Simulation copy() {
		GameOfLifeConfiguration golC = null;
		return new GameOfLifeSimulation(golC, sampleGrid);
	}
	
	@Override
	protected void step(double elapsedTime) {
		if(isPaused == false) {
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
		SimulationButtons.makeButtonH("Save", e->save(Integer.toString(numRows), 
				Integer.toString(numCols), 
				sampleGrid.getGridConfig()), hboxTop, SCREEN_SIZE);
		liveConc = SimulationButtons.makeReturnableTextFieldV("Set live concentration", vboxLeft, -LEFT_OFFSET);
		deadConc = SimulationButtons.makeReturnableTextFieldV("Set dead concentration", vboxLeft, -LEFT_OFFSET);
	}
	
	@Override
	protected void setConcentrations() {
		concMap.put('l', Double.parseDouble(liveConc.getText()));
		concMap.put('d', Double.parseDouble(deadConc.getText()));
		sampleGrid.setConcMap(concMap);
		setConc.setDisable(true);
	}
	
	private void save(String nR, String nC, String cC) {
		new GameOfLifeExporter(nR, nC, cC).buildXML();
	}

	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(0, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}
}
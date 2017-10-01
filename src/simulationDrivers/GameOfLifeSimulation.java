package simulationDrivers;

import XMLClasses.GameOfLifeConfiguration;
import XMLClasses.GameOfLifeExporter;
import XMLClasses.GridConfiguration;
import cellManager.Grid;
import cellManager.RectangleGrid;
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
	
	public GameOfLifeSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}
	
	@Override
	protected void setUpThresholds() {
		numRows = sampleGrid.getNumRows();
		numCols = sampleGrid.getNumCols();
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
	}
	
	private void save(String nR, String nC, String cC) {
		XMLOutput = new GameOfLifeExporter(nR, nC, cC);
		((GameOfLifeExporter) XMLOutput).buildXML();
	}

	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(0, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}
}
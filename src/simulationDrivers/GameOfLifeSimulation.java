package simulationDrivers;

import XMLClasses.GameOfLifeConfiguration;
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
	
	public GameOfLifeSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}
	
	/*private void save(String sT, String nR, String nC, String cC, String pC, String pG, String sT1, String fB,
			String sB, String sS) {
		XMLOutput = new XMLExporter(sT, nR, nC, cC, pC, pG, sT1, fB, sB, sS);
		XMLOutput.buildXML();
	}*/
	
	@Override
	protected void setUpThresholds() {
		// do nothing
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
		// do nothing
	}

	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(0, 0, 0);
		g.updateGraph();
		sampleGrid.update();
	}
}
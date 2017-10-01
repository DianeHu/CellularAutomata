package simulationDrivers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import XMLClasses.GridConfiguration;
import XMLClasses.SegregationReader;
import XMLClasses.WatorConfiguration;
import XMLClasses.WatorExporter;
import XMLClasses.WatorReader;
import XMLClasses.XMLExporter;
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
public class WatorSimulation extends Simulation {

	private TextField fishBreed;
	private TextField sharkBreed;
	private TextField sharkStarve;
	private TextField fishConc;
	private TextField sharkConc;
	private TextField waterConc;
	private Map<Character, Double> concMap = new HashMap<>();
	private double fBreedTurns;
	private double sBreedTurns;
	private double starveTurns;
	private int numRows;
	private int numCols;
	private WatorConfiguration XMLConfiguration = null;
	
	public WatorSimulation(GridConfiguration gC, Grid g) {
		super(gC, g);
	}
	
	@Override
	protected void setUpThresholds() {
		numRows = sampleGrid.getNumRows();
		numCols = sampleGrid.getNumCols();
		fBreedTurns = XMLConfiguration.getFishBreedTurns();
		sBreedTurns = XMLConfiguration.getSharkBreedTurns();
		starveTurns = XMLConfiguration.getSharkStarveTurns();
	}
	
	@Override
	protected Graph createGraph(Grid g) {
		return new WatorGraph(g);
	}
	
	@Override
	public Simulation copy() {
		WatorConfiguration wG = null;
		return new WatorSimulation(wG, sampleGrid);
	}
	
	@Override
	protected void makeSimSpecificFields(Stage s) {
		SimulationButtons.makeButtonH("Save", e->save(Integer.toString(numRows), 
				Integer.toString(numCols), sampleGrid.getGridConfig(), 
				Double.toString(fBreedTurns), Double.toString(sBreedTurns),
				Double.toString(starveTurns)), hboxTop, SCREEN_SIZE);
		fishConc = SimulationButtons.makeReturnableTextFieldV("Set fish concentration", vboxLeft, 3*OFFSET - SCREEN_SIZE);
		sharkConc = SimulationButtons.makeReturnableTextFieldV("Set shark concentration", vboxLeft, 3*OFFSET - SCREEN_SIZE);
		waterConc = SimulationButtons.makeReturnableTextFieldV("Set water concentration", vboxLeft, 3*OFFSET - SCREEN_SIZE);
		fishBreed = SimulationButtons.makeReturnableTextFieldV("Input fishBreed", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		sharkBreed = SimulationButtons.makeReturnableTextFieldV("Input sharkBreed", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		sharkStarve = SimulationButtons.makeReturnableTextFieldV("Input sharkStarve", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e->userSetThreshold(), vboxRight, 3*OFFSET-SCREEN_SIZE);
	}
	
	@Override
	protected void setConcentrations() {
		concMap.put('f', Double.parseDouble(fishConc.getText()));
		concMap.put('s', Double.parseDouble(sharkConc.getText()));
		concMap.put('e', Double.parseDouble(waterConc.getText()));
		sampleGrid.setConcMap(concMap);
		setConc.setDisable(true);
	}
	
	@Override
	protected GridConfiguration setInputConfig(File dataFile) {
		XMLConfiguration = new WatorReader().getGridConfiguration(dataFile);
		return XMLConfiguration;
	}
	
	@Override
	protected void userSetThreshold() {
		fBreedTurns = Double.parseDouble(fishBreed.getText());
		sBreedTurns = Double.parseDouble(sharkBreed.getText());
		starveTurns = Double.parseDouble(sharkStarve.getText());
	}

	private void save(String nR, String nC, String cC, String fB, String sB, String sS) {
		new WatorExporter(nR, nC, cC, fB, sB, sS).buildXML();;
	}
	
	@Override
	protected void manualStep() {
		sampleGrid.createsNewGrid(fBreedTurns, sBreedTurns, starveTurns);
		g.updateGraph();
		sampleGrid.update();
	}
	
	@Override
	protected void step(double elapsedTime) {
		if(isPaused == false) {
			manualStep();
		} else {
			sampleGrid.createPausedGrid(fBreedTurns, sBreedTurns, starveTurns);
			g.updateGraph();
			sampleGrid.update();
		}
	}
}
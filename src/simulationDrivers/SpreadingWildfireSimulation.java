package simulationDrivers;

import java.io.File;

import XMLClasses.GridConfiguration;
import XMLClasses.XMLException;
import XMLClasses.XMLExporter;
import XMLClasses.XMLReader;
import cellManager.Grid;
import cellManager.HexagonGrid;
import cellManager.RectangleGrid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author Tyler Yam
 * @author Diane Hu This class holds most of the front end processing. This
 *         class essentially runs the simulations.
 */
public class SpreadingWildfireSimulation extends Simulation {

	private TextField probGrow;
	private TextField probCatch;
	private double growthProbability = 0.5;
	private double catchProbability = 0.5;
	
	public SpreadingWildfireSimulation(GridConfiguration gC) {
		super(gC);
	}

	@Override
	protected void makeButtons(Stage s) {
		SimulationButtons.makeButtonH("Choose XML File for Configuration", e -> openFile(s), hboxTop, SCREEN_SIZE);
		SimulationButtons.makeButtonH("Start Simulation", e -> startMethod(s), hboxTop, SCREEN_SIZE);
		//SimulationButtons.makeButtonH("Save", e -> save(simType, nRows, nCols, cellConfig, pCatch, pGrow, segThreshold,
		//		fBreedTurns, sBreedTurns, sStarveTurns), hboxTop, SCREEN_SIZE);

		SimulationButtons.makeButtonV("Pause", e -> pause(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Resume", e -> resume(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Speed Up", e -> faster(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Slow Down", e -> slower(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Reset", e -> reset(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Step", e -> manualStep(), vboxRight, SCREEN_SIZE);

		probGrow = SimulationButtons.makeReturnableTextField("Input probGrow", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		probCatch = SimulationButtons.makeReturnableTextField("Input probCatch", vboxRight, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonV("Submit", e->userSetThreshold(), vboxRight, 3*OFFSET-SCREEN_SIZE);
	}
	
	@Override
	protected void userSetThreshold() {
		growthProbability = Double.parseDouble(probGrow.getText());
		growthProbability = Double.parseDouble(probCatch.getText());
	}

	/*private void save(String sT, String nR, String nC, String cC, String pC, String pG, String sT1, String fB,
			String sB, String sS) {
		XMLOutput = new XMLExporter(sT, nR, nC, cC, pC, pG, sT1, fB, sB, sS);
		XMLOutput.buildXML();
	}*/
	
	@Override
	protected void step(double elapsedTime) {
		sampleGrid.createsNewGrid(growthProbability, catchProbability, 0);
		g.updateGraph();
		sampleGrid.update();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
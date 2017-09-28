package simulationDrivers;

import java.io.File;

import XMLClasses.GridConfiguration;
import XMLClasses.XMLException;
import XMLClasses.XMLExporter;
import XMLClasses.XMLReader;
import cellManager.Grid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
 * @author Diane Hu
 * This class holds most of the front end processing.
 * This class essentially runs the simulations.
 */
public class Simulation extends Application {

	private static final Color EMPTY_DISPLAY_BORDER = Color.DARKGRAY;
	private static final Color EMPTY_DISPLAY_BACKGROUND = Color.LIGHTGRAY;
	private static final int GRID_DISPLAY_SIZE = 400;
	private static final String DATA_FILE_EXTENSION = "*.xml";
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private static final int SIZE = 500;
	private static final Color BACKGROUND = Color.BLACK;
	private static final String TITLE = "SIMULATION";
	private static final int FRAMES_PER_SECOND = 2;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private HBox hboxTop = new HBox();
	private VBox vboxRight = new VBox();
	private Group splash = new Group();
	private Group simulationScreen = new Group();
	private Scene myScene;
	private Grid sampleGrid;
	private Stage myStage;
	private GridConfiguration XMLConfiguration;
	private static final int OFFSET = 7;
	private static int SCREEN_SIZE = 200 + OFFSET;
	private static double timePassing = SECOND_DELAY;
	private GridPane emptyPane = new GridPane();
	private BorderPane screenBorder = new BorderPane();
	private Timeline animation = new Timeline();
	private Group root;
	private boolean isFirstTime = true;
	private XMLExporter XMLOutput;
	private static String simType;
	private static String nRows;
	private static String nCols;
	private static String cellConfig;
	private static String pCatch;
	private static String pGrow;
	private static String segThreshold;
	private static String fBreedTurns;
	private static String sBreedTurns;
	private static String sStarveTurns;
	/**
	 * This method starts the application
	 */
	public void start(Stage primaryStage) throws Exception {
		addButtonsToBorder(primaryStage);
	}

	/**
	 * @param s
	 * @throws Exception
	 *             This method add buttons to the Border Pane and calls the
	 *             startSplash and addEvents methods
	 */
	public void addButtonsToBorder(Stage s) throws Exception {	
		
		Rectangle temp = new Rectangle();
		temp.setWidth(GRID_DISPLAY_SIZE);
		temp.setHeight(GRID_DISPLAY_SIZE);
		temp.setFill(EMPTY_DISPLAY_BACKGROUND);
		temp.setStroke(EMPTY_DISPLAY_BORDER);
		GridPane.setRowIndex(temp, 0);
		GridPane.setColumnIndex(temp, 0);
		emptyPane.getChildren().add(temp);
		
		SimulationButtons.makeButtonH("Choose XML File for Configuration", e->openFile(s), hboxTop, SCREEN_SIZE, OFFSET*hboxTop.getChildren().size());
		SimulationButtons.makeButtonH("Start Simulation", e->startMethod(s), hboxTop, SCREEN_SIZE, OFFSET*hboxTop.getChildren().size());
		SimulationButtons.makeButtonH("Save", e->save(simType, nRows, nCols, cellConfig, pCatch, pGrow, segThreshold, fBreedTurns, sBreedTurns, sStarveTurns), hboxTop, SCREEN_SIZE, OFFSET*hboxTop.getChildren().size());
		
		SimulationButtons.makeButtonV("Pause", e->pause(), vboxRight, SCREEN_SIZE, OFFSET*vboxRight.getChildren().size());
		SimulationButtons.makeButtonV("Resume", e->resume(), vboxRight, SCREEN_SIZE, OFFSET*vboxRight.getChildren().size());
		SimulationButtons.makeButtonV("Speed Up", e->faster(), vboxRight, SCREEN_SIZE, OFFSET*vboxRight.getChildren().size());
		SimulationButtons.makeButtonV("Slow Down", e->slower(), vboxRight, SCREEN_SIZE, OFFSET*vboxRight.getChildren().size());
		SimulationButtons.makeButtonV("Reset", e->reset(), vboxRight, SCREEN_SIZE, OFFSET*vboxRight.getChildren().size());
		SimulationButtons.makeButtonV("Step", e->manualStep(), vboxRight, SCREEN_SIZE, OFFSET*vboxRight.getChildren().size());
		
		hboxTop.setPadding(new Insets(OFFSET));
		hboxTop.setSpacing(OFFSET);
		
		vboxRight.setPadding(new Insets(OFFSET));
		vboxRight.setSpacing(OFFSET);
		
		screenBorder.setCenter(emptyPane);
		screenBorder.setTop(hboxTop);
		screenBorder.setRight(vboxRight);
		
		splash.getChildren().add(screenBorder);
		
		setUpStage(s, new Scene(splash, SIZE, SIZE, BACKGROUND));
	}

	private void setUpStage(Stage s, Scene scene) {
		myStage = s;
		myStage.setScene(scene);
		myStage.setTitle(TITLE);
		myStage.show();
	}
	
	private void startMethod(Stage s) {
		try {
			startSimulation(s);
		} catch (Exception e1) {
			ErrorMessages.createErrors("Failed to Start\nChoose Valid Configuration File");
		}
	}

	/**
	 * This method allows the stepButton to step through our grid every time it is
	 * click
	 */
	private void manualStep() {
		sampleGrid.createsNewGrid();
		sampleGrid.update();
	}
	
	/**
	 * @param s
	 *            This method opens the file chooser to input in an XML
	 */
	private void openFile(Stage s) {
		File dataFile = myChooser.showOpenDialog(s);
		GridConfiguration InputConfiguration = null;
		if (dataFile != null) {
			try {
				InputConfiguration = new XMLReader().getGridConfiguration(dataFile);
			} catch (XMLException e) {
				Alert a = new Alert(AlertType.ERROR);
				a.setContentText(e.getMessage());
				a.showAndWait();
			}
			XMLConfiguration = InputConfiguration;
		} else {
			// nothing selected, so quit the application
			ErrorMessages.createErrors("No File Chosen");
			Platform.exit();
		}

	}

	/**
	 * @param s
	 * @throws Exception
	 *             This method starts the simulation
	 */
	public void startSimulation(Stage s) throws Exception {

		// attach scene to the stage and display it
		setUpStage(s, setSimulation());
		// attach "game loop" to timeline to play it
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(timePassing));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	/**
	 * @param xml
	 * @return This method sets up the scene upon which the simulation will run and
	 *         returns it
	 */
	private Scene setSimulation() {
		root = new Group();
		sampleGrid = new Grid(root, XMLConfiguration);
		sampleGrid.initialize();
		screenBorder.setCenter(root);
		
		if (isFirstTime == true) {
			simulationScreen.getChildren().add(screenBorder);
			myScene = new Scene(simulationScreen, SIZE, SIZE, BACKGROUND);
		}
		isFirstTime = false;
		
		return myScene;
	}

	/**
	 * @param elapsedTime
	 *            This method iterates through the grid, updating it as the
	 *            simulation runs
	 */
	private void step(double elapsedTime) {
		sampleGrid.createsNewGrid();
		sampleGrid.update();
	}

	/**
	 * This method resumes the simulation after it is paused
	 */
	private void resume() {
		animation.play();
	}

	/**
	 * This method pauses the simulation
	 */
	private void pause() {
		animation.pause();
	}

	/**
	 * This method steps through the simulation at twice the speed
	 */
	private void faster() {
		timePassing *= 2;
		animation.setRate(timePassing);

	}
	
	private void save(String sT, String nR, String nC, String cC, String pC, String pG, String sT1, String fB, String sB, String sS) {
		XMLOutput = new XMLExporter(sT, nR, nC, cC, pC, pG, sT1, fB, sB, sS);
		XMLOutput.buildXML();
	}

	/**
	 * This method steps through the simulation at half the speed
	 */
	private void slower() {
		timePassing *= .5;
		animation.setRate(timePassing);
	}

	/**
	 * @param extensionAccepted
	 * @return This method makes the FileChooser object
	 */
	private FileChooser makeChooser(String extensionAccepted) {
		FileChooser result = new FileChooser();
		result.setTitle("Open Data File");
		result.setInitialDirectory(new File(System.getProperty("user.dir")));
		result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
		return result;
	}

	/**
	 * This method resets the grid pane so that a new file can be put in
	*/
	private void reset() {
		screenBorder.getChildren().remove(root);
		screenBorder.setRight(vboxRight);
		screenBorder.setCenter(emptyPane);
		timePassing = SECOND_DELAY;
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}

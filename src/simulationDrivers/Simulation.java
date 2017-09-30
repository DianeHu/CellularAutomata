package simulationDrivers;

import java.io.File;

import XMLClasses.GameOfLifeReader;
import XMLClasses.GridConfiguration;
import XMLClasses.SegregationReader;
import XMLClasses.SpreadingWildfireConfiguration;
import XMLClasses.ForagingAntsConfiguration;
import XMLClasses.ForagingAntsReader;
import XMLClasses.SpreadingWildfireReader;
import XMLClasses.WatorConfiguration;
import XMLClasses.WatorReader;
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
public abstract class Simulation extends Application {

	private static final Color EMPTY_DISPLAY_BORDER = Color.DARKGRAY;
	private static final Color EMPTY_DISPLAY_BACKGROUND = Color.LIGHTGRAY;
	private static final int GRID_DISPLAY_SIZE = 400;
	private static final String DATA_FILE_EXTENSION = "*.xml";
	//private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private static final int VERT_SIZE = 650;
	private static final int HORIZONTAL_SIZE = 550;
	private static final Color BACKGROUND = Color.TRANSPARENT;
	private static final String TITLE = "SIMULATION";
	private static final int FRAMES_PER_SECOND = 2;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	protected HBox hboxTop = new HBox();
	protected VBox vboxRight = new VBox();
	private HBox hboxBottom = new HBox();
	private Group splash = new Group();
	private Group simulationScreen = new Group();
	private Scene myScene;
	protected Grid sampleGrid;
	private Stage myStage;
	protected Button submit;
	protected GridConfiguration XMLConfiguration;
	protected static final int OFFSET = 7;
	protected static int SCREEN_SIZE = 200 + OFFSET;
	private static double timePassing = SECOND_DELAY;
	private GridPane emptyPane = new GridPane();
	private BorderPane screenBorder = new BorderPane();
	private Timeline animation = new Timeline();
	private Group root;
	private boolean isFirstTime = true;
	protected Graph g;
	protected boolean isPaused = false;
	private String simType;
	// private ScrollPane gridScroll;
	// private final ScrollBar sc = new ScrollBar();
	private XMLExporter XMLOutput;

	public Simulation(GridConfiguration gC, Grid g) {
		XMLConfiguration = gC;
		sampleGrid = g;
	}

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

		makeButtons(s);
		hboxTop.setPadding(new Insets(OFFSET));
		hboxTop.setSpacing(OFFSET);

		vboxRight.setPadding(new Insets(OFFSET));
		vboxRight.setSpacing(OFFSET);

		screenBorder.setLeft(emptyPane);
		screenBorder.setTop(hboxTop);
		screenBorder.setCenter(vboxRight);
		// screenBorder.setCenter(hboxCenter);
		screenBorder.setBottom(hboxBottom);
		// HBox.setHgrow(g.getLineChart(), Priority.ALWAYS);
		// screenBorder.setPrefSize(SIZE, SIZE);
		screenBorder.setPrefSize(HORIZONTAL_SIZE, VERT_SIZE);
		screenBorder.getStyleClass().add("pane");
		splash.getChildren().add(screenBorder);
		splash.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());

		Scene scene = new Scene(splash, HORIZONTAL_SIZE, VERT_SIZE);
		setUpStage(s, scene);
		scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
	}

	public void setSimType(String s) {
		simType = s;
	}

	private void makeButtons(Stage s) {
		SimulationButtons.makeButtonH("Choose XML File for Configuration", e -> openFile(s), hboxTop, SCREEN_SIZE);
		SimulationButtons.makeButtonH("Start Simulation", e -> startMethod(s), hboxTop, SCREEN_SIZE);
		// SimulationButtons.makeButtonH("Save", e -> save(simType, nRows, nCols,
		// cellConfig, pCatch, pGrow, segThreshold,
		// fBreedTurns, sBreedTurns, sStarveTurns), hboxTop, SCREEN_SIZE);

		SimulationButtons.makeButtonV("Pause", e -> pause(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Resume", e -> resume(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Speed Up", e -> faster(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Slow Down", e -> slower(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Reset", e -> reset(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Step", e -> manualStep(), vboxRight, SCREEN_SIZE);
		makeSimSpecificFields(s);
	}

	protected abstract void makeSimSpecificFields(Stage s);

	private void setUpStage(Stage s, Scene scene) {
		myStage = s;
		myStage.setScene(scene);
		myStage.setTitle(TITLE);
		myStage.show();
	}

	protected void startMethod(Stage s) {
		try {
			startSimulation(s);
		} catch (Exception e1) {
			e1.printStackTrace();
			// ErrorMessages.createErrors("Failed to Start\nChoose Valid Configuration
			// File");
		}
	}

	/**
	 * This method allows the stepButton to step through our grid every time it is
	 * click
	 */
	protected abstract void manualStep();

	/**
	 * @param s
	 *            This method opens the file chooser to input in an XML
	 */
	protected void openFile(Stage s) {
		FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
		File dataFile = myChooser.showOpenDialog(s);
		GridConfiguration InputConfiguration = null;
		if (dataFile != null) {
			try {
				switch (simType) {
				case ("Wator"):
					InputConfiguration = new WatorReader().getGridConfiguration(dataFile);
					break;
				case ("SpreadingWildfire"):
					InputConfiguration = new SpreadingWildfireReader().getGridConfiguration(dataFile);
					break;
				case ("GameOfLife"):
					InputConfiguration = new GameOfLifeReader().getGridConfiguration(dataFile);
					break;
				case ("Segregation"):
					InputConfiguration = new SegregationReader().getGridConfiguration(dataFile);
					break;
				case ("ForagingAnts"):
					InputConfiguration = new ForagingAntsReader().getGridConfiguration(dataFile);
					break;
				}
			} catch (XMLException e) {
				throw e;
			}
			XMLConfiguration = InputConfiguration;
		} else {
			// nothing selected, so quit the application
			ErrorMessages.createErrors("No File Chosen");
			//Platform.exit();
		}
	}

	public abstract Simulation copy();

	/**
	 * @param s
	 * @throws Exception
	 *             This method starts the simulation
	 */
	public void startSimulation(Stage s) throws Exception {

		// attach scene to the stage and display it
		setUpStage(s, setSimulation());
		setUpThresholds();
		// attach "game loop" to timeline to play it
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(timePassing));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	protected abstract void setUpThresholds();

	/**
	 * @param xml
	 * @return This method sets up the scene upon which the simulation will run and
	 *         returns it
	 */
	private Scene setSimulation() {
		root = new Group();
		sampleGrid = new RectangleGrid(root, XMLConfiguration);
		sampleGrid.setSimType(simType);
		sampleGrid.initialize();
		g = new Graph(sampleGrid);
		g.addToBox(hboxBottom);
		screenBorder.setLeft(root);
		screenBorder.getStyleClass().add("pane");

		if (isFirstTime == true) {
			simulationScreen.getChildren().add(screenBorder);
			simulationScreen.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
			myScene = new Scene(simulationScreen, HORIZONTAL_SIZE, VERT_SIZE, BACKGROUND);
			myScene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
		}
		isFirstTime = false;

		return myScene;
	}

	/**
	 * @param elapsedTime
	 *            This method iterates through the grid, updating it as the
	 *            simulation runs
	 */
	protected abstract void step(double elapsedTime);

	/**
	 * This method resumes the simulation after it is paused
	 */
	protected void resume() {
		// animation.play();
		// sampleGrid.setPaused(false);
		isPaused = false;
	}

	protected abstract void userSetThreshold();

	/**
	 * This method pauses the simulation
	 */
	protected void pause() {
		// animation.pause();
		// sampleGrid.setPaused(true);
		isPaused = true;
	}

	/**
	 * This method steps through the simulation at twice the speed
	 */
	protected void faster() {
		timePassing *= 2;
		animation.setRate(timePassing);
	}

	/*
	 * private void save(String sT, String nR, String nC, String cC, String pC,
	 * String pG, String sT1, String fB, String sB, String sS) { XMLOutput = new
	 * XMLExporter(sT, nR, nC, cC, pC, pG, sT1, fB, sB, sS); XMLOutput.buildXML(); }
	 */

	/**
	 * This method steps through the simulation at half the speed
	 */
	protected void slower() {
		timePassing *= .5;
		animation.setRate(timePassing);
	}

	/**
	 * @param extensionAccepted
	 * @return This method makes the FileChooser object
	 */
	protected FileChooser makeChooser(String extensionAccepted) {
		FileChooser result = new FileChooser();
		result.setTitle("Open Data File");
		result.setInitialDirectory(new File(System.getProperty("user.dir")));
		result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
		return result;
	}

	/**
	 * This method resets the grid pane so that a new file can be put in
	 */
	protected void reset() {
		screenBorder.getChildren().remove(root);
		screenBorder.setCenter(vboxRight);
		screenBorder.setLeft(emptyPane);
		screenBorder.getStyleClass().add("pane");
		// vboxRight.getChildren().clear();
		timePassing = SECOND_DELAY;

	}
}
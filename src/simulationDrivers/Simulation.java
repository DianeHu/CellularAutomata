package simulationDrivers;

import java.io.File;

import XMLClasses.GridConfiguration;
import XMLClasses.XMLException;
import cellManager.Grid;
import cellManager.RectangleGrid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
 * @author Diane Hu This class holds most of the front end processing. This
 *         class essentially runs the simulations.
 */
public abstract class Simulation extends Application {

	private static final Color EMPTY_DISPLAY_BORDER = Color.DARKGRAY;
	private static final Color EMPTY_DISPLAY_BACKGROUND = Color.LIGHTGRAY;
	private static final int GRID_DISPLAY_SIZE = 400;
	private static final String DATA_FILE_EXTENSION = "*.xml";
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private static final int VERT_SIZE = 650;
	protected static final int LEFT_OFFSET = 35;
	private static final int HORIZONTAL_SIZE = 725;
	private static final Color BACKGROUND = Color.TRANSPARENT;
	private static final String TITLE = "SIMULATION";
	private static final int FRAMES_PER_SECOND = 2;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	protected HBox hboxTop = new HBox();
	protected VBox vboxRight = new VBox();
	protected VBox vboxLeft = new VBox();
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
	protected String simType;
	private Button startButton;
	protected Button setConc;
	private Button stepButton;
	// private ScrollPane gridScroll;
	// private final ScrollBar sc = new ScrollBar();

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
	private void addButtonsToBorder(Stage s) throws Exception {
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
		
		vboxLeft.setPadding(new Insets(OFFSET));
		vboxLeft.setSpacing(OFFSET);
		
		screenBorder.setLeft(vboxLeft);
		screenBorder.setCenter(emptyPane);
		screenBorder.setTop(hboxTop);
		screenBorder.setRight(vboxRight);
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
		startButton = SimulationButtons.makeReturnableButtonH("Start Simulation", e -> startMethod(s), hboxTop, SCREEN_SIZE);
		setConc = SimulationButtons.makeReturnableButtonV("Set conc.", e->setConcentrations(), vboxLeft, OFFSET + 3*LEFT_OFFSET);
		SimulationButtons.makeButtonV("Pause", e -> pause(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Resume", e -> resume(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Speed Up", e -> faster(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Slow Down", e -> slower(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV("Reset", e -> reset(), vboxRight, SCREEN_SIZE);
		stepButton = SimulationButtons.makeReturnableButtonV("Step", e -> manualStep(), vboxRight, SCREEN_SIZE);
		makeSimSpecificFields(s);
	}

	protected abstract void setConcentrations();
	
	protected abstract void makeSimSpecificFields(Stage s);

	private void setUpStage(Stage s, Scene scene) {
		myStage = s;
		myStage.setScene(scene);
		myStage.setTitle(TITLE);
		myStage.show();
	}

	private void startMethod(Stage s) {
		try {
			startSimulation(s);
			startButton.setDisable(true);
			setConc.setDisable(true);
			stepButton.setDisable(true);
		} catch (Exception e1) {
			e1.printStackTrace();
			ErrorMessages.createErrors("Failed to Start\nChoose Valid Configuration File");
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
	private void openFile(Stage s) {
		animation.pause();
		File dataFile = myChooser.showOpenDialog(s);
		if (dataFile != null) {
			try {
				XMLConfiguration = setInputConfig(dataFile);
			} catch (XMLException e) {
				throw e;
			}
			startButton.setDisable(false);
			hboxBottom.getChildren().clear();
		} else {
			ErrorMessages.createErrors("No File Chosen");
			startButton.setDisable(true);
		}
	}
	
	protected abstract GridConfiguration setInputConfig (File datafile);

	public abstract Simulation copy();

	/**
	 * @param s
	 * @throws Exception
	 *             This method starts the simulation
	 */
	private void startSimulation(Stage s) throws Exception {

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
		g = createGraph(sampleGrid);
		g.addToBox(hboxBottom);
		screenBorder.setCenter(root);
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
	
	//protected abstract void createGrid(Group root);
	
	protected abstract Graph createGraph(Grid g);

	/**
	 * @param elapsedTime
	 *            This method iterates through the grid, updating it as the
	 *            simulation runs
	 */
	protected abstract void step(double elapsedTime);

	/**
	 * This method resumes the simulation after it is paused
	 */
	private void resume() {
		// animation.play();
		// sampleGrid.setPaused(false);
		isPaused = false;
	}

	protected abstract void userSetThreshold();

	/**
	 * This method pauses the simulation
	 */
	private void pause() {
		// animation.pause();
		// sampleGrid.setPaused(true);
		isPaused = true;
		stepButton.setDisable(false);
	}

	/**
	 * This method steps through the simulation at twice the speed
	 */
	private void faster() {
		timePassing *= 2;
		animation.setRate(timePassing);
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
		startButton.setDisable(false);
		setConc.setDisable(false);
		screenBorder.getChildren().remove(root);
		screenBorder.setLeft(vboxLeft);
		screenBorder.setRight(vboxRight);
		screenBorder.setCenter(emptyPane);
		screenBorder.getStyleClass().add("pane");
		hboxBottom.getChildren().remove(g.getLineChart());
		timePassing = SECOND_DELAY;

	}
}
package simulationDrivers;

import java.io.File;
import java.util.ResourceBundle;

import XMLClasses.GridConfiguration;
import XMLClasses.XMLException;
import cellManager.Grid;
import cellManager.HexagonGrid;
import cellManager.RectangleGrid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
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
 * @author Diane Hu
 * @author Tyler Yam
 * 
 *         This class is the simulation superclass. It controls the
 *         functionality of a single simulation instantiation in the subclasses,
 *         and is able to set grid edge style, shape, etc. based on booleans
 *         passed through from the main driver. It depends on the Grid class and
 *         XML classes. Should be used by instantiating a subclass, or adding a
 *         subclass for a new simulation.
 */
public abstract class Simulation extends Application {

	private static final Color EMPTY_DISPLAY_BORDER = Color.DARKGRAY;
	private static final Color EMPTY_DISPLAY_BACKGROUND = Color.LIGHTGRAY;
	private static final int GRID_DISPLAY_SIZE = 400;
	private static final String DATA_FILE_EXTENSION = "*.xml";
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private static final int VERT_SIZE = 650;
	private static final int HORIZONTAL_SIZE = 575;
	private static final Color BACKGROUND = Color.TRANSPARENT;
	private static final String TITLE = "SIMULATION";
	private static final int FRAMES_PER_SECOND = 2;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private HBox hboxBottom = new HBox();
	private Group splash = new Group();
	private Group simulationScreen = new Group();
	private Scene myScene;
	private static double timePassing = SECOND_DELAY;
	private GridPane emptyPane = new GridPane();
	private BorderPane screenBorder = new BorderPane();
	private Timeline animation = new Timeline();
	private Group root;
	private Stage myStage;
	private String simType;
	private Button startButton;
	private Button stepButton;
	private Boolean isRectangle = true;
	private Button resetButton;
	private Boolean isToroidal = false;
	private Boolean isMaxNeighbors = true;
	/**
	 * We decided the following variables would be protected since only Simulation
	 * subclasses have access to these implementation features. As such, no other
	 * classes other than those that directly instantiate simulations have access,
	 * and doing it any other way would require an excessive number of getters and
	 * setters.
	 */
	protected HBox hboxTop = new HBox();
	protected VBox vboxRight = new VBox();
	protected Grid sampleGrid;
	protected Button submit;
	protected GridConfiguration XMLConfiguration;
	protected static final int OFFSET = 7;
	protected static int SCREEN_SIZE = 200 + OFFSET;
	protected boolean isFirstTime = true;
	protected Graph g;
	protected boolean isPaused = false;
	protected boolean isStarted = false;
	protected Button saveButton;
	
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	/**
	 * @param gC
	 * @param g
	 *            Simulation constructor with a GridConfiguration and a Grid
	 */
	public Simulation(GridConfiguration gC, Grid g) {
		XMLConfiguration = gC;
		sampleGrid = g;
	}

	/**
	 * This method starts the application by adding the initial buttons to the
	 * original screen
	 */
	public void start(Stage primaryStage) throws Exception {
		addButtonsToBorder(primaryStage);
	}

	/**
	 * @param s
	 * @throws Exception
	 *             This method adds buttons to the Border Pane and calls the
	 *             startSplash and addEvents methods, creating the basic look of the
	 *             screen when no movement has been called yet.
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

		screenBorder.setCenter(emptyPane);
		screenBorder.setTop(hboxTop);
		screenBorder.setRight(vboxRight);
		screenBorder.setBottom(hboxBottom);

		screenBorder.setPrefSize(HORIZONTAL_SIZE, VERT_SIZE);
		screenBorder.getStyleClass().add("pane");
		splash.getChildren().add(screenBorder);
		splash.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());

		Scene scene = new Scene(splash, HORIZONTAL_SIZE, VERT_SIZE);
		setUpStage(s, scene);
		scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
	}

	/**
	 * @param s
	 *            Sets the current simulation type string
	 */
	public void setSimType(String s) {
		simType = s;
	}

	/**
	 * @param b
	 *            Sets whether or not the current simulation runs on a rectangleGrid
	 */
	public void setIsRectangle(Boolean b) {
		isRectangle = b;
	}

	/**
	 * @param b
	 *            Sets the type of neighbors a grid should use--max (all 8) or not
	 *            (4).
	 */
	public void setMaxNeighbors(Boolean b) {
		isMaxNeighbors = b;
	}

	/**
	 * @param b
	 *            Sets whether or not a simulation runs on a toroidal edged Grid
	 */
	public void setIsToroidal(Boolean b) {
		isToroidal = b;
	}

	/**
	 * @param s
	 *            Makes the generic buttons every simulation needs, calls
	 *            makeSimSpecificFields to make the buttons specific to each
	 *            subsimulation.
	 */
	private void makeButtons(Stage s) {
		SimulationButtons.makeButtonH(myResources.getString("choose"), e -> openFile(s), hboxTop, SCREEN_SIZE);
		startButton = SimulationButtons.makeReturnableButtonH(myResources.getString("start"), e -> startMethod(s), hboxTop,
				SCREEN_SIZE);
		SimulationButtons.makeButtonV(myResources.getString("pauseButton"), e -> pause(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV(myResources.getString("resumeButton"), e -> resume(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV(myResources.getString("speedup"), e -> faster(), vboxRight, SCREEN_SIZE);
		SimulationButtons.makeButtonV(myResources.getString("slowdown"), e -> slower(), vboxRight, SCREEN_SIZE);
		resetButton = SimulationButtons.makeReturnableButtonV(myResources.getString("resetButton"), e -> reset(), vboxRight, SCREEN_SIZE);
		stepButton = SimulationButtons.makeReturnableButtonV(myResources.getString("stepButton"), e -> manualStep(), vboxRight, SCREEN_SIZE);
		resetButton.setDisable(!isStarted);
		stepButton.setDisable(!(isPaused & isStarted));
		makeSimSpecificFields(s);
		saveButton.setDisable(!isStarted);
	}

	/**
	 * @param s
	 *            Abstract method replicated in the subclasses that makes the
	 *            necessary specific buttons/textfields.
	 */
	protected abstract void makeSimSpecificFields(Stage s);

	private void setUpStage(Stage s, Scene scene) {
		myStage = s;
		myStage.setScene(scene);
		myStage.setTitle(TITLE);
		myStage.show();
	}

	/**
	 * @param s
	 *            Starts the simulation, and flips the disables of buttons as
	 *            appropriate.
	 */
	private void startMethod(Stage s) {
		try {
			startSimulation(s);
			isStarted = true;
			startButton.setDisable(isStarted);
			resetButton.setDisable(!isStarted);
			saveButton.setDisable(!isStarted);
		} catch (Exception e1) {
			ErrorMessages.createErrors(myResources.getString("chooseFail"));
			e1.printStackTrace();
		}
	}

	/**
	 * This method calls a single step of the simulation, to step through once.
	 * Called as an ordinary step when the simulation isn't paused, or when the step
	 * button is clicked when the simulation is paused. Abstracted so subsimulations
	 * can update their specific graphs appropriately.
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
				ErrorMessages.createErrors(myResources.getString("invalidxml"));
			}
			startButton.setDisable(false);
			hboxBottom.getChildren().clear();
		} else {
			ErrorMessages.createErrors(myResources.getString("nofilechosen"));
			startButton.setDisable(true);
		}
	}

	/**
	 * @param datafile
	 * @return Returns a GridConfiguration appropriate to each simulation type. This
	 *         method is called in openFile in order to instantiate the correct
	 *         XMLConfiguration per simulation type.
	 */
	protected abstract GridConfiguration setInputConfig(File datafile);

	/**
	 * @return Returns a copy of the current simulation as appropriate to the
	 *         simulation type. Used to simplify simulation instantiation by user
	 *         choice--see Main. Used to call Simulation instantiation through a map
	 *         that has Simulations as values.
	 */
	public abstract Simulation copy();

	/**
	 * @param s
	 * @throws Exception
	 *             This method starts the simulation, and sets up the appropriate
	 *             thresholds for the simulation.
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

	/**
	 * Abstract threshold setter-upper replicated in subclasses, to update
	 * simulation specific thresholds as necessary.
	 */
	protected abstract void setUpThresholds();

	/**
	 * @param xml
	 * @return This method sets up the scene upon which the simulation will run and
	 *         returns it. In order to avoid duplicated children, it only sets the
	 *         screenBorder borderpane once per simulation. Its children are
	 *         manipulated separately.
	 */
	private Scene setSimulation() {
		root = new Group();
		if (isRectangle) {
			sampleGrid = new RectangleGrid(root, XMLConfiguration);
		} else {
			sampleGrid = new HexagonGrid(root, XMLConfiguration);
		}
		sampleGrid.setSimType(simType);
		sampleGrid.initialize();
		sampleGrid.setMaxNeighbors(isMaxNeighbors);
		sampleGrid.setIsToroidal(isToroidal);
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

	/**
	 * @param g
	 * @return Abstract method used in subclasses to create graphs specific to
	 *         simulations.
	 */
	protected abstract Graph createGraph(Grid g);

	/**
	 * @param elapsedTime
	 *            This method iterates through the grid, updating it as the
	 *            simulation runs. Abstracted into subclasses to perform simulation
	 *            specific behavior.
	 */
	protected abstract void step(double elapsedTime);

	/**
	 * This method resumes the simulation after it is paused
	 */
	private void resume() {
		isPaused = false;
		stepButton.setDisable(!(isPaused && isStarted));
	}

	/**
	 * This abstract method directs what happens when the user wishes to set/reset
	 * the thresholds of the simulation mid simulation. Abstracted as different
	 * simulations have different thresholds to update.
	 */
	protected abstract void userSetThreshold();

	/**
	 * This method pauses the simulation
	 */
	private void pause() {
		isPaused = true;
		stepButton.setDisable(!(isPaused && isStarted));
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
	 * @return This method makes the FileChooser object that allows users to open an
	 *         XML File.
	 */
	private FileChooser makeChooser(String extensionAccepted) {
		FileChooser result = new FileChooser();
		result.setTitle(myResources.getString("open"));
		result.setInitialDirectory(new File(System.getProperty("user.dir")));
		result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
		return result;
	}

	/**
	 * This method resets the grid pane so that a new file can be put in. Also
	 * removes the current graph.
	 */
	private void reset() {
		startButton.setDisable(false);
		screenBorder.getChildren().remove(root);
		screenBorder.setRight(vboxRight);
		screenBorder.setCenter(emptyPane);
		screenBorder.getStyleClass().add("pane");
		hboxBottom.getChildren().remove(g.getLineChart());
		timePassing = SECOND_DELAY;

	}
}
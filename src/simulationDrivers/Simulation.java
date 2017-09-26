package simulationDrivers;

import java.io.File;

import XMLClasses.GridConfiguration;
import XMLClasses.XMLException;
import XMLClasses.XMLReader;
import cellManager.Grid;
import cellManager.RectangleGrid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
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
	private static final int SIZE = 600;
	private static final Color BACKGROUND = Color.TRANSPARENT;
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
	private static Button fileChooserButton;
	private static Button startButton;
	private static Button pauseButton;
	private static Button resumeButton;
	private static Button fasterButton;
	private static Button slowerButton;
	private static Button resetButton;
	private static Button stepButton;
	private static double timePassing = SECOND_DELAY;
	private GridPane emptyPane = new GridPane();
	private BorderPane screenBorder = new BorderPane();
	private Timeline animation = new Timeline();
	private Group root;
	private boolean isFirstTime = true;
	private Graph g;

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
		SimulationButtons.initializeTop(hboxTop);
		SimulationButtons.initializeRight(vboxRight);
		
		fileChooserButton = (Button) hboxTop.getChildren().get(0);
		startButton = (Button) hboxTop.getChildren().get(1);
		
		Rectangle temp = new Rectangle();
		temp.setWidth(GRID_DISPLAY_SIZE);
		temp.setHeight(GRID_DISPLAY_SIZE);
		temp.setFill(EMPTY_DISPLAY_BACKGROUND);
		temp.setStroke(EMPTY_DISPLAY_BORDER);
		GridPane.setRowIndex(temp, 0);
		GridPane.setColumnIndex(temp, 0);
		emptyPane.getChildren().add(temp);
		
		pauseButton = (Button) vboxRight.getChildren().get(0);
		resumeButton = (Button) vboxRight.getChildren().get(1);
		fasterButton = (Button) vboxRight.getChildren().get(2);
		slowerButton = (Button) vboxRight.getChildren().get(3);
		resetButton = (Button) vboxRight.getChildren().get(4);
		stepButton = (Button) vboxRight.getChildren().get(5);
		
		g = new Graph();
		g.addToBox(vboxRight);
		
		screenBorder.setCenter(emptyPane);
		screenBorder.setTop(hboxTop);
		screenBorder.setRight(vboxRight);
		screenBorder.setPrefSize(SIZE, SIZE);
		screenBorder.getStyleClass().add("pane");
		splash.getChildren().add(screenBorder);
		splash.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
		
		Scene scene = new Scene(splash, SIZE, SIZE);
		setUpStage(s, scene);
		scene.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
		addEvents(s);

	}
	
	private void setUpStage(Stage s, Scene scene) {
		myStage = s;
		myStage.setScene(scene);
		myStage.setTitle(TITLE);
		myStage.show();
	}

	/**
	 * @param s
	 *            This method adds functionality to each button. This method calls
	 *            openFile, startSimulation, pause, resume, slower, faster, reset,
	 *            and manualStep
	 */
	private void addEvents(Stage s) {

		fileChooserButton.setOnAction(e -> openFile(s));
		startButton.setOnAction(e -> {
			try {
				startSimulation(s);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		pauseButton.setOnAction(e -> pause());
		resumeButton.setOnAction(e -> resume());
		slowerButton.setOnAction(e -> slower());
		fasterButton.setOnAction(e -> faster());
		resetButton.setOnAction(e -> reset());
		stepButton.setOnAction(e -> manualStep());
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
		sampleGrid = new RectangleGrid(root, XMLConfiguration);
		sampleGrid.initialize();
		screenBorder.setCenter(root);
		screenBorder.getStyleClass().add("pane");
		
		if (isFirstTime == true) {
			simulationScreen.getChildren().add(screenBorder);
			simulationScreen.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
			myScene = new Scene(simulationScreen, SIZE, SIZE, BACKGROUND);
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
	private void step(double elapsedTime) {
		sampleGrid.createsNewGrid();
		sampleGrid.update();
		g.updateGraph();
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
		screenBorder.getStyleClass().add("pane");
		timePassing = SECOND_DELAY;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
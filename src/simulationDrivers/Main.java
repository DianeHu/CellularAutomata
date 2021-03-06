package simulationDrivers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import XMLClasses.ForagingAntsConfiguration;
import XMLClasses.GameOfLifeConfiguration;
import XMLClasses.RPSConfiguration;
import XMLClasses.SegregationConfiguration;
import XMLClasses.SpreadingWildfireConfiguration;
import XMLClasses.StyleConfiguration;
import XMLClasses.StyleReader;
import XMLClasses.WatorConfiguration;
import XMLClasses.XMLException;
import cellManager.HexagonGrid;
import cellManager.RectangleGrid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Diane Hu Simulation main driver class. This class runs the actual
 *         cellular automata simulation, using instantiations of specific
 *         simulations (GameOfLife, SpreadingWildfire, RPS, Wator, Segregation,
 *         ForagingAnts). Depends on the simulation class/subclasses and the XML
 *         classes. Use to run a cellular automaton.
 * 
 *         Note: the simulation names given above are the ones that work if
 *         typed into the simulation chooser. Any others will give an error
 *         dialogue box.
 */
public class Main extends Application {

	private WatorConfiguration wG = null;
	private SpreadingWildfireConfiguration sWG = null;
	private GameOfLifeConfiguration gofC = null;
	private SegregationConfiguration sC = null;
	private ForagingAntsConfiguration faC = null;
	private RPSConfiguration rpsC = null;
	private Stage myStage = new Stage();
	private TextField simType = new TextField();
	private HBox hbox = new HBox();
	private int SCREEN_SIZE = 450;
	private int OFFSET = 7;
	private Button submit = new Button();
	private String simulationSetByUser;
	private RectangleGrid recGrid;
	private HexagonGrid hexGrid;
	private BorderPane border = new BorderPane();
	private Group startScreen = new Group();
	private Map<String, Simulation> pickRecSimByName;
	private Map<String, Simulation> pickHexSimByName;
	private Map<String, Simulation> simMap;
	private static final int FRAMES_PER_SECOND = 2;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private Timeline animation = new Timeline();
	private static final String DATA_FILE_EXTENSION = "*.xml";
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private StyleConfiguration styler = null;
	private boolean isRectangle = false;
	private boolean isStylish = false;
	
	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage) Creates the
	 * initial screen where users pick simulation type and upload style files.
	 */
	@Override
	public void start(Stage myStage) throws Exception {
		setUpStage(myStage, startSimPicker(myStage));

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	/**
	 * @param s
	 * @return
	 * @throws Exception,
	 *             invalid simulation type
	 * 
	 *             This method initializes all simulation maps, and creates the
	 *             buttons the user sees on the initial screen.
	 */
	private Scene startSimPicker(Stage s) throws Exception {
		initMap();
		hbox.setPadding(new Insets(OFFSET));
		hbox.setSpacing(OFFSET);
		SimulationButtons.makeButtonH(myResources.getString("choosestyle"), e -> openStyleFile(s), hbox, SCREEN_SIZE);
		simType = SimulationButtons.makeReturnableTextFieldH(myResources.getString("choosesim"), hbox, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonH(myResources.getString("createsim"), e -> {
			try {
				setSim();
			} catch (Exception e1) {
				ErrorMessages.createErrors(myResources.getString("invalidsim"));
			}
		}, hbox, 3 * OFFSET - SCREEN_SIZE);
		submit.setDisable(!isStylish);
		border.setCenter(hbox);
		border.getStyleClass().add("pane");
		border.setPrefSize(SCREEN_SIZE, SCREEN_SIZE);
		startScreen.getChildren().add(border);
		startScreen.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
		Scene myScene = new Scene(startScreen, SCREEN_SIZE, SCREEN_SIZE, Color.TRANSPARENT);
		return myScene;
	}

	/**
	 * @param extensionAccepted
	 * @return Returns the filechooser used by the simulation to open an XML style
	 *         file.
	 */
	private FileChooser makeChooser(String extensionAccepted) {
		FileChooser result = new FileChooser();
		result.setTitle(myResources.getString("open"));
		result.setInitialDirectory(new File(System.getProperty("user.dir")));
		result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
		return result;
	}

	/**
	 * @param s
	 * 
	 *            Method used to open filechooser in order to read XML style file.
	 */
	private void openStyleFile(Stage s) {
		animation.pause();
		File dataFile = myChooser.showOpenDialog(s);
		if (dataFile != null) {
			try {
				styler = new StyleReader().getStyleConfiguration(dataFile);
				isStylish = true;
				submit.setDisable(!isStylish);
			} catch (XMLException e) {
				ErrorMessages.createErrors(myResources.getString("invalidstyle"));
			}
		} else {
			ErrorMessages.createErrors(myResources.getString("nofilechosen"));
		}
	}

	/**
	 * @throws Exception
	 * 
	 *             This method sets the current simulation type, and determines
	 *             based on the style file whether a simulation should be rectagon
	 *             or hexagon based, whether the grid is toroidal or finite, and
	 *             whether all neighbors or only the cardinal ones should be
	 *             counted. Defaults to cardinal neighbors, finite grid, and
	 *             rectangular grid. This method reads from the TextField user input
	 *             and sets all these conditions.
	 */
	private void setSim() throws Exception {
		if (styler.getGridShape() != null) {
			setSimMap();
		} else {
			simMap = pickRecSimByName;
		}
		Stage newStage = new Stage();
		simulationSetByUser = simType.getText();
		recGrid = new RectangleGrid(startScreen, null);
		hexGrid = new HexagonGrid(startScreen, null);
		recGrid.setSimType(simulationSetByUser);
		hexGrid.setSimType(simulationSetByUser);
		Simulation s = simMap.get(simulationSetByUser).copy();
		s.setSimType(simulationSetByUser);
		s.setMaxNeighbors(styler.getNeighborType().equals(myResources.getString("max")));
		s.setIsToroidal(styler.getEdgeShape().equals(myResources.getString("toroidal")));
		s.setIsRectangle(isRectangle);
		s.start(newStage);
	}

	/**
	 * @param s
	 * @param scene
	 * 
	 *            Basic set up stage method that sets up the current stage with a
	 *            scene.
	 */
	private void setUpStage(Stage s, Scene scene) {
		myStage = s;
		myStage.setScene(scene);
		myStage.setTitle(myResources.getString("picksim"));
		myStage.show();
	}

	/**
	 * Initializes the maps that are used to determine Simulation instantiation
	 * based on simulation type user typed in. Needs two maps in order to account
	 * for both rectangle and hexagon grid shapes.
	 */
	private void initMap() {
		pickRecSimByName = new HashMap<String, Simulation>();
		pickRecSimByName.put(myResources.getString("Wator"), new WatorSimulation(wG, recGrid));
		pickRecSimByName.put(myResources.getString("SpreadingWildfire"), new SpreadingWildfireSimulation(sWG, recGrid));
		pickRecSimByName.put(myResources.getString("GameOfLife"), new GameOfLifeSimulation(gofC, recGrid));
		pickRecSimByName.put(myResources.getString("Segregation"), new SegregationSimulation(sC, recGrid));
		pickRecSimByName.put(myResources.getString("ForagingAnts"), new ForagingAntsSimulation(faC, recGrid));
		pickRecSimByName.put(myResources.getString("RPS"), new RPSSimulation(rpsC, recGrid));

		pickHexSimByName = new HashMap<String, Simulation>();
		pickHexSimByName.put(myResources.getString("Wator"), new WatorSimulation(wG, hexGrid));
		pickHexSimByName.put(myResources.getString("SpreadingWildfire"), new SpreadingWildfireSimulation(sWG, hexGrid));
		pickHexSimByName.put(myResources.getString("GameOfLife"), new GameOfLifeSimulation(gofC, hexGrid));
		pickHexSimByName.put(myResources.getString("Segregation"), new SegregationSimulation(sC, hexGrid));
		pickHexSimByName.put(myResources.getString("ForagingAnts"), new ForagingAntsSimulation(faC, hexGrid));
		pickHexSimByName.put(myResources.getString("RPS"), new RPSSimulation(rpsC, recGrid));
	}

	/**
	 * Sets the style map to be used for the current simulation based on user input
	 * in the XML style file.
	 */
	private void setSimMap() {
		switch (styler.getGridShape()) {
		case ("Hexagon"):
			simMap = pickHexSimByName;
			isRectangle = false;
			break;
		case ("Rectangle"):
			simMap = pickRecSimByName;
			isRectangle = true;
			break;
		}
	}

	/**
	 * @param args
	 *            Main launch method
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
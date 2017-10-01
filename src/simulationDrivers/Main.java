package simulationDrivers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import XMLClasses.ForagingAntsConfiguration;
import XMLClasses.GameOfLifeConfiguration;
import XMLClasses.GridConfiguration;
import XMLClasses.SegregationConfiguration;
import XMLClasses.SegregationReader;
import XMLClasses.SpreadingWildfireConfiguration;
import XMLClasses.StyleConfiguration;
import XMLClasses.StyleReader;
import XMLClasses.WatorConfiguration;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class Main extends Application {

	private static GridConfiguration g = null;
	private static WatorConfiguration wG = null;
	private static SpreadingWildfireConfiguration sWG = null;
	private static GameOfLifeConfiguration gofC = null;
	private static SegregationConfiguration sC = null;
	private static ForagingAntsConfiguration faC = null;
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
	private static double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private Timeline animation = new Timeline();
	private static final String DATA_FILE_EXTENSION = "*.xml";
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private StyleConfiguration styler = null;
	private boolean isRectangle = false;

	@Override
	public void start(Stage myStage) throws Exception {
		setUpStage(myStage, startSimPicker(myStage));

		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	private Scene startSimPicker(Stage s) throws Exception {
		initMap();
		hbox.setPadding(new Insets(OFFSET));
		hbox.setSpacing(OFFSET);
		simType = SimulationButtons.makeReturnableTextFieldH("Choose simulation type", hbox, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonH("Create new Simulation", e -> {
			try {
				setSim();
			} catch (Exception e1) {
				ErrorMessages.createErrors("Invalid simulation type entered");
				//e1.printStackTrace();
			}
		}, hbox, 3 * OFFSET - SCREEN_SIZE);
		SimulationButtons.makeButtonH("Choose styling file", e -> openStyleFile(s), hbox, SCREEN_SIZE);
		border.setCenter(hbox);
		border.getStyleClass().add("pane");
		border.setPrefSize(SCREEN_SIZE, SCREEN_SIZE);
		startScreen.getChildren().add(border);
		startScreen.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
		Scene myScene = new Scene(startScreen, SCREEN_SIZE, SCREEN_SIZE, Color.TRANSPARENT);
		return myScene;
	}

	private FileChooser makeChooser(String extensionAccepted) {
		FileChooser result = new FileChooser();
		result.setTitle("Open Data File");
		result.setInitialDirectory(new File(System.getProperty("user.dir")));
		result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
		return result;
	}

	private void openStyleFile(Stage s) {
		animation.pause();
		File dataFile = myChooser.showOpenDialog(s);
		if (dataFile != null) {
			try {
				styler = new StyleReader().getStyleConfiguration(dataFile);
			} catch (XMLException e) {
				throw e;
			}
		} else {
			ErrorMessages.createErrors("No File Chosen");
		}
	}

	private void setSim() throws Exception {
		if(styler.getGridShape() != null) {
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
		s.setMaxNeighbors(styler.getNeighborType() == "Max");
		s.setStrokeFill(styler.getGridOutline() == "Yes");
		s.setIsToroidal(styler.getEdgeShape() == "Toroidal");
		s.setIsRectangle(isRectangle);
		s.start(newStage);
	}

	private void setUpStage(Stage s, Scene scene) {
		myStage = s;
		myStage.setScene(scene);
		myStage.setTitle("Pick simulation");
		myStage.show();
	}

	private void initMap() {
		pickRecSimByName = new HashMap<String, Simulation>();
		
		pickRecSimByName.put("Wator", new WatorSimulation(wG, recGrid));
		pickRecSimByName.put("SpreadingWildfire", new SpreadingWildfireSimulation(sWG, recGrid));
		pickRecSimByName.put("GameOfLife", new GameOfLifeSimulation(gofC, recGrid));
		pickRecSimByName.put("Segregation", new SegregationSimulation(sC, recGrid));
		pickRecSimByName.put("ForagingAnts", new ForagingAntsSimulation(faC, recGrid));

		pickHexSimByName = new HashMap<String, Simulation>();
		pickHexSimByName.put("Wator", new WatorSimulation(wG, hexGrid));
		pickHexSimByName.put("SpreadingWildfire", new SpreadingWildfireSimulation(sWG, hexGrid));
		pickHexSimByName.put("GameOfLife", new GameOfLifeSimulation(gofC, hexGrid));
		pickHexSimByName.put("Segregation", new SegregationSimulation(sC, hexGrid));
		pickHexSimByName.put("ForagingAnts", new ForagingAntsSimulation(faC, hexGrid));
	}

	private void setSimMap() {
		switch (styler.getGridShape()) {
		case ("Hexagon"):
			simMap = pickHexSimByName;
			isRectangle = false;
			break;
		case ("Rectangle"):
			simMap = pickRecSimByName;
			isRectangle = true;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
package simulationDrivers;

import java.util.HashMap;
import java.util.Map;

import XMLClasses.ForagingAntsConfiguration;
import XMLClasses.GameOfLifeConfiguration;
import XMLClasses.GridConfiguration;
import XMLClasses.SegregationConfiguration;
import XMLClasses.SpreadingWildfireConfiguration;
import XMLClasses.WatorConfiguration;
import cellManager.Grid;
import cellManager.HexagonGrid;
import cellManager.RectangleGrid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application{
	
	private static GridConfiguration g = null;
	private static WatorConfiguration wG = null;
	private static SpreadingWildfireConfiguration sWG = null;
	private static GameOfLifeConfiguration gofC = null;
	private static SegregationConfiguration sC = null;
	private static ForagingAntsConfiguration faC = null;
	private Stage myStage = new Stage();
	private TextField simType = new TextField();
	private HBox hbox = new HBox();
	private int SCREEN_SIZE = 300;
	private int OFFSET = 7;
	private Button submit = new Button();
	private String simulationSetByUser;
	private RectangleGrid recGrid;
	private HexagonGrid hexGrid;
	private BorderPane border = new BorderPane();
	private Group startScreen = new Group();
	private Map<String, Simulation> pickSimByName;
	private static final int FRAMES_PER_SECOND = 2;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private Timeline animation = new Timeline();

	@Override
	public void start(Stage myStage) throws Exception {
		setUpStage(myStage, startSimPicker());
		
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}
	
	private Scene startSimPicker() throws Exception{
		initMap();
		simType = SimulationButtons.makeReturnableTextFieldH("Choose simulation type", hbox, 3 * OFFSET - SCREEN_SIZE);
		submit = SimulationButtons.makeReturnableButtonH("Create new Simulation", e->{
			try {
				setSim();
			} catch (Exception e1) {
				ErrorMessages.createErrors("Invalid simulation type entered");
				e1.printStackTrace();
			}
		}, hbox, 3*OFFSET-SCREEN_SIZE);
		border.setCenter(hbox);
		border.getStyleClass().add("pane");
		border.setPrefSize(SCREEN_SIZE, SCREEN_SIZE);
		startScreen.getChildren().add(border);
		startScreen.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
		Scene myScene = new Scene(startScreen, SCREEN_SIZE, SCREEN_SIZE, Color.TRANSPARENT);
		return myScene;
	}
	
	private void setSim() throws Exception {
		Stage newStage = new Stage();
		simulationSetByUser = simType.getText();
		recGrid = new RectangleGrid(startScreen, g);
		hexGrid = new HexagonGrid(startScreen, g);
		recGrid.setSimType(simulationSetByUser);
		hexGrid.setSimType(simulationSetByUser);
		Simulation s = pickSimByName.get(simulationSetByUser).copy();
		s.setSimType(simulationSetByUser);
		s.start(newStage);
	}
	
	private void setUpStage(Stage s, Scene scene) {
		myStage = s;
		myStage.setScene(scene);
		myStage.setTitle("Pick simulation");
		myStage.show();
	}
	
	private void initMap() {
		pickSimByName = new HashMap<String, Simulation>();
		pickSimByName.put("Wator", new WatorSimulation(wG, recGrid));
		pickSimByName.put("SpreadingWildfire", new SpreadingWildfireSimulation(sWG, recGrid));
		pickSimByName.put("GameOfLife", new GameOfLifeSimulation(gofC, recGrid));
		pickSimByName.put("Segregation", new SegregationSimulation(sC, recGrid));
		pickSimByName.put("ForagingAnts", new ForagingAntsSimulation(faC, recGrid));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
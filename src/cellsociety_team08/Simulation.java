package cellsociety_team08;

import java.io.File;
import java.util.ResourceBundle;

import XMLClasses.GridConfiguration;
import XMLClasses.XMLException;
import XMLClasses.XMLReader;
import cellManager.Grid;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class Simulation extends Application {

	public static final String DATA_FILE_EXTENSION = "*.xml";
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private static final int SIZE = 500;
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
	
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
	
	public void start (Stage primaryStage) throws Exception {
		addButtonsToBorder(primaryStage);
    }
	
	public void addButtonsToBorder(Stage s) throws Exception
	{
		emptyPane.getChildren().clear();
		SimulationButtons.initializeTop(hboxTop);
		SimulationButtons.initializeRight(vboxRight);
		fileChooserButton = (Button) hboxTop.getChildren().get(0);
		startButton = (Button) hboxTop.getChildren().get(1);
		Rectangle temp = new Rectangle();
		temp.setWidth(400);
		temp.setHeight(400);
		temp.setFill(Color.LIGHTGRAY);
		temp.setStroke(Color.DARKGRAY);
		GridPane.setRowIndex(temp, 0);
		GridPane.setColumnIndex(temp, 0);
		emptyPane.getChildren().add(temp);
		pauseButton = (Button) vboxRight.getChildren().get(0);
		resumeButton = (Button) vboxRight.getChildren().get(1);
		fasterButton = (Button) vboxRight.getChildren().get(2);
		slowerButton = (Button) vboxRight.getChildren().get(3);
		resetButton = (Button) vboxRight.getChildren().get(4);
		stepButton = (Button) vboxRight.getChildren().get(5);
		screenBorder.setCenter(emptyPane);
		screenBorder.setTop(hboxTop);
		screenBorder.setRight(vboxRight);
		startSplash(s);
		
		splash.getChildren().add(screenBorder);

		addEvents(s);

	}

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
	
	private void manualStep() {
		sampleGrid.createsNewGrid();
		sampleGrid.update();
	}
	
	private void openFile(Stage s)
	{
		File dataFile = myChooser.showOpenDialog(s);
        GridConfiguration InputConfiguration = null;
        if (dataFile != null) {
            try {
                InputConfiguration  = new XMLReader(myResources.getString("gridConfig")).getGridConfiguration(dataFile);
            }
            catch (XMLException e) {
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText(e.getMessage());
                a.showAndWait();
            }
            // silly trick to select data file multiple times for this demo
            XMLConfiguration = InputConfiguration;
        }
        else {
            // nothing selected, so quit the application
            Platform.exit();
        }
	}
	
	public void startSplash(Stage s) throws Exception {

		// attach scene to the stage and display it
		myStage = s;
		Scene scene = setUpSplash();
	    myStage.setScene(scene);
	    myStage.setTitle(TITLE);
	    myStage.show();
        // attach "game loop" to timeline to play it
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
   	}
	
	public void startSimulation(Stage s) throws Exception {
		
		// attach scene to the stage and display it
		myStage = s;
		Scene scene = setSimulation(XMLConfiguration);
	    myStage.setScene(scene);
	    myStage.setTitle(TITLE);
	    myStage.show();
        // attach "game loop" to timeline to play it
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(timePassing));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
		
	}
	
	private Scene setSimulation(GridConfiguration xml)
	{	
		screenBorder.getChildren().remove(root);
		root = new Group();
		sampleGrid = new Grid(root,xml);
		sampleGrid.initialize();
		screenBorder.setCenter(root);
		if(isFirstTime == true) {
			simulationScreen.getChildren().add(screenBorder);
			myScene = new Scene(simulationScreen, SIZE, SIZE, BACKGROUND);
		}
		isFirstTime = false;
		//myScene = new Scene(simulationScreen, SIZE, SIZE, BACKGROUND);
	    return myScene;
	}
	
	private Scene setUpSplash(){
		myScene = new Scene(splash, SIZE, SIZE, BACKGROUND);
	    return myScene;
	}
	
	private void step (double elapsedTime) 
	{   
			sampleGrid.createsNewGrid();
			sampleGrid.update();
	}
	
	

	private void resume() {
		animation.play();
	}

	private void pause() {
		animation.pause();
	}
	private void faster() {
		timePassing*=2;
		animation.setRate(timePassing);
		
	}

	private void slower() {
		timePassing*=.5;
		animation.setRate(timePassing);
	}
	private FileChooser makeChooser (String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
	    // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
	    return result;
	}
	
	private void reset() {
		//screenBorder.getChildren().add(emptyPane);
		screenBorder.getChildren().remove(root);
		
		
	}
    public static void main (String[] args) {
        launch(args);
    }
    

}

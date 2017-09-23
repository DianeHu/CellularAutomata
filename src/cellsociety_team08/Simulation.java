package cellsociety_team08;
import java.io.File;
import java.util.Arrays;

import cellManager.Grid;
import cells.Cell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;

import XMLClasses.GridConfiguration;
import XMLClasses.XMLException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import XMLClasses.XMLReader;
import cellsociety_team08.SimulationButtons;

public class Simulation extends Application {

	public static final String DATA_FILE_EXTENSION = "*.xml";
	private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private static final int SIZE = 500;
	private static final Color BACKGROUND = Color.TRANSPARENT;
	private static final String TITLE = "SIMULATION";
	public static final int FRAMES_PER_SECOND = 2;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private Group root = new Group();
	private Scene myScene;
	private Cell sampleCell;
	private Grid sampleGrid;
	private Stage myStage;
	private int colorNum = 0;
	private GridConfiguration XMLConfiguration;
	private int simulationType;
	
	public void start (Stage primaryStage) throws Exception {
        File dataFile = myChooser.showOpenDialog(primaryStage);
        GridConfiguration InputConfiguration = null;
        if (dataFile != null) {
            try {
                InputConfiguration  = new XMLReader("GridConfiguration").getGridConfiguration(dataFile);
                //System.out.println(InputConfiguration.getSegregationThreshold());
                //System.out.println(InputConfiguration.getProbCatch());
                //System.out.println(InputConfiguration.getNumCols());
                //System.out.println(Arrays.deepToString(InputConfiguration.getCellConfiguration()));
               
                
                
            }
            catch (XMLException e) {
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText(e.getMessage());
                a.showAndWait();
            }
            // silly trick to select data file multiple times for this demo
            XMLConfiguration = InputConfiguration;
            chooseSimulation(root,primaryStage);
        }
        else {
            // nothing selected, so quit the application
            Platform.exit();
        }
    }
	
	public void chooseSimulation(Group g,Stage s) throws Exception
	{
		SimulationButtons.initialize(g);
		startSplash(s);
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
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
		
	}
	
	public void startSplash(Stage s) throws Exception {

		// attach scene to the stage and display it
		myStage = s;
		Scene scene = setUpSplash();
	    myStage.setScene(scene);
	    myStage.setTitle(TITLE);
	    myStage.show();
        // attach "game loop" to timeline to play it
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
        simulationType = SimulationButtons.setSimulation();
	}
	
	private Scene setSimulation(GridConfiguration xml)
	{
		//int width = 0, height =0;
		Paint background = Color.TRANSPARENT;
		if(colorNum == 1)
		{
			background = Color.PINK;
		}
		if(colorNum == 2)
		{
			background = Color.GREEN;
		}
		
		root = new Group();
		myScene = new Scene(root, SIZE, SIZE, background);
		//sampleCell = new BurningTreeCell(10, 10, SIZE, SIZE);
		//sampleCell.drawCell(root);
		
		sampleGrid = new Grid(root,xml); 
		sampleGrid.initialize();
		
		//root.getChildren().addAll();
		
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
	    myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
	    return myScene;
	}
	
	private Scene setUpSplash(){
		Paint background = Color.WHITE;
		myScene = new Scene(root, SIZE, SIZE, background);
		myScene.setOnKeyPressed(e -> handleKeyInputSplash(e.getCode()));
	    myScene.setOnMouseClicked(e -> handleMouseInputSplash(e.getX(), e.getY()));
	    return myScene;
	}
	
	private void step (double elapsedTime) 
	{   
		//myStage.setScene(setSimulation(XMLSample));
		//sampleGrid.createsNewGrid();
		//sampleGrid.update(root); 
		colorNum++;
	}
	
	private void handleKeyInput (KeyCode code) {
		if(code == KeyCode.A)
		{
			colorNum++;
			myStage.setScene(setSimulation(XMLConfiguration));
		}
		if(code == KeyCode.SPACE) {			
			sampleGrid.createsNewGrid();			
			sampleGrid.update(root); 
		}
	}
	
	private void handleMouseInput (double x, double y) {
        
    }
	
	private void handleKeyInputSplash (KeyCode code) {
		
		if(code == KeyCode.S)
		{
			myStage.setScene(setSimulation(XMLConfiguration));
		}
	}
	
	private void handleMouseInputSplash (double x, double y) {
        
    }
	
	public int setSimulationType()
	{
		return simulationType;
	}
	
	private FileChooser makeChooser (String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
	    // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
	    return result;
	}
	
    public static void main (String[] args) {
        launch(args);
    }
    

}

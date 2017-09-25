package simulationDrivers;

import XMLClasses.GridConfiguration;
import cellManager.Grid;
import cells.Cell;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashScreen extends Application {
	
	public static final String DATA_FILE_EXTENSION = "*.xml";
	//private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
	private static final int SIZE = 500;
	private static final Color BACKGROUND = Color.TRANSPARENT;
	private static final String TITLE = "SIMULATION";
	public static final int FRAMES_PER_SECOND = 2;
	public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	public static final double SECOND_DELAY = .00000000001 / FRAMES_PER_SECOND;
	private Group root = new Group();
	private Scene myScene;
	private Cell sampleCell;
	private Grid sampleGrid;
	private Stage myStage;
	private int colorNum = 0;
	private GridConfiguration XMLConfiguration;
	//private int simulationType;
	
	private static MenuItem menuItem1;
	private static MenuItem menuItem2; 
	private static MenuItem menuItem3;
	private static MenuItem menuItem4;
	private static MenuButton menuButton; 
	private static Button startButton;
	private static int simulationType;
	
	public void start(Stage s) throws Exception {

		// attach scene to the stage and display it
		myStage = s;
		Scene scene = setUpSplash();
	    myStage.setScene(scene);
	    myStage.setTitle(TITLE);
	    myStage.show();
	    initialize(root);
        // attach "game loop" to timeline to play it
        //KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
        //                              e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        //animation.getKeyFrames().add(frame);
        animation.play();
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
		System.out.println(colorNum);
	}
	
	private void handleKeyInputSplash (KeyCode code) {
		
		if(code == KeyCode.S)
		{
			myStage.setScene(setUpSplash());
		}
	}
	
	private void handleMouseInputSplash (double x, double y) {
        
    }
	
	
	public static void initialize(Group root)
	{

		menuItem1 = new MenuItem("1. Schelling's model of segregation");
		menuItem2 = new MenuItem("2. Spreading of Fire");
		menuItem3 = new MenuItem("3. Wa-Tor World model of predator-prey relationships");
		menuItem4 = new MenuItem("4. John Conway's Game of Life");
		menuButton = new MenuButton("Simulation Types", null, menuItem1, menuItem2, menuItem3,menuItem4);
		startButton = new Button("Start");
		startButton.relocate(100, 100);
		root.getChildren().add(menuButton);
		root.getChildren().add(startButton);
		
	}
	
	public int setSimulation()
	{
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			
			public void handle(ActionEvent event) {
				
				System.out.println(startButton.getText());
				myStage.close();
				startButton.disableProperty();
				
				
			}
		});
		
		if(startButton.isDisable())
		{
			return 10;
		}
		
		menuItem1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			
			public void handle(ActionEvent event) {
				
				System.out.println(menuItem1.getText());
				menuItem1.disableProperty();
				
			}
		});
		if(menuItem1.isDisable())
		{
			return 1;
		}
		
		menuItem2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			
			public void handle(ActionEvent event) {
				
				System.out.println(menuItem2.getText());
				menuItem2.disableProperty();
				
			}
		});
		if(menuItem2.isDisable())
		{
			return 2;
		}
		
		menuItem3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			
			public void handle(ActionEvent event) {
				
				System.out.println(menuItem3.getText());
				menuItem3.disableProperty();
				
			}
		});
		if(menuItem3.isDisable())
		{
			return 3;
		}
		
		menuItem4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			
			public void handle(ActionEvent event) {
				
				System.out.println(menuItem4.getText());
				menuItem4.disableProperty();
				
			}
		});
		if(menuItem4.isDisable())
		{
			return 4;
		}
		
		return 0;
		
	}
	
	public static void main (String[] args) {
        launch(args);
    }
	

}

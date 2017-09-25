package simulationDrivers;
/*package cellsociety_team08;
import java.io.File;

import cellManager.Grid;
import cells.BurningTreeCell;
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
//import javafx

public class SimulationTest extends Application {

	private static final int SIZE = 400;
	private static final Color BACKGROUND = Color.WHITE;
	private static final String TITLE = "SIMULATION";
	private static final double MILLISECOND_DELAY = 0;
	private static final double SECOND_DELAY = 0;
	private Group root = new Group();
	private Scene myScene;
	private Grid sampleGrid;
	private Stage myStage;
	private int colorNum = 0;

	
	@Override
	public void start(Stage s) throws Exception {
		// attach scene to the stage and display it
		myStage = s;
		Scene sceneSplash = setUpSplash();
		myStage.setScene(sceneSplash);
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
	
	public void start(Stage s) throws Exception {
		myStage = s;
		Scene scene = setSimulation();
        s.setScene(scene);
        s.setTitle(TITLE);
        s.show();
        // attach "game loop" to timeline to play it
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
        
		// attach scene to the stage and display it
		myStage = s;
		Scene scene = setSimulation();
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
	
	private Scene setSimulation()
	{
		//int width = 0, height =0;
		Paint background = Color.AQUA;
		if(colorNum == 1)
		{
			background = Color.WHITE;
		}
		if(colorNum == 2)
		{
			background = Color.GREEN;
		}
		
		root = new Group();
		myScene = new Scene(root, SIZE, SIZE, background);
		//sampleCell = new BurningTreeCell(10, 10, SIZE, SIZE);
		//sampleCell.drawCell(root);
		
		sampleGrid = new Grid(root); 
		sampleGrid.initialize();
		
		//root.getChildren().addAll();
		
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
	    myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
	    return myScene;
	}
	
	private Scene setUpSplash(){
		int width = 0, height =0;
		Paint background = Color.WHITE;
		myScene = new Scene(root, width, height, background);
		myScene.setOnKeyPressed(e -> handleKeyInputSplash(e.getCode()));
	    myScene.setOnMouseClicked(e -> handleMouseInputSplash(e.getX(), e.getY()));
	    return myScene;
	}
	
	private void step (double elapsedTime) 
	{		
		sampleGrid.createsNewGrid(); 
		sampleGrid.update(root);
	}
	
	private void handleKeyInput (KeyCode code) {

	}
	
	private void handleMouseInput (double x, double y) {
        
    }
	
	private void handleKeyInputSplash (KeyCode code) {
		
	}
	
	private void handleMouseInputSplash (double x, double y) {
        
    }
	
    public static void main (String[] args) {
        launch(args);
    }

}*/

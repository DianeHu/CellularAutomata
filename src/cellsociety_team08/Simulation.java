package cellsociety_team08;
import java.io.File;
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

public class Simulation extends Application {

	private static final String SIZE = null;
	private static final String BACKGROUND = null;
	private static final String TITLE = null;
	private static final double MILLISECOND_DELAY = 0;
	private static final double SECOND_DELAY = 0;
	private Group root = new Group();
	private Scene myScene;

	@Override
	public void start(Stage s) throws Exception {
		File xmlSample = null;
		// attach scene to the stage and display it
		Scene sceneSplash = setUpSplash();
		s.setScene(sceneSplash);
		Scene scene = setSimulation(xmlSample);
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
		
	}
	
	
	
	private Scene setSimulation(File xml)
	{
		int width = 0, height =0;
		Paint background = Color.WHITE;
		myScene = new Scene(root, width, height, background);
		myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
	    myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
	    return myScene;
	}
	
	private Scene setUpSplash()
	{
		int width = 0, height =0;
		Paint background = Color.WHITE;
		myScene = new Scene(root, width, height, background);
		myScene.setOnKeyPressed(e -> handleKeyInputSplash(e.getCode()));
	    myScene.setOnMouseClicked(e -> handleMouseInputSplash(e.getX(), e.getY()));
	    return myScene;
	}
	
	private void step (double elapsedTime) 
	{
		
	}
	
	private void handleKeyInput (KeyCode code) {
		
	}
	
	private void handleMouseInput (double x, double y) {
        
    }
	
	private void handleKeyInputSplash (KeyCode code) {
		
	}
	
	private void handleMouseInputSplash (double x, double y) {
        
    }

}

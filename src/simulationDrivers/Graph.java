package simulationDrivers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;


public class Graph {
	private LineChart<Number, Number> chart;
	private static final int FRAMES_PER_SECOND = 2;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private static double timePassing = SECOND_DELAY;

	private XYChart.Series<Number, Number> dataSeries;

	private NumberAxis xAxis;

	private Timeline animation;

	private double sequence = 0;

	private double y = 10;

	private final int MAX_DATA_POINTS = 25, MAX = 10, MIN = 5;;

	public Parent createContent() {

	    xAxis = new NumberAxis(0, 10, 1);
	    final NumberAxis yAxis = new NumberAxis(0, 100, 10);
	    chart = new LineChart<>(xAxis, yAxis);

	    // setup chart
	    chart.setAnimated(false);
	    chart.setLegendVisible(false);
	    chart.setTitle("Population Monitor");
	    xAxis.setLabel("Time");
	    xAxis.setForceZeroInRange(false);

	    yAxis.setLabel("Percentage of Population");
	    yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, null, null));

	    // add starting data
	    dataSeries = new XYChart.Series<>();
	    dataSeries.setName("Data");

	    // create some starting data
	    dataSeries.getData()
	            .add(new XYChart.Data<Number, Number>(++sequence, y));

	    chart.getData().add(dataSeries);

	    return chart;
	}

	private void step(double timePassing) {
	    dataSeries.getData().add(new XYChart.Data<Number, Number>();

	    // after 25hours delete old data
	    if (sequence > MAX_DATA_POINTS) {
	        dataSeries.getData().remove(0);
	    }

	    // every hour after 24 move range 1 hour
	    if (sequence > MAX_DATA_POINTS - 1) {
	        xAxis.setLowerBound(xAxis.getLowerBound() + 1);
	        xAxis.setUpperBound(xAxis.getUpperBound() + 1);
	    }
	}

	public void start(Stage primaryStage){
		primaryStage.setScene(new Scene(createContent()));
	    primaryStage.setTitle("Animated Line Chart");
	    primaryStage.show();
	    
		animation = new Timeline();
		KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(timePassing));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

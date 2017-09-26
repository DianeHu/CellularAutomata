package simulationDrivers;
import javafx.application.Application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Graph2 {
	private LineChart<Number, Number> chart;
	private static final int FRAMES_PER_SECOND = 2;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private static double timePassing = SECOND_DELAY;
	private XYChart.Series<Number, Number> dataSeries;
	private NumberAxis xAxis;
	private Timeline animation;
	private int temp = 0;
	private int y = 10;

	public Parent createContent() {
		xAxis = new NumberAxis(0, 10, 1);
		final NumberAxis yAxis = new NumberAxis(0, 100, 10);
		chart = new LineChart<>(xAxis, yAxis);
		
		chart.setAnimated(true);
		chart.setLegendVisible(true);
		chart.setTitle("Population Monitor");
		xAxis.setLabel("Time");
		xAxis.setForceZeroInRange(false);
		
		yAxis.setLabel("Percentage of Population");
		yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis, null, null));
		
		dataSeries = new XYChart.Series<>();dataSeries.setName("Data");
		
		dataSeries.getData().add(new XYChart.Data<Number, Number>(temp++, y));
		
		chart.getData().add(dataSeries);
		return chart;
	}
	
	private void step(double timePassing) {
		dataSeries.getData().add(new XYChart.Data<Number, Number>(1, 2));
	}
	
	public void start(Stage s) {
		s.setScene(new Scene(createContent()));
		s.setTitle("Chart");
		s.show();
		
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

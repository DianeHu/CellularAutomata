package simulationDrivers;

import java.util.HashMap;
import java.util.Map;

import cellManager.Grid;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Graph {
	private Series<Number, Number> series1 = new Series<Number, Number>();;
	private Series<Number, Number> series2 = new Series<Number, Number>();;
	private Series<Number, Number> series3 = new Series<Number, Number>();;
	private LineChart<Number, Number> lineChart;
	private int step = 0;
	private Grid g;
	private String simType;
	private Map<Series<Number, Number>, String> segMap = new HashMap<>();
	private Map<Series<Number, Number>, String> fireMap = new HashMap<>();
	private Map<Series<Number, Number>, String> watorMap = new HashMap<>();
	private Map<Series<Number, Number>, String> lifeMap = new HashMap<>();
	private Map<Series<Number, Number>, String> simMap = new HashMap<>();

	public Graph(Grid newGrid) {
		g = newGrid;
		//simType = newGrid.getSimType();
		simType = "Wator";
		lineChart = createContent();
	}

	public void updateGraph() {
		lineChart.setLegendVisible(true);
		selectUpdates();
		step++;
	}

	private void selectUpdates() {
		switch (simType) {
		case ("Segregation"):
			series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentBS()));
			series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentOS()));
			series3.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentEmpty()));
			break;
		case ("GameOfLife"):
			series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentLive()));
			series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentDead()));
			break;
		case ("SpreadingWildfire"):
			series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentTree()));
			series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentBurning()));
			series3.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentLand()));
		case ("Wator"):
			series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentSharks()));
			series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentFish()));
			series3.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentEmpty()));
		}
	}

	public void addToBox(Pane box) {
		box.getChildren().add(lineChart);
	}

	public LineChart<Number, Number> getLineChart() {
		return lineChart;
	}

	public void clear() {
		series1.getData().clear();
		series2.getData().clear();
	}

	@SuppressWarnings("unchecked")
	public LineChart<Number, Number> createContent() {
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setForceZeroInRange(false);
		xAxis.setLabel("Timestep");
		yAxis.setLabel("Population %");
		xAxis.setTickLabelFill(Color.WHITE);
		yAxis.setTickLabelFill(Color.WHITE);
		//xAxis.setStyle("-fx-text-fill: #000000");
		//yAxis.setStyle("-fx-text-fill: #000000");
		//xAxis.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
		//yAxis.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
		lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		//lineChart.setStyle("-fx-stroke: #000000");
		//lineChart.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());
		lineChart.setTitle("Population Monitor");
		lineChart.setMaxSize(400, 200);
		initMaps();
		chooseMap();
		contentInitializer();

		// series1.getNode().setStyle(".bluechart");
		// series2.getNode().setStyle(".orangechart");

		lineChart.getData().addAll(series1, series2);
		if(series3 != null) {
			lineChart.getData().add(series3);
		}
		
		lineChart.setLegendSide(Side.RIGHT);
		lineChart.setLegendVisible(false);
		return lineChart;
	}

	private void initMaps() {
		segMap.put(series1, "Blue Schelling Cell");
		segMap.put(series2, "Orange Schelling Cell");
		segMap.put(series3, "Empty Cell");

		lifeMap.put(series1, "Live Cell");
		lifeMap.put(series2, "Dead Cell");

		fireMap.put(series1, "Tree Cell");
		fireMap.put(series2, "Burning Tree Cell");
		fireMap.put(series3, "Empty Land Cell");

		watorMap.put(series1, "Shark Cell");
		watorMap.put(series2, "Fish Cell");
		watorMap.put(series3, "Empty Cell");
	}

	private void chooseMap() {
		switch (simType) {
		case ("Segregation"):
			simMap = segMap;
			break;
		case ("GameOfLife"):
			simMap = lifeMap;
			break;
		case ("SpreadingWildfire"):
			simMap = fireMap;
			break;
		case ("Wator"):
			simMap = watorMap;
		}
	}

	private void contentInitializer() {
		series1.setName(simMap.get(series1));
		series2.setName(simMap.get(series2));
		if(simMap.get(series3) != null) {
			series3.setName(simMap.get(series3));
		}
	}
}
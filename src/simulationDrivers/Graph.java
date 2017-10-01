package simulationDrivers;

import cellManager.Grid;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Graph {
	protected Series<Number, Number> series1 = new Series<Number, Number>();;
	protected Series<Number, Number> series2 = new Series<Number, Number>();;
	protected Series<Number, Number> series3 = new Series<Number, Number>();;
	protected LineChart<Number, Number> lineChart;
	protected int step = 0;
	protected Grid g;

	public Graph(Grid newGrid) {
		g = newGrid;
		lineChart = createContent();
	}

	public void updateGraph() {
		update();
		step++;
	}

	protected abstract void update();
	
	public void addToBox(Pane box) {
		box.getChildren().add(lineChart);
	}

	public LineChart<Number, Number> getLineChart() {
		return lineChart;
	}

	public LineChart<Number, Number> createContent() {
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setForceZeroInRange(false);
		xAxis.setLabel("Timestep");
		yAxis.setLabel("Population %");
		xAxis.setTickLabelFill(Color.WHITE);
		yAxis.setTickLabelFill(Color.WHITE);
		lineChart = new LineChart<Number, Number>(xAxis, yAxis);
		lineChart.setTitle("Population Monitor");
		lineChart.setMaxSize(600, 200);
		
		setNames();
		addData();

		lineChart.setLegendSide(Side.RIGHT);
		return lineChart;
	}
	
	protected abstract void addData();
	
	protected abstract void setNames();

}
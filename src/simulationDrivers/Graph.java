package simulationDrivers;

import cellManager.Grid;

import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author Diane Hu
 * 
 *         Graph superclass creating a linechart object that tracks cell
 *         populations over time. Used in the cellular automata to track their
 *         specific cell types and their changes over time. Depends on javafx
 *         classes used to create charts.
 */
public abstract class Graph {

	/**
	 * The following series are protected as to be able to be accessed by all
	 * subclasses. Since only Graph subclasses have access, we decided this would be
	 * sufficiently private, and save adding more getters and setters.
	 */
	protected Series<Number, Number> series1 = new Series<Number, Number>();
	protected Series<Number, Number> series2 = new Series<Number, Number>();
	protected Series<Number, Number> series3 = new Series<Number, Number>();
	protected Series<Number, Number> series4 = new Series<Number, Number>();
	protected LineChart<Number, Number> lineChart;
	protected int step = 0;
	protected Grid g;

	/**
	 * @param newGrid
	 * 
	 *            Graph constructor taking a grid object, and creating a linechart
	 */
	public Graph(Grid newGrid) {
		g = newGrid;
		lineChart = createContent();
	}

	/**
	 * Updates the graph and steps so that the x axis updates correctly
	 */
	public void updateGraph() {
		update();
		step++;
	}

	/**
	 * Abstract update method used by subclasses to dictate what series get updated,
	 * and by what.
	 */
	protected abstract void update();

	/**
	 * @param box
	 * 
	 *            Adds the current linechart to a given formatting pane.
	 */
	public void addToBox(Pane box) {
		box.getChildren().add(lineChart);
	}

	/**
	 * @return Returns the linechart object of the current graph.
	 */
	public LineChart<Number, Number> getLineChart() {
		return lineChart;
	}

	/**
	 * @return Creates all the initial simulation non-specific fields in a linechart
	 *         and returns it. Sets axis names, chart title, legend size, and makes
	 *         calls to methods that do simulation specific data updates.
	 */
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

	/**
	 * Abstract method used by subclasses to add appropriate data based on
	 * simulation type.
	 */
	protected abstract void addData();

	/**
	 * Sets the names of the series/lines in the chart based on simulation type.
	 * Abstract method called by subclasses.
	 */
	protected abstract void setNames();

}
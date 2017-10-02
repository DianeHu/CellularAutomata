package simulationDrivers;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

/**
 * @author Diane Hu
 * 
 *         Subclass of main Graph class. Instantiates a graph for the
 *         segregation simulation. Used by that simulation subclass to create
 *         the appropriate graph, and depends on javafx chart imports.
 *
 */
public class SegregationGraph extends Graph {

	/**
	 * @param newGrid
	 * 
	 *            Constructor using superclass constructor.
	 */
	public SegregationGraph(Grid newGrid) {
		super(newGrid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#update()
	 * 
	 * Updates the simulation appropriate series--percent blue, orange, and empty.
	 */
	@Override
	protected void update() {
		series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentBS()));
		series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentOS()));
		series3.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentEmpty()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#addData()
	 * 
	 * Adds the appropriate series to the linechart.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void addData() {
		lineChart.getData().addAll(series1, series2, series3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#setNames()
	 * 
	 * Sets the appropriate names for the series in this graph/simulation type.
	 */
	@Override
	protected void setNames() {
		series1.setName("BlueSchellingCount");
		series2.setName("OrangeSchellingCount");
		series3.setName("EmptyCount");
	}
}

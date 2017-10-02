package simulationDrivers;

import cellManager.Grid;

import javafx.scene.chart.XYChart;

/**
 * @author Diane Hu Graph subclass that creates a graph object for the game of
 *         life simulation. Depends on javafx chart imports. Used in simulation
 *         subclas for this simulation to instantiate an appropriate graph.
 *
 */
public class GameOfLifeGraph extends Graph {

	/**
	 * @param newGrid
	 *            Constructor using superclass constructor.
	 */
	public GameOfLifeGraph(Grid newGrid) {
		super(newGrid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#update()
	 * 
	 * Updates simulation specific data of percentage of live and dead cells.
	 */
	@Override
	protected void update() {
		series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentLive()));
		series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentDead()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#addData()
	 * 
	 * Adds the appropriate data to the linechart for this simulation.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void addData() {
		lineChart.getData().addAll(series1, series2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#setNames()
	 * 
	 * Sets the simulation appropriate names for the series.
	 */
	@Override
	protected void setNames() {
		series1.setName("LiveCellCount");
		series2.setName("DeadCellCount");
	}
}

package simulationDrivers;

import java.util.ResourceBundle;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

/**
 * @author Diane Hu
 * 
 *         Subclass of Graph superclass. Used by the spreadingwildfire
 *         simulation to instantiate a graph appropriate to that simulation.
 *         Depends on javafx chart imports.
 *
 */
public class SpreadingWildfireGraph extends Graph {

	/**
	 * @param newGrid
	 * 
	 *            Constructor using superclass constructor.
	 */
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
	
	public SpreadingWildfireGraph(Grid newGrid) {
		super(newGrid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#update()
	 * 
	 * Updates the appropriate series for this simulation type--percent tree,
	 * burning, and land.
	 */
	@Override
	protected void update() {
		series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentTree()));
		series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentBurning()));
		series3.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentLand()));
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
	 * Sets the appropriate names for the series in this graph/simulation.
	 */
	@Override
	protected void setNames() {
		series1.setName(myResources.getString("treecount"));
		series2.setName(myResources.getString("burningtreecount"));
		series3.setName(myResources.getString("emptylandcount"));
	}
}

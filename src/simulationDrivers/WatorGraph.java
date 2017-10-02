package simulationDrivers;

import java.util.ResourceBundle;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

/**
 * @author Diane Hu
 * 
 *         Subclass of graph superclass. Instantiates the appropriate graph for
 *         the wator simulation subclass. Depends on javafx chart imports.
 *
 */
public class WatorGraph extends Graph {

	/**
	 * @param newGrid
	 * 
	 *            Constructor using superclass constructor.
	 */
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
	
	public WatorGraph(Grid newGrid) {
		super(newGrid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#update()
	 * 
	 * Updates the appropriate series for this graph--percent shark, fish, and
	 * empty.
	 */
	@Override
	protected void update() {
		series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentSharks()));
		series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentFish()));
		series3.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentEmpty()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#addData()
	 * 
	 * Adds the correct series to the linechart.
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
	 * Sets the appropriate series names for this graph type
	 */
	@Override
	protected void setNames() {
		series1.setName(myResources.getString("sharkcount"));
		series2.setName(myResources.getString("fishcount"));
		series3.setName(myResources.getString("emptycount"));
	}
}

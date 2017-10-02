package simulationDrivers;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

/**
 * @author Diane Hu Subclass of Graph superclass. Used to instantiate a graph
 *         object for the RPS simulation. Called in that simulation subclass to
 *         make an appropriate graph. Depends on javafx chart imports.
 *
 */
public class RPSGraph extends Graph {

	/**
	 * @param newGrid
	 * 
	 *            Constructor using superclass constructor.
	 */
	public RPSGraph(Grid newGrid) {
		super(newGrid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#update()
	 * 
	 * Updates the appropriate series for this simulation--percent blue, green, red,
	 * and white.
	 */
	@Override
	protected void update() {
		series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentBlueRPS()));
		series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentGreenRPS()));
		series3.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentRedRPS()));
		series4.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentWhiteRPS()));
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
		lineChart.getData().addAll(series1, series2, series3, series4);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simulationDrivers.Graph#setNames()
	 * 
	 * Sets the appropriate names for the series in this simulation.
	 */
	@Override
	protected void setNames() {
		series1.setName("BlueRPSCount");
		series2.setName("GreenRPSCount");
		series3.setName("RedRPSCount");
		series4.setName("WhiteRPSCount");
	}
}

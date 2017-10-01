package simulationDrivers;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

public class SpreadingWildfireGraph extends Graph{

	public SpreadingWildfireGraph(Grid newGrid) {
		super(newGrid);
	}

	@Override
	protected void update() {
		series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentTree()));
		series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentBurning()));
		series3.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentLand()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void addData() {
		lineChart.getData().addAll(series1, series2, series3);
	}
	
	@Override
	protected void setNames() {
		series1.setName("TreeCount");
		series2.setName("BurningTreeCount");
		series3.setName("EmptyLandCount");
	}
}

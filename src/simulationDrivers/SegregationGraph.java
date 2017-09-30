package simulationDrivers;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

public class SegregationGraph extends Graph{

	public SegregationGraph(Grid newGrid) {
		super(newGrid);
	}

	@Override
	protected void update() {
		series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentBS()));
		series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentOS()));
		series3.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentEmpty()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void addData() {
		lineChart.getData().addAll(series1, series2, series3);
	}
	
	@Override
	protected void setNames() {
		series1.setName("BlueSchellingCount");
		series2.setName("OrangeSchellingCount");
		series3.setName("EmptyCount");
	}
}

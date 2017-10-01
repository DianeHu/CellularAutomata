package simulationDrivers;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

public class ForagingAntsGraph extends Graph{

	public ForagingAntsGraph(Grid newGrid) {
		super(newGrid);
	}

	@Override
	protected void update() {
		series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * 0));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void addData() {
		lineChart.getData().addAll(series1);
	}
	
	@Override
	protected void setNames() {
		series1.setName("NumAntGroups");
	}
}
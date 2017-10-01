package simulationDrivers;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

public class WatorGraph extends Graph{

	public WatorGraph(Grid newGrid) {
		super(newGrid);
	}

	@Override
	protected void update() {
		series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentSharks()));
		series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentFish()));
		series3.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentEmpty()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void addData() {
		lineChart.getData().addAll(series1, series2, series3);
	}
	
	@Override
	protected void setNames() {
		series1.setName("SharkCount");
		series2.setName("FishCount");
		series3.setName("EmptyCount");
	}
}

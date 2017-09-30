package simulationDrivers;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

public class GameOfLifeGraph extends Graph{

	public GameOfLifeGraph(Grid newGrid) {
		super(newGrid);
	}

	@Override
	protected void update() {
		series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentLive()));
		series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentDead()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void addData() {
		lineChart.getData().addAll(series1, series2);
	}
	
	@Override
	protected void setNames() {
		series1.setName("LiveCellCount");
		series2.setName("DeadCellCount");
	}
}

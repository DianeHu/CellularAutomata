package simulationDrivers;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

public class RPSGraph extends Graph{

	public RPSGraph(Grid newGrid) {
		super(newGrid);
	}

	@Override
	protected void update() {
		series1.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentBlueRPS()));
		series2.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentGreenRPS()));
		series3.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentRedRPS()));
		series4.getData().add(new XYChart.Data<Number, Number>(step, 100 * g.percentWhiteRPS()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void addData() {
		lineChart.getData().addAll(series1, series2, series3, series4);
	}
	
	@Override
	protected void setNames() {
		series1.setName("BlueRPSCount");
		series2.setName("GreenRPSCount");
		series3.setName("RedRPSCount");
		series4.setName("WhiteRPSCount");
	}
}

package simulationDrivers;

import java.util.ResourceBundle;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

public class SegregationGraph extends Graph{

	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
	
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
		series1.setName(myResources.getString("blueschellingcount"));
		series2.setName(myResources.getString("orangeschellingcount"));
		series3.setName(myResources.getString("emptycount"));
	}
}

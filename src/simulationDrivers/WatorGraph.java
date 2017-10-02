package simulationDrivers;

import java.util.ResourceBundle;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

public class WatorGraph extends Graph{

	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
	
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
		series1.setName(myResources.getString("sharkcount"));
		series2.setName(myResources.getString("fishcount"));
		series3.setName(myResources.getString("emptycount"));
	}
}

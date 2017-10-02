package simulationDrivers;

import java.util.ResourceBundle;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

public class SpreadingWildfireGraph extends Graph{

	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
	
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
		series1.setName(myResources.getString("treecount"));
		series2.setName(myResources.getString("burningtreecount"));
		series3.setName(myResources.getString("emptylandcount"));
	}
}

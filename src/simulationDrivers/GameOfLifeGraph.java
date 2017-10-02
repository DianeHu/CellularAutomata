package simulationDrivers;

import java.util.ResourceBundle;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

public class GameOfLifeGraph extends Graph{

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
	
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
		series1.setName(myResources.getString("livecellcount"));
		series2.setName(myResources.getString("deadcellcount"));
	}
}

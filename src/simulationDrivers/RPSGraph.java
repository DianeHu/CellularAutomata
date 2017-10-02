package simulationDrivers;

import java.util.ResourceBundle;

import cellManager.Grid;
import javafx.scene.chart.XYChart;

public class RPSGraph extends Graph{

	// initializes the resources used to get text Strings
	private static final String DEFAULT_RESOURCE_PACKAGE = "Resources/Labels";
	private static ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE);
	
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
		series1.setName(myResources.getString("bluerpscount"));
		series2.setName(myResources.getString("greenrpscount"));
		series3.setName(myResources.getString("redrpscount"));
		series4.setName(myResources.getString("whiterpscount"));
	}
}

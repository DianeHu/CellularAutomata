package simulationDrivers;

import cellManager.Grid;
import cellManager.RectangleGrid;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Pane;
 
 
public class Graph {
	private Series series1;
	private Series series2;
	private LineChart<Number, Number> lineChart;
	private int step = 0;
	
	public Graph() {
		lineChart = createContent();
	}
    
    public void updateGraph(Grid g) {
    	series1.getData().add(new XYChart.Data(step, g.percentLive()));
    	series2.getData().add(new XYChart.Data(step, g.percentDead()));
    	step++;
    }
    
    public void addToBox(Pane box) {
    	box.getChildren().add(lineChart);
    }
    
    public LineChart getLineChart() {
    	return lineChart;
    }
    
    public LineChart createContent() {
    	final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Timestep");
         yAxis.setLabel("Percentage of Population");
        lineChart = new LineChart<Number, Number>(xAxis,yAxis);
       
        lineChart.setTitle("Population Monitor");
                          
        series1 = new XYChart.Series();
        series1.setName("Live");
        //series1.getNode().setStyle(".bluechart");
        
        series2 = new XYChart.Series();
        series2.setName("Dead");
        //series2.getNode().setStyle(".orangechart");
        
        lineChart.getData().addAll(series1, series2);
        return lineChart;
    }
}
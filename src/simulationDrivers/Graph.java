package simulationDrivers;

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
	private Group root;
	
	public Graph() {
		lineChart = createContent();
	}
    
    public void updateGraph() {
    	series1.getData().add(new XYChart.Data(1, 2));
    }
    
    public void addToBox(Pane box) {
    	box.getChildren().add(lineChart);
    }
    
    public Node getParent() {
    	return this.getParent();
    }
    
    public LineChart createContent() {
    	final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Timestep");
        lineChart = new LineChart<Number, Number>(xAxis,yAxis);
       
        lineChart.setTitle("Population Monitor");
                          
        series1 = new XYChart.Series();
        series1.setName("BlueSchellingCell");
        
        series1.getData().add(new XYChart.Data(1, 23));
        series1.getData().add(new XYChart.Data(2, 14));
        series1.getData().add(new XYChart.Data(3, 15));
        series1.getData().add(new XYChart.Data(4, 24));
        
        series2 = new XYChart.Series();
        series2.setName("OrangeSchellingCell");
        series2.getData().add(new XYChart.Data(1, 33));
        series2.getData().add(new XYChart.Data(2, 34));
        series2.getData().add(new XYChart.Data(3, 25));
        series2.getData().add(new XYChart.Data(4, 44));
        series2.getData().add(new XYChart.Data(5, 39));
        series2.getData().add(new XYChart.Data(6, 16));
        
        lineChart.getData().addAll(series1, series2);
        return lineChart;
    }
}
package cellsociety_team08;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class SimulationButtons {
	
	private static MenuItem menuItem1;
	private static MenuItem menuItem2; 
	private static MenuItem menuItem3;
	private static MenuItem menuItem4;
	private static MenuButton menuButton;
	
	public static void initialize(Group root)
	{

		menuItem1 = new MenuItem("1. Schelling's model of segregation");
		menuItem2 = new MenuItem("2. Spreading of Fire");
		menuItem3 = new MenuItem("3. Wa-Tor World model of predator-prey relationships");
		menuItem4 = new MenuItem("4. John Conway's Game of Life");
		menuButton = new MenuButton("Simulation Types", null, menuItem1, menuItem2, menuItem3,menuItem4);
		root.getChildren().add(menuButton);
		
	}
	
	public static String setSimulation()
	{
		
		menuItem1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			
			public void handle(ActionEvent event) {
				
				System.out.println(menuItem1.getText());
				menuItem1.disableProperty();
				
			}
		});
		if(menuItem1.isDisable())
		{
			return "Segregation";
		}
		
		menuItem2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			
			public void handle(ActionEvent event) {
				
				System.out.println(menuItem2.getText());
				menuItem2.disableProperty();
				
			}
		});
		if(menuItem2.isDisable())
		{
			return "SpreadingWildfire";
		}
		
		menuItem3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			
			public void handle(ActionEvent event) {
				
				System.out.println(menuItem3.getText());
				menuItem3.disableProperty();
				
			}
		});
		if(menuItem3.isDisable())
		{
			return "Wator";
		}
		
		menuItem4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			
			public void handle(ActionEvent event) {
				
				System.out.println(menuItem4.getText());
				menuItem4.disableProperty();
				
			}
		});
		if(menuItem4.isDisable())
		{
			return "GameOfLife";
		}
		
		return "Error";
	}
}

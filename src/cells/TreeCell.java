package cells;

import java.util.ArrayList;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TreeCell extends Cell{
	private int rowNum;
	private int colNum;
	private Rectangle block;
	private ArrayList<Cell> neighbors;
	private boolean fireThreat = false;
	private double probCatch = .15;
	private double threatSeverity = 1;
	private int width;
	private int height;
	
	public TreeCell(int myRowNum, int myColNum, int width, int height) {
		super(myRowNum, myColNum, width, height);
		block.setFill(Color.FORESTGREEN);
	}
	
	@Override
	public boolean isSurroundingNeighbor(int otherRowNum, int otherColNum) {
		if((Math.abs(rowNum-otherRowNum)<=1 && (colNum == otherColNum)) || (Math.abs(colNum-otherColNum)<=1 && (rowNum == otherRowNum))) {
			return true;
		}
		return false;
	}
	
	public void setNeighbors(ArrayList<Cell> n) {
		super.setNeighbors(n);
	}
	
	public void burn(Group root) {
		if(fireThreat) {
			double test = Math.random();
			if(test < threatSeverity * probCatch) {
				BurningTreeCell newCell = new BurningTreeCell(rowNum, colNum, width, height);
				changeCellType(root, this, newCell);
			}
		}
	}
	
	public void checkFireThreat() {
		for(Cell cell : neighbors) {
			if(cell instanceof BurningTreeCell) {
				fireThreat = true;
				threatSeverity++;
			}
		}
	}
	
	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		double numBlue = (double)getNumBlueNeighbors();
		double numOrange = (double)getNumOrangeNeighbors();
		boolean satisfied = numBlue/(numOrange+numBlue) >= 0.3;
		if(!satisfied) {
			moveToRandomEmptySpace(emptySpots, grid);
		}
	}

}

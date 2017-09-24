package cells;

import java.util.ArrayList;
import java.util.Random;

import cellManager.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SharkCell extends Cell{
	private static int breedTurns;
	private static int starveTurns;
	private int numBreedTurns;
	private int numStarveTurns;
	
	public SharkCell(int myRowNum, int myColNum) {
		super(myRowNum, myColNum);
		setColor(Color.SLATEGREY);
	}

	public SharkCell() {
		super();
		setColor(Color.SLATEGREY);
	}
	
	public void setBreedTurns(int n) {
		breedTurns = n;
	}
	
	public void setStarveTurns(int n) {
		starveTurns = n;
	}
	public boolean isNeighbor(int otherRowNum, int otherColNum, int numRows, int numCols) {
		return super.isNeighborTorus(otherRowNum, otherColNum, numRows, numCols);
	}

	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		//System.out.println(numStarveTurns + " " +starveTurns);
		if(numStarveTurns<starveTurns) {
			ArrayList<Cell> emptyNeighbors = getEmptyNeighbors();
	        ArrayList<FishCell> neighborfish = getNeighboringFish();

	        if(eatFish(neighborfish,grid)) {
				numStarveTurns = 0;
				//System.out.println(" eaten");
			}
	        else {
				if(numBreedTurns>=breedTurns) {
					breed(emptyNeighbors,grid);
				}	
				if(numBreedTurns!=-1) {
					moveToRandomPlace(emptyNeighbors,grid);
				}
				numStarveTurns++;
	        }	
			
			numBreedTurns++;
		}
	}
	
	private void breed(ArrayList<Cell> emptySpots, Grid grid) {
		SharkCell newshark = new SharkCell(getRow(), getCol());
		if(moveToRandomPlace(emptySpots,grid)) {
			numBreedTurns = -1;
			grid.addToNewGrid(newshark);
		}
	}
	
	public boolean eatFish(ArrayList<FishCell> fish, Grid grid) {
		boolean moved = false;
		while(!moved) {
			int numFish = fish.size();
			if(numFish==0) {
				break;
			}
			Random rand = new Random(); 
			FishCell testFish = fish.get(rand.nextInt(numFish));
			if(grid.newGridContainsSharkAt(testFish.getRow(),testFish.getCol())) {
				fish.remove(testFish);
			}
			else {
				setRow(testFish.getRow()); setCol(testFish.getCol());
				testFish.die(grid); 
				grid.addToNewGrid(this);
				moved = true;
			}
		}
		return moved;
	}
	
	private ArrayList<FishCell> getNeighboringFish(){
		ArrayList<FishCell> neighborfish = new ArrayList<FishCell>();
		for(Cell c:getNeighbors()) {
			if(c instanceof FishCell) {
				FishCell f = (FishCell)c;
				neighborfish.add(f);
			}
		}
		return neighborfish;
	}


	@Override
	public SharkCell copy() {
		SharkCell newCell = new SharkCell();
		newCell.setBreedTurns(breedTurns);
		newCell.setStarveTurns(starveTurns);
		return newCell;
	}
	



}
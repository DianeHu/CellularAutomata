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

	
	public void setBreedTurns(int n) {
		breedTurns = n;
	}
	
	public void setStarveTurns(int n) {
		starveTurns = n;
	}
	public boolean isNeighbor(int otherRowNum, int otherColNum) {
		return super.isNeighbor4(otherRowNum, otherColNum);
	}

	public void moveCell(ArrayList<Cell> emptySpots, Grid grid) {
		
		if(numStarveTurns!=starveTurns) {
			ArrayList<Cell> emptyNeighbors = getEmptyNeighbors();
	        ArrayList<FishCell> neighborfish = getNeighboringFish();
	        
			/*if(neighborfish.size()==0) {
				moveToRandomPlace(emptyNeighbors,grid);
				numStarveTurns++;
			}
			else {
				if(eatFish(neighborfish,grid)) {
					numStarveTurns = 0;
				}
			}*/
	        if(eatFish(neighborfish,grid)) {
				numStarveTurns = 0;
			}
	        else {
				moveToRandomPlace(emptyNeighbors,grid);
				numStarveTurns++;
	        }
			
			if(numBreedTurns>=breedTurns) {
				breed(emptyNeighbors,grid);
			}		
			
			numBreedTurns++;
		}
	}
	
	private void breed(ArrayList<Cell> emptySpots, Grid grid) {
		SharkCell newshark = new SharkCell(getRow(), getCol());
		if(newshark.moveToRandomPlace(emptySpots,grid)) {
			numBreedTurns = -1;
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
			System.out.println(testFish.getRow() + " " +testFish.getCol());
			System.out.println(getRow() + " " +getCol());
			System.out.println(grid.newGridContainsSharkAt(testFish.getRow(),testFish.getCol()));
			if(grid.newGridContainsSharkAt(testFish.getRow(),testFish.getCol())) {
				fish.remove(testFish);
			}
			else {
				setRow(testFish.getRow()); setCol(testFish.getCol());
				testFish.die(grid); 
				grid.addToNewGrid(this);
				System.out.println("added to grid");
				moved = true;
			}
		}
		System.out.println(moved);
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
	



}
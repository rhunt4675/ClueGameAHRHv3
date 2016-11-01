package experiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

public class IntBoard {
	public Map<BoardCell, Set<BoardCell>> adjCells;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	private static int ROWS = 4;
	private static int COLS = 4;

	public IntBoard() {
		grid = new BoardCell[ROWS][COLS];
		adjCells = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>(); 

		for (int i = 0; i < ROWS; i++){
			for (int j = 0; j < COLS; j++){
				grid[i][j] = new BoardCell(i, j, false);
			}
		}
	}

	// Have the tempAdj inside both for loops so it will make a new temp list for EACH cell rather than for EACH time the function is called.
	public void calcAdjacencies(){
		for (int i = 0; i < ROWS; i++){
			for (int j = 0; j < COLS; j++){
				Set<BoardCell> tempAdj = new HashSet<BoardCell>();
				if (!((i - 1) < 0)){
					tempAdj.add(grid[i - 1][j]);
				}
				if (!((j - 1) < 0)){
					tempAdj.add(grid[i][j - 1]);
				}
				if (!((i + 1) >= ROWS)){
					tempAdj.add(grid[i + 1][j]);
				}
				if (!((j + 1) >= COLS)){
					tempAdj.add(grid[i][j + 1]);
				}
				if (!adjCells.containsKey(grid[i][j])){
					adjCells.put(grid[i][j], tempAdj);
				}
			}
		}
	}

	public void calcTargets(BoardCell startCell, int pathLength){
		// Parameters thisCell and numSteps
		BoardCell thisCell = startCell;
		int numSteps = pathLength;
		visited.add(thisCell);
		
		// for each adjCells
		for (BoardCell cell : adjCells.get(thisCell)) {	
			if (visited.contains(cell)) { //if visited do nothing
				continue;
			} else {	//else add adjCell to visited list
				visited.add(cell);
				if (numSteps == 1) { // if numSteps == 1, add adjCell to Targets
					targets.add(cell);
					visited.remove(cell);
				} else { // else calcTargets(adjCell, numbSteps-1 
					calcTargets(cell, (numSteps-1));
					visited.remove(cell);
				}
			}
		}
	}//End calcTargets

	public Set<BoardCell> getTargets() {
		return targets;
	}

	// Since we just need the set of adjacent cells, we can return the set by using the cell that is passed as a key.
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjCells.get(cell);
	}

	// Changed this, too.  We don't want to return a new BoardCell, we want to return what we already made
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}


}

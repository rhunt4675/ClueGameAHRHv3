package tests;
import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;


public class TVCD_BoardAdjTargetTests {

	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance and initialize it		
		board = Board.getInstance();
		board.setConfigFiles("TVCD_BoardLayout.csv", "TVCD_ClueLegend.txt");		
		board.initialize();
	}

	//ADJECENT TESTS
	
	// Test surrounded by 4 walkways *******************
	// Only need to test 1 per rubric
	// Location is in color GREEN in the spreadsheet
	@Test
	public void testOnlyFourWalkways() {
		// Test only walkways as adjacent locations
		Set<BoardCell> testList = board.getAdjList(14,14);
		assertTrue(testList.contains(board.getCellAt(14, 13)));
		assertTrue(testList.contains(board.getCellAt(14, 15)));
		assertTrue(testList.contains(board.getCellAt(13, 14)));
		assertTrue(testList.contains(board.getCellAt(15, 14)));
		assertEquals(4, testList.size());
	}

	// This tests the locations are at each edge of the board
	// Four different locations per rubric
	// Location is in color SALMON in the spreadsheet
	@Test
	public void testAdjacencyAtEdgeOfBoard()
	{
		// Test on top edge of board, just one walkway piece 
		Set<BoardCell> testList = board.getAdjList(0, 19);
		assertTrue(testList.contains(board.getCellAt(0, 20)));
		assertTrue(testList.contains(board.getCellAt(1, 19)));
		assertEquals(2, testList.size());

		// Test on left edge of board, three walkway pieces 
		testList = board.getAdjList(15, 0);
		assertTrue(testList.contains(board.getCellAt(15, 1)));
		assertTrue(testList.contains(board.getCellAt(16, 0)));
		assertTrue(testList.contains(board.getCellAt(14, 0)));
		assertEquals(3, testList.size());

		// Test on bottom edge of board, next to 1 room piece 
		testList = board.getAdjList(25, 11);
		assertTrue(testList.contains(board.getCellAt(25, 10)));
		assertTrue(testList.contains(board.getCellAt(24, 11)));
		assertEquals(2, testList.size());

		// Test on right edge of board, next to 1 room piece 
		testList = board.getAdjList(10, 25);
		assertTrue(testList.contains(board.getCellAt(9, 25)));
		assertEquals(1, testList.size());
	}


	// Locations beside a room cell that is not a doorway
	// Need 2 different locations
	// Location is in color ORANGE in the spreadsheet
	@Test
	public void testWallBesideIsNotDoor() {

		// Tests an adjacent cell is not a doorway entrance, is a wall
		Set<BoardCell>testList = board.getAdjList(11, 21);
		assertTrue(testList.contains(board.getCellAt(10, 21)));
		assertTrue(testList.contains(board.getCellAt(12, 21)));
		assertTrue(testList.contains(board.getCellAt(11, 22)));
		assertEquals(3, testList.size());

		// Tests a location beside a room cell that is not a doorway and not an entrance
		testList = board.getAdjList(2, 23);
		assertTrue(testList.contains(board.getCellAt(2, 22)));
		assertTrue(testList.contains(board.getCellAt(3, 23)));
		assertEquals(2, testList.size());
	}

	
	// Test of four door entrance directions being adjacent to a doorway with needed direction
	// All four door directions need to be tested
	// Location is in color PURPLE in the spreadsheet
	@Test
	public void testAllAdjecentDoorways() {
		
		// Test the door is able to be entered DOWN
		Set<BoardCell>testList = board.getAdjList(9, 25);
		assertTrue(testList.contains(board.getCellAt(10, 25)));
		assertTrue(testList.contains(board.getCellAt(8, 25)));
		assertTrue(testList.contains(board.getCellAt(9, 24)));
		assertEquals(3, testList.size());
		
		// Test the door is able to be entered UP
		testList = board.getAdjList(6, 20);
		assertTrue(testList.contains(board.getCellAt(5, 20)));
		assertTrue(testList.contains(board.getCellAt(6, 21)));
		assertTrue(testList.contains(board.getCellAt(7, 20)));
		assertEquals(3, testList.size());
		
		// Test the door is able to be entered RIGHT
		testList = board.getAdjList(21, 7);
		assertTrue(testList.contains(board.getCellAt(21, 6)));
		assertTrue(testList.contains(board.getCellAt(21, 8)));
		assertEquals(2, testList.size());
		
		// Test the door is able to be entered LEFT
		testList = board.getAdjList(1, 22);
		assertTrue(testList.contains(board.getCellAt(1, 23)));
		assertTrue(testList.contains(board.getCellAt(2, 22)));
		assertEquals(2, testList.size());
	}

	// Locations are doorways with only exit
	// Need two locations per rubric
	// Location is in color PINK in the spreadsheet
	@Test
	public void testOneDoorwayExitAdjacent() {
		
		// Test the door is able to be exited LEFT only
		Set<BoardCell>testList = board.getAdjList(1, 23);
		assertTrue(testList.contains(board.getCellAt(1, 22)));
		assertEquals(1, testList.size());

		// Test the door is able to be exited RIGHT only
		testList = board.getAdjList(1, 17);
		assertTrue(testList.contains(board.getCellAt(1, 18)));
		assertEquals(1, testList.size());
	}	

	
	
	// TARGETS TEST
	
	// Targets are along walkways, at various distances
	// Need 4 different location distances
	// These are WHITE on the planning spreadsheet
	
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(13, 14, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 13)));
		assertTrue(targets.contains(board.getCellAt(12, 14)));
		assertTrue(targets.contains(board.getCellAt(13, 15)));
		assertTrue(targets.contains(board.getCellAt(14, 14)));

		board.calcTargets(17, 6, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 6)));
		assertTrue(targets.contains(board.getCellAt(18, 6)));
		assertTrue(targets.contains(board.getCellAt(17, 5)));
		assertTrue(targets.contains(board.getCellAt(17, 7)));
	}

	// Tests of just walkways, 2 steps
	// These are WHITE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(24, 11, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(25, 10)));

		board.calcTargets(0, 20, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 19)));
		assertTrue(targets.contains(board.getCellAt(2, 20)));		
	}

	// Tests of just walkways, 4 steps
	// These are WHITE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(12, 0, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 2)));
		assertTrue(targets.contains(board.getCellAt(13, 1)));
		assertTrue(targets.contains(board.getCellAt(13, 3)));
		assertTrue(targets.contains(board.getCellAt(14, 0)));
		assertTrue(targets.contains(board.getCellAt(16, 0)));
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(15, 1)));

		// Includes a path that doesn't have enough length
		board.calcTargets(11, 5, 4);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(7, 5)));
		assertTrue(targets.contains(board.getCellAt(8, 4)));	
		assertTrue(targets.contains(board.getCellAt(9, 5)));
		assertTrue(targets.contains(board.getCellAt(10, 3)));
		assertTrue(targets.contains(board.getCellAt(10, 4)));
		assertTrue(targets.contains(board.getCellAt(12, 8)));
		assertTrue(targets.contains(board.getCellAt(13, 7)));	
	}	

	// Tests of just walkways plus one door, 6 steps
	// These are WHITE on the planning spreadsheet

	/********* This test is wonky for some reason *********/
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(16, 25, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(16, 21)));
		assertTrue(targets.contains(board.getCellAt(18, 21)));	
		
		board.calcTargets(0, 5, 6);
		targets = board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 8)));
		assertTrue(targets.contains(board.getCellAt(2, 8)));	
		assertTrue(targets.contains(board.getCellAt(2, 5)));
		assertTrue(targets.contains(board.getCellAt(1, 6)));
		assertTrue(targets.contains(board.getCellAt(5, 4)));
		assertTrue(targets.contains(board.getCellAt(6, 5)));
		assertTrue(targets.contains(board.getCellAt(4, 7)));
		assertTrue(targets.contains(board.getCellAt(4, 5)));
		assertTrue(targets.contains(board.getCellAt(3, 6)));
		assertTrue(targets.contains(board.getCellAt(2, 7)));
		
	}	
	
	
	// Test getting into a room
	// These are LIGHT GREY on the planning spreadsheet
	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(20, 20, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		// directly UP INTO ROOM
		assertTrue(targets.contains(board.getCellAt(21, 19)));
		assertTrue(targets.contains(board.getCellAt(19, 19)));
		assertTrue(targets.contains(board.getCellAt(19, 21)));
		assertTrue(targets.contains(board.getCellAt(20, 18)));
		
		//One room that is less than 4 steps
		board.calcTargets(11, 14, 4);
		targets= board.getTargets();
		assertEquals(13, targets.size());
		// directly UP INTO ROOM
		assertTrue(targets.contains(board.getCellAt(10, 13)));
		assertTrue(targets.contains(board.getCellAt(11, 15)));
		assertTrue(targets.contains(board.getCellAt(7, 14)));
		assertTrue(targets.contains(board.getCellAt(9, 14)));
		assertTrue(targets.contains(board.getCellAt(9, 12)));
		assertTrue(targets.contains(board.getCellAt(8, 13)));
		assertTrue(targets.contains(board.getCellAt(15, 14)));
		assertTrue(targets.contains(board.getCellAt(13, 14)));
		assertTrue(targets.contains(board.getCellAt(14, 15)));
		assertTrue(targets.contains(board.getCellAt(14, 13)));
		assertTrue(targets.contains(board.getCellAt(13, 16)));
		assertTrue(targets.contains(board.getCellAt(12, 13)));
		assertTrue(targets.contains(board.getCellAt(12, 15)));
		
	}
	
	//Test targets when leaving a room
	// These are DARK BLUE on the planning spreadsheet
	@Test
	public void leaveRoomTargets() {
		
		board.calcTargets(11, 15, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		// directly UP INTO ROOM
		assertTrue(targets.contains(board.getCellAt(12, 15)));
		
		// One room is exactly 2 away
		board.calcTargets(21, 18, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		// directly UP INTO ROOM
		assertTrue(targets.contains(board.getCellAt(19, 18)));
		assertTrue(targets.contains(board.getCellAt(20, 17)));
		assertTrue(targets.contains(board.getCellAt(20, 19)));
	}

}

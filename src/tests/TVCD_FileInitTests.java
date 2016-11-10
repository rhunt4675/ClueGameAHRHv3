/* Casey DeRosa Truyen Van
 * CSCU306 
 * Clue Board Part I tests
 */

package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class TVCD_FileInitTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 26;
	public static final int NUM_COLUMNS = 26;

	// NOTE: I made Board static because I only want to set it up one 
	// time (using @BeforeClass), no need to do setup before each test.
	private static Board board;

	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("TVCD_BoardLayout.csv", "TVCD_ClueLegend.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	@Test
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());

		// Tests to check the legend is loading properly
		assertEquals("COMPUTER ROOM", legend.get('C'));
		assertEquals("FOX ROOM", legend.get('F'));
		assertEquals("THE ROOM ROOM", legend.get('R'));
		assertEquals("CITY OF FLICKERING DESTRUCTION", legend.get('D'));
		assertEquals("THE LONG LIBRARY", legend.get('L'));
		assertEquals("BUTCHER SHOP", legend.get('B'));
		assertEquals("ARWING HANGER", legend.get('A'));
		assertEquals("GLADIATOR PIT", legend.get('G'));
		assertEquals("SWORD ARMORY", legend.get('S'));
		assertEquals("CLOSET", legend.get('X'));
		assertEquals("WALKWAY", legend.get('W'));
	}

	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}

	// test the doorways for each room are in the correct direction
	@Test
	public void FourDoorDirections() {
		// Door for C 
		BoardCell room = board.getCellAt(4, 1); 
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		// Door for F
		room = board.getCellAt(1, 8);	
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		// Door for F
		room = board.getCellAt(1, 17); 
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		// Door for R
		room = board.getCellAt(7, 15); 
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Door for R
		room = board.getCellAt(11, 20); 
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		// Door for S
		room = board.getCellAt(16, 21);  
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		// Door for G
		room = board.getCellAt(21, 19); 
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Door for A
		room = board.getCellAt(21, 6);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		// Door for L
		room = board.getCellAt(17, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		// Door for D
		room = board.getCellAt(13, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());


		// Test that room pieces that aren't doors know it
		room = board.getCellAt(15, 9);
		assertFalse(room.isDoorway());	

		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(5, 13);
		assertFalse(cell.isDoorway());		

	}

	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(18, numDoors);
	}

	// Test a the room cells to ensure the room initial is correct.
	@Test
	public void testRoomInitials() {
		assertEquals('F', board.getCellAt(0, 7).getInitial()); // Room for F
		assertEquals('B', board.getCellAt(0, 25).getInitial()); // Room for B
		assertEquals('X', board.getCellAt(9, 8).getInitial()); // Room for X
		assertEquals('R', board.getCellAt(7, 17).getInitial()); // Room for R
		assertEquals('G', board.getCellAt(25, 25).getInitial()); // Room for G
		assertEquals('L', board.getCellAt(16, 1).getInitial()); // Room for L
		assertEquals('A', board.getCellAt(20, 4).getInitial()); // Room for A
		assertEquals('D', board.getCellAt(11, 0).getInitial()); // Room for D
		assertEquals('S', board.getCellAt(14, 25).getInitial()); // Room for S

	}


}

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.BadConfigFormatException;

public class Board extends JPanel implements ActionListener, MouseListener {
	private final long serialVersionUID = -535617142739434066L;
	private BoardCell[][] board;
	private Map<Character, String> rooms;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private static String boardConfigFile;
	private static String roomConfigFile;
	
	private Solution solution;
	private String[] enterableRooms;
	private Player[] players;
	private String[] weapons;
	private Card[] cards;
	
	private int currentPlayerIndex = -1;
	private boolean humanMustFinishTurn = false;
	private Set<BoardCell> visibleTargets = null;
	private ControlPanel controlpanel;
	
	private int numRows = 0;
	private int numColumns = 0;
	public final int MAX_BOARD_SIZE = 50;

	private static Board theInstance = new Board();

	/***********************************************************************/
	private Board() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		rooms = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		solution = new Solution();
		
		addMouseListener(this);
	}

	/***********************************************************************/
	public static Board getInstance(){
		return theInstance;
	}

	/***********************************************************************/
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
		} catch (BadConfigFormatException e){
			System.out.println(e.getMessage());
		}
	}

	/***********************************************************************/	
	public void loadRoomConfig() throws BadConfigFormatException{
		// Reset Parameters
		rooms.clear();
		List<String> cardRooms = new ArrayList<String>();
		
		Scanner in = null;		
		try{
			FileReader roomConfig = new FileReader(roomConfigFile);
			in = new Scanner(roomConfig);
			while (in.hasNextLine()){
				String line = in.nextLine();
				String[] type = line.split(", ");
				if (!(type[2].equals("Card") || type[2].equals("Other"))){
					throw new BadConfigFormatException(roomConfigFile);
				}
				
				char symbol = type[0].charAt(0);
				String theRoom = type[1];
				Boolean valid = type[2].equals("Card") ? true : false;
				rooms.put(symbol, theRoom);
				if (valid) cardRooms.add(theRoom);
			}
			in.close();
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		} finally {
			if (in != null) in.close();
		}
		
		enterableRooms = new String[cardRooms.size()];
		for (int i = 0; i < cardRooms.size(); i++)
			enterableRooms[i] = cardRooms.get(i);
	}

	/***********************************************************************/
	public void loadBoardConfig() throws BadConfigFormatException {
		// Reset Parameters
		for (BoardCell bc[] : board)
			Arrays.fill(bc, null);
		numRows = numColumns = 0;
		Scanner in = null;
		
		try {
			FileReader boardConfig = new FileReader(boardConfigFile);
			in = new Scanner(boardConfig);
			int i = 0;
			int firstLine = 0;
			while (in.hasNextLine()){
				numRows++;
				String line = in.nextLine();
				String[] spot = line.split(",");

				if (numColumns == 0){
					firstLine = spot.length;
					numColumns = firstLine;
				}
				numColumns = spot.length;
				if (numColumns != firstLine){
					throw new BadConfigFormatException(boardConfigFile);
				}

				for (int j = 0; j < spot.length; j++){
					char initial = spot[j].charAt(0);
					if (!rooms.containsKey(initial))
						throw new BadConfigFormatException(boardConfigFile);
					boolean isRoom = initial != 'W';
					
					board[i][j] = new BoardCell(i, j, isRoom);
					board[i][j].setInitial(initial);					
					
					if (spot[j].length() == 2){
						switch (spot[j].charAt(1)){
						case 'L':
						case 'R':
						case 'U':
						case 'D':
							board[i][j].setDoorDirection(spot[j].charAt(1));
							break;
						case 'N':
							board[i][j].setName();
							break;
						default:
							break;
						}	
					}
				}
				i++;
			}

		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		} finally {
			if (in != null) in.close();
		}
	}

	/***********************************************************************/
	public void calcAdjacencies() {
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numColumns; j++){
				Set<BoardCell> tempAdj = new HashSet<BoardCell>();
				if (board[i][j].getInitial() != 'W'){
					if (board[i][j].isDoorway()){
						switch(board[i][j].getDoorDirection()){
						case LEFT:
							tempAdj.add(board[i][j - 1]);
							break;
						case RIGHT:
							tempAdj.add(board[i][j + 1]);
							break;
						case UP:
							tempAdj.add(board[i - 1][j]);
							break;
						case DOWN:
							tempAdj.add(board[i + 1][j]);
							break;
						default:
							break;
						}
					}
				}
				else {
					if (!((i - 1) < 0)) {
						if (board[i - 1][j].getInitial() == 'W' || board[i - 1][j].getDoorDirection() == DoorDirection.DOWN) {
							tempAdj.add(board[i - 1][j]);
						}
					}
					if (!((j - 1) < 0)) {
						if (board[i][j - 1].getInitial() == 'W' || board[i][j - 1].getDoorDirection() == DoorDirection.RIGHT) {
							tempAdj.add(board[i][j - 1]);
						}
					}
					if (!((i + 1) >= numRows)) {
						if (board[i + 1][j].getInitial() == 'W' || board[i + 1][j].getDoorDirection() == DoorDirection.UP) {
							tempAdj.add(board[i + 1][j]);
						}
					}
					if (!((j + 1) >= numColumns)) {
						if (board[i][j + 1].getInitial() == 'W' || board[i][j + 1].getDoorDirection() == DoorDirection.LEFT) {
							tempAdj.add(board[i][j + 1]);
						}
					}
				}
				if (!adjMatrix.containsKey(board[i][j])) {
					adjMatrix.put(board[i][j], tempAdj);
				}
			}
		}

	}

	/***********************************************************************/
	public void calcTargets(int row, int col, int pathLength) { // removed for tests: BoardCell startCell
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		
		visited.add(board[row][col]);
		
		findTargets(row, col, pathLength);
	}//End calcTargets	


	/***********************************************************************/
	
	public void findTargets(int row, int col, int pathLength){
		BoardCell thisCell = board[row][col];

		// Iterate through adjacent BoardCell(s)
		for (BoardCell adj : adjMatrix.get(thisCell)) {
			// Don't double back on our path
			if (visited.contains(adj))
				continue;
			
			// Protect ourselves from doubling back in the next round
			visited.add(adj);
			
			// Add a target if we expired the pathLength counter, otherwise recurse
			if (pathLength == 1 || adj.isDoorway())
				targets.add(adj);
			else
				findTargets(adj.getRow(), adj.getColumn(), pathLength - 1);
			
			// Clean adjacent cell out of visited set
			visited.remove(adj);
		}
	}
	
	/***********************************************************************/
	public void setConfigFiles(String board, String room) {
		boardConfigFile = "data/" + board;
		roomConfigFile = "data/" + room;
	}

	/***********************************************************************/
	public Map<Character, String> getLegend() {
		return  rooms; // To make it pass, return rooms
	}

	/***********************************************************************/
	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}

	/***********************************************************************/
	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	}

	/***********************************************************************/
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		// TODO Auto-generated method stub
		return adjMatrix.get(board[i][j]);
	}

	public Set<BoardCell> getTargets() {
		// TODO Auto-generated method stub
		return targets;
	}
	
	// We split this into loadPlayerConfigurationFile and loadCardConfigurationFile
	public void loadConfigFiles() {
		
	}
	
	public void loadPlayerConfigurationFile(String filename) throws FileNotFoundException, BadConfigFormatException {
		Scanner in = null;
		try {
			FileReader playerConfig = new FileReader(filename);
			in = new Scanner(playerConfig);
			
			int numPlayers = Integer.parseInt(in.nextLine());
			players = new Player[numPlayers];
			
			for (int i = 0; i < numPlayers; i++) {
				String line = in.nextLine();
				String[] tags = line.split(", ");
				
				if (tags.length != 5)
					throw new BadConfigFormatException("Each player must be specified by five identifiers: C/H, name, row, column, color");
				
				// Parse tags
				String type = tags[0];
				String name = tags[1];
				int row = Integer.parseInt(tags[2]);
				int column = Integer.parseInt(tags[3]);
				Color color = (Color) Class.forName("java.awt.Color").getField(tags[4].toUpperCase()).get(null);
				
				// Computer/Human Distinction in First Column
				if (type.equals("C")) {
					players[i] = new ComputerPlayer(name, row, column, color);
				} else if (type.equals("H")) {
					players[i] = new HumanPlayer(name, row, column, color);
					currentPlayerIndex = i;
				} else {
					throw new BadConfigFormatException("Invalid player type specifier: " + type);
				}
			}
			in.close();
			
		} catch (NoSuchElementException e) {
			throw new BadConfigFormatException("First line identifier inconsistent with number of players.");
		} catch (NumberFormatException e) {
			throw new BadConfigFormatException("Expected row/column identifiers in columns 3 and 4.");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (in != null) in.close();
		}
	}
	
	public void loadWeaponConfigurationFile(String filename) throws FileNotFoundException, BadConfigFormatException {
		Scanner in = null;
		try {
			FileReader cardConfig = new FileReader(filename);
			in = new Scanner(cardConfig);
			
			String numWeapons = in.nextLine();
			weapons = new String[Integer.parseInt(numWeapons)];
			
			// Iterate through weapons
			for (int i = 0; i < weapons.length; i++) {
				String weapon = in.nextLine();
				
				if (weapon.length() == 0)
					throw new BadConfigFormatException("Each weapon must have a non-zero length identifier.");
				
				weapons[i] = weapon;
			}
		} catch (NumberFormatException e) {
			throw new BadConfigFormatException("Expected integer.");
		} finally {
			if (in != null) in.close();
		}
	}
	
	public void generateDeck() {
		int numCards = weapons.length + players.length + enterableRooms.length;
		cards = new Card[numCards];
		
		
		int i = 0, j = 0;
		for (j = i; j < weapons.length + i; j++)
			cards[j] = new Card(CardType.WEAPON, weapons[j - i]);
		i = j;
		
		for (j = i; j < players.length + i; j++)
			cards[j] = new Card(CardType.PERSON, players[j - i].getName());
		i = j;
		
		for (j = i; j < enterableRooms.length + i; j++)
			cards[j] = new Card(CardType.ROOM, enterableRooms[j - i]);
		i = j;
		
		int weaponSolIndex = (int) Math.random() * weapons.length;
		int playerSolIndex = (int) Math.random() * players.length;
		int roomSolIndex = (int) Math.random() * enterableRooms.length;
		
		solution.weapon = weapons[weaponSolIndex];
		solution.person = players[playerSolIndex].getName();
		solution.room = enterableRooms[roomSolIndex];
	}
	
	public void deal() {
		int playerPointer = 0;
		
		// Iterate through cards
		Collections.shuffle(Arrays.asList(cards));
		for (Card c : cards) {
			if (c.getCardName().equals(solution.weapon)
					|| c.getCardName().equals(solution.person)
					|| c.getCardName().equals(solution.room))
				continue;
			
			players[playerPointer].addCard(c);
			playerPointer = (playerPointer + 1) % players.length;
		}
	}
	
	public Card handleSuggestion(Solution suggestion) {
		// Iterate through players
		int playerNum = currentPlayerIndex;
		for (int i = 0; i < players.length - 1; i++) {
			playerNum = (playerNum + 1) % players.length;
			
			Card action = players[playerNum].disproveSuggestion(suggestion);
			if (action != null)
				return action;
		}
		return null;
	}
	
	public boolean checkAccusation(Solution accusation) {
		return (solution != null
				&& solution.person != null && solution.person.equals(accusation.person)
				&& solution.room != null && solution.room.equals(accusation.room)
				&& solution.weapon != null && solution.weapon.equals(accusation.weapon));
	}
	
	// turns a string input into a card
	public Card returnCard(String name) {
		for (Card c : cards) {
			if (c.getCardName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				board[i][j].draw(g);
			}
		}
		
		for (Player p : players) {
			int row = p.getRow();
			int col = p.getColumn();
			board[row][col].drawPlayer(p.getColor(), g);
		}
		
 		if (visibleTargets != null) {
			for (BoardCell bc : visibleTargets) {
				int row = bc.getRow();
				int col = bc.getColumn();
				board[row][col].drawTarget(g);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == controlpanel.nextPlayerButton) {
			if (humanMustFinishTurn) {
				JOptionPane.showMessageDialog(this, "You must finish your turn!");
			} else {
				// Setup Control Panel
				Player player = getCurrentPlayer();
				int roll = (int) (Math.random() * 6) + 1;
				controlpanel.setPlayerText(player.getName());
				controlpanel.setDieRollText(Integer.toString(roll));
				
				// Generate and Utilize Targets
				calcTargets(player.row, player.column, roll);
				Set<BoardCell> targets = getTargets();
				humanMustFinishTurn = player.makeMove(targets);				
				
				if (player instanceof HumanPlayer) visibleTargets = targets;
				else visibleTargets = null;
				
				if (!humanMustFinishTurn) advancePlayer();
				
				repaint();
			}
		} else if (e.getSource() == controlpanel.makeAccusationButton) {
			// Handle Make Accusation Button
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// Only allowed to select cell when human player is active
		if (!humanMustFinishTurn)
			return;
		
		int row = (arg0.getY() / (BoardCell.cellHeight));
		int col = (arg0.getX() / (BoardCell.cellWidth));
		
		if (row >= 0 && row < numRows && col >= 0 && col < numColumns) {
			// Check if selection is valid
			for (BoardCell bc : visibleTargets) {
				if (row == bc.getRow() && col == bc.getColumn()) {
					getCurrentPlayer().column = col;
					getCurrentPlayer().row = row;
					
					advancePlayer();
					humanMustFinishTurn = false;
					visibleTargets = null;
					repaint();
					return;
				}
			}
			
			// If we got to here, invalid selection
			JOptionPane.showMessageDialog(this, "Invalid target selection!");
		}
	}

	
	public String[] getAllWeapons() {
		return Arrays.copyOf(weapons, weapons.length);
	}
	
	public Player[] getAllPlayers() {
		return Arrays.copyOf(players, players.length);
	}
	
	public String[] getAllRooms() {
		return Arrays.copyOf(enterableRooms, enterableRooms.length);
	}
	
	public String getRoomName(char roomInitial){
		return rooms.get(roomInitial);
	}
	
	public Player getCurrentPlayer() {
		return players[currentPlayerIndex];
	}
	
	public void advancePlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
	}
	
	public void setControlPanel(ControlPanel cp) {
		controlpanel = cp;
	}

	/*  Testing Only */
	public Player[] getPlayers() {return players;}
	public String[] getWeapons() { return weapons; }
	public Card[] getCards() { return cards;}
	public Solution getSolution() { return solution;}
	
	public void setWeapons(String[] param) {weapons = param; }
	public void setPersons(Player[] param) {players = param; }
	public void setLegend(Map<Character, String> map) {rooms = map; }
	public void setCurrentPlayerIndex(int index) {currentPlayerIndex = index; }

	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mousePressed(MouseEvent arg0) {}
	@Override public void mouseReleased(MouseEvent arg0) {}
}

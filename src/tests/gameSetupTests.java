package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.Color;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class gameSetupTests {
	private static Board board;
	
	/* Setup static Board */
	@BeforeClass
	public static void init() {
		board = Board.getInstance();
		board.setConfigFiles("TVCD_BoardLayout.csv", "TVCD_ClueLegend.txt");		
		board.initialize();
	}
	
	/* Test loading of people from "Players.txt" */
	@Test
	public void loadPeople() throws FileNotFoundException, BadConfigFormatException {
		board.loadPlayerConfigurationFile("Players.txt");
		Player players[] = board.getPlayers();
		
		/* Check Number of Players */
		assertEquals(players.length, 6);
		
		/* Check a Computer Player */
		assertEquals(players[0].getName(), "Austin");
		assertEquals(players[0].getRow(), 0);
		assertEquals(players[0].getColumn(), 3);
		assertEquals(players[0].getColor(), Color.RED);
		assert(players[0] instanceof ComputerPlayer);
		
		/* Check another Computer Player */
		assertEquals(players[1].getName(), "Ryan");
		assertEquals(players[1].getRow(), 0);
		assertEquals(players[1].getColumn(), 16);
		assertEquals(players[1].getColor(), Color.GREEN);
		assert(players[1] instanceof ComputerPlayer);
		
		/* Check a Human Player */
		assertEquals(players[2].getName(), "Kevin");
		assertEquals(players[2].getRow(), 9);
		assertEquals(players[2].getColumn(), 0);
		assertEquals(players[2].getColor(), Color.BLUE);
		assert(players[2] instanceof HumanPlayer);
	}
	
	/* Test loading of cards (from "Weapons.txt", "Players.txt", "Legend.txt") */
	@Test
	public void loadCards() throws FileNotFoundException, BadConfigFormatException {
		board.loadPlayerConfigurationFile("Players.txt");
		board.loadWeaponConfigurationFile("Weapons.txt");
		board.generateDeck();
		
		/* Check number of cards */
		Card cards[] = board.getCards();
		assertEquals(cards.length, 18);
		
		/* Count number of each type of card */
		int people = 0, weapons = 0, rooms = 0;
		for (Card c : cards) {
			switch (c.getType()) {
			case PERSON: people++; break;
			case WEAPON: weapons++; break;
			case ROOM: rooms++; break;
			}
		}
		
		/* Assert number of each type of card */
		assertEquals(people, 6);
		assertEquals(weapons, 3);
		assertEquals(rooms, 9);
		
		/* Check a Weapon */
		assertEquals(cards[0].getCardName(), "Chain");
		assertEquals(cards[0].getType(), CardType.WEAPON);
		
		/* Check a Person */
		assertEquals(cards[3].getCardName(), "Austin");
		assertEquals(cards[3].getType(), CardType.PERSON);
		
		/* Check a Room */
		assertEquals(cards[9].getCardName(), "COMPUTER ROOM");
		assertEquals(cards[9].getType(), CardType.ROOM);
	}
	
	/* Check dealing */
	@Test
	public void dealCards() throws FileNotFoundException, BadConfigFormatException {
		board.loadPlayerConfigurationFile("Players.txt");
		board.loadWeaponConfigurationFile("Weapons.txt");
		board.generateDeck();
		board.deal();

		Player[] players = board.getPlayers();
		Card[] cards = board.getCards();
		
		List<List<Card>> hands = new ArrayList<List<Card>>();
		int numCards = 0;
		for (Player p : players) {
			hands.add(p.getCards());
			numCards += p.getCards().size();
		}
		
		/* All cards are dealt (except for 3 solution cards) */
		assertEquals(cards.length - 3, numCards);
		
		/* All players have similar numbers of cards */
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
		for (List<Card> hand : hands) {
			if (hand.size() < min) min = hand.size();
			if (hand.size() > max) max = hand.size();
		}
		assertTrue(min == max || min + 1 == max);
		
		/* No duplicate dealt cards */
		Set<Card> cardSet = new HashSet<Card>();
		for (List<Card> hand : hands) {
			cardSet.addAll(hand);
		}
		assertEquals(cardSet.size(), numCards);
	}
	
	@Test
	public void testTargetSelection() {
		/* Random Selection with No Rooms in List */
		ComputerPlayer player = new ComputerPlayer("Test", 0, 0, null);
		board.calcTargets(4, 14, 2);
		boolean loc_4_12 = false, loc_5_13 = false, loc_6_14 = false, loc_5_15 = false, loc_4_16 = false;
		
		for (int i = 0; i < 1000; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(4, 12)) loc_4_12 = true;
			else if (selected == board.getCellAt(5, 13)) loc_5_13 = true;
			else if (selected == board.getCellAt(6, 14)) loc_6_14 = true;
			else if (selected == board.getCellAt(5, 15)) loc_5_15 = true;
			else if (selected == board.getCellAt(4, 16)) loc_4_16 = true;
		}
		assertTrue(loc_4_12);
		assertTrue(loc_5_13);
		assertTrue(loc_6_14);
		assertTrue(loc_5_15);
		assertTrue(loc_4_16);
		
		/* Non-Random Selection with non-visited Room in List */
		board.calcTargets(10, 4, 2);
		
		for (int i = 0; i < 1000; i++) {
			player.setLastRoomLocation(' ');
			BoardCell selected = player.pickLocation(board.getTargets());
			assertEquals(selected, board.getCellAt(10, 3));
		}
		
		/* Random Selection with visited Room in List */
		player.setLastRoomLocation('D');
		board.calcTargets(10, 4, 2);
		boolean loc_10_3 = false, loc_8_4 = false, loc_9_5 = false, loc_11_5 = false;

		for (int i = 0; i < 1000; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if (selected == board.getCellAt(10, 3)) loc_10_3 = true;
			else if (selected == board.getCellAt(8, 4)) loc_8_4 = true;
			else if (selected == board.getCellAt(9, 5)) loc_9_5 = true;
			else if (selected == board.getCellAt(11, 5)) loc_11_5 = true;
		}
		assertTrue(loc_10_3);
		assertTrue(loc_8_4);
		assertTrue(loc_9_5);
		assertTrue(loc_11_5);
	}
	
	@Test
	public void testAccusation() throws FileNotFoundException, BadConfigFormatException {
		board.loadPlayerConfigurationFile("Players.txt");
		board.loadWeaponConfigurationFile("Weapons.txt");
		board.generateDeck();
		board.deal();
		Solution solution = board.getSolution();
		
		/* Correct Solution */
		assertTrue(board.checkAccusation(solution));
		
		/* Wrong Person	*/
		Solution wrongPerson = new Solution();
		wrongPerson.person = "WrongPlayer";
		wrongPerson.weapon = solution.weapon;
		wrongPerson.room = solution.room;
		assertFalse(board.checkAccusation(wrongPerson));
		
		/* Wrong Weapon */
		Solution wrongWeapon = new Solution();
		wrongWeapon.person = solution.person;
		wrongWeapon.weapon = "WrongWeapon";
		wrongWeapon.room = solution.room;
		assertFalse(board.checkAccusation(wrongWeapon));
		
		/* Wrong Room */
		Solution wrongRoom = new Solution();
		wrongRoom.person = solution.person;
		wrongRoom.weapon = solution.weapon;
		wrongRoom.room = "WrongRoom";
		assertFalse(board.checkAccusation(wrongRoom));
	}
	
	@Test
	public void testSuggestion() {
		ComputerPlayer player = new ComputerPlayer("Test", 16, 21, null);
		player.setLastRoomLocation('T'); // Fake Room
		player.setSeenWeapons(Arrays.asList(new Card(CardType.WEAPON, "weapon_0"), new Card(CardType.WEAPON, "weapon_1"), new Card(CardType.WEAPON, "weapon_2")));
		player.setSeenPersons(Arrays.asList(new Card(CardType.PERSON, "person_1")));
		
		Map<Character, String> map = new HashMap<Character, String>();
		map.put('T', "TestRoom");

		String[] weapons = {"weapon_0", "weapon_1", "weapon_2", "weapon_3"};
		Player[] players = {new HumanPlayer("person_1", 0 ,0, null), new HumanPlayer("person_2", 0 ,0 ,null)};
		board.setWeapons(weapons);
		board.setPersons(players);
		board.setLegend(map);
		Solution suggestion = player.createSuggestion();
		
		/* Room matches Current Location */
		assertTrue(suggestion.room.equals("TestRoom"));
		
		/* Only one weapon not seen */
		assertTrue(suggestion.weapon.equals("weapon_3"));
		
		/* Only one person not seen */
		assertTrue(suggestion.person.equals("person_2"));
		
		/* Random weapon & person selection if more than one unknown*/
		String[] new_weapons = {"weapon_0", "weapon_1", "weapon_2", "weapon_3", "weapon_4"};
		Player[] new_players = {new HumanPlayer("person_1", 0 ,0, null), new HumanPlayer("person_2", 0 ,0 ,null), new HumanPlayer("person_3", 0, 0, null)};
		board.setWeapons(new_weapons);
		board.setPersons(new_players);
		
		boolean weapon_3 = false, weapon_4 = false, person_2 = false, person_3 = false;
		
		for (int i = 0; i < 100; i++) {
			suggestion = player.createSuggestion();
			if (suggestion.weapon.equals("weapon_3")) weapon_3 = true;
			else if (suggestion.weapon.equals("weapon_4")) weapon_4 = true;
			
			if (suggestion.person.equals("person_2")) person_2 = true;
			else if (suggestion.person.equals("person_3")) person_3 = true;
		}
		assertTrue(weapon_3);
		assertTrue(weapon_4);
		assertTrue(person_2);
		assertTrue(person_3);
	}
	
	/* Disprove suggestion */
	@Test
	public void disproveSuggestion() {
		ComputerPlayer player = new ComputerPlayer("Test", 16, 21, null);
		Card[] hand = {new Card(CardType.WEAPON, "weapon_0"), new Card(CardType.PERSON, "person_0"), new Card(CardType.ROOM, "room_0")}; 
		player.addCard(hand[0]);
		player.addCard(hand[1]);
		player.addCard(hand[2]);
		
		/* 1 matching card */
		Solution suggestion = new Solution();
		suggestion.weapon = "weapon_0";
		suggestion.person = "wrong_person_0";
		suggestion.room = "wrong_room_0";
		Card card = player.disproveSuggestion(suggestion);
		assertEquals(card, hand[0]);
		
		/* >1 matching card */
		suggestion = new Solution();
		suggestion.weapon = "weapon_0";
		suggestion.person = "person_0";
		suggestion.room = "wrong_room_0";
		
		boolean weapon = false, person = false;
		for (int i = 0; i < 1000; i++) {
			card = player.disproveSuggestion(suggestion);
			if (card == hand[0]) weapon = true;
			else if (card == hand[1]) person = true;
		}
		assertTrue(weapon);
		assertTrue(person);
		
		/* No matching card */
		suggestion = new Solution();
		suggestion.weapon = "wrong_weapon_0";
		suggestion.person = "wrong_person_0";
		suggestion.room = "wrong_room_0";
		card = player.disproveSuggestion(suggestion);
		assertEquals(card, null);
	}
	
	/* Handle suggestion */
	@Test
	public void handleSuggestion() throws FileNotFoundException, BadConfigFormatException {
		Player[] players = {new HumanPlayer("hp_0", 0, 0, null), new HumanPlayer("hp_1", 0 ,0, null), new ComputerPlayer("cp_0", 0, 0, null)};
		board.setPersons(players);
		board.setCurrentPlayerIndex(0);
		
		Card[] cards = {new Card(CardType.PERSON, "person_0"), new Card(CardType.WEAPON, "weapon_0"), new Card(CardType.ROOM, "room_0")};  
		players[0].addCard(cards[0]);
		players[1].addCard(cards[1]);
		players[2].addCard(cards[2]);
		
		/* No one can disprove */
		Solution suggestion = new Solution();
		suggestion.person = "wrong_person_0";
		suggestion.weapon = "wrong_weapon_0";
		suggestion.room = "wrong_room_0";
		Card c = board.handleSuggestion(suggestion);
		assertEquals(c, null);
		
		/* Accusing player can disprove */
		suggestion.person = "person_0";
		suggestion.weapon = "wrong_weapon_0";
		suggestion.room = "wrong_room_0";
		c = board.handleSuggestion(suggestion);
		assertEquals(c, null);
		
		/* Human player can disprove */
		suggestion.person = "wrong_person_0";
		suggestion.weapon = "weapon_0";
		suggestion.room = "wrong_room_0";
		c = board.handleSuggestion(suggestion);
		assertEquals(c, cards[1]);
		
		/* Human accuser player can disprove */
		board.setCurrentPlayerIndex(1);
		suggestion.person = "wrong_person_0";
		suggestion.weapon = "weapon_0";
		suggestion.room = "wrong_room_0";
		c = board.handleSuggestion(suggestion);
		assertEquals(c, null);
		
		/* Two players can disprove */
		board.setCurrentPlayerIndex(2);
		suggestion.person = "person_0";
		suggestion.weapon = "weapon_0";
		suggestion.room = "wrong_room_0";
		c = board.handleSuggestion(suggestion);
		assertEquals(c, cards[0]);
		
		/* Two players can disprove, Computer player disproves next */
		board.setCurrentPlayerIndex(1);
		suggestion.person = "person_0";
		suggestion.weapon = "wrong_weapon_0";
		suggestion.room = "room_0";
		c = board.handleSuggestion(suggestion);
		assertEquals(c, cards[2]);
	}
}


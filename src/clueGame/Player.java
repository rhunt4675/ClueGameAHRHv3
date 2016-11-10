package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Player {
	private String playerName;
	protected int row;
	protected int column;
	private Color color;
	private List<Card> myCards = new ArrayList<Card>();
	
	protected static List<Card> seenWeapons = new ArrayList<Card>();
	protected static List<Card> seenPersons = new ArrayList<Card>();
	protected static List<Card> seenRooms = new ArrayList<Card>();
	
	public Player(String name, int row, int column, Color color) {
		this.playerName = name;
		this.row = row;
		this.column = column;
		this.color = color;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		List<Card> options = new ArrayList<Card>();
		for (Card c : myCards) {
			if (c.type == CardType.PERSON && c.getCardName().equals(suggestion.person)
				|| c.type == CardType.ROOM && c.getCardName().equals(suggestion.room)
				|| c.type == CardType.WEAPON && c.getCardName().equals(suggestion.weapon))
				options.add(c);
		}
		
		if (options.size() > 0) {
			Card choice = options.get((int) (Math.random() * options.size()));
			
			if (choice.type == CardType.WEAPON) seenWeapons.add(choice);
			else if (choice.type == CardType.PERSON) seenPersons.add(choice);
			else if (choice.type == CardType.ROOM) seenRooms.add(choice);
			
			return choice;
		} else
			return null;
	}
	
	public void addCard(Card card) {
		myCards.add(card);
	}
	
	@Override
	public String toString() {
		return playerName;
	}
	
	public abstract boolean makeMove(Set<BoardCell> targets);
	
	public String getName() { return playerName; }
	public int getRow() { return row; }
	public int getColumn() { return column; }
	public Color getColor() { return color; }
	public List<Card> getCards() { return myCards; }
}
package clueGame;

public class Card {
	
	private String cardName;
	CardType type;
	
	public Card(CardType type, String name) {
		this.cardName = name;
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Card))
			return false;
		
		Card card = (Card) obj;
		return (cardName.equals(card.getCardName()) && type == card.getType());
	}

	public String getCardName() {
		return cardName;
	}

	public CardType getType() {
		return type;
	}
}



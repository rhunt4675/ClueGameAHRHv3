package clueGame;

public enum CardType {
	PERSON("PERSON"), WEAPON("WEAPON"), ROOM("ROOM");
	private String value;
	
	CardType(String value){
		this.value = value;
	}
	
	// Return an enum member given a string representation
	static CardType fromString(String str) {
		for (CardType type : CardType.values())
			if (type.value.equals(str))
				return type;
		return null;
	}
}

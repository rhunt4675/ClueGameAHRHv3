package clueGame;

public class BadConfigFormatException extends Exception{
	private static final long serialVersionUID = -8436274110449011956L;

	public BadConfigFormatException() {
		super("Your Config Sucks");
	}

	public BadConfigFormatException(String fileName) {
		super("Incorrect format for "+ fileName);
	}

}

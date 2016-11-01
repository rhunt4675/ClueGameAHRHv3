package clueGame;

public class BadConfigFormatException extends Exception{
	
	private String fileName;
	
	public BadConfigFormatException() {
		super("Your Config Sucks");
		// TODO Auto-generated constructor stub
	}

	public BadConfigFormatException(String fileName) {
		super("Incorrect format for "+ fileName);
		this.fileName = fileName;
		// TODO Auto-generated constructor stub
	
		// try catch for log file
	}

}

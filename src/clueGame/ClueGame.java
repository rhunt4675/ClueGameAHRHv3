package clueGame;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ClueGame extends JFrame{
	private static Board board;

	public static void main(String[] args) {
		board = Board.getInstance();
		board.setConfigFiles("Clue.csv", "legend.txt");		
		board.initialize();

		JFrame frame = new JFrame();
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(Board.getInstance());
		frame.setVisible(true);
	}
}
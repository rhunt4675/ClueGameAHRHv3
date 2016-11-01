package clueGame;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Game {

	public Game() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		JFrame todd = new JFrame();
		todd.setSize(600, 200);
		todd.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		todd.add(new ControlPanel());
		todd.setVisible(true);
		todd.pack();
	}

}

package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class ClueGame extends JFrame {
	private static final long serialVersionUID = 655655196393659806L;
	private Board board;
	private JMenuItem detectiveNotesMenuItem;

	public ClueGame() throws FileNotFoundException, BadConfigFormatException {

		board = Board.getInstance();
		board.setConfigFiles("Clue.csv", "legend.txt");
		board.initialize();
		try {
			board.loadPlayerConfigurationFile("Players.txt");
		} catch (FileNotFoundException| BadConfigFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}
		try {
			board.loadWeaponConfigurationFile("Weapons.txt");
		} catch (FileNotFoundException| BadConfigFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}

		board.generateDeck();
		board.deal();

		detectiveNotesMenuItem = new JMenuItem("Show Detective Notes");
		detectiveNotesMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == detectiveNotesMenuItem) {
					DetectiveNotes dn = new DetectiveNotes();
					dn.setVisible(true);
				}
			}
		}
	);

		JMenu menu = new JMenu("File");
		menu.add(detectiveNotesMenuItem);

		JMenuBar menubar = new JMenuBar();
		menubar.add(menu);


		setSize(700, 700);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		add(board, BorderLayout.CENTER);

		ControlPanel cp = new ControlPanel();
		add(cp, BorderLayout.SOUTH);
		
		setVisible(true);
		setJMenuBar(menubar);
		Player[] players = board.getAllPlayers();
		String name = "";
		List<Card> cards = null;
		for (Player p : players) {
			if (p instanceof HumanPlayer) {
				name = p.getName();
				cards = p.getCards();
			}
		}
		MyCards c = new MyCards(cards);
		c.setPreferredSize(new Dimension(100, 0));
		add(c, BorderLayout.EAST);
		String message = "You are " + name + "," + " press Next Player to begin play";
		JOptionPane.showMessageDialog(this, message, "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);

	}
	
	public static void main(String[] args) {
		try {
			ClueGame cg = new ClueGame();
		} catch (FileNotFoundException | BadConfigFormatException e){
			e.printStackTrace();
		}
	}

}
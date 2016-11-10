package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class ClueGame extends JFrame {
	private static final long serialVersionUID = 655655196393659806L;
	private DetectiveNotes dn;

	public void init() {
		ControlPanel cp = new ControlPanel();

		Board board = Board.getInstance();
		board.setConfigFiles("Clue.csv", "legend.txt");
		board.initialize();
		
		try {
			board.loadPlayerConfigurationFile("Players.txt");
			board.loadWeaponConfigurationFile("Weapons.txt");
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}

		board.generateDeck();
		board.deal();
		board.setControlPanel(cp);

		JMenuItem detectiveNotesMenuItem = new JMenuItem("Show Detective Notes");
		detectiveNotesMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == detectiveNotesMenuItem) {
					dn.setVisible(true);
				}
			}
		});

		JMenu menu = new JMenu("File");
		menu.add(detectiveNotesMenuItem);
		JMenuBar menubar = new JMenuBar();
		menubar.add(menu);

		setSize(700, 700);
		setJMenuBar(menubar);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		add(board, BorderLayout.CENTER);
		add(cp, BorderLayout.SOUTH);
		
		setVisible(true);

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
		String message = "You are " + name + ", press Next Player to begin play";
		JOptionPane.showMessageDialog(this, message, "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String[] args) {
		(new ClueGame()).init();
	}
}
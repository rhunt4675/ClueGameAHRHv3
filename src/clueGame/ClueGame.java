package clueGame;

import java.awt.BorderLayout;
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

public class ClueGame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 655655196393659806L;
	private JMenuItem detectiveNotesMenuItem, exitMenuItem;
	private ControlPanel cp = new ControlPanel();
	private DetectiveNotes dn;

	public void init() {
		// Setup Board -- Errors are Fatal
		try {
			Board.getInstance().setConfigFiles("Clue.csv", "legend.txt");
			Board.getInstance().initialize();
			Board.getInstance().loadPlayerConfigurationFile("Players.txt");
			Board.getInstance().loadWeaponConfigurationFile("Weapons.txt");
			Board.getInstance().generateDeck();
			Board.getInstance().deal();
			Board.getInstance().setControlPanel(cp);
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
			System.exit(1);
		}

		// Setup the JMenu
		detectiveNotesMenuItem = new JMenuItem("Show Detective Notes");
		detectiveNotesMenuItem.addActionListener(this);
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(this);
		JMenu menu = new JMenu("File");
		menu.add(detectiveNotesMenuItem);
		menu.add(exitMenuItem);
		JMenuBar menubar = new JMenuBar();
		menubar.add(menu);

		// Setup the JFrame
		setLayout(new BorderLayout());
		setSize(700, 660);
		setJMenuBar(menubar);
		setVisible(true);
		setResizable(false);
		setTitle("Clue!");
		setLocation(100, 100);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// Get Human Player Info
		Player[] players = Board.getInstance().getAllPlayers();
		String name = "";
		List<Card> cards = null;
		for (Player p : players) {
			if (p instanceof HumanPlayer) {
				name = p.getName();
				cards = p.getCards();
			}
		}
		
		// Setup JFrame Components
		MyCards c = new MyCards(cards);
		c.setPreferredSize(new Dimension(190, 0));	
		
		// Add panels to JFrame
		add(Board.getInstance(), BorderLayout.CENTER);
		add(cp, BorderLayout.SOUTH);
		add(c, BorderLayout.EAST);

		// Add friendly welcome message
		JOptionPane.showMessageDialog(this, "You are " + name + ", press Next Player to begin play.", "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void main(String[] args) {
		(new ClueGame()).init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == detectiveNotesMenuItem) {
			if (dn == null)
				dn = new DetectiveNotes(ClueGame.this);
			dn.setVisible(true);
		} else if (e.getSource() == exitMenuItem) {
			dispose();
		}
	}
}
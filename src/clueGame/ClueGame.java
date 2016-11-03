package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class ClueGame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 655655196393659806L;
	private Board board;
	private JMenuItem detectiveNotesMenuItem;

	public static void main(String[] args) {
		try {
			(new ClueGame()).init();
		} catch (FileNotFoundException | BadConfigFormatException e){
			e.printStackTrace();
		}
	}
	
	public void init() throws FileNotFoundException, BadConfigFormatException {
		board = Board.getInstance();
		board.setConfigFiles("Clue.csv", "legend.txt");
		board.initialize();
		board.loadPlayerConfigurationFile("Players.txt");
		board.loadWeaponConfigurationFile("Weapons.txt");
		board.generateDeck();
		
		detectiveNotesMenuItem = new JMenuItem("Show Detective Notes");
		detectiveNotesMenuItem.addActionListener(this);
		
		JMenu menu = new JMenu("File");
		menu.add(detectiveNotesMenuItem);

		JMenuBar menubar = new JMenuBar();
		menubar.add(menu);
		
		JFrame frame = new JFrame();
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(board);
		frame.setVisible(true);
		frame.setJMenuBar(menubar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == detectiveNotesMenuItem) {
			DetectiveNotes dn = new DetectiveNotes();
			dn.setVisible(true);
		}
	}
}
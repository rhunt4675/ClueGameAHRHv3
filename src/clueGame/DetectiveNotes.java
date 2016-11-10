package clueGame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class DetectiveNotes extends JDialog {
	private static final long serialVersionUID = 1L;
	public DetectiveNotes() {
		setModal(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		Player[] players = Board.getInstance().getAllPlayers();
		String[] rooms = Board.getInstance().getAllRooms();
		String[] weapons = Board.getInstance().getAllWeapons();
		
		// People Options Panel
		JPanel peoplePanel = new JPanel();
		peoplePanel.setBorder(BorderFactory.createTitledBorder("People"));
		for (Player p : players)
			peoplePanel.add(new JCheckBox(p.getName()));
		
		// Room Options Panel
		JPanel roomPanel = new JPanel();
		roomPanel.setBorder(BorderFactory.createTitledBorder("Rooms"));
		for (String s : rooms)
			roomPanel.add(new JCheckBox(s));
		
		// Weapons Options Panel
		JPanel weaponsPanel = new JPanel();
		weaponsPanel.setBorder(BorderFactory.createTitledBorder("Weapons"));
		for (String s : weapons)
			weaponsPanel.add(new JCheckBox(s));
		
		// People Guess Panel
		JPanel peopleGuess = new JPanel();
		peopleGuess.setBorder(BorderFactory.createTitledBorder("Person Guess"));
		peopleGuess.add(new JComboBox<Player>(players));

		// Rooms Guess Panel
		JPanel roomGuess = new JPanel();
		roomGuess.setBorder(BorderFactory.createTitledBorder("Room Guess"));
		roomGuess.add(new JComboBox<String>(rooms));
		
		// Weapons Guess Panel
		JPanel weaponGuess = new JPanel();
		weaponGuess.setBorder(BorderFactory.createTitledBorder("Weapon Guess"));
		weaponGuess.add(new JComboBox<String>(weapons));
		
		this.setSize(700, 400);
		setLayout(new GridLayout(3, 2));
		add(peoplePanel);
		add(peopleGuess);
		add(roomPanel);
		add(roomGuess);
		add(weaponsPanel);
		add(weaponGuess);
	}
}

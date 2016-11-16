package clueGame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class GuessDialog extends JDialog {
	private static final long serialVersionUID = -7596708419109790072L;
	public boolean suggestion;
	public JButton submit, cancel;
	public JComboBox<String> rooms;
	public JComboBox<String> weapons;
	public JComboBox<Player> players;
	
	public GuessDialog(String rooomIfSuggestionOnly) {
		// Suggestion vs. Accusation
		suggestion = (rooomIfSuggestionOnly != null);
		
		// Arrays of Options
		String[] allRooms = Board.getInstance().getAllRooms();
		String[] allWeapons = Board.getInstance().getAllWeapons();
		Player[] allPlayers = Board.getInstance().getAllPlayers();
		
		// Buttons
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		submit.addActionListener(Board.getInstance());
		cancel.addActionListener(Board.getInstance());
		
		// Combo Boxes
		rooms = new JComboBox<String>(allRooms);
		weapons = new JComboBox<String>(allWeapons);
		players = new JComboBox<Player>(allPlayers);
		
		// Suggestion vs. Accusation
		if (suggestion) {
			rooms.setSelectedItem((Object) rooomIfSuggestionOnly);
			rooms.setEnabled(false);
		}
		
		// Composition
		setTitle("Make a Guess!");
		setSize(350, 200);
		setLayout(new GridLayout(4, 2));
		add(new JLabel("Room: "));
		add(rooms);
		add(new JLabel("Weapon: "));
		add(weapons);
		add(new JLabel("Player: "));
		add(players);
		add(submit);
		add(cancel);
	}
}

package clueGame;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MyCards extends JPanel{
	private JPanel suspect, room, weapon;
	Board board = Board.getInstance();
	List<Card> cards = null;
	public MyCards(List<Card> cards) {
		this.cards = cards;
		Person();
		add(suspect);
		Rooms();
		add(room);
		Weapons();
		add(weapon);
		this.setBorder(BorderFactory.createTitledBorder("My Cards"));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	private void Person() {
		suspect = new JPanel();
		JTextArea person = new JTextArea();
		suspect.setLayout(new BorderLayout());
		for (Card c : cards) {
			if (c.getType() == CardType.PERSON) {
				person.setText(person.getText()+ "\n" + c.getCardName());
			}
		}
		suspect.add(person,BorderLayout.CENTER);
		suspect.setBorder(BorderFactory.createTitledBorder("People"));
	}
	
	private void Rooms() {
		room = new JPanel();
		JTextArea space = new JTextArea();
		room.setLayout(new BorderLayout());
		for (Card c : cards) {
			if (c.getType() == CardType.ROOM) {
				space.setText(space.getText()+ "\n" + c.getCardName());
			}
		}
		room.add(space, BorderLayout.CENTER);
		room.setBorder(BorderFactory.createTitledBorder("Rooms"));
	}
	private void Weapons() {
		weapon = new JPanel();
		JTextArea w = new JTextArea();
		weapon.setLayout(new BorderLayout());
		for (Card c : cards) {
			if (c.getType() == CardType.WEAPON) {
				w.setText(w.getText()+ "\n" + c.getCardName());
			}
		}
		weapon.add(w,BorderLayout.CENTER);
		weapon.setBorder(BorderFactory.createTitledBorder("Weapons"));
	}
	
	
	
}

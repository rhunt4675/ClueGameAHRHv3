package clueGame;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MyCards extends JPanel{
	private static final long serialVersionUID = 1104472266993732463L;
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
		person.setEditable(false);
		suspect.setLayout(new BorderLayout());
		for (Card c : cards) {
			if (c.getType() == CardType.PERSON) {
				person.setText(person.getText() + c.getCardName() + "\n");
			}
		}
		suspect.add(person,BorderLayout.CENTER);
		suspect.setBorder(BorderFactory.createTitledBorder("People"));
	}
	
	private void Rooms() {
		room = new JPanel();
		JTextArea space = new JTextArea();
		space.setEditable(false);
		room.setLayout(new BorderLayout());
		for (Card c : cards) {
			if (c.getType() == CardType.ROOM) {
				space.setText(space.getText() + c.getCardName() + "\n");
			}
		}
		room.add(space, BorderLayout.CENTER);
		room.setBorder(BorderFactory.createTitledBorder("Rooms"));
	}
	private void Weapons() {
		weapon = new JPanel();
		JTextArea w = new JTextArea();
		w.setEditable(false);
		weapon.setLayout(new BorderLayout());
		for (Card c : cards) {
			if (c.getType() == CardType.WEAPON) {
				w.setText(w.getText() + c.getCardName() + "\n");
			}
		}
		weapon.add(w,BorderLayout.CENTER);
		weapon.setBorder(BorderFactory.createTitledBorder("Weapons"));
	}
	
	
	
}

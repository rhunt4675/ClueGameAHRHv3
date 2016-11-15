package clueGame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlPanel extends JPanel {
	private static final long serialVersionUID = -8078217447930365244L;
	public final JButton nextPlayerButton = new JButton("Next Player");
	public final JButton makeAccusationButton = new JButton("Make Accusation");
	public JTextField whosTurnText = new JTextField(12);
	public JTextField dieText = new JTextField(5);
	public JTextField guessText = new JTextField(15);
	public JTextField resultText = new JTextField(8);
	
	public ControlPanel() {
		// Action Listeners
		nextPlayerButton.addActionListener(Board.getInstance());
		makeAccusationButton.addActionListener(Board.getInstance());
		
		// Not allowed to edit JTextFields
		whosTurnText.setEditable(false);
		dieText.setEditable(false);
		guessText.setEditable(false);
		resultText.setEditable(false);
		
		// Who's Turn? Box
		JPanel whosTurn = new JPanel();
		whosTurn.add(new JLabel("Whose turn?"));
		whosTurn.add(whosTurnText);
		
		// Die Display Box
		JPanel die = new JPanel();
		die.setBorder(BorderFactory.createTitledBorder("Die"));
		die.add(new JLabel("Roll"));
		die.add(dieText);
		
		// Guess Box
		JPanel guess = new JPanel();
		guess.setBorder(BorderFactory.createTitledBorder("Guess"));
		guess.add(new JLabel("Guess"));
		guess.add(guessText);	
		
		// Result Box
		JPanel result = new JPanel();	
		result.setBorder(BorderFactory.createTitledBorder("Guess Result"));	
		result.add(new JLabel("Response"));
		result.add(resultText);
		
		// Top Row
		JPanel topRow = new JPanel();
		topRow.setLayout(new GridLayout(1, 3));
		topRow.add(whosTurn);
		topRow.add(nextPlayerButton);
		topRow.add(makeAccusationButton);
		
		// Bottom Row
		JPanel bottomRow = new JPanel();
		bottomRow.setLayout(new GridLayout(1,3));
		bottomRow.add(die);
		bottomRow.add(guess);
		bottomRow.add(result);
		
		this.setLayout(new GridLayout(2,1));
		this.add(topRow);
		this.add(bottomRow);	
	}
	
	public void setPlayerText(String name) {
		whosTurnText.setText(name);
	}
	
	public void setDieRollText(String roll) {
		dieText.setText(roll);
	}
	
	public void setGuessTest(String guess) {
		guessText.setText(guess);
	}
	
	public void setResultText(String result) {
		resultText.setText(result);
	}
}

package clueGame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlPanel extends JPanel {
	private static final long serialVersionUID = -8078217447930365244L;

	public ControlPanel() {
		// Whose Turn TextField
		JTextField whosTurnText = new JTextField(12);
		whosTurnText.setEditable(false);
		
		// Die Text TextField
		JTextField dieText = new JTextField(5);
		dieText.setEditable(false);

		// Guess Text TextField
		JTextField guessText = new JTextField(15);
		guessText.setEditable(false);
		
		// Result Text TextField		
		JTextField resultText = new JTextField(8);
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
		topRow.add(new JButton("Next player"));
		topRow.add(new JButton("Make an accusation"));
		
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
}

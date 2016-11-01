package clueGame;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlPanel extends JPanel {

	public ControlPanel() {
		// Create JPanels
		JPanel topRow = new JPanel();
		JPanel bottomRow = new JPanel();
		JPanel whosTurn = new JPanel();
		JPanel die = new JPanel();
		JPanel guess = new JPanel();
		JPanel result = new JPanel();
		
		//Fill in whostTurn JPanels
		JTextField whosTurnText = new JTextField(12);
		whosTurnText.setEditable(false);
		
		JLabel whoseTurnLabel = new JLabel("Whose turn?");
		
		whosTurn.add(whoseTurnLabel);
		whosTurn.add(whosTurnText);
		
		// Fill in die JPanel
		die.setBorder(BorderFactory.createTitledBorder("Die"));
		
		JLabel dieRollLabel = new JLabel("Roll");
		
		JTextField dieText = new JTextField(5);
		dieText.setEditable(false);
		
		die.add(dieRollLabel);
		die.add(dieText);
		
		// Fill in guess JPanel
		guess.setBorder(BorderFactory.createTitledBorder("Guess"));
		
		JLabel guessLabel = new JLabel("Guess");
		
		JTextField guessText = new JTextField(15);
		guessText.setEditable(false);
		
		guess.add(guessLabel);
		guess.add(guessText);
		
		// Fill in the result JPanel
		result.setBorder(BorderFactory.createTitledBorder("Guess Result"));
		
		JLabel resultLabel = new JLabel("Response");
		
		JTextField resultText = new JTextField(8);
		resultText.setEditable(false);
		
		result.add(resultLabel);
		result.add(resultText);
		
		//Fill in top row
		topRow.setLayout(new GridLayout(1, 3));
		topRow.add(whosTurn);
		topRow.add(new JButton("Next player"));
		topRow.add(new JButton("Make an accusation"));
		
		//Fill in the bottom row
		bottomRow.setLayout(new GridLayout(1,3));
		bottomRow.add(die);
		bottomRow.add(guess);
		bottomRow.add(result);
		
		
		
		this.setLayout(new GridLayout(2,1));
		this.add(topRow);
		this.add(bottomRow);
		
	}

}

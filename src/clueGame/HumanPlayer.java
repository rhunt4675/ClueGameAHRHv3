package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}

	@Override
	public boolean makeMove(Set<BoardCell> targets) {
		return true;
	}

}

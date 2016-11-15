package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private boolean room;
	private DoorDirection direction;
	public static final int cellHeight = 20;
	public static final int cellWidth = 20;
	private boolean drawStr = false;
	
	public BoardCell(int row, int column, boolean room) {
		this.row = row;
		this.column = column;
		this.room = room;
		this.direction = DoorDirection.NONE;
	}

	// Check if space is a walkway 
	public boolean isWalkway() {
		return !room;
	}

	// Check if space is a room
	public boolean isRoom() {
		return room;	
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	// Check if space is a doorway
	public boolean isDoorway() {
		switch (direction){
		case LEFT:
		case RIGHT:
		case UP:
		case DOWN:
			return true;
		default:
			return false;
		}
	}

	public DoorDirection getDoorDirection() {
		return direction;
	}

	public void setDoorDirection(char door) {
		switch (door){
		case 'L':
			direction = DoorDirection.LEFT;
			break;
		case 'R':
			direction = DoorDirection.RIGHT;
			break;
		case 'D':
			direction = DoorDirection.DOWN;
			break;
		case 'U':
			direction = DoorDirection.UP;
			break;
		default:
			direction = DoorDirection.NONE;
		}
	}

	public char getInitial() {
		return initial;
	}

	public void setInitial(char initial){
		this.initial = initial;
	}
	
	public void setName() {
		drawStr = true;
	}
	
	public void draw(Graphics g) {
		int doorHeight = (int) Math.max(1, cellWidth / 5.0);
		int doorWidth = (int) Math.max(1, cellHeight / 5.0);
		
		if (isRoom()) g.setColor(Color.GRAY);
		else g.setColor(Color.yellow);
		g.fillRect(column*cellWidth, row*cellHeight, cellHeight, cellWidth);
		
		if (!isRoom()) {
		g.setColor(Color.BLACK);
		g.drawRect(column*cellWidth, row*cellHeight, cellHeight, cellWidth);
		}
		
		if (isDoorway()) { 
			g.setColor(Color.BLUE);
			switch (direction) {
			case UP:
				g.fillRect(column*cellWidth, row*cellHeight, cellWidth , doorHeight);
				break;
			case DOWN:
				g.fillRect(column*cellWidth, (row+1)*cellHeight-doorHeight, cellWidth, doorHeight);
				break;
			case LEFT:
				g.fillRect(column*cellWidth, row*cellHeight, doorWidth , cellHeight);
				break;
			case RIGHT:
				g.fillRect((column+1)*cellWidth-doorWidth, row*cellHeight, doorWidth , cellHeight);
				break;
			default:
				break;
			}
		}
		
		if (drawStr) {
			g.setColor(Color.BLUE);
			g.drawString(Board.getInstance().getRoomName(initial), column*cellWidth, row*cellHeight);
		}
	}
	
	public void drawPlayer(Player player, Graphics g) {
		// Check how many players share a given square
		int total = 0, index = 0;
		Player[] players = Board.getInstance().getAllPlayers();
		for (Player p : players) {
			if (p.row == row && p.column == column) {
				if (p == player) index = total;
				total++;
			}
		}
		
		g.setColor(player.getColor());
		g.fillArc(column*cellWidth, row*cellHeight, cellWidth, cellHeight, (int) (index * (360.0 / total)), (int) (360.0 / total));
	}

	public void drawTarget(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect(column*cellWidth + 1, row*cellHeight + 1, cellWidth - 2, cellHeight - 2);
	}
}

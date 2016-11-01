package clueGame;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private boolean room;
	private DoorDirection direction;

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
}

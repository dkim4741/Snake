package snake;

/**
 * This is the interface that will be used for the Composite Design Pattern. There are methods here that will
 * be used for the game model.
 */

public interface GameItems {
	Cell[][] getCellGrid();
	int getCurrentScore();
	boolean getSnakeIsDead();
	void addNewSnakePart(int x, int y);
	int getHighScore();
	void setHighScore(int highScore);
	
}
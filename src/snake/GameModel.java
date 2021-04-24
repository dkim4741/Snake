package snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * This model class will set the coordinates for the game grid, the snake, and the apple.
 * There will also be methods to update snake coordinates, apple position, the scores and arrow keys.
 */
class GameModel implements GameItems{
    
    static final int TOTAL_GAME_AREA = 20;
    
    private Cell[][] cellgrid = new Cell[TOTAL_GAME_AREA][TOTAL_GAME_AREA];
    private int currentScore = 0;
    private int highScore = 0;
    //false means game is paused
    private boolean isPlaying = false;
    private int timeinteval = 1500;
    private int snakeParts;
    private ArrayList<Point> SnakeCoordinates;
    private Point applePosition;
    private Arrow currentArrow = Arrow.RIGHT;
    private Arrow previousArrow = Arrow.LEFT;
    //boolean to see if user wants to reset game
    private boolean reset = false;
    private boolean snakeIsDead = false;
    
    /**
	 * GameModel that creates a grid for the game starting. 
	 * Apple created and snake parts created
	 */
    public GameModel(){
        for (Cell[] cellgrid1 : cellgrid) {
            for (int j = 0; j < cellgrid1.length; j++) {
                cellgrid1[j] = new Cell();
            }
        }
        
        changeApplePosition();
        
        SnakeCoordinates = new ArrayList<>();
        
        updateSnakeParts();
    }
    
    /**
	 * Apple position changes randomly in grid every time apple eaten
	 */
    public void changeApplePosition(){
        int x, y;
        x = new Random().nextInt(TOTAL_GAME_AREA-1);
        y = new Random().nextInt(TOTAL_GAME_AREA-1);
        
        if(snakeParts>0){
        while (SnakeCoordinates.contains(new Point(x, y))) {            
            x = new Random().nextInt(TOTAL_GAME_AREA-1);
            y = new Random().nextInt(TOTAL_GAME_AREA-1);
        }}
        
        this.applePosition = new Point(x, y);
    }
    
    /**
	 * Apple removed from grid. Used when game ends.
	 */    public void removeApple(){
        
        this.applePosition = new Point(-1, -1);
    }
    
	 /**
		 * Updates snake's body 
		 */
    public void updateSnakeParts(){
        this.snakeParts = SnakeCoordinates.size();
    }
    
    /**
	 * Snake gets a new body part. 
	 * @param int x for the horizontal position
	 * @param int y for the vertical position
	 */
    public void addNewSnakePart(int x, int y){
        this.SnakeCoordinates.add(new Point(x, y));
    }
    
    /**
	 * Snake coordinates change with position and coordinate values.
	 * @param position for snake position
	 * @param newX for x position when snake moves 
	 * @param newY for y position when snake moves 
	 */
    public void altersnakecoordinates(int position, int newX, int newY){
        this.SnakeCoordinates.remove(position);
        this.SnakeCoordinates.add(position, new Point(newX, newY));
    }
    
    /**
	 * Gets the Cell grid
	 */
    public Cell[][] getCellGrid(){
        return cellgrid;
    }
    
    /**
	 * Gets the type of component in cell.
	 * @param int x for the horizontal position of cell type is
	 * @param int y for the vertical position of cell type is
	 */
    public int getCellType(int x, int y){
        return cellgrid[x][y].getCellType();
    }
    
    /**
	 * Sets a new cell type in grid.
	 * @param int x for horizontal position to set cell component 
	 * @param int y for vertical position to set cell component
	 * @param int type for type to set cell component (0 = none, 1 = snake, 2 = apple)
	 */
    void setCellType(int x, int y, int type){
        cellgrid[x][y].setCellType(y);
    }
    
    public void setReset(boolean reset){this.reset = reset;}
    
    public void setScore(int score){ this.currentScore = score;}
    public int getCurrentScore() {return currentScore;}
    
 
    
    public void setArrowKey(String key){
        //other part of the if check to make sure snake only moves forward and not backward
        
        if (key.toLowerCase().equals("up") && !previousArrow.equals(Arrow.DOWN)) {
            this.currentArrow = Arrow.UP;
        }
        else if (key.toLowerCase().equals("down") && !previousArrow.equals(Arrow.UP)) {
            this.currentArrow = Arrow.DOWN;
        }
        else if (key.toLowerCase().equals("right") && !previousArrow.equals(Arrow.LEFT)){
            this.currentArrow = Arrow.RIGHT;
        }
        else if (key.toLowerCase().equals("left") && !previousArrow.equals(Arrow.RIGHT)){
            this.currentArrow = Arrow.LEFT;
        }  
    }
    public String getArrowKey(){
        return this.currentArrow.toString();
    }
    
    public int incrementScore(){
        currentScore +=1;
        return currentScore;
    }
    
    public int incrementhighScore(){
        highScore +=1;
        return highScore;
    }
    
    public int getGridHeight(){return cellgrid.length;}
    public int getGridWidth(){return cellgrid[0].length;}
    
    public void setPlayingMode(boolean isPlaying) {this.isPlaying = isPlaying;} 
    public boolean getPlayingMode() {return isPlaying;}
    
    public int getTimeInterval(){return timeinteval;}
    public void setTimeInterval(int time){this.timeinteval = time;}
    
    public void snakeDies(){

    	this.snakeIsDead = true;
        this.snakeParts = 0;
        SnakeCoordinates.removeAll(SnakeCoordinates);
        this.previousArrow = Arrow.LEFT;
        this.currentArrow = Arrow.RIGHT;
    }
    
    public boolean getSnakeIsDead() {
    	return this.snakeIsDead;
    }
    
    public void ResetGame(){
        snakeDies();
        setScore(0);
        removeApple();
        this.previousArrow = Arrow.LEFT;
        this.currentArrow = Arrow.RIGHT;
        this.snakeIsDead = false;
    }
    
    public void NextStep(){
        Cell nextVersion[][] = new Cell[TOTAL_GAME_AREA][TOTAL_GAME_AREA];
        
        if(reset){
            ResetGame();
        }
        
        else{
        if (snakeParts == 0) {
            addNewSnakePart(0, 2);
            updateSnakeParts();
        }
        else if (snakeParts < 4) {
            addNewSnakePart(0, 2);
            updateSnakeParts();
            updateSnakePosition();
            
        }
        else{updateSnakePosition();}
        
        //if snake eats apple
        if(SnakeCoordinates.get(0).x == applePosition.x && SnakeCoordinates.get(0).y == applePosition.y)
        { 
            eat();
        }
   
        //if snake touches the boundary
        if(SnakeCoordinates.get(0).x < 0 || SnakeCoordinates.get(0).x > TOTAL_GAME_AREA-1 ||SnakeCoordinates.get(0).y <0 ||SnakeCoordinates.get(0).y > TOTAL_GAME_AREA-1)
        {
            snakeDies();
            isPlaying = false;
        }
        
        //if snake eats itself
        for (int i = 1; i < SnakeCoordinates.size()-1; i++) {
                if (SnakeCoordinates.get(0).equals(SnakeCoordinates.get(i))) {
                    snakeDies();
                    isPlaying = false;
                    break;
                }
            }
                }
        
        for (int y = 0; y <cellgrid.length; y++) {
            for (int x = 0; x < cellgrid[y].length; x++) {
                if (SnakeCoordinates.contains(new Point(x, y))) {
                    nextVersion[y][x] = new Cell(1);
                }
                else if (applePosition.equals(new Point(x, y))) {
                    nextVersion[y][x] = new Cell(2);
                }
                else {nextVersion[y][x] = new Cell(0);}
            }
        }
        
        cellgrid = nextVersion;
    }
    
    public void updateSnakePosition(){
        
        for (int i = snakeParts-1; i > 0; i--) {
            altersnakecoordinates(i, SnakeCoordinates.get(i-1).x, SnakeCoordinates.get(i-1).y);
        }
        switch (currentArrow) {
            case DOWN:
                altersnakecoordinates(0, SnakeCoordinates.get(0).x, (SnakeCoordinates.get(0).y)+1);
                break;
            case UP:
                altersnakecoordinates(0, SnakeCoordinates.get(0).x, (SnakeCoordinates.get(0).y)-1);
                break;
            case RIGHT:
                altersnakecoordinates(0, (SnakeCoordinates.get(0).x)+1, (SnakeCoordinates.get(0).y));
                break;
            case LEFT:
                altersnakecoordinates(0, (SnakeCoordinates.get(0).x)-1, (SnakeCoordinates.get(0).y));
                break;
            default:
                break;
        }
       
        previousArrow = currentArrow;

        
    }
    
    //Update snake part, apple position, and score
    public void eat(){
        addNewSnakePart(SnakeCoordinates.get(snakeParts-1).x, SnakeCoordinates.get(snakeParts-1).y);
        changeApplePosition();
        incrementScore();
        updateSnakeParts();
    }

	public int getHighScore() {
		return highScore;
	}


	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
    
    
}
//Arrow controls
enum Arrow {
    UP, DOWN, RIGHT, LEFT
}

package snake;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

class GameModel {
    
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
    
    
    //changes the Position of the apple by randomly assigning cordinates
    public void changeApplePosition(){
        int x, y;
        x = new Random().nextInt(TOTAL_GAME_AREA-1);
        y = new Random().nextInt(TOTAL_GAME_AREA-1);
        
        //to prevent overlapping of snake's coordinates and the apple's position
        if(snakeParts>0){
        while (SnakeCoordinates.contains(new Point(x, y))) {            
            x = new Random().nextInt(TOTAL_GAME_AREA-1);
            y = new Random().nextInt(TOTAL_GAME_AREA-1);
        }}
        
        this.applePosition = new Point(x, y);
    }
    
    //remove Apple from the grid
    public void removeApple(){
        
        this.applePosition = new Point(-1, -1);
    }
    
    public void updateSnakeParts(){
        this.snakeParts = SnakeCoordinates.size();
    }

    //add new snakePart, x and y are the cordinates of the new part
    public void addNewSnakePart(int x, int y){
        this.SnakeCoordinates.add(new Point(x, y));
    }
    
    //alter the coordinates of the snake parts at the necessary index
    public void altersnakecoordinates(int position, int newX, int newY){
        this.SnakeCoordinates.remove(position);
        this.SnakeCoordinates.add(position, new Point(newX, newY));
    }
    
    public Cell[][] getCellGrid(){
        return cellgrid;
    }
    
    public int getCellType(int x, int y){
        return cellgrid[x][y].getCellType();
    }
    
    void setCellType(int x, int y, int type){
        cellgrid[x][y].setCellType(y);
    }
    
    public void setReset(boolean reset){this.reset = reset;}
    
    public void setScore(int score){ this.currentScore = score;}
    public int getCurrentScore() {return currentScore;}
    
    public int getHighScore() {
		return highScore;
	}


	public void setHighScore(int highScore) {
	
			this.highScore = highScore;

	}
 
    
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
    //gets the user input
    public String getArrowKey(){
        return this.currentArrow.toString();
    }
    
    public int incrementScore(){
        currentScore +=1;
        return currentScore;
    }
    
    public int incrementHighScore() {
    	highScore++;
    	return highScore;
    }
    
    
    public int getGridHeight(){return cellgrid.length;}
    public int getGridWidth(){return cellgrid[0].length;}
    
    public void setPlayingMode(boolean isPlaying) {this.isPlaying = isPlaying;} 
    public boolean getPlayingMode() {return isPlaying;}
    
    public int getTimeInterval(){return timeinteval;}
    public void setTimeInterval(int time){this.timeinteval = time;}
    
    public void snakeDies(){
    //snakeIsDead boolean set to true when snake dies and snakecoordinates are reset to intial
        //decrementLive();
    	this.snakeIsDead = true;
        this.snakeParts = 0;
        SnakeCoordinates.removeAll(SnakeCoordinates);
        this.previousArrow = Arrow.LEFT;
        this.currentArrow = Arrow.RIGHT;
    }
    
    public boolean getSnakeIsDead() {
    	return this.snakeIsDead;
    }
    
    //reseting all the variables when Reset pressed
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
        
        if(currentScore > highScore) {
        	incrementHighScore();
        }
    }


	
    
    
}
//Arrow controls
enum Arrow {
    UP, DOWN, RIGHT, LEFT
}

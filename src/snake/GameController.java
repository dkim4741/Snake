
package snake;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * This class will construct the visual and controlling environment for the game, setting the instructions
 * to control the snake as well as the conditions for playing and finishing the game.
 */
class GameController {
    private final GameModel snakeGameModel;
    private final GameView snakeGameView;
    private final NorthGamePanel northpanel;
    private final SouthGamePanel southpanel;
    
    private Timer gameTimer = null;
    
    /**
     * Constructor for the class, including external elements to build the interface.
     */
    public GameController(GameModel snakeGameModel, GameView snakeView, NorthGamePanel npanel, SouthGamePanel spanel) {
        this.snakeGameModel = snakeGameModel;
        this.snakeGameView = snakeView;
        this.northpanel = npanel;
        this.southpanel = spanel;
        
        updateGameViewDisplay();
        
        initialpanelViewListeners();
    }
    
    /**
     * This method will organize the playable interface and adjust the celling for the game.
     */
    private void updateGameViewDisplay() {  
        int startx = 20;
        int starty = 20;
        double CellWidth = 20;
        
        int numOfRows = snakeGameModel.getGridHeight();
        int numOfColumn = snakeGameModel.getGridWidth();
        
        Cell[][] Cells = new Cell[numOfRows][numOfColumn];
        
        for (int i = 0; i < Cells.length; i++) {
                double curretYPos = (i * CellWidth)+starty;
            for (int j = 0; j < Cells[i].length; j++) {
                double curentXPos = (j * CellWidth)+startx;
                
                int currentCellType = snakeGameModel.getCellType(i, j);
                
                Cells[i][j] = new Cell(currentCellType);
                Cells[i][j].setCircle(curentXPos, curretYPos, CellWidth, CellWidth);
            }
        }
        snakeGameView.setCells(Cells);
        snakeGameView.repaint();
        }
    
    /**
     * This method ensures the score and highscore is updated if necessary,
     * and shows the Game Over message when the snake is dead.
     */
    private void performOneStep() {
        snakeGameModel.NextStep();
        southpanel.setScoreLabel(snakeGameModel.getCurrentScore());
        southpanel.setHighScoreLabel(snakeGameModel.getHighScore());

       // southpanel.setLivesLabel(snakeGameModel.getCurrentLive());
        
        if (!snakeGameModel.getPlayingMode()) {
            stopTime();
            northpanel.updatePanelForPlayMode(false);
        }
        
        if(snakeGameModel.getSnakeIsDead() == true)
        {
            northpanel.updatePanelForGameOverMode();
            javax.swing.JLabel label = new javax.swing.JLabel("Game Over");
            label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, null, JOptionPane.WARNING_MESSAGE);
        }
            

    }
    
    /**
     * This method includes the instructions to control the snake all over the interface
     * of the game, as well as the controls to exit the game.
     */
    private void initialpanelViewListeners() {
        northpanel.addPlayButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snakeGameModel.setPlayingMode(!snakeGameModel.getPlayingMode());
                stopTime();
                
                if (snakeGameModel.getPlayingMode()) {
                    snakeGameModel.setArrowKey("right");
                    startGame();
                    northpanel.updatePanelForPlayMode(true);
                }
                else{northpanel.updatePanelForPlayMode(false);}            
            }
        });
        
        northpanel.addNewGameButoonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            javax.swing.JLabel label = new javax.swing.JLabel("New Game");
            label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label,"Are you sure?", JOptionPane.INFORMATION_MESSAGE);
            
                    snakeGameModel.setReset(true);
                    snakeGameModel.NextStep();
                    southpanel.setScoreLabel(snakeGameModel.getCurrentScore());
                    //southpanel.setLivesLabel(snakeGameModel.getCurrentLive());
                    updateGameViewDisplay();
                   snakeGameModel.setReset(false);
                   snakeGameModel.setPlayingMode(false);
                    snakeGameModel.changeApplePosition();
                    northpanel.updatePanelforGameStartMode();
            }
        });
        
        
        snakeGameView.addKeyArrowListener(KeyEvent.VK_UP, "up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snakeGameModel.setArrowKey("up");   
            }
        });
        
        snakeGameView.addKeyArrowListener(KeyEvent.VK_DOWN, "down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snakeGameModel.setArrowKey("down");   
            }
        });
        
        snakeGameView.addKeyArrowListener(KeyEvent.VK_RIGHT, "right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snakeGameModel.setArrowKey("right");   
            }
        });
             
        snakeGameView.addKeyArrowListener(KeyEvent.VK_LEFT, "left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snakeGameModel.setArrowKey("left");   
            }
        });
        
    }
    
    private void stopTime(){
        if(gameTimer != null){gameTimer.stop();}}
    
    private void startGame(){
        gameTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                startOneStepThread();
            }
        });
        gameTimer.start();
    }
    
    private void startOneStepThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
            synchronized(snakeGameModel){
                
                if(snakeGameModel.getSnakeIsDead() == true){
                    snakeGameModel.removeApple();
                }
                else{
                performOneStep();}
                
                updateGameViewDisplay();}   
            }
        }).start();
    }
    
}
    

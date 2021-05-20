package snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class SnakeGame {

    public static void main(String[] args) {
      EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {  
                // Creates the visual frame for the game.
                GameFrame frame = new GameFrame();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(new Dimension(450, 550));
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setTitle("Snake Game");
                frame.getContentPane().setBackground(Color.BLACK);
            }
        });
    }
    
}

class GameFrame extends JFrame{
    
    private GameModel snakeModel;
    private GameController snakeController;
    private GameView snakeGameView;
    private NorthGamePanel northGamePanel;
    private SouthGamePanel southGamePanel;
    

    public GameFrame() {
        
        // Includes every element of the game into the game frame
        
        snakeGameView = new GameView();
        add(snakeGameView, BorderLayout.CENTER);
        
        snakeModel = new GameModel();
       
        northGamePanel = new NorthGamePanel();
        add(northGamePanel, BorderLayout.NORTH);
        
        southGamePanel = new SouthGamePanel();
        add(southGamePanel, BorderLayout.SOUTH);
        
        snakeController = new GameController(snakeModel, snakeGameView, northGamePanel, southGamePanel);
        
        
        
    }
    
    
    
    
}

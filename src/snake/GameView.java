import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
/**
 * This is the view class that will set the view of the game when user began.
 * There are methods to update snake coordinates and change the color of each cell accordingly.
 */
    class GameView extends JComponent 
	{
	    
	    private Cell[][] snakeGame;
	    
	    public GameView()
	    {
	    	
	    }

	    public void addKeyArrowListener(int keyCode, String Name, Action action){
	        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0), Name);
	        getActionMap().put(Name, action);
	    }

	    @Override
	    protected void paintComponent(Graphics g)
	    {
	        Graphics2D g1 = (Graphics2D) g;
	        
	        for (int i = 0; i<snakeGame.length; i++)
	        {
	            for (int j = 0; j < snakeGame[i].length; j++) 
	            {
	                if (snakeGame[i][j].isSnake()) 
	                {
	                    g1.setColor(Color.BLUE);     
	                } 
	                else if (snakeGame[i][j].isFruit()) 
	                {
	                    g1.setColor(Color.RED);
	                }
	                else
	                {
	                    g1.setColor(Color.GREEN);
	                }
	                g1.fill(snakeGame[i][j].getCircle());
	            }
	        }
	        
	        g1.setColor(Color.RED);
    
	    }
	    
	    public void setCells(Cell[][] allCells)
	    {
	        this.snakeGame = allCells;
	    }
	    
	    public Cell[][] getCells()
	    {
	    	return snakeGame;
	    }
	            
	}
}

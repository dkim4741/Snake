
package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

class NorthGamePanel extends JPanel{

    private final JButton play;
    private final JButton newGame;

    public NorthGamePanel() {
        setBackground(Color.BLACK);
 
        
        play = new JButton("Play");
        play.setBackground(Color.WHITE);
        play.setPreferredSize(new Dimension(100, 30));
        add(play);
        
        newGame = new JButton("New Game");
        newGame.setBackground(Color.WHITE);
        newGame.setPreferredSize(new Dimension(100, 30));
        add(newGame);
        
    }
    
   
    public void addPlayButtonListener(ActionListener playAction){
        play.addActionListener(playAction);
    }
    
    public void addNewGameButoonListener(ActionListener newGameAction){
        newGame.addActionListener(newGameAction);
    }
    
    public void updatePanelForPlayMode(boolean isPlaying){
        if(isPlaying){
            newGame.setEnabled(false);
            play.setText("Pause");
        }
        else{
            newGame.setEnabled(true);
            play.setText("Play");
        }
    }
    
    public void updatePanelForGameOverMode(){
        newGame.setEnabled(true);
        play.setEnabled(false);
    }
    
    public void updatePanelforGameStartMode(){
        newGame.setEnabled(true);
        play.setEnabled(true);
    }
}

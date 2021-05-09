package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


class SouthGamePanel extends JPanel {
         
    private final JLabel scorelabel;
    private final JLabel highScoreLabel;
    //private final JLabel lives;

    public SouthGamePanel() {
        setBackground(Color.BLACK);
        setLayout(new GridLayout(0, 2));
        
        scorelabel = new JLabel();
        scorelabel.setForeground(Color.white);
        setScoreLabel(0);
        scorelabel.setFont(new Font("Monospaced", Font.PLAIN, 18));
        scorelabel.setPreferredSize(new Dimension(100, 30));
        add(scorelabel);
        
        highScoreLabel = new JLabel();
        highScoreLabel.setForeground(Color.white);
        setHighScoreLabel(0);
        highScoreLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));
        highScoreLabel.setPreferredSize(new Dimension(100, 30));
        add(highScoreLabel);
        
//        lives = new JLabel("", SwingConstants.RIGHT);
//        lives.setForeground(Color.white);
//        setLivesLabel(3);
//        lives.setFont(new Font("Monospaced", Font.PLAIN, 18));
//        lives.setPreferredSize(new Dimension(100, 30));
//        add(lives);
        
    }
    
    public void setScoreLabel(int score){
    scorelabel.setText(" Score: "+ score);
    }
    
    public void setHighScoreLabel(int highScore) {
    	highScoreLabel.setText(" High Score: "+ highScore);
    }
    
//    public void setLivesLabel(int live){
//    lives.setText("Lives: "+ live+" ");
//    }
}

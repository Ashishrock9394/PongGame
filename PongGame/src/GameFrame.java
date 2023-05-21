import java.awt.*;
import javax.swing.*;

//JFrame is the main container which contains title, max/min and close buttons
public class GameFrame extends JFrame {

//    Instance of panel
    GamePanel panel;
    GameFrame(){
        panel = new GamePanel();
        this.add(panel);  //Adding the game panel to JFrame
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack(); //Frame will automatically adjust size to accommodate to game panel
        this.setVisible(true);
        this.setLocationRelativeTo(null); //Frame will appear in the middle of the screen
    }
}

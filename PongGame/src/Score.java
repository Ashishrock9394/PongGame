import java.awt.*;

public class Score extends Rectangle{
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1; //Holds score of player1
    int player2; //Holds score of player2
    Score(int GAME_WIDTH, int GAME_HEIGHT){
        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;
    }
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.PLAIN, 30));
        g.drawLine(GAME_WIDTH / 2, 0, GAME_WIDTH / 2, GAME_HEIGHT);
        g.drawString("PlayerA: " + String.valueOf(player1), GAME_WIDTH / 4 - 110, 25);
        g.drawString("PlayerB: " + String.valueOf(player2), GAME_WIDTH / 2 + 140, 25);
    }

}

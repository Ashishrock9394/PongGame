import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class Paddle extends Rectangle{
    int id; //For paddles
    int yVelocity; //Speed of moving paddles
    int speed = 10; //pixels

    Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id){
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT); //Rectangle class properties x,y,width,height
        this.id=id; //Giving player1 and player2 ids
    }

    public void keyPressed(KeyEvent e){
        switch(id){
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_Q){
                    setYDirection(-speed);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_A){
                    setYDirection(speed);
                    move();
                }
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_P){
                    setYDirection(-speed);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_L){
                    setYDirection(speed);
                    move();
                }
                break;
        }
    }

    public void keyReleased(KeyEvent e){
        switch(id){
            case 1:
                if(e.getKeyCode()==KeyEvent.VK_Q){
                    setYDirection(0);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_A){
                    setYDirection(0);
                    move();
                }
                break;
            case 2:
                if(e.getKeyCode()==KeyEvent.VK_P){
                    setYDirection(0);
                    move();
                }
                if(e.getKeyCode()==KeyEvent.VK_L){
                    setYDirection(0);
                    move();
                }
                break;
        }
    }

    public void setYDirection(int yDirection){
        yVelocity = yDirection;
    }

    public void move(){
        y = y + yVelocity; //y is the vertical component we made our paddles with
    }

    public void draw(Graphics g){
        //set colors of paddles
        if(id==1){
            g.setColor(Color.red);
        } else {
            g.setColor(Color.green);
        }
        g.fillRect(x,y,width,height);

    }
}

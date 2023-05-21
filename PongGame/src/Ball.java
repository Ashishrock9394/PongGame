import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class Ball extends Rectangle{

    Random random;
    int xVelocity;  //Speed of ball in x
    int yVelocity;  //Speed of ball in y
    int initialSpeed=3;

    Ball(int x, int y, int width, int height){
        super(x,y,width,height);
        random = new Random();
        // X direction
        int randomXDirection = random.nextBoolean()?1:-1;
        setXDirection(randomXDirection*initialSpeed);
        //Y Direction
        int randomYDirection = random.nextBoolean()?1:-1;
        setYDirection(randomYDirection*initialSpeed);
    }

//    Ball moves in both x and y direction.
    public void setXDirection(int randomXDirection){
        xVelocity = randomXDirection;
    }

    public void setYDirection(int randomYDirection){
        yVelocity = randomYDirection;
    }

//    move method
    public void move(){
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(Graphics g){
        g.setColor(Color.blue);
        g.fillOval(x,y,height,width);
    }
}

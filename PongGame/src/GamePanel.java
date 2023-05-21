import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

//JPanel is a lightweight contains used to organize components within a frame or another panel
//Runnable is added so game can run on a separate thread, reducing crowding (Usually for time-consuming tasks)

public class GamePanel extends JPanel implements Runnable{

//    Instantiating required objects
    static final int GAME_WIDTH = 1000; // So that we cannot mistakenly change width
    static final int GAME_HEIGHT = (int) (GAME_WIDTH*((double)5/9)); // Aspect ratio 5:9
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 25;
    static final int PADDLE_WIDTH=25;
    static final int PADDLE_HEIGHT=100;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    GamePanel(){
        //We create new paddles and balls everytime the panel boots
        newPaddles();
        newBall();

        //Instantiating above properties
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true); //Current component receives the focus
        this.addKeyListener(new AL()); //AL is the inner class than records keystrokes
        this.setPreferredSize(SCREEN_SIZE); //We already have the dimensions

        gameThread = new Thread(this); //This implies class is capable of running on a different thread since it implements Runnable
        gameThread.start();

    }

//    If we ever want to reset/start the game
    public void newBall(){
        random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2) , (GAME_HEIGHT/2)-(BALL_DIAMETER/2) , BALL_DIAMETER , BALL_DIAMETER); // Ball will appear in middle of x and y axis

    }

    public void newPaddles(){
        int initialPos = (GAME_HEIGHT/2) - (PADDLE_HEIGHT/2);
        paddle1 = new Paddle(0, initialPos, PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH, initialPos, PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

//    Paint method inherited from Component class, which is a superclass of JPanel but being overriden.
    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight()); // createImage is a method form Component class
        graphics = image.getGraphics(); //By calling getGraphics() on the image object, you obtain a Graphics object that serves as a context for performing
        // drawing operations on the image. The Graphics object provides methods for drawing lines, shapes, text, images, and other graphical elements onto the associated image.
        draw(graphics); // calling draw method
        g.drawImage(image,0,0,this); //By calling g.drawImage(image, 0, 0, this);, you are instructing the Graphics object to draw the image at position (0, 0) on the
        // component that is currently being painted.
    }

    public void draw(Graphics g){
        //Drawing paddles
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    public void move(){
        //To make paddle movement smoother, we can benefit from the run method where we run panel move() with specific ticks
        paddle1.move(); //now paddle will appear to move at 60fps (60 ticks)
        paddle2.move();
        ball.move();
    }

    public void checkCollision(){
        //stops paddles at window edges
        //paddle 1
        if(paddle1.y<=0){
            paddle1.y=0;
        }
        if(paddle1.y>=(GAME_HEIGHT-PADDLE_HEIGHT)){
            paddle1.y=(GAME_HEIGHT-PADDLE_HEIGHT);
        }

        //paddle 2
        if(paddle2.y<=0){
            paddle2.y=0;
        }
        if(paddle2.y>=(GAME_HEIGHT-PADDLE_HEIGHT)){
            paddle2.y=(GAME_HEIGHT-PADDLE_HEIGHT);
        }

        //Bounce ball off top & bottom window edges
        //Y-Direction
        if(ball.y <= 0){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }

        //Bounces ball off paddles

        //paddle 1
         if(ball.intersects(paddle1)) { //Intersects is a rectangle class method
             ball.xVelocity = Math.abs(ball.xVelocity);
             ball.xVelocity++; //Optional for more difficulty
             if (ball.yVelocity > 0) {
                 ball.yVelocity++;
             } else {
                 ball.yVelocity--;
             }
             ball.setXDirection(ball.xVelocity);
             ball.setYDirection(ball.yVelocity);
         }

        //paddle 2
        if(ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);

//            NOTE : DISABLING TO ONLY INCREASE BALL SPEED WHEN IT HITS PADDLE 1, SO BOTH PLAYERS GET TO EXPERIENCE SAME CHALLENGE FOR 1 COMPLETE ROUND
//            ball.xVelocity++; //Optional for more difficulty
//            if (ball.yVelocity > 0) {
//                ball.yVelocity++;
//            } else {
//                ball.yVelocity--;
//            }
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        //Give player 1 point and creates new paddles and balls
        if(ball.x <=0){
            score.player2++;
            newPaddles();
            newBall();
        }

        if(ball.x >= GAME_WIDTH-BALL_DIAMETER){
            score.player1++;
            newPaddles();
            newBall();
        }
    }

    public void run() {
        //game loop to achieve preferable amount of ticks
        long lastTime = System.nanoTime(); //get current system time in nanoseconds
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks; // average number of nano-seconds per tick
        double delta = 0; //elapsed times between frames/ticks
        while(true){
            long now = System.nanoTime();
            delta+=(now - lastTime)/ns;
            lastTime = now;
            if(delta>=1){
                move(); //move all the components
                checkCollision(); //Checks for collisions
                repaint(); //recall paint
                delta--;
            }
        }
    }

//    Action Listener AL. keyPressed and keyReleased will help in paddle
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}

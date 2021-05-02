package Main;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;

import Objects.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel {

    /**
	 *
	 */
	@Serial
    private static final long serialVersionUID = 1L;
    String DisplayText = "Hi. Click NEW GAME or load a SAVED GAME.";
    private ArrayList<Ball> balls;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<Missile> missiles;
    private Player paddle;
    private boolean paused = false;
    private Brick[] bricks;
    boolean inGame = false;
    private final Random rand = new Random();
    private JWindow menuWindow;
    private instance CurrentInstance;
    instance SavedInstance;
    private int InGameTextCount = 1000; //high enough so it doesn't print the victory message at the start
    PowerUpFactory powerUpFactory =  new PowerUpFactory();
    RandomLevel level = new RandomLevel();
    private int powerCount =0;
    private Image img;
    
    public Board(String img) {
		initBoard(new ImageIcon(img).getImage());
    }

    private void initBoard(Image img)  
    {
    	this.img = img;
    	addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));
        setBackground(Color.WHITE);
        gameInit();

    }

    private void gameInit() {
        paddle = Player.getPaddleInstance();

        SavedInstance = new instance();
        CurrentInstance = new instance();

        makeNewInstance();
        Menu menu = Menu.getMenu();
        menuWindow = menu.makingTheMenu(this);
        stopGame("Welcome!. Choose NEW GAME or load a SAVED GAME");
        Timer timer = new Timer(Commons.PERIOD, new GameCycle());
        timer.start();
    }
    //Don't use call this function directly. use makeNewInstance() or makeNextLevel(). Both use this function in their body
    private void makeGameInstance() {
        CurrentInstance = new instance();

        CurrentInstance.setisEmpty(false);
        balls = CurrentInstance.getBalls();
        CurrentInstance.bricks = level.getBricks();

        bricks = CurrentInstance.getbricks();
        powerUps = CurrentInstance.getPowerups();
        missiles = paddle.getMissiles();

        balls.add(new Ball());
    
        level.Generate();
        System.out.println("Current Life: " +paddle.getLife());

        paddle.setX(CurrentInstance.getPlayerX());
        paddle.setY(CurrentInstance.getPlayerY());
     }
    //Generates the same with no previous data
    public void makeNewInstance() {
        makeGameInstance();
        paddle.initState();   
    }
    //Makes the next Level
    public void makeNextLevel() {
        makeGameInstance();
        paddle.setBallStuckToPaddle(true);
        paddle.setPowerUp("Default");
        powerUps.clear();
    }
    //Transfers information from the saved instance variable to the current instance variable
    void getsavedInstance() throws CloneNotSupportedException {
        CurrentInstance = new instance();

        CurrentInstance.setBallsCloneOf(SavedInstance.getBalls());
        CurrentInstance.setBricksCloneOf(SavedInstance.getbricks());
        CurrentInstance.setPowerUpCloneOf(SavedInstance.getPowerups());

        balls = CurrentInstance.getBalls();
        bricks = CurrentInstance.getbricks();
        powerUps = CurrentInstance.getPowerups();
        SavedInstance.getPlayerinfo(paddle);
    }
    //Copies the information from the current instance variable to the saved instance variable
    void saveTheGame() throws CloneNotSupportedException{
        SavedInstance = new instance();
        SavedInstance.setisEmpty(false);
        SavedInstance.setBallsCloneOf(CurrentInstance.getBalls());
        SavedInstance.setBricksCloneOf(CurrentInstance.getbricks());
        SavedInstance.setPowerUpCloneOf(CurrentInstance.getPowerups());
        SavedInstance.savePlayerInfo(paddle);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        var g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        if (inGame) {
            drawObjects(g2d);

            drawStringTopRight(g2d, "Life: " +paddle.getLife(), 56);
            drawStringTopRight(g2d, "Level: " +paddle.getLevel(), 18);
            drawStringTopRight(g2d, "Score: " +paddle.getScore(), 36);
        }
        if(paused) {
            DrawSavedMessage(g2d);
        }
        if (InGameTextCount <100){
            InGameTextCount++;
            DrawSavedMessage(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    //A general message function that will be used to continouesly draw and update the life, score etc on the top right
   private void drawStringTopRight(Graphics2D g2d, String message, int height) {
        var font = new Font("Verdana", Font.BOLD, 15);
        FontMetrics fontMetrics = this.getFontMetrics(font);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString(message, Commons.WIDTH - fontMetrics.stringWidth(message) - 2, height);
    }

    private void drawObjects(Graphics2D g2d) {
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),paddle.getImageWidth(), paddle.getImageHeight(), this);

        for (Ball ball : balls) {
            g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getImageWidth(), ball.getImageHeight(), this);
        }
        for (PowerUp powerUp : powerUps) {
            g2d.drawImage(powerUp.getImage(), powerUp.getX(), powerUp.getY(), powerUp.getImageWidth(), powerUp.getImageHeight(), this);
        }
        for (Missile missile: missiles) {
            g2d.drawImage(missile.getImage(), missile.getX(),missile.getY(), missile.getImageWidth(), missile.getImageHeight(), this);
        }
        for (Brick brick: bricks) {
            if (!brick.isDestroyed()) {
                g2d.drawImage(brick.getImage(), brick.getX(), brick.getY(), brick.getImageWidth(), brick.getImageHeight(), this);
            }
        }
    }
    //Draws the messages that will be displayed due to user action events by menu button, or when the user progresses to the next level
    private void DrawSavedMessage(Graphics2D g2d) {

        var font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);
        g2d.setColor(Color.WHITE);
        g2d.setFont(font);
        g2d.drawString(DisplayText,(Commons.WIDTH - fontMetrics.stringWidth(DisplayText)) / 2, 20);
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!paused) {
                doGameCycle();
            }
            repaint();
        }
    }
    public void moveGameObjects(){
        paddle.loadImage();
        paddle.move();

        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) {
                brick.move();
            }
        }
        for (Ball ball : balls) {
            ball.move();
        }
        for (PowerUp powerup : powerUps) {
            powerup.move();
        }
        for(Missile missile: missiles) {
       	 	missile.move();
        }
    }
    private void doGameCycle() {
        moveGameObjects();
        checkCollision();
        checkOnlyUnbreakableBricksLeft();
        cleanUp();
    }
    //Clean Up all the game object that are not destroyed and don't need to be called again.
    private void cleanUp() {
        balls.removeIf(Sprite::isDestroyed);
        powerUps.removeIf(Sprite::isDestroyed);
        missiles.removeIf(Sprite::isDestroyed);

        if(balls.size() < 1){
            System.out.println("Current Life: " + paddle.getLife());
            paddle.loseALife();
            Ball ball = new Ball();
            ball.ballLaunchRandom();
            balls.add(ball);

            paddle.setBallStuckToPaddle(true);

            if(paddle.getLife()<1) {
                stopGame("Game Over. Try NEW GAME or LOAD SAVE.");
            }
        }
        if(powerCount>500 && !Brick.isOnlyUnbreakableBricksLeft()) //To return powerup to normal
        {
        	for(Ball ball: balls){
                ball.ChangeToNormalBall();
        	}
        }
        if(powerCount>500) {
        	paddle.setPowerUp("default");
        }
        powerCount++;
    }
    //This callback represents the variable setting when the game is temporarily stopped
    public void stopGame(String DisplayText) {
        this.DisplayText = DisplayText;
        inGame = false;
        paused = true;
        menuWindow.setVisible(true);
    }
    //This callback represents the variable setting when the game is un paused

    public void startGame(String DisplayText) {
        this.DisplayText = DisplayText;
        inGame = true;
        paused =false;
        menuWindow.setVisible(false);
    }

  private void checkCollision() {        //Each method in the body checks a collision between any two objects as the name of the nested functions suggest.

      checkCollisionBallsDropped();
	  	checkCollisionMissileBricks();
        checkCollisionPaddleBall();
        checkCollisionBallBricks();
        checkCollisionPowerupPaddle();
        checkCollisionBrickMovement();
    }

    private void checkCollisionPowerupPaddle() {
        for (PowerUp powerup: powerUps) {
            if(powerup.getRect().intersects(paddle.getRect())) {
            	powerCount=0;
                if(powerup.getType().equalsIgnoreCase("moreball")){
                    ArrayList<Ball> newBalls = new ArrayList<>();
                    for(Ball ball: balls) {
                        try {
                            Ball ballCopy = balls.get(0).clone();
                            ballCopy.ballLaunchRandom();
                            newBalls.add(ballCopy);

                            ballCopy = balls.get(0).clone();
                            ballCopy.ballLaunchRandom();
                            newBalls.add(ballCopy);

                         } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                         }
                    }
                    balls.addAll(newBalls);
                }
                else if (powerup.getType().equalsIgnoreCase("redball")){
                    for (Ball ball : balls) {
                        ball.ChangeToRedBall();
                    }
                }
                else {
                    paddle.setPowerUp(powerup.getType());
                }
                paddle.setBallStuckToPaddle(true);
                powerup.setDestroyed(true);
            }
        }
}

    private void checkCollisionBrickMovement(){
        for(int i=0;i<bricks.length;i++){
            for(int j=0;j<bricks.length;j++){
                if(i!=j){
                    if(bricks[i].getRect().intersects(bricks[j].getRect()) && !bricks[i].isDestroyed() && !bricks[j].isDestroyed()){
                      if(bricks[j].getRect().getMaxX()>bricks[i].getRect().getMaxX()){
                            bricks[i].ChangeDirection("left");
                            bricks[j].ChangeDirection("right");
                        }
                        else {
                            bricks[i].ChangeDirection("right");
                            bricks[j].ChangeDirection("left");
                        }
                    }
                    if(bricks[i].getX()<=0)
                        bricks[i].ChangeDirection("right");
                    if(bricks[i].getX()>= Commons.WIDTH - bricks[i].getImageWidth())
                        bricks[i].ChangeDirection("left");
                }
            }
        }
    }

private void checkCollisionPaddleBall() {
    for (Ball ball : balls) {
        if ((ball.getRect()).intersects(paddle.getRect())) {
            int max = 0;
            int min = 0;
            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();

            int first = paddleLPos + paddle.getImageWidth() / 4;
            int second = paddleLPos + paddle.getImageWidth() / 2;
            int third = paddleLPos + +3 * paddle.getImageWidth() / 4;
            int fourth = paddleLPos + 4 * paddle.getImageWidth();

            if (ballLPos < first) {
                min = 135;
                max = 155;
            }

            if (ballLPos >= first && ballLPos < second) {
                min = 110;
                max = 130;
            }

            if (ballLPos >= second && ballLPos < third) {
                if (rand.nextInt(2) == 0) {
                    min = 70;
                    max = 80;
                } else {
                    min = 100;
                    max = 110;
                }
            }

            if (ballLPos >= third && ballLPos < fourth) {
                min = 50;
                max = 70;
            }

            if (ballLPos > fourth) {
                min = 25;
                max = 45;
            }
            double radianValue = Math.toRadians(rand.nextInt((max - min) + 1) + min);
            System.out.println("Angle of Launch after collision: " + Math.toDegrees(radianValue));
            ball.setXDir((int) (ball.getSpeed() * Math.cos(radianValue)));
            ball.setYDir((int) (-Math.abs(ball.getSpeed() * Math.sin(radianValue))));
            System.out.println("YDIR: " + ball.getYDir() + "/ XDIR: " + ball.getXDir());
        }
    }

  }
  private void checkCollisionBallsDropped() {
    for(Ball ball: balls) {
        if (ball.getRect().getMaxY() > Commons.BOTTOM_EDGE) {
            ball.setDestroyed(true);
        }
    }
  }

  private void checkOnlyUnbreakableBricksLeft() { //Checks for no breakable breaks left, and if we should move on to the next level.
    Brick.setNoBricksLeft(true);
    Brick.setOnlyUnbreakableBricksLeft(true);

    for (Brick brick : bricks) {
          if (!brick.isDestroyed()) { // if a brick isn't destroyed and it is not unbreakable, then only unbreakablebrick left is false.
            Brick.setNoBricksLeft(false);
            if (!(brick instanceof BrickNotBreakable)) {
                  Brick.setOnlyUnbreakableBricksLeft(false);
                  break;
              }
          }
      }
      //if no breakable bricks left then change balls to red
      if (Brick.isOnlyUnbreakableBricksLeft()) {
          for (Ball ball : balls) {
              ball.ChangeToRedBall();
          }    
      }
    //if no bricks left then make new level
      if (Brick.isNoBricksLeft()) {
        paddle.setLevel(paddle.getLevel() + 1);
        DisplayText = "Victory! Time for Level " + (paddle.getLevel()) + "!";
        makeNextLevel();
        InGameTextCount = 0;
    }
  }

  private void checkCollisionBallBricks() {

  	   for (Brick brick: bricks) {
           for (Ball ball : balls) {
               if ((ball.getRect()).intersects(brick.getRect())) {

                   int ballLeft = (int) ball.getRect().getMinX();
                   int ballHeight = (int) ball.getRect().getHeight();
                   int ballWidth = (int) ball.getRect().getWidth();
                   int ballTop = (int) ball.getRect().getMinY();

                   var pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                   var pointLeft = new Point(ballLeft - 1, ballTop);
                   var pointTop = new Point(ballLeft, ballTop - 1);
                   var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                   if (!brick.isDestroyed()) {
                       if (brick.getRect().contains(pointRight)) {
                           ball.setXDir(-1 * Math.abs(ball.getXDir()));
                       } else if (brick.getRect().contains(pointLeft)) {
                           ball.setXDir(Math.abs(ball.getXDir()));
                       }
                       if (brick.getRect().contains(pointTop)) {
                           ball.setYDir(Math.abs(ball.getYDir()));
                       } else if (brick.getRect().contains(pointBottom)) {
                           ball.setYDir(-1 * Math.abs(ball.getYDir()));
                       }
                       brick.DecreaseHP(ball.isRedBall());
                       brick.updateImage();

                       if (brick.isDestroyed()) {
                           if (rand.nextInt(3) == 1) {
                               powerUps.add(powerUpFactory.getPowerUp(rand.nextInt(8), brick.getX() + brick.getImageWidth() / 2, brick.getY()));
                           }
                       }
                   }
               }
           }
        }
  	}
  private void checkCollisionMissileBricks() {
	  for(Missile missile: missiles) {
		  for (Brick brick: bricks) {
              if (!brick.isDestroyed()) {
		          if ((missile.getRect()).intersects(brick.getRect())) {
	                  brick.DecreaseHP(false);
                      brick.updateImage();
	                  missile.setDestroyed(true);
                      if(brick.isDestroyed()) {
                        powerUps.add(powerUpFactory.getPowerUp(rand.nextInt(9), brick.getX()+ brick.getImageWidth(), brick.getY()));
                    }
		          }
              }
		  }
	  }
  }

  private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {

            paddle.keyReleased(e);
        }

      @Override
        public void keyPressed(KeyEvent e) {
    	  //4
    	  int key = e.getKeyCode();
          paddle.keyPressed(e);
        if(inGame) {
            if (key == KeyEvent.VK_ESCAPE) {
                    try {
                        saveTheGame();
                        stopGame("Game File Saved successfully.");
                        InGameTextCount = 0;
                    } catch (CloneNotSupportedException cloneNotSupportedException) {
                        cloneNotSupportedException.printStackTrace();
                    }
                }
            }
        }
    }
}
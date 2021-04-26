package Main;

import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;

import Objects.Ball;
import Objects.Brick;
import Objects.BrickFactory;
import Objects.BrickNotBreakable;
import Objects.Missile;
import Objects.Player;
import Objects.PowerUp;
import Objects.PowerUpFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Timer timer;
    String saved_message = "Hi. Click NEW GAME or load a SAVED GAME.";
    private ArrayList<Ball> balls;
    private ArrayList<PowerUp> powerups;
    private Player paddle;
    private boolean paused = false;
    private Brick[] bricks;
    boolean inGame = false;
    
    private JWindow menuWindow;
    instance[] instances;
    private int VictoryCount = 1000; //high enough so it doesn't print the victory message at the start
    BrickFactory factory = new BrickFactory();
    PowerUpFactory powerUpFactory =  new PowerUpFactory();
    public Board() {

        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(Commons.WIDTH, Commons.HEIGHT));
        setBackground(Color.WHITE);
        gameInit();
        
    }

    private void gameInit() {
        paddle = Player.getPaddleInstance();
        instances = new instance[2];

        for(int i = 0; i < instances.length; i++) {
            instances[i] = new instance();
            }
        makeNewInstance();
        Menu menu = Menu.getMenu();
        menuWindow = menu.makingTheMenu(this);
        pauseGame();
        timer = new Timer(Commons.PERIOD, new GameCycle());
        timer.start();
    }
    //Don't use call this function. use makeNewInstance(); or makeNextLevel();
    private void makeGameInstance() {
        instances[0] = new instance();
        instances[0].setisEmpty(false);

        balls = instances[0].getBalls();
        bricks = instances[0].getbricks();
        powerups = instances[0].getPowerups();

        balls.add(new Ball());

        maketheBricks();
        System.out.println("Current Life: " +paddle.getLife());

        paddle.setX(instances[0].getPlayerX());
        paddle.setY(instances[0].getPlayerY());
     }
    
    void makeNewInstance() {
        makeGameInstance();
        paddle.initState();

    }
    private void makeNextLevel() {
        makeGameInstance();
        paddle.setBallStuckToPaddle(true);
    }
    void getsavedInstance() throws CloneNotSupportedException {
        instances[0] = new instance();

        instances[0].setBallsCloneOf(instances[1].getBalls());
        instances[0].setBricksCloneOf(instances[1].getbricks());
        instances[0].setPowerUpCloneOf(instances[1].getPowerups());

        balls = instances[0].getBalls();
        bricks = instances[0].getbricks();
        powerups = instances[0].getPowerups();

        paddle.setX(instances[1].getPlayerX());
        paddle.setY(instances[1].getPlayerY());
        paddle.setLife(instances[1].getLife());
        paddle.setScore(instances[1].getScore());
        paddle.setLevel(instances[1].getLevel());
        paddle.setPowerUp(instances[1].getplayerPowerUpAbility());
        paddle.setPowerUp(instances[1].getplayerPowerUpSpeed());
        paddle.setBallStuckToPaddle(instances[1].isBallStuckToPaddle());

    }
    void saveTheGame() throws CloneNotSupportedException{
        instances[1] = new instance();
        instances[1].setisEmpty(false);
        instances[1].setBallsCloneOf(instances[0].getBalls());
        instances[1].setBricksCloneOf(instances[0].getbricks());
        instances[1].setPowerUpCloneOf(instances[0].getPowerups());

        instances[1].setPlayerX(paddle.getX());
        instances[1].setPlayerY(paddle.getY());
        instances[1].setLife(paddle.getLife());
        instances[1].setScore(paddle.getScore());
        instances[1].setLevel(paddle.getLevel());
        instances[1].setPlayerPowerUpAbility(paddle.getPlayerPowerUpAbility());
        instances[1].setPlayerPowerUpSpeed(paddle.getPlayerPowerUpSpeed());

        instances[1].setBallStuckToPaddle(paddle.isBallStuckToPaddle());

    }
    public void maketheBricks(){
        int k = 0;
        //Make the Bricks
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                int rand = ((int)(Math.random()*7 )) + 1;
                bricks[k] = factory.getBrick(j * 145 + 120, i *50 + 50, rand);
                k++;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

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
        else if (!paused && VictoryCount <100){
            VictoryCount++;
            DrawSavedMessage(g2d); 
        }

        Toolkit.getDefaultToolkit().sync();
    } 


   private void drawStringTopRight(Graphics2D g2d, String message, int height) {
        var font = new Font("Verdana", Font.BOLD, 15);
        FontMetrics fontMetrics = this.getFontMetrics(font);
        g2d.setColor(Color.RED);
        g2d.setFont(font);
        g2d.drawString(message, Commons.WIDTH - fontMetrics.stringWidth(message) - 2, height);
    }

    private void drawObjects(Graphics2D g2d) {
        g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),paddle.getImageWidth(), paddle.getImageHeight(), this);

        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);    
            g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(), ball.getImageWidth(), ball.getImageHeight(), this);
        }
        for (int i = 0; i < powerups.size(); i++) {
            PowerUp powerUp = powerups.get(i);    
            g2d.drawImage(powerUp.getImage(), powerUp.getX(), powerUp.getY(), powerUp.getImageWidth(), powerUp.getImageHeight(), this);
        }

        for (int i = 0; i < Commons.N_OF_BRICKS; i++) {
            if (!bricks[i].isDestroyed()) {
                g2d.drawImage(bricks[i].getImage(), bricks[i].getX(), bricks[i].getY(), bricks[i].getImageWidth(), bricks[i].getImageHeight(), this);
            }

        }

        for (int i = 0; i < paddle.getMissiles().size(); i++) {
        	 Missile missile = paddle.getMissiles().get(i);

        	if (!missile.isOutOfBounds() && !missile.isDestroyed()) {
                g2d.drawImage(missile.getImage(), missile.getX(),missile.getY(), missile.getImageWidth(), missile.getImageHeight(), this);
            }
        	else {
        		paddle.getMissiles().remove(i);
        	}
        }
    }
    private void DrawSavedMessage(Graphics2D g2d) {

        var font = new Font("Verdana", Font.BOLD, 18);
        FontMetrics fontMetrics = this.getFontMetrics(font);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(saved_message,(Commons.WIDTH - fontMetrics.stringWidth(saved_message)) / 2, 20);
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

        for(int i=0;i<bricks.length;i++){
            if(!bricks[i].isDestroyed()) {
                bricks[i].move();
            }
        }
        for(int i=0;i<balls.size();i++){
            balls.get(i).move();
        }
        for(int i=0;i<powerups.size();i++){
            powerups.get(i).move();
        }

        for(int i = 0; i < paddle.getMissiles().size(); i++) {
       	 	Missile missile = paddle.getMissiles().get(i);
       	 	missile.move();	
        }
    }
    private void doGameCycle() {
        moveGameObjects();
        checkCollision();
        checkifOnlyUnbreakbleBricksLeft();      
    }
    private void stopGame() {
        saved_message = "Game Over. Try Again. Choose NEW GAME or load a SAVED GAME";
        inGame = false;
        menuWindow.setVisible(true);
    }
    
  private void checkCollision() {
	  	checkCollisionBallsDropped();
	  	checkCollisionMissileBricks();
        checkCollisionPaddleBall();
        checkCollisionBallBricks();
        checkCollisionPowerupPaddle();
        checkCollisionBrickMovement();
    }
  
    private void checkCollisionPowerupPaddle() { 
        for (int j = 0; j < powerups.size(); j++) {
            PowerUp powerup = powerups.get(j);
            if(powerup.getRect().intersects(paddle.getRect())) {
                if(powerup.getType().equalsIgnoreCase("moreball")){
                    int ballsize = balls.size(); 
                    for(int i=0; i< ballsize; i++) {
                        try {
                            Ball ball = balls.get(0).clone();
                            ball.ballLaunchRandom();
                            balls.add(ball);

                            ball = balls.get(0).clone();
                            ball.ballLaunchRandom();
                            balls.add(ball);
                            
                         } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                         }
                    }
                } 
                else if (powerup.getType().equalsIgnoreCase("redball")){
                    for(int i = 0; i < balls.size() ; i++) {
                        balls.get(i).ChangeToRedBall();
                    }
                    for(int i =0; i < bricks.length;i++) {
                        bricks[i].setBreakable();
                    }
                }
                else {
                    paddle.setPowerUp(powerup.getType());
                }
                paddle.setBallStuckToPaddle(true);
                powerups.remove(j);
            }
            if(powerup.isOutOfBounds()) {
                powerups.remove(j);
            }
        }
}

    private void checkCollisionBrickMovement(){
        for(int i=0;i<bricks.length;i++){
            for(int j=0;j<bricks.length;j++){
                if(i==j) {
                    continue;
                }
                else{
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
    for(int i = 0; i < balls.size();i++) {
        Ball ball = balls.get(i);
        if ((ball.getRect()).intersects(paddle.getRect())) {
            Random rand = new Random();
            int max=0;
            int min=0;
            int paddleLPos = (int) paddle.getRect().getMinX();
            int ballLPos = (int) ball.getRect().getMinX();
  
            int first = paddleLPos +paddle.getImageWidth()/4;
            int second = paddleLPos +paddle.getImageWidth()/2;
            int third = paddleLPos + +3*paddle.getImageWidth()/4;
            int fourth = paddleLPos + 4*paddle.getImageWidth();
  
            if (ballLPos < first) {
              min = 135;
              max = 155;
            }
  
            if (ballLPos >= first && ballLPos < second) {
              min = 110;
              max = 130;  
              }
  
            if (ballLPos >= second && ballLPos < third) {
              if(rand.nextInt(2) == 0) {
                  min = 70;
                  max = 80;
              }
              else {
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
            ball.setXDir((int)(ball.getSpeed()*Math.cos(radianValue)));
            ball.setYDir((int) (-Math.abs( ball.getSpeed()*Math.sin(radianValue))));
            System.out.println("YDIR: " + ball.getYDir() + "/ XDIR: " + ball.getXDir());
        }
    }
  
  }
  private void checkCollisionBallsDropped() {
    for(int i = 0; i < balls.size();i++) {
        Ball ball = balls.get(i);
        if (ball.getRect().getMaxY() > Commons.BOTTOM_EDGE) {
            balls.remove(i);
            balls.size();
                if(balls.size() < 1){
                    System.out.println("Current Life: " + paddle.getLife());         
                    paddle.loseALife();
                    ball = new Ball();
                    ball.ballLaunchRandom();
                    balls.add(ball);
                    paddle.setBallStuckToPaddle(true);

                    if(paddle.getLife()<1) {
                        stopGame();
                    }
                }
        }
        
    }
  }
  private void checkifOnlyUnbreakbleBricksLeft(){
    boolean NoBricksLeft = true;
    boolean BreakableBricksLeft = false;  

    for(int i = 0; i < bricks.length;i++) {
        if(!bricks[i].isDestroyed()){
            NoBricksLeft = false;

            if(!(bricks[i] instanceof BrickNotBreakable)) {
                BreakableBricksLeft = true;
                break;
            }
        }

      }
      //if no breakable bricks left then:
        if(!BreakableBricksLeft){
            for(int r=0; r < balls.size();r++) {
                balls.get(r).ChangeToRedBall();
            }
            for(int k=0;k<Commons.N_OF_BRICKS; k++)
              bricks[k].setBreakable();
        }
        if (NoBricksLeft) { 
            paddle.setLevel(paddle.getLevel()+1);
            saved_message = "Victory! Time for Level " + (paddle.getLevel()) + "!";
            makeNextLevel();
            VictoryCount=0;
        }
  }

  private void checkCollisionBallBricks() {
  	   for (int i = 0; i < Commons.N_OF_BRICKS; i++) {
        for(int j = 0; j< balls.size();j++) {
            Ball ball = balls.get(j);
            if ((ball.getRect()).intersects(bricks[i].getRect())) {

                int ballLeft = (int) ball.getRect().getMinX();
                int ballHeight = (int) ball.getRect().getHeight();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballTop = (int) ball.getRect().getMinY();

                var pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                var pointLeft = new Point(ballLeft - 1, ballTop);
                var pointTop = new Point(ballLeft, ballTop - 1);
                var pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);
                if (!bricks[i].isDestroyed()) {
                    if (bricks[i].getRect().contains(pointRight)) {
                        ball.setXDir(-1*Math.abs(ball.getXDir()));
                    } 
                    else if (bricks[i].getRect().contains(pointLeft)) {
                        ball.setXDir(1*Math.abs(ball.getXDir()));
                    }
                    if (bricks[i].getRect().contains(pointTop)) {
                        ball.setYDir(1*Math.abs(ball.getYDir()));
                    } 
                    else if (bricks[i].getRect().contains(pointBottom)) {
                        ball.setYDir(-1*Math.abs(ball.getYDir()));
                    }
                    bricks[i].DecreaseHP();
                    bricks[i].updateImage();

                    if(bricks[i].isDestroyed()) {
                        Random rand = new Random();
                        if(rand.nextInt(3) == 1) {
                            powerups.add(powerUpFactory.getPowerUp(rand.nextInt(8), bricks[i].getX()+ bricks[i].getImageWidth()/2, bricks[i].getY()));
                        }
                    }
                }
            }
            }
        }
  	}
  private void checkCollisionMissileBricks() {
	  for(int i = 0; i < paddle.getMissiles().size();i++) {
		  for (int b = 0; b < Commons.N_OF_BRICKS; b++) {
              if (!bricks[b].isDestroyed()) {
		          if ((paddle.getMissiles().get(i).getRect()).intersects(bricks[b].getRect())) {
	                  bricks[b].DecreaseHP();
                      bricks[b].updateImage();
	                  paddle.getMissiles().get(i).setDestroyed(true);

                      if(bricks[b].isDestroyed()) {
                        Random rand = new Random();
                        powerups.add(powerUpFactory.getPowerUp(rand.nextInt(8), bricks[i].getX()+ bricks[b].getImageWidth(), bricks[b].getY()));
                    }
		          }
              }
		  }
	  }	
  }

    void unpauseGame() {
        paused = false;
    }

    private void pauseGame() {
        paused = true;
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
                saved_message = "Press NEW GAME to start over, or LOAD SAVE to load a saved game";
                if(paused) {
                    menuWindow.setVisible(false);
                    unpauseGame();
                }
                else { 
                    menuWindow.setVisible(true); 
                    pauseGame();
                    } 
                }
            }
        }
    }
}
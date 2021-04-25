package Main;
import Objects.Ball;
import Objects.Brick;
import Objects.PowerUp;

import java.util.ArrayList;

public class instance {
    private ArrayList<Ball> balls;
    private ArrayList<PowerUp> powerups;
    private Brick[] bricks;
    private int score;
    private int level;
    private int count;
    private int playerX;
    private boolean isEmpty;
    private int playerY;
    private int playerLife;
    private boolean BallStuckToPaddle;
    private String playerPowerUp;

    public instance() {
          bricks = new Brick[Commons.N_OF_BRICKS];
          balls = new ArrayList<>();
          powerups = new ArrayList<>();
          isEmpty = true;
          playerX =Commons.INIT_PADDLE_X;
          playerY =Commons.INIT_PADDLE_Y;

    }
    public int getPlayerY() {
        return playerY;
    }
    public int getPlayerX() {
        return playerX;
    }
    public void setPlayerY(int y) {
        playerY = y;
    }
    public void setPlayerX(int x) { 
        playerX = x;

    }
    public void setBallStuckToPaddle(boolean BallStuckToPaddle) {
        this.BallStuckToPaddle = BallStuckToPaddle;
    }
    public boolean isBallStuckToPaddle() {
        return BallStuckToPaddle;
    }
    public void setisEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
    public boolean isEmpty() {
    return isEmpty;
    }
    public void setPlayerPowerUp(String playerPowerUp) {
        this.playerPowerUp = playerPowerUp;
    }
    public String getplayerPowerUp() {
        return playerPowerUp;
    }
    public void setScore(int score) {
            this.score = score;
    }
    public int getScore() {
        return score;
    }
    public int getLife() {
        return playerLife;
    }
    public void setLife(int playerLife) {
        this.playerLife = playerLife;
    } 
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public void setBallsCloneOf(ArrayList<Ball> OldList) throws CloneNotSupportedException  {
        for(Ball ball: OldList){
            this.balls.add(ball.clone());
            }
    }
    public void setBricksCloneOf(Brick[] OldList) throws CloneNotSupportedException  {
        for(Brick brick: OldList){
            this.bricks[count] = brick.clone();
            count++;
            }
            count=0;
    }
    public void setPowerUpCloneOf(ArrayList<PowerUp> OldList) throws CloneNotSupportedException  {
            for(PowerUp powerup: OldList){
                this.powerups.add(powerup.clone());
            }
        }
  
    public ArrayList<Ball> getBalls(){
        return balls;
    }
    public Brick[] getbricks(){
        return bricks;
    }
    public ArrayList<PowerUp> getPowerups() {
        return powerups;
    }
}

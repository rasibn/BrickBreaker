package Main;
import Objects.Ball;
import Objects.Brick;
import Objects.Player;
import Objects.PowerUp;

import java.util.ArrayList;

/*
This class' job is to serve as a abstraction to hold all the game state related information for the board to then draw.
There will be an instance of this class in board which should be the current instance being drawn, while the rest of the instances should represent save files.
 */
public class instance {
    private final ArrayList<Ball> balls;
    private final ArrayList<PowerUp> powerups;
    public Brick[] bricks;
    private int score;
    private int level;
    private int count;
    private int playerX;
    private int playerY;
    private boolean isEmpty;
    private int playerLife;
    private boolean BallStuckToPaddle;
    private String playerPowerUpAbility;
    private String playerPowerUpSpeed;
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
    public void setPlayerPowerUpAbility(String playerPowerUpAbility) {
        this.playerPowerUpAbility = playerPowerUpAbility;
    }
  
    public void setPlayerPowerUpSpeed(String playerPowerUpSpeed) {
        this.playerPowerUpSpeed = playerPowerUpSpeed;
    }
    public String getplayerPowerUpAbility() {
        return playerPowerUpAbility;
    }
    public String getplayerPowerUpSpeed() {
        return playerPowerUpSpeed;
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
    //The player is a singleton clss so we will just save and load the specifics of the state of the player object, rather than creating a new player object.
    public void savePlayerInfo(Player paddle) {
        this.setPlayerX(paddle.getX());
        this.setPlayerY(paddle.getY());
        this.setLife(paddle.getLife());
        this.setScore(paddle.getScore());
        this.setLevel(paddle.getLevel());
        this.setPlayerPowerUpAbility(paddle.getPlayerPowerUpAbility());
        this.setPlayerPowerUpSpeed(paddle.getPlayerPowerUpSpeed());
        this.setBallStuckToPaddle(paddle.isBallStuckToPaddle());
    }
    public void getPlayerinfo(Player paddle) {
        paddle.setX(this.getPlayerX());
        paddle.setY(this.getPlayerY());
        paddle.setLife(this.getLife());
        paddle.setScore(this.getScore());
        paddle.setLevel(this.getLevel());
        paddle.setPowerUp(this.getplayerPowerUpAbility());
        paddle.setPowerUp(this.getplayerPowerUpSpeed());
        paddle.setBallStuckToPaddle(this.isBallStuckToPaddle());
    }
}

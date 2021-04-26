package Objects;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Behaviour.Animation;
import Main.Commons;

public class Player extends Sprite{

	private Animation animation;

	private static Player paddle;
	private ArrayList<Missile> Missiles;	
	private final int defaultSpeed = 6;

	private int PlayerLife;
	private boolean ShootingEnabled = false;
	private boolean BallStuckToPaddle;
	private int score;
	private String playerPowerUpAbility = "default";
	private String playerPowerUpSpeed ="default";
	private int level;
	
	private Player(){
		initPaddle();
	}
	public static Player getPaddleInstance() {
		
		if(paddle == null) {
			paddle = new Player();
		}
		return paddle;
	}
	
	private void initPaddle() {
		
		animation = new Animation();
		Missiles = new ArrayList<>();
		setXDir(0);
        initState();
		
	}
    public void initState() {
		PlayerLife=3;
		score = 0;
		level = 1;
		setBallStuckToPaddle(true);
		setPowerUp("default");
	}
	public void resetForStateNextLevel() {
		setBallStuckToPaddle(true);

	}
    
    public void loadImage() {
    	image = animation.CycleImageWithDelay(5);
        getImageDimensions();
    }
    
	private void Fire() {
		
		Missile missile1 = new Missile();
		Missile missile2 = new Missile();

		missile1.setX(this.getX());
		missile2.setX(this.getX()+this.getImageWidth()-missile1.getImageWidth());

		missile1.setY(this.getY());
		missile2.setY(this.getY());
		
		Missiles.add(missile1);
		Missiles.add(missile2);
	}

	
	private void setImagesDefault() {
		animation.clearImagesFromCycle();
		animation.addToAnimationCycle("src/PNG/50-Breakout-Tiles.png");
		animation.addToAnimationCycle("src/PNG/51-Breakout-Tiles.png");
		animation.addToAnimationCycle("src/PNG/52-Breakout-Tiles.png");
	}
	
	private void setImageFire() {
		animation.clearImagesFromCycle();
		animation.addToAnimationCycle("src/PNG/53-Breakout-Tiles.png");
		animation.addToAnimationCycle("src/PNG/54-Breakout-Tiles.png");
		animation.addToAnimationCycle("src/PNG/55-Breakout-Tiles.png");
	}
	
	private void setImageLong() {
		animation.clearImagesFromCycle();
		animation.addToAnimationCycle("src/PNG/56-Breakout-Tiles.png");
		animation.addToAnimationCycle("src/PNG/56-Breakout-Tiles-flipped.png");
	}
	private void setImageSmall() {
		animation.clearImagesFromCycle();
		animation.addToAnimationCycle("src/PNG/57-Breakout-Tiles.png");
	
	}
	public void setPowerUp(String powerUpName) {
		playerPowerUpAbility = powerUpName;
		switch (powerUpName) {
			case "fire" -> {
				powerUpFire();
				playerPowerUpAbility = powerUpName;
			}
			case "long" -> {
				powerUpLong();
				playerPowerUpAbility = powerUpName;
			}
			case "extralife" -> setLife(getLife() + 1);
			case "small" -> {
				powerUpSmall();
				playerPowerUpAbility = powerUpName;
			}
			case "slow" -> {
				powerUpSlow();
				playerPowerUpSpeed = powerUpName;
			}
			case "fast" -> {
				powerUpFast();
				playerPowerUpSpeed = powerUpName;
			}
			case "default" -> {
				playerPowerUpSpeed = powerUpName;
				playerPowerUpAbility = powerUpName;
				powerUpNone();
			}
			default -> {
				powerUpNone();
				System.out.println("Wrong Power Name!");
			}
		}
	}
	private void powerUpFire() {
		speed = defaultSpeed;
		setImageFire();
		ShootingEnabled = true;
	}
	private void powerUpLong() {
		speed = defaultSpeed;
		setImageLong();
		ShootingEnabled = false;
	}
	private void powerUpSmall() {
		speed = defaultSpeed;
		setImageSmall();
		ShootingEnabled = false;
	}
	private void powerUpSlow() {
		//setImagesDefault();
		speed = defaultSpeed/2;
		//ShootingEnabled = false;
	}
	private void powerUpFast() {
		//setImagesDefault();
		speed = defaultSpeed*2;
		//ShootingEnabled = false;
	}
	private void powerUpNone() {
		speed = defaultSpeed;
		setImagesDefault();
		ShootingEnabled = false;
	}
	

	public void move() {
			setX(getX()+getXDir());
	        if (getX() <= 0) {
	            setX(0);
	        }
	        if (getX() >= Commons.WIDTH - getImageWidth()) {
	            setX(Commons.WIDTH - getImageWidth());
	        }
	    }

	    
	public Animation getAnimation() {
		return animation;
		}
		
	public boolean isShootingEnabled() {
		return ShootingEnabled;
	}
	 public ArrayList<Missile> getMissiles() {
		return Missiles;
	}
	public boolean isBallStuckToPaddle() {
		return BallStuckToPaddle;
	}
	public void setBallStuckToPaddle(boolean val) {
		BallStuckToPaddle = val;
	}

	public void keyPressed(KeyEvent e) 
	{
	    int key = e.getKeyCode();
	    
	    if (key == KeyEvent.VK_LEFT) {setXDir(-speed);}
	    if (key == KeyEvent.VK_RIGHT) {setXDir(speed);}
	    if (key == KeyEvent.VK_SPACE) {
	    	if(isShootingEnabled()) {
	    		Fire();
	    	}
	    	BallStuckToPaddle = false;
	    }

	}
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {setXDir(0);}
	    if (key == KeyEvent.VK_RIGHT) {setXDir(0);}
	}
	public void gainALife() {
		PlayerLife++;
	}
	public void loseALife() {
		PlayerLife--;
	}
	public int getLife() {
		return PlayerLife;
	}
	public void setLife(int PlayerLife) {
		this.PlayerLife = PlayerLife;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLevel() {
		return level;
	}
	public String getPlayerPowerUpAbility() {
		return playerPowerUpAbility;
	}
	public String getPlayerPowerUpSpeed() {
		return playerPowerUpSpeed;
	}	
}
	


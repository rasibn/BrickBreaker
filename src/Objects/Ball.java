package Objects;
import java.util.Random;

import javax.swing.ImageIcon;

import Main.Commons;
public class Ball extends Sprite implements Cloneable{
    private String path;

    public Ball() {

        initBall();
    }
      
    private void initBall() {
        setX(Player.getPaddleInstance().getX() + Player.getPaddleInstance().getImageWidth()/2 - this.getImageWidth()/2);
    	setY(Player.getPaddleInstance().getY() - this.getImageHeight());
    	speed =6;
        setYDir(-speed);
    	setImageScaling(2.0);
        ballLaunchRandom();
        setPath("src/PNG/58-Breakout-Tiles.png");
        loadImage();
    }
    
    public void ballLaunchRandom() {
        Random rand = new Random();
        double degreevalue =rand.nextInt(30);        
        if(Math.random() > 0.5){
            degreevalue += 40;
        }
        else {
            degreevalue += 110;
        }
        System.out.println("Initial Launch angle:" + degreevalue);
        
        setYDir((int) (-Math.abs(speed*Math.sin(Math.toRadians(degreevalue)))));
        setXDir((int) (speed*Math.cos(Math.toRadians(degreevalue))));      
    }

    @Override
    public void loadImage() {
        var ii = new ImageIcon(path);
        image = ii.getImage();
        getImageDimensions();
    }
    public void powerUpCloneBall() {
    	//Will make this later.
    }

    @Override
    public void move() {
        if (getY() >= Commons.HEIGHT) {
            setDestroyed(true);
        }
    	if(Player.getPaddleInstance().isBallStuckToPaddle()) {
    		setX(Player.getPaddleInstance().getX() + Player.getPaddleInstance().getImageWidth()/2 - this.getImageWidth()/2);
    		setY(Player.getPaddleInstance().getY() - this.getImageHeight());
    	}
    	else {
			setX(getX()+getXDir());
			setY(getY()+getYDir());
	
	        if (getX() <= 0) {
	            setXDir(Math.abs(getXDir()));
	        }
	        if (getX() >= Commons.WIDTH - getImageWidth()) {
		        setXDir(-Math.abs(getXDir()));
	        }
	
	        if (getY() <= 0) {
	            setYDir(Math.abs(getYDir()));
	        }
    	}
    }

    public void setPath(String path){
        this.path = path;
    }

    public void ChangeToRedBall() {
        path = "src/PNG/redball.png";
        loadImage();
    }
    public boolean isRedBall(){
        return path.equals("src/PNG/redball.png");
    }

    @Override
    public Ball clone() throws CloneNotSupportedException {
        return (Ball) super.clone();
    }
}

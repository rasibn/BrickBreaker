package Objects;

import javax.swing.ImageIcon;
public class Missile extends Sprite {

    private int ydir;
	private boolean OutOfBounds;
    private boolean destroyed;
    public Missile() {

        initMissile();
    }
    private void initMissile() {
        ydir = -5;

        loadImage();
        getImageDimensions();
    }
    
    @Override
    public void loadImage() {
        var ii = new ImageIcon("src/PNG/1.png");
        image = ii.getImage();
    }

    public void move() {
        setY(getY() + ydir);

        if (getY() <= 0) {
           OutOfBounds = true;
        }
    }
    
    public void setYDir(int y) {

        ydir = y;
    }

    public int getYDir() {

        return ydir;
    }
    public void setDestroyed(boolean val) {

        destroyed = val;
    }
    public boolean isDestroyed() {
        return destroyed;
    }

	public boolean isOutOfBounds() {
		return OutOfBounds;
	}
}

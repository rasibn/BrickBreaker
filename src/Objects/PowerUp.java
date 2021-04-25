package Objects;
import Main.Commons;
import javax.swing.ImageIcon;

public abstract class PowerUp extends Sprite implements Cloneable{

    protected String path;
    protected String PowerUpName;
	private boolean OutOfBounds;
    public PowerUp() {
        initPowerUp();
    }
    private void initPowerUp() {
        setYDir(3);
        setImageScaling(2);
    }
    
    @Override
    public void loadImage() {
        var ii = new ImageIcon(path);
        image = ii.getImage();
    }
    public String getType() {
        return PowerUpName;
    }
    public void move() {
        setY(getY() + getYDir());

        if (getY() >= Commons.HEIGHT) {
           OutOfBounds = true;
        }
    }
	public boolean isOutOfBounds() {
		return OutOfBounds;
	}
    @Override
    public PowerUp clone() throws CloneNotSupportedException {
        PowerUp newPowerUp = (PowerUp) super.clone();
        return newPowerUp;
    }
}


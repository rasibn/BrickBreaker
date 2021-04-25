package Objects;
import Main.Commons;
import javax.swing.ImageIcon;

public abstract class PowerUp extends Sprite {
    private String path;

	private boolean OutOfBounds;
    public PowerUp() {
        initPowerUp();
    }
    private void initPowerUp() {
        setYDir(3);
        loadImage();
        getImageDimensions();
    }
    
    @Override
    public void loadImage() {
        var ii = new ImageIcon(path);
        image = ii.getImage();
    }

    public void move() {
        setY(getY() + getYDir());

        if (getY() <= Commons.HEIGHT) {
           OutOfBounds = true;
        }
    }
	public boolean isOutOfBounds() {
		return OutOfBounds;
	}
}


package Objects;
import Main.Commons;
import javax.swing.ImageIcon;

public abstract class PowerUp extends Sprite implements Cloneable{

    protected String path;
    protected String PowerUpName;
    public PowerUp() {
        initPowerUp();
    }
    private void initPowerUp() {
        setYDir(3);
        setImageScaling(2.1  );
    }
    
    @Override
    public void loadImage() {
        var ii = new ImageIcon(path);
        image = ii.getImage();
    }
    public String getType() {
        return PowerUpName;
    }
    @Override
    public void move() {
        setY(getY() + getYDir());

        if (getY() >= Commons.HEIGHT) {
           setDestroyed(true);
        }
    }
    @Override
    public PowerUp clone() throws CloneNotSupportedException {
        return (PowerUp) super.clone();
    }
}


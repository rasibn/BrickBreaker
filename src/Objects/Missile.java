package Objects;

import javax.swing.ImageIcon;
public class Missile extends Sprite {

    public Missile() {

        initMissile();
    }
    private void initMissile() {
        setYDir(-5);

        loadImage();
        getImageDimensions();
    }
    
    @Override
    public void loadImage() {
        var ii = new ImageIcon("src/PNG/1.png");
        image = ii.getImage();
    }
    @Override
    public void move() {
        setY(getY() + getYDir());

        if (getY() <= 0) {
           setDestroyed(true);
        }
    }
}

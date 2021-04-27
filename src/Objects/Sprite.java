package Objects;

import java.awt.Image;
import java.awt.Rectangle;

public abstract class Sprite {

    private int x;
    private int y;
    private int xdir;
    private int ydir;
    protected int speed;
    private double ImageScale = 1.7; //This is the default image scaling. If you increase this value then the image will be shorter.
    private int imageWidth;
    private int imageHeight;
    protected Image image;
    private boolean destroyed = false;

    public void setX(int x) {

        this.x = x;
    }
   
    public void setYDir(int y) {

        ydir = y;
    }

    public int getYDir() {

        return ydir;
    }
    public int getXDir() {

        return xdir;
    }
    public void setXDir(int x) {
        xdir = x;
    }

    public int getX() {

        return x;
    }

    public void setY(int y) {

        this.y = y;
    }
    public abstract void loadImage();

    public int getY() {

        return y;
    }
    public void setImageScaling(double ImageScale){
        this.ImageScale = ImageScale;
    }
    public int getSpeed() {
        return speed;
    }
    /*
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    */
    public abstract void move();

    public int getImageWidth() {

        return (int) (imageWidth/ImageScale);
    }

    public int getImageHeight() {

        return (int) (imageHeight/ImageScale);
    }

    public Image getImage() {

        return image;
    }

    public Rectangle getRect() {

        return new Rectangle(x, y, getImageWidth(), getImageHeight());
    }
    public boolean isDestroyed() {
        return destroyed;
    }
    public void setDestroyed(boolean val) {
        destroyed = val;
    }
    void getImageDimensions() {

        imageWidth =(int) (image.getWidth(null)/ImageScale);
        imageHeight =(int) (image.getHeight(null)/ImageScale);
    }
}
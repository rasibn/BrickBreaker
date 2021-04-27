package Objects;

public class SmallBrick1HP extends Brick {
    public SmallBrick1HP(int x, int y){
        super(x, y);
        DefaultHP = 1;
        HP = DefaultHP;
        this.updateImage();
    }
    @Override
    public void updateImage(){
     SetPath("src/PNG/26-Breakout-Tiles.png");
     super.loadImage();
    }
}

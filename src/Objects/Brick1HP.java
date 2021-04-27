package Objects;

public class Brick1HP extends Brick {
    public Brick1HP(int x, int y){
        super(x, y);
        DefaultHP = 1;
        HP = DefaultHP;
        score = DefaultHP*10;
        this.updateImage();
    }
    @Override
    public void updateImage(){
     SetPath("src/PNG/01-Breakout-Tiles.png");
     super.loadImage();
    }
}

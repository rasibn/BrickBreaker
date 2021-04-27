package Objects;

public class Brick1HP extends Brick {
    public Brick1HP(int x, int y){
        super(x, y);
        HP = 1;
        score = HP*10;
        this.updateImage();
    }
    @Override
    public void updateImage(){
     SetPath("src/PNG/01-Breakout-Tiles.png");
     super.loadImage();
    }
}

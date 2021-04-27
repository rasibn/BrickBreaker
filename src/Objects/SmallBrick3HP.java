package Objects;

public class SmallBrick3HP extends Brick {
    public SmallBrick3HP(int x, int y){
        super(x, y);
        DefaultHP = 3;
        HP = DefaultHP;
        this.updateImage();
       }   
    @Override
    public void updateImage(){
        if(HP==3)
            SetPath("src/PNG/24-Breakout-Tiles.png");
       else if(HP==2)
            SetPath("src/PNG/25-Breakout-Tiles.png");
       else if (HP == 1) {
            SetPath("src/PNG/26-Breakout-Tiles.png");
       }
        
        super.loadImage();
    }
}

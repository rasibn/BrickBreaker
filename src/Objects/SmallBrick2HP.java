package Objects;

public class SmallBrick2HP extends Brick {
    public SmallBrick2HP(int x, int y){
        super(x, y);
        DefaultHP = 2;
        HP = DefaultHP;
        this.updateImage();
       }   
    @Override
    public void updateImage(){
      if( HP == 2){
        SetPath("src/PNG/25-Breakout-Tiles.png");
      }
      else if(HP == 1) {
        SetPath("src/PNG/26-Breakout-Tiles.png");
      }
        super.loadImage();
    }
}

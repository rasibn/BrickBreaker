package Objects;

public class BrickNotBreakable extends Brick {
   public BrickNotBreakable(int x,int y){
    super(x, y);
    super.HP = -1;
    score = 100;
    this.updateImage();
   }
   @Override
   public void updateImage(){
    SetPath("src/PNG/07-Breakout-Tiles.png");
    super.loadImage();
    }
}

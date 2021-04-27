package Objects;

public class Brick3HP extends Brick {
    public Brick3HP(int x, int y){
        super(x, y);
        DefaultHP = 3;
        HP = DefaultHP;
        score = DefaultHP*10;
        this.updateImage();
       }
      @Override
      public void updateImage(){
       if(HP==3)
         SetPath("src/PNG/05-Breakout-Tiles.png");
        else if(HP==2)
         SetPath("src/PNG/06-Breakout-Tiles.png");
        else if (HP ==1) {
          SetPath("src/PNG/04-Breakout-Tiles.png");
        }
         
         super.loadImage();
      }
}

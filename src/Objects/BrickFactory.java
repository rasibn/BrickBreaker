package Objects;

public class BrickFactory {
    public Brick getBrick(int x, int y, int type){
            //Big Bricks
            if(type==1)
                return new Brick1HP(x, y);
            if(type==2)
                return new Brick2HP(x, y);
            if(type==3)
                return new Brick3HP(x, y);
          
            //Smaller Bricks
            if(type==4) {
                return new SmallBrick1HP(x, y);
            }
            if(type==5) {
                return new SmallBrick2HP(x, y);
            }
            if(type==6) {
                return new SmallBrick3HP(x, y);
            }
            //Unbreakable bricks
            if(type==7){
                return new BrickNotBreakable(x,y);
            }
            else
            return null;
        }
}


package Objects;

public class PowerUpRedBall extends PowerUp {
    PowerUpRedBall(){
        super();
        PowerUpName = "redball";
        path ="src/PNG/44-Breakout-Tiles.png";
        loadImage();
        getImageDimensions();
    }
}

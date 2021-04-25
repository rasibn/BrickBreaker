package Objects;

public class PowerUpFire extends PowerUp {
    PowerUpFire(){
        super();
        PowerUpName = "fire";
        path ="src/PNG/48-Breakout-Tiles.png";
        loadImage();
        getImageDimensions();
    }
}

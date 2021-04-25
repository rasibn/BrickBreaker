package Objects;

public class PowerUpDefault extends PowerUp {
    PowerUpDefault(){
        super();
        PowerUpName = "default";
        path ="src/PNG/59-Breakout-Tiles.png";
        loadImage();
        getImageDimensions();
    }
}
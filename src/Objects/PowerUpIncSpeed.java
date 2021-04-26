package Objects;

public class PowerUpIncSpeed extends PowerUp {
    PowerUpIncSpeed(){
        super();
        PowerUpName = "fast";
        path ="src/PNG/42-Breakout-Tiles.png";
        loadImage();
        getImageDimensions();
    }
}
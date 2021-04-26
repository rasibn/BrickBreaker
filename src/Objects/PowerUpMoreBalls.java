package Objects;

public class PowerUpMoreBalls extends PowerUp {
    PowerUpMoreBalls(){
        super();
        PowerUpName = "moreball";
        path ="src/PNG/43-Breakout-Tiles.png";
        loadImage();
        getImageDimensions();
    }
}

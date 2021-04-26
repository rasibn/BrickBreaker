package Objects;

public class PowerUpExtraLife extends PowerUp {
    PowerUpExtraLife(){
        super();
        PowerUpName = "extralife";
        path ="src/PNG/60-Breakout-Tiles.png";
        loadImage();
        getImageDimensions();
        }
}
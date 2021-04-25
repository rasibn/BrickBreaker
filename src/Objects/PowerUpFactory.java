package Objects;
public class PowerUpFactory {

	public PowerUp getPowerUp(int name, int x,int y)
	{
        PowerUp power = null;
		switch(name)
		{
			case 0:
				power =new PowerUpdecSize();
				break;
			case 1:
				power =new PowerUpIncSize();
				break;
			case 2:
				power =new PowerUpIncSpeed();
				break;
			case 3:
				power =new PowerUpdecSpeed();
				break;
			case 4:
				power =new PowerUpExtraLife();
				break;
			case 5:
				power = new PowerUpRedBall();
				break;
			case 6:
				power =new PowerUpMoreBalls();
				break;
			case 7:
				power =new PowerUpFire();
				break;
			case 8:
				power = new PowerUpDefault();
			default:
				System.out.println("Invalid Power Up.");
		}
		power.setX(x);
		power.setY(y);
		return power;
	}
}

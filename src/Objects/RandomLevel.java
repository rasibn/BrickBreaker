package Objects;

import Main.Commons;

public class RandomLevel 
{
	private Brick[] bricks;
    private int[][] rand = new int[5][6];
    private BrickFactory factory = new BrickFactory();
    
    public RandomLevel()
    {
    	
    }
    public void Generate()
    {
    	bricks = new Brick[Commons.N_OF_BRICKS];
    	int k = 0;
    	
    	for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 6; j++) {
            	rand[i][j] = ((int)(Math.random()*7)) + 1;
            	
            	//following code removes possibility of deadlock
            	if (i > 0)  
	            {
	            	if (i > 1)
                	{
                		if (i == 4)
                    	{
                    		rand[i][j] = ((int)(Math.random()*6)) + 1;
                    	}
                		if (rand[i][j] == 7 && (rand[i-2][j] == 7 || rand[i-1][j] == 7))
    	                {
    	                	rand[i][j] = ((int)(Math.random()*6)) + 1;
    	                }
                	}
	            	
                	if (j > 0)
    	            {
    	            	if (rand[i][j] == 7 && (rand[i][j-1] == 7 || rand[i-1][j-1] == 7))
    	                {
    	                	rand[i][j] = ((int)(Math.random()*6)) + 1;
    	                }
    	            }
	            }
                bricks[k] = factory.getBrick(j * 145 + 120, i *50 + 50, rand[i][j]);
                k++;
            }
        }
    	
    }
    public Brick[] getBricks(){
        return bricks;
    }
}

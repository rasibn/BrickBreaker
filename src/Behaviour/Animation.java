package Behaviour;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Animation {
	private final ArrayList<String> ImagesPaths;
	private int count;
	private int CurrentImageNum = 0;
	
	public Animation(){
		ImagesPaths = new ArrayList<>();
	}
	public void addToAnimationCycle(String Path){
		ImagesPaths.add(Path);
	}
	
	public void clearImagesFromCycle() {
		ImagesPaths.clear();
	}
	
	public Image CycleImageWithDelay(int Delay) {
		count++;
		
		if(count > Delay) {
			count=0;
			CurrentImageNum++;
		}
		
		if( CurrentImageNum >= ImagesPaths.size()) {
			CurrentImageNum = 0;
		}
		
		var ii = new ImageIcon(ImagesPaths.get(CurrentImageNum));
		return ii.getImage();
	}
}
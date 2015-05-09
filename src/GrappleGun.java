import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GrappleGun implements Weapon{

	private int hitX, hitY, reloadSpeed, fireSpeed, clipSize;
	private int reloadCounter;
	private boolean firing, reloading;
	private BufferedImage gunImage;
	
	GrappleGun(){
		reloadSpeed = 500;
		fireSpeed = 40;
		clipSize = 1;

		firing = false;
		reloading = false;
		
//		try {
//			gunImage = ImageIO.read(DisplayPanel.class.getResource("/src/graphics/Enemy1.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
	
	@Override
	public void fire() {
		firing = true;
	}

	@Override
	public void reload() {
		reloading = true;
		
	}

	@Override
	public int getFireSpeed() {
		return fireSpeed;
	}

	@Override
	public int getHitX() {
		return hitX;
	}
	public void setHitX(int x){
		hitX=x;
	}

	@Override
	public int getHitY() {
		return hitY;
	}
	public void setHitY(int y){
		hitY=y;
	}

	@Override
	public void incrementReloadCounter() {
		reloadCounter++;		
	}

	@Override
	public BufferedImage getImage() {
		return gunImage;		
	}

}

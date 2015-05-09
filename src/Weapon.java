import java.awt.image.BufferedImage;


public interface Weapon {
	
	public void fire();
	public void reload();
	public int getFireSpeed();
	public int getHitX();
	public int getHitY();
	public void setHitX(int x);
	public void setHitY(int y);
	public void incrementReloadCounter();
	public BufferedImage getImage();

}

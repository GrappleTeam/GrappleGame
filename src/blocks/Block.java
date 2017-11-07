package blocks;

public interface Block {

	public String getType();
	public void setType(String s);
	
	public boolean withinBounds(int testX, int testY, int testWidth, int testHeight);
	
	public int getX();
	public int getY();
	public int getWidth();
	public int getHeight();
	public String getImageString();
	
	public void setX(int x);
	public void setY(int y);
	public void setWidth(int width);
	public void setHeight(int height);
	
	public boolean isPlatform();
	
}

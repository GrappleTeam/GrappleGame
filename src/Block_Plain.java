public class Block_Plain implements Block{
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	private String type = "standard";
	private String imageString = "graphics/BrickBlock01.png";

	
	Block_Plain(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	Block_Plain(int x, int y, Block_Plain r){
		this.x = x;
		this.y = y;
		this.width = r.width;
		this.height = r.height;
	}
	
	public String getType(){return type;}
	public void setType(String s){type = s;}
	
	public boolean withinBounds(int testX, int testY, int testWidth, int testHeight){
		if(x<testX+testWidth && testX<x+width && y<testY+testHeight && testY<y+height ){
			return true;
		}
		return false;
	}
	
	public int getX(){return x;}
	public int getY(){return y;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public String getImageString(){return imageString;}
	
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	public void setWidth(int width){this.width = width;}
	public void setHeight(int height){this.height = height;}

	public boolean isPlatform() {
		return false;
	}
	
}

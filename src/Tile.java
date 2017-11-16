public class Tile {

	private String image;
	private int x,y;

	Tile(int x, int y, String s){
		this.x = x;
		this.y = y;
		this.image = s;
	}

	public int getX(){return x;}
	public int getY(){return y;}
	public String getImage(){return image;}

	public void setX(int x){this.x=x;}
	public void setY(int y){this.y=y;}
	public void setImage(String s){this.image=s;}

}

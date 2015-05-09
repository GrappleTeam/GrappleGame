public class Block_Platform extends Block_Plain{
	private String type = "platformblock";
	private String imageString = "graphics/PlatformBlock01.png";
	
	Block_Platform(int x, int y, int width, int height){
		super(x, y, width, height);
	}
	
	@Override
	public String getImageString(){return imageString;}
	@Override
	public String getType(){return type;}
	@Override
	public void setType(String s){type = s;}
	@Override
	public boolean isPlatform(){
		return true;
	}
}

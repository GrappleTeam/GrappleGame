public class Block_Slow extends Block_Plain{

	private String type = "slowblock";
	private String imageString = "graphics/SlowBlock01.png";
	
	Block_Slow(int x, int y, int width, int height){
		super(x, y, width, height);
	}
	@Override
	public String getImageString(){return imageString;}
	@Override
	public String getType(){return type;}
	@Override
	public void setType(String s){type = s;}
}

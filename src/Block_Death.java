public class Block_Death extends Block_Plain{

	private String type = "deathblock";
	private String imageString = "graphics/DeathBlock01.png";
	
	Block_Death(int x, int y, int width, int height){
		super(x, y, width, height);
	}
	@Override
	public String getImageString(){return imageString;}
	@Override
	public String getType(){return type;}
	@Override
	public void setType(String s){type = s;}
}

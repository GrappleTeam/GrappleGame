package blocks;

public class BlockDeath extends BlockPlain {

	private String type = "deathblock";
	private String imageString = "graphics/DeathBlock01.png";
	
	public BlockDeath(int x, int y, int width, int height){
		super(x, y, width, height);
	}
	@Override
	public String getImageString(){return imageString;}
	@Override
	public String getType(){return type;}
	@Override
	public void setType(String s){type = s;}
}

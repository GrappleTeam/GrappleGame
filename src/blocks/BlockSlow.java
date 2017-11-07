package blocks;

public class BlockSlow extends BlockPlain {

	private String type = "slowblock";
	private String imageString = "graphics/SlowBlock01.png";
	
	public BlockSlow(int x, int y, int width, int height){
		super(x, y, width, height);
	}
	@Override
	public String getImageString(){return imageString;}
	@Override
	public String getType(){return type;}
	@Override
	public void setType(String s){type = s;}
}

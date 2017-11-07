package blocks;

public class BlockPortal extends BlockPlain {

	private String type = "portal";
	private String imageString = "graphics/Portal01.png";
	
	public BlockPortal(int x, int y, int width, int height){
		super(x, y, width, height);
	}
	@Override
	public String getImageString(){return imageString;}
	@Override
	public String getType(){return type;}
	@Override
	public void setType(String s){type = s;}
}

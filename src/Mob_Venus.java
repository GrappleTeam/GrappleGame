
public class Mob_Venus extends Mob{
	
	private static final int damage = 3;
	private static final int health = 5;
	private int mod = 2;
	private int tic = 0;
	
	Mob_Venus(int x, int y){
		super("Venus", damage, health, x, y);
		this.sprite = ImageUtils.getImage("graphics/Plant02.png");
	}
	
	Mob_Venus(int damage, int health, int x, int y){
		super("Venus", damage, health, x, y);
		this.sprite = ImageUtils.getImage("graphics/Plant02.png");
	}
	
	@Override
	public void pattern()
	{
			if(tic>50){
				mod = -mod;
				tic = 0;
			}
			setXspeed(mod);
			move();
			tic++;
	}
}



public class Mob_ChuChu extends Mob{
	
	private static final int damage = 3;
	private static final int health = 5;
	private int speedmod = 4;
	private int tic = 0;
	
	Mob_ChuChu(int x, int y){
		super("ChuChu", damage, health, x, y);
		this.sprite = getImage("graphics/Sprout01.png");
	}
	
	Mob_ChuChu(int damage, int health, int x, int y){
		super("ChuChu", damage, health, x, y);
		this.sprite = getImage("graphics/Sprout01.png");
	}
	
	@Override
	public void pattern()
	{
			jump();
			if(tic>100){
				speedmod = -speedmod;
				tic = 0;
			}
			setXspeed(speedmod);
			move();
			tic++;
	}
}


public class Mob_Player extends Mob{
	
	Mob_Player(int damage, int health, int x, int y){
		super("Player", damage, health, x, y);
		this.sprite = getImage("graphics/girl10.png");
	}
}

package data;

// Represents when your unit damages another unit
public class DamageEvent extends ActionEvent
{
	int damage;
	
	public DamageEvent(int player, int time, int damage) 
	{
		super(player, time);
		this.damage = damage;
		
	}

	public int getDamage()
	{
		return damage;
	}
}

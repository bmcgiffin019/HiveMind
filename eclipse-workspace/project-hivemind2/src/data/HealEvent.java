package data;

// Represents when your unit damages another unit
public class HealEvent extends ActionEvent
{
	int heal;
	
	public HealEvent(int player, int time, int damage) 
	{
		super(player, time);
		this.heal = damage;
		
	}

	public int getHeal()
	{
		return heal;
	}
}

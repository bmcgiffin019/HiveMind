package data;

// Represents when your unit damages another unit
public class DamageAttemptEvent extends ActionEvent
{
	int damageAttempt;
	
	public DamageAttemptEvent(int player, int time, int damage) 
	{
		super(player, time);
		this.damageAttempt = damage;
		
	}

	public int getDamageAttempt()
	{
		return damageAttempt;
	}
}

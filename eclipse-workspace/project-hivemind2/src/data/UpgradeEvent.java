package data;

// Represents when your unit damages another unit
public class UpgradeEvent extends StateEvent
{
	int value;
	
	public UpgradeEvent(int player, int time, int value) 
	{
		super(player, time);
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}
}

package data;

// Represents when your unit damages another unit
public class BuildEvent extends StateEvent
{
	int value;
	
	public BuildEvent(int player, int time, int value) 
	{
		super(player, time);
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}
}

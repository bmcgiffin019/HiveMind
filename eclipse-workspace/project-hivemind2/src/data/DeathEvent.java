package data;

import org.newdawn.slick.geom.Point;

// Represents when your unit damages another unit
public class DeathEvent extends StateEvent
{
	int value;
	Point location;
	
	public DeathEvent(int player, int time, int value, Point location) 
	{
		super(player, time);
		this.value = value;
		this.location = location;
	}

	public int getValue()
	{
		return value;
	}
	
	public Point getLocation()
	{
		return location;
	}
}

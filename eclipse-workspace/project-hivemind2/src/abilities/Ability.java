package abilities;


import org.newdawn.slick.geom.Point;

import objects.units.Unit;

public class Ability 
{
	protected Unit owner;
	protected int cooldown;
	protected int charges;
	protected int energy;
	protected int timer;
	
	public Ability(Unit owner) 
	{
		this.owner = owner;
		cooldown = -1;
		charges = -1;
		energy = -1;
		timer = 0;
	}
	
	public void use()
	{
		
	}
	
	public void use(Unit u)
	{
		
	}
	
	public void use(Point p)
	{
		
	}
	
	public void use(int x, int y)
	{
		
	}
	
	public void update()
	{
		if(cooldown != -1)
		{
			if(timer > 0)
			{
				timer--;
			}
	
		}
	}
	
	public int getTimer()
	{
		return timer;
	}

	
}

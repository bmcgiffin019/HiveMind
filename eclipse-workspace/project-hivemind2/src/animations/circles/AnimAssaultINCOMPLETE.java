package animations.circles;

import org.newdawn.slick.Color;

import core.Values;

public class AnimAssaultINCOMPLETE extends AnimationCircle
{
	boolean upgraded;
	
	public AnimAssaultINCOMPLETE(float x, float y, int team, boolean upgraded) 
	{
		super(x, y, 30, team);
		this.upgraded = upgraded;
	}

	protected int getRadius() 
	{
		if(upgraded) return (int) (Values.ASSAULT_ATTACK_SPLASH_RADIUS * Values.ASSAULT_UPGRADE_EXPLOSIVE_SPLASH_RADIUS_MOD);
		else return Values.ASSAULT_ATTACK_SPLASH_RADIUS;
	}
	
	protected int getBorderWidth() 
	{
		return 0;
	}

	protected Color getBorderColor() 
	{
		return null;
	}

	protected Color getFillColor() 
	{
		if(team == Values.BLUE_ID)
		{
			return new Color(50, 100, 255, getFadeAlphaValue());
		}
		else				
		{
			return new Color(255, 100, 50, getFadeAlphaValue());
		}
	}

}

package animations.circles;

import org.newdawn.slick.Color;

import core.Values;

public class AnimMissile extends AnimationCircle
{

	public AnimMissile(float x, float y, int team) 
	{
		super(x, y, 60, team);
	}

	protected int getRadius() 
	{
		return Values.RAIDER_UPGRADE_MISSILE_EFFECT_RADIUS;
	}
	
	protected int getBorderWidth() 
	{
		return 5;
	}

	protected Color getBorderColor() 
	{
		if(team == Values.BLUE_ID)
		{
			return new Color(70, 70, 90, getFadeAlphaValue() / 4);
		}
		else				
		{
			return new Color(90, 70, 70, getFadeAlphaValue() / 4);
		}
	}

	protected Color getFillColor() 
	{
		if(team == Values.BLUE_ID)
		{
			return new Color(20, 20, 60, getFadeAlphaValue());
		}
		else				
		{
			return new Color(60, 20, 20, getFadeAlphaValue());
		}
	}

}

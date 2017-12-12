package animations.circles;

import org.newdawn.slick.Color;

import core.Values;

public class AnimEMP extends AnimationCircle
{
	int maxRadius;

	public AnimEMP(float x, float y, int team, int radius) 
	{
		super(x, y, 45, team);
		this.maxRadius = radius;
	}

	protected int getRadius() 
	{
		return (int) (percentComplete() * maxRadius);
	}
	


	protected int getBorderWidth() 
	{
		return 5;
	}

	protected Color getBorderColor() 
	{
		if(team == Values.BLUE_ID)
		{
			return new Color(120, 200, 255, getFadeAlphaValue());
		}
		else				
		{
			return new Color(255, 200, 120, getFadeAlphaValue());
		}
	}

	protected Color getFillColor() 
	{
		if(team == Values.BLUE_ID)
		{
			return new Color(100, 160, 255, getFadeAlphaValue());
		}
		else				
		{
			return new Color(255, 160, 100, getFadeAlphaValue());
		}
	}

}

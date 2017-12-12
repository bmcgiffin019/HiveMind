package animations.circles;

import org.newdawn.slick.Color;

import core.Values;

public class AnimFlare extends AnimationCircle
{
	int maxRadius;

	public AnimFlare(float x, float y, int radius) 
	{
		super(x, y, Values.STAR_SOLAR_FLARE_DURATION, -1);
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
		
			return new Color(255, (int) (100 - (percentLeft() * 100)), 0, getFadeAlphaValue());
	}

	protected Color getFillColor() 
	{

		return new Color(255, (int) (125 - (percentLeft() * 100)), 0, getFadeAlphaValue());
		
	}
	
	public int getFadeAlphaValue()
	{
		return (int) (255.0f * percentLeft());
	}

}

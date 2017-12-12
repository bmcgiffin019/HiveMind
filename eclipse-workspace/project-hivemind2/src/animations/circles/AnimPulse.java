package animations.circles;

import org.newdawn.slick.Color;

import core.Values;

public class AnimPulse extends AnimationCircle
{
	int maxRadius;

	public AnimPulse(float x, float y, int radius) 
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
		int white = (int) (200 - (percentLeft() * 200));
		return new Color(white, white, 255, getFadeAlphaValue());
	}

	protected Color getFillColor() 
	{
		int white = (int) (255 - (percentLeft() * 200));
		return new Color(white, white, 255, getFadeAlphaValue());
		
	}
	
	public int getFadeAlphaValue()
	{
		return (int) (255.0f * percentLeft());
	}

}

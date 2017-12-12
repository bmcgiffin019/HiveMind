package animations.circles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.Values;

public class AnimRift extends AnimationCircle
{
	
	int shade = 10;

	public AnimRift(float x, float y, int team) 
	{
		super(x, y, Values.SPECIALIST_RIFT_DURATION, team);
	}

	public void render(Graphics g) 
	{
		super.render(g);
		
		if(percentComplete() < .10)
		{
			shade = (int) (percentComplete() * 10);
		}
		else if(percentComplete() > .90)
		{
			shade = 14 - (int) (percentComplete() * 10);

		}
		else
		{
			shade = 14;
		}
	}
	
	protected int getRadius() 
	{
		return Values.SPECIALIST_RIFT_RADIUS;
	}

	protected int getBorderWidth() 
	{
		return 4;
	}
	
	protected void drawBorder(Graphics g)
	{
		g.setColor(getBorderColor());
		
		g.setLineWidth(getBorderWidth());
		g.drawOval(x- getRadius() * .25f, y - getRadius() * .25f, getRadius()*2 * .25f, getRadius()*2 * .25f);

		g.setLineWidth(getBorderWidth()-1);
		g.drawOval(x- getRadius() * .50f, y - getRadius() * .50f, getRadius()*2 * .50f, getRadius()*2 * .50f);
		
		g.setLineWidth(getBorderWidth()-2);
		g.drawOval(x- getRadius() * .75f, y - getRadius() * .75f, getRadius()*2 * .75f, getRadius()*2 * .75f);

		
		g.setLineWidth(getBorderWidth()-3);
		g.drawOval(x- getRadius()       , y - getRadius()       , getRadius()*2       , getRadius()*2      );
	

		g.resetLineWidth();
	}
	
	protected void drawFill(Graphics g)
	{
		g.setColor(getBorderColor());
		
		g.fillOval(x- getRadius() * .25f, y - getRadius() * .25f, getRadius()*2 * .25f, getRadius()*2 * .25f);
		g.fillOval(x- getRadius() * .50f, y - getRadius() * .50f, getRadius()*2 * .50f, getRadius()*2 * .50f);
		g.fillOval(x- getRadius() * .75f, y - getRadius() * .75f, getRadius()*2 * .75f, getRadius()*2 * .75f);
		g.fillOval(x- getRadius()       , y - getRadius()       , getRadius()*2       , getRadius()*2      );

	}
	protected Color getBorderColor() 
	{
		if(team == Values.BLUE_ID)
		{
			return new Color(80, 60, 160, shade);
		}
		else				
		{
			return new Color(160, 60, 80, shade);
		}
	}

	protected Color getFillColor() 
	{		
		if(team == Values.BLUE_ID)
		{
			return new Color(70, 50, 160, shade);
		}
		else				
		{
			return new Color(160, 50, 70, shade);
		}
	}

}

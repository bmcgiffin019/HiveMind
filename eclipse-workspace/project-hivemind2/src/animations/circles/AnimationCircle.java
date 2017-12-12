package animations.circles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import animations.Animation;

public abstract class AnimationCircle extends Animation
{
	int team;
	
	public AnimationCircle(float x, float y, int duration, int team) 
	{
		super(x, y, duration);
		this.team = team;
	}

	public void render(Graphics g) 
	{
		if(hasBorder())
		{
			drawBorder(g);
		}
		
		drawFill(g);


	}
	
	protected void drawBorder(Graphics g)
	{
		g.setLineWidth(getBorderWidth());
		g.setColor(getBorderColor());
		g.drawOval(x- getRadius(), y- getRadius(), getRadius()*2, getRadius()*2);
		g.resetLineWidth();
	}
	
	protected void drawFill(Graphics g)
	{
		g.setColor(getFillColor());
		g.fillOval(x - getRadius(), y - getRadius(), getRadius()*2, getRadius()*2);
	}
	
	private boolean hasBorder()
	{
		return getBorderWidth() > 0 && getBorderColor() != null;
	}
	
	abstract protected int getRadius();
	abstract protected int getBorderWidth();
	abstract protected Color getBorderColor();
	abstract protected Color getFillColor();

	

}

package animations;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.Utility;
import core.Values;

public class MissileBoom extends Animation 
{
	Image image;
	float w;
	float h;
	Color c;
	int darken;
	boolean isBurst;
	int team;
	
	public MissileBoom(float x, float y, int d, float size, int team) 
	{
		super(x, y, d);

		this.w = size * 2;
		this.h = size * 2;
		this.x = x;
		this.y = y;
		this.x = x + Utility.random(-w / 5, w / 5) - 3;
		this.y = y + Utility.random(-h / 5, h / 5) - 3;
		this.team = team;
	}

	public void render(Graphics g) 
	{
		if (ticks > duration)
			return;
		

		float opacityPercent = 1 - ((float) ticks) / ((float) duration);
		int fadeAlpha = (int) (255.0f * opacityPercent);
		
		//border
		g.setLineWidth(5);
		g.setColor(new Color(150, 150, 150, fadeAlpha/4));
		g.drawOval(x - w / 2, y - h / 2, w, h);
		g.resetLineWidth();

		
		// inside
		if (team == Values.BLUE_ID)
			g.setColor(new Color(20, 20, 35, fadeAlpha));
		else if (team == Values.RED_ID)
			g.setColor(new Color(35, 20, 20, fadeAlpha));
		
	
		g.fillOval(x - w / 2, y - h / 2, w, h);

	}
}
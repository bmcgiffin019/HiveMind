package animations;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.Utility;
import ui.Fonts;

public class AnimationSpark extends Animation
{
	int red;
	int green;
	int blue;
	
	int size = 10;
	
	float xSpeed;
	float ySpeed;
	float xAcc;
	float yAcc;
	
	public AnimationSpark(float x, float y, int duration, int size, int r, int g, int b) 
	{
		super(x, y, duration);
		this.red = r;
		this.green = g;
		this.blue = b;
		this.size = size;
		xSpeed = Utility.random(-1.5, 1.5);
		xAcc = Utility.random(-.1, .1);
		ySpeed = Utility.random(-1.5, 1.5);
		yAcc = Utility.random(-.1, .1);

	}

	
	public void update()
	{
		super.update();
		x += xSpeed;
		y += ySpeed;
		
		xSpeed += xAcc;
		ySpeed += yAcc;
	}
	
	public void render(Graphics g) 
	{
		g.setFont(Fonts.arialblack36);
		g.setColor(new Color(red, green, blue, getFadeAlphaValue()));
		g.fillOval(x, y, size, size);

	}

}

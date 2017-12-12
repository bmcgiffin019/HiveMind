package animations;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import ui.Fonts;

public class AnimationText extends Animation
{
	String text;
	int red;
	int green;
	int blue;
	final static int FLOAT_TEXT_DURATION = 240;
	
	public AnimationText(float x, float y, String text, int r, int g, int b) 
	{
		super(x, y, FLOAT_TEXT_DURATION);
		this.text = text;
		this.red = r;
		this.green = g;
		this.blue = b;
	}

	
	public void update()
	{
		super.update();
		y--;	
	}
	
	public void render(Graphics g) 
	{
		g.setFont(Fonts.arialblack36);
		g.setColor(new Color(red, green, blue, getFadeAlphaValue()));

		int len = Fonts.arialblack36.getWidth(text);
		g.drawString(text, x - len / 2, y);
	}

}

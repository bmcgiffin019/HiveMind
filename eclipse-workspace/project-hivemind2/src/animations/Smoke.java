package animations;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.Utility;

public class Smoke extends Animation {
	Image image;

	public static final int SMOKE_SIZE = 12;
	int greyscale;
	
	float w;
	float h;

	public Smoke(float x, float y, float size) {
		super(x, y, 50);

		this.w = SMOKE_SIZE * size;
		this.h = SMOKE_SIZE * size;
		this.x = x - w / 2 + Utility.random(-w / 3, w / 3);
		this.y = y - h / 2 + Utility.random(-h / 3, h / 3);
		greyscale = 175;
	}

	public void render(Graphics g) {

		float percent = 1 - ((float) ticks) / ((float) duration);
		float width = w - (w * percent * .75f);
		float height = h - (h * percent * .75f);

		g.setColor(new Color(greyscale, greyscale, greyscale, 75));
		g.fillOval(x, y, width, height);

	}
}
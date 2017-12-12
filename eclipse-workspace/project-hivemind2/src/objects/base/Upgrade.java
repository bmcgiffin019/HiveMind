package objects.base;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.Values;
import ui.Images;

public class Upgrade 
{
	protected Image icon;
	private Image frame;
	protected int x;
	protected int y;

	protected Upgrade() {
		frame = Images.upgrades.getSprite(0, 0);
	}

	protected void setImage()
	{
		icon = Images.upgrades.getSprite(x, y);
	}

	public void render(Graphics g, int x, int y, int team) 
	{
		if (team == Values.BLUE_ID)
			g.drawImage(icon, x, y, new Color(50, 160, 255, 200));
		if (team == Values.RED_ID)
			g.drawImage(icon, x, y, new Color(255, 50, 25, 200));

		g.drawImage(frame, x, y, new Color(160, 160, 160, 150));
	}
}

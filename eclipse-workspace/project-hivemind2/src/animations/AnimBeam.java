package animations;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.Values;
import objects.units.Unit;

public class AnimBeam extends Animation {
	protected Unit origin;
	protected Unit target;
	protected int team;
	protected float width;
	protected boolean isBurst;

	public AnimBeam(Unit origin, Unit target, int width, int duration, boolean isBurst) {
		super(origin.getX(), origin.getY(), duration);

		this.origin = origin;
		this.target = target;
		team = origin.getTeam();
		this.width = width;
		this.isBurst = isBurst;
	}


	public void render(Graphics g) {
		if (ticks > duration)
			return;

		if (isBurst) {
			float opacityPercent = 1 - ((float) ticks) / ((float) duration);
			int fadeAlpha = (int) (255.0f * opacityPercent);
			int fadeWidth = (int) (width * opacityPercent);

			if (team == Values.BLUE_ID)
				g.setColor(new Color(75, 150, 255, fadeAlpha));
			else if (team == Values.RED_ID)
				g.setColor(new Color(255, 100, 100, fadeAlpha));
			g.setLineWidth(fadeWidth);
		} else {
			if (team == Values.BLUE_ID)
				g.setColor(new Color(75, 150, 255, 150));
			else if (team == Values.RED_ID)
				g.setColor(new Color(255, 100, 100, 150));
			g.setLineWidth(width);
		}


		g.drawLine(origin.getCenterX(), origin.getCenterY(), target.getCenterX(), target.getCenterY());
		g.setLineWidth(1);

		// g.fillOval(target.getXCenter(), target.getYCenter(), 100, 100);

	}

}

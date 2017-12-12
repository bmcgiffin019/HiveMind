package animations.projectiles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import animations.Animation;
import core.Values;
import objects.units.Unit;

public class Projectile extends Animation {
	protected Unit origin;
	protected Unit target;

	private float xRoll;
	private float yRoll;
	private float var = 32;

	protected float xMid;
	protected float yMid;
	protected int team;

	protected int speed;

	public Projectile(Unit origin, Unit target, int speed, int duration) {
		super(origin.getX(), origin.getY(), duration);
		ticks = 0;
		this.origin = origin;
		this.target = target;
		team = origin.getTeam();
		this.speed = speed;
	
	}

	public void update() {
		super.update();

		if (isDone())
			return;

		// Add in some variance
		float targetX = (float) (target.getCenterX() - var / 2 + xRoll * var);
		float targetY = (float) (target.getCenterY() - var / 2 + yRoll * var);

		// Calculate Current X Position
		float xDiff = Math.abs(origin.getCenterX() - targetX);
		if (origin.getCenterX() > targetX)
			xDiff *= -1;
		xMid = origin.getCenterX() + xDiff * (float) ticks / (float) duration;

		// Calculate Current Y Position
		float yDiff = Math.abs(origin.getCenterY() - targetY);
		if (origin.getCenterY() > targetY)
			yDiff *= -1;
		yMid = origin.getCenterY() + yDiff * (float) ticks / (float) duration;

	}

	public void render(Graphics g) {
		if (ticks > duration)
			return;
		// else if(target.isDead()) return;
		else {
			// Color Bullets
			if (team == Values.BLUE_ID)
				g.setColor(new Color(75, 150, 255));
			else if (team == Values.RED_ID)
				g.setColor(new Color(255, 100, 100));

		}

	}

}

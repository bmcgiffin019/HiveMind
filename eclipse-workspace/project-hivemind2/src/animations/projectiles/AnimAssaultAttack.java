package animations.projectiles;

import org.newdawn.slick.Graphics;

import objects.units.Unit;

public class AnimAssaultAttack extends Projectile {

	public AnimAssaultAttack(Unit origin, Unit target, int speed, int duration) {
		super(origin, target, speed, duration);
		size = 20;

	}

	public void render(Graphics g) {
		super.render(g);
		g.fillOval(xMid, yMid, size, size);
	}

	public void update() {
		super.update();
	}
}

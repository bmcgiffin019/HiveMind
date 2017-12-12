package animations.projectiles;

import org.newdawn.slick.Graphics;

import objects.units.Unit;

public class AnimTurretAttack extends Projectile {

	public AnimTurretAttack(Unit origin, Unit target, int speed, int duration) {
		super(origin, target, speed, duration);
		size = 15;
	}

	public void render(Graphics g) {
		super.render(g);
		g.fillOval(xMid, yMid, size, size);
	}

	public void update() {
		super.update();
	}
}

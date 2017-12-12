package animations.projectiles;

import org.newdawn.slick.Graphics;

import objects.units.Unit;

public class AnimMinerAttack extends Projectile {

	public AnimMinerAttack(Unit origin, Unit target, int speed, int duration) {
		super(origin, target, speed, duration);
		size = 10;

	}

	public void render(Graphics g) {
		super.render(g);
		g.fillRect(xMid, yMid, size, size);
	}

	public void update() {
		super.update();
	}
}

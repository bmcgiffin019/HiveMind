package animations.projectiles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import animations.Animation;
import core.Values;
import objects.units.Unit;

public class AnimAssaultSplash extends Animation {
	
	Unit owner;
	float w;
	float h;
	int delay;

	public AnimAssaultSplash(Unit owner, Unit target, int delay, float size) {
		super(target.getCenterX(), target.getCenterY(), delay + 10);

		this.w = size;
		this.h = size;
		this.x = x - w / 2;
		this.y = y - h / 2;
		this.owner = owner;

	}

	public void update() {
		super.update();

		if (isDone()) {
			return;
		}

	}

	public void render(Graphics g) {
		if (ticks > delay) {
			if (owner.getTeam() == Values.BLUE_ID)
				g.setColor(new Color(75, 150, 255, 50));
			if (owner.getTeam() == Values.RED_ID)
				g.setColor(new Color(255, 100, 100, 50));

			g.fillOval(x, y, w, h);
		}

	}
}
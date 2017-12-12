package animations;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.Game;
import core.Utility;
import ui.Images;

public class Boom extends Animation {
	Image image;

	public static final int BOOM_SIZE = 20;

	float w;
	float h;

	public Boom(float x, float y, float size) {
		super(x, y, 10);
		image = Images.boom;

		this.w = image.getWidth() * size;
		this.h = image.getHeight() * size;
		this.x = x - w / 2;
		this.y = y - h / 2;
		this.size = size;
	}

	public float scatterX() {
		return x + w / 2 + Utility.random(-w / 2, w / 2);
	}

	public float scatterY() {
		return y + h / 2 + Utility.random(-h / 2, h / 2);
	}

	public void update() {
		super.update();

		if (isDone()) {
			return;
		}

		else if (ticks == duration - 1 && size >= .5) {
			Game.addAnimation(new Boom(scatterX(), scatterY(), size / 2));
			Game.addAnimation(new Boom(scatterX(), scatterY(), size / 2));
			Game.addAnimation(new Boom(scatterX(), scatterY(), size / 2));
			Game.addAnimation(new Boom(scatterX(), scatterY(), size / 2));
		}

	}

	public void render(Graphics g) {
		if (image != null) {
			image.setCenterOfRotation(image.getWidth() / 2 * size, image.getHeight() / 2 * size);
			// image.setRotation(theta);
			image.draw(x, y, size * 2);
		}
	}
}
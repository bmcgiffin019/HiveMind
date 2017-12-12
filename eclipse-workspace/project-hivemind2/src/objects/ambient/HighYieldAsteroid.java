package objects.ambient;

import org.newdawn.slick.SlickException;

import ui.Images;

public class HighYieldAsteroid extends Asteroid {

	public HighYieldAsteroid(float x, float y, float xSpeed, float ySpeed, int size) throws SlickException {
		super(x, y, xSpeed, ySpeed, size);
		sheet = Images.asteroid2;
		image = sheet.getSprite(model, 0);
		// minerals = minerals * Values.ASTEROID_HIGH_YIELD_MULTIPLIER;
	}

}

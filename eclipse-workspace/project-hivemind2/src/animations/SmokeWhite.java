package animations;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import core.Utility;

public class SmokeWhite extends Smoke {

	public SmokeWhite(float x, float y, float size) {
		super(x, y, size);

		greyscale = 225;
	}

}
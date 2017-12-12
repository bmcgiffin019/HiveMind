package objects.ambient;

import org.newdawn.slick.SlickException;

import core.Values;
import objects.base.GameObject;

public class Ambient extends GameObject {
	protected Ambient(float x, float y) throws SlickException {
		super(x, y, Values.AMBIENT_ID);
	}

	boolean isInvulnerable()
	{
		return false;
	}
}

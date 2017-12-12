package effects;

import animations.Smoke;
import core.Game;
import objects.base.Player;
import objects.units.Unit;

public class Shattered extends Effect {
	public Shattered(Unit u,  Player source, int delay, int duration) {
		super(u, source);
		this.delay = delay;
		this.duration = duration;
	}

	public boolean shattersArmor() {
		return isActive();
	}

	public void periodicEffect() {
		// temporary; for testing
		Game.addAnimation(new Smoke(u.getCenterX(), u.getCenterY(), u.getWidth() / 32));
	}
}

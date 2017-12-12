package effects;

import objects.base.Player;
import objects.units.Unit;

public class Stunned extends Effect {
	public Stunned(Unit u, Player source, int delay, int duration) {
		super(u, source);
		this.delay = delay;
		this.duration = duration;
	}

	public boolean stopsMove() {
		return isActive();
	}

	public boolean stopsAction() {
		return isActive();
	}

	public void periodicEffect() {
		u.haltMovement();
		// get a new animation!!!!
		// Game.animations.add(new Smoke(u.getXCenter(), u.getYCenter(),
		// u.getWidth()/32));
	}
}

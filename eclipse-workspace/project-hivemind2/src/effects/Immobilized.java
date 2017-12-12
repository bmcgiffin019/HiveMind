package effects;

import objects.base.Player;
import objects.units.Unit;

public class Immobilized extends Effect {
	public Immobilized(Unit u, Player source, int delay, int duration) {
		super(u, source);
		this.delay = delay;
		this.duration = duration;
	}

	public boolean stopsMove() {
		return isActive();
	}
}

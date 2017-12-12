package effects;

import objects.base.Player;
import objects.units.Unit;

public class Disabled extends Effect {
	public Disabled(Unit u, Player source, int delay, int duration) {
		super(u, source);
		this.delay = delay;
		this.duration = duration;
	}

	public boolean stopsAction() {
		return isActive();
	}
}

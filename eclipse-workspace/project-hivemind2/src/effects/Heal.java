package effects;

import objects.base.Player;
import objects.units.Unit;

public class Heal extends Effect {
	public Heal(Unit u, Player source, int delay, float healInitial) {
		super(u, source);
		hasInitial = true;
		this.delay = delay;
		this.healInitial = healInitial;
		this.duration = delay + 1;
		isPositive = true;
	}

	public Heal(Unit u, Player source, int delay, float healInitial, float healPeriodic, int duration, int tick) {
		super(u, source);
		hasInitial = true;
		this.delay = delay;
		this.healInitial = healInitial;

		hasPeriodic = true;
		this.healPeriodic = healPeriodic;
		this.duration = duration;
		this.tick = tick;

	}
}

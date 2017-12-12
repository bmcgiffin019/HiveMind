package effects;

import objects.base.Player;
import objects.units.Unit;

public class DamageArea extends Effect {
	public DamageArea(Unit u,  Player source, int delay, float damageInitial) {
		super(u, source);
		hasInitial = true;
		this.delay = delay;
		this.damageInitial = damageInitial;
		this.duration = delay + 1;

	}

	public DamageArea(Unit u,  Player source, int delay, float damageInitial, float damagePeriodic, int duration, int tick) {
		super(u, source);
		hasInitial = true;
		this.delay = delay;
		this.damageInitial = damageInitial;

		hasPeriodic = true;
		this.damagePeriodic = damagePeriodic;
		this.duration = duration;
		this.tick = tick;

	}

	public void initialEffect() {
		u.takeAreaDamage(damageInitial, source);
	}


}

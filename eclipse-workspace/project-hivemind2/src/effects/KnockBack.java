package effects;

import objects.base.Player;
import objects.units.Unit;

public class KnockBack extends Effect {
	float force;
	int angleToward;

	public KnockBack(Unit u, Player source, int delay, float force, int angleToward) {
		super(u, source);
		hasInitial = true;
		this.delay = delay;
		this.force = force;
		this.duration = delay + 1;
		this.angleToward = angleToward;

	}

	public void initialEffect() {
		int angle = (int) u.getTheta();
		u.haltMovement();
		u.turn(angleToward);
		u.reverse(force);
		u.turn(angle);
	}

}

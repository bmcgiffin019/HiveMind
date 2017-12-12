package effects;

import objects.base.Player;
import objects.units.Unit;

public class Invulnerable extends Effect
{
	public Invulnerable(Unit u, Player source, int delay, int duration) {
		super(u, source);
		hasPeriodic = true;
		this.delay = delay;
		this.duration = duration;
		this.tick = 15;
		isPositive = true;
	}

	public boolean stopsDamage()	{
		return isActive();
	}
	public void periodicEffect() {
	//	Game.animations.add(new Smoke(u.getXCenter(), u.getYCenter(), u.getWidth() / 32));
	}

}

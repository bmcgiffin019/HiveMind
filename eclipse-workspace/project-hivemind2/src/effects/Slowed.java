package effects;

import animations.Smoke;
import core.Game;
import objects.base.Player;
import objects.units.Unit;

public class Slowed extends Effect {
	public Slowed(Unit u, Player source, int delay, int duration, float amount) {
		super(u, source);
		hasPeriodic = true;
		this.delay = delay;
		this.duration = duration;
		this.tick = 15;
		this.slowValue = amount;
	}

	public boolean slowsMove() 
	{
		return isActive();
	}

	public void periodicEffect() 
	{
		Game.addAnimation(new Smoke(u.getCenterX(), u.getCenterY(), u.getWidth() / 32));
	}

}

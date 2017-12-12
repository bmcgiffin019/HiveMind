package weapons;

import core.Utility;
import effects.Damage;
import objects.units.Unit;

public abstract class Weapon {
	protected boolean active;
	protected Unit owner;
	protected float range;
	protected float damage;
	protected int cooldown;
	protected int shotTimer;
	protected int speed;
	protected int delay;

	Weapon(Unit owner) {
		this.owner = owner;
	}

	public float getRange() {
		return range;
	}
	
	public void setRange(float value)
	{
		range = value;
	}

	public void update() {
		if (shotTimer > 0 && !owner.isStunned()) {
			shotTimer--;
		}
	}

	public void activate() {
		active = true;
	}

	public boolean canShoot(Unit a) {
		return a != null && owner != null && owner.canAct() && active && shotTimer == 0
				&& owner.inRange(a);
	}

	// How many frames does it take to reach the target at my speed?
	public int getDelay(Unit a) {
		return (int) (Utility.distance(owner, a) / speed);
	}

	public boolean use(Unit a) {
		if (canShoot(a)) {
			delay = getDelay(a);

			// Basic Damage
			a.addEffect(new Damage(a, owner.getOwner(), delay, damage));

			owner.actionComplete();
			shotTimer = cooldown;
			animation(a, getDelay(a));

			return true;
		} else {
			return false;
		}

	}

	public abstract void animation(Unit a, int delay);

}

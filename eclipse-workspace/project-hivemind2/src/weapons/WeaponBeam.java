package weapons;

import effects.Damage;
import objects.units.Unit;

public abstract class WeaponBeam extends Weapon {
	WeaponBeam(Unit owner) {
		super(owner);
	}

	public boolean use(Unit a) {
		if (canShoot(a)) {
			// Basic Damage
			a.addEffect(new Damage(a, owner.getOwner(), 1, damage));
			owner.actionComplete();
			shotTimer = cooldown;
			animation(a);

			return true;
		} else {
			return false;
		}

	}

	public int getDelay(Unit a) {
		return 1;
	}

	public abstract void animation(Unit a);

	public void animation(Unit a, int delay) {
		// does nothing
	}

}

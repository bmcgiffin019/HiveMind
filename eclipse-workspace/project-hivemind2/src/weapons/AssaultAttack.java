package weapons;

import java.util.ArrayList;

import animations.projectiles.AnimAssaultAttack;
import animations.projectiles.AnimAssaultSplash;
import core.Game;
import core.Utility;
import core.Values;
import effects.DamageArea;
import effects.Slowed;
import objects.units.Unit;
import objects.upgrades.AssaultExplosive;
import ui.Audio;

public class AssaultAttack extends Weapon {
	float splashRadius;
	float splashPercent;

	public AssaultAttack(Unit owner) {
		super(owner);

		active = true;
		damage = Values.ASSAULT_ATTACK_DAMAGE;
		speed = Values.ASSAULT_ATTACK_SPEED;
		range = Values.ASSAULT_ATTACK_RANGE;
		cooldown = Values.ASSAULT_ATTACK_COOLDOWN;
		splashRadius = Values.ASSAULT_ATTACK_SPLASH_RADIUS;

	}

	public boolean use(Unit a) {
		if (canShoot(a)) {
			owner.reverse(Values.ASSAULT_ATTACK_RECOIL);
			delay = getDelay(a);
			animation(a, getDelay(a));
			owner.actionComplete();
			shotTimer = cooldown;
			Audio.blast.play(owner.getPosition(), Utility.random(.9f, 1.1f), 1);

			if (owner.getOwner().hasResearch(AssaultExplosive.class)) {
				splashRadius = Values.ASSAULT_ATTACK_SPLASH_RADIUS * Values.ASSAULT_UPGRADE_EXPLOSIVE_SPLASH_RADIUS_MOD;
			}

			ArrayList<Unit> targets = getEnemiesInSplash(a);

			if (targets != null) {
				for (Unit u : targets) {

					float percentDistance = 1 - Utility.distance(u, a) / splashRadius;
					u.addEffect(new DamageArea(u, owner.getOwner(), delay, damage * percentDistance));

					if (owner.getOwner().hasResearch(AssaultExplosive.class)) {
						u.addEffect(new Slowed(u, owner.getOwner(), delay, Values.ASSAULT_UPGRADE_EXPLOSIVE_SLOW_DURATION,
								Values.ASSAULT_UPGRADE_EXPLOSIVE_SPEED_REDUCTION));
					}
					else
					{
						if (owner.getOwner().hasResearch(AssaultExplosive.class)) {
							u.addEffect(new Slowed(u, owner.getOwner(), delay, Values.ASSAULT_SLOW_DURATION,
									Values.ASSAULT_SPEED_REDUCTION));
						}
					}
					Game.addAnimation(new AnimAssaultSplash(owner, a, delay, splashRadius));

				}
			}

			return true;
		} else
			return false;

	}

	public ArrayList<Unit> getEnemiesInSplash(Unit a) {
		return owner.getOwner().getEnemyUnitsInRadius(a.getCenterX(), a.getCenterY(), splashRadius);
	}

	public void animation(Unit a, int delay) {

		Game.addAnimation(new AnimAssaultAttack(owner, a, speed, delay));
	}

}

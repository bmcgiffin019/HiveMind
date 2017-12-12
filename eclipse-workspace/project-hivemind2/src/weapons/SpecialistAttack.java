package weapons;

import animations.AnimBeam;
import core.Game;
import core.Utility;
import core.Values;
import effects.Damage;
import effects.KnockBack;
import effects.Locked;
import effects.Stunned;
import objects.units.Miner;
import objects.units.Unit;
import objects.upgrades.SpecialistKinetic;
import ui.Audio;

public class SpecialistAttack extends WeaponBeam {
	public SpecialistAttack(Unit owner) {
		super(owner);

		active = true;
		damage = Values.SPECIALIST_ATTACK_DAMAGE;
		range = Values.SPECIALIST_ATTACK_RANGE;
		cooldown = Values.SPECIALIST_ATTACK_COOLDOWN;
	}

	public boolean use(Unit a) 
	{		
		if (canShoot(a) && owner.getCurEnergy() > Values.SPECIALIST_ATTACK_ENERGY_COST) 
		{
			
			if (owner.getOwner().hasResearch(SpecialistKinetic.class))
			{
				a.addEffect(new Damage(a, a.getOwner(), 1, damage * Values.SPECIALIST_UPGRADE_KINETIC_DAMAGE_BONUS));
			}
			else
			{
				a.addEffect(new Damage(a, a.getOwner(), 1, damage));
			}

			owner.actionComplete();
			shotTimer = cooldown;
			animation(a);
			
			owner.loseEnergy(Values.SPECIALIST_ATTACK_ENERGY_COST);
			
			Audio.laser[Utility.random(0, 2)].play(owner.getPosition(), .7f, 1f);	
			
			// owner.reverse(Values.RAIDER_ATTACK_RECOIL);
			owner.addEffect(new Locked(owner, owner.getOwner(), 1, Values.SPECIALIST_ATTACK_LOCK_TIME));
			owner.rotate((int) owner.getAngleToward(a.getCenterX(), a.getCenterY()));
			owner.haltMovement();

			// KINETIC - Knocks Back
			if (owner.getOwner().hasResearch(SpecialistKinetic.class)
					&& !((a instanceof Miner) && ((Miner) a).isMining())) {
				int angleTo = (int) a.getAngleToward(owner.getCenterX(), owner.getCenterY());
				a.addEffect(new KnockBack(a, owner.getOwner(), 1, Values.SPECIALIST_UPGRADE_KINETIC_PUSH, angleTo));
			}

			// KINETIC - Stuns
			if (owner.getOwner().hasResearch(SpecialistKinetic.class)
					&& !((a instanceof Miner) && ((Miner) a).isMining())) {
				a.addEffect(new Stunned(a, owner.getOwner(), 1, Values.SPECIALIST_UPGRADE_KINETIC_STUN_TIME));
			}
			return true;
		} else
			return false;

	}

	public void animation(Unit a) {
		Game.addAnimation(new AnimBeam(owner, a, 11, 45, true));
	}


}

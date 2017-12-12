package weapons;

import animations.AnimBeam;
import core.Game;
import core.Values;
import effects.Heal;
import objects.units.Unit;
import objects.upgrades.SupportFix;
import ui.Audio;

public class SupportHeal extends WeaponBeam {
	float heal;

	public SupportHeal(Unit owner) {
		super(owner);

		active = true;
		if(owner.getOwner().hasResearch(SupportFix.class)) {
			heal = Values.SUPPORT_UPGRADE_FIX_HEAL_AMOUNT;
		}
		else {
			heal = Values.SUPPORT_HEAL_AMOUNT;

		}
		range = Values.SUPPORT_HEAL_RANGE;
		cooldown = Values.SUPPORT_HEAL_COOLDOWN;


	}

	public boolean use(Unit a) {
		if (canShoot(a) && owner.hasEnergy(Values.SUPPORT_HEAL_ENERGY_COST)) {
			// Basic Damage
			a.addEffect(new Heal(a, owner.getOwner(), 1, heal));
			owner.loseEnergy(Values.SUPPORT_HEAL_ENERGY_COST);
			owner.actionComplete();
			Audio.heal.play(owner.getPosition());	
			shotTimer = cooldown;
			animation(a);
			
			if(owner.getOwner().hasResearch(SupportFix.class)) {
				a.reduceEffects(Values.SUPPORT_UPGRADE_FIX_EFFECT_REDUCTION);
			}
			return true;
		} else {
			return false;
		}

	}

	public void animation(Unit a) {
		// Game.animations.add(new AnimSupportHeal(owner, a, cooldown));

		Game.addAnimation(new AnimBeam(owner, a, 8, cooldown, false));
	}

}

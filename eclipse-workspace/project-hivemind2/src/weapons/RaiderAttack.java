package weapons;

import animations.projectiles.AnimRaiderAttack;
import core.Game;
import core.Values;
import effects.Damage;
import effects.DamageIgnoreArmor;
import objects.units.Unit;
import objects.upgrades.RaiderPierce;
import ui.Audio;

public class RaiderAttack extends Weapon 
{	
	public RaiderAttack(Unit owner) 
	{
		super(owner);

		active = true;
		damage = Values.RAIDER_ATTACK_DAMAGE;
		speed = Values.RAIDER_ATTACK_SPEED;
		range = Values.RAIDER_ATTACK_RANGE;
		cooldown = Values.RAIDER_ATTACK_COOLDOWN;
	}


	public void playMGSound()
	{
		final float PITCH = 1.6f;
		final float VOLUME = 1.3f;
		
		
		if(!Audio.mg[0].playing())
		{
			Audio.mg[0].play(owner.getLocation(), PITCH, VOLUME);
		}
		else if(!Audio.mg[1].playing())
		{
			Audio.mg[1].play(owner.getLocation(), PITCH, VOLUME);
		}
		else if(!Audio.mg[2].playing())
		{
			Audio.mg[2].play(owner.getLocation(), PITCH, VOLUME);
		}
		else if(!Audio.mg[3].playing())
		{
			Audio.mg[3].play(owner.getLocation(), PITCH, VOLUME);
		}
		else if(!Audio.mg[4].playing())
		{
			Audio.mg[4].play(owner.getLocation(), PITCH, VOLUME);
		}
	}
	
	public boolean use(Unit a) 
	{
		if (canShoot(a)) 
		{
			owner.reverse(Values.RAIDER_ATTACK_RECOIL);
			playMGSound();
			
			delay = getDelay(a);

			if (owner.getOwner().hasResearch(RaiderPierce.class))
			{
				float targetArmor = a.getCurArmor() * (1.0f - Values.RAIDER_UPGRADE_PIERCE_PERCENT);
				float actualDamage = damage * Values.RAIDER_UPGRADE_PIERCE_DAMAGE - targetArmor;
				
				if(actualDamage > 0)
				{
					a.addEffect(new DamageIgnoreArmor(a, owner.getOwner(), delay, actualDamage));
				}
			}
			else
			{
				a.addEffect(new Damage(a, owner.getOwner(), delay, damage));
			}
			
			// Basic Damage

			owner.actionComplete();
			shotTimer = cooldown;
			animation(a, getDelay(a));

			return true;
		} else {
			return false;
		}


	}

	public void animation(Unit a, int delay) {
		Game.addAnimation(new AnimRaiderAttack(owner, a, speed, delay));
	}

}

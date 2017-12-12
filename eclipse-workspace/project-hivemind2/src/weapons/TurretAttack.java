package weapons;

import animations.projectiles.AnimTurretAttack;
import core.Game;
import core.Values;
import objects.units.Unit;
import ui.Audio;

public class TurretAttack extends Weapon {

	public TurretAttack(Unit owner) {
		super(owner);

		active = true;
		damage = Values.TURRET_ATTACK_DAMAGE;
		speed = Values.TURRET_ATTACK_SPEED;
		range = Values.TURRET_ATTACK_RANGE;
		cooldown = Values.TURRET_ATTACK_COOLDOWN;

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

	public void animation(Unit a, int delay) {
		Game.addAnimation(new AnimTurretAttack(owner, a, speed, delay));
		playMGSound();
	}

}

package weapons;

import animations.AnimBeam;
import core.Game;
import core.Utility;
import core.Values;
import objects.units.Unit;
import ui.Audio;


public class MinerAttack extends WeaponBeam {
	float heal;

	public MinerAttack(Unit owner) 
	{
		super(owner);
		active = false;
		damage = Values.MINER_ATTACK_DAMAGE;
		speed = Values.MINER_ATTACK_SPEED;
		range = Values.MINER_ATTACK_RANGE;
		cooldown = Values.MINER_ATTACK_COOLDOWN;

	}

	public boolean use(Unit a) {
		if (super.use(a)) 
		{
			owner.actionComplete();
			shotTimer = cooldown;
			animation(a);
			Audio.laser[Utility.random(0, 2)].play(owner.getPosition(), 1.5f, .6f);	

			return true;

		} 
		else {
			return false;
		}
	}

	public void animation(Unit a) 
	{
		Game.addAnimation(new AnimBeam(owner, a, 5, 60, true));
	}

}
package abilities;


import animations.circles.AnimMissile;
import core.Game;
import core.Values;
import effects.DamageArea;
import objects.units.Unit;


final public class MissileExplosion extends AbilityArea
{	
	public MissileExplosion(Unit owner) 
	{
		super(owner);
		charges = 1;
	}

	public void use()
	{
		if(charges > 0)
		{
			super.use(owner.getLocation());
			charges--;

			if(owner != null)
			{
				owner.die();
				owner.actionComplete();
				animation();
			}
		}
	}


	public void hit(Unit u)
	{
		u.addEffect(new DamageArea(u, owner.getOwner(), 1, Values.RAIDER_UPGRADE_MISSILE_DAMAGE));
	}

	public void animation()
	{
		Game.addAnimation(new AnimMissile(owner.getCenterX(), owner.getCenterY(), owner.getTeam()));
	}

	public int getRadius() 
	{
		return Values.RAIDER_UPGRADE_MISSILE_EFFECT_RADIUS;
	}


}

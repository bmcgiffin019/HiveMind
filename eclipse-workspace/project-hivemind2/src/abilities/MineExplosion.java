package abilities;


import java.util.ArrayList;

import animations.circles.AnimMine;
import core.Game;
import core.Values;
import effects.DamageArea;
import objects.units.Mine;
import objects.units.Unit;

final public class MineExplosion extends AbilityArea
{	
	public MineExplosion(Unit owner) 
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
				ArrayList<Mine> mines =  owner.getMinesInRadius(getCenter().getX(), getCenter().getY(), getRadius());

				if(mines != null)
				{

					for(int i = 0; i < mines.size(); i++)
					{
						Mine m = (Mine) mines.get(i);

						if(m != null && m.isAlive())
						{
							m.die();
						}
					}
				}

				owner.die();
				owner.actionComplete();
				animation();
			}
		}

	}


	public void hit(Unit u)
	{
		u.addEffect(new DamageArea(u, owner.getOwner(), 1, Values.MINER_UPGRADE_MINE_DAMAGE_VALUE));
	}

	public void animation()
	{
		Game.addAnimation(new AnimMine(owner.getCenterX(), owner.getCenterY(), owner.getTeam()));
	}

	public int getRadius() 
	{
		return Values.MINER_UPGRADE_MINE_DAMAGE_RADIUS;
	}


}

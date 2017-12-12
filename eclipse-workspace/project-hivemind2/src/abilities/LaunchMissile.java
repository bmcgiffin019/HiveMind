package abilities;

import core.Values;
import objects.units.Unit;

public class LaunchMissile extends Ability 
{
	public LaunchMissile(Unit owner)
	{
		super(owner);
		charges = Values.RAIDER_UPGRADE_MISSILE_CHARGES;
	}

	public void use(Unit u)
	{	
		if(charges > 0 && owner.getDistance(u) < Values.RAIDER_UPGRADE_MISSILE_RANGE)
		{
			owner.getOwner().spawnMissile(owner, u);
			charges--;
		}
	}
	
	public boolean hasMissile()
	{
		return charges > 0;
	}


}

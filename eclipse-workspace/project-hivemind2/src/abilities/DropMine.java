package abilities;


import org.newdawn.slick.geom.Point;

import core.Values;
import objects.units.Miner;

public class DropMine extends Ability
{

	public DropMine(Miner owner) 
	{
		super(owner);
		cooldown = Values.MINER_UPGRADE_MINE_DROP_COOLDOWN;
	}

	public void use()
	{
		if(timer == 0 && ((Miner)owner).hasMinerals(Values.MINER_UPGRADE_MINE_DROP_MINERAL_COST))
		{
			owner.getOwner().spawnMine(owner.getCenterX(), owner.getCenterY(), owner.getCenterX(), owner.getCenterY());
			((Miner)owner).loseMinerals(Values.MINER_UPGRADE_MINE_DROP_MINERAL_COST);
			timer = cooldown;
		}
	}
	
	public void use(Point loc)
	{
		if(timer == 0 && ((Miner)owner).hasMinerals(Values.MINER_UPGRADE_MINE_DROP_MINERAL_COST))
		{
			owner.getOwner().spawnMine(owner.getCenterX(), owner.getCenterY(), loc.getCenterX(), loc.getCenterY());
			((Miner)owner).loseMinerals(Values.MINER_UPGRADE_MINE_DROP_MINERAL_COST);
			timer = cooldown;
		}
	}
}

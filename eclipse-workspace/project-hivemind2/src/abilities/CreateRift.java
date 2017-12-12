package abilities;

import org.newdawn.slick.geom.Point;

import animations.circles.AnimRift;
import core.Game;
import core.Utility;
import core.Values;
import objects.units.Unit;

public class CreateRift extends Ability 
{
	Point p;
	
	public CreateRift(Unit owner)
	{
		super(owner);
	}

	public void use(Unit u)
	{
		if(u != null)
		{
			use(u.getLocation());
		}

	}
	
	public void use(Point p)
	{
		this.p = p;
		
		if(owner.hasEnergy(Values.SPECIALIST_RIFT_ENERGY_COST) && 
		   Utility.distance(owner.getLocation(), p) < Values.SPECIALIST_RIFT_RANGE)
		{
			super.use();
			//			Audio.aegis.play(owner.getLocation());	
			owner.getOwner().spawnRift(p.getX(), p.getY());
			animation();

			owner.loseEnergy(Values.ASSAULT_UPGRADE_AEGIS_ENERGY_COST);
		}
	}
	
	public void animation()
	{		
		Game.addAnimation(new AnimRift(p.getX(), p.getY(), owner.getTeam()));
	}
	

}

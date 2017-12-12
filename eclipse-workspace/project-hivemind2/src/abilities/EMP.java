package abilities;


import animations.circles.AnimEMP;
import core.Game;
import core.Values;
import effects.Stunned;
import objects.units.Unit;
import ui.Audio;

final public class EMP extends AbilityArea
{	
	float energySpent = 0;
	
	public EMP(Unit owner) 
	{
		super(owner);
		energy = Values.SUPPORT_UPGRADE_EMP_MINIMUM_ENERGY;
	}
	
	public void use()
	{
		//if(owner.)
		if (owner.hasEnergy(Values.SUPPORT_UPGRADE_EMP_MINIMUM_ENERGY))
		{
			energySpent = owner.getCurEnergy();
			owner.loseEnergy(energySpent);
			super.use(owner.getLocation());
			owner.actionComplete();
			animation();
			Audio.emp.play(owner.getLocation());			
			
//			super.use(owner.getLocation());
//			owner.loseEnergy(Values.SUPPORT_UPGRADE_EMP_ENERGY_COST);
//			owner.actionComplete();
//			animation();
//			Audio.emp.play(owner.getLocation());	

		}
	}

	public void hit(Unit u)
	{
		u.addEffect(new Stunned(u, owner.getOwner(), 1, (int)(Values.SUPPORT_UPGRADE_EMP_DURATION_PER_ENERGY * energySpent)));
	}
	
	public void animation()
	{
	//	Game.animations.add(new AnimMissile(owner.getCenterX(), owner.getCenterY(), 
	//			  owner.getTeam()));
		
		Game.addAnimation(new AnimEMP(owner.getCenterX(), owner.getCenterY(), 
									  owner.getTeam(), getRadius()));
	}

	public int getRadius() 
	{
		//return Values.SUPPORT_UPGRADE_EMP_RADIUS;
		return (int) (Values.SUPPORT_UPGRADE_EMP_RADIUS_PER_ENERGY * energySpent);
	}

	
}

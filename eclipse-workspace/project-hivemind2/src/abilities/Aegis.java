package abilities;

import core.Values;
import effects.DamageReduction;
import objects.units.Unit;
import ui.Audio;

public class Aegis extends AbilitySelf
{

	public Aegis(Unit owner) 
	{
		super(owner);
	}

	public void use()
	{
		if(owner.hasEnergy(Values.ASSAULT_UPGRADE_AEGIS_ENERGY_COST))
		{
			super.use();
			Audio.aegis.play(owner.getLocation());	

			owner.loseEnergy(Values.ASSAULT_UPGRADE_AEGIS_ENERGY_COST);
		}
	}
	protected void apply(Unit u) 
	{
		u.addEffect(new DamageReduction(u, owner.getOwner(), 1, Values.ASSAULT_UPGRADE_AEGIS_DURATION, Values.ASSAULT_UPGRADE_AEGIS_DAMAGE_BLOCKED));
	}

}

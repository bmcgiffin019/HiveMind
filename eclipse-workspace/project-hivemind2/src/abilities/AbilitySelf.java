package abilities;


import objects.units.Unit;

public abstract class AbilitySelf extends Ability
{		
	public AbilitySelf(Unit owner) 
	{
		super(owner);
	}
	
	public void use()
	{
		apply(owner);
	}
	
	abstract protected void apply(Unit u);
	
	
}

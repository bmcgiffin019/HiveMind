package objects.units;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import abilities.EMP;
import core.Utility;
import core.Values;
import objects.base.Player;
import objects.upgrades.SupportEMP;
import objects.upgrades.SupportEnergy;
import objects.upgrades.SupportFix;
import ui.Images;
import weapons.SupportHeal;

public abstract class Support extends Unit {
	private SupportHeal basicAttack;
	private boolean energyUpgraded = false;
	private boolean empUpgraded = false;	
	private boolean fixUpgraded = false;
	
	public Support(Player p) throws SlickException 
	{
		super(p);

		sheet = p.getImageSupport();
		image = sheet.getSprite(0, team);
		timer = (int) (Math.random() * 1000);
		theta = (int) (Math.random() * 360);

		basicAttack = new SupportHeal(this);
		
		curHealth = Values.SUPPORT_HEALTH;
		maxHealth = Values.SUPPORT_HEALTH;
		maxSpeed = Values.SUPPORT_SPEED;
		curArmor = Values.SUPPORT_ARMOR;
		baseArmor = Values.SUPPORT_ARMOR;
		acceleration = Values.SUPPORT_ACCELERATION;
		value = Values.SUPPORT_COST;
		combatValue = value;

		curEnergy = Values.SUPPORT_ENERGY_START_PERCENT * Values.BASE_ENERGY_MAX;
		maxEnergy = Values.BASE_ENERGY_MAX;
		energyRegenRate = Values.BASE_ENERGY_REGEN;
		
		// Centering Spawn
		this.w = (int) (image.getWidth() * scale);
		this.h = (int) (image.getWidth() * scale);
		this.x = x - w;
		this.y = y - h;
	}

	public float getRange() 
	{
		if(inNebula()) return Values.CONCEALMENT_RANGE;
		else return basicAttack.getRange();
	}


	public void update() {
		super.update();

		
		if (!energyUpgraded && getOwner().hasResearch(SupportEnergy.class)) {
			maxEnergy = Values.SUPPORT_UPGRADE_ENERGY_VALUE;
			energyRegenRate = Values.SUPPORT_UPGRADE_ENERGY_REGEN;
			energyUpgraded = true;
			combatValue = combatValue * Values.COMBAT_VALUE_UPGRADE_MULTIPLIER;
		}
		
		if(!empUpgraded &&  getOwner().hasResearch(SupportEMP.class))
		{
			ability = new EMP(this);
			empUpgraded = true;
			combatValue = combatValue * Values.COMBAT_VALUE_UPGRADE_MULTIPLIER;
		}
		
		if(!fixUpgraded && getOwner().hasResearch(SupportFix.class))
		{
			combatValue = combatValue * Values.COMBAT_VALUE_UPGRADE_MULTIPLIER;
			fixUpgraded = true;
		}
		
		
		basicAttack.update();
		

		if (canAct()) {
			action();
			if (getOrder().equals(Order.ATTACK))
				attack();
			else if (getOrder().equals(Order.DEFEND))
				defend();
			else if (getOrder().equals(Order.GUARD))
				guard();
			else if (getOrder().equals(Order.RALLY))
				rally();
			else if (getOrder().equals(Order.RUN))
				run();
			else if (getOrder().equals(Order.SKIRMISH))
				skirmish();
			else if (getOrder().equals(Order.SPECIAL))
				special();
		}
	}

	public void shoot(Unit u) {
		if (u == null)
			return;
		turnTo(u);
		basicAttack.use(u);
	}

	public void shoot() 
	{
		shoot(getTargetUnit());
	}
	
	// ability is close burst EMP
	
	public void ability()
	{
		if(ability instanceof EMP)
		{
			((EMP)ability).use();
		}
	}
		
	
	public void ability(Unit u)
	{
		ability();
	}
	
	public void ability(Point p)
	{
		ability();
	}
	
	final protected void deathTrigger() { }


}

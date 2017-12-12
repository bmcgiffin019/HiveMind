package objects.units;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import abilities.CreateRift;
import core.Utility;
import core.Values;
import objects.base.Player;
import objects.upgrades.SpecialistKinetic;
import objects.upgrades.SpecialistReactor;
import objects.upgrades.SpecialistRift;
import ui.Images;
import weapons.SpecialistAttack;

public abstract class Specialist extends Unit {
	private SpecialistAttack basicAttack;
	boolean riftUpgraded = false;
	boolean energyUpgraded = false;
	boolean kineticUpgraded = false;
	
	public Specialist(Player p) throws SlickException {
		super(p);

		sheet = p.getImageSpecialist();
		image = sheet.getSprite(0, team);
		timer = (int) (Math.random() * 1000);
		theta = (int) (Math.random() * 360);

		basicAttack = new SpecialistAttack(this);

		baseArmor = Values.SPECIALIST_ARMOR;
		curArmor = baseArmor;
		curHealth = Values.SPECIALIST_HEALTH;
		maxHealth = Values.SPECIALIST_HEALTH;
		maxSpeed = Values.SPECIALIST_SPEED;
		acceleration = Values.SPECIALIST_ACCELERATION;
		value = Values.SPECIALIST_COST;
		combatValue = value;
		curEnergy = Values.SPECIALIST_ENERGY_START_PERCENT * Values.BASE_ENERGY_MAX;
		maxEnergy = Values.BASE_ENERGY_MAX;
		energyRegenRate = Values.BASE_ENERGY_REGEN;
		
		
		
		// Centering Spawn
		this.w = (int) (image.getWidth() * scale);
		this.h = (int) (image.getWidth() * scale);
		this.x = x - w;
		this.y = y - h;
	}

	public void update() {
		super.update();

		if(!riftUpgraded && this.getOwner().hasResearch(SpecialistRift.class))
		{
			riftUpgraded = true;
			ability = new CreateRift(this);
			combatValue = combatValue * Values.COMBAT_VALUE_UPGRADE_MULTIPLIER;
		}
		
	
		if (!energyUpgraded && getOwner().hasResearch(SpecialistReactor.class)) {
			maxEnergy = Values.SPECIALIST_UPGRADE_REACTOR_VALUE;
			energyRegenRate = Values.SPECIALIST_UPGRADE_REACTOR_REGEN;
			energyUpgraded = true;
			combatValue = combatValue * Values.COMBAT_VALUE_UPGRADE_MULTIPLIER;
		}
		
		if(!kineticUpgraded && getOwner().hasResearch(SpecialistKinetic.class))
		{
			combatValue = combatValue * Values.COMBAT_VALUE_UPGRADE_MULTIPLIER;
			kineticUpgraded = true;
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

	public float getRange() 
	{
		if(inNebula()) return Values.CONCEALMENT_RANGE;
		else return basicAttack.getRange();
	}


	public void shoot(Unit u) {
		if (u == null)
			return;
		turnTo(u);
		basicAttack.use(u);
	}

	public void shoot() {
		shoot(getTargetUnit());
	}
	
	
	// Ability is a gravity well
	
	public void ability()
	{
		ability.use(getTargetUnit());
	}
	
	public void ability(Unit u)
	{
		ability.use(u);
	}
	
	public void ability(Point p)
	{
		ability.use(p);
	}
	
	final protected void deathTrigger() { }

}

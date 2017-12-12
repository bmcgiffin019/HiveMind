package objects.units;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import abilities.Aegis;
import core.Utility;
import core.Values;
import objects.base.Player;
import objects.upgrades.AssaultAegis;
import objects.upgrades.AssaultExplosive;
import objects.upgrades.AssaultShield;
import ui.Images;
import weapons.AssaultAttack;

public abstract class Assault extends Unit {
	private AssaultAttack basicAttack;
	boolean shieldUpgraded = false;
	boolean aegisUpgraded = false;
	boolean explosiveUpgraded = false;
	
	public Assault(Player p) throws SlickException {
		super(p);

		sheet = p.getImageAssault();
		image = sheet.getSprite(0, team);
		timer = (int) (Math.random() * 1000);
		theta = (int) (Math.random() * 360);

		basicAttack = new AssaultAttack(this);

		curHealth = Values.ASSAULT_HEALTH;
		maxHealth = Values.ASSAULT_HEALTH;
		maxSpeed = Values.ASSAULT_SPEED;
		curArmor = Values.ASSAULT_ARMOR;
		baseArmor = Values.ASSAULT_ARMOR;
		acceleration = Values.ASSAULT_ACCELERATION;
		value = Values.ASSAULT_COST;
		combatValue = value;
		
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

		
		if (!shieldUpgraded && getOwner().hasResearch(AssaultShield.class)) {
			curShield = Values.ASSAULT_UPGRADE_SHIELD_VALUE;
			maxShield = Values.ASSAULT_UPGRADE_SHIELD_VALUE;
			shieldRegenRate = Values.ASSAULT_UPGRADE_SHIELD_REGEN;
			shieldUpgraded = true;
			combatValue = combatValue * Values.COMBAT_VALUE_UPGRADE_MULTIPLIER;
		}
		
		if (!aegisUpgraded && getOwner().hasResearch(AssaultAegis.class)) 
		{
			curEnergy = Values.BASE_ENERGY_MAX * ASSAULT_UPGRADE_ENERGY_START_PERCENT;
			maxEnergy = Values.BASE_ENERGY_MAX;
			energyRegenRate = Values.BASE_ENERGY_REGEN;
			aegisUpgraded = true;
			ability = new Aegis(this);
			combatValue = combatValue * Values.COMBAT_VALUE_UPGRADE_MULTIPLIER;
		}
		
		if(!explosiveUpgraded && getOwner().hasResearch(AssaultExplosive.class))
		{
			basicAttack.setRange(Values.ASSAULT_ATTACK_RANGE + Values.ASSAULT_UPGRADE_EXPLOSIVE_RANGE_INCREASE);
			explosiveUpgraded = true;	
			combatValue = combatValue * Values.COMBAT_VALUE_UPGRADE_MULTIPLIER;
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

	public void shoot() {
		shoot(getTargetUnit());
	}
	
	
	// Ability is a self-shield; can't target others.
	
	public void ability()
	{
		if(ability instanceof Aegis)
		{
			((Aegis) ability).use();
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

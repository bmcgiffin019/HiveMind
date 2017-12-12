package objects.units;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import abilities.Ability;
import animations.Boom;
import core.Game;
import core.Utility;
import core.Values;
import data.BlockEvent;
import data.DamageAttemptEvent;
import data.DamageEvent;
import data.Data;
import data.DeathEvent;
import data.HealEvent;
import effects.Damage;
import effects.DamageArea;
import effects.DamageIgnoreArmor;
import effects.DamageReduction;
import effects.Disabled;
import effects.Effect;
import effects.Immobilized;
import effects.Invulnerable;
import effects.Shattered;
import effects.Slowed;
import effects.Stunned;
import objects.ambient.Asteroid;
import objects.ambient.Hazard;
import objects.ambient.Nebula;
import objects.base.GameObject;
import objects.base.Player;
import ui.Audio;
import ui.Camera;

public abstract class Unit extends GameObject implements Values {
	// Public Constants
	public final static int SPIN_SPEED_QUICK = 5;
	public final static int SPIN_SPEED_SLOW = 2;
	public final static int SPAWN_BORDER = 60;

	// Protected Data
	private ArrayList<Effect> effects;

	protected float curHealth = 0;
	protected float maxHealth = 0;
	protected float curEnergy = 0;
	protected float maxEnergy = 0;
	protected float energyRegenRate = 0;
	protected boolean alive = true;
	protected int hitTimer = 0;
	protected boolean wasHit = false;
	protected float maxSpeed = 0;
	protected float curSpeed = 0;
	protected float acceleration = 0;
	protected float baseArmor = 0;
	protected float curArmor = 0;
	protected float curShield = 0;
	protected float maxShield = 0;
	protected float shieldRegenRate = 0;
	protected float dodgeChance = 0;

	protected int value;
	protected float combatValue;
	private Order myOrder;
	private Unit targetUnit;
	private Point targetPoint;

	protected Ability ability;

	protected ArrayList<Unit> enemies;
	protected ArrayList<Unit> allies;
	protected ArrayList<Asteroid> asteroids;
	protected ArrayList<Nebula> nebulae;
	protected ArrayList<Hazard> hazards;
	
	// Private Data
	private final Player p;
	protected boolean hasConcealment = false;
	protected boolean inNebula = false;
	
	private boolean canAct = true;
	private boolean canMove = true;
	private boolean isStunned = false;
	private boolean isImmobilized = false;
	private boolean isShattered = false;
	private boolean isDisabled = false;
	private boolean isCombusted = false;
	private boolean isInvulnerable = false;
	protected boolean isUntargetable = false;
	private float damageReduction = 0;
	private float speedReduction = 0;

	private int spinDirection = 1;
	private boolean highlighted = false;
	private int highlightTimer = 0;
	
	public Unit(Player p) throws SlickException {
		super(0, 0, p.getTeam());

		this.p = p;

		Point pos = getSpawn(p);
		this.x = pos.getX();
		this.y = pos.getY();

		effects = new ArrayList<Effect>();
		if (Math.random() < .5)
			spinDirection = -1;

		targetPoint = new Point(0, 0);
		myOrder = Order.NONE;

		ability = new Ability(this);

	}

	// Abstract Methods

	abstract public void action();

	abstract public void shoot(Unit u);

	abstract public void shoot();

	abstract public void ability(Point p);

	abstract public void ability(Unit u);

	abstract public void ability();

	// Accessor Methods

	abstract public float getRange();

	public boolean isAlive() {
		return alive;
	}

	public boolean isDead() {
		return !alive;
	}

	public boolean isDamaged() {
		return curHealth < maxHealth - 1;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public boolean isImmobilized() {
		return isImmobilized;
	}

	public boolean isInvulnerable() {
		return isInvulnerable;
	}

	public boolean isTargetable() {
		return !isUntargetable;
	}

	public boolean isStunned() {
		return isStunned;
	}

	public boolean isSlowed() {
		return speedReduction > 0;
	}

	public float getSpeedReduction() {
		return speedReduction;
	}

	public boolean isCombusted() {
		return isCombusted;
	}

	public boolean hasDamageReduction() {
		return damageReduction > 0;
	}

	public float getDamageReduction() {
		return damageReduction;
	}

	public boolean hasShield() {
		return maxShield > 0;
	}

	public boolean hasEnergy() {
		return maxEnergy > 0;
	}

	public boolean canMove() {
		return canMove;
	}

	public boolean canAct() {
		return canAct;
	}

	public boolean hasArmor() {
		return curArmor > 0;
	}

	public float getCurShield() {
		return curShield;
	}

	public float getMaxShield() {
		return maxShield;
	}

	public float getShieldRegenRate() {
		return shieldRegenRate;
	}

	public float getCurHealth() {
		return curHealth;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public float getCurEnergy() {
		return curEnergy;
	}

	public float getMaxEnergy() {
		return maxEnergy;
	}

	public float getEnergyRegenRate() {
		return energyRegenRate;
	}

	public float getPercentHealth() {
		return curHealth / maxHealth;
	}

	public float getCurArmor() {
		return curArmor;
	}

	public float getBaseArmor() {
		return baseArmor;
	}

	public int getValue() {
		return value;
	}

	public float getCombatValue() {
		return combatValue;
	}

	public Player getOwner() {
		return p;
	}

	public Order getOrder() {
		return myOrder;
	}

	public Point getTargetPoint() {
		return targetPoint;
	}

	public Unit getTargetUnit() {
		return targetUnit;
	}

	public boolean hasTargetUnit() {
		return targetUnit != null;
	}

	public int getHitTimer() {
		return hitTimer;
	}

	public int getTimer() {
		return timer;
	}

	public boolean wasHit() {
		return wasHit;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void actionComplete() {
		canAct = false;
	}

	public void moveComplete() {
		canMove = false;
	}

	public boolean hasConcealment() {
		return hasConcealment;
	}
	
	public boolean inNebula() {
		return inNebula;
	}

	
	public boolean inRange()
	{
		return inRange(getTargetUnit());
	}
	public boolean inRange(Unit target) 
	{
		// If it does not exist, it is not in range!
		if(target == null)
		{
			return false;
		}
		
		// A target with concealment is protected against long-range attacks
		if (target.hasConcealment() && getDistance(target) > Values.CONCEALMENT_RANGE) 
		{
			return false;
		} 
		
		return getDistance(target) < getRange();
		
	}

	public float getDistance(int x, int y) {
		return Utility.distance(getCenterX(), getCenterY(), x, y);
	}

	public float getDistance(Unit u) {
		return Utility.distance(this, u);
	}

	public float getDistance(Point p) {
		return Utility.distance(this.getLocation(), p);
	}

	public Point getSpawn(Player p) {
		BaseShip b = Game.getBaseShip(p.getTeam());
		if (b == null)
			return new Point(0, 0);
		Rectangle zone = new Rectangle(b.getX() + SPAWN_BORDER, b.getY() + SPAWN_BORDER, b.getWidth() - SPAWN_BORDER,
				b.getHeight() - SPAWN_BORDER);
		return new Point(Utility.random(zone.getX(), zone.getX() + zone.getWidth()),
				Utility.random(zone.getY(), zone.getY() + zone.getHeight()));
	}

	public BaseShip getHomeBase() {
		return Game.getBaseShip(team);
	}

	public BaseShip getEnemyBase() {
		if (team == Values.BLUE_ID)
			return Game.getBaseShip(Values.RED_ID);
		else if (team == Values.RED_ID)
			return Game.getBaseShip(Values.BLUE_ID);
		else
			return null;
	}

	// ALLY

	public Unit nearestAlly() {
		return nearestAlly(Unit.class);
	}

	public Unit nearestAlly(Class<? extends Unit> clazz) {
		if (allies == null || allies.isEmpty())
			return null;

		float nearestDistance = Float.MAX_VALUE;
		Unit nearestTarget = null;

		for (Unit a : allies) {
			float d = Utility.distance(this, a);

			if (d < nearestDistance && clazz.isInstance(a) && a != this && !(a instanceof BaseShip)) {
				nearestDistance = d;
				nearestTarget = a;
			}
		}

		return nearestTarget;

	}

	public Unit nearestAllyExclude(Class<? extends Unit> clazz) {
		if (allies == null || allies.isEmpty())
			return null;

		float nearestDistance = Float.MAX_VALUE;
		Unit nearestTarget = null;

		for (Unit a : allies) {
			float d = Utility.distance(this, a);

			if (d < nearestDistance && !clazz.isInstance(a) && a != this && !(a instanceof BaseShip)) {
				nearestDistance = d;
				nearestTarget = a;
			}
		}

		return nearestTarget;

	}

	public ArrayList<Unit> getAllies(Class<? extends Unit> clazz) {
		if (allies == null || allies.isEmpty())
			return null;

		ArrayList<Unit> alliesOfType = new ArrayList<Unit>();
		for (Unit u : allies) {
			if (clazz.isInstance(u))
				alliesOfType.add(u);
		}
		return alliesOfType;
	}

	public ArrayList<Unit> getAlliesExclude(Class<? extends Unit> clazz) {
		if (allies == null || allies.isEmpty())
			return null;

		ArrayList<Unit> alliesNotOfType = new ArrayList<Unit>();
		for (Unit u : allies) {
			if (!clazz.isInstance(u))
				alliesNotOfType.add(u);
		}
		return alliesNotOfType;
	}

	public int countAlliesInRadius(float radius) {
		if (allies == null || allies.isEmpty())
			return 0;
		return getAlliesInRadius(radius, Unit.class).size();
	}

	public ArrayList<Unit> getAlliesInRadius(float radius) {
		return getAlliesInRadius(radius, Unit.class);
	}

	public ArrayList<Unit> getAlliesInRadius(float radius, Class<? extends Unit> clazz) {
		if (allies == null || allies.isEmpty())
			return null;

		ArrayList<Unit> radiusAllies = new ArrayList<Unit>();

		for (Unit e : allies) {
			if (clazz.isInstance(e)
					&& Utility.distance(getCenterX(), getCenterY(), e.getCenterX(), e.getCenterY()) <= radius) {
				radiusAllies.add(e);
			}
		}
		return radiusAllies;
	}

	// ENEMY

	public Unit nearestEnemy() {
		return nearestEnemy(Unit.class);

	}

	public Unit nearestEnemy(Class<? extends Unit> clazz) {
		if (enemies == null || enemies.isEmpty())
			return null;

		float nearestDistance = Float.MAX_VALUE;
		Unit nearestTarget = null;

		for (Unit a : enemies) {
			float d = Utility.distance(this, a);

			if (d < nearestDistance && clazz.isInstance(a)) {
				nearestDistance = d;
				nearestTarget = a;
			}
		}

		return nearestTarget;

	}

	public Unit nearestEnemyExclude(Class<? extends Unit> clazz) {
		if (enemies == null || enemies.isEmpty())
			return null;

		float nearestDistance = Float.MAX_VALUE;
		Unit nearestTarget = null;

		for (Unit a : enemies) {
			float d = Utility.distance(this, a);

			if (d < nearestDistance && !clazz.isInstance(a) && a != this && !(a instanceof BaseShip)) {
				nearestDistance = d;
				nearestTarget = a;
			}
		}

		return nearestTarget;

	}

	public ArrayList<Unit> getEnemies(Class<? extends Unit> clazz) {
		if (enemies == null || enemies.isEmpty())
			return null;

		ArrayList<Unit> allEnemies = new ArrayList<Unit>();
		for (Unit e : enemies) {
			if (clazz.isInstance(e)) {
				allEnemies.add(e);
			}
		}
		return allEnemies;
	}

	public int countEnemiesInRadius(float radius) {
		if (enemies == null || enemies.isEmpty())
			return 0;
		return getEnemiesInRadius(radius, Unit.class).size();
	}

	public ArrayList<Unit> getEnemiesInRadius(float radius) {
		return getEnemiesInRadius(radius, Unit.class);
	}

	public ArrayList<Unit> getEnemiesInRadius(float radius, Class<? extends Unit> clazz) {
		if (enemies == null || enemies.isEmpty()) {
			return null;
		}
		ArrayList<Unit> radiusEnemies = new ArrayList<Unit>();
		for (Unit e : enemies) {
			if (clazz.isInstance(e) && getDistance(e) <= radius) {
				radiusEnemies.add(e);
			}
		}
		return radiusEnemies;
	}

	public ArrayList<Mine> getMinesInRadius(float x, float y, float radius) {
		ArrayList<Mine> radiusMines = new ArrayList<Mine>();
		ArrayList<Mine> allMines = getOwner().getMines();
		for (Mine m : allMines) {
			if (Utility.distance(x, y, m.getCenterX(), m.getCenterY()) <= radius) {
				radiusMines.add(m);
			}
		}
		return radiusMines;
	}

	public ArrayList<Mine> getEnemyMinesInRadius(float x, float y, float radius) {
		ArrayList<Mine> radiusMines = new ArrayList<Mine>();
		ArrayList<Mine> allMines = getOwner().getMines();
		for (Mine m : allMines) {
			if (getTeam() != m.getTeam() && Utility.distance(x, y, m.getCenterX(), m.getCenterY()) <= radius) {
				radiusMines.add(m);
			}
		}
		return radiusMines;
	}

	public ArrayList<Mine> getAlliedMinesInRadius(float x, float y, float radius) {
		ArrayList<Mine> radiusMines = new ArrayList<Mine>();
		ArrayList<Mine> allMines = getOwner().getMines();
		for (Mine m : allMines) {
			if (getTeam() == m.getTeam() && Utility.distance(x, y, m.getCenterX(), m.getCenterY()) <= radius) {
				radiusMines.add(m);
			}
		}
		return radiusMines;
	}

	public Asteroid nearestAsteroid() {
		if (asteroids.isEmpty())
			return null;

		float nearestDistance = Float.MAX_VALUE;
		Asteroid nearestTarget = null;

		for (Asteroid a : asteroids) {
			float d = Utility.distance(this, a);

			if (d < nearestDistance) {
				nearestDistance = d;
				nearestTarget = a;
			}

		}

		return nearestTarget;

	}
	
	public Nebula nearestNebula() {
		

		if (nebulae.isEmpty())
			return null;

		float nearestDistance = Float.MAX_VALUE;
		Nebula nearestTarget = null;

		for (Nebula n : nebulae) {
			float d = Utility.distance(this, n);

			if (d < nearestDistance) {
				nearestDistance = d;
				nearestTarget = n;
			}

		}

		return nearestTarget;

	}
	
	public Hazard nearestHazard() {
		
	
		
		if (hazards.isEmpty())
			return null;

		float nearestDistance = Float.MAX_VALUE;
		Hazard nearestTarget = null;

		for (Hazard n : hazards) {
			float d = Utility.distance(this, n);

			if (d < nearestDistance) {
				nearestDistance = d;
				nearestTarget = n;
			}

		}

		return nearestTarget;

	}

	public final float getAngleToward(float targetX, float targetY) {
		float yDiff = targetY - getCenterY();
		float xDiff = targetX - getCenterX();

		float angle = 0;

		// Quadrant 1 - Down and Right
		if (xDiff >= 0 && yDiff >= 0) {
			angle = (float) Math.toDegrees(Math.atan(yDiff / xDiff));
		}

		// Quadrant 2 - Down and Left
		else if (xDiff <= 0 && yDiff >= 0) {
			angle = (float) Math.toDegrees(Math.atan(yDiff / xDiff)) + 180;
		}

		// Quadrant 3 - Up and Left
		else if (xDiff <= 0 && yDiff <= 0) {
			angle = (float) Math.toDegrees(Math.atan(yDiff / xDiff)) + 180;
		}

		// Quadrant 4 - Up and Right
		else if (xDiff >= 0 && yDiff <= 0) {
			angle = (float) Math.toDegrees(Math.atan(yDiff / xDiff));
		}

		if (angle < 0) {
			angle = 360 + angle;
		}
		if (angle > 360) {
			angle = angle % 360;
		}

		return angle;
	}

	// Mutator Methods

	public void addMinerals(float amount) {
		p.addMinerals(amount);
	}

	public boolean hasEnergy(float amount) {
		return curEnergy > amount;
	}

	public void loseEnergy(float amount) {
		curEnergy -= amount;
		if (curEnergy < 0)
			curEnergy = 0;
	}

	public void regainHealth(float amount) {

		if (curHealth + amount > maxHealth) {
			curHealth = maxHealth;
			Data.addEvent(new HealEvent(getTeam(), Game.getTime(), Math.round(maxHealth - curHealth)));
		} else {
			curHealth += amount;
			Data.addEvent(new HealEvent(getTeam(), Game.getTime(), Math.round(amount)));
		}

	}

	public void regainEnergy(float amount) {
		curEnergy += amount;

		if (curEnergy > maxEnergy) {
			curEnergy = maxEnergy;
		}
	}

	public void regainShield(float amount) {
		curShield += amount;

		if (curShield > maxShield) {
			curShield = maxShield;
		}
	}

	public void loseHealth(float amount) {
		curHealth = Math.max(curHealth - amount, 0);

		if (curHealth == 0) {
			image = null;
			alive = false;
		}
	}

	public void takeDamage(float amount, Player source) 
	{
		int dmgData = 0;
		int blockData = 0;
		int attemptData = Math.round(amount);

		if (!isInvulnerable) {
			float attackRoll = Utility.random(0, 100);
			float dodgeChanceMod = 1;
			if (isStunned())
				dodgeChanceMod = 0;
			else if (isSlowed())
				dodgeChanceMod = 0.5f;

			if (attackRoll > dodgeChance * dodgeChanceMod) {
				if (hasDamageReduction()) {
					blockData += amount * getDamageReduction();
					amount -= amount * getDamageReduction();

				}

				hitTimer = 0;
				wasHit = true;

				// Track the data after damageReduction, but before amount is
				// modified
				if (curShield > amount) {
					// It's all shields, so armor does not apply
					blockData += Math.round(amount);
				} else {
					// Armor applies to non-shield damage. Could be zero since
					// damage reduction is already applied
					dmgData = Math.round(Math.max((amount - curShield) - curArmor, 0));
					blockData += Math.round(Math.min(amount, curArmor));
				}

				if (hasShield()) {
					amount = damageShield(amount);
				}

				amount -= curArmor;

				if (amount > 0) {
					loseHealth(amount);
				}

			} else {
				// Missed
				dmgData = 0;
				blockData = Math.round(amount);
			}

			Data.addEvent(new DamageEvent(source.getTeam(), Game.getTime(), dmgData));
			Data.addEvent(new DamageAttemptEvent(source.getTeam(), Game.getTime(), attemptData));
			Data.addEvent(new BlockEvent(source.getTeam(), Game.getTime(), blockData));

		}
	}

	// Area damage cannot be evaded
	public void takeAreaDamage(float amount, Player source) {
		int dmgData = 0;
		int blockData = 0;
		int attemptData = Math.round(amount);

		if (!isInvulnerable) {
			if (hasDamageReduction()) {
				blockData += amount * getDamageReduction();
				amount -= amount * getDamageReduction();
			}
			hitTimer = 0;
			wasHit = true;
			// Track the data after damageReduction, but before amount is
			// modified
			if (curShield > amount) {
				// if it's all shields, we count this as mitigation
				blockData += Math.round(amount);

			} else {
				// Armor applies to non-shield damage. Could be zero since
				// damage reduction is already applied

				dmgData = Math.round(Math.max((amount - curShield) - curArmor, 0));
				blockData += Math.round(Math.min(amount, curArmor));

			}

			if (hasShield()) {
				amount = damageShield(amount);

			}

			amount -= curArmor;

			if (amount > 0) {
				loseHealth(amount);
			}

			Data.addEvent(new DamageAttemptEvent(source.getTeam(), Game.getTime(), attemptData));
			Data.addEvent(new DamageEvent(source.getTeam(), Game.getTime(), dmgData));
			Data.addEvent(new BlockEvent(source.getTeam(), Game.getTime(), blockData));

		}
	}

	// Ignores armor, but can be evaded and reduced by damage reduction
	public void takeArmorPiercingDamage(float amount, Player source) {
		int dmgData = 0;
		int blockData = 0;
		int attemptData = Math.round(amount);

		if (!isInvulnerable) {
			float attackRoll = Utility.random(0, 100);
			float dodgeChanceMod = 1;
			if (isStunned())
				dodgeChanceMod = 0;
			else if (isSlowed())
				dodgeChanceMod = 0.5f;

			if (attackRoll > dodgeChance * dodgeChanceMod) {

				if (hasDamageReduction()) {
					blockData += amount * getDamageReduction();
					amount -= amount * getDamageReduction();

				}

				hitTimer = 0;
				wasHit = true;
				// Track the data after damageReduction, but before amount is
				// modified
				if (curShield > amount) {
					// It's all shields, so armor does not apply
					blockData += Math.round(amount);
				} else {
					// Armor applies to non-shield damage. Could be zero since
					// damage reduction is already applied
					dmgData = Math.round(amount - curShield);
				}

				if (hasShield()) {
					amount = damageShield(amount);

				}

				if (amount > 0) {
					loseHealth(amount);

				}

			} else {
				// Missed
				dmgData = 0;
				blockData = Math.round(amount);
			}

			Data.addEvent(new DamageEvent(source.getTeam(), Game.getTime(), dmgData));
			Data.addEvent(new DamageAttemptEvent(source.getTeam(), Game.getTime(), attemptData));
			Data.addEvent(new BlockEvent(source.getTeam(), Game.getTime(), blockData));

		}
	}

	public float damageShield(float amount) {
		if (!isInvulnerable) {
			curShield -= amount;

			if (curShield < 0) {
				float extra = 0 - curShield;
				curShield = 0;
				return extra;
			} else {
				return 0;
			}
		} else
			return 0;

	}

	public void update() 
	{
		super.update();

		
		canMove = true;
		canAct = true;
		hasConcealment = false;
		
		isCombusted = false;
		isDisabled = false;
		isImmobilized = false;
		isShattered = false;
		isStunned = false;
		isInvulnerable = false;
		damageReduction = 0;

		curArmor = baseArmor;

		regainEnergy(energyRegenRate);


		if (ability != null)
			ability.update();

		if (hitTimer > Values.SHIELD_RECOVERY_DELAY) {
			regainShield(shieldRegenRate);
		}

		hitTimer++;

		if (sheet != null && sheet.getSprite(0, team) != null) {
			image = sheet.getSprite(0, team);
		}
		enemies = Game.getEnemies(this);
		allies = Game.getAllies(this);
		asteroids = Game.getAsteroids();
		nebulae = Game.getNebulae();
		hazards = Game.getHazards();
		
		if (!isInBounds() && (team == Values.BLUE_ID || this.team == Values.RED_ID)) {
			curHealth = 0;
			alive = false;
			die();
		}
	
		updateEffects();
		updateNebula();
		updateStun();



	}
	
	private void updateEffects()
	{
		for (int i = 0; i < effects.size(); i++) {
			Effect e = effects.get(i);
			e.update();
			if (e.stopsAction())
				canAct = false;
			if (e.stopsMove())
				canMove = false;
			if (e.shattersArmor()) {
				curArmor = 0;
			}

			if (e.isActive()) {
				if (e instanceof Disabled)
					isDisabled = true;
				if (e instanceof Immobilized)
					isImmobilized = true;
				if (e instanceof Shattered)
					isShattered = true;
				if (e instanceof Slowed)
					speedReduction = Math.max(speedReduction, e.reducesSpeedPercent());
				if (e instanceof Stunned)
					isStunned = true;
				if (e instanceof Invulnerable)
					isInvulnerable = true;
				if (e instanceof DamageReduction)
					damageReduction = Math.max(damageReduction, e.reducesDamagePercent());
			}
			if (e.isExpired()) {
				effects.remove(i);
				i--;
			}
		}
	}
	
	private void updateStun()
	{
		// Objects that can't move spin out of control
		if (isStunned()) {
			if ((Math.abs(xSpeed) > 1.5 || Math.abs(ySpeed) > 1.5))
				theta += SPIN_SPEED_QUICK * spinDirection;
			if ((Math.abs(xSpeed) > 0.5 || Math.abs(ySpeed) > 0.5))
				theta += SPIN_SPEED_SLOW * spinDirection;
		}
	}

	public final void addEffect(Effect e) {
		if (this instanceof BaseShip) {
			if (e instanceof Damage || e instanceof DamageArea || e instanceof DamageIgnoreArmor)
				applyEffect(e);
		} else
			applyEffect(e);

	}

	private final void applyEffect(Effect e) {
		if (e instanceof Disabled && this.isDisabled)
			purgeEffect(Disabled.class);
		if (e instanceof Immobilized && this.isImmobilized)
			purgeEffect(Slowed.class);
		if (e instanceof Shattered && this.isShattered)
			purgeEffect(Shattered.class);
		if (e instanceof Slowed && isSlowed())
			purgeEffect(Slowed.class);
		if (e instanceof Stunned && this.isStunned)
			purgeEffect(Stunned.class);

		effects.add(e);

		if (e instanceof Disabled)
			isDisabled = true;
		if (e instanceof Immobilized)
			isImmobilized = true;
		if (e instanceof Shattered)
			isShattered = true;
		if (e instanceof Slowed)
			speedReduction = Math.max(speedReduction, e.reducesSpeedPercent());
		if (e instanceof Stunned)
			isStunned = true;
		if (e instanceof Invulnerable)
			isInvulnerable = true;
		if (e instanceof DamageReduction)
			damageReduction = Math.max(damageReduction, e.reducesDamagePercent());

	}

	private final void purgeEffect(Class<?> c) {
		for (int i = 0; i < effects.size(); i++) {
			Effect e = effects.get(i);
			if (c.isInstance(e)) {
				effects.remove(e);
				i--;
			}
		}
	}

	public void reduceEffects(int amount) {
		for (Effect e : effects) {
			if (!e.isPositive()) {
				e.reduceDuration(amount);
			}
		}
	}

	abstract public void draw(Graphics g);

	private void updateNebula()
	{
		ArrayList<Nebula> nebulae = Game.getNebulae();
		
		for(Nebula n : nebulae)
		{
			if(n.containsUnit(this))
			{
				inNebula = true;
				hasConcealment = true;
				return;
			}
		}
		
		inNebula = false;
	}
	
	public void render(Graphics g) {
		super.render(g);

		// Invulnerable
		if (hasDamageReduction()) {

			// Edge
			if (getTeam() == Values.BLUE_ID)
				g.setColor(new Color(75, 225, 255, 255));
			if (getTeam() == Values.RED_ID)
				g.setColor(new Color(255, 175, 100, 255));

			float width = (float) (w * 1.5);
			float height = (float) (h * 1.5);
			g.setLineWidth(3);
			g.drawOval(getCenterX() - width / 2, getCenterY() - height / 2, width, height);
			g.resetLineWidth();
			// Middle

			if (getTeam() == Values.BLUE_ID)
				g.setColor(new Color(75, 200, 255, 150));
			if (getTeam() == Values.RED_ID)
				g.setColor(new Color(255, 150, 100, 150));

			width = (float) (w * 1.5);
			height = (float) (h * 1.5);

			g.fillOval(getCenterX() - width / 2, getCenterY() - height / 2, width, height);

		}

		// Shield
		else if (hasShield() && getCurShield() > 0) {

			if (getTeam() == Values.BLUE_ID)
				g.setColor(new Color(75, 150, 255, 150));
			if (getTeam() == Values.RED_ID)
				g.setColor(new Color(255, 100, 100, 150));

			// Max Shield
			float width = (float) (w * 1.5);
			float height = (float) (h * 1.5);
			g.drawOval(getCenterX() - width / 2, getCenterY() - height / 2, width, height);

			if (getTeam() == Values.BLUE_ID)
				g.setColor(new Color(75, 150, 255, 50));
			if (getTeam() == Values.RED_ID)
				g.setColor(new Color(255, 100, 100, 50));

			// Current Shield
			width = (float) (w * curShield / maxShield * 1.5);
			height = (float) (h * curShield / maxShield * 1.5);
			g.fillOval(getCenterX() - width / 2, getCenterY() - height / 2, width, height);

		}

		// Healthbar
		if ((isAlive() && !isUntargetable) && Camera.getZoom() >= Camera.HEALTHBAR_ZOOM_THRESHOLD) {
			g.setColor(new Color(0, 70, 0));
			g.fillRect(x, y - 10, image.getWidth() * scale, 7);

			g.setColor(new Color(0, 255, 0));
			g.fillRect(x, y - 9, curHealth / maxHealth * image.getWidth() * scale, 5);

			if (hasEnergy()) {
				g.setColor(new Color(70, 0, 70));
				g.fillRect(x, y - 20, image.getWidth() * scale, 7);

				g.setColor(new Color(255, 0, 255));
				g.fillRect(x, y - 19, curEnergy / maxEnergy * image.getWidth() * scale, 5);
			}

		}
		
//		if(inNebula())
//		{
//			highlighted = true;
//		}
//		else
//		{
//			highlighted = false;
//		}
		
		// Highlighted (DEBUG)
		
		if(highlightTimer > 0)
		{
			highlightTimer--;
		}
		
		if(highlighted || highlightTimer > 0)
		{
			g.setColor(Values.COLORS_150[getTeam()]);
			g.drawOval(x-w/2, y-h/2, w*2, h*2);
			//g.drawOval(x-getRange(), y-getRange(), getRange()*2, getRange()*2);
		}

		if (getTeam() == Values.BLUE_ID && Game.isDrawingPlayerGraphicsBlue()) {
			draw(g);
		}

		if (getTeam() == Values.RED_ID && Game.isDrawingPlayerGraphicsRed()) {
			draw(g);
		}

	}
	public void setHighlight(int time)
	{
		highlightTimer = time;
	}
	
	public void setHighlight(boolean highlighted)
	{
		this.highlighted = highlighted;
	}

	public final void accelerate() {
		if (!canMove)
			return;

		if (isSlowed())
			accelerate(acceleration - acceleration * speedReduction);
		else
			accelerate(acceleration);

			image = sheet.getSprite(1, team);
		
		

		canMove = false;

	}

	private void accelerate(float amount) {
		if (!canMove)
			return;

		changeSpeed(amount, theta);

	}

	public final void changeSpeed(float amount, float theta) {
		// Find the change in my speed from moving forward for one frame in the
		// direction I'm facing
		float xDelta = 0;
		float yDelta = 0;
		float xSpeedMax = 0;
		float ySpeedMax = 0;

		xDelta = (float) (amount * Math.cos(Math.toRadians(theta)));
		yDelta = (float) (amount * Math.sin(Math.toRadians(theta)));
		xSpeedMax = (float) (maxSpeed * Math.cos(Math.toRadians(theta)));
		ySpeedMax = (float) (maxSpeed * Math.sin(Math.toRadians(theta)));

		if (xDelta < 0 && xSpeed + xDelta >= -Math.abs(xSpeedMax))
			xSpeed += xDelta;
		else if (xDelta > 0 && xSpeed + xDelta <= Math.abs(xSpeedMax))
			xSpeed += xDelta;

		if (yDelta < 0 && ySpeed + yDelta >= -Math.abs(ySpeedMax))
			ySpeed += yDelta;
		else if (yDelta > 0 && ySpeed + yDelta <= Math.abs(ySpeedMax))
			ySpeed += yDelta;
	}

	public final void reverse(double amount) {

		// Find the change in my speed from moving forward for one frame in the
		// direction I'm facing
		float xDelta = 0;
		float yDelta = 0;

		xDelta = (float) (amount * Math.cos(Math.toRadians(theta + 180)));
		yDelta = (float) (amount * Math.sin(Math.toRadians(theta + 180)));

		xSpeed += xDelta;
		ySpeed += yDelta;

	}

	// Allows turning even if move is locked.
	public final void rotate(int degrees) {
		while (degrees > 360)
			degrees -= 360;
		while (degrees < 0)
			degrees += 360;

		thetaOld = theta;
		theta = degrees;
	}

	public final void turn() {
		turnTo(targetPoint.getX(), targetPoint.getY());

	}

	public final void turn(int degrees) {
		if (!canMove)
			return;

		if (degrees > 360)
			degrees -= 360;
		if (degrees < 0)
			degrees += 360;

		thetaOld = theta;
		theta = degrees;
	}

	public final void turnAround() {
		if (!canMove)
			return;

		theta += 180;
		if (theta > 360)
			theta -= 360;
	}

	public final void turnLeft() {
		turnLeft(90);
	}

	public final void turnLeft(int degrees) {
		if (!canMove)
			return;

		theta -= degrees;
		if (theta < 0)
			theta += 360;
	}

	public final void turnRight() {
		turnRight(90);
	}

	public final void turnRight(int degrees) {
		if (!canMove)
			return;

		theta += degrees;
		if (theta > 360)
			theta -= 360;
	}

	public final void turnTo(Point p) {
		turnTo(p.getX(), p.getY());
	}

	public final void turnTo(float x, float y) {
		turn((int) getAngleToward(x, y));
	}

	public final void turnTo(GameObject o) {
		if (o == null)
			return;
		turnTo(o.getCenterX(), o.getCenterY());
	}

	public final void move(int degrees) {
		if (!canMove)
			return;

		turn(degrees);
		accelerate();
	}

	public final void move() {
		moveTo(targetPoint.getX(), targetPoint.getY());
	}

	public final void moveTo(Point p) {
		moveTo(p.getX(), p.getY());
	}

	public final void moveTo(float x, float y) {
		if (!canMove)
			return;

		turnTo(x, y);
		accelerate();
	}

	public final void moveTo(GameObject o) {
		if (o == null)
			return;
		if (!canMove)
			return;

		turnTo(o);
		accelerate();
	}

	public void haltMovement() {
		xSpeed = 0;
		ySpeed = 0;
	}

	public void lockMiner() {
		if (this instanceof Miner && ((Miner) this).isMining())
			canMove = false;
		if (this instanceof Miner && ((Miner) this).isDroppingMine())
			canMove = true;
	}

	public void unlockMiner() {
		if (this instanceof Miner && ((Miner) this).isMining())
			canMove = true;
		if (this instanceof Miner && ((Miner) this).isDroppingMine())
			canMove = true;

	}

	public void setOrder(Order o) {
		myOrder = o;
	}

	// Every unit can track a single target at a time
	public void setTarget(Unit u) {
		if (u != null) {
			targetUnit = u;
			targetPoint.setX(u.getCenterX());
			targetPoint.setY(u.getCenterY());
		}
	}

	public void setTarget(Point p) {
		targetUnit = null;
		targetPoint.setX(p.getX());
		targetPoint.setY(p.getY());
		}

	public void setTarget(float x, float y) {
		targetUnit = null;
		targetPoint.setX(x);
		targetPoint.setY(y);
	}

	final public void die() {
		alive = false;
		curHealth = 0;
		Game.addAnimation(new Boom(getCenterX(), getCenterY(), w / Boom.BOOM_SIZE));

		Audio.boom[Utility.random(0, 2)].play(getPosition(), (float) (1.2f - .05 * this.value), this.value / 10f);
		deathTrigger();
		Data.addEvent(new DeathEvent(getTeam(), Game.getTime(), getValue(), getPosition()));
	}

	abstract protected void deathTrigger();

	abstract protected void attack();

	abstract protected void defend();

	abstract protected void guard();

	abstract protected void rally();

	abstract protected void run();

	abstract protected void skirmish();

	abstract protected void special();

	public enum Order {
		ATTACK, DEFEND, GUARD, RALLY, RUN, SKIRMISH, SPECIAL, NONE;
	}
}

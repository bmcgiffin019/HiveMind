package objects.units;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import abilities.DropMine;
import animations.AnimationSpark;
import core.Game;
import core.Utility;
import core.Values;
import objects.ambient.Asteroid;
import objects.ambient.HighYieldAsteroid;
import objects.base.Player;
import objects.upgrades.MinerHull;
import objects.upgrades.MinerLaser;
import objects.upgrades.MinerMine;
import ui.Camera;
import ui.Images;
import weapons.MinerAttack;

public abstract class Miner extends Unit {
	private boolean mining;
	private float load;
	private float capacity;
	private float rate;
	private BaseShip home;
	private Asteroid target;
	private boolean droppingMine;
	private int droppingMineTimeLeft;
	private Point dropMineLocation;

	public final static int MINER_SPACE = 60;

	private boolean armorUpgraded = false;
	private boolean gunUpgraded = false;
	private boolean mineUpgraded = false;

	private MinerAttack basicAttack;

	public Miner(Player p) throws SlickException {
		super(p);

		droppingMine = false;
		droppingMineTimeLeft = Values.MINER_UPGRADE_MINE_BUILD_TIME;
		sheet = p.getImageMiner();
		image = sheet.getSprite(0, team);
		timer = (int) (Math.random() * 1000);
		theta = (int) (Math.random() * 360);

		basicAttack = new MinerAttack(this);

		curHealth = Values.MINER_HEALTH;
		maxHealth = Values.MINER_HEALTH;
		maxSpeed = Values.MINER_SPEED;
		acceleration = Values.MINER_ACCELERATION;
		capacity = Values.MINER_CAPACITY;
		value = Values.MINER_COST;
		combatValue = value;
		curArmor = Values.MINER_ARMOR;
		baseArmor = Values.MINER_ARMOR;
		mining = false;
		scale = 1.4f;
		rate = Values.MINER_RATE;

		load = 0;

		home = getHomeBase();

		// Centering Spawn
		this.w = (int) (image.getWidth() * scale);
		this.h = (int) (image.getWidth() * scale);
		this.x = x - w;
		this.y = y - h;
	}

	final public float getLoad() {
		return load;
	}

	final public float getCapacity() {
		return capacity;
	}

	final public boolean hasMinerals() {
		return load > 0;
	}

	final public float getRate() {
		return rate;
	}

	final public boolean isFull() {
		return load == capacity;
	}


	public float getRange() 
	{
		if(inNebula()) return Values.CONCEALMENT_RANGE;
		else return basicAttack.getRange();
	}


	final public Asteroid nearestOpenAsteroid() {
		if (asteroids.isEmpty())
			return null;

		float nearestDistance = Float.MAX_VALUE;
		Asteroid nearestTarget = null;

		for (Asteroid a : asteroids) {
			float d = Utility.distance(this, a);

			if (d < nearestDistance && a.hasMiningSlots() && a.hasMinerals()) {
				nearestDistance = d;
				nearestTarget = a;
			}
		}
		return nearestTarget;
	}

	final public Asteroid nearestOpenHighYieldAsteroid() {
		if (asteroids.isEmpty())
			return null;

		float nearestDistance = Float.MAX_VALUE;
		Asteroid nearestTarget = null;

		for (Asteroid a : asteroids) {
			float d = Utility.distance(this, a);

			if (d < nearestDistance && a.hasMiningSlots() && a.hasMinerals() && a instanceof HighYieldAsteroid) {
				nearestDistance = d;
				nearestTarget = a;
			}
		}
		return nearestTarget;

	}

	final public void render(Graphics g) {
		super.render(g);
		if (isAlive() && Camera.getZoom() >= .5) 
		{
			g.setColor(new Color(120, 120, 65));
			g.fillRect(x, y - 20, image.getWidth() * scale, 7);

			g.setColor(new Color(255, 255, 100));
			g.fillRect(x, y - 19, load / capacity * image.getWidth() * scale, 5);
			


		}
		
		if(isAlive() && Math.random() < .15 && mining)
		{
			int c = Utility.random(30, 255);
			int d = Utility.random(45, 45);
			int s = Utility.random(6, 10);
			Game.addAnimation(new AnimationSpark(x + Utility.random(w), y + Utility.random(h), d, s, 255, 255, c));
		}
		
		
	}

	final public void update() {
		super.update();
		basicAttack.update();

		if (!armorUpgraded && getOwner().hasResearch(MinerHull.class)) {
			curArmor += Values.MINER_UPGRADE_HULL_ARMOR_BONUS;
			baseArmor += Values.MINER_UPGRADE_HULL_ARMOR_BONUS;
			capacity += Values.MINER_UPGRADE_HULL_CAPACITY_BONUS;
			curHealth += Values.MINER_UPGRADE_HULL_HEALTH_BONUS;
			maxHealth = Values.MINER_HEALTH + Values.MINER_UPGRADE_HULL_HEALTH_BONUS;
			armorUpgraded = true;
			combatValue++;
		}

		if (!gunUpgraded && getOwner().hasResearch(MinerLaser.class)) {
			basicAttack.activate();
			rate = Values.MINER_UPGRADE_RATE;
			gunUpgraded = true;
			combatValue++;
		}

		if (!mineUpgraded && getOwner().hasResearch(MinerMine.class)) {
			ability = new DropMine(this);
			mineUpgraded = true;
			combatValue++;
		}

		// CURRENTLY MINING - Collect Resources
		if (canAct() && mining && target.hasMinerals()) {

//			float oldTheta = theta;
//
//			theta = target.getTheta();
			xSpeed = target.getXSpeed();
			ySpeed = target.getYSpeed();
//			x += xSpeed;
//			y += ySpeed;
			
			//theta = oldTheta;

			turn((int) theta);

			if (target instanceof HighYieldAsteroid)
				load += rate * Values.ASTEROID_HIGH_YIELD_MULTIPLIER;
			else
				load += rate;

			target.extractMinerals(Values.MINER_RATE);

			if (timer % 30 <= 15) {
				image = sheet.getSprite(2, team);
			}
			if (load > capacity) {
				stopMine();
				load = capacity;
			}

		}

		// Dropping Mine

		updateDroppingMine();

		if (isDroppingMine()) {
			xSpeed = 0;
			ySpeed = 0;
			
			if (readyToDrop()) 
			{
				((DropMine) ability).use(dropMineLocation);
				endDroppingMine();
			}
		}

		// UNLOADING STUFF
		if (canAct() && !mining && load > 0 && x >= home.getX() && y >= home.getY()
				&& x + getWidth() <= home.getX() + home.getWidth()
				&& y + getHeight() <= home.getY() + home.getHeight()) {
			addMinerals(load);
			load = 0;
		}

		// CAN RETURN
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

	final public void startMine(Asteroid a) {
		if (a != null && !mining && inMiningZone(a) && a.hasMiningSlots() && hasMiningSpace()) {
			xSpeed = a.getXSpeed();
			ySpeed = a.getYSpeed();
			mining = true;
			a.attachMiner(this);
			target = a;
			lockMiner();
		}
	}

	final public void stopMine() {

		if (target != null && mining) {
			mining = false;
			target.detachMiner(this);
			target = null;
			unlockMiner();
		}

	}

	final public boolean inMiningZone(Asteroid a) {
		return Utility.distance(getCenterX(), getCenterY(), a.getCenterX(), a.getCenterY()) < a.getMiningRadius();
	}

	final public boolean hasMiningSpace() {
		for (Unit a : allies) {
			if (a instanceof Miner && ((Miner) a).isMining()) {
				if (Utility.distance(getCenterX(), getCenterY(), a.getCenterX(), a.getCenterY()) < MINER_SPACE) {
					return false;
				}
			}
		}
		return true;
	}

	final public boolean isMining() {
		return mining;
	}

	final public boolean hasMinerals(int amount) {
		return load >= amount;
	}

	final public void loseMinerals(int amount) {
		if (hasMinerals(amount))
			load -= amount;
		else
			load = 0;
	}

	final public void shoot(Unit u) {
		if (u == null)
			return;
		turnTo(u);
		basicAttack.use(u);
	}

	final public void shoot() {
		shoot(getTargetUnit());
	}

	// Drops a mine in a place.

	final public void startDroppingMine() {
		droppingMine = true;
		droppingMineTimeLeft = Values.MINER_UPGRADE_MINE_BUILD_TIME;
		lockMiner();
	}

	final public void endDroppingMine() {
		droppingMine = false;
		unlockMiner();
	}

	final public void updateDroppingMine() {
		if (droppingMineTimeLeft > 0) {
			droppingMineTimeLeft--;
		}


	}

	final public boolean readyToDrop() {
		return droppingMine && droppingMineTimeLeft == 0;

	}

	final public boolean isDroppingMine() {
		return droppingMine;
	}

	final public void ability() 
	{
		if (!isMining() && ability.getTimer() == 0  && hasMinerals(Values.MINER_UPGRADE_MINE_DROP_MINERAL_COST)) 
		{
			startDroppingMine();
			dropMineLocation = getLocation();
		}
	}

	final public void ability(Unit u) {
		moveTo(u);
		if (!isMining() && ability.getTimer() == 0 && hasMinerals(Values.MINER_UPGRADE_MINE_DROP_MINERAL_COST)) {
			if (Utility.isNear(this, u)) {
				ability();
			}
		}
	}

	final public void ability(Point p) {
		moveTo(p);

		if (!isMining() && ability.getTimer() == 0  && hasMinerals(Values.MINER_UPGRADE_MINE_DROP_MINERAL_COST)) 
		{
			startDroppingMine();
			dropMineLocation = p;

		}
	}

	//
	// public void die() {
	// super.die();
	// if (target != null)
	// target.detachMiner(this);
	//
	// }

	final protected void deathTrigger() {
		if (target != null)
			target.detachMiner(this);
	}

}

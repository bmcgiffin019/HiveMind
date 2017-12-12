package objects.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import animations.AnimationText;
import core.Game;
import core.Utility;
import core.Values;
import data.BuildEvent;
import data.Data;
import objects.units.Assault;
import objects.units.BaseShip;
import objects.units.Mine;
import objects.units.Miner;
import objects.units.Missile;
import objects.units.Raider;
import objects.units.Rift;
import objects.units.Specialist;
import objects.units.Support;
import objects.units.Unit;

public abstract class Player {
	// Protected Data

	protected int timer;

	// Private Data
	private Game g;
	
	private float difficultyRating = 1.0f;
	private float mineralEfficiency = 1.0f;
	
	private long latencyAverage;
	private int latencyCount;
	private long latencyLast;
	private long latencyRecent;
	
	private float minerals;
	private float mineralsMined;
	private float mineralsLost;
	private String name;
	private String message;
	private String messageTwo;
	private String messageThree;

	private int team;
	private boolean isDefeated;
	private ArrayList<Upgrade> upgrades;
	private Queue<BuildOrder> buildQueue;
	
	private int researchTimer;
	private Upgrade researchGoal;

	private ArrayList<Unit> alliedUnitList;
	private ArrayList<Unit> enemyUnitList;
	private Map<Class<? extends Unit>, Integer> alliedUnitCount;
	private Map<Class<? extends Unit>, Integer> enemyUnitCount;
	private Map<Class<? extends Unit>, Integer> alliedUnitQueueCount;
		
	private int armyValue;
	private int armyValueEnemy;
	
	private SpriteSheet imageBase;
	private SpriteSheet imageRaider;
	private SpriteSheet imageMiner;
	private SpriteSheet imageAssault;
	private SpriteSheet imageSpecialist;
	private SpriteSheet imageSupport;
	private SpriteSheet imageMine;
	private SpriteSheet imageMissile;
	private SpriteSheet imageTurret;

	
	public void loadImageSet(String skin) throws SlickException
	{
		imageBase = new SpriteSheet("res/units/"+skin+"/base.png", 450, 300, 0);
		imageRaider = new SpriteSheet("res/units/"+skin+"/raider.png", 32, 32, 0);
		imageMiner = new SpriteSheet("res/units/"+skin+"/miner.png", 40, 40, 0);
		imageTurret = new SpriteSheet("res/units/"+skin+"/turret.png", 70, 70, 0);
		imageAssault = new SpriteSheet("res/units/"+skin+"/assault.png", 64, 64, 0);
		imageSpecialist = new SpriteSheet("res/units/"+skin+"/specialist.png", 96, 96, 0);
		imageSupport = new SpriteSheet("res/units/"+skin+"/support.png", 46, 46, 0);
		imageMine = new SpriteSheet("res/units/"+skin+"/mine.png", 46, 46, 0);
		imageMissile = new SpriteSheet("res/units/"+skin+"/missile.png", 22, 8, 0);
	}

	public SpriteSheet getImageBase()
	{
		return imageBase;
	}
	
	public SpriteSheet getImageRaider()
	{
		return imageRaider;
	}
	public SpriteSheet getImageAssault()
	{
		return imageAssault;
	}
	public SpriteSheet getImageSpecialist()
	{
		return imageSpecialist;
	}
	public SpriteSheet getImageMiner()
	{
		return imageMiner;
	}
	public SpriteSheet getImageSupport()
	{
		return imageSupport;
	}
	public SpriteSheet getImageMine()
	{
		return imageMine;
	}
	public SpriteSheet getImageMissile()
	{
		return imageMissile;
	}
	public SpriteSheet getImageTurret()
	{
		return imageTurret;
	}
	
	public Player(int team, Game g) throws SlickException 
	{
		this.team = team;
		this.g = g;
		upgrades = new ArrayList<Upgrade>();
		timer = 0;
		name = "Player";
		message = " ";
		messageTwo = " ";
		messageThree = " ";
		buildQueue = new LinkedList<BuildOrder>();
		loadImageSet("classic");
	}
	
	public void addLatency(long amount)
	{
		if(latencyCount == 0)
		{
			latencyAverage = amount;
			latencyLast = amount;
			latencyRecent = amount;
			latencyCount++;
		}
		else
		{
			latencyAverage = ((latencyAverage * latencyCount) + amount) / (latencyCount + 1);
			latencyRecent = ((latencyRecent * (Values.FRAMES_PER_SECOND-1)) + amount) /  Values.FRAMES_PER_SECOND;
			latencyLast = amount;
			latencyCount++;
		}
	}
	
	public long getAverageLatency()
	{
		return latencyAverage;
	}
	
	public long getRecentLatency()
	{
		if(latencyCount >1)
		{
			return latencyRecent;	
		}
		else
		{
			return 0;
		}
	}
	
	public long getLatency()
	{
		return latencyLast;	
	}
	
	public void setStartingMinerals()
	{
		// This is not effected by difficulty rating
		minerals = Values.STARTING_MINERALS;

	}

	public void update() throws SlickException 
	{
		
		timer++;
		
		// Set Latency Mineral Efficiency Cost
		if(getRecentLatency() > Values.LATENCY_RED)
		{
			mineralEfficiency = Values.LATENCY_RED_MINERAL_EFFICIENCY;
		}
		else if(getRecentLatency() > Values.LATENCY_YELLOW)
		{
			mineralEfficiency = Values.LATENCY_YELLOW_MINERAL_EFFICIENCY;
		}
		else
		{
			mineralEfficiency = 1;
		}

		
		// Research
		if(researchTimer > 0)
		{
			researchTimer--;
		}
		if(researchTimer == 0 && researchGoal != null)
		{
			completeResearch();
		}

		// Try to spawn a unit if the time is up
		if (!buildQueue.isEmpty() && buildQueue.peek().time == 0) 
		{
			// Hold on spawning the unit if we've hit the unit cap
			if(getTotalValue() + buildQueue.peek().value <= Values.BASE_UNIT_VALUE_CAP)
			{
				Class<? extends Unit> type = buildQueue.poll().name;
				spawnUnit(type);
			}
		}

		// Otherwise, count down
		else if (!buildQueue.isEmpty() && buildQueue.peek() != null) {
			buildQueue.peek().time--;
		}

		// ALLIED CONTENT
		alliedUnitList = Game.getAllies(this);
		alliedUnitCount = countUnits(alliedUnitList);
		alliedUnitQueueCount = countUnits(buildQueue);

		// ENEMY CONTENT
		enemyUnitList = Game.getEnemies(this);
		enemyUnitCount = countUnits(enemyUnitList);


		armyValue = calculateArmyValue(getMyUnits());
		armyValueEnemy = calculateArmyValue(getEnemyUnits());
		
		action();

	}
	

	public abstract void draw(Graphics g);
	
	private Map<Class<? extends Unit>, Integer> countUnits(Queue<BuildOrder> buildQueue) {
		Map<Class<? extends Unit>, Integer> units = new HashMap<>();
		for (BuildOrder order : buildQueue) {
			Class<? extends Unit> unitClazz = baseUnitType(order.name);
			int numUnits = 1;
			if (units.containsKey(unitClazz)) {
				numUnits = units.get(unitClazz) + 1;
			}

			units.put(unitClazz, numUnits);
		}
		return units;
	}

	private Map<Class<? extends Unit>, Integer> countUnits(Collection<Unit> unitList) {
		Map<Class<? extends Unit>, Integer> units = new HashMap<>();
		for (Unit unit : unitList) {
			Class<? extends Unit> unitClazz = baseUnitType(unit.getClass());
			int numUnits = 1;
			if (units.containsKey(unitClazz)) {
				numUnits = units.get(unitClazz) + 1;
			}

			units.put(unitClazz, numUnits);
		}
		return units;
	}

	/**
	 * The action performed every frame for this player.
	 * 
	 * @throws SlickException
	 */
	public abstract void action() throws SlickException;

	/****** Accessor Methods ********/
	public int countUnit(Class<? extends Unit> clazz, Map<Class<? extends Unit>, Integer> counts) {
		int units = 0;
		if (counts.containsKey(clazz)) {
			units = counts.get(clazz);
		}
		return units;
	}

	public boolean isResearching()
	{
		return researchTimer != 0;
	}
	public int getResearchTimeLeft()
	{
		return researchTimer;
	}
	
	public boolean isDefeated()
	{
		return isDefeated;
	}
	public int countMyUnit(Class<? extends Unit> clazz) {
		return countMyUnitInPlay(clazz) + countUnit(clazz, alliedUnitQueueCount);
	}

	public int countMyUnits() {
		return countMyRaiders() + countMyMiners() + countMyAssaults() + countMySupports() + countMySpecialists();
	}
	public int countMyRaiders() {
		return countMyUnit(Raider.class);
	}

	public int countMyMiners() {
		return countMyUnit(Miner.class);
	}

	public int countMyAssaults() {
		return countMyUnit(Assault.class);
	}

	public int countMySpecialists() {
		return countMyUnit(Specialist.class);
	}

	public int countMySupports() {
		return countMyUnit(Support.class);
	}

	public int countMyUnitInPlay(Class<? extends Unit> clazz) {
		return countUnit(clazz, alliedUnitCount);
	}

	public int countMyRaidersInPlay() {
		return countMyUnitInPlay(Raider.class);
	}

	public int countMyMinersInPlay() {
		return countMyUnitInPlay(Miner.class);
	}

	public int countMyAssaultsInPlay() {
		return countMyUnitInPlay(Assault.class);
	}

	public int countMySpecialistsInPlay() {
		return countMyUnitInPlay(Specialist.class);
	}

	public int countMySupportsInPlay() {
		return countMyUnitInPlay(Support.class);
	}

	public int countEnemyUnit(Class<? extends Unit> clazz) {
		return countUnit(clazz, enemyUnitCount);
	}
	public int countEnemyUnits() {
		return countEnemyRaiders() + countEnemyMiners() + countEnemyAssaults() 
		+ countEnemySupports() + countEnemySpecialists();		}

	public int countEnemyRaiders() {
		return countEnemyUnit(Raider.class);
	}

	public int countEnemyMiners() {
		return countEnemyUnit(Miner.class);
	}

	public int countEnemyAssaults() {
		return countEnemyUnit(Assault.class);
	}

	public int countEnemySpecialists() {
		return countEnemyUnit(Specialist.class);
	}

	public int countEnemySupports() {
		return countEnemyUnit(Support.class);
	}

	public ArrayList<Unit> getMyUnits() 
	{
		return alliedUnitList;
	}
	
	public ArrayList<Unit> getMyUnits(Class<? extends Unit> clazz) 
	{	
		ArrayList<Unit> units = new ArrayList<Unit>();
		
		if(alliedUnitList == null || alliedUnitList.isEmpty())
		{
			return null;
		}
		for(Unit u : alliedUnitList)
		{
			if(clazz.isAssignableFrom(u.getClass()))
			{
				units.add(u);
			}
		}
		return units;
	}
	
	// deprecated
	public ArrayList<Unit> getAllies()
	{
		return getMyUnits();
	}
	
	// deprecated
	public ArrayList<Unit> getEnemies()
	{
		return getEnemyUnits();
	}
	
	public ArrayList<Unit> getEnemyUnits() 
	{
		return enemyUnitList;
	}
	
	//deprecated
	public ArrayList<Unit> getEnemies(Class<? extends Unit> clazz) 
	{
		return getEnemyUnits(clazz);
	}
	
	public ArrayList<Unit> getEnemyUnits(Class<? extends Unit> clazz) 
	{
		ArrayList<Unit> units = new ArrayList<Unit>();
		
		if(enemyUnitList == null || enemyUnitList.isEmpty())
		{
			return null;
		}
		for(Unit u : enemyUnitList)
		{
			if(clazz.isAssignableFrom(u.getClass()))
			{
				units.add(u);
			}
		}
		return units;
	}

	public int getTotalValue() {
		return calculateTotalValue(getMyUnits());
	}

	public int getTotalValueEnemy() {
		return calculateTotalValue(getEnemyUnits());
	}
	
	private int calculateTotalValue(List<Unit> units) 
	{
		int value = 0;
		
		if(units == null)
		{
			return 0;
		}
		for (Unit unit : units) 
		{
				value += unit.getValue();
		}
		
		return value;
	}


	private int calculateArmyValue(List<Unit> units) {
		int value = 0;
		
		if(units == null)
		{
			return 0;
		}
		for (Unit unit : units) 
		{
			value += unit.getCombatValue();
		}
		return value;
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

	public String getMessageTwo() {
		return messageTwo;
	}
	

	public String getMessageThree() {
		return messageThree;
	}

	public int getArmyValue() {
		return armyValue;
	}

	public int getArmyValueOpponent() {
		return armyValueEnemy;
	}

	public float getMinerals() {
		return minerals;
	}

	public float getMineralsMined() {
		return mineralsMined;
	}
	
	public float getMineralsLost() {
		return mineralsLost;
	}

	public int getTeam() {
		return team;
	}
	
	public int getEnemyTeam() {
		if(getTeam() == Values.BLUE_ID)
		{
			return Values.RED_ID;
		}
		else if(getTeam() == Values.RED_ID)
		{
			return Values.BLUE_ID;
		}
		else
		{
			return -1;
		}
	}

	public ArrayList<Upgrade> getUpgrades() {
		return upgrades;
	}

	public int getNumUpgrades() {
		return upgrades.size();
	}
	
	public int getNextUpgradeCost() {
		return upgrades.size() * Values.UPGRADE_TAX + Values.UPGRADE_COST;
	}

	public ArrayList<Mine> getMines() {
		return g.getMines();
	}

	public Player getOpponent() {
		return g.getOpponent(this);
	}

	public boolean isBuilding() {
		return !buildQueue.isEmpty();
	}

	public int getNextBuildTimeLeft() {
		if (isBuilding())
			return buildQueue.peek().time;
		else
			return 0;
	}

	public int getTotalBuildTime() {
		int time = 0;
		for (BuildOrder b : buildQueue) {
			time += b.time;
		}
		return time;

	}

	public int getNextBuildTimeFull() {
		return buildQueue.isEmpty() ? 0 : buildQueue.peek().time;
	}

	public Class<? extends Unit> getNextBuildType() {
		return buildQueue.isEmpty() ? null : buildQueue.peek().name;
	}
	
	public float getDifficultyRating()
	{
		return difficultyRating;
	}
	
	public float getMineralEfficiency()
	{
		return mineralEfficiency;
	}

	/** Upgrades **/

	public void setDifficultyRating(float difficultyRating)
	{
		if(difficultyRating >= 0.0f)
		{
			this.difficultyRating = difficultyRating;
		}
	}
	
	public boolean hasResearch(Class<? extends Upgrade> clazz) {
		for (Upgrade i : upgrades) {
			if (clazz.isInstance(i)) {
				return true;
			}
		}
		return false;
	}

	public boolean beginResearch(Class<? extends Upgrade> type) {
		if (!isResearching() && !hasResearch(type)) {
			try 
			{
				researchGoal = type.newInstance();
				researchTimer = Values.RESEARCH_TIME;	
				return true;
			} catch (InstantiationException e) {
				e.printStackTrace();
				return false;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public void completeResearch()
	{		
		//Data.addEvent(new UpgradeEvent(getTeam(), Game.getTime(), val)); 
		upgrades.add(researchGoal);
		researchGoal = null;
	}

	//deprecated - remove after kids fix their code
	public boolean canBuyUpgrade() {
		return true;
	}

	public BaseShip getMyBase() {
		if (team == Values.BLUE_ID)
		{
			return Game.getBaseShip(Values.BLUE_ID);
		}
		else if(team == Values.RED_ID)
		{
			return Game.getBaseShip(Values.RED_ID);
		}
		else
		{
			return Game.getBaseShip(Values.NEUTRAL_ID);

		}
	}

	public BaseShip getEnemyBase() {
		if (team == Values.BLUE_ID)
		{
			return Game.getBaseShip(Values.RED_ID);
		}
		else if(team == Values.RED_ID) {
			return Game.getBaseShip(Values.BLUE_ID);
		}
		else
		{
			return Game.getBaseShip(Values.NEUTRAL_ID);
		}
	}

	// Mutator
	protected void setName(String n) {
		name = n;
	}

	public void setMessage(String m) {
		setMessageOne(m);
	}
	
	public void loseGame()
	{
		isDefeated = true;
		Data.setWinner(getEnemyTeam());
	}

	public void setMessageOne(String m) {
		message = m;
	}

	public void setMessageTwo(String m) {
		messageTwo = m;
	}
	
	public void setMessageThree(String m) {
		messageThree = m;
	}

	public void addMinerals(float amount) 
	{		
		float actualAmount = amount * getDifficultyRating() * getMineralEfficiency();
		
		minerals += actualAmount;
		mineralsMined += actualAmount;
		mineralsLost += amount - actualAmount;
		
		Game.addAnimation(new AnimationText(getMyBase().getX() + Utility.random((int)getMyBase().getWidth()), 
                getMyBase().getY(), "+" + (int) actualAmount, 255, 255, 0)); 
		
	}

	public void subtractMinerals(float amount) {
		minerals -= amount;
	}

	/****** Unit Construction ********/

	public void spawnUnit(Class<? extends Unit> type) throws SlickException {
		if (type == Raider.class) {
			g.addUnit(buildRaider());
		} else if (type == Miner.class) {
			g.addUnit(buildMiner());
		} else if (type == Assault.class) {
			g.addUnit(buildAssault());
		} else if (type == Support.class) {
			g.addUnit(buildSupport());
		} else if (type == Specialist.class) {
			g.addUnit(buildSpecialist());
		}
	}

	public void spawnMine(float xNow, float yNow, float xOrigin, float yOrigin) {
		try {
			g.addUnit(new Mine(this, xNow, yNow, xOrigin, yOrigin));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void spawnMissile(Unit creator, Unit target) {
		try {
			g.addUnit(new Missile(creator, target));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	
	public void spawnRift(float x, float y) {
		try {
			g.addUnit(new Rift(this, x, y));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	public abstract Raider buildRaider() throws SlickException;

	public abstract Miner buildMiner() throws SlickException;

	public abstract Assault buildAssault() throws SlickException;

	public abstract Specialist buildSpecialist() throws SlickException;

	public abstract Support buildSupport() throws SlickException;

	protected boolean addUnitToQueue(Class<? extends Unit> unitClazz, int unitCost, int unitBuildTime) 
	{
		if (minerals >= unitCost) 
		{		
		
			BuildOrder bo = new BuildOrder(unitClazz, unitBuildTime, unitCost);

			buildQueue.offer(bo);
			minerals -= unitCost;
			Data.addEvent(new BuildEvent(getTeam(), Game.getTime(), unitCost));
			
			return true;
		} else {
			return false;
		}
	}

	protected boolean addRaiderToQueue() throws SlickException {
		return addUnitToQueue(Raider.class, Values.RAIDER_COST, Values.RAIDER_BUILD_TIME);
	}

	protected boolean addMinerToQueue() throws SlickException {
		return addUnitToQueue(Miner.class, Values.MINER_COST, Values.MINER_BUILD_TIME);
	}

	protected boolean addAssaultToQueue() throws SlickException {
		return addUnitToQueue(Assault.class, Values.ASSAULT_COST, Values.ASSAULT_BUILD_TIME);
	}

	protected boolean addSpecialistToQueue() throws SlickException {
		return addUnitToQueue(Specialist.class, Values.SPECIALIST_COST, Values.SPECIALIST_BUILD_TIME);
	}

	protected boolean addSupportToQueue() throws SlickException {
		return addUnitToQueue(Support.class, Values.SUPPORT_COST, Values.SUPPORT_BUILD_TIME);
	}

	/** Utility Methods **/
	private Class<? extends Unit> baseUnitType(Class<? extends Unit> clazz) {
		if (Miner.class.isAssignableFrom(clazz)) {
			return Miner.class;
		} else if (Raider.class.isAssignableFrom(clazz)) {
			return Raider.class;
		} else if (Assault.class.isAssignableFrom(clazz)) {
			return Assault.class;
		} else if (Specialist.class.isAssignableFrom(clazz)) {
			return Specialist.class;
		} else if (Support.class.isAssignableFrom(clazz)) {
			return Support.class;
		}
		return null;
	}
	
	// Getters for My Units
	
	public int countMyUnitsInRadius(float x, float y, float radius) 
	{
		if (alliedUnitList == null || alliedUnitList.isEmpty())		return 0;		
		return getMyUnitsInRadius(x, y, radius, Unit.class).size();
	}
	
	public ArrayList<Unit> getMyUnitsInRadius(float x, float y, float radius) {
		return getMyUnitsInRadius(x, y, radius, Unit.class);
	}

	public ArrayList<Unit> getMyUnitsInRadius(float x, float y, float radius, Class<? extends Unit> clazz) 
	{
		if (alliedUnitList == null || alliedUnitList.isEmpty())		return null;		

		ArrayList<Unit> radiusAllies = new ArrayList<Unit>();
		
		for (Unit e : alliedUnitList) 
		{			
			if (clazz.isInstance(e) && Utility.distance(x, y, e.getCenterX(), e.getCenterY()) <= radius) {
				radiusAllies.add(e);
			}
		}
		return radiusAllies;
	}
	
	public ArrayList<Unit> getMyUnitsInArea(Rectangle r) {
		return getMyUnitsInArea(r, Unit.class);
	}
	
	public ArrayList<Unit> getMyUnitsInArea(Rectangle r, Class<? extends Unit> clazz) 
	{
		if (alliedUnitList == null || alliedUnitList.isEmpty())		return null;		

		ArrayList<Unit> rectAllies = new ArrayList<Unit>();
		
		for (Unit e : alliedUnitList) 
		{			
			if (clazz.isInstance(e) &&
					e.getCenterX() >= r.getX() && e.getCenterX() <= r.getX() + r.getWidth() &&
					e.getCenterY() >= r.getY() && e.getCenterY() <= r.getY() + r.getHeight())
			{
				rectAllies.add(e);
			}
		}
		return rectAllies;
	}
	
	
	// Getters for Enemy Units

	public int countEnemyUnitsInRadius(float x, float y, float radius) 
	{
		if (enemyUnitList == null || enemyUnitList.isEmpty())		return 0;		
		return getEnemyUnitsInRadius(x, y, radius, Unit.class).size();
	}
	
	public ArrayList<Unit> getEnemyUnitsInRadius(float x, float y, float radius) {
		return getEnemyUnitsInRadius(x, y, radius, Unit.class);
	}

	public ArrayList<Unit> getEnemyUnitsInRadius(float x, float y, float radius, Class<? extends Unit> clazz) 
	{
		if (enemyUnitList == null || enemyUnitList.isEmpty())		return null;		

		ArrayList<Unit> radiusEnemies = new ArrayList<Unit>();
		
		for (Unit e : enemyUnitList) 
		{			
			if (clazz.isInstance(e) && Utility.distance(x, y, e.getCenterX(), e.getCenterY()) <= radius) {
				radiusEnemies.add(e);
			}
		}
		return radiusEnemies;
	}
	
	public ArrayList<Unit> getEnemyUnitsInArea(Rectangle r) {
		return getEnemyUnitsInArea(r, Unit.class);
	}
	
	public ArrayList<Unit> getEnemyUnitsInArea(Rectangle r, Class<? extends Unit> clazz) 
	{
		if (enemyUnitList == null || enemyUnitList.isEmpty())		return null;		

		ArrayList<Unit> rectEnemies = new ArrayList<Unit>();
		
		for (Unit e : enemyUnitList) 
		{			
			if (clazz.isInstance(e) &&
					e.getCenterX() >= r.getX() && e.getCenterX() <= r.getX() + r.getWidth() &&
					e.getCenterY() >= r.getY() && e.getCenterY() <= r.getY() + r.getHeight())
			{
				rectEnemies.add(e);
			}
		}
		return rectEnemies;
	}

	
}

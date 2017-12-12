package scenario;


import java.lang.reflect.Constructor;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import core.Game;
import core.Utility;
import core.Values;
import objects.ambient.Asteroid;
import objects.ambient.BigStar;
import objects.ambient.Hazard;
import objects.ambient.HighYieldAsteroid;
import objects.ambient.Moon;
import objects.ambient.Nebula;
import objects.ambient.Pulsar;
import objects.base.Player;
import objects.units.BaseShip;
import objects.units.Unit;
import scenario.events.Event;
import scenario.events.None;
import scenario.events.Pulse;
import scenario.events.SolarFlare;
import teams.neutral.pirate.*;


public class Scenario {
	private static String title;

	private Game game;

	private Player neutral;
	private BaseShip neutralBaseShip;
	private boolean hasNeutralBaseShip;

	private static ArrayList<Asteroid> asteroids;
	private static ArrayList<Hazard> hazards;
	private static ArrayList<Nebula> nebulae;

	private static int numAsteroids;
	private static int numHighYieldAsteroids;
	private static int numNebulae;
	private static int neutralSpawnCooldown;
	private static ArrayList<Class<? extends Unit>> unitPack;

	private static Adjective adjective;
	private static Noun noun;
	private static Battle battle;

	private static Event event;

	public Scenario(Game game) {

		this.game = game;
		asteroids = new ArrayList<Asteroid>();
		nebulae = new ArrayList<Nebula>();
		hazards = new ArrayList<Hazard>();

		numAsteroids = 15;
		numHighYieldAsteroids = 5;
		numNebulae = 0;
		neutralSpawnCooldown = 10000;

		battle = Battle.getRandom();
		
		if(Game.basicMode)
		{
			adjective = Adjective.getBasic();
			noun = Noun.getBasic();
		}
		else
		{
			adjective = Adjective.getRandom();
			noun = Noun.getRandom();
		}
		
		event = new None();
		unitPack = new ArrayList<Class<? extends Unit>>();

		applyAdjective();
		applyNoun();

	}

	public void manageEvents() {
		if (hasEvent()) {
			event.update();
		}

	}

	public void applyAdjective() {

		switch (adjective) {

		case ABUNDANT:
			numAsteroids += 10;
			break;

		case AVERAGE:
			numAsteroids += 5;
			break;

		case BARREN:
			numHighYieldAsteroids -= 5;
			break;

		case HIDDEN:
			numNebulae += 1;
			break;

		case LEGENDARY:
			noun = Adjective.getLegendaryNoun();
			break;

		case POOR:
			numAsteroids += 5;
			numHighYieldAsteroids -= 5;
			break;

		case RELENTLESS:		// DOESN'T WORK - at present, nothing spawns on edges
			noun = Adjective.getRelentlessNoun();
			neutralSpawnCooldown *= .75;
			break;
			
		case RICH:
			numAsteroids -= 5;
			numHighYieldAsteroids += 5;
			break;

		case SHROUDED:
			numNebulae += 2;
			break;

		case SPARSE:
			// Nothing
			break;

		case UNSTABLE:
			noun = Adjective.getUnstableNoun();
			break;

		default:
			break;


		}

	}

	// The NOUN determines the major set piece that defines the environment
	public void applyNoun() {
		switch (noun) {
		case STAR:
			break;

		case NEBULA:
			numNebulae += 3;
			break;

		case ASTEROID_BELT:
			if (adjective.equals(Adjective.LEGENDARY)) {
				numHighYieldAsteroids *= 1.5 + numAsteroids;
				numAsteroids = 0;
			} else {
				numAsteroids *= 1.5;
				numHighYieldAsteroids *= 1.5;
			}
			break;

		case PIRATES:
			hasNeutralBaseShip = true;
			break;
			
		default:
			break;

		}

	}

	public void addToUnitPack(Class<? extends Unit> clazz, int number) {
		for (int i = 0; i < number; i++) {
			unitPack.add(clazz);
		}
	}

	public static void clear() {
		asteroids.clear();
		nebulae.clear();
		hazards.clear();

		battle = null;
		adjective = null;
		noun = null;
	}

	public static ArrayList<Asteroid> getAsteroids() {
		ArrayList<Asteroid> temp = new ArrayList<Asteroid>();

		for (Asteroid a : asteroids) {
			temp.add(a);
		}
		return temp;
	}

	public static ArrayList<Nebula> getNebulae() {
		ArrayList<Nebula> temp = new ArrayList<Nebula>();

		for (Nebula a : nebulae) {
			temp.add(a);
		}
		return temp;
	}

	public static ArrayList<Hazard> getHazards() {
		ArrayList<Hazard> temp = new ArrayList<Hazard>();

		for (Hazard a : hazards) {
			temp.add(a);
		}
		return temp;
	}

	public Point getEdgeSpawn() {
		int r = Utility.random(4);
		final int OFFSCREEN = 200;

		switch (r) {
		// top
		case 0:
			return new Point(Utility.random(-Values.PLAYFIELD_WIDTH / 2, Values.PLAYFIELD_WIDTH / 2),
					-Values.PLAYFIELD_HEIGHT / 2 - OFFSCREEN);

		// bottom
		case 1:
			return new Point(Utility.random(-Values.PLAYFIELD_WIDTH / 2, Values.PLAYFIELD_WIDTH / 2),
					Values.PLAYFIELD_HEIGHT / 2 + OFFSCREEN);

		// left
		case 2:
			return new Point(-Values.PLAYFIELD_WIDTH / 2 - OFFSCREEN,
					Utility.random(-Values.PLAYFIELD_HEIGHT / 2, Values.PLAYFIELD_HEIGHT / 2));

		// right
		case 3:
			return new Point(Values.PLAYFIELD_WIDTH / 2 + OFFSCREEN,
					Utility.random(-Values.PLAYFIELD_HEIGHT / 2, Values.PLAYFIELD_HEIGHT / 2));
		}

		return new Point(0, 0);
	}

	public void update() throws SlickException 
	{

		if (Game.getTime() % neutralSpawnCooldown == 0 && Game.getTime() > neutralSpawnCooldown) {
			final int VARIANCE = 150;
			Point p = getEdgeSpawn();

			for (int i = 0; i < unitPack.size(); i++) {

				try {
					Class<? extends Unit> type = unitPack.get(i);
					Constructor<? extends Unit> c = type.getDeclaredConstructor(Player.class, int.class, int.class);

					int xSpawn = (int) (p.getX() + Utility.random(-VARIANCE, VARIANCE));
					int ySpawn = (int) (p.getY() + Utility.random(-VARIANCE, VARIANCE));

					Unit u1 = c.newInstance(neutral, xSpawn, ySpawn);
					Unit u2;

					if (hasSetPiece()) {
						u2 = c.newInstance(neutral, -xSpawn, ySpawn);
					} else {
						u2 = c.newInstance(neutral, -xSpawn, -ySpawn);
					}

					u1.setHighlight(250);
					u2.setHighlight(250);

					game.addUnit(u1);
					game.addUnit(u2);
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

	public void spawn() throws SlickException 
	{
		title = battle + " the " + adjective + " " + noun;

		neutral = new Pirate(Values.NEUTRAL_ID, game);
		game.setNeutral(neutral);

		if (hasNeutralBaseShip) {
			neutralBaseShip = new BaseShip(neutral);
			game.addUnit(neutralBaseShip);
			neutral.setStartingMinerals();
		}

		// Spawn Asteroids
		spawnAsteroidRandom(numAsteroids);
		spawnAsteroidRandomHighYield(numHighYieldAsteroids);

		// Spawn Nebulae
		spawnNebulaRandom(numNebulae);

		// Spawn Big Star
		if (hasStar()) {
			spawnBigStar();
		}

		// Spawn Pulsar
		if (hasPulsar()) {
			spawnPulsar();
		}
		
		if(hasMoon())
		{
			spawnMoon();
		}

	}

	/********** ACCESSOR METHODS ***********/

	public boolean hasSetPiece() {
		return hasNeutralBaseShip() || hasEvent();
	}

	public Player getNeutral() {
		return neutral;
	}

	public BaseShip getNeutralBaseShip() {
		return neutralBaseShip;
	}

	public boolean hasNeutralBaseShip() {
		return hasNeutralBaseShip;
	}

	public static String getTitle() {
		return title;
	}

	public static int getEventCountdown() {
		return event.getCountdown();
	}

	public static boolean hasEvent() {
		return !(event instanceof None);
	}

	public static int getNumAsteroids() {
		return numAsteroids;
	}

	public static int getNumHighYieldAsteroids() {
		return numHighYieldAsteroids;
	}

	public static int getNumNebulae() {
		return numNebulae;
	}

	public static boolean isLegendary() {
		return adjective.equals(Adjective.LEGENDARY);
	}
	
	public static boolean isUnstable() {
		return adjective.equals(Adjective.UNSTABLE);
	}
	
	public static boolean hasMoon()
	{
		return noun == Noun.MOON;
	}
	public static boolean hasStar() {
		return noun == Noun.STAR;
	}

	public static boolean hasPulsar() {
		return noun == Noun.PULSAR;
	}

	public Adjective getAdjective() {
		return adjective;
	}

	/********** SPAWNING METHODS ***********/

	public void spawnAsteroid(int x, int y, int size) throws SlickException {
		float xSpeed = Utility.random(Values.ASTEROID_MIN_SPEED, Values.ASTEROID_MAX_SPEED);
		float ySpeed = Utility.random(Values.ASTEROID_MIN_SPEED, Values.ASTEROID_MAX_SPEED);

		if (Math.random() > .5)
			xSpeed *= -1;
		if (Math.random() > .5)
			ySpeed *= -1;

		Asteroid a = new Asteroid(x, y, xSpeed, ySpeed, size);
		Asteroid b;

		if (hasSetPiece()) {
			b = new Asteroid(-x, y, -xSpeed, ySpeed, size);
		} else {
			b = new Asteroid(-x, -y, -xSpeed, -ySpeed, size);
		}

		a.invertCorner();
		b.invertCorner();

		asteroids.add(a);
		asteroids.add(b);

	}

	public void spawnHighYieldAsteroid(int x, int y, int size) throws SlickException {

		float xSpeed = Utility.random(Values.ASTEROID_MIN_SPEED, Values.ASTEROID_MAX_SPEED);
		float ySpeed = Utility.random(Values.ASTEROID_MIN_SPEED, Values.ASTEROID_MAX_SPEED);

		if (Math.random() > .5)
			xSpeed *= -1;
		if (Math.random() > .5)
			ySpeed *= -1;

		Asteroid a = new HighYieldAsteroid(x, y, xSpeed, ySpeed, size);
		Asteroid b;

		if (hasSetPiece()) {
			b = new HighYieldAsteroid(-x, y, -xSpeed, ySpeed, size);
		} else {
			b = new HighYieldAsteroid(-x, -y, -xSpeed, -ySpeed, size);
		}

		a.invertCorner();
		b.invertCorner();

		asteroids.add(a);
		asteroids.add(b);

	}

	public int randomAsteroidSize() {
		double r = Math.random();

		if (r < .25) {
			return Values.ASTEROID_MIN_SIZE;
		} else if (r > .75) {
			return Values.ASTEROID_MAX_SIZE;
		} else {
			return Values.ASTEROID_MID_SIZE;
		}

	}

	public void spawnNebulaRandom(int amount) throws SlickException {
		for (int i = 0; i < amount; i++) {
			int x = Utility.random(-Values.NEBULA_SPAWN_WIDTH, Values.NEBULA_SPAWN_HEIGHT);
			int y = Utility.random(-Values.NEBULA_SPAWN_WIDTH, Values.NEBULA_SPAWN_HEIGHT);
			int size = Utility.random(Values.NEBULA_MIN_SIZE, Values.NEBULA_MAX_SIZE);

			nebulae.add(new Nebula(x, y, size));

			if (hasSetPiece()) {
				nebulae.add(new Nebula(-x, y, size));
			} else {
				nebulae.add(new Nebula(-x, -y, size));
			}
		}

	}

	public void spawnBigStar() throws SlickException {
		BigStar s;
		if (isLegendary()) {
			s = new BigStar(0, Values.HAZARD_Y_POSITION, Values.STAR_LEGENDARY_SIZE);
		} else {
			s = new BigStar(0, Values.HAZARD_Y_POSITION);

		}

		hazards.add(s);
		event = new SolarFlare(neutral, isUnstable(), isLegendary());
		event.linkHazard(s);
	}

	public void spawnPulsar() throws SlickException {
		Pulsar p;
		if (isLegendary()) {
			p = new Pulsar(0, Values.HAZARD_Y_POSITION, Values.PULSAR_LEGENDARY_SIZE);
		} else {
			p = new Pulsar(0, Values.HAZARD_Y_POSITION);
		}

		hazards.add(p);
		event = new Pulse(neutral, isUnstable(), isLegendary());
		event.linkHazard(p);
	}
	
	public void spawnMoon() throws SlickException {
		Moon m;
		if (isLegendary()) {			
			m = new Moon(0,  0, Values.ASTEROID_MAX_SIZE*6);
		} else {
			m = new Moon(0,  0, Values.ASTEROID_MAX_SIZE*4);
		}

		asteroids.add(m);
	}

	public void spawnAsteroidRandom(int amount) throws SlickException {
		for (int i = 0; i < amount; i++) {
			int rX = Utility.random(-Values.ASTEROID_SPAWN_WIDTH / 2, Values.ASTEROID_SPAWN_WIDTH / 2);
			int rY = Utility.random(-Values.ASTEROID_SPAWN_HEIGHT / 2, Values.ASTEROID_SPAWN_HEIGHT / 2);

			if (Asteroid.isValidSpawn(rX, rY))
			{
				spawnAsteroid(rX, rY, randomAsteroidSize());
			}
			else
			{
				i--;
			}

		}
	}

	public void spawnAsteroidRandomHighYield(int amount) throws SlickException {
		for (int i = 0; i < amount; i++) {
			int rX = Utility.random(-Values.ASTEROID_HIGH_YIELD_SPAWN_WIDTH / 2,
					Values.ASTEROID_HIGH_YIELD_SPAWN_WIDTH / 2);

			int rY = Utility.random(-Values.ASTEROID_HIGH_YIELD_SPAWN_HEIGHT / 2,
					Values.ASTEROID_HIGH_YIELD_SPAWN_HEIGHT / 2);

			if (Asteroid.isValidSpawn(rX, rY))
			{
				spawnHighYieldAsteroid(rX, rY, randomAsteroidSize());
			}
			else
			{
				i--;
			}

		}
	}

}

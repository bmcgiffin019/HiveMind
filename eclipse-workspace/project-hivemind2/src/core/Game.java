// added a comment

package core;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import animations.Animation;
import data.Data;
import objects.ambient.Asteroid;
import objects.ambient.Hazard;
import objects.ambient.Nebula;
import objects.base.Player;
import objects.units.BaseShip;
import objects.units.Mine;
import objects.units.Turret;
import objects.units.Unit;
import scenario.Scenario;
import teams.s2.basic.Fs;
import teams.starter.random.Random;
import teams.starter.heavy.Heavy;
import teams.starter.rush.Rush;
import teams.starter.swarm.Swarm;
import ui.Audio;
import ui.Camera;
import ui.Display;
import ui.Starfield;

public class Game extends BasicGameState 
{
	// Common game setting toggles
	public static boolean infoMode = false;		// Displays additional information in the UI.  Press 'i' to toggle in game
	public static boolean sfxOn = true;			// Enable sound effects
	public static boolean musicOn = true;		// Enables music.  Press 'm' to cycle track in game.
	public static boolean basicMode = false;	// Hides complicated events, such as Solar Flares and Pirates
	
	// Player setup
	private void setPlayers() throws SlickException {
		playerOne = new Fs(Values.BLUE_ID, this);
		playerOne.setDifficultyRating(1);			
		
		playerTwo = new Random(Values.RED_ID, this);
		playerOne.setDifficultyRating(1);
	}
	
	private static Camera c;	
	private static int currentFPS;

	private static boolean playerGraphicsBlue = false;
	private static boolean playerGraphicsRed = false;

	private static int gameSpeed = Values.GAME_SPEED;
	private static boolean paused = false;

	private GameContainer gc;
	private StateBasedGame sbg;
	private int id;
	private boolean gameOver;

	private Player playerOne;
	private Player playerTwo;
	private Player playerNeutral;

	private static BaseShip alpha;
	private static BaseShip beta;
	private static BaseShip omega;
	private static ArrayList<Unit> units;

	private static ArrayList<Animation> animations;
	private ArrayList<Nebula> nebulae;
	private ArrayList<Hazard> hazards;
	private ArrayList<Asteroid> asteroids;

	private Scenario scenario;
	private static Display display;

	private static int timer = 0;
	private int gameOverTimer;
	private float screenCenterX;
	private float screenCenterY;

	private Starfield starfield;


	Game(int id) {
		this.id = id;
	}

	public Player getPlayerNeutral() {
		return playerNeutral;
	}

	public void setNeutral(Player p) {
		playerNeutral = p;
	}

	public static int getGameSpeed() {
		return gameSpeed;
	}

	public static boolean isGamePaused() {
		return paused;
	}

	public static int getTime() {
		return timer;
	}

	public static boolean isDrawingPlayerGraphicsBlue() {
		return playerGraphicsBlue;
	}

	public static boolean isDrawingPlayerGraphicsRed() {
		return playerGraphicsRed;
	}

	public static BaseShip getBaseShip(int team) {
		if (team == Values.BLUE_ID)
			return alpha;
		else if (team == Values.RED_ID)
			return beta;
		else if (team == Values.NEUTRAL_ID)
			return omega;
		else
			return null;
	}

	public static ArrayList<Unit> getEnemies(Unit self) {
		return getEnemies(self.getTeam());

	}

	public static ArrayList<Unit> getEnemies(Player self) {
		return getEnemies(self.getTeam());

	}

	public static ArrayList<Unit> getEnemies(int team) {
		ArrayList<Unit> enemies = new ArrayList<Unit>();

		for (Unit a : Game.units) {
			if (a.isAlive() && a.getTeam() != Values.AMBIENT_ID && a.getTeam() != team && a.isTargetable()) {
				enemies.add(a);
			}
		}

		return enemies;
	}

	public static ArrayList<Unit> getAllies(Unit self) {
		ArrayList<Unit> allies = new ArrayList<Unit>();

		for (Unit a : Game.units) {
			if (a.isAlive() && a.getTeam() == self.getTeam() && a != self && a.isTargetable()) {
				allies.add(a);
			}
		}

		return allies;
	}

	public static ArrayList<Unit> getAllies(Player self) {
		ArrayList<Unit> allies = new ArrayList<Unit>();

		for (Unit a : Game.units) {
			if (a.isAlive() && a.getTeam() == self.getTeam() && !(a instanceof Turret)) {
				allies.add(a);
			}
		}
		return allies;
	}

	public static ArrayList<Asteroid> getAsteroids() {
		return Scenario.getAsteroids();
	}

	public static ArrayList<Nebula> getNebulae() {
		return Scenario.getNebulae();
	}

	public static ArrayList<Hazard> getHazards() {
		return Scenario.getHazards();

	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		this.gc = gc;
		this.sbg = sbg;

		// Overall audio volume modifiers
		gc.setSoundVolume(1f);
		gc.setMusicVolume(.7f);

		// Initialize Arrays
		units = new ArrayList<Unit>();

		animations = new ArrayList<Animation>();

		gc.setShowFPS(false);

	}

	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {

		starfield = new Starfield();

		setPlayers();
		playerOne.setStartingMinerals();
		playerTwo.setStartingMinerals();

		gameOver = false;
		timer = 0;
		Data.clear();
		Data.updateName(playerOne.getName(), Values.BLUE_ID);
		Data.updateName(playerTwo.getName(), Values.RED_ID);

		// Spawn Baseships
		alpha = new BaseShip(playerOne);
		beta = new BaseShip(playerTwo);

		
		boolean shiftUp = false;
		if(Math.random() < .5)
		{
			shiftUp = !shiftUp;
		}
		
		alpha.setDriftUp(shiftUp);
		beta.setDriftUp(!shiftUp);
		
		units.add(alpha);
		units.add(beta);

		// Spawn Turrets
		units.add(new Turret(alpha, false));
		units.add(new Turret(alpha, true));
		units.add(new Turret(beta, false));
		units.add(new Turret(beta, true));

		scenario = new Scenario(this);
		scenario.spawn();

		if(scenario.hasSetPiece())
		{
			alpha.shiftOpening();
			beta.shiftOpening();
			
			for(Unit u : units)
			{
				if(u instanceof Turret)
				{
					((Turret) u).shiftOpening();
				}
			}
		}
		
		if (scenario.hasNeutralBaseShip()) {
			omega = scenario.getNeutralBaseShip();
		}
		else
		{
			omega = null;
		}

		nebulae = Scenario.getNebulae();
		hazards = Scenario.getHazards();
		asteroids = Scenario.getAsteroids();

		// Initialize Display Elements
		display = new Display(alpha, beta);
		c = new Camera(gc);
	}

	public void leave(GameContainer gc, StateBasedGame sbg) {
		Data.setBluePosition(alpha.getPosition());
		Data.setRedPosition(beta.getPosition());
		units.clear();
		Scenario.clear();
		nebulae.clear();
		asteroids.clear();
		hazards.clear();
		animations.clear();
		alpha = null;
		beta = null;
		playerOne = null;
		playerTwo = null;
		display = null;
		c = null;
		paused = false;
		gameSpeed = 1;

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		g.setBackground(Color.black);
		g.scale(Camera.getZoom(), Camera.getZoom());
		g.translate(screenCenterX, screenCenterY);

		starfield.render(g);

		for (Hazard h : hazards) {
			h.render(g);
		}

		for (Asteroid a : asteroids) {
			a.render(g);
		}

		drawBorders(g);

		if (playerGraphicsBlue) {
			playerOne.draw(g);
		}
		if (playerGraphicsRed) {
			playerTwo.draw(g);
		}

		for (Animation a : animations) {
			a.render(g);
		}

		for (Unit a : units) {
			a.render(g);
		}

		for (Nebula n : nebulae) {
			n.render(g);
		}

		g.resetTransform();

		display.render(g);


	}

	public void keyPressed(int key, char c) {
		if (key == Input.KEY_1 && !gameOver) {
			gameSpeed = 1;
			sfxOn = true;
		} else if (key == Input.KEY_2 && !gameOver) {
			gameSpeed = 2;
			sfxOn = true;

		} else if (key == Input.KEY_3 && !gameOver) {
			gameSpeed = 3;
			sfxOn = true;

		} else if (key == Input.KEY_4 && !gameOver) {
			gameSpeed = 4;
			sfxOn = true;
		} else if (key == Input.KEY_4 && !gameOver) {
			gameSpeed = 5;
			sfxOn = true;

		} else if (key == Input.KEY_0 && !gameOver) {
			gameSpeed = 10;
			sfxOn = true;

		}
	}

	public void keyReleased(int key, char c) {
		try {
			
			// Music
			if (key == Input.KEY_M) {
				Audio.setRandomGameMusic();
				Audio.playGameMusic();
				display.resetMusicTimer();
			}

			// Restart Game
			if (key == Input.KEY_R && gc.getInput().isKeyPressed(Input.KEY_LSHIFT)) {
				sbg.enterState(Engine.GAME_ID, new FadeOutTransition(Color.black, Values.TRANSITION_FADE_TIME),
						new FadeInTransition(Color.black, Values.TRANSITION_FADE_TIME));
			}

			// End Game Early
			if (key == Input.KEY_T && gc.getInput().isKeyPressed(Input.KEY_LSHIFT)) {
				sbg.enterState(Engine.END_ID, new FadeOutTransition(Color.black, Values.TRANSITION_FADE_TIME),
						new FadeInTransition(Color.black, Values.TRANSITION_FADE_TIME));
			}

			if (key == Input.KEY_SPACE && gameOver) {
				sbg.enterState(Engine.END_ID, new FadeOutTransition(Color.black, Values.TRANSITION_FADE_TIME),
						new FadeInTransition(Color.black, Values.TRANSITION_FADE_TIME));
			}

			// Blue Player Graphics
			if (key == Input.KEY_Q) {
				playerGraphicsBlue = !playerGraphicsBlue;
			}

			// Red Player Graphics
			if (key == Input.KEY_E) {
				playerGraphicsRed = !playerGraphicsRed;
			}

			// Info Mode + Player Graphics
			if (key == Input.KEY_I || key == Input.KEY_F) {
				infoMode = !infoMode;
			}

			// Speeds
			if (key == Input.KEY_SPACE && !gameOver) {
				paused = !paused;
			}

		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		screenCenterX = (float) (Values.RESOLUTION_X / Camera.getZoom() / 2 - Camera.getX());
		screenCenterY = (float) (Values.RESOLUTION_Y / Camera.getZoom() / 2 - Camera.getY());

		if (!paused) {

			for (int j = 0; j < gameSpeed; j++) {

				long startTime;
				long duration;

				// PLAYER ONE TURN
			
				startTime = System.nanoTime();
				playerOne.update();
				for (int i = 0; i < units.size(); i++) {
					if (units.get(i).isAlive() && units.get(i).getOwner() == playerOne) {
						units.get(i).update();
					}
				}
				
				duration = (System.nanoTime() - startTime) / 1000;

				if (timer > 1) {
					playerOne.addLatency(duration);
				}
				
				// PLAYER TWO TURN

				startTime = System.nanoTime();
				playerTwo.update();
				for (int i = 0; i < units.size(); i++) {
					if (units.get(i).isAlive() && units.get(i).getOwner() == playerTwo) {
						units.get(i).update();
					}
				}
				duration = (System.nanoTime() - startTime) / 1000;
				if (timer > 1) {
					playerTwo.addLatency(duration);
				}
				
				playerNeutral.update();
				for (int i = 0; i < units.size(); i++) {
					if (units.get(i).isAlive() && units.get(i).getOwner() == playerNeutral) {
						units.get(i).update();
					}
				}

				// PLAYER THREE TURN
				
				scenario.update();
				
				for (int i = 0; i < animations.size(); i++) {
					animations.get(i).update();
				}

				for (Asteroid a : asteroids) {
					a.update();
				}

				for (Hazard h : hazards) {
					h.update();
				}

				scenario.manageEvents();

				timer++;

				currentFPS = gc.getFPS();

				cleanup();
			}
		}
		c.update();
		display.update(units);

	}

	public void cleanup() {
		for (int i = 0; i < units.size(); i++) {
			Unit a = units.get(i);
			if (a.isDead()) {
				units.remove(i);
				a.die();
				i--;
			}
		}

		for (int i = 0; i < animations.size(); i++) {
			Animation a = animations.get(i);
			if (a.isDone()) {
				animations.remove(i);
				i--;
			}
		}

		if (playerOne.isDefeated() && !gameOver) {
			gameOver = true;
			gameOverTimer = 0;
			gameSpeed = 1;
			gc.setTargetFrameRate(60);

			ArrayList<Unit> units = playerOne.getMyUnits();
			for (Unit u : units) {
				u.die();
			}

		} else if (playerTwo.isDefeated() && !gameOver) {
			gameOver = true;
			gameOverTimer = 0;
			gameSpeed = 1;
			gc.setTargetFrameRate(60);

			ArrayList<Unit> units = playerTwo.getMyUnits();
			for (Unit u : units) {
				u.die();
			}
		}

		if (gameOverTimer == 60) {
			paused = true;
			gameOverTimer = 0;
			gc.setTargetFrameRate(60);
		} else if (gameOver && !paused) {
			gameOverTimer++;
		}

	}
	
	public static void addAnimation(Animation a)
	{
		animations.add(a);
	}

	public Player getOpponent(Player p) {
		if (p.equals(playerOne))
			return playerTwo;
		else
			return playerOne;
	}

	public int getID() {
		return id;
	}

	public void mouseMoved(int oldX, int oldY, int newX, int newY) {
		if (!c.isAuto()) {
			Camera.shiftCamera(newX - oldX, newY - oldY);
		}
	}

	public void addUnit(Unit u) throws SlickException {
		units.add(u);

	}

	public static ArrayList<Mine> getMines() {
		ArrayList<Mine> mines = new ArrayList<Mine>();

		for (Unit u : units) {
			if (u instanceof Mine)
				mines.add((Mine) u);
		}

		return mines;
	}

	public void drawBorders(Graphics g) {
		g.setColor(new Color(100, 0, 100, 40));

		int border = 200;

		// WEST
		g.fillRect(-Values.STARFIELD_WIDTH + border + 100, -Values.STARFIELD_HEIGHT,
				Values.STARFIELD_WIDTH - Values.PLAYFIELD_WIDTH / 2, Values.STARFIELD_HEIGHT * 2);

		// NORTH
		g.fillRect(-Values.STARFIELD_WIDTH, -Values.STARFIELD_HEIGHT + border + 100, Values.STARFIELD_WIDTH * 2,
				Values.STARFIELD_HEIGHT - Values.PLAYFIELD_HEIGHT / 2);

		// EAST
		g.fillRect(Values.PLAYFIELD_WIDTH / 2 - border, -Values.STARFIELD_HEIGHT,
				Values.STARFIELD_WIDTH - Values.PLAYFIELD_WIDTH / 2, Values.STARFIELD_HEIGHT * 2);

		// SOUTH
		g.fillRect(-Values.STARFIELD_WIDTH, Values.PLAYFIELD_HEIGHT / 2 - border, Values.STARFIELD_WIDTH * 2,
				Values.STARFIELD_HEIGHT - Values.PLAYFIELD_HEIGHT / 2);

	}

	public static Point getUnitCenter() {
		Point p = new Point(0, 0);
		int totalWeight = 0;

		for (Unit u : units) {
			float unitX = u.getX() * u.getValue();
			float unitY = u.getY() * u.getValue();
			totalWeight += u.getValue();

			p.setX(p.getX() + unitX);
			p.setY(p.getY() + unitY);

		}

		p.setX(p.getX() / totalWeight);
		p.setY(p.getY() / totalWeight);

		// System.out.println(p.getX());

		return p;
	}

	// for autocam
	public static float getUnitDensity() {
		Point center = getUnitCenter();
		float totalDistance = 0;
		float density = 0;
		float maxDistance = Utility.distance(0, 0, Values.PLAYFIELD_WIDTH / 2, Values.PLAYFIELD_HEIGHT / 2);

		for (Unit u : units) {
			totalDistance += u.getDistance(center) / maxDistance;
		}

		density = totalDistance / units.size();

		return density;
	}

	// for autocamera
	public static Point getUnitCenterExcludeOutliers(Point trueCenter) {
		Point p = new Point(0, 0);
		long totalWeight = 0;

		for (Unit u : units) {
			float distance = u.getDistance(trueCenter) / 1000;
			int seconds = u.getTimer() / 120;

			long unitX = (long) (u.getX() * u.getValue() * seconds * distance);
			long unitY = (long) (u.getY() * u.getValue() * seconds * distance);

			totalWeight += u.getValue() * seconds * distance;
			p.setX(p.getX() + unitX);
			p.setY(p.getY() + unitY);

		}

		p.setX(p.getX() / totalWeight);
		p.setY(p.getY() / totalWeight);

		// System.out.println(p.getX());

		return p;
	}

	public static int getFPS() {
		return currentFPS;
	}

}

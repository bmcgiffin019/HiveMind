package objects.ambient;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Game;
import core.Utility;
import core.Values;
import objects.units.BaseShip;
import objects.units.Miner;
import ui.Camera;
import ui.Fonts;
import ui.Images;

public class Asteroid extends Ambient 
{
	final float MAX_SPEED = .8f;
	final float MIN_SPEED = .1f;
	final float MAX_ROTATION = .02f;
	final float MINING_PERCENT = .25f;
	
	protected float turnSpeed;

	protected int size;
	protected int model;
	protected float minerals;
	protected Miner[] miners;

	public Asteroid(float x, float y, float xSpeed, float ySpeed, int size) throws SlickException {
		super(x, y);

		sheet = Images.asteroid;
		model = Utility.random(0, 3);
		image = sheet.getSprite(model, 0);
		this.size = size;
		scale = 1 + size * .6f;
		w = (int) (image.getWidth() * scale);
		h = (int) (image.getHeight() * scale);
		theta = Utility.random(0, 360);
		turnSpeed = Utility.random(-MAX_ROTATION, MAX_ROTATION);
		minerals = Values.ASTEROID_MINERALS_BASE * size;

		miners = new Miner[size];

		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;

	}
	
	public void invertCorner()
	{
		if(x > 0)
		{
			x = x - image.getWidth() * scale;
		}

		if(y > 0)
		{
			y = y - image.getHeight() * scale;

		}		
	}

	public boolean hasMinerals() {
		return minerals > 0;
	}

	public float getMinerals() {
		return minerals;
	}
	
	public boolean hasMiningSlots() {
		return getCurMiners() < getMaxMiners();
	}

	public float getX() {
		return x - image.getHeight() * scale / 2;
	}

	public float getY() {
		return y - image.getHeight() * scale / 2;
	}
	

	public float getCenterX() {
		return x;
	}

	public float getCenterY() {
		return y;
	}

	public float getXSpeed() {
		return xSpeed;
	}

	public float getYSpeed() {
		return ySpeed;
	}

	public void render(Graphics g) {

		if (image != null) 
		{
			image.setCenterOfRotation(image.getWidth() / 2 * scale, image.getHeight() / 2 * scale);
			image.setRotation(theta);
			image.draw(x-image.getWidth() / 2 * scale, y-image.getHeight() / 2 * scale, scale);

			int labelOpacity = (int) (Camera.getZoom() * 500);
			if (labelOpacity >= 100) {
				g.setColor(new Color(150, 150, 150, labelOpacity));
				g.setFont(Fonts.arial60);
				g.drawString("" + getCurMiners() + "/" + size, getCenterX() - 30, getCenterY() - 30);
			}
		}
	}

	public void update() {
		super.update();
		theta += turnSpeed;

		int proximity = Values.ASTROID_BOUNDARY_PROXIMITY;

		avoidBaseShip(Game.getBaseShip(Values.BLUE_ID));
		avoidBaseShip(Game.getBaseShip(Values.RED_ID));

		if (x < -Values.PLAYFIELD_WIDTH / 2 + proximity) {
			xSpeed += .1;
		}
		if (x > Values.PLAYFIELD_WIDTH / 2 - proximity) {
			xSpeed -= .1;
		}
		if (y < -Values.PLAYFIELD_HEIGHT / 2 + proximity) {
			ySpeed += .1;
		}
		if (y > Values.PLAYFIELD_HEIGHT / 2 - proximity) {
			ySpeed -= .1;
		}

		// Quick little max speed setting to prevent absurdity
		// Create proper asteroid physics later

		if (xSpeed > Values.ASTEROID_MAX_SPEED) {
			xSpeed = Values.ASTEROID_MAX_SPEED;
		} else if (xSpeed < -Values.ASTEROID_MAX_SPEED) {
			xSpeed = -Values.ASTEROID_MAX_SPEED;
		} else if (ySpeed > Values.ASTEROID_MAX_SPEED) {
			ySpeed = Values.ASTEROID_MAX_SPEED;
		} else if (ySpeed < -Values.ASTEROID_MAX_SPEED) {
			ySpeed = -Values.ASTEROID_MAX_SPEED;
		}
		
		
	}

	public static boolean isValidSpawn(int x, int y) {
		return !isSpawnNearBaseShip(x, y, Game.getBaseShip(Values.BLUE_ID)) && !isSpawnNearBaseShip(x, y, Game.getBaseShip(Values.RED_ID));
	}

	public static boolean isSpawnNearBaseShip(int xPos, int yPos, BaseShip b) {
		return Utility.distance(b.getCenterX(), b.getCenterY(), xPos, yPos) < Values.ASTROID_BASE_SHIP_PROXIMITY_SPAWN;
	}

	public void avoidBaseShip(BaseShip b) {

		if (Utility.distance(b, this) < Values.ASTROID_BASE_SHIP_PROXIMITY) 
		{
			if (x < b.getCenterX())
				xSpeed -= .00005;
			if (x > b.getCenterX())
				xSpeed += .00005;
			if (y < b.getCenterY())
				ySpeed -= .00005;
			if (y > b.getCenterY())
				ySpeed += .00005;
		}

	}

	public void extractMinerals(float amount) {
		minerals -= amount;
		if (minerals <= 0) {
			image = sheet.getSprite(model, 1);
			minerals = 0;

			for (int i = 0; i < miners.length; i++) {
				if (miners[i] != null) {
					miners[i].stopMine();
				}
			}
		}
	}

	public void attachMiner(Miner h) {
		for (int i = 0; i < miners.length; i++) {
			if (miners[i] == null) {
				miners[i] = h;
				return;
			}
		}
	}

	public void detachMiner(Miner h) {
		for (int i = 0; i < miners.length; i++) {
			if (miners[i] == h) {
				miners[i] = null;
				return;
			}
		}
	}

	public int getCurMiners() {
		int count = 0;
		for (int i = 0; i < miners.length; i++) {
			if (miners[i] != null)
				count++;
		}

		return count;
	}

	public int getMaxMiners() {
		return size;
	}

	// ASSUMES SQUARE!!!
	public float getMiningRadius() 
	{
		return image.getWidth() * scale * MINING_PERCENT;
	}

}

package objects.base;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import core.Values;

public abstract class GameObject {
	protected SpriteSheet sheet;
	protected Image image;
	protected GameContainer gc;

	protected float scale = 1;
	protected int team = 0;
	protected int timer = 0;
	protected float x = 0;
	protected float y = 0;
	protected int w = 0;
	protected int h = 0;
	protected float theta = 0;
	protected float thetaOld = 0;
	protected float xSpeed = 0;
	protected float ySpeed = 0;
	protected boolean isLocked = false;

	public GameObject(float x, float y, int team) throws SlickException {
		image = null;
		this.x = x;
		this.y = y;
		this.team = team;
	}

	public int getTeam() {
		return team;
	}
	
	public int getEnemyTeam()
	{
		if(team == Values.BLUE_ID)  return Values.RED_ID;
		else if (team == Values.RED_ID) return Values.BLUE_ID;
		else return -2;
	}

	public void render(Graphics g) 
	{
		if (image != null) 
		{
			image.setCenterOfRotation(image.getWidth() / 2 * scale, image.getHeight() / 2 * scale);
			image.setRotation(theta);
			image.draw(x, y, scale);
		}
	}

	public void update() {
		timer++;
		if (theta > 360)
			theta -= 360;
		if (theta < 0)
			theta += 360;

		if (!isLocked) {
			x += xSpeed;
			y += ySpeed;
		}

		if (image != null) {
			w = (int) (image.getWidth() * scale);
			h = (int) (image.getHeight() * scale);
		}

	}

	public boolean isInBounds() {
		return x > -Values.PLAYFIELD_WIDTH / 2 + 5 && x < Values.PLAYFIELD_WIDTH / 2 - 5
				&& y > -Values.PLAYFIELD_HEIGHT / 2 + 5 && y < Values.PLAYFIELD_HEIGHT / 2 - 5;
	}

	public float getX() {
		return x;
	}

	public float getCenterX() {
		return x + w / 2;
	}

	public float getY() {
		return y;
	}

	public float getCenterY() {
		return y + h / 2;
	}
	
	public Point getPosition() {
		return new Point(getCenterX(), getCenterY());
	}

	public float getWidth() {
		return w;
	}

	public float getHeight() {
		return h;
	}

	public float getTheta() {
		return theta;
	}
	
	public float getSpeedX()
	{
		return xSpeed;
	}
	
	public float getSpeedY()
	{
		return ySpeed;
	}

	public Point getLocation()
	{
		return new Point(getCenterX(), getCenterY());
	}



}

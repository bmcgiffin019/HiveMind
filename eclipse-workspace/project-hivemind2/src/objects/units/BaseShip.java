package objects.units;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import core.Game;
import core.Values;
import objects.base.Player;
import ui.Fonts;
import ui.Images;

public final class BaseShip extends Unit {
	float buildQueueTime = 0;
	float buildQueueMax = 0;
	float buildPercent = 0;
	float buildQueueFull = 0;
	boolean moving = false;
	boolean driftUp = false;

	public BaseShip(Player p) throws SlickException {
		super(p);

		sheet = p.getImageBase();
		image = sheet.getSprite(0, team);

		w = (int) (image.getWidth() * scale);
		h = (int) (image.getHeight() * scale);


		
		if (p.getTeam() == Values.BLUE_ID) {
			this.x = -Values.BASE_SHIP_X_POSITION - w / 2;
			this.y = Values.BASE_SHIP_Y_POSITION - h / 2;
		} else if (p.getTeam() == Values.RED_ID) {
			this.x = Values.BASE_SHIP_X_POSITION - w / 2;
			this.y = Values.BASE_SHIP_Y_POSITION - h / 2;
		} else {
			this.x = 0;
			this.y = PIRATE_Y_POSITION - h / 2;
		}

		curHealth = 10000;
		maxHealth = 10000;

		curArmor = Values.BASE_SHIP_ARMOR;
		baseArmor = Values.BASE_SHIP_ARMOR;
		

	}
	
	public void setDriftUp(boolean up)
	{
		driftUp = up;
	}

	
	public void shiftOpening()
	{
		y = Values.BASE_SHIP_Y_POSITION_ALTERNATE;
	}
	
	public boolean driftUp()
	{
		return driftUp;
	}
	public void update() {
		super.update();
//		

		
	//	System.out.println(this.getTeam() +  " " + driftUp);
		buildQueueTime = getOwner().getNextBuildTimeLeft();
		buildQueueMax = getOwner().getNextBuildTimeFull();
		buildQueueFull = getOwner().getTotalBuildTime();

		if (buildQueueMax == 0 || buildQueueTime == 0) {
			buildPercent = 0;
		} else {
			buildPercent = (buildQueueMax - buildQueueTime) / buildQueueMax;
		}

		// Quit out before movement if it's a neutral ship
		if(getTeam() == Values.NEUTRAL_ID)
		{
			return;
		}
		
		if (timer / FRAMES_PER_SECOND >= Values.BASE_SHIP_MOVE_START) {

			if (getCenterX() <= -Values.BASE_SHIP_SPEED || getCenterX() >= Values.BASE_SHIP_SPEED) {
				if (getTeam() == Values.BLUE_ID) {
					x += Values.BASE_SHIP_SPEED;
			
					if(driftUp)
					{
						y-= Values.BASE_SHIP_DRIFT;
					}
					else
					{
						y+= Values.BASE_SHIP_DRIFT;

					}
					
					moving = true;
				} else {
					x -= Values.BASE_SHIP_SPEED;
					
					if(driftUp)
					{
						y-= Values.BASE_SHIP_DRIFT;
					}
					else
					{
						y+= Values.BASE_SHIP_DRIFT;

					}
					
					moving = true;
					
					
				}
			} else {
				moving = false;
			}
		}
	}

	public void render(Graphics g) {

		image.setFilter(Image.FILTER_NEAREST);
		image.setCenterOfRotation(image.getWidth() / 2 * scale, image.getHeight() / 2 * scale);
		//image.setRotation(theta);
		image.draw(x, y, scale);
		
		if (getCurHealth() < getMaxHealth()) {
			g.setColor(Values.COLORS_150_DARK[team]);
			g.fillRect(x, y - 30, w * scale, 20 * scale);
			g.setColor(Values.COLORS_150[team]);
			g.fillRect(x, y - 30, getCurHealth() / getMaxHealth() * w * scale, 20 * scale);
		}

		int secondsOfBuilding = (int) (buildQueueFull / 60.0);

		if (getOwner().isBuilding()) {
			g.setFont(Fonts.calibri32);
			int offX = Fonts.calibri32.getWidth(secondsOfBuilding + "") / 2;
			int offY = Fonts.calibri32.getHeight(secondsOfBuilding + "") / 2;

			int playerOffset = 4;
			if (getTeam() == 1) {
				playerOffset = -4;
			}

			g.setColor(new Color(255, 255, 255));
			g.drawString(secondsOfBuilding + "", x + w / 2 - offX + playerOffset, y + h / 2 - offY + 2);
		}

		draw(g);
	}

	public boolean isMoving() {
		return moving;
	}

	public void action() {

	}

	public void shoot(Unit u) {

	}

	public void shoot() {

	}

	public void ability(Point p) {

	}

	public void ability(Unit u) {

	}

	public void ability() {
	}

	protected void attack() {

	}

	protected void defend() {

	}

	protected void skirmish() {

	}

	protected void special() {

	}

	protected void guard() {

	}

	protected void rally() {

	}

	protected void run() {

	}

	public void draw(Graphics g) {
		// g.setColor(new Color(200, 200, 200, 100));
		// g.drawLine(-Values.PLAYFIELD_WIDTH, y, Values.PLAYFIELD_WIDTH, y);
		// g.drawLine(-Values.PLAYFIELD_WIDTH, y+h, Values.PLAYFIELD_WIDTH, y+h);
		//
		//
		// g.setColor(new Color(200, 200, 100, 100));
		// g.drawLine(-Values.PLAYFIELD_WIDTH, Values.PLAYFIELD_WIDTH,
		// Values.PLAYFIELD_WIDTH, 0);
		// g.drawLine(-Values.PLAYFIELD_WIDTH, -Values.BASE_SHIP_Y_POSITION,
		// Values.PLAYFIELD_WIDTH, -Values.BASE_SHIP_Y_POSITION);
		// g.drawLine(-Values.PLAYFIELD_WIDTH, Values.BASE_SHIP_Y_POSITION,
		// Values.PLAYFIELD_WIDTH, Values.BASE_SHIP_Y_POSITION);
		//
		// g.setColor(new Color(200, 200, 200, 100));
		// g.drawLine(x, -Values.PLAYFIELD_HEIGHT, x, Values.PLAYFIELD_HEIGHT);
		// g.drawLine(x+w, -Values.PLAYFIELD_HEIGHT, x+w, Values.PLAYFIELD_HEIGHT);
		//
		//
		// g.setColor(new Color(200, 200, 100, 100));
		// g.drawLine(0, -Values.PLAYFIELD_HEIGHT, 0, Values.PLAYFIELD_HEIGHT);
		// g.drawLine(-Values.BASE_SHIP_X_POSITION, -Values.PLAYFIELD_HEIGHT,
		// -Values.BASE_SHIP_X_POSITION, Values.PLAYFIELD_HEIGHT);
		// g.drawLine(Values.BASE_SHIP_X_POSITION, -Values.PLAYFIELD_HEIGHT,
		// Values.BASE_SHIP_X_POSITION, Values.PLAYFIELD_HEIGHT);

	}

	final protected void deathTrigger() {
		getOwner().loseGame();

	}

	@Override
	public float getRange() {
		// TODO Auto-generated method stub
		return 0;
	}

}

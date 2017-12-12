package objects.units;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import core.Utility;
import core.Values;
import ui.Images;
import weapons.TurretAttack;

public final class Turret extends Unit {
	private BaseShip owner;
	private TurretAttack basicAttack;
	boolean facingRight;

	public Turret(BaseShip b, boolean facingRight) throws SlickException {
		super(b.getOwner());

		owner = b;
		isUntargetable = true;
		sheet = owner.getOwner().getImageTurret();
		image = sheet.getSprite(0, team);

		basicAttack = new TurretAttack(this);

		if (!facingRight)
			this.x = b.getCenterX() - 112 - image.getWidth() / 2;
		else
			this.x = b.getCenterX() + 112 - image.getWidth() / 2;
		this.y = b.getCenterY() - image.getHeight() / 2;

		curHealth = 100;
		maxHealth = 100;
		maxSpeed = 0f;
		acceleration = .0f;

		this.facingRight = facingRight;

	}

	public void update() {
		super.update();
		if (owner.isDead()) {
			die();
		}

		basicAttack.update();

		if(owner.isMoving())
		{

				if (getTeam() == Values.BLUE_ID) 	x += Values.BASE_SHIP_SPEED;
				else					x -= Values.BASE_SHIP_SPEED;
			
				if(owner.driftUp())
				{
					y-= Values.BASE_SHIP_DRIFT;
				}
				else
				{
					y+= Values.BASE_SHIP_DRIFT;

				}
				
				
		}
		
		if (canAct())
			action();
	}
	
	public void shiftOpening()
	{
		this.y = owner.getCenterY() - image.getHeight() / 2;
	}

	public void shoot(Unit u) {
		if (u == null)
			return;
		turnTo(u);
		basicAttack.use(u);
	}

	public void action() {
		Unit a = nearestEnemy();

		if (a != null && Utility.distance(this, a) < basicAttack.getRange()) {

			turnTo(a);
			shoot(a);
		} else {
			if (facingRight)
				theta = 0;
			else
				theta = 180;
		}
	}

	@Override
	protected void attack() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void defend() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void skirmish() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void special() {
		// TODO Auto-generated method stub

	}

	public void shoot() {

	}

	@Override
	protected void guard() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void rally() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ability(Point p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ability(Unit u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ability() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	final protected void deathTrigger() { }
	
	public float getRange() 
	{
		if(inNebula()) return Values.CONCEALMENT_RANGE;
		else return basicAttack.getRange();
	}


}

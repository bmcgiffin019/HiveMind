package objects.units;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import abilities.MissileExplosion;
import animations.SmokeWhite;
import core.Game;
import core.Values;
import ui.Images;

public final class Missile extends Unit 
{
	boolean detonated = false;
	Unit creator;
	Unit target;
	int timer;
		
	public Missile(Unit creator, Unit target) throws SlickException 
	{
		super(creator.getOwner());
		sheet = creator.getOwner().getImageMissile();
		image = sheet.getSprite(0, team);
		x = creator.getCenterX();
		y = creator.getCenterY();
		xSpeed = creator.getSpeedX();
		ySpeed = creator.getSpeedY();
		maxSpeed = RAIDER_UPGRADE_MISSILE_SPEED;
		acceleration = RAIDER_UPGRADE_MISSILE_ACCELERATION;
		timer = RAIDER_UPGRADE_MISSILE_TIMER;
		dodgeChance = RAIDER_UPGRADE_MISSILE_DODGE_CHANCE;
		curHealth = RAIDER_UPGRADE_MISSILE_HEALTH;
		maxHealth = RAIDER_UPGRADE_MISSILE_HEALTH;
		ability = new MissileExplosion(this);
		this.creator = creator;
		this.target = target;
	}

	public boolean isNearTarget()
	{
		return x >= target.getCenterX() - 1 && 
			   x <= target.getCenterX() + 1 && 
			   y >= target.getCenterY() - 1 && 
			   y <= target.getCenterY() + 1;
	}
	
	public void update()
	{
		super.update();
		
		action();
	}
	
	public void action() 
	{
		timer--;
		
		if(target != null && target.isAlive())
		{
			if(timer % 5 == 0)
			{
				Game.addAnimation(new SmokeWhite(getCenterX(), getCenterY(), 1));
			}
			
			moveTo(target);
		}
	
		
		Unit u = nearestEnemy();
		if(u != null && getDistance(u) < Values.RAIDER_UPGRADE_MISSILE_TRIGGER_RADIUS)
		{
			detonate();
		}	
		
		if(timer == 0)
		{
			detonate();
		}
		

		
	}
	
	final protected void deathTrigger()
	{
		detonate();
	}

	public void detonate()
	{
		if(!detonated)
		{
			detonated = true;
			((MissileExplosion)ability).use();
		}
	}

	protected void attack() {
		// TODO Auto-generated method stub

	}

	protected void defend() {
		// TODO Auto-generated method stub

	}

	protected void skirmish() {
		// TODO Auto-generated method stub

	}

	protected void special() {
		// TODO Auto-generated method stub

	}

	public void shoot() {

	}

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
	public void shoot(Unit u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getRange() {
		// TODO Auto-generated method stub
		return 0;
	}

}

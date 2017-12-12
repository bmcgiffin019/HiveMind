package objects.units;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import abilities.MineExplosion;
import core.Values;
import objects.base.Player;
import ui.Images;

public final class Mine extends Unit 
{
	boolean detonated = false;
	float originX;
	float originY;
	Unit nearest = null;
		
	public Mine(Player p, float xNow, float yNow, float xOrigin, float yOrigin) throws SlickException 
	{
		super(p);
		isUntargetable = true;
		sheet = p.getImageMine();
		image = sheet.getSprite(0, team);
		maxSpeed = 1.0f;
		acceleration = .1f;
		ability = new MineExplosion(this);
		this.x = xNow;
		this.y = yNow;
		this.originX = xOrigin;
		this.originY = yOrigin;
		nearest = nearestEnemy();
	}

	public boolean isNearOrigin()
	{
		return x >= originX - 3 && 
			   x <= originX + 3 && 
			   y >= originY - 3 && 
			   y <= originY + 3;
	}
	
	public void update()
	{
		super.update();
		
		action();
	}
	
	public void action() 
	{
		if(timer % 15 == 0)
		{
			nearest = nearestEnemy();
		}

		if(getDistance(nearest) < Values.MINER_UPGRADE_MINE_AGGRO_RADIUS)
		{
			moveTo(nearest);
		}
		else if(!isNearOrigin())
		{
			moveTo(originX, originY);
		}
		else
		{
			xSpeed = 0;
			ySpeed = 0;
		}
		
		if(nearest != null && getDistance(nearest) < Values.MINER_UPGRADE_MINE_TRIGGER_RADIUS)
		{
			detonate();
		}
		
		rotate(0);
	}

	public void detonate()
	{
		if(!detonated)
		{
			detonated = true;
			((MineExplosion)ability).use();
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
	public void shoot(Unit u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	final protected void deathTrigger() { detonate(); }

	@Override
	public float getRange() {
		// TODO Auto-generated method stub
		return 0;
	}


}

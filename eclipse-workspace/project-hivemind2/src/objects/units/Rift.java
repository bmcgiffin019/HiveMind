package objects.units;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import core.Values;
import objects.base.Player;

public final class Rift extends Unit 
{	
	int timeLeft = 0;
	public Rift(Player p, float x, float y) throws SlickException 
	{
		super(p);
		isUntargetable = true;
		sheet = null;
		image = null;
		maxSpeed = 1.0f;
		acceleration = .1f;
		timeLeft = Values.SPECIALIST_RIFT_DURATION;
		this.x = x;
		this.y = y;
	}


	public void update()
	{
		super.update();
		
		if(timeLeft > 0)
		{
			timeLeft--;
		}
		else
		{
			die();
		}
		
		action();
	}
	
	public void action() 
	{
		ArrayList<Unit> nearby = this.getEnemiesInRadius(Values.SPECIALIST_RIFT_RADIUS);
		
		if(nearby == null)
		{
			return;
		}
		
		for(Unit u : nearby)
		{
			float theta = u.getAngleToward(x, y);
			float amount = Values.SPECIALIST_RIFT_PULL_RATE * ( 1 - (getDistance(u) / Values.SPECIALIST_RIFT_RADIUS));
			
			u.changeSpeed(amount, theta);
			
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

//		g.setColor(new Color(255, 255, 0, 75));
//		g.fillOval(x - Values.SPECIALIST_RIFT_RADIUS,  y - Values.SPECIALIST_RIFT_RADIUS,  Values.SPECIALIST_RIFT_RADIUS*2, Values.SPECIALIST_RIFT_RADIUS*2);
//	
//		g.setColor(new Color(255, 255, 0, 125));
//		g.fillOval(x - Values.SPECIALIST_RIFT_RADIUS/2,  y - Values.SPECIALIST_RIFT_RADIUS/2,  Values.SPECIALIST_RIFT_RADIUS, Values.SPECIALIST_RIFT_RADIUS);
//	
//	
	}
	
	final protected void deathTrigger() {  }


	@Override
	public float getRange() {
		// TODO Auto-generated method stub
		return 0;
	}


}

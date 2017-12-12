package teams.starter.swarm;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Utility;
import objects.ambient.Nebula;
import objects.units.Assault;
import objects.units.Raider;
import objects.units.Unit;

public class SwarmRaider extends Raider {
	Swarm p;

	public SwarmRaider(Swarm p) throws SlickException {
		super(p);
		this.p = p;
	}

	public void action() 
	{	
		Unit a = nearestEnemy();
		shoot(a);
		ability(a);
		

	}
	
	protected void engageNearby()
	{
		Unit a = nearestEnemy();
		if(a != null && Utility.distance(this, a) < 1500)
		{
			if(a instanceof Assault)
			{
				turnTo(a);
				move(180);
			}
			else
			{
				moveTo(a);
			}
		}
	}

	protected void attack()
	{
		engageNearby();
		
		Unit a = nearestEnemy();
		moveTo(a);
	}

	protected void defend() 
	{
		engageNearby();
		moveTo(getHomeBase());
	}

	protected void guard() {

	}

	protected void rally() {
		
		engageNearby();
		

		
		Nebula neb = nearestNebula();
		if(neb != null)
		{
			moveTo(neb.getCenterX(), neb.getCenterY());
		}
		else
		{
			moveTo(-3000, 0);
		}


	}

	protected void skirmish() {

	}

	protected void special() {


	}

	protected void run() {


	}

	public void draw(Graphics g) {

		
	}
}

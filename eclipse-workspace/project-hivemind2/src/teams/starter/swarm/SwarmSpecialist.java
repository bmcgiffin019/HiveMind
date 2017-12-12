package teams.starter.swarm;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import objects.ambient.Nebula;
import objects.units.Specialist;
import objects.units.Unit;

public class SwarmSpecialist extends Specialist {
	Swarm p;

	public SwarmSpecialist(Swarm p) throws SlickException {
		super(p);
		this.p = p;
	}

	public void action() 
	{	
		Unit a = nearestEnemy();
		shoot(a);
	}

	protected void attack()
	{
		Unit a = nearestEnemy();
		moveTo(a);
	}

	protected void defend() 
	{
		moveTo(getHomeBase());
	}

	protected void guard() {

	}

	protected void rally() 
	{
		Nebula neb = nearestNebula();
		
		// Try to hide in the nebula with the team
		if(neb != null)
		{
			float yNebRally = 0;
			
			// Figure out where the top or bottom of a nebula is
			if(neb.getCenterY() < 0)
			{
				yNebRally = neb.getCenterY()+neb.getRadius()+50;
			}
			else
			{
				yNebRally = neb.getCenterY()-neb.getRadius()-50;
			}	
			
			// Move to center top or center bottom
			moveTo(neb.getCenterX(), yNebRally);
		}
		
		// Otherwise move to preset location
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

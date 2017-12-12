package teams.starter.swarm;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import objects.units.Assault;
import objects.units.Unit;

public class SwarmAssault extends Assault {
	Swarm p;

	public SwarmAssault(Swarm p) throws SlickException {
		super(p);
		this.p = p;
	}

	public void action() 
	{
		Unit a = nearestEnemy();

		moveTo(a);
		shoot(a);
	

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
	protected void guard() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void rally() {
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

	@Override
	protected void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}

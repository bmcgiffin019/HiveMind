package teams.starter.rush;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import objects.units.Assault;
import objects.units.Unit;

public class RushAssault extends Assault {
	Rush p;

	public RushAssault(Rush p) throws SlickException {
		super(p);
		this.p = p;
	}

	public void action() 
	{
		Unit a = nearestEnemy();

		moveTo(a);
		shoot(a);

	}

	protected void attack() {
		// TODO Auto-generated method stub

	}

	protected void defend() {
		// TODO Auto-generated method stub

	}

	protected void guard() {
		// TODO Auto-generated method stub

	}

	protected void rally() {
		// TODO Auto-generated method stub

	}

	protected void skirmish() {
		// TODO Auto-generated method stub

	}

	protected void special() {
		// TODO Auto-generated method stub

	}

	protected void run() {
		// TODO Auto-generated method stub

	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}

package teams.starter.rush;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import objects.units.Support;
import objects.units.Unit;

public class RushSupport extends Support {
	Rush p;

	public RushSupport(Rush p) throws SlickException {
		super(p);
		this.p = p;
	}

	public void action() {
		Unit u = nearestAlly();
		moveTo(nearestAllyExclude(Support.class));

		if (u != null && u.isDamaged()) {
			shoot(u);
		}

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
	
	protected void skirmish() {
		// TODO Auto-generated method stub

	}

	protected void rally() {
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

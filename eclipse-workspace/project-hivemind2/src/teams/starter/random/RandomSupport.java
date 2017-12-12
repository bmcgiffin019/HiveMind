package teams.starter.random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import objects.units.Support;
import objects.units.Unit;

public class RandomSupport extends Support {
	Random p;

	public RandomSupport(Random p) throws SlickException {
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
	protected void skirmish() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void rally() {
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

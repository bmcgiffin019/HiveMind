package teams.starter.rush;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import objects.units.Miner;
import objects.units.Raider;
import objects.units.Unit;

public class RushRaider extends Raider {
	Rush p;

	public RushRaider(Rush p) throws SlickException {
		super(p);
		this.p = p;
	}

	public void action() 
	{		
		// Moves toward the nearest enemy Miner
		Unit a = nearestEnemy(Miner.class);
		if(a == null)
		{
			moveTo(nearestAllyExclude(Raider.class));
		}
		else
		{
			moveTo(a);
		}
		
		// Attacks nearest enemy
		Unit b = nearestEnemy();
		shoot(b);
	}

	protected void attack()
	{
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

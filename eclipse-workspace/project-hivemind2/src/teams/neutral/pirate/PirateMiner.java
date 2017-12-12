package teams.neutral.pirate;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import objects.base.Player;
import objects.units.Miner;

public class PirateMiner extends Miner {
	Pirate p;


	public PirateMiner(Player p) throws SlickException {
		super(p);
		this.p = (Pirate) p;
	}
	

	public PirateMiner(Player p, int x, int y) throws SlickException {
		super(p);
		this.p = (Pirate) p;
		this.x = x;
		this.y = y;
	}

	public void action() {
		if (isFull()) {
			moveTo(getHomeBase());
		} else {
			moveTo(nearestOpenAsteroid());
			startMine(nearestOpenAsteroid());
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

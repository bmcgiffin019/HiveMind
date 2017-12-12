package teams.neutral.pirate;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Utility;
import core.Values;
import objects.base.Player;
import objects.units.Assault;
import objects.units.BaseShip;
import objects.units.Miner;
import objects.units.Raider;
import objects.units.Unit;

public class PirateRaider extends Raider {
	Pirate p;
	int squad = 0;
	public PirateRaider(Player p) throws SlickException {
		super(p);
		this.p = (Pirate) p;
	}
	

	public PirateRaider(Player p, int x, int y) throws SlickException {
		super(p);
		this.p = (Pirate) p;
		this.x = x;
		this.y = y;
	}


	public void action()
	{		


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
	protected void guard() 
	{
		Unit a = nearestEnemy();
		shoot(a);
				
		// Move toward my miners
		Unit m = this.nearestAlly(Miner.class);
		if(m != null)
		{
			moveTo(m);
			return;
		}

		// Move toward other enemies
		moveTo(getHomeBase());
	}

	@Override
	protected void rally() 
	{
		Unit a = nearestEnemy();
		shoot(a);
				
		if(getDistance(a) < 1000)
		{
			moveTo(a);
		}

		if(squad == 0)
		{
			moveTo(5000, getHomeBase().getY());
		}
		else
		{
			moveTo(-5000, getHomeBase().getY());
		}
	}
	
	public void setSquad(int i)
	{
		squad = i;
	}

	@Override
	protected void skirmish() 
	{
		Unit a = nearestEnemy();
		shoot(a);
		
		// Avoid Base ships
		if(Utility.distance(this, a) < Values.TURRET_ATTACK_RANGE * 1.5)
		{

			if(a instanceof Assault || a instanceof BaseShip)
			{
				turnTo(a);
				move(180);
				return;
			}
		}
		
		// Move toward miners
		Unit m = this.nearestEnemy(Miner.class);
		if(m != null)
		{
			moveTo(m);
			return;
		}

		// Move toward other enemies
		moveTo(a);

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

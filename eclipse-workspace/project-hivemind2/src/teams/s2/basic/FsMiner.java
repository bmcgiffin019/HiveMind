package teams.s2.basic;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Utility;
import objects.units.Miner;

public class FsMiner extends Miner {
	Fs p;

	/***************** Constructor ***************/
	
	public FsMiner(Fs p) throws SlickException {
		super(p);
		this.p = p;
	}
	
	/***************** Action Method ***************/
	
	public void action() {
		
		// This method is called every frame, BEFORE the order method is called
		
		if (isFull()) {
			moveTo(getHomeBase());
		} else {
			if(Utility.distance(this,nearestOpenAsteroid()) < Utility.distance(this,nearestOpenHighYieldAsteroid())/2)
			{
				moveTo(nearestOpenAsteroid());
				startMine(nearestOpenAsteroid());
			}else {
				moveTo(nearestOpenHighYieldAsteroid());
				startMine(nearestOpenHighYieldAsteroid());
			}
			
			
			
		}
	}
	
	/***************** Order Methods ***************/

	protected void attack() {
		// This method is called every frame while the unit's order is set to ATTACK
	}

	protected void defend() {
		// This method is called every frame while the unit's order is set to DEFEND
	}

	protected void guard() {
		// This method is called every frame while the unit's order is set to GUARD
	}

	protected void rally() {
		// This method is called every frame while the unit's order is set to RALLY
	}

	protected void skirmish() {
		// This method is called every frame while the unit's order is set to SKIRMISH
	}

	protected void special() {
		// This method is called every frame while the unit's order is set to SPECIAL
	}

	protected void run() {
		// This method is called every frame while the unit's order is set to RUN
	}

	/***************** DRAW Methods ***************/
	
	public void draw(Graphics g) {

		// This method allows you to draw things on the screen.  It's only visible if you enable  
		// that player's drawings.  Press 'q' to enable drawings for BLUE and 'e' for RED.
	}
}

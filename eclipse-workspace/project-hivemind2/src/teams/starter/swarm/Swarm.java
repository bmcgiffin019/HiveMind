package teams.starter.swarm;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Game;
import objects.base.Player;
import objects.units.Assault;
import objects.units.Miner;
import objects.units.Raider;
import objects.units.Specialist;
import objects.units.Support;
import objects.units.Unit;
import objects.units.Unit.Order;
import objects.upgrades.AssaultShield;
import objects.upgrades.MinerHull;
import objects.upgrades.MinerLaser;
import objects.upgrades.RaiderEngine;
import objects.upgrades.RaiderMissile;
import objects.upgrades.RaiderPierce;

public class Swarm extends Player
{
	
	boolean makeMiner = true;
	
	public Swarm(int team, Game g) throws SlickException {
		super(team, g);
		setName("Swarm");
		loadImageSet("frame");
	}

	public Raider buildRaider() throws SlickException {
		return new SwarmRaider(this);
	}

	public Miner buildMiner() throws SlickException {
		return new SwarmMiner(this);
	}

	public Assault buildAssault() throws SlickException {
		return new SwarmAssault(this);
	}

	public Specialist buildSpecialist() throws SlickException {
		return new SwarmSpecialist(this);
	}

	public Support buildSupport() throws SlickException {
		return new SwarmSupport(this);
	}

	public void action() throws SlickException 
	{
		// Research all the upgrades;  Swarm Raiders know how to use missiles (but not smartly)
		beginResearch(RaiderPierce.class);
		beginResearch(RaiderEngine.class);
		beginResearch(RaiderMissile.class);
		
		// Also research Mining passive upgrades next
		beginResearch(MinerLaser.class);
		beginResearch(MinerHull.class);

		// If the opponent makes a lot of assaults, make a specialist
		if(countEnemyAssaults() > countMySpecialists() * 3)
		{
			addSpecialistToQueue();
		}
		
		// Standard build: Alternates between making 1 miner and 2 raiders.
		if(makeMiner)
		{
			addMinerToQueue();
			makeMiner = false;
		}
		else
		{
			addRaiderToQueue();
			addRaiderToQueue();
			makeMiner = true;
		}
		
		// If I have more than 12 raiders, go out and attack!
		if(countMyRaiders() >= 12)
		{
			for(Unit u : getAllies())
			{
				u.setOrder(Order.ATTACK);
			}
		}
		else if(countMyRaiders() >= 6 && countMyRaiders() < 12)
		{
			for(Unit u : getAllies())
			{
				u.setOrder(Order.RALLY);
			}
		}
		else
		{
			for(Unit u : getAllies())
			{
				u.setOrder(Order.DEFEND);
			}
		}

	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}

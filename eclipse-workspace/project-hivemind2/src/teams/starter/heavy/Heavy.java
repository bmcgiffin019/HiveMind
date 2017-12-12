package teams.starter.heavy;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Game;
import objects.base.Player;
import objects.units.Assault;
import objects.units.Miner;
import objects.units.Raider;
import objects.units.Specialist;
import objects.units.Support;
import objects.upgrades.AssaultExplosive;
import objects.upgrades.AssaultShield;
import objects.upgrades.MinerHull;
import objects.upgrades.MinerLaser;
import objects.upgrades.SpecialistKinetic;
import objects.upgrades.SpecialistReactor;

public class Heavy extends Player
{
	
	boolean makeMiner = true;
	
	public Heavy(int team, Game g) throws SlickException {
		super(team, g);
		setName("Heavy");
		loadImageSet("frame");
	}

	public Raider buildRaider() throws SlickException {
		return new HeavyRaider(this);
	}

	public Miner buildMiner() throws SlickException {
		return new HeavyMiner(this);
	}

	public Assault buildAssault() throws SlickException {
		return new HeavyAssault(this);
	}

	public Specialist buildSpecialist() throws SlickException {
		return new HeavySpecialist(this);
	}

	public Support buildSupport() throws SlickException {
		return new HeavySupport(this);
	}

	public void action() throws SlickException 
	{
		// Research the passive upgrades for Assault, Specialist, and Miner
		beginResearch(AssaultShield.class);
		beginResearch(AssaultExplosive.class);
		beginResearch(SpecialistReactor.class);
		beginResearch(SpecialistKinetic.class);
		beginResearch(MinerLaser.class);
		beginResearch(MinerHull.class);
		
		// Get up to 5 miners first
		if(countMyMiners() < 5)
		{
			addMinerToQueue();
		}
		
		// Keep a 2:1 ratio of assault : specialist
		else if(countMyAssaults() < countMySpecialists( ) * 2)
		{
			addAssaultToQueue();
		}
		else
		{
			addSpecialistToQueue();

		}

	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}

package teams.starter.random;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Game;
import core.Utility;
import objects.base.Player;
import objects.units.Assault;
import objects.units.Miner;
import objects.units.Raider;
import objects.units.Specialist;
import objects.units.Support;
import objects.units.Unit;
import objects.units.Unit.Order;
import objects.upgrades.AssaultAegis;
import objects.upgrades.AssaultExplosive;
import objects.upgrades.AssaultShield;
import objects.upgrades.MinerHull;
import objects.upgrades.MinerLaser;
import objects.upgrades.RaiderEngine;
import objects.upgrades.RaiderMissile;
import objects.upgrades.RaiderPierce;
import objects.upgrades.SpecialistKinetic;
import objects.upgrades.SpecialistReactor;
import objects.upgrades.SpecialistRift;
import objects.upgrades.SupportEnergy;
import objects.upgrades.SupportFix;

public class Random extends Player {
	int count = 0;
	int r = 0;

	public Random(int team, Game g) throws SlickException {
		super(team, g);
		setName("Random");
		loadImageSet("frame");
	}

	public Raider buildRaider() throws SlickException {
		return new RandomRaider(this);
	}

	public Miner buildMiner() throws SlickException {
		return new RandomMiner(this);
	}

	public Assault buildAssault() throws SlickException {
		return new RandomAssault(this);
	}

	public Specialist buildSpecialist() throws SlickException {
		return new RandomSpecialist(this);
	}

	public Support buildSupport() throws SlickException {
		return new RandomSupport(this);
	}

	public void action() throws SlickException 
	{
		
		// Research all the passive upgrades
		beginResearch(MinerLaser.class);
		beginResearch(MinerHull.class);
		beginResearch(AssaultShield.class);
		beginResearch(AssaultExplosive.class);
		beginResearch(RaiderEngine.class);
		beginResearch(RaiderPierce.class);
		beginResearch(SpecialistReactor.class);
		beginResearch(SpecialistKinetic.class);
		beginResearch(SupportEnergy.class);
		beginResearch(SupportFix.class);
		beginResearch(SupportEnergy.class);
		
		// Always make 4 miners at the start
		if(timer == 1)
		{
			addMinerToQueue();
			addMinerToQueue();
			addMinerToQueue();
			addMinerToQueue();
			addMinerToQueue();
			addMinerToQueue();
		}

		// Choose a new unit to produce and specify how many to build
		if (count == 0) {
			r = Utility.random(0, 5);

			switch (r) {
			case 0: // Raiders
				count = 7;
				break;
			case 1: // Miners
				count = 1;
				break;
			case 2: // Assault
				count = 3;
				break;
			case 3: // Support
				count = 2;
				break;
			case 4: // Specialist
				count = 1;
				break;
			}
		}

		// Build units and count down to new selection
		switch (r) {
		case 0:
			setMessageOne("Raiders Mode");
			if (addRaiderToQueue())
				count--;
			break;

		case 1:
			setMessageOne("Miner Mode");
			if (addMinerToQueue())
				count--;
			break;

		case 2:
			setMessageOne("Assault Mode");
			if (addAssaultToQueue())
				count--;
			break;

		case 3:
			setMessageOne("Support Mode");
			if (addSupportToQueue())
				;
				count--;
			break;

		case 4:
			setMessageOne("Specialist Mode");
			if (addSpecialistToQueue())
				count--;
			break;

		}

		for (Unit u : getAllies()) {
			setMessageTwo("Attack");
			u.setOrder(Order.ATTACK);
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}

package teams.starter.rush;

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
import objects.upgrades.SpecialistKinetic;
import objects.upgrades.SpecialistReactor;

public class Rush extends Player {

	boolean makeMiner = true;

	public Rush(int team, Game g) throws SlickException {
		super(team, g);
		setName("Rush");
		loadImageSet("frame");
	}

	public Raider buildRaider() throws SlickException {
		return new RushRaider(this);
	}

	public Miner buildMiner() throws SlickException {
		return new RushMiner(this);
	}

	public Assault buildAssault() throws SlickException {
		return new RushAssault(this);
	}

	public Specialist buildSpecialist() throws SlickException {
		return new RushSpecialist(this);
	}

	public Support buildSupport() throws SlickException {
		return new RushSupport(this);
	}

	public void action() throws SlickException {
		// Research passive upgrades for relevant units
		beginResearch(RaiderPierce.class);
		beginResearch(RaiderEngine.class);
		beginResearch(SpecialistReactor.class);
		beginResearch(SpecialistKinetic.class);
		beginResearch(MinerLaser.class);
		beginResearch(MinerHull.class);

		// Make 2 miners, then make more if I get a LOT of specialists
		if (countMyMiners() < 2 || countMySpecialists() > countMyMiners()) {
			addMinerToQueue();
		}
		
		// Keep a 4:1 ratio of raider : specialist
		else if (countMyRaiders() < countMySpecialists() * 4) {
			addRaiderToQueue();
		} else {
			addSpecialistToQueue();

		}

	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}
}

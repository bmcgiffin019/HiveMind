package teams.neutral.pirate;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import core.Game;
import core.Utility;
import core.Values;
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
import ui.Alert;

public class Pirate extends Player {
	
	boolean alerted = false;
	float escalation = 1f;
	
	public Pirate(int team, Game g) throws SlickException {
		super(team, g);
		setName("Pirate");
		loadImageSet("pirate");
	}

	public Raider buildRaider() throws SlickException {
		return new PirateRaider(this);
	}

	public Miner buildMiner() throws SlickException {
		return new PirateMiner(this);
	}

	public Assault buildAssault() throws SlickException {
		return new PirateAssault(this);
	}

	public Specialist buildSpecialist() throws SlickException {
		return new PirateSpecialist(this);
	}

	public Support buildSupport() throws SlickException {
		return new PirateSupport(this);
	}

	public void action() throws SlickException {
		if (countMyRaiders() > countMyMiners() * 4 || countMyMiners() < 3) {
			addMinerToQueue();
		} else if (countMyRaiders() > countMySupports() * 7) {
			addSupportToQueue();
		} else {
			addRaiderToQueue();
		}

		if (getMyBase() == null) {
			setSkirmishMode();
		} else if (countMyRaiders() > 24 * escalation) {
			setSkirmishMode();
		} else if (countMyRaiders() > 16 * escalation) {
			setRallyMode();
		} else {
			setGuardMode();
		}

	}

	public void setSkirmishMode() {
		ArrayList<Unit> units = getAllies();
		for (Unit u : units) {
			u.setOrder(Order.SKIRMISH);
		}
		
		if(!alerted)
		{
			if(timer > 1)
			{
				Alert.set(Values.ALERT_STANDARD, 200,  0,  255);
			}

			alerted = true;
		}		
	}

	public void setGuardMode() {
		ArrayList<Unit> units = getAllies();
		for (Unit u : units) {
			u.setOrder(Order.GUARD);
		}
		
		
		if(alerted)
		{
			// gather up 20% more forces each time you skirmish
			escalation += .2;
			alerted = false;
		}
	}

	public void setRallyMode() {
		ArrayList<Unit> units = getAllies();

		for (int i = 0; i < units.size(); i++) {
			if (units.get(i) instanceof PirateRaider) {
				if (i % 2 == 0) {
					((PirateRaider) units.get(i)).setSquad(0);
				} else {
					((PirateRaider) units.get(i)).setSquad(1);
				}
				units.get(i).setOrder(Order.RALLY);
			}

		}
		
		if(alerted)
		{
			// gather up 20% more forces each time you skirmish
			escalation += .2;
			alerted = false;
		}
	}


	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

}

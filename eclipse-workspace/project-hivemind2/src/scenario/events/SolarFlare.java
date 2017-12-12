package scenario.events;

import java.util.ArrayList;

import animations.circles.AnimFlare;
import core.Game;
import core.Utility;
import core.Values;
import effects.DamageArea;
import effects.DamageIgnoreArmor;
import objects.ambient.BigStar;
import objects.base.Player;
import objects.units.Unit;
import ui.Alert;

public class SolarFlare extends Event {
	private int range;
	private Player player;
	
	public SolarFlare(Player player, boolean unstable, boolean legendary) {
		int r = (int) Utility.random(Values.STAR_SOLAR_FLARE_COOLDOWN * -.1, Values.STAR_SOLAR_FLARE_COOLDOWN * .1);
		countdown = Values.STAR_SOLAR_FLARE_COOLDOWN + r;
		span = countdown;
		quick = unstable;
		strong = legendary;

		range = Values.STAR_SOLAR_FLARE_RANGE;

		if (strong) {
			range *= 1.5;
		}
		this.player = player;
	}

	void activate() 
	{
		ArrayList<Unit> units = Game.getEnemies(Values.NEUTRAL_ID);

		for (Unit u : units) {
			float distance = Utility.distance(u, owner);
			if (distance < range) {
				int delay = (int) ((distance / range) * Values.STAR_SOLAR_FLARE_DURATION);

				int damage = Values.STAR_SOLAR_FLARE_DAMAGE;
				if (strong) {
					damage *= 1.5;
				}

				u.addEffect(new DamageArea(u, player, delay, damage));

			}
		}

		int r = (int) Utility.random(Values.STAR_SOLAR_FLARE_COOLDOWN * -.1, Values.STAR_SOLAR_FLARE_COOLDOWN * .1);
		countdown = Values.STAR_SOLAR_FLARE_COOLDOWN + r;
		span = countdown;

		Game.addAnimation(new AnimFlare(owner.getCenterX(), owner.getCenterY(), range));
		
		Alert.set(Values.ALERT_STANDARD, 255, 100, 0);

	}
}

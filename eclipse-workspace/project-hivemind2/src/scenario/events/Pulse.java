package scenario.events;

import java.util.ArrayList;

import animations.circles.AnimFlare;
import animations.circles.AnimPulse;
import core.Game;
import core.Utility;
import core.Values;
import effects.DamageArea;
import effects.DamageIgnoreArmor;
import effects.Stunned;
import objects.ambient.BigStar;
import objects.base.Player;
import objects.units.Unit;
import ui.Alert;

public class Pulse extends Event {
	private int range;
	private int effect;
	private int cooldown;
	Player player;
	
	public Pulse(Player player, boolean unstable, boolean legendary) {
		int r = (int) Utility.random(Values.PULSAR_PULSE_COOLDOWN * -.1, Values.PULSAR_PULSE_COOLDOWN * .1);
		countdown = Values.PULSAR_PULSE_COOLDOWN + r;
		span = countdown;
		quick = unstable;
		strong = legendary;
		effect = Values.PULSAR_PULSE_STUN_DURATION;
		range = Values.PULSAR_PULSE_RANGE;
		cooldown = Values.PULSAR_PULSE_COOLDOWN;
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
				int delay = (int) ((distance / range) * Values.PULSAR_PULSE_DURATION);

				if (strong) {
					effect *= 1.5;
				}

				u.addEffect(new Stunned(u, player, delay, effect));

			}
		}

		int r = (int) Utility.random(cooldown * -.1, cooldown * .1);
		countdown = cooldown + r;
		span = countdown;

		Game.addAnimation(new AnimPulse(owner.getCenterX(), owner.getCenterY(), range));
		Alert.set(Values.ALERT_STANDARD, 180, 180, 255);

	}
}

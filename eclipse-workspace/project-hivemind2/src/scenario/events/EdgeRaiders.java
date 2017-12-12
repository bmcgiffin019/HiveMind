package scenario.events;

import java.util.ArrayList;

import animations.circles.AnimFlare;
import core.Game;
import core.Utility;
import core.Values;
import effects.DamageArea;
import effects.DamageIgnoreArmor;
import objects.ambient.BigStar;
import objects.units.Unit;

public class EdgeRaiders extends Event {
	int range;

	public EdgeRaiders()
	{
		int r = (int) Utility.random(Values.STAR_SOLAR_FLARE_COOLDOWN * -.1, Values.STAR_SOLAR_FLARE_COOLDOWN * .1);
		countdown = Values.STAR_SOLAR_FLARE_COOLDOWN + r;
		span = countdown;

	}

	void activate() 
	{
		
		

		int r = (int) Utility.random(Values.STAR_SOLAR_FLARE_COOLDOWN * -.1, Values.STAR_SOLAR_FLARE_COOLDOWN * .1);
		countdown = Values.STAR_SOLAR_FLARE_COOLDOWN + r;
		span = countdown;

	}
}

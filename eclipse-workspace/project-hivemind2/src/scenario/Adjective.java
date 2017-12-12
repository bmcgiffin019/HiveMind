package scenario;

import core.Utility;

public enum Adjective {
	SPARSE, AVERAGE, POOR, BARREN, RICH, ABUNDANT,  // Basic effects
	HIDDEN, SHROUDED, 								// Nebulae
	UNSTABLE, LEGENDARY, 							// Star / Pulsar
	RELENTLESS;										// Neutrals
	

	// Legendary --> Doubles the effect of the noun... 2x damage star, etc ?

	public static Adjective getRandom() {
		return Adjective.values()[(Utility.random(Adjective.values().length))];
	}
	
	public static Adjective getBasic()
	{
		Adjective[] nouns = {Adjective.SPARSE, Adjective.AVERAGE, Adjective.POOR, Adjective.RICH, Adjective.ABUNDANT, Adjective.BARREN};
		return nouns[Utility.random(nouns.length)];
	}


	public String toString() {
		String word = super.toString().toLowerCase();
		String first = word.toUpperCase().substring(0, 1);
		word = first + word.substring(1, word.length());

		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == '_') {
				word = word.substring(0, i) + " " + word.substring(i + 1, i + 2).toUpperCase()
						+ word.substring(i + 2, word.length());
			}

		}

		return word;
	}
	
	public static Noun getLegendaryNoun()
	{
		switch (Utility.random(4)) {
		case 0:
			return Noun.STAR;
		case 1:
			return Noun.PULSAR;
		case 2:
			return Noun.ASTEROID_BELT;
		case 3:
			return Noun.MOON;
		default:
			return Noun.VOID;	
		}
	}
	
	public static Noun getUnstableNoun()
	{
		switch (Utility.random(2)) {
		case 0:
			return Noun.STAR;
		case 1:
			return Noun.PULSAR;
		default:
			return Noun.VOID;	
		}
	}
	
	public static Noun getRelentlessNoun()
	{
		switch (Utility.random(1)) {
		case 0:
			return Noun.PIRATES;		// doesn't work though!
		default:
			return Noun.VOID;	
		}
	}
}

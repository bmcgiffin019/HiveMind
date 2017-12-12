package scenario;

import core.Utility;

public enum Battle {
	BATTLE_OF, WAR_OF, FIGHT_FOR, SKIRMISH_FOR, ASSAULT_ON, DUEL_OF, STRIKE_ON, ENCOUNTER_AT, CLASH_AT, DEFENSE_OF, 
	SHOWDOWN_AT, HAVOC_AT, CONFLICT_OVER, BRAWL_OVER, SCRAP_ABOUT, STRIFE_CONCERNING, QUARREL_OVER, RUMBLE_OF, 
	TURMOIL_IN_REGARDS_TO, TROUBLE_WITH, 
	
	CONFLAGRATION_REGARDING, TIFF_OVER, CONFUSION_CONCERNING;

	public static Battle getRandom() {
		// likely
		int r = Utility.random(Battle.values().length);

		return Battle.values()[(Utility.random(r))];
	}

	public String toString() {
		String word = super.toString().toLowerCase();
		String first = word.toUpperCase().substring(0, 1);
		word = first + word.substring(1, word.length());

		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == '_') {
				word = word.substring(0, i) + " " + word.substring(i + 1, word.length());
			}
		}

		return word;
	}
}

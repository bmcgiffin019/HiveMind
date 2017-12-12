package scenario.events;

import objects.ambient.Hazard;

public abstract class Event {
	int countdown = -1;
	int span = -1;
	int timeSinceLastEvent = 0;
	boolean hadEvent = false;
	boolean quick = false;
	boolean strong = false;

	Hazard owner;

	Event() {

	}

	public int getCountdown() {
		return countdown;
	}

	public void update() {
		if (countdown > 0) {
			countdown--;
 
			// Quick events go twice as fast
			if (quick && countdown > 0) {
				countdown--;
			}

			if (hadEvent) {
				timeSinceLastEvent++;
			}

		}
		if (countdown == 0) {
			activate();

			if (owner != null) {
				timeSinceLastEvent = 0;
				owner.updateTimeSinceLastEvent(0);
				owner.hadFirstEvent();
				hadEvent = true;
			}
		}

		if (owner != null && span > 0 && countdown > 0) {
			owner.updateProgress(getProgress());
			owner.updateTimeSinceLastEvent(timeSinceLastEvent);

		}
	}

	public void linkHazard(Hazard h) {
		owner = h;
	}

	public float getProgress() {
		return (float) (span - countdown) / (float) span;
	}

	abstract void activate();

}

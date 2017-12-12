package effects;

import objects.base.Player;
import objects.units.Unit;

public class Effect {
	Unit u;
	Player source;
	
	int timer;
	int duration;
	int tick = 30;
	int delay = 1;
	float damageInitial = 0;
	float damagePeriodic = 0;
	float healInitial = 0;
	float healPeriodic = 0;
	float damageReduction = 0;
	float slowValue = 0;
	boolean hasPeriodic = false;
	boolean hasInitial = false;
	boolean isPositive = false;
	
	public Effect(Unit u, Player source) 
	{
		this.u = u;
		this.source = source;
		duration = delay + 1;

	}

	public boolean slowsMove() {
		return false;
	}

	public float reducesSpeedPercent()
	{
		return slowValue;
	}
	
	public boolean isPositive()
	{
		return isPositive;
	}
	public boolean stopsMove() {
		return false;
	}

	public boolean stopsAction() {
		return false;
	}

	public boolean shattersArmor() {
		return false;
	}
	
	public boolean stopsDamage()	{
		return false;
	}

	public boolean reducesDamage()	{
		return false;
	}
	public float reducesDamagePercent()	{
		return damageReduction;
	}
	
	public boolean isActive() {
		return timer >= delay;
	}

	public boolean isExpired() {
		return duration != -1 && timer >= duration;
	}

	public boolean isInitial() {
		return hasInitial && timer == delay;
	}

	public boolean isPeriodic() {
		return hasPeriodic && (timer - delay) % tick == 0;
	}

	public void initialEffect() {
		if (damageInitial > 0)
			u.takeDamage(damageInitial, source);
		if (healInitial > 0)
			u.regainHealth(healInitial);

		// System.out.println("heal initial in effect: " + healInitial);

	}

	public void periodicEffect() {
		if (damagePeriodic > 0)
			u.takeArmorPiercingDamage(damagePeriodic, source);
		if (healPeriodic > 0)
			u.regainHealth(healPeriodic);

	}

	public void update() {
		timer++;

		if (isInitial()) {
			initialEffect();
		}

		if (isPeriodic()) {
			periodicEffect();
		}

	}
	
	public void reduceDuration(int amount)
	{
		timer += amount;
	}
}

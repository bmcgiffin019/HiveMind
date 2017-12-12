package abilities;

import java.util.ArrayList;

import org.newdawn.slick.geom.Point;

import objects.units.Unit;

public abstract class AbilityArea extends Ability {
	public AbilityArea(Unit owner) {
		super(owner);
	}

	public void use(Point center) 
	{
		ArrayList<Unit> units = owner.getEnemiesInRadius(getRadius());

		if (units != null) {
			for (Unit e : units) {
				hit(e);
			}
		}
	}

	abstract protected void hit(Unit u);

	abstract protected int getRadius();

	protected Point getCenter() {
		return new Point(owner.getCenterX(), owner.getCenterY());
	}

}

package core;

import java.util.ArrayList;

import org.newdawn.slick.geom.Point;

import objects.base.GameObject;
import objects.units.Unit;

public class Utility {
	public static int random(int max) {
		return (int) (Math.random() * max);
	}

	public static int random(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;

	}

	public static float random(double min, double max) {
		return (float) (min + (Math.random() * (max - min)));
	}

	public static float distance(GameObject a, GameObject b) {
		if (a == null || b == null)
			return -1;
		return distance(a.getCenterX(), a.getCenterY(), b.getCenterX(), b.getCenterY());
	}
	
	public static float distance(Point a, Point b) {
		if (a == null || b == null)
			return -1;
		return distance(a.getCenterX(), a.getCenterY(), b.getCenterX(), b.getCenterY());
	}

	public static float distance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
	
	public static boolean isNear(float x1, float y1, float x2, float y2)
	{
		return x1 >= x2 - 1 && 
			   x1 <= x2 + 1 && 
			   y1 >= y2 - 1 && 
			   y2 <= y2 + 1;
	}
	
	public static boolean isNear(Unit u, float x, float y)
	{
		return isNear(u.getCenterX(), u.getCenterY(), x, y);
	}
	
	public static boolean isNear(Unit u, Unit v)
	{
		return isNear(u.getCenterX(), u.getCenterY(), v.getCenterX(), v.getCenterY());
	}

	public static boolean hasInstance(ArrayList<Unit> units, Class<? extends Unit> clazz) {
		for (Unit u : units) {
			if (clazz.isAssignableFrom(u.getClass())) {
				return true;
			}
		}
		return false;
	}
	
}

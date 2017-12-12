package data;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;

public class Data 
{
	private static int winnerID = 1;
	private static ArrayList<Event> events = new ArrayList<Event>();
	private static String[] names = new String[3];
	private static int[] damage = new int[3];
	private static int[] heal = new int[3];
	private static int[] block = new int[3];
	private static int[] attempts = new int[3];
	private static int[] production = new int[3];
	private static Point bluePos = new Point(0, 0);
	private static Point redPos = new Point(0, 0);

	public static void clear()
	{
		events.clear();
		Heatmap.clear();
	}
	public static void addEvent(Event e)
	{
		events.add(e);
		
		if(e instanceof DeathEvent)
		{
			Heatmap.addDeath((DeathEvent)e);
		}
	}
	
	public static void renderHeatmap(int x, int y, Graphics g)
	{
		Heatmap.render(x, y, g);

	}
	public static void calculate()
	{
		damage[0] = 0;
		damage[1] = 0;
		heal[0] = 0;
		heal[1] = 0;
		block[0] = 0;
		block[1] = 0;
		attempts[0] = 0;
		attempts[1] = 0;
		production[0] = 0;
		production[1] = 0;
		
		for(Event e : events)
		{
			if(e instanceof DamageEvent)
			{
				addDamage((DamageEvent)e);
			}		
			if(e instanceof DamageAttemptEvent)
			{
				addDamageAttempt((DamageAttemptEvent)e);
			}	
			if(e instanceof HealEvent)
			{
				addHeal((HealEvent)e);
			}		
			if(e instanceof BlockEvent)
			{
				addBlock((BlockEvent)e);
			}	
			if(e instanceof BuildEvent)
			{
				addProduction((BuildEvent)e);
			}		
			if(e instanceof UpgradeEvent)
			{
				addProduction((UpgradeEvent)e);
			}		
		}
	}
	
	
	public static String getWinner()
	{
		return names[winnerID];
	}
	
	public static int getWinnerID()
	{
		return winnerID;
	}
	
	public static void setWinner(int id)
	{
		winnerID = id;
	}
	public static Point getBluePosition()
	{
		return bluePos;
	}
	public static Point getRedPosition()
	{
		return redPos;
	}
	public static void setBluePosition(Point p)
	{
		bluePos = p;
	}
	
	public static void setRedPosition(Point p)
	{
		redPos = p;
	}
	// Names
	public static String getName(int playerID)
	{
		return names[playerID];
	}
	public static void updateName(String name, int playerID)
	{
		names[playerID] = name;
	}
	
	// Damage
	public static int getDamage(int playerID)
	{
		return damage[playerID];
	}
	public static int getDamageTotal()
	{
		return damage[0] + damage[1];
	}
	
	public static float getDamagePercent(int playerID)
	{
		return ((float) damage[playerID]) / ((float) getDamageTotal());
	}
	private static void addDamage(DamageEvent e)
	{
		if(e.getPlayerID() <= damage.length)
		{
		damage[e.getPlayerID()] += e.getDamage();
		}
	}
	
	// Damage Attempts
	private static void addDamageAttempt(DamageAttemptEvent e)
	{
		attempts[e.getPlayerID()] += e.getDamageAttempt();
	}
	
	
	// Healing
	public static int getHeal(int playerID)
	{
		return heal[playerID];
	}
	private static void addHeal(HealEvent e)
	{
		if(e.getPlayerID() <= heal.length)
		{
		heal[e.getPlayerID()] += e.getHeal();
		}
	}
	
	public static float percentOfDamageHealed(int playerID)
	{
		return ((float) heal[playerID]) / ((float) damage[0] + damage[1]);
	}
	
	// Blocking
	public static int getBlock(int playerID)
	{
		return block[playerID];
	}
	private static void addBlock(BlockEvent e)
	{
		block[e.getPlayerID()] += e.getBlock();
	}
	public static float percentOfAttemptedDamageBlocked(int playerID)
	{
		return ((float) block[playerID]) / ((float) attempts[0] + attempts[1]);
	}
	
	// Production

	public static int getProduction(int playerID)
	{
		return production[playerID];
	}
	
	public static float getProductionPercent(int playerID)
	{
		return ((float) production[playerID]) / ((float) production[0] + production[1]);
	}
	private static void addProduction(BuildEvent e)
	{
		production[e.getPlayerID()] += e.getValue();
	}
	
	private static void addProduction(UpgradeEvent e)
	{
		production[e.getPlayerID()] += e.getValue();
	}
	
	// Death Events
	

}

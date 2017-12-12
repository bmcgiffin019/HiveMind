package data;

public class Event 
{
	private int time;
	private int player;

	public Event(int player, int time)
	{
		this.time = time;
		this.player = player;
	}
	
	public int getTime()
	{
		return time;
	}
	
	public int getPlayerID()
	{
		return player;
	}
	
	// TYPES
	
	//every event has a timestamp
	
		// STATE EVENT
		// Produce 	who		type
		// Destroy	who		type
	
		// ACTION EVENT
		// Mine		who		amount			light aqua
		// Damage	who		amount			reddish purple
		// Heal		who		amount			yellow
		// Block	who		amount			green
	
	


}

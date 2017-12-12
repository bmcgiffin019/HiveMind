package data;

// Represents when your unit damages another unit
public class BlockEvent extends ActionEvent
{
	int block;
	
	public BlockEvent(int player, int time, int damage) 
	{
		super(player, time);
		this.block = damage;
		
	}

	public int getBlock()
	{
		return block;
	}
}

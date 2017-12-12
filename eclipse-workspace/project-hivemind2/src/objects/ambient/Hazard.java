package objects.ambient;

import org.newdawn.slick.SlickException;

public class Hazard extends Ambient
{
	protected float progress = 0;
	protected int timeSinceLastEvent = 0;
	protected boolean hadEvent = false;
	public Hazard(int xPos, int yPos) throws SlickException
	{
		super(xPos, yPos);
	}
	
	public void updateProgress(float percent)
	{
		progress = percent;
	}
	
	public void updateTimeSinceLastEvent(int frames)
	{
		timeSinceLastEvent = frames;
	}
	
	public void hadFirstEvent()
	{
		hadEvent = true;
	}
}

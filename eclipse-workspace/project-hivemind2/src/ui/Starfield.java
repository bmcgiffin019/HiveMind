package ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import core.Values;

public class Starfield 
{
	ArrayList<Star> stars;
	int rows;
	int cols;
	final int SCALE = 10;
	final float CHANCE = .005f;
	final int STAR_BASE_BRIGHTNESS = 750;
	final int STAR_MAX_BRIGHTNESS = 140;
	
	public Starfield()
	{
		
		rows = Values.STARFIELD_WIDTH/SCALE;
		cols = Values.STARFIELD_HEIGHT/SCALE;
		
		stars = new ArrayList<Star>();
		
		for(int x = 0; x < rows; x++)
		{
			for(int y = 0; y < cols; y++)
			{
				if(Math.random() < CHANCE)
				{
					
					double r = Math.random();
					int s = 2;
					
					if(r > .40 && r <= .70)
					{
						s = 3;
					}
					else if(r > .70 && r <= .90)
					{
						s = 4;
					}
					else if(r > .90)
					{
						
						s = 5;
					}
						
					stars.add(new Star(x * SCALE, y * SCALE, s));
				}				
			}
		}
	}
	
	public void render(Graphics g)
	{	
		float cameraRange = Camera.ZOOM_MAX - Camera.ZOOM_MIN;
		int lightLevel = Integer.min(STAR_MAX_BRIGHTNESS, (int) ((Camera.getZoom() - Camera.ZOOM_MIN) / cameraRange * STAR_BASE_BRIGHTNESS));
		
		
		g.setColor(new Color(lightLevel, lightLevel, lightLevel));
		
		for(Star s : stars)
		{
			s.render(g);
		}
	}
	
	class Star
	{
		Star(int x, int y, int size)
		{
			this.x = x;
			this.y = y;
			this.size = size;
		}
		
		public float x;
		public float y;
		public int size;
		
		public void render(Graphics g)
		{
			g.fillRect(x - Values.STARFIELD_WIDTH/2, y - Values.STARFIELD_HEIGHT/2, size, size);
		}
		
	}


}

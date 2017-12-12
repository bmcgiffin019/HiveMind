package objects.ambient;


import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import animations.Animation;
import core.SimplexNoise;
import core.Utility;
import core.Values;
import objects.units.Unit;

import java.io.*;

public class Nebula extends Ambient
{
	private double featureSize = 600;
	private int size;
	private Image image;
	private Graphics imageG;
	private int radius;
	
	public Nebula(int xPos, int yPos) throws SlickException
	{
		this(xPos, yPos, 3000);
	}
	
	public Nebula(int xPos, int yPos, int size) throws SlickException
	{
		super(xPos-size/2, yPos-size/2);
		this.size = size;
		featureSize = size/5;
		w = size;
		h = size;
		radius = size/4;
		
		// Set up the image and graphics object
		try 
		{
			image = new Image(size,size);
			imageG = image.getGraphics();
		} catch (SlickException e) {
			Log.error("creating local image or graphics context failed: " + e.getMessage());
		}
				

//		int r = Utility.random(255);
//		int g = Utility.random(255);
//		int b = Utility.random(255);
		
		for(int max = 255; max > 0; max -= 50)
		{
			constructLayer(Utility.random(max/2, max), Utility.random(max/2, max), Utility.random(max/2, max), (float)max/255.0f);
			featureSize *= .7;
//			r += Utility.random(-75, 25);
//			g += Utility.random(-75, 25);
//			b += Utility.random(-75, 25);

		}
		
	}
	
	public void constructLayer(int r, int g, int b, float alpha)
	{
		SimplexNoise noise = new SimplexNoise(Utility.random(100000));
		
		int step = 15;
		
		for (int y = 0; y < image.getHeight(); y += step)
		{
			for (int x = 0; x < image.getWidth(); x += step)
			{
			double value = noise.eval(x / featureSize, y / featureSize);		
			double scaled = (double) ((value + 1) / 2);
			
			// Reduce stuff on edges
			float distanceToCenter = Utility.distance(x, y, size/2, size/2);
	
			// Subtract if it's far from center

			scaled = scaled - Math.pow(distanceToCenter, 1.15) / (size) + .5;
			
			if(scaled < 0) scaled = 0;
			
				
				imageG.setColor(new Color(r, g, b, (int) (255.0 * scaled * alpha)));
				imageG.fillRect(x, y, step, step);

			}
		}
//		imageG.setColor(Color.red);
//		imageG.drawRect(0, 0, size, size);
		imageG.flush();
	}
	
	public boolean containsUnit(Unit u)
	{
		int distance = (int) Utility.distance(u.getCenterX(), u.getCenterY(), x + w/2, y + h/2);
		return distance < radius;
	}
	
	public void render(Graphics g)
	{
		g.drawImage(image, x, y);
		//g.setColor(Color.red);
		//g.drawOval(x+w/2-radius, y+h/2-radius, radius*2, radius*2);
		//System.out.println("hello");
	}
	
	public int getRadius()
	{
		return radius;
	}
	

}
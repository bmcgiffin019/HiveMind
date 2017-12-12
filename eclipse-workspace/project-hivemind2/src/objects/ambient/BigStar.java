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

import java.io.*;

public class BigStar extends Hazard {
	private int size;
	private SimplexNoise noise;
	private Image image0;
	private Image image1;
	private Image image2;
	private Image image3;
	private Image overlay;
	private Graphics imageG;
	private int eventShading = 0;
	
	public BigStar(int xPos, int yPos) throws SlickException {
		this(xPos, yPos, Values.STAR_BASE_SIZE);
	}

	public BigStar(int xPos, int yPos, int size) throws SlickException {
		super(xPos - size / 2, yPos - size / 2);
		this.size = size;
		w = size;
		h = size;
		// Set up the image and graphics object
		try {
			image0 = new Image(size, size);
			image1 = new Image(size, size);
			image2 = new Image(size, size);
			image3 = new Image(size, size);

		} catch (SlickException e) {
			Log.error("creating local image or graphics context failed: " + e.getMessage());
		}

//		for (int i = 0; i < 17; i++) 
//		{
	noise = new SimplexNoise(Utility.random(100000));
			constructLayer(image0, 3, 255);
			constructLayer(image1, 3.05, 165);
			constructLayer(image2, 3.1, 165);
			constructLayer(image3, 3.15, 165);

		//}

		overlay = image1;

	}

	public void constructLayer(Image i, double scale, int alpha) {


		int step = 5;
		
		try {
			imageG = i.getGraphics();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int y = 0; y < i.getHeight(); y += step) {
			for (int x = 0; x < i.getWidth(); x += step) {
				double value = noise.eval(x / scale, y / scale);
				float scaled = (float) ((value + 1) * .5);

				float distanceToCenter = Utility.distance(x, y, size / 2, size / 2);
				if(distanceToCenter <= size/2)
				{
					int yellow = (int) (255 * scaled);
					//System.out.println(yellow);
					imageG.setColor(new Color(255, yellow, 0, alpha));
					imageG.fillRect(x, y, step, step);
				}
			}
		}
		
		imageG.flush();
	}
	
	public void update()
	{
		super.update();
		if(timer % 20 == 0)
		{
			if(overlay == image1)
			{
				overlay = image2;
			}
			else if(overlay == image2)
			{
				overlay = image3;
			}
			else if(overlay == image3)
			{
				overlay = image1;
			}
		}
	}
	
	


	public void render(Graphics g) 
	{
		g.drawImage(image0, x, y);
		g.drawImage(overlay, x, y); 
	
		if(hadEvent && timeSinceLastEvent < 450)
		{
			eventShading = (int) (150-timeSinceLastEvent/3);
			g.setColor(new Color(255, 0, 0, eventShading)); 
		}
		else
		{
			eventShading = (int) (150 * progress);
			g.setColor(new Color(255, 0, 0, eventShading)); 
		}

		g.fillOval(x,  y,  w+5,  h+5);
		
	}
	
	public int getEventShading()
	{
		return eventShading;
	}

}
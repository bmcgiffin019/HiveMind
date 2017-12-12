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

public class Pulsar extends Hazard {
	private int size;
	private SimplexNoise noise;
	private Image image0;
	private Image image1;
	private Image image2;
	private int eventShading;
	
	private Graphics imageG;

	public Pulsar(int xPos, int yPos) throws SlickException {
		this(xPos, yPos, Values.PULSAR_BASE_SIZE);
	}

	public Pulsar(int xPos, int yPos, int size) throws SlickException {
		super(xPos - size / 2, yPos - size / 2);
		this.size = size;
		w = size;
		h = size;
		// Set up the image and graphics object
		try {
			image0 = new Image(size, size);
			image1 = new Image(size, size);
			image2 = new Image(size, size);

		} catch (SlickException e) {
			Log.error("creating local image or graphics context failed: " + e.getMessage());
		}

		// for (int i = 0; i < 17; i++)
		// {
		noise = new SimplexNoise(Utility.random(100000));
		constructLayer(image0, 40, 255);
		constructLayer(image1, 30, 165);
		constructLayer(image2, 20, 165);

		// }

	}

	public void constructLayer(Image i, double scale, int alpha) {

		int step = 1;

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
				if (distanceToCenter <= size / 2) {
					int white = (int) (200 * scaled);
					// System.out.println(yellow);
					imageG.setColor(new Color(white, white, 255, alpha));
					imageG.fillRect(x, y, step, step);
				}
			}
		}

		imageG.flush();
	}

	public void update() {
		super.update();
			
		image1.setRotation(timer/8 % 360);
		image2.setRotation(timer/16 % 360);

	}

	public void render(Graphics g) {
		g.drawImage(image0, x, y);
		g.drawImage(image1, x, y);
		g.drawImage(image2, x, y);

		if (hadEvent && timeSinceLastEvent < 450) {
			eventShading = (int) (150-timeSinceLastEvent/3);
			g.setColor(new Color(225, 225, 255, eventShading));
		} else {
			eventShading = (int) (150 * progress);
			g.setColor(new Color(225, 225, 255, eventShading));
		}

		g.fillOval(x, y, w + 5, h + 5);

	}
	
	
	public int getEventShading()
	{
		return eventShading;
	}

}
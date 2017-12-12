package objects.ambient;

import org.newdawn.slick.SlickException;

import ui.Images;

public class Moon extends Asteroid 
{
	public Moon(float x, float y, int size) throws SlickException {
		
		super(x, y, 0, 0, size);
		sheet = Images.asteroid;
		image = sheet.getSprite(model, 0);
		turnSpeed = 0;
				
		x = -image.getWidth() * scale ;
		y = -image.getHeight() * scale;
	}
	
}

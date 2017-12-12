package ui;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import core.Values;

public class Images 
{
	public static SpriteSheet base;
	public static SpriteSheet raider;
	public static SpriteSheet miner;
	public static SpriteSheet assault;
	public static SpriteSheet specialist;
	public static SpriteSheet support;
	public static SpriteSheet mines;
	public static SpriteSheet missiles;

	public static SpriteSheet stars;
	public static SpriteSheet asteroid, asteroid2;
	public static SpriteSheet turret;
	
	public static SpriteSheet upgrades;




	public static Image boom;
	public static Image combust;

	
	public static Image endBackground;
	public static SpriteSheet teamLogos;

	
	public static void loadImages() throws SlickException 
	{
		base = new SpriteSheet("res/units/classic/base.png", 450, 300, 0);
	//	raider = new SpriteSheet("res/units/classic/raider.png", 32, 32, 0);
		miner = new SpriteSheet("res/units/classic/miner.png", 40, 40, 0);
		turret = new SpriteSheet("res/units/classic/turret.png", 70, 70, 0);
		assault = new SpriteSheet("res/units/classic/assault.png", 64, 64, 0);
		specialist = new SpriteSheet("res/units/classic/specialist.png", 96, 96, 0);
		support = new SpriteSheet("res/units/classic/support.png", 46, 46, 0);
		mines = new SpriteSheet("res/units/classic/mine.png", 46, 46, 0);
		missiles = new SpriteSheet("res/units/classic/missile.png", 22, 8, 0);
		
		asteroid = new SpriteSheet("res/asteroid5.png", 128, 128, 0);
		asteroid2 = new SpriteSheet("res/asteroid6.png", 128, 128, 0);
		upgrades = new SpriteSheet("res/upgrades.png", 30, 30, 0);


		combust = new Image("res/combust.png");
		boom = new Image("res/boom.png");
	}
	
	
	
	public static void loadEndImages() throws SlickException 
	{
		endBackground = new Image("res/metal.png");
		endBackground = endBackground.getScaledCopy(Values.RESOLUTION_X, Values.RESOLUTION_Y);
		teamLogos = new SpriteSheet("res/teamlogos2.png", 660, 825, 0);
	}


}

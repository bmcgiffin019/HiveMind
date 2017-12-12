package ui;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;

public class Fonts {
	public static TrueTypeFont arial20;
	public static TrueTypeFont arial60;
	public static TrueTypeFont calibri32;
	public static TrueTypeFont calibri12;
	public static TrueTypeFont calibri14;

	public static TrueTypeFont calibri16;
	public static TrueTypeFont calibri20;

	public static TrueTypeFont consolas64;
	public static TrueTypeFont consolas48;
	public static TrueTypeFont consolas32;
	public static TrueTypeFont consolas24;

	public static TrueTypeFont consolas20;
	public static TrueTypeFont consolas14;

	public static TrueTypeFont impact28;
	public static TrueTypeFont arialblack36;
	public static TrueTypeFont verdana36;


	public static void loadFonts() 
	{
		arial20 = new TrueTypeFont(new Font("Arial", Font.BOLD, 20), false);
		arial60 = new TrueTypeFont(new Font("Arial", Font.BOLD, 60), false);
		
		calibri32 = new TrueTypeFont(new Font("Calibri", Font.BOLD, 32), false);
		calibri12 = new TrueTypeFont(new Font("Calibri", Font.BOLD, 12), false);
		calibri14 = new TrueTypeFont(new Font("Calibri", Font.BOLD, 14), false);

		calibri16 = new TrueTypeFont(new Font("Calibri", Font.BOLD, 16), false);
		calibri20 = new TrueTypeFont(new Font("Calibri", Font.BOLD, 20), false);

		consolas64 = new TrueTypeFont(new Font("Consolas", Font.BOLD, 64), false);
		consolas48 = new TrueTypeFont(new Font("Consolas", Font.BOLD, 48), false);
		consolas32 = new TrueTypeFont(new Font("Consolas", Font.BOLD, 32), false);
		consolas24 = new TrueTypeFont(new Font("Consolas", Font.BOLD, 24), false);
		consolas20 = new TrueTypeFont(new Font("Consolas", Font.PLAIN, 20), false);
		consolas14 = new TrueTypeFont(new Font("Consolas", Font.PLAIN, 14), false);

		verdana36 = new TrueTypeFont(new Font("Verdana", Font.BOLD, 36), false);
		
		arialblack36 = new TrueTypeFont(new Font("Arial Black", Font.PLAIN, 36), false);
		impact28 = new TrueTypeFont(new Font("Impact", Font.PLAIN, 28), false);

	}
}

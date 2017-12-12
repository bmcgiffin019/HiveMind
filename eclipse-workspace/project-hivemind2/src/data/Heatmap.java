package data;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Point;

import core.Values;
import ui.Fonts;


class Heatmap {
	final static int MAP_WIDTH = 184;
	final static int MAP_HEIGHT = (int) (MAP_WIDTH * .625);
	final static float MAP_SCALE_X = ((float) MAP_WIDTH) / Values.PLAYFIELD_WIDTH;
	final static float MAP_SCALE_Y = ((float) MAP_HEIGHT) / Values.PLAYFIELD_HEIGHT;
	final static int MAP_RENDER_SIZE = 4;

	private static final int T1 = 2;
	private static final int T2 = 4;
	private static final int T3 = 6;
	private static final int T4 = 8;

	
	private static int[][] redMap = new int[MAP_WIDTH][MAP_HEIGHT];
	private static int[][] redDisplay = new int[MAP_WIDTH][MAP_HEIGHT];
	private static int[][] blueMap = new int[MAP_WIDTH][MAP_HEIGHT];
	private static int[][] blueDisplay = new int[MAP_WIDTH][MAP_HEIGHT];
	

	static void addDeath(DeathEvent e) 
	{
		Point p = scale(translate(e.getLocation()));

		// Check edges for off screen deaths
		if (p.getX() < 0)
			p.setX(0);
		if (p.getX() >= MAP_WIDTH)
			p.setX(MAP_WIDTH - 1);
		if (p.getY() < 0)
			p.setY(0);
		if (p.getY() >= MAP_HEIGHT)
			p.setY(MAP_HEIGHT - 1);

		if(e.getPlayerID() == Values.RED_ID)
		{
			redMap[(int) p.getX()][(int) p.getY()] += e.getValue();
		}
		
		if(e.getPlayerID() == Values.BLUE_ID)
		{
			blueMap[(int) p.getX()][(int) p.getY()] += e.getValue();
		}
	}
	
	static void clear()
	{
		for (int i = 0; i < MAP_WIDTH; i++) {
			for (int j = 0; j < MAP_HEIGHT; j++) {
				redMap[i][j] = 0;
				redDisplay[i][j] = 0;
				blueMap[i][j] = 0;
				blueDisplay[i][j] = 0;
			}
		}
	}

	private static Point translate(Point p) {
		return new Point(p.getX() + Values.PLAYFIELD_WIDTH / 2, p.getY() + Values.PLAYFIELD_HEIGHT / 2);
	}

	private static Point scale(Point p) {
		return new Point(p.getX() * MAP_SCALE_X, p.getY() * MAP_SCALE_Y);
	}

	public static void render(int x, int y, Graphics g) {
		// Box Border
		g.setColor(Color.black);
		g.drawRect(x, y, MAP_WIDTH * MAP_RENDER_SIZE, MAP_HEIGHT * MAP_RENDER_SIZE);

		// Box
		g.setColor(new Color(10, 10, 10, 200));
		g.fillRect(x, y, MAP_WIDTH * MAP_RENDER_SIZE, MAP_HEIGHT * MAP_RENDER_SIZE);
		
		setRedDisplayMap();
		setBlueDisplayMap();

		// Deaths!!!!
		float highest = 0;
		
		// Search red for highest
		for (int i = 0; i < MAP_WIDTH; i++) {
			for (int j = 0; j < MAP_HEIGHT; j++) {
				if (highest < redDisplay[i][j]) {
					highest = redDisplay[i][j];
				}
			}
		}
		
		// Search blue for highest
		for (int i = 0; i < MAP_WIDTH; i++) {
			for (int j = 0; j < MAP_HEIGHT; j++) {
				if (highest < blueDisplay[i][j]) {
					highest = blueDisplay[i][j];
				}
			}
		}

		// Gridlines
		for (int i = 0; i < MAP_WIDTH; i++) 
		{
			g.setColor(new Color(255, 255, 255, 10));
			g.fillRect(x + i * MAP_RENDER_SIZE, (int) (y + MAP_HEIGHT *.25 * MAP_RENDER_SIZE + MAP_RENDER_SIZE/2), MAP_RENDER_SIZE, MAP_RENDER_SIZE);
			g.fillRect(x + i * MAP_RENDER_SIZE, (int) (y + MAP_HEIGHT *.50 * MAP_RENDER_SIZE + MAP_RENDER_SIZE/2), MAP_RENDER_SIZE, MAP_RENDER_SIZE);
			g.fillRect(x + i * MAP_RENDER_SIZE, (int) (y + MAP_HEIGHT *.75 * MAP_RENDER_SIZE + MAP_RENDER_SIZE/2), MAP_RENDER_SIZE, MAP_RENDER_SIZE);

		}
		
		for (int j = 0; j < MAP_HEIGHT; j++) 
		{
			g.setColor(new Color(255, 255, 255, 10));
			g.fillRect((int) (x + MAP_WIDTH *.25 * MAP_RENDER_SIZE + MAP_RENDER_SIZE/2), y + j * MAP_RENDER_SIZE, MAP_RENDER_SIZE, MAP_RENDER_SIZE);
			g.fillRect((int) (x + MAP_WIDTH *.50 * MAP_RENDER_SIZE + MAP_RENDER_SIZE/2), y + j * MAP_RENDER_SIZE, MAP_RENDER_SIZE, MAP_RENDER_SIZE);
			g.fillRect((int) (x + MAP_WIDTH *.75 * MAP_RENDER_SIZE + MAP_RENDER_SIZE/2), y + j * MAP_RENDER_SIZE, MAP_RENDER_SIZE, MAP_RENDER_SIZE);

		}
		
		
		// Treat all damage that's 90% as high as highest as the top tier
		highest = highest * .8f;
		
		for (int i = 0; i < MAP_WIDTH; i++) 
		{
			for (int j = 0; j < MAP_HEIGHT; j++) 
			{
				int rValue = (int) (255 * ((float) redDisplay[i][j]) / ((float) highest));
				int bValue = (int) (255 * ((float) blueDisplay[i][j]) / ((float) highest));
				
				// Swap the colors.  Red color for blue deaths, and vice versa
				g.setColor(new Color(bValue, rValue*2/3, rValue, rValue+bValue+100));
				g.fillRect(x + i * MAP_RENDER_SIZE, y + j * MAP_RENDER_SIZE, MAP_RENDER_SIZE, MAP_RENDER_SIZE);
			}
		}
		
		Point blu = scale(translate(new Point(-Values.BASE_SHIP_X_POSITION, -Values.BASE_SHIP_Y_POSITION)));
		Point red = scale(translate(new Point(Values.BASE_SHIP_X_POSITION, Values.BASE_SHIP_Y_POSITION)));
		Point blu2 = scale(translate(Data.getBluePosition()));
		Point red2 = scale(translate(Data.getRedPosition()));
		
		// Base ships
		g.setColor(new Color(205, 205, 135));
		g.fillRect(x + blu.getX() * MAP_RENDER_SIZE, y + blu.getY() * MAP_RENDER_SIZE, MAP_RENDER_SIZE, MAP_RENDER_SIZE);
		g.fillRect(x + red.getX() * MAP_RENDER_SIZE, y + (red.getY()+1) * MAP_RENDER_SIZE, MAP_RENDER_SIZE, MAP_RENDER_SIZE);
		g.fillRect(x + blu2.getX() * MAP_RENDER_SIZE, y + blu2.getY() * MAP_RENDER_SIZE, MAP_RENDER_SIZE, MAP_RENDER_SIZE);
		g.fillRect(x + red2.getX() * MAP_RENDER_SIZE, y + (red2.getY()+1) * MAP_RENDER_SIZE, MAP_RENDER_SIZE, MAP_RENDER_SIZE);

		// Labels
		Font f1 = Fonts.consolas24;
		g.setFont(f1);	
		
		String m0 = "BATTLE RECORD";
		g.setColor(new Color(40, 40, 40));
		g.drawString(m0, x+9, y+5);
		g.setColor(new Color(205, 205, 135));
		g.drawString(m0, x+7, y+3);
		
		
		Font f2 = Fonts.calibri16;
		g.setFont(f2);	
		
		String m1 = "BLUE VICTORIES";
		g.setColor(new Color(20, 20, 20));
		g.drawString(m1, x+7, MAP_HEIGHT * MAP_RENDER_SIZE - f2.getHeight(m1) + y+2);
		g.setColor(new Color(Values.COLORS[Values.BLUE_ID]));
		g.drawString(m1, x+5, MAP_HEIGHT * MAP_RENDER_SIZE - f2.getHeight(m1) + y);
		
		String m2 = "RED VICTORIES";
		g.setColor(new Color(20, 20, 20));
		g.drawString(m2, MAP_WIDTH * MAP_RENDER_SIZE - f2.getWidth(m2) + x - 3, MAP_HEIGHT * MAP_RENDER_SIZE - f2.getHeight(m2) + y+2);
		g.setColor(new Color(Values.COLORS[Values.RED_ID]));
		g.drawString(m2, MAP_WIDTH * MAP_RENDER_SIZE - f2.getWidth(m2) + x - 5, MAP_HEIGHT * MAP_RENDER_SIZE - f2.getHeight(m2) + y);
		
		
		
		
	}


	private static void addToRedDisplay(int x, int y, int value) {
		if (x > 0 && x < MAP_WIDTH && y > 0 && y < MAP_HEIGHT) {
			redDisplay[x][y] += value;
		}
	}

	
	private static int getRedLoc(int x, int y) {
		if (x > 0 && x < MAP_WIDTH && y > 0 && y < MAP_HEIGHT) 
		{
			return redMap[x][y];
		}
		return 0;
	}


	private static void setRedDisplayMap() {
		for (int i = 0; i < MAP_WIDTH; i++) {
			for (int j = 0; j < MAP_HEIGHT; j++) {
				if (redMap[i][j] > 0) {
					// Core
					addToRedDisplay(i, j, getRedLoc(i, j));

					// Layer 1
					addToRedDisplay(i - 1, j - 1, getRedLoc(i, j) / T1);
					addToRedDisplay(i - 1, j + 0, getRedLoc(i, j) / T1);
					addToRedDisplay(i - 1, j + 1, getRedLoc(i, j) / T1);

					addToRedDisplay(i + 0, j - 1, getRedLoc(i, j) / T1);
					addToRedDisplay(i + 0, j + 1, getRedLoc(i, j) / T1);

					addToRedDisplay(i + 1, j - 1, getRedLoc(i, j) / T1);
					addToRedDisplay(i + 1, j + 0, getRedLoc(i, j) / T1);
					addToRedDisplay(i + 1, j + 1, getRedLoc(i, j) / T1);

					// Layer 2
					
					addToRedDisplay(i - 2, j - 2, getRedLoc(i, j) / T3);
					addToRedDisplay(i - 2, j - 1, getRedLoc(i, j) / T2);
					addToRedDisplay(i - 2, j + 0, getRedLoc(i, j) / T2);
					addToRedDisplay(i - 2, j + 1, getRedLoc(i, j) / T2);
					addToRedDisplay(i - 2, j + 2, getRedLoc(i, j) / T3);

					addToRedDisplay(i + 2, j - 2, getRedLoc(i, j) / T3);
					addToRedDisplay(i + 2, j - 1, getRedLoc(i, j) / T2);
					addToRedDisplay(i + 2, j + 0, getRedLoc(i, j) / T2);
					addToRedDisplay(i + 2, j + 1, getRedLoc(i, j) / T2);
					addToRedDisplay(i + 2, j + 2, getRedLoc(i, j) / T3);

					addToRedDisplay(i - 1, j - 2, getRedLoc(i, j) / T2);
					addToRedDisplay(i + 0, j - 2, getRedLoc(i, j) / T2);
					addToRedDisplay(i + 1, j - 2, getRedLoc(i, j) / T2);

					addToRedDisplay(i - 1, j + 2, getRedLoc(i, j) / T2);
					addToRedDisplay(i + 0, j + 2, getRedLoc(i, j) / T2);
					addToRedDisplay(i + 1, j + 2, getRedLoc(i, j) / T2);

					// Layer 3

					addToRedDisplay(i - 3, j - 3, getRedLoc(i, j) / T4);
					addToRedDisplay(i - 3, j - 2, getRedLoc(i, j) / T3);
					addToRedDisplay(i - 3, j - 1, getRedLoc(i, j) / T3);
					addToRedDisplay(i - 3, j + 0, getRedLoc(i, j) / T3);
					addToRedDisplay(i - 3, j + 1, getRedLoc(i, j) / T3);
					addToRedDisplay(i - 3, j + 2, getRedLoc(i, j) / T3);
					addToRedDisplay(i - 3, j + 3, getRedLoc(i, j) / T4);

					addToRedDisplay(i + 3, j - 3, getRedLoc(i, j) / T4);
					addToRedDisplay(i + 3, j - 2, getRedLoc(i, j) / T3);
					addToRedDisplay(i + 3, j - 1, getRedLoc(i, j) / T3);
					addToRedDisplay(i + 3, j + 0, getRedLoc(i, j) / T3);
					addToRedDisplay(i + 3, j + 1, getRedLoc(i, j) / T3);
					addToRedDisplay(i + 3, j + 2, getRedLoc(i, j) / T3);
					addToRedDisplay(i + 3, j + 3, getRedLoc(i, j) / T4);

					addToRedDisplay(i - 2, j - 3, getRedLoc(i, j) / T3);
					addToRedDisplay(i - 1, j - 3, getRedLoc(i, j) / T3);
					addToRedDisplay(i + 0, j - 3, getRedLoc(i, j) / T3);
					addToRedDisplay(i + 1, j - 3, getRedLoc(i, j) / T3);
					addToRedDisplay(i + 2, j - 3, getRedLoc(i, j) / T3);

					addToRedDisplay(i - 2, j + 3, getRedLoc(i, j) / T3);
					addToRedDisplay(i - 1, j + 3, getRedLoc(i, j) / T3);
					addToRedDisplay(i + 0, j + 3, getRedLoc(i, j) / T3);
					addToRedDisplay(i + 1, j + 3, getRedLoc(i, j) / T3);
					addToRedDisplay(i + 2, j + 3, getRedLoc(i, j) / T3);

				}

			}
		}
	}
	private static void addToBlueDisplay(int x, int y, int value) {
		if (x > 0 && x < MAP_WIDTH && y > 0 && y < MAP_HEIGHT) {
			blueDisplay[x][y] += value;
		}
	}

	
	private static int getBlueLoc(int x, int y) {
		if (x > 0 && x < MAP_WIDTH && y > 0 && y < MAP_HEIGHT) 
		{
			return blueMap[x][y];
		}
		return 0;
	}

	private static void setBlueDisplayMap() {
		for (int i = 0; i < MAP_WIDTH; i++) {
			for (int j = 0; j < MAP_HEIGHT; j++) {
				if (blueMap[i][j] > 0) {
					// Core
					addToBlueDisplay(i, j, getBlueLoc(i, j));

					// Layer 1
					addToBlueDisplay(i - 1, j - 1, getBlueLoc(i, j) / T1);
					addToBlueDisplay(i - 1, j + 0, getBlueLoc(i, j) / T1);
					addToBlueDisplay(i - 1, j + 1, getBlueLoc(i, j) / T1);

					addToBlueDisplay(i + 0, j - 1, getBlueLoc(i, j) / T1);
					addToBlueDisplay(i + 0, j + 1, getBlueLoc(i, j) / T1);

					addToBlueDisplay(i + 1, j - 1, getBlueLoc(i, j) / T1);
					addToBlueDisplay(i + 1, j + 0, getBlueLoc(i, j) / T1);
					addToBlueDisplay(i + 1, j + 1, getBlueLoc(i, j) / T1);

					// Layer 2
					
					addToBlueDisplay(i - 2, j - 2, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i - 2, j - 1, getBlueLoc(i, j) / T2);
					addToBlueDisplay(i - 2, j + 0, getBlueLoc(i, j) / T2);
					addToBlueDisplay(i - 2, j + 1, getBlueLoc(i, j) / T2);
					addToBlueDisplay(i - 2, j + 2, getBlueLoc(i, j) / T3);

					addToBlueDisplay(i + 2, j - 2, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i + 2, j - 1, getBlueLoc(i, j) / T2);
					addToBlueDisplay(i + 2, j + 0, getBlueLoc(i, j) / T2);
					addToBlueDisplay(i + 2, j + 1, getBlueLoc(i, j) / T2);
					addToBlueDisplay(i + 2, j + 2, getBlueLoc(i, j) / T3);

					addToBlueDisplay(i - 1, j - 2, getBlueLoc(i, j) / T2);
					addToBlueDisplay(i + 0, j - 2, getBlueLoc(i, j) / T2);
					addToBlueDisplay(i + 1, j - 2, getBlueLoc(i, j) / T2);

					addToBlueDisplay(i - 1, j + 2, getBlueLoc(i, j) / T2);
					addToBlueDisplay(i + 0, j + 2, getBlueLoc(i, j) / T2);
					addToBlueDisplay(i + 1, j + 2, getBlueLoc(i, j) / T2);

					// Layer 3

					addToBlueDisplay(i - 3, j - 3, getBlueLoc(i, j) / T4);
					addToBlueDisplay(i - 3, j - 2, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i - 3, j - 1, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i - 3, j + 0, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i - 3, j + 1, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i - 3, j + 2, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i - 3, j + 3, getBlueLoc(i, j) / T4);

					addToBlueDisplay(i + 3, j - 3, getBlueLoc(i, j) / T4);
					addToBlueDisplay(i + 3, j - 2, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i + 3, j - 1, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i + 3, j + 0, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i + 3, j + 1, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i + 3, j + 2, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i + 3, j + 3, getBlueLoc(i, j) / T4);

					addToBlueDisplay(i - 2, j - 3, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i - 1, j - 3, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i + 0, j - 3, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i + 1, j - 3, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i + 2, j - 3, getBlueLoc(i, j) / T3);

					addToBlueDisplay(i - 2, j + 3, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i - 1, j + 3, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i + 0, j + 3, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i + 1, j + 3, getBlueLoc(i, j) / T3);
					addToBlueDisplay(i + 2, j + 3, getBlueLoc(i, j) / T3);

				}
			}
		}
	}

}

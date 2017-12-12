package ui;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.TrueTypeFont;

import core.Game;
import core.Values;
import objects.ambient.Asteroid;
import objects.ambient.BigStar;
import objects.ambient.Hazard;
import objects.ambient.HighYieldAsteroid;
import objects.ambient.Nebula;
import objects.ambient.Pulsar;
import objects.base.Player;
import objects.base.Upgrade;
import objects.units.Assault;
import objects.units.BaseShip;
import objects.units.Miner;
import objects.units.Raider;
import objects.units.Specialist;
import objects.units.Support;
import objects.units.Unit;
import scenario.Scenario;

public class Display implements Values {
	private int timer = 0;
	private int musicTimer = 0;
	private boolean musicReset = false;
	private ArrayList<Unit> units;

	private Player one;
	private Player two;
	
	private int numBlueRaiders;
	private int numBlueMiners;
	private int numBlueAssaults;
	private int numBlueSpecialist;
	private int numBlueSupports;

	private int numRedRaiders;
	private int numRedMiners;
	private int numRedAssaults;
	private int numRedSpecialist;
	private int numRedSupports;

	private Image imgBlueRaider;
	private Image imgBlueMiner;
	private Image imgBlueAssault;
	private Image imgBlueSpecialist;
	private Image imgBlueSupport;

	private Image imgRedRaider;
	private Image imgRedMiner;
	private Image imgRedAssault;
	private Image imgRedSpecialist;
	private Image imgRedSupport;

	public Display(BaseShip alpha, BaseShip beta) {

		one = alpha.getOwner();
		two = beta.getOwner();

		imgBlueRaider = alpha.getOwner().getImageRaider().getSprite(0, 0);
		imgBlueMiner = alpha.getOwner().getImageMiner().getSprite(0, 0);
		imgBlueAssault = alpha.getOwner().getImageAssault().getSprite(0, 0);
		imgBlueSpecialist = alpha.getOwner().getImageSpecialist().getSprite(0, 0);
		imgBlueSupport = alpha.getOwner().getImageSupport().getSprite(0, 0);

		imgRedRaider = beta.getOwner().getImageRaider().getSprite(0, 1);
		imgRedMiner = beta.getOwner().getImageMiner().getSprite(0, 1);
		imgRedAssault = beta.getOwner().getImageAssault().getSprite(0, 1);
		imgRedSpecialist = beta.getOwner().getImageSpecialist().getSprite(0, 1);
		imgRedSupport = beta.getOwner().getImageSupport().getSprite(0, 1);


	}

	public void update(ArrayList<Unit> units) {

		this.units = units;

		timer++;

		numBlueRaiders = 0;
		numBlueMiners = 0;
		numBlueAssaults = 0;
		numBlueSpecialist = 0;
		numBlueSupports = 0;

		numRedRaiders = 0;
		numRedMiners = 0;
		numRedAssaults = 0;
		numRedSpecialist = 0;
		numRedSupports = 0;

		for (Unit u : units) {
			if (u.getTeam() == Values.BLUE_ID && u instanceof Raider)
				numBlueRaiders++;
			if (u.getTeam() == Values.BLUE_ID && u instanceof Miner)
				numBlueMiners++;
			if (u.getTeam() == Values.BLUE_ID && u instanceof Assault)
				numBlueAssaults++;
			if (u.getTeam() == Values.BLUE_ID && u instanceof Specialist)
				numBlueSpecialist++;
			if (u.getTeam() == Values.BLUE_ID && u instanceof Support)
				numBlueSupports++;

			if (u.getTeam() == Values.RED_ID && u instanceof Raider)
				numRedRaiders++;
			if (u.getTeam() == Values.RED_ID && u instanceof Miner)
				numRedMiners++;
			if (u.getTeam() == Values.RED_ID && u instanceof Assault)
				numRedAssaults++;
			if (u.getTeam() == Values.RED_ID && u instanceof Specialist)
				numRedSpecialist++;
			if (u.getTeam() == Values.RED_ID && u instanceof Support)
				numRedSupports++;

		}
	}

	public void render(Graphics g) 
	{
		displayUpgrades(g);
		displayBottomBar(g);
		displayMiniMap(g);
		displayPlayerString(g, Values.BLUE_ID);
		displayPlayerString(g, Values.RED_ID);
		displayTimer(g);
		displayFPS(g);
		displayScenario(g);
		displayEndGameMessage(g);
		displayMusicReset(g);
		
		Alert.render(g);

	}
		
	void displayScenario(Graphics g)
	{ 
		int opacity;
		int startFadeIn = 20;

		int fadeInUntil = 120;
		int startFadeOut = 600;
		int fadeTime = 100;
		int maxOpacity = 180;
		
		int yDown = 360;
		int h = 80;
		
		if(Game.getTime() < fadeInUntil)
		{
			opacity = (int) (((float) (Game.getTime() - startFadeIn) / (float) fadeTime) * maxOpacity);
		}
		else if(Game.getTime() > startFadeOut)
		{
			opacity = (int) (maxOpacity * ((float)(fadeTime - (Game.getTime() - startFadeOut))) / (float) fadeTime);
		}
		else
		{
			opacity = maxOpacity;
		}
		
		if(timer < startFadeOut + fadeTime)
		{
	
			g.setColor(new Color(10, 10, 10, opacity));
			g.fillRect(0, yDown + RESOLUTION_Y/2-h/2, RESOLUTION_X, h);
			
			g.setColor(new Color(215, 215, 150, opacity));
			g.fillRect(0, yDown + RESOLUTION_Y/2-h/2, RESOLUTION_X, 5);
			g.fillRect(0, yDown + RESOLUTION_Y/2-h/2+h-5, RESOLUTION_X, 5);

			TrueTypeFont fontName = Fonts.verdana36;
			g.setFont(fontName);
			
			
			int offX = fontName.getWidth(Scenario.getTitle()) / 2;
			int offY = fontName.getHeight(Scenario.getTitle()) / 2;

			
			opacity = (int) ((float) opacity * (255.0/ (float) maxOpacity));
			g.setColor(new Color(255, 255, 255, opacity));
			g.drawString(Scenario.getTitle(), RESOLUTION_X/2-offX, yDown + RESOLUTION_Y/2-offY);
			
			displayMusic(g, opacity);
		}
		

	}
	
	void displayEndGameMessage(Graphics g)
	{
		TrueTypeFont fontName = Fonts.consolas32;
		g.setFont(fontName);
		int w = 470;
		int h = 160;
		
		if(one.isDefeated() || two.isDefeated())
		{
			g.setColor(new Color(10, 10, 10, 150));
			g.fillRect(RESOLUTION_X/2-w/2, RESOLUTION_Y/2-h/2+14, w, h);

			g.setColor(new Color(255, 255, 255));
			g.drawString("Press SPACE to Continue", RESOLUTION_X/2-200, RESOLUTION_Y/2+30);
		}

		if(one.isDefeated()) 
		{
			g.setColor(Values.RED);
			g.drawRect(RESOLUTION_X/2-w/2, RESOLUTION_Y/2-h/2+14, w, h);
			
			g.setColor(new Color(255, 255, 255));
			g.drawString("Red Wins!", RESOLUTION_X/2-100, RESOLUTION_Y/2-30);
		}
		else if(two.isDefeated())
		{
			g.setColor(Values.BLUE);
			g.drawRect(RESOLUTION_X/2-w/2, RESOLUTION_Y/2-h/2+14, w, h);
			
			g.setColor(new Color(255, 255, 255));
			g.drawString("Blue Wins!", RESOLUTION_X/2-100, RESOLUTION_Y/2-30);
		}
				

	}

	
	public void resetMusicTimer()
	{
		musicTimer=0;
		musicReset = true;
	}
	void displayMusic(Graphics g, int opacity)
	{

		TrueTypeFont fontName = Fonts.calibri16;
			int x = 50;
			int y = RESOLUTION_Y - 200;
			
			g.setFont(fontName);
			
			if(Audio.getGameMusic().isSpecial())
			{
				g.setColor(new Color(255, 255, 120, opacity));
				g.drawString("*Secret Track*", x, y);
			}
			else
			{
				g.setColor(new Color(255, 255, 180, opacity));
				g.drawString("Music:", x, y);
			}

			y += 20;
			g.setFont(fontName);
			g.setColor(new Color(255, 255, 255, opacity));
			g.drawString(Audio.getGameMusic().getArtist() + " - " + Audio.getGameMusic().getTitle(), x, y);
			
	}
	
	void displayMusicReset(Graphics g)
	{
		if(musicReset && musicTimer < 120*8)
		{
			musicTimer++;

			displayMusic(g, 255);
		}
	}

	void displayPlayerString(Graphics g, int team)
	{
		TrueTypeFont fontName = Fonts.consolas24;
		TrueTypeFont fontMessage = Fonts.consolas20;
		int x = 150;
		int y = 20;
		int h = fontName.getHeight() / 2 - fontMessage.getHeight() / 2;
		int ySpace = fontName.getHeight();
		int len;

		if (team == Values.BLUE_ID) {
			// Name
			g.setFont(fontName);
			g.setColor(BLUE);
			len = fontName.getWidth(one.getName());
			g.drawString(one.getName(), x - len / 2, y);

			// Message
			g.setFont(fontMessage);
			len = fontName.getWidth(one.getMessage());
			g.setColor(new Color(200, 200, 200));
			g.drawString(one.getMessage(), x - len / 2, y + ySpace + h);

			// Message2
			g.setFont(fontMessage);
			len = fontName.getWidth(one.getMessageTwo());
			g.drawString(one.getMessageTwo(), x - len / 2, y + ySpace * 2 + h);
			
			// Message3
			g.setFont(fontMessage);
			len = fontName.getWidth(one.getMessageThree());
			g.drawString(one.getMessageThree(), x - len / 2, y + ySpace * 3 + h);
		}

		x = Values.RESOLUTION_X - 450;

		if (team == Values.RED_ID) {
			// Name
			g.setFont(fontName);
			g.setColor(RED);
			len = fontName.getWidth(two.getName());
			g.drawString(two.getName(), x - len / 2, y);

			// Message
			g.setFont(fontMessage);
			len = fontName.getWidth(two.getMessage());
			g.setColor(new Color(200, 200, 200));
			g.drawString(two.getMessage(), x - len / 2, y + ySpace + h);

			// Message2
			g.setFont(fontMessage);
			len = fontName.getWidth(two.getMessageTwo());
			g.drawString(two.getMessageTwo(), x - len / 2, y + ySpace * 2 + h);
			
			// Message3
			g.setFont(fontMessage);
			len = fontName.getWidth(two.getMessageThree());
			g.drawString(two.getMessageThree(), x - len / 2, y + ySpace * 3 + h);
		}

		// Game Speed

		if (Game.isGamePaused()) {
			String s = "||";
			g.setFont(fontMessage);
			len = fontName.getWidth(s);
			g.drawString(s, Values.RESOLUTION_X / 2 - len / 2, Values.RESOLUTION_Y - 70);
		} else if (Game.getGameSpeed() > 1) {
			String s = "";
			for (int i = 0; i < Game.getGameSpeed(); i++) {
				s += ">";
			}
			g.setFont(fontMessage);
			len = fontName.getWidth(s);
			g.drawString(s, Values.RESOLUTION_X / 2 - len / 2, Values.RESOLUTION_Y - 70);
		}

	}


	

	void displayTimer(Graphics g) {

		g.setFont(Fonts.consolas24);
		int time = (int) (Game.getTime() / Values.FRAMES_PER_SECOND);
		int len = Fonts.consolas24.getWidth(time + "");

		g.setColor(new Color(120, 100, 150));
		g.drawString("" + time, Values.RESOLUTION_X / 2 - len / 2, Values.RESOLUTION_Y - 34);

		
	}
	
	void displayFPS(Graphics g) {

		
		if (Game.infoMode) {
			g.setFont(Fonts.calibri12);
			int len = Fonts.calibri12.getWidth(Game.getFPS() + " FPS");
			g.setColor(new Color(255, 255, 255));
			g.drawString(Game.getFPS() + " FPS", Values.RESOLUTION_X / 2 - len / 2, Values.RESOLUTION_Y - 90);
		}
		
	}


	void displayMiniMap(Graphics g) {
		if (units == null)
			return;

		int h = PLAYFIELD_HEIGHT/100;
		int w = PLAYFIELD_WIDTH/100;
		int x = Values.RESOLUTION_X - w - 10;
		int y = 20;

		// Background
		g.setColor(new Color(10, 10, 10, 150));
		g.fillRect(x, y, w, h);

		// Borders
		g.setColor(new Color(50, 50, 50, 50));
		g.fillRect(x, y, w, 5);
		g.fillRect(x, y + h - 5, w, 5);
		g.fillRect(x, y, 5, h);
		g.fillRect(x + w - 5, y, 5, h);
		g.fillRect(x, y, w, h);

		// Camera
		
		g.resetLineWidth();
		g.setColor(new Color(200, 200, 200, 10));
		float mmX = Camera.getX() / ((float) PLAYFIELD_WIDTH) * w + x + w / 2;
		float mmY = Camera.getY() / ((float) PLAYFIELD_HEIGHT) * h + y + h / 2;
		float mmW = ((Camera.getViewWidth() / ((float) PLAYFIELD_WIDTH)) * w);
		float mmH = ((Camera.getViewHeight() / ((float) PLAYFIELD_HEIGHT)) * h);
		g.fillRect(mmX - mmW / 2, mmY - mmH / 2, mmW, mmH);
		g.setColor(new Color(200, 200, 200, 45));
		g.drawRect(mmX - mmW / 2, mmY - mmH / 2, mmW, mmH);

		for(Hazard u : Game.getHazards())
		{
			int mmSize = 0;

			mmX = u.getCenterX() / ((float) PLAYFIELD_WIDTH) * w + x + w / 2;
			mmY = u.getCenterY() / ((float) PLAYFIELD_HEIGHT) * h + y + h / 2;
			
			if (u instanceof BigStar)
			{
				int yellow = 195 - ((BigStar) u).getEventShading();
				g.setColor(new Color(new Color(255, yellow, 0)));		
				mmSize = 15;
			}
			if (u instanceof Pulsar)
			{
				int white = 80 + ((Pulsar) u).getEventShading();
				g.setColor(new Color(new Color(white, white, 255)));			
				mmSize = 10;
			}
		
			g.fillOval(mmX, mmY, mmSize, mmSize);
		}
		
		for(Nebula u : Game.getNebulae())
		{
			int mmSize = 0;

			mmX = (u.getX() + u.getRadius()/2) / ((float) PLAYFIELD_WIDTH) * w + x + w/2;
			mmY = (u.getY() + u.getRadius()/2) / ((float) PLAYFIELD_HEIGHT) * h + y + h/2;
			
			if (u instanceof Nebula)
			{
				g.setColor(new Color(new Color(90, 40, 90, 80)));		
				mmSize = 15;
			}
		
			g.fillOval(mmX, mmY, mmSize, mmSize);
		}
		
		for (Unit u : units) 
		{
			int mmSize = 0;

			if (u instanceof Raider)
				mmSize = 1;
			if (u instanceof Miner)
				mmSize = 2;
			if (u instanceof Assault)
				mmSize = 3;
			if (u instanceof Specialist)
				mmSize = 4;
			if (u instanceof Support)
				mmSize = 2;

			mmX = u.getCenterX() / ((float) PLAYFIELD_WIDTH) * w + x + w / 2;
			mmY = u.getCenterY() / ((float) PLAYFIELD_HEIGHT) * h + y + h / 2;


			if (u instanceof BaseShip) 
			{
				g.setColor(new Color(Values.COLORS_150[u.getTeam()]));				
				g.fillRect(mmX - 4, mmY - 3, 9, 6);
			}
			else
			{
				g.setColor(new Color(Values.COLORS_150[u.getTeam()]));				
				g.fillOval(mmX, mmY, mmSize, mmSize);

			}

		}

		ArrayList<Asteroid> asteroids = Game.getAsteroids();

		for (Asteroid a : asteroids) {
			mmX = (a.getX()) / ((float) PLAYFIELD_WIDTH) * w + x + w / 2;
			mmY = (a.getY()) / ((float) PLAYFIELD_HEIGHT) * h + y + h / 2;
			
			if(a instanceof HighYieldAsteroid) g.setColor(new Color(170,90,90, 100));
			else g.setColor(new Color(150, 150, 150, 100));
			g.fillOval(mmX, mmY, a.getMaxMiners(), a.getMaxMiners());

		}
		
		TrueTypeFont fontName = Fonts.calibri16;		
		g.setFont(fontName);
		g.setColor(new Color(255, 255, 255, 180));
		int off = fontName.getWidth(Scenario.getTitle()) / 2;
		g.drawString(Scenario.getTitle() + "", Values.RESOLUTION_X - w/2 - 10 - off, 230);



	}

	void displayUpgrades(Graphics g) {
		int x = 0;
		int y = RESOLUTION_Y-80;
		int w = RESOLUTION_X;
		int gap = 40;

		ArrayList<Upgrade> upgrades = one.getUpgrades();

		for (int i = 0; i < upgrades.size(); i++) {
			upgrades.get(i).render(g, 5 + gap * i, y, Values.BLUE_ID);



		}
		
		int researchTimeLeft = one.getResearchTimeLeft() / Values.FRAMES_PER_SECOND;
		int off = Fonts.calibri12.getWidth(researchTimeLeft + "") / 2;
		g.setFont(Fonts.calibri12);
		g.setColor(new Color(255, 255, 255, 200));
		g.drawString(researchTimeLeft + "", 21 + gap * upgrades.size() - off, y+5);

		upgrades = two.getUpgrades();

		for (int i = 0; i < upgrades.size(); i++) {
			upgrades.get(i).render(g, w - 5 - gap * i - 32, y, Values.RED_ID);


		}
		
		researchTimeLeft =  two.getResearchTimeLeft() / Values.FRAMES_PER_SECOND;
		off = Fonts.calibri12.getWidth(researchTimeLeft + "") / 2;
		g.setFont(Fonts.calibri12);
		g.setColor(new Color(255, 255, 255, 200));
		g.drawString(researchTimeLeft + "", w - 22 - gap * upgrades.size() - off, y+5);

	}

	
	Color getRelativeColor(int myVal, int opponentVal)
	{
		if(myVal <= opponentVal)
		{
			return Color.white;
		}
		else if(myVal >= opponentVal && myVal < opponentVal*2)
		{
			return new Color(255, 255, 150);
		}
		else
		{
			return new Color(255, 255, 60);
		}
	}
	
	
	void displayBottomBar(Graphics g) {
		int x = 0;
		int h = 40;
		int y = RESOLUTION_Y - h;
		int w = RESOLUTION_X;

		g.resetLineWidth();
		
		// Box Background Image
		g.setColor(new Color(20, 20, 20, 170));
		g.fillRect(x - 3, y, w, h + 3);

		g.setColor(Color.white);
		g.setFont(Fonts.consolas24);

		int ind = 10; // x indent
		int dwn = 7; // y indent
		int gap = 80; // space between entries
		int txt = 38; // space between icon left and text left

		final float IMAGE_SIZE = 24;
		
		// Units

		if (numBlueMiners > 0) {
			imgBlueMiner.draw(x + ind, y + dwn, IMAGE_SIZE, IMAGE_SIZE);
			g.drawString(numBlueMiners + "", x + txt, y + dwn);
		}

		if (numBlueRaiders > 0) {
			imgBlueRaider.draw(x + ind + gap, y + dwn, IMAGE_SIZE, IMAGE_SIZE);
			g.drawString(numBlueRaiders + "", x + gap + txt, y + dwn);
		}

		if (numBlueAssaults > 0) {

			imgBlueAssault.draw(x + ind + gap * 2, y + dwn, IMAGE_SIZE, IMAGE_SIZE);
			g.drawString(numBlueAssaults + "", x + gap * 2 + txt, y + dwn);
		}

		if (numBlueSpecialist > 0) {
			imgBlueSpecialist.draw(x + ind + gap * 3, y + dwn, IMAGE_SIZE, IMAGE_SIZE);
			g.drawString(numBlueSpecialist + "", x + gap * 3 + txt, y + dwn);
		}

		if (numBlueSupports > 0) {
			imgBlueSupport.draw(x + ind + gap * 4, y + dwn, IMAGE_SIZE, IMAGE_SIZE);
			g.drawString(numBlueSupports + "", x + gap * 4 + txt, y + dwn);
		}

		ind = 70;
		txt = 38;

		if (numRedSupports > 0) {
			imgRedSupport.draw(w - (x + ind + gap * 4), y + dwn, IMAGE_SIZE, IMAGE_SIZE);
			g.drawString(numRedSupports + "", w - (x + gap * 4 + txt), y + dwn);
		}

		if (numRedSpecialist > 0) {
			imgRedSpecialist.draw(w - (x + ind + gap * 3), y + dwn, IMAGE_SIZE, IMAGE_SIZE);
			g.drawString(numRedSpecialist + "", w - (x + gap * 3 + txt), y + dwn);
		}

		if (numRedAssaults > 0) {
			imgRedAssault.draw(w - (x + ind + gap * 2), y + dwn, IMAGE_SIZE, IMAGE_SIZE);
			g.drawString(numRedAssaults + "", w - (x + gap * 2 + txt), y + dwn);
		}

		if (numRedRaiders > 0) {
			imgRedRaider.draw(w - (x + ind + gap), y + dwn, IMAGE_SIZE, IMAGE_SIZE);
			g.drawString(numRedRaiders + "", w - (x + gap + txt), y + dwn);
		}

		if (numRedMiners > 0) {
			imgRedMiner.draw(w - (x + ind), y + dwn, IMAGE_SIZE, IMAGE_SIZE);
			g.drawString(numRedMiners + "", w - (x + txt), y + dwn);
		}

		// Minerals Value

		if (Game.infoMode) {
			g.setFont(Fonts.calibri12);
			int off = Fonts.calibri12.getWidth((int) one.getMineralsMined() + " mined") / 2;
			g.setColor(new Color(255, 255, 255));
			g.drawString((int) one.getMineralsMined() + " mined", w / 2 - 200 - off, y + dwn - 52);


			off = Fonts.calibri12.getWidth((int)two.getMineralsMined() + " mined") / 2;
			g.setColor(new Color(255, 255, 255));
			g.drawString((int)two.getMineralsMined() + " mined", w / 2 + 200 - off, y + dwn - 52);
			
			g.setFont(Fonts.calibri12);
			off = Fonts.calibri12.getWidth((int) one.getMineralsLost() + " lost") / 2;
			g.setColor(new Color(255, 255, 255));
			g.drawString((int) one.getMineralsLost() + " lost", w / 2 - 200 - off, y + dwn - 35);


			off = Fonts.calibri12.getWidth((int) two.getMineralsLost() + " lost") / 2;
			g.setColor(new Color(255, 255, 255));
			g.drawString((int)two.getMineralsLost() + " lost", w / 2 + 200 - off, y + dwn - 35);
		}
		
		
		float armyPosition = .28f;
		float fleetPosition = .33f;
		float mineralsPosition = .38f;
		float latencyPosition = .43f;


		
		// Power
		drawDataBox(g, Values.BLUE, "Power", one.getArmyValue() + "", (int) (w * armyPosition), y + dwn - 5);
		drawDataBox(g, Values.RED, "Power", two.getArmyValue() + "", (int) (w * (1-armyPosition)), y + dwn - 5);

		// Capacity
		String label1 = ((int)((((float)one.getTotalValue()  / (float)Values.BASE_UNIT_VALUE_CAP) * 100))) + "%";
		String label2 = ((int)((((float)two.getTotalValue()  / (float)Values.BASE_UNIT_VALUE_CAP) * 100))) + "%";
		drawDataBox(g, Values.BLUE, "Capacity", label1 + "", (int) (w * fleetPosition), y + dwn - 5);
		drawDataBox(g, Values.RED, "Capacity", label2 + "", (int) (w * (1-fleetPosition)), y + dwn - 5);

		// Minerals
		drawDataBox(g, Values.BLUE, "Minerals", (int) one.getMinerals() + "", (int) (w * mineralsPosition), y + dwn - 5);
		drawDataBox(g, Values.RED, "Minerals", (int) two.getMinerals() + "", (int) (w * (1-mineralsPosition)), y + dwn - 5);

		// Latency
		drawDataBox(g, Values.BLUE, "Latency", one.getRecentLatency() + "", (int) (w * latencyPosition), y + dwn - 5);
		drawDataBox(g, Values.RED, "Latency", two.getRecentLatency() + "", (int) (w * (1-latencyPosition)), y + dwn - 5);

	}
	
	public void drawDataBox(Graphics g, Color c, String label, String data, int xPos, int yPos)
	{
		int off;
		
		g.setFont(Fonts.calibri20);
		
		
		// Data
		off = Fonts.calibri20.getWidth(data) / 2;
		g.setColor(getDataColor(label, c));
		g.drawString(data,xPos-off, yPos);

		//  Labels
		g.setFont(Fonts.calibri12);
		
		off = Fonts.calibri12.getWidth(label) / 2;
		g.setColor(c);
		g.drawString(label,xPos-off, yPos+22);
		
	}
	
	public Color getDataColor(String label, Color c)
	{
		if(label.equals("Power"))
		{
			if(c.equals(Values.BLUE))
			{
				return getRelativeColor(one.getArmyValue(),one.getArmyValueOpponent());
			}
			else
			{
				return getRelativeColor(two.getArmyValue(),two.getArmyValueOpponent());
			}
		}
		else if(label.equals("Capacity"))
		{
			if(c.equals(Values.BLUE))
			{
				return getRelativeColor(one.getTotalValue(),(int) (Values.BASE_UNIT_VALUE_CAP*.45));
			}
			else
			{
				return getRelativeColor(two.getTotalValue(),(int)(Values.BASE_UNIT_VALUE_CAP*.45));
			}
		}
		else if(label.equals("Latency"))
		{
			if(c.equals(Values.BLUE))
			{
				if(one.getRecentLatency() > Values.LATENCY_RED)
				{
					return Color.red;
				}
				else if(one.getRecentLatency() > Values.LATENCY_YELLOW)
				{
					return Color.yellow;
				}
				else
				{
					return Color.white;
				}
			}
			else
			{
				if(two.getRecentLatency() > Values.LATENCY_RED)
				{
					return Color.red;
				}
				else if(two.getRecentLatency() > Values.LATENCY_YELLOW)
				{
					return Color.yellow;
				}
				else
				{
					return Color.white;
				}
			}
		}
		else
		{
			return Color.white;
		}
	}
	
	



}
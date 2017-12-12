
package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import data.Data;
import ui.Fonts;
import ui.Images;


public class End extends BasicGameState 
{
	StateBasedGame sbg;
	GameContainer gc;
	int id;

	
	End(int id)
	{
		this.id = id;
	}
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		this.sbg = sbg;
		this.gc = gc;
		Images.loadEndImages();

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{	
		Images.endBackground.draw();
		
		g.setColor(Values.COLORS_35[Data.getWinnerID()]);
		Images.teamLogos.getSubImage(0, Data.getWinnerID()).draw(Values.RESOLUTION_X-124, 25, 66, 83);

		g.fillRect(0,  0, Values.RESOLUTION_X, Values.RESOLUTION_Y);
		renderPlayerData(g, Values.BLUE_ID);
		renderPlayerData(g, Values.RED_ID);

		g.setFont(Fonts.consolas64);	
		g.setColor(Color.black);
		String win = "Team " + Data.getWinner() + " Wins";
		g.drawString(win.toUpperCase(), 38, 38);
		g.setColor(Values.COLORS[Data.getWinnerID()]);
		g.drawString(win.toUpperCase(), 35, 35);
		
		Data.renderHeatmap(Values.RESOLUTION_X / 2 - 120, 120, g);
		
	}
		
	public void renderPlayerData(Graphics g, int id)
	{
		drawLeftBar("PRODUCTION", new Color(45, 20, 45, 225), 120, Data.getProduction(id), Data.getProductionPercent(id), id, g);
		drawLeftBar("DAMAGE",  new Color(75, 75, 20, 225), 240,Data.getDamage(id), Data.getDamagePercent(id), id, g);
		drawLeftBar("MITIGATION",  new Color(85, 45, 20, 225), 360,Data.getBlock(id), Data.percentOfAttemptedDamageBlocked(id), id, g);
		drawLeftBar("HEALING", new Color(20, 45, 20, 225), 480, Data.getHeal(id), Data.percentOfDamageHealed(id), id, g);
	}
	
	
	public void drawLeftBar(String label, Color c, int y, int value, float percent, int id, Graphics g)
	{
		drawBar(label, c, y, value, percent, id, g, 20);
	}
	public void drawRightBar(String label, Color c, int y, int value, float percent, int id, Graphics g)
	{
		drawBar(label, c, y, value, percent, id, g, Values.RESOLUTION_X/2);
	}
	
	private void drawBar(String label, Color c, int y, int value, float percent, int id, Graphics g, int x)
	{
		final int BAR_X_OFFSET = 10;
		final int BAR_Y_OFFSET = 33;
		final int BAR_HEIGHT = 25;
		final int BAR_WIDTH = Values.RESOLUTION_X/2 - 170;
		
		g.setLineWidth(4);
		
		// General things that you only do once
		if(id == Values.BLUE_ID)
		{
			// Box Border 
			g.setColor(Color.black);
			g.drawRect(x,  y, BAR_WIDTH+20, 100);
			
			// Box
			g.setColor(c);
			g.fillRect(x,  y, BAR_WIDTH+20, 100);
			
			// Title with Shadow
			g.setFont(Fonts.consolas24);	
			g.setColor(new Color(0, 0, 0));
			g.drawString(label, x+9, y+5);
			g.setColor(new Color(205, 205, 135));
			g.drawString(label, x+7, y+3);
		}

		// Blue stuff
		if(id == Values.BLUE_ID)
		{		
			g.setColor(Color.black);
			g.drawRect(x+BAR_X_OFFSET, y+BAR_Y_OFFSET, BAR_WIDTH, BAR_HEIGHT);
			g.setColor(new Color(6, 160, 204, 100));
			g.fillRect(x+BAR_X_OFFSET, y+BAR_Y_OFFSET, BAR_WIDTH, BAR_HEIGHT);
			g.setColor(new Color(6, 160, 204, 255));
			g.fillRect(x+BAR_X_OFFSET, y+BAR_Y_OFFSET, percent * BAR_WIDTH, BAR_HEIGHT);
		}
		
		// Red stuff
		else
		{
			y += BAR_HEIGHT + 9;
			g.setColor(Color.black);
			g.drawRect(x+BAR_X_OFFSET, y+BAR_Y_OFFSET, BAR_WIDTH, BAR_HEIGHT);
			g.setColor(new Color(225, 25, 30, 100));
			g.fillRect(x+BAR_X_OFFSET, y+BAR_Y_OFFSET, BAR_WIDTH, BAR_HEIGHT);
			g.setColor(new Color(225, 25, 30, 255));
			g.fillRect(x+BAR_X_OFFSET, y+BAR_Y_OFFSET, percent * BAR_WIDTH, BAR_HEIGHT);
		}
		
		// Label Each Bar
		g.resetLineWidth();
		g.setFont(Fonts.calibri16);	

	//	g.setColor(new Color(0, 0, 0));
	//	g.drawString(value + " (" +Math.round(percent*100) + "%)", x+BAR_X_OFFSET+12, y+BAR_Y_OFFSET+7);
		g.setColor(new Color(255, 255, 255));
		g.drawString(value + " (" +Math.round(percent*100) + "%)", x+BAR_X_OFFSET+10, y+BAR_Y_OFFSET+5);
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{

	}
	
	public void enter(GameContainer gc, StateBasedGame sbg)
	{
		Data.calculate();
	}
	
	public void leave(GameContainer gc, StateBasedGame sbg)
	{
		
	}
		
	public void keyReleased(int key, char c) 
	{
		sbg.enterState(Engine.TITLE_ID, 
				new FadeOutTransition(Color.black, Values.TRANSITION_FADE_TIME), 
				new FadeInTransition(Color.black, Values.TRANSITION_FADE_TIME));
		
	}

	public int getID() 
	{
		return id;
	}

}

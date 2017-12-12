package core;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import ui.Audio;
import ui.Fonts;
import ui.Images;

public class Splash extends BasicGameState 
{
	StateBasedGame sbg;
	GameContainer gc;
	Image splash;
	Image splashScaled;
	int id;
	int step;
	float progress = 0;
	String message = "";
	
	Splash(int id)
	{
		this.id = id;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		this.sbg = sbg;
		gc.setMouseGrabbed(true);

		splash = new Image("res/splash.png");
		splashScaled = 	splash.getScaledCopy(Values.RESOLUTION_X, Values.RESOLUTION_Y);
		Fonts.calibri32 = new TrueTypeFont(new Font("Calibri", Font.BOLD, 32), false);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		if(splash != null)
		{  
			splashScaled.draw();
		}
		
		String m = message;
		
		TrueTypeFont f = Fonts.calibri32;
		g.setFont(f);	
		g.setColor(new Color(220, 220, 220, 255));
		g.drawString(m, Values.RESOLUTION_X/2 - f.getWidth(m)/2, Values.RESOLUTION_Y - f.getHeight(m)/2 - 50);
		if(step == 0)
		{
			step = 1;
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{		
		if(step == 1)
		{	
			Fonts.loadFonts();
			step = 2;
			message = "Loading Images";
		}
		else if(step == 2)
		{
			Images.loadImages();
			step = 3;
			message = "Loading Audio";
		}
		else if(step == 3)
		{
			Audio.loadAudio();
			step = 4;
			message = "Setting Music";
		}
		else if(step == 4)
		{
			Audio.setRandomGameMusic();
			Audio.loadGameMusicFile();
			step = 5;
			message = "Ready!";
		}

	}
	
	public void enter()
	{
		message = "Loading Fonts";
	}
		
	public void keyReleased(int key, char c) 
	{
		if(step == 5)
		{
			try {
				Audio.playGameMusic();
			} catch (SlickException e) {
		
				e.printStackTrace();
			}
			
			step = 4;
			
		sbg.enterState(Engine.GAME_ID, 
				new FadeOutTransition(Color.black, Values.TRANSITION_FADE_TIME_SLOW), 
				new FadeInTransition(Color.black, Values.TRANSITION_FADE_TIME_SLOW));
		

		}
	}

	public int getID() 
	{
		return id;
	}

}

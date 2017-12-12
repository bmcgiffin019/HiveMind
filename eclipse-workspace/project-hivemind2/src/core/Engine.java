
package core;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Engine extends StateBasedGame 
{
    public static final int TITLE_ID = 0;
    public static final int GAME_ID  = 1;
    public static final int END_ID  = 2;

    private BasicGameState title;
    private BasicGameState game;
    private BasicGameState end;

	public Engine(String name) 
	{
		super(name);
		
		title = new Splash(TITLE_ID);
		game = new Game(GAME_ID);
		end = new End(END_ID);
	}

	public void initStatesList(GameContainer gc) throws SlickException 
	{
		addState(title);
		addState(game);
		addState(end);
	}

	public static void main(String[] args) 
	{
		try {
			AppGameContainer appgc = new AppGameContainer(new Engine("Project Hivemind"));

			appgc.setDisplayMode(Values.RESOLUTION_X, Values.RESOLUTION_Y, true);
			appgc.setTargetFrameRate(Values.FRAMES_PER_SECOND);
			appgc.start();
			appgc.setVSync(true);

		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
}
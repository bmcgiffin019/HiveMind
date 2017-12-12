package ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import core.Values;

public class Camera 
{
	public static final float AUTO_CAM_ZOOM_LEVEL = .50f;
	
	// autoCam does not work
	boolean autoCam = false;
	boolean moving = false;
	
	// Basic Zooming and Scrolling
	public static final float ZOOM_RATE = .005f;
	public static final float ZOOM_MAX = .50f;
	public static final float ZOOM_MIN = .10f;
	public static final float ZOOM_BASE = .20f;
	public static final float HEALTHBAR_ZOOM_THRESHOLD = .30f;

	public static final float SCROLL_RATE_BASE_AUTO = 2;
	public static final float SCROLL_RATE_BASE_MOUSE = 2;
	public static final float SCROLL_RATE_BASE_KEY = 10;
	

	private static float x;
	private static float y;

	private static float zoom;

	private static int viewSizeX;
	private static int viewSizeY;

	GameContainer gc;

	
	public boolean isAuto()
	{
		return autoCam;
	}
	public Camera(GameContainer gc) 
	{
		if(Math.random() > .5)
		{
			x = Values.RESOLUTION_X / 2 - Values.BASE_SHIP_X_POSITION;
			y = Values.RESOLUTION_Y / 2 - Values.BASE_SHIP_Y_POSITION;

		}
		else
		{
			x = Values.RESOLUTION_X / 2 + Values.BASE_SHIP_X_POSITION;
			y = Values.RESOLUTION_Y / 2 + Values.BASE_SHIP_Y_POSITION;
		}
		
		
		this.gc = gc;


		zoom = ZOOM_BASE;
		viewSizeX = (int) (Values.RESOLUTION_X * 1 / zoom);
		viewSizeY = (int) (Values.RESOLUTION_Y * 1 / zoom);

	}
	
	public void cameraControl()
	{
		float scroll_rate = SCROLL_RATE_BASE_KEY * 1 / zoom;

		if (gc.getInput().isKeyDown(Input.KEY_W))
			y -= scroll_rate;
		if (gc.getInput().isKeyDown(Input.KEY_S))
			y += scroll_rate;
		if (gc.getInput().isKeyDown(Input.KEY_A))
			x -= scroll_rate;
		if (gc.getInput().isKeyDown(Input.KEY_D))
			x += scroll_rate;
		
		
		if (gc.getInput().isKeyDown(Input.KEY_HOME)) {
			x = 0;
			y = 0;
		}

		if (gc.getInput().isMouseButtonDown(0))
			zoomIn();
		if (gc.getInput().isMouseButtonDown(1))
			zoomOut();
		if (gc.getInput().isMouseButtonDown(2)) {
			x = 0;
			y = 0;
		}

	}
	
	public void update() 
	{
		

			cameraControl();

	}

	public static float getX() {
		return x;
	}

	public static float getY() {
		return y;
	}

	public static float getViewWidth() {
		return viewSizeX;
	}

	public static float getViewHeight() {
		return viewSizeY;
	}

	public static void shiftCamera(float deltaX, float deltaY) 
	{		
		final float DELTA_X = deltaX * 1 / zoom;
		final float DELTA_Y = deltaY * 1 / zoom;

		// West
		if (DELTA_X < 0 && x - viewSizeX / 2 + DELTA_X > -Values.PLAYFIELD_WIDTH / 2) {
			x += DELTA_X * SCROLL_RATE_BASE_MOUSE;
		} else if (DELTA_X < 0) {
			x = -Values.PLAYFIELD_WIDTH / 2 + viewSizeX / 2;
		}

		// East
		if (DELTA_X > 0 && x + viewSizeX / 2 + DELTA_X < Values.PLAYFIELD_WIDTH / 2) {
			x += DELTA_X * SCROLL_RATE_BASE_MOUSE;
		} else if (DELTA_X > 0) {
			x = Values.PLAYFIELD_WIDTH / 2 - viewSizeX / 2;
		}

		// North
		if (DELTA_Y < 0 && y - viewSizeY / 2 + DELTA_Y > -Values.PLAYFIELD_HEIGHT / 2) {
			y += DELTA_Y * SCROLL_RATE_BASE_MOUSE;
		} else if (DELTA_Y < 0) {
			y = -Values.PLAYFIELD_HEIGHT / 2 + viewSizeY / 2;
		}

		// South
		if (DELTA_Y > 0 && y + viewSizeY / 2 + DELTA_Y < Values.PLAYFIELD_HEIGHT / 2) {
			y += DELTA_Y * SCROLL_RATE_BASE_MOUSE;
		} else if (DELTA_Y > 0) {
			y = Values.PLAYFIELD_HEIGHT / 2 - viewSizeY / 2;
		}

	}

	public static void centerView(float xPos, float yPos) {
		x = xPos;
		y = yPos;
	}

	
	public static void zoomIn(float amount)
	{
		viewSizeX = (int) (Values.RESOLUTION_X * 1 / zoom);
		viewSizeY = (int) (Values.RESOLUTION_Y * 1 / zoom);

		zoom += amount;
		if (zoom < ZOOM_MIN)
			zoom = ZOOM_MIN;
		if (zoom > ZOOM_MAX)
			zoom = ZOOM_MAX;
	}
	
	public static void zoomOut(float amount) {
		viewSizeX = (int) (Values.RESOLUTION_X * 1 / zoom);
		viewSizeY = (int) (Values.RESOLUTION_Y * 1 / zoom);

		zoom -= amount;
		if (zoom < ZOOM_MIN)
			zoom = ZOOM_MIN;
		if (zoom > ZOOM_MAX)
			zoom = ZOOM_MAX;
	}
	
	
	public static void zoomIn() {
		viewSizeX = (int) (Values.RESOLUTION_X * 1 / zoom);
		viewSizeY = (int) (Values.RESOLUTION_Y * 1 / zoom);

		zoom += ZOOM_RATE;
		if (zoom < ZOOM_MIN)
			zoom = ZOOM_MIN;
		if (zoom > ZOOM_MAX)
			zoom = ZOOM_MAX;
	}

	public static void zoomOut() {
		viewSizeX = (int) (Values.RESOLUTION_X * 1 / zoom);
		viewSizeY = (int) (Values.RESOLUTION_Y * 1 / zoom);

		zoom -= ZOOM_RATE;
		if (zoom < ZOOM_MIN)
			zoom = ZOOM_MIN;
		if (zoom > ZOOM_MAX)
			zoom = ZOOM_MAX;
	}
	
	public static float getZoom() {
		return zoom;
	}

}

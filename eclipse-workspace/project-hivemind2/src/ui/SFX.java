package ui;

import java.io.InputStream;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Point;

import core.Game;
import core.Utility;

public class SFX extends Sound {
	public SFX(InputStream arg0, String arg1) throws SlickException {
		super(arg0, arg1);
	}

	public SFX(String filename) throws SlickException {
		super(filename);
	}

	public void play(Point pos) {
		if (Game.getGameSpeed() < 5) {
			if (isInView(pos)) {
				play(1f, soundMultiplierZoom());
			}
			else if (isNearView(pos)) {
				play(1f, soundMultiplierDistance(pos) * soundMultiplierZoom());
			}
		}
	}
	

	public void play(Point pos, float pitch, float volume) {
		if (Game.sfxOn) {
			if (isInView(pos)) {
				play(pitch, volume * soundMultiplierZoom());
			}
			else if (isNearView(pos)) {
				play(pitch, volume * soundMultiplierDistance(pos) * soundMultiplierZoom());
			}
		}
	}


	public boolean isInView(Point pos) {
		return pos.getX() >= Camera.getX() - Camera.getViewWidth() / 2
				&& pos.getX() <= Camera.getX() + Camera.getViewWidth() / 2
				&& pos.getY() >= Camera.getY() - Camera.getViewWidth() / 2
				&& pos.getY() <= Camera.getY() + Camera.getViewHeight() / 2;
	}

	public boolean isNearView(Point pos) {
		return pos.getX() >= Camera.getX() - Camera.getViewWidth()
				&& pos.getX() <= Camera.getX() + Camera.getViewWidth()
				&& pos.getY() >= Camera.getY() - Camera.getViewWidth()
				&& pos.getY() <= Camera.getY() + Camera.getViewHeight();
	}

	public float soundMultiplierDistance(Point pos) {
		return 1 - (Utility.distance(pos.getX(), pos.getY(), Camera.getX(), Camera.getY())
				/ ((Camera.getViewWidth() + Camera.getViewHeight() / 2)));
	}

	public float soundMultiplierZoom() {
		return Camera.getZoom() * 2;
	}

}

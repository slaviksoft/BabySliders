package com.slaviksoft.games.babysliders.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.slaviksoft.games.babysliders.BabySlidersGame;

import custom.IAdRequestHandler;

public class DesktopLauncher implements IAdRequestHandler {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1080;
		config.height = 1920;
		new LwjglApplication(new BabySlidersGame(), config);
	}

	@Override
	public void showAds(boolean show) {

	}
}

package com.limewire.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.limewire.game.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "LimeWire";
		config.width = 563;
		config.height = 563;
		config.resizable = false;
		new LwjglApplication(new Game(), config);
	}
}

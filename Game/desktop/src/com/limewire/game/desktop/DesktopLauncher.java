package com.limewire.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.limewire.game.data.Game;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "LimeWire";
		config.addIcon("Logo.png", Files.FileType.Internal);
		config.width = 1600;
		config.height = 900;
		config.resizable = false;
		new LwjglApplication(new Game(), config);
	}
}

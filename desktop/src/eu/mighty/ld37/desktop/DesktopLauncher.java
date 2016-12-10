package eu.mighty.ld37.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import eu.mighty.ld37.MightyLD37Game;
import eu.mighty.ld37.game.Defaults;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Defaults.windowWidth;
		config.height = Defaults.windowHeight;
		config.resizable = true;
		config.title = Defaults.windowTitle;
		new LwjglApplication(new MightyLD37Game(), config);
	}
}

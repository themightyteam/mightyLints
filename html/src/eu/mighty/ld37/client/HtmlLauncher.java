package eu.mighty.ld37.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import eu.mighty.ld37.MightyLD37Game;
import eu.mighty.ld37.game.Defaults;

public class HtmlLauncher extends GwtApplication {

	@Override
	public GwtApplicationConfiguration getConfig() {
		return new GwtApplicationConfiguration(Defaults.windowWidth,
				Defaults.windowHeight);
	}

	@Override
	public ApplicationListener createApplicationListener() {
		return new MightyLD37Game();
	}
}
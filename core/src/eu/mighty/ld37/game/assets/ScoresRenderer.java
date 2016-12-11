package eu.mighty.ld37.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import eu.mighty.ld37.game.Defaults;

public class ScoresRenderer {

	private BitmapFont bitmapFont;

	private SpriteBatch mybatch;

	public ScoresRenderer() {
		bitmapFont = new BitmapFont(
				Gdx.files.internal("fonts/URW_Chancery_L.fnt"),
				Gdx.files.internal("fonts/URW_Chancery_L.png"), false);
		mybatch = new SpriteBatch();
	}

	public void render(int cyanTeamScore, int orangeTeamScore) {

		mybatch.begin();

		bitmapFont.setColor(Color.CYAN);
		bitmapFont.draw(mybatch,
				"cyan lints: " + String.valueOf(cyanTeamScore),
				Defaults.windowWidth / 20,
				Defaults.windowHeight - 20);

		bitmapFont.setColor(Color.ORANGE);
		bitmapFont.draw(mybatch,
				"orange lints: " + String.valueOf(cyanTeamScore),
				Defaults.windowWidth * 13 / 20,
				Defaults.windowHeight - 20);
		mybatch.end();
	}
}

package eu.mighty.ld37;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import eu.mighty.ld37.game.screen.BattleScreen;

public class MightyLD37Game extends Game {
	// used by all screens
	public SpriteBatch batcher;

	@Override
	public void create () {
		this.batcher = new SpriteBatch();
		setScreen(new BattleScreen(this));
	}
	
	@Override
	public void render() {
		super.render();
	}
}

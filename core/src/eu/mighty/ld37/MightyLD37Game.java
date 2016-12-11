package eu.mighty.ld37;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.assets.AudioClips;
import eu.mighty.ld37.game.assets.Musics;
import eu.mighty.ld37.game.screen.BattleScreen;

public class MightyLD37Game extends Game {
	// used by all screens
	public SpriteBatch batcher;
	public AudioClips audioClips;
	public Musics musics;

	@Override
	public void create () {
		this.batcher = new SpriteBatch();
		this.audioClips = new AudioClips();
		this.musics = new Musics();
		
		setScreen(new BattleScreen(this, Defaults.ROLE_GOAL));
	}
	
	@Override
	public void render() {
		super.render();
	}
}

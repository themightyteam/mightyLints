package eu.mighty.ld37.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


public class AudioClips {
	private static Sound explosion;
	private static Sound fire;

	public AudioClips() {
		explosion = Gdx.audio.newSound(Gdx.files
				.internal("sounds/explosion.wav"));
		fire = Gdx.audio.newSound(Gdx.files.internal("sounds/fire.wav"));
	}

	public void playExplosion() {
		AudioClips.explosion.play();
	}

	public void playFire() {
		AudioClips.fire.play();
	}
}
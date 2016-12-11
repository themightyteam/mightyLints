package eu.mighty.ld37.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


public class AudioClips {
	private static Sound explosion;
	private static Sound fire;
	private static Sound respawn;
	private static Sound respawnPlayer;

	public AudioClips() {
		explosion = Gdx.audio.newSound(Gdx.files
				.internal("sounds/explosion.wav"));
		fire = Gdx.audio.newSound(Gdx.files.internal("sounds/fire.wav"));
		respawn = Gdx.audio.newSound(Gdx.files.internal("sounds/respawn.wav"));
		respawnPlayer = Gdx.audio.newSound(Gdx.files
				.internal("sounds/respawnPlayer.wav"));
	}

	public void playExplosion() {
		AudioClips.explosion.play();
	}

	public void playFire() {
		AudioClips.fire.play();
	}

	public void playRespawn() {
		AudioClips.respawn.play();
	}

	public void playRespawnPlayer() {
		AudioClips.respawnPlayer.play();
	}
}
package eu.mighty.ld37.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;


public class AudioClips {
	private static Sound explosion;
	private static Sound fire;
	private static Sound respawn;
	private static Sound respawnPlayer;
	private static Sound tooMighty;

	public static float VOLUME_HIGH = 0.8f;
	public static float VOLUME_MEDIUM = 0.5f;
	public static float VOLUME_LOW = 0.1f;

	public AudioClips() {
		explosion = Gdx.audio.newSound(Gdx.files
				.internal("sounds/explosion.wav"));
		fire = Gdx.audio.newSound(Gdx.files.internal("sounds/fire.wav"));
		respawn = Gdx.audio.newSound(Gdx.files.internal("sounds/respawn.wav"));
		respawnPlayer = Gdx.audio.newSound(Gdx.files
				.internal("sounds/respawnPlayer.wav"));
		tooMighty = Gdx.audio.newSound(Gdx.files
				.internal("sounds/tooMighty.wav"));
	}

	public void playExplosion(float volume) {
		AudioClips.explosion.play(volume);
	}

	public void playFire(float volume) {
		AudioClips.fire.play(volume);
	}

	public void playRespawn(float volume) {
		AudioClips.respawn.play(volume);
	}

	public void playRespawnPlayer(float volume) {
		AudioClips.respawnPlayer.play(volume);
	}

	public void playtooMigthy(float volume) {
		AudioClips.tooMighty.play(volume);
	}

	public float calculateVolume(Vector3 pos1, Vector3 pos2) {
		float dist = pos1.dst2(pos2);

		System.out.println(dist);
		if (dist < 10000) {
			return this.VOLUME_HIGH;
		}

		if (dist < 100000) {
			return this.VOLUME_MEDIUM;
		}
		return this.VOLUME_LOW;
	}
}
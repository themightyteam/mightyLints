package eu.mighty.ld37.game.assets;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Musics {

	Music turkishMarch;


	public Musics() {
		this.turkishMarch = Gdx.audio.newMusic(Gdx.files
				.internal("sounds/turkishMarch.mp3"));
	}

	public void playTurkishMarch() {
		this.turkishMarch.play();
		this.turkishMarch.setLooping(true);
	}

	public void stopTurkishMarch() {
		this.turkishMarch.stop();
		this.turkishMarch.setLooping(true);
	}

	public void pauseTurkishMarch() {
		this.turkishMarch.pause();
	}

	public void dispose() {
		this.turkishMarch.stop();
		this.turkishMarch.dispose();
	}
}
package eu.mighty.ld37.game.listeners;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;

import eu.mighty.ld37.game.assets.AudioClips;
import eu.mighty.ld37.game.components.BulletComponent;
import eu.mighty.ld37.game.components.ShipComponent;

public class AudioListener implements EntityListener {
	private ComponentMapper<BulletComponent> bm = ComponentMapper
			.getFor(BulletComponent.class);
	private ComponentMapper<ShipComponent> sm = ComponentMapper
			.getFor(ShipComponent.class);

	private AudioClips audioClips;

	public AudioListener(AudioClips audioClips) {
		this.audioClips = audioClips;
	}

	@Override
	public void entityAdded(Entity entity) {
		BulletComponent bc = bm.get(entity);

		if (bc != null) {
			this.audioClips.playFire();
			return;
		}

		ShipComponent sc = sm.get(entity);
		System.out.println("playrespawn");
		if (sc != null) {
			this.audioClips.playRespawn();
			return;
		}

	}

	@Override
	public void entityRemoved(Entity entity) {
		ShipComponent sc = sm.get(entity);

		if (sc != null) {
			this.audioClips.playExplosion();
			return;
		}

	}

}

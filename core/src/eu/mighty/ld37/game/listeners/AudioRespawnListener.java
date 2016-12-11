package eu.mighty.ld37.game.listeners;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.math.Vector3;

import eu.mighty.ld37.game.assets.AudioClips;
import eu.mighty.ld37.game.components.PlayerComponent;
import eu.mighty.ld37.game.components.ShipComponent;
import eu.mighty.ld37.game.components.TransformComponent;

public class AudioRespawnListener implements EntityListener {
	private ComponentMapper<ShipComponent> sm = ComponentMapper
			.getFor(ShipComponent.class);
	private ComponentMapper<TransformComponent> tm = ComponentMapper
			.getFor(TransformComponent.class);
	private ComponentMapper<PlayerComponent> pm = ComponentMapper
			.getFor(PlayerComponent.class);

	private AudioClips audioClips;
	private Entity player;

	public AudioRespawnListener(AudioClips audioClips, Entity player) {
		this.audioClips = audioClips;
		this.player = player;
	}

	@Override
	public void entityAdded(Entity entity) {
		PlayerComponent pc = pm.get(entity);

		if (pc != null) {
			// this.audioClips.playExplosion(this.audioClips.VOLUME_MEDIUM);
			this.audioClips.playtooMigthy(this.audioClips.VOLUME_HIGH);
			return;
		}

	}

	@Override
	public void entityRemoved(Entity entity) {
		PlayerComponent pc = pm.get(entity);

		if (pc != null) {
			this.audioClips.playRespawnPlayer(this.audioClips.VOLUME_HIGH);
			return;
		}

		ShipComponent sc = sm.get(entity);
		Vector3 lintPos = tm.get(entity).pos;
		Vector3 playerPos = tm.get(player).pos;

		if (sc != null) {
			this.audioClips.playRespawn(this.audioClips.calculateVolume(
					lintPos, playerPos));
			return;
		}
	}
}

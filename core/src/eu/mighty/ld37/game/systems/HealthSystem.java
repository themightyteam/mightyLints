package eu.mighty.ld37.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import eu.mighty.ld37.game.assets.AudioClips;
import eu.mighty.ld37.game.components.HealthComponent;

public class HealthSystem extends IteratingSystem {
	private ComponentMapper<HealthComponent> hm = ComponentMapper.getFor(HealthComponent.class);

	private AudioClips audioClips;

	public HealthSystem(AudioClips audioClips) {
		super(Family.all(HealthComponent.class).get());
		this.audioClips = audioClips;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		HealthComponent hc = hm.get(entity);

		if (hc.health <= 0) {
			this.getEngine().removeEntity(entity);
			this.audioClips.playExplosion();
		}
	}
}
